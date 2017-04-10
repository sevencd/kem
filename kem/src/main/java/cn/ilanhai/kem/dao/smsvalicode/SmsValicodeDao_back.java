package cn.ilanhai.kem.dao.smsvalicode;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.dao.AbstractJdbcDao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.enums.ValidateStatus;
import cn.ilanhai.kem.domain.imgvalicode.ImgVailcodeEntity;
import cn.ilanhai.kem.domain.smsvalicode.SmsValidateCodeEntity;

@Component("smsvalicodeDao")
public class SmsValicodeDao_back extends BaseDao {
	public SmsValicodeDao_back() {
		super();

	}

	@Override
	public int save(Entity enity) throws DaoAppException {
		SmsValidateCodeEntity smsValidateCodeEntity = null;
		try {
			smsValidateCodeEntity = (SmsValidateCodeEntity) enity;
			if (!this.isExists(smsValidateCodeEntity.getId()))
				return this.add(smsValidateCodeEntity);
			return this.update(smsValidateCodeEntity);
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private int add(final SmsValidateCodeEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO sys_identify_code(identity_code,user_id,user_type,module_code,work_id,createtime,deadline,status)VALUES(?,?,?,?,?,?,?,?);";
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		UserType userType;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		holder = new GeneratedKeyHolder();
		val = template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				statement.setString(1, entity.getIdentityCode());
				if (entity.getUserId() == null) {
					statement.setNull(2, Types.INTEGER);
				} else {
					statement.setString(2, entity.getUserId());
				}
				statement.setString(3, entity.getUserType() == null ? null
						: String.format("%s", entity.getUserType().getValue()));
				statement.setString(4, entity.getModuleCode());
				statement.setString(5, entity.getWorkId());
				statement.setTimestamp(6, new Timestamp(entity.getCreatetime()
						.getTime()));
				statement.setTimestamp(7, new Timestamp(entity.getDeadline()
						.getTime()));
				statement.setString(8, entity.getStatus() == null ? null
						: String.format("%s", entity.getStatus().getValue()));
				System.out.println(statement.toString());
				return statement;
			}
		}, holder);
		if (val > 0)
			entity.setId(holder.getKey().intValue());
		return val;
	}

	private int update(final SmsValidateCodeEntity entity)
			throws DaoAppException {
		final String sqlScript = "UPDATE sys_identify_code SET identity_code=?,user_id=?,user_type=?,module_code=?,work_id=?,status=? WHERE id=?;";
		JdbcTemplate template = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		val = template.update(sqlScript, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setString(1, entity.getIdentityCode());
				if (entity.getUserId() == null) {
					ps.setNull(2, Types.INTEGER);
				} else {
					ps.setString(2, entity.getUserId());
				}
				ps.setString(
						3,
						entity.getUserType() == null ? null : String.format(
								"%s", entity.getUserType().getValue()));
				ps.setString(4, entity.getModuleCode());
				ps.setString(5, entity.getWorkId());
				ps.setString(
						6,
						entity.getStatus() == null ? null : String.format("%s",
								entity.getStatus().getValue()));
				if (entity.getId() == null) {
					ps.setNull(7, Types.INTEGER);
				} else {
					ps.setInt(7, entity.getId());
				}
				System.out.println(ps.toString());
			}
		});
		return val;
	}

	private boolean isExists(final Integer id) throws DaoAppException {
		final String sqlScript = "SELECT id FROM sys_identify_code WHERE id=?";
		JdbcTemplate template = null;
		Integer val = -1;// 查询记录数
		// 如果无ID，则
		if (id == null) {
			return false;
		}

		template = this.getJdbcTemplate();
		val = template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
				System.out.println(ps.toString());
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next())
					return new Integer(rs.getInt("id"));
				return new Integer(-1);
			}
		});
		return val > 0;
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		IdDto idDto = null;
		try {
			if (entity.getClass() == IdDto.class) {
				idDto = (IdDto) entity;
				return this.queryById(idDto);
			}
			return null;
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private Entity queryById(final IdDto entity) throws DaoAppException {
		final String sqlScript = "SELECT id,identity_code,user_id,user_type,module_code,work_id,createtime,deadline,status FROM sys_identify_code WHERE id=?;";
		JdbcTemplate template = null;
		if (entity == null)
			throw new DaoAppException("请指定查询参数");
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, entity.getId());
			}
		}, new ResultSetExtractor<SmsValidateCodeEntity>() {
			public SmsValidateCodeEntity extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				SmsValidateCodeEntity smsValidateCodeEntity = null;
				if (rs.next()) {
					smsValidateCodeEntity = new SmsValidateCodeEntity();
					smsValidateCodeEntity.setId(rs.getInt("id"));
					smsValidateCodeEntity.setCreatetime(rs
							.getTimestamp("createtime"));
					smsValidateCodeEntity.setDeadline(rs
							.getTimestamp("deadline"));
					smsValidateCodeEntity.setIdentityCode(rs
							.getString("identity_code"));
					smsValidateCodeEntity.setModuleCode(rs
							.getString("module_code"));
					smsValidateCodeEntity.setWorkId(rs.getString("work_id"));
					smsValidateCodeEntity.setUserId(rs.getString("user_id"));
					smsValidateCodeEntity.setUserType(UserType.valueOf(rs
							.getInt("user_type")));
					smsValidateCodeEntity.setStatus(ValidateStatus.valueOf(rs
							.getInt("status")));
				}
				return smsValidateCodeEntity;
			}
		});
	}
}
