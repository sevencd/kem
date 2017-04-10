package cn.ilanhai.kem.dao.user.frontuser;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.dao.AbstractJdbcDao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.enums.VoucherType;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.IdentifyType;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.user.backuser.BackUserEntity;
import cn.ilanhai.kem.domain.user.backuser.QueryByUserNameNoConditionEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserInfoEntity;
import cn.ilanhai.kem.domain.user.frontuser.QueryByPhoneNoConditionEntity;
import cn.ilanhai.kem.domain.user.frontuser.dto.QueryFrontUserByCompany;
import cn.ilanhai.kem.domain.user.frontuser.dto.SearchFrontUserInfoDto;
import cn.ilanhai.kem.util.MD5Util;

@Component("frontuserDao")
public class FrontuserDao extends BaseDao {
	public FrontuserDao() {
		super();
	}

	/**
	 * 判断该用户是否存在
	 * 
	 * @param phoneNo
	 *            电话号码
	 * @return true 存在，false 不存在
	 * @throws DaoAppException
	 */
	private boolean isExists(final FrontUserEntity frontUserEntity) throws DaoAppException {
		final String sqlScript = "SELECT user_id FROM user_front_user WHERE user_id=?";
		JdbcTemplate template = null;
		Integer val = -1;
		if (frontUserEntity == null)
			throw new DaoAppException("");

		template = this.getJdbcTemplate();
		val = template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, Str.isNullOrEmpty(frontUserEntity.getUserID()) ? "" : frontUserEntity.getUserID());
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next())
					return 1;
				return -1;
			}
		});
		return val > 0;
	}

	@Override
	public int save(Entity enity) throws DaoAppException {
		try {
			if (enity instanceof FrontUserEntity) {
				FrontUserEntity frontUserEntity = null;

				frontUserEntity = (FrontUserEntity) enity;
				if (!this.isExists(frontUserEntity))
					return this.add(frontUserEntity);
				return this.update(frontUserEntity);

			} else if (enity instanceof FrontUserInfoEntity) {
				return saveInfo((FrontUserInfoEntity) enity);
			} else {
				return super.save(enity);
			}
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private int add(final FrontUserEntity entity) throws DaoAppException {
		return addNewUser(entity);
	}

	private int update(final FrontUserEntity entity) throws DaoAppException {
		return updateNewUser(entity);
	}

	private int oldAddUser(final FrontUserEntity entity) {
		final String sqlScript = "INSERT INTO user_front_user(user_id,user_name,user_type,user_phone,user_email,createtime,login_pwd,status)VALUES(?,?,?,?,?,?,?,?);";
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		holder = new GeneratedKeyHolder();
		val = template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				int i = 1;
				statement = con.prepareStatement(sqlScript);
				statement.setString(i++, entity.getUserID());
				statement.setString(i++, entity.getUserName());
				statement.setString(i++, String.format("%s", entity.getUserType().getValue()));
				statement.setString(i++, entity.getPhoneNo());
				statement.setString(i++, entity.getEmail());
				statement.setTimestamp(i++, new Timestamp(entity.getCreatetime().getTime()));
				statement.setString(i++, entity.getLoginPwd());
				statement.setString(i++, String.format("%s", entity.getStatus().getValue()));
				return statement;
			}
		}, holder);
		if (val > 0)
			return val;
		return -1;
	}

	private int addNewUser(final FrontUserEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO user_main_user (user_id, user_type, createtime, state) VALUES (?, ?, ?, ?);";
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		holder = new GeneratedKeyHolder();
		val = template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				int i = 1;
				statement = con.prepareStatement(sqlScript);
				statement.setObject(i++, entity.getUserID());
				statement.setObject(i++, entity.getUserType().getValue());
				statement.setObject(i++, new Timestamp(entity.getCreatetime().getTime()));
				statement.setObject(i++, entity.getStatus().getValue());
				return statement;
			}
		}, holder);
		if (val > 0) {

			if (!addIdentity(entity)) {
				return -1;
			}
			if (!addVoucher(entity)) {
				return -1;
			}
			if (entity.getInfos() != null && entity.getInfos().size() > 0) {
				// 更新信息 先无效再添加
				for (FrontUserInfoEntity info : entity.getInfos()) {
					if (isExistInfo(info)) {
						if (!updateInfo(info)) {
							return -1;
						}
					} else {
						if (!addInfo(info)) {
							return -1;
						}
					}
				}
			}

			// 暂时只关注 电话 邮件
			// 电话
			// FrontUserInfoEntity frontUserInfoEntity_phone = new
			// FrontUserInfoEntity();
			// frontUserInfoEntity_phone.setUserID(entity.getUserID());
			// frontUserInfoEntity_phone.setInfoType(UserInfoType.PHONE.getValue());
			// frontUserInfoEntity_phone.setInfo(entity.getPhoneNo());
			// frontUserInfoEntity_phone.setInfoState(entity.getStatus().getValue());
			// if (!addInfo(frontUserInfoEntity_phone)) {
			// return -1;
			// }
			// // 姓名
			// FrontUserInfoEntity frontUserInfoEntity_name = new
			// FrontUserInfoEntity();
			// frontUserInfoEntity_name.setUserID(entity.getUserID());
			// frontUserInfoEntity_name.setInfoType(UserInfoType.NAME.getValue());
			// frontUserInfoEntity_name.setInfo(entity.getUserName());
			// frontUserInfoEntity_name.setInfoState(entity.getStatus().getValue());
			// if (!addInfo(frontUserInfoEntity_name)) {
			// return -1;
			// }
			// // 邮件
			// FrontUserInfoEntity frontUserInfoEntity_email = new
			// FrontUserInfoEntity();
			// frontUserInfoEntity_email.setUserID(entity.getUserID());
			// frontUserInfoEntity_email.setInfoType(UserInfoType.EMAIL.getValue());
			// frontUserInfoEntity_email.setInfo(entity.getUserName());
			// frontUserInfoEntity_email.setInfoState(entity.getStatus().getValue());
			// if (!addInfo(frontUserInfoEntity_email)) {
			// return -1;
			// }
			return val;
		}
		return -1;
	}

	private int saveInfo(FrontUserInfoEntity info) throws DaoAppException {
		if (isExistInfo(info)) {
			if (!updateInfo(info)) {
				return -1;
			}
		} else {
			if (!addInfo(info)) {
				return -1;
			}
		}
		return -1;
	}

	private int updateNewUser(final FrontUserEntity entity) throws DaoAppException {
		final String sqlScript = "UPDATE user_main_user SET user_type=?,state=? WHERE user_id=?;";
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		holder = new GeneratedKeyHolder();
		val = template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				int i = 1;
				statement = con.prepareStatement(sqlScript);
				statement.setObject(i++, entity.getUserType().getValue());
				statement.setObject(i++, entity.getStatus().getValue());
				statement.setObject(i++, entity.getUserID());
				return statement;
			}
		}, holder);
		if (val < 0) {
			return -1;
		}
		// 更新标识
		if (isExistIdentity(entity)) {
			if (!updateIdentity(entity)) {
				return -1;
			}
		} else {
			if (!addIdentity(entity)) {
				return -1;
			}
		}
		// 更新凭证
		if (isExistVoucher(entity)) {
			if (!updateVoucher(entity)) {
				return -1;
			}
		} else {
			if (!addVoucher(entity)) {
				return -1;
			}
		}
		if (entity.getInfos() != null && entity.getInfos().size() > 0) {
			// 更新信息 先无效再添加
			for (FrontUserInfoEntity info : entity.getInfos()) {
				if (isExistInfo(info)) {
					if (!updateInfo(info)) {
						return -1;
					}
				} else {
					if (!addInfo(info)) {
						return -1;
					}
				}
			}
		}
		return val;

	}

	/**
	 * 是否存在该标识
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean isExistIdentity(FrontUserEntity entity) throws DaoAppException {
		if (Str.isNullOrEmpty(entity.getUserID())) {
			return false;
		}
		StringBuilder SQL = new StringBuilder(
				"SELECT DISTINCT identify_id FROM  user_identify_user WHERE user_id = ? AND identify_type = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getUserID());
		params.add(0);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		if (sqlRowSet.next()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否存在该信息
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean isExistInfo(FrontUserInfoEntity entity) throws DaoAppException {
		if (Str.isNullOrEmpty(entity.getUserID())) {
			return false;
		}
		StringBuilder SQL = new StringBuilder(
				"SELECT DISTINCT info_id FROM  user_info_user WHERE user_id = ? AND info_type = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getUserID());
		params.add(entity.getInfoType());
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		if (sqlRowSet.next()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否存在该凭证
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean isExistVoucher(FrontUserEntity entity) throws DaoAppException {
		if (Str.isNullOrEmpty(entity.getUserID())) {
			return false;
		}
		StringBuilder SQL = new StringBuilder(
				"SELECT DISTINCT voucher_id FROM  user_voucher_user WHERE user_id = ? AND voucher_type = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getUserID());
		params.add(VoucherType.PASSWORD.getValue());
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		if (sqlRowSet.next()) {
			return true;
		}
		return false;
	}

	/**
	 * 添加标识
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean addIdentity(FrontUserEntity entity) throws DaoAppException {
		if (Str.isNullOrEmpty(entity.getUserID())) {
			return false;
		}
		StringBuilder SQL = new StringBuilder(
				"INSERT INTO user_identify_user (user_id, identify,identify_type, identify_state) VALUES (?,?, ?, ?);");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getUserID());
		params.add(entity.getPhoneNo());
		params.add(IdentifyType.PHONENO.getValue());
		params.add(1);
		int val = this.execUpdate(SQL.toString(), params.toArray());
		if (val > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 更新标识(现版本只更新电话)
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean updateIdentity(FrontUserEntity entity) throws DaoAppException {
		if (Str.isNullOrEmpty(entity.getUserID())) {
			return false;
		}
		StringBuilder SQL = new StringBuilder(
				"UPDATE user_identify_user SET identify=?,identify_type=?,identify_state=? WHERE user_id=? AND identify_type = ?;");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getPhoneNo());
		params.add(IdentifyType.PHONENO.getValue());
		params.add(1);
		params.add(entity.getUserID());
		params.add(IdentifyType.PHONENO.getValue());
		int val = this.execUpdate(SQL.toString(), params.toArray());
		if (val > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 添加凭证(现版本只更新password)
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean addVoucher(FrontUserEntity entity) throws DaoAppException {
		if (Str.isNullOrEmpty(entity.getUserID())) {
			return false;
		}
		StringBuilder SQL = new StringBuilder(
				"INSERT INTO user_voucher_user (user_id, voucher,voucher_type, vouche_state) VALUES (?,?, ?, ?);");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getUserID());
		params.add(entity.getLoginPwd());
		params.add(VoucherType.PASSWORD.getValue());
		params.add(1);
		int val = this.execUpdate(SQL.toString(), params.toArray());
		if (val > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 更新凭证
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean updateVoucher(FrontUserEntity entity) throws DaoAppException {
		if (Str.isNullOrEmpty(entity.getUserID())) {
			return false;
		}
		StringBuilder SQL = new StringBuilder(
				"UPDATE user_voucher_user SET voucher=?,voucher_type=?,vouche_state=? WHERE user_id=? AND voucher_type = ?;");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getLoginPwd());
		params.add(VoucherType.PASSWORD.getValue());
		params.add(1);
		params.add(entity.getUserID());
		params.add(VoucherType.PASSWORD.getValue());
		int val = this.execUpdate(SQL.toString(), params.toArray());
		if (val > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 添加信息
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean addInfo(FrontUserInfoEntity entity) throws DaoAppException {
		if (Str.isNullOrEmpty(entity.getUserID())) {
			return false;
		}
		StringBuilder SQL = new StringBuilder(
				"INSERT INTO user_info_user (user_id, info,info_type, info_state) VALUES (?,?, ?, ?);");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getUserID());
		params.add(entity.getInfo());
		params.add(entity.getInfoType());
		params.add(1);
		int val = this.execUpdate(SQL.toString(), params.toArray());
		if (val > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 更新信息
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private boolean updateInfo(FrontUserInfoEntity frontUserInfoEntity) throws DaoAppException {
		if (Str.isNullOrEmpty(frontUserInfoEntity.getUserID())) {
			return false;
		}
		StringBuilder SQL = new StringBuilder(
				"UPDATE user_info_user SET info=?,info_state=? WHERE user_id=? AND info_type = ?;");
		List<Object> params = new ArrayList<Object>();
		params.add(frontUserInfoEntity.getInfo());
		params.add(1);
		params.add(frontUserInfoEntity.getUserID());
		params.add(frontUserInfoEntity.getInfoType());
		int val = this.execUpdate(SQL.toString(), params.toArray());
		if (val > 0) {
			return true;
		}
		return false;
	}

	private int oldUpdateUser(final FrontUserEntity entity) {
		final String sqlScript = "UPDATE user_front_user SET user_name=?,user_type=?,user_phone=?,user_email=?,login_pwd=?,status=? WHERE user_id=?;";
		JdbcTemplate template = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		val = template.update(sqlScript, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setString(1, entity.getUserName());
				ps.setString(2, String.format("%s", entity.getUserType().getValue()));
				ps.setString(3, entity.getPhoneNo());
				ps.setString(4, entity.getEmail());
				ps.setString(5, entity.getLoginPwd());
				ps.setString(6, String.format("%s", entity.getStatus().getValue()));
				ps.setString(7, entity.getUserID());
			}
		});
		return val;
	}

	// @Override
	public boolean isExists(Entity entity) throws DaoAppException {
		FrontUserEntity frontUserEntity = null;
		try {
			frontUserEntity = (FrontUserEntity) entity;
			if (!this.isExists(frontUserEntity))
				return false;
			return true;
		} catch (DaoAppException e) {
			throw e;
		}
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");

		if (entity instanceof FrontUserEntity) {
			return queryData((FrontUserEntity) entity);
		} else if (entity instanceof QueryByPhoneNoConditionEntity) {
			return queryData((QueryByPhoneNoConditionEntity) entity);
		} else if (entity instanceof QueryByUserNameNoConditionEntity) {
			return queryData((QueryByUserNameNoConditionEntity) entity);
		} else if (entity.getClass() == IdEntity.class) {
			return queryById((IdEntity<String>) entity);
		} else if (entity instanceof FrontUserInfoEntity) {
			return queryByInfo((FrontUserInfoEntity) entity);
		} else if (entity instanceof SearchFrontUserInfoDto) {
			return queryByInfo((SearchFrontUserInfoDto) entity);
		} else if (entity instanceof QueryFrontUserByCompany) {
			return queryData((QueryFrontUserByCompany) entity);
		}
		return null;
	}

	private Entity queryByInfo(FrontUserInfoEntity info) {
		final String sqlScript = "SELECT user_name,user_phone from user_front_user where  user_id = (SELECT user_id FROM user_info_user WHERE info_type=? and info =? limit 1);";
		JdbcTemplate template = null;

		final FrontUserInfoEntity frontUserInfoEntity = info;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setObject(i++, frontUserInfoEntity.getInfoType());
				ps.setObject(i++, frontUserInfoEntity.getInfo());
			}
		}, new ResultSetExtractor<FrontUserEntity>() {
			public FrontUserEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				FrontUserEntity registFrontUserEntity = null;
				if (rs.next()) {
					registFrontUserEntity = new FrontUserEntity();
					String name = rs.getString("user_name");
					if (Str.isNullOrEmpty(name)) {
						name = rs.getString("user_phone");
					}
					registFrontUserEntity.setUserName(name);
				}
				return registFrontUserEntity;
			}
		});
	}

	private Entity queryByInfo(SearchFrontUserInfoDto info) {
		final String sqlScript = "SELECT user_id,info_type,info FROM user_info_user WHERE info_type=? and user_id =? and info_state = 1 limit 1;";
		JdbcTemplate template = null;

		final SearchFrontUserInfoDto frontUserInfoEntity = info;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setObject(i++, frontUserInfoEntity.getInfoType());
				ps.setObject(i++, frontUserInfoEntity.getUserID());
			}
		}, new ResultSetExtractor<FrontUserInfoEntity>() {
			public FrontUserInfoEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				FrontUserInfoEntity registFrontUserEntity = null;
				if (rs.next()) {
					registFrontUserEntity = new FrontUserInfoEntity();
					registFrontUserEntity.setInfo(rs.getString("info"));
					registFrontUserEntity.setUserID(rs.getString("user_id"));
					registFrontUserEntity.setInfoType(rs.getInt("info_type"));
				}
				return registFrontUserEntity;
			}
		});
	}

	// 根据电话号码查询用户
	private Entity queryData(FrontUserEntity entity) throws DaoAppException {
		final String sqlScript = "SELECT * FROM user_front_user WHERE user_phone=?;";
		JdbcTemplate template = null;

		final FrontUserEntity registFrontUserEntity = entity;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, registFrontUserEntity.getPhoneNo());
			}
		}, new ResultSetExtractor<FrontUserEntity>() {
			public FrontUserEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				FrontUserEntity registFrontUserEntity = null;
				if (rs.next()) {
					registFrontUserEntity = new FrontUserEntity();
					registFrontUserEntity.setUserID(rs.getString("user_id"));
					registFrontUserEntity.setUserName(rs.getString("user_name"));
					registFrontUserEntity.setUserType(UserType.getUserType(rs.getInt("user_type")));
					registFrontUserEntity.setPhoneNo(rs.getString("user_phone"));
					registFrontUserEntity.setEmail(rs.getString("user_email"));
					registFrontUserEntity.setCreatetime(rs.getDate("createtime"));
					registFrontUserEntity.setLoginPwd(rs.getString("login_pwd"));
					registFrontUserEntity.setStatus(UserStatus.getUserStatus(rs.getInt("status")));
				}
				return registFrontUserEntity;
			}
		});
	}

	// 根据电话号码查询用户
	private Entity queryData(QueryByUserNameNoConditionEntity entity) throws DaoAppException {
		final String sqlScript = "SELECT * FROM user_back_user WHERE user_name=?;";
		JdbcTemplate template = null;

		final QueryByUserNameNoConditionEntity queryByUserNameNoConditionEntity = entity;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, queryByUserNameNoConditionEntity.getUserName());
			}
		}, new ResultSetExtractor<BackUserEntity>() {
			public BackUserEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				BackUserEntity backUserEntity = null;
				if (rs.next()) {
					backUserEntity = new BackUserEntity();
					backUserEntity.setUserID(rs.getString("user_id"));
					backUserEntity.setUserName(rs.getString("user_name"));
					backUserEntity.setCreatetime(rs.getDate("createtime"));
					backUserEntity.setLogin_pwd(rs.getString("login_pwd"));
					backUserEntity.setStatus(UserStatus.getUserStatus(rs.getInt("status")));
				}
				return backUserEntity;
			}
		});
	}

	// 根据电话号码查询用户
	private Entity queryData(final QueryByPhoneNoConditionEntity entity) throws DaoAppException {
		final String sqlScript = "SELECT * FROM user_front_user WHERE user_phone=?;";
		JdbcTemplate template = null;

		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, entity.getPhoneNo());
			}
		}, new ResultSetExtractor<FrontUserEntity>() {
			public FrontUserEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				FrontUserEntity frontUserEntity = null;
				if (rs.next()) {
					frontUserEntity = new FrontUserEntity();
					frontUserEntity.setUserID(rs.getString("user_id"));
					frontUserEntity.setUserName(rs.getString("user_name"));
					frontUserEntity.setUserType(UserType.getUserType(rs.getInt("user_type")));
					frontUserEntity.setPhoneNo(rs.getString("user_phone"));
					frontUserEntity.setEmail(rs.getString("user_email"));
					frontUserEntity.setCreatetime(rs.getDate("createtime"));
					frontUserEntity.setLoginPwd(rs.getString("login_pwd"));
					frontUserEntity.setStatus(UserStatus.getUserStatus(rs.getInt("status")));
				}
				return frontUserEntity;
			}
		});
	}

	// 根据电话号码查询用户
	private Entity queryData(final QueryFrontUserByCompany entity) throws DaoAppException {
		final String sqlScript = "select um.user_id,um.user_type,um.state,ui.identify,ui.identify_code,um.createtime,uv.voucher from user_main_user um LEFT JOIN user_identify_user ui on ui.user_id = um.user_id LEFT JOIN user_voucher_user uv on uv.user_id = um.user_id where ui.identify = ? and ui.identify_code=?";
		JdbcTemplate template = null;

		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setString(i++, entity.getCompanyId());
				ps.setString(i++, entity.getCompanyCode());
			}
		}, new ResultSetExtractor<FrontUserEntity>() {
			public FrontUserEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				FrontUserEntity frontUserEntity = null;
				if (rs.next()) {
					frontUserEntity = new FrontUserEntity();
					frontUserEntity.setUserID(rs.getString("user_id"));
					frontUserEntity.setUserType(UserType.getUserType(rs.getInt("user_type")));
					frontUserEntity.setCreatetime(rs.getDate("createtime"));
					frontUserEntity.setLoginPwd(rs.getString("voucher"));
					frontUserEntity.setCompanyCode(rs.getString("identify_code"));
					frontUserEntity.setStatus(UserStatus.getUserStatus(rs.getInt("status")));
				}
				return frontUserEntity;
			}
		});
	}

	// 根据用户id查询用户
	private Entity queryById(final IdEntity<String> entity) throws DaoAppException {

		final String sqlScript = "SELECT * FROM user_front_user WHERE user_id=?;";
		final String sqlScript2 = "SELECT * FROM user_info_user WHERE user_id=?;";
		JdbcTemplate template = null;
		template = this.getJdbcTemplate();
		FrontUserEntity result = template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, entity.getId());
			}
		}, new ResultSetExtractor<FrontUserEntity>() {
			public FrontUserEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				FrontUserEntity frontUserEntity = null;
				if (rs.next()) {
					frontUserEntity = new FrontUserEntity();
					frontUserEntity.setUserID(rs.getString("user_id"));
					frontUserEntity.setUserName(rs.getString("user_name"));
					frontUserEntity.setUserType(UserType.getUserType(rs.getInt("user_type")));
					frontUserEntity.setPhoneNo(rs.getString("user_phone"));
					frontUserEntity.setEmail(rs.getString("user_email"));
					frontUserEntity.setCreatetime(rs.getTimestamp("createtime"));
					frontUserEntity.setLoginPwd(rs.getString("login_pwd"));
					frontUserEntity.setStatus(UserStatus.getUserStatus(rs.getInt("status")));
				}
				return frontUserEntity;
			}
		});

		if (result != null) {
			List<Object> param = new ArrayList<Object>();
			List<FrontUserInfoEntity> infos = new ArrayList<FrontUserInfoEntity>();
			param.add(entity.getId());
			SqlRowSet sqlRowSet = this.execQueryForRowSet(sqlScript2, param.toArray());
			while (sqlRowSet.next()) {
				infos.add(bulidFrontUserInfoEntity(sqlRowSet));
			}
			result.setInfos(infos);
		}
		return result;
	}

	private FrontUserInfoEntity bulidFrontUserInfoEntity(SqlRowSet sqlRowSet) {
		FrontUserInfoEntity frontUserInfoEntity = new FrontUserInfoEntity();
		frontUserInfoEntity.setInfo(sqlRowSet.getString("info"));
		frontUserInfoEntity.setInfoState(sqlRowSet.getInt("info_state"));
		frontUserInfoEntity.setInfoType(sqlRowSet.getInt("info_type"));
		frontUserInfoEntity.setUserID(sqlRowSet.getString("user_id"));
		return frontUserInfoEntity;
	}
}
