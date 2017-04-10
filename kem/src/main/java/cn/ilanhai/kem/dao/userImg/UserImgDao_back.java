package cn.ilanhai.kem.dao.userImg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.UserImgOrderType;
import cn.ilanhai.kem.domain.enums.UserResourceType;
import cn.ilanhai.kem.domain.userImg.DeleteUserImgDataEntity;
import cn.ilanhai.kem.domain.userImg.SearchUserImgRequestEntity;
import cn.ilanhai.kem.domain.userImg.UserImgDataDto;
import cn.ilanhai.kem.domain.userImg.UserImgEntity;
import cn.ilanhai.kem.domain.userImg.UserImgUploadEntity;

@Component("userImgDao")
public class UserImgDao_back extends BaseDao {

	private Logger logger = Logger.getLogger(UserImgDao_back.class);

	public UserImgDao_back() {
		super();
	}

	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof SearchUserImgRequestEntity) {
			return queryUserImg((SearchUserImgRequestEntity) entity);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		CountDto count = new CountDto();
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof SearchUserImgRequestEntity) {
			count.setCount(queryUserImgForCount((SearchUserImgRequestEntity) entity));
			return count;
		}
		if (entity instanceof IdEntity) {
			return queryUserImgById((IdEntity<String>) entity);
		}
		return null;
	}

	private Entity queryUserImgById(IdEntity<String> entity) throws DaoAppException {
		String sql = "SELECT * FROM USER_RESOURCE_IMG WHERE img_id = '" + entity.getId() + "'";
		SqlRowSet sqlRowSet = this.execQueryForRowSet(sql);
		if (sqlRowSet.next()) {
			UserImgEntity userImgEntity = new UserImgEntity();
			userImgEntity.setImgId(sqlRowSet.getString("img_id"));
			userImgEntity.setUserId(sqlRowSet.getString("user_id"));
			return userImgEntity;
		}
		return null;
	}

	private Iterator<Entity> queryUserImg(SearchUserImgRequestEntity entity) {
		logger.info("进入查询:" + FastJson.bean2Json(entity));

		if (!Str.isNullOrEmpty(entity.getMaterialId())) {
			String SQL = "SELECT * FROM USER_MATERIAL_IMG UMI LEFT JOIN USER_RESOURCE_IMG URI ON UMI.img_id = URI.img_id WHERE UMI.material_id =?";
			if (!Str.isNullOrEmpty(entity.getImgName())) {
				SQL += " AND URI.IMG_NAME LIKE ?";
			}

			if (!Str.isNullOrEmpty(entity.getImgMd5())) {
				SQL += " AND URI.md5 = ?";
			}
			if (!Str.isNullOrEmpty(entity.getType())) {
				SQL += " AND UMI.img_type =  " + UserResourceType.getEnum(entity.getType()).getValue();
			}
			SQL += " AND URI.USER_ID in (?,'0') ORDER BY URI.CREATETIME "
					+ UserImgOrderType.valueOf(entity.getOrderType()) + " LIMIT ?,? ;";
			final String sqlScript = SQL;
			JdbcTemplate template = null;
			final SearchUserImgRequestEntity searchTemplateDto = entity;

			logger.info(sqlScript);

			template = this.getJdbcTemplate();
			return template.query(sqlScript, new PreparedStatementSetter() {
				public void setValues(PreparedStatement ps) throws SQLException {
					int i = 1;
					ps.setString(i++, searchTemplateDto.getMaterialId());
					if (!Str.isNullOrEmpty(searchTemplateDto.getImgName())) {
						ps.setString(i++, "%" + searchTemplateDto.getImgName() + "%");
					}
					if (!Str.isNullOrEmpty(searchTemplateDto.getImgMd5())) {
						ps.setString(i++, searchTemplateDto.getImgMd5());
					}
					ps.setString(i++, searchTemplateDto.getUserId());
					ps.setInt(i++, searchTemplateDto.getStartCount());
					ps.setInt(i++, searchTemplateDto.getPageSize());
				}
			}, new RowMapper<Entity>() {
				public UserImgDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserImgDataDto userImgDataDto = new UserImgDataDto();
					userImgDataDto.setImgId(rs.getString("img_id"));
					userImgDataDto.setImgPath(searchTemplateDto.getServiceName() + rs.getString("img_path"));
					userImgDataDto.setCreatetime(rs.getTimestamp("createtime"));
					return userImgDataDto;
				}
			}).iterator();
		} else {
			String SQL = "SELECT * FROM USER_RESOURCE_IMG WHERE 1=1 ";
			if (!Str.isNullOrEmpty(entity.getImgName())) {
				SQL += " AND IMG_NAME LIKE ?";
			}

			if (!Str.isNullOrEmpty(entity.getImgMd5())) {
				SQL += " AND md5 = ?";
			}
			if (!Str.isNullOrEmpty(entity.getType())) {
				SQL += " AND img_type =  " + UserResourceType.getEnum(entity.getType()).getValue();
			}

			SQL += " AND USER_ID= ? ORDER BY CREATETIME " + UserImgOrderType.valueOf(entity.getOrderType())
					+ " LIMIT ?,? ;";
			final String sqlScript = SQL;
			JdbcTemplate template = null;
			final SearchUserImgRequestEntity searchTemplateDto = entity;

			logger.info(sqlScript);

			template = this.getJdbcTemplate();
			return template.query(sqlScript, new PreparedStatementSetter() {
				public void setValues(PreparedStatement ps) throws SQLException {
					int i = 1;
					if (!Str.isNullOrEmpty(searchTemplateDto.getImgName())) {
						ps.setString(i++, "%" + searchTemplateDto.getImgName() + "%");
					}
					if (!Str.isNullOrEmpty(searchTemplateDto.getImgMd5())) {
						ps.setString(i++, searchTemplateDto.getImgMd5());
					}
					ps.setString(i++, searchTemplateDto.getUserId());
					ps.setInt(i++, searchTemplateDto.getStartCount());
					ps.setInt(i++, searchTemplateDto.getPageSize());
				}
			}, new RowMapper<Entity>() {
				public UserImgDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserImgDataDto userImgDataDto = new UserImgDataDto();
					userImgDataDto.setImgId(rs.getString("img_id"));
					String path = rs.getString("img_path");
					if (!path.contains("://")) {
						path = searchTemplateDto.getServiceName() + path;
					}
					userImgDataDto.setImgPath(path);
					userImgDataDto.setCreatetime(rs.getTimestamp("createtime"));
					return userImgDataDto;
				}
			}).iterator();
		}
	}

	private int queryUserImgForCount(SearchUserImgRequestEntity entity) throws DaoAppException {
		List<Object> args = new ArrayList<Object>();

		if (entity.getMaterialId() != null) {
			String SQL = "SELECT count(1) FROM USER_MATERIAL_IMG UMI LEFT JOIN USER_RESOURCE_IMG URI ON UMI.img_id = URI.img_id WHERE UMI.material_id =?";
			args.add(entity.getMaterialId());
			if (!Str.isNullOrEmpty(entity.getImgName())) {
				SQL += " AND URI.IMG_NAME LIKE ?";
				args.add(entity.getImgName());
			}
			if (!Str.isNullOrEmpty(entity.getType())) {
				SQL += " AND UMI.img_type =  " + UserResourceType.getEnum(entity.getType()).getValue();
			}
			SQL += " AND URI.USER_ID in (?,0) ORDER BY URI.CREATETIME "
					+ UserImgOrderType.valueOf(entity.getOrderType()) + ";";
			args.add(entity.getUserId());
			final String sqlScript = SQL;
			return this.execQueryForObject(sqlScript, args.toArray(), Integer.class);
		} else {
			String SQL = "SELECT count(1) FROM USER_RESOURCE_IMG WHERE 1=1 ";
			if (!Str.isNullOrEmpty(entity.getImgName())) {
				SQL += " AND IMG_NAME LIKE ?";
				args.add(entity.getImgName());
			}
			if (!Str.isNullOrEmpty(entity.getType())) {
				SQL += " AND img_type = " + UserResourceType.getEnum(entity.getType()).getValue();
			}
			SQL += " AND USER_ID= ? ORDER BY CREATETIME " + UserImgOrderType.valueOf(entity.getOrderType()) + " ;";
			args.add(entity.getUserId());
			final String sqlScript = SQL;
			return this.execQueryForObject(sqlScript, args.toArray(), Integer.class);
		}
	}

	@Override
	public int save(Entity enity) throws DaoAppException {
		UserImgUploadEntity imgEntity = null;
		try {
			imgEntity = (UserImgUploadEntity) enity;
			if (!this.isExists(imgEntity))
				return this.add(imgEntity);
			return this.update(imgEntity);
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private boolean isExists(final UserImgUploadEntity entity) throws DaoAppException {
		final String sqlScript = "SELECT img_id FROM user_resource_img WHERE img_id=?";
		JdbcTemplate template = null;
		String val = null;
		if (entity == null || Str.isNullOrEmpty(entity.getImgId()))
			return false;
		template = this.getJdbcTemplate();
		val = template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, entity.getImgId());
			}
		}, new ResultSetExtractor<String>() {
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next())
					return rs.getString("img_id");
				return null;
			}
		});
		return !Str.isNullOrEmpty(val);
	}

	private int add(final UserImgUploadEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO user_resource_img(img_id,img_name,img_path,img_type,createtime,user_id,md5)VALUES(?,?,?,?,?,?,?);";
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
				statement = con.prepareStatement(sqlScript);
				statement.setString(1, entity.getImgId());
				statement.setString(2, entity.getImgName());
				statement.setString(3, entity.getImgPath());
				statement.setString(4, entity.getType());
				statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				statement.setString(6, entity.getUserId());
				statement.setString(7, entity.getImgMd5());
				return statement;
			}
		}, holder);
		return val;
	}

	private int update(final UserImgUploadEntity entity) throws DaoAppException {
		final String sqlScript = "UPDATE user_resource_img SET img_name=?,img_path=?,img_type=? WHERE img_id=?;";
		JdbcTemplate template = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		val = template.update(sqlScript, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, entity.getImgName());
				ps.setString(2, entity.getImgPath());
				ps.setString(3, entity.getType());
				ps.setString(4, entity.getImgId());
			}
		});
		return val;
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		final String sqlScript = "DELETE FROM user_resource_img WHERE img_id=?;";
		JdbcTemplate template = null;
		int val = -1;
		if (entity == null)
			return val;
		final DeleteUserImgDataEntity deleteUserImgDataEntity = (DeleteUserImgDataEntity) entity;
		template = this.getJdbcTemplate();
		val = template.update(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, deleteUserImgDataEntity.getImgId());
			}
		});
		return val;
	}
}
