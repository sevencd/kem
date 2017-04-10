package cn.ilanhai.kem.dao.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.auth.QueryUserBound;
import cn.ilanhai.kem.domain.auth.UserBoundEntity;


public class UserBoundDao_back extends BaseDao {
	public UserBoundDao_back() {
		super();
	}

	private boolean isExists(final UserBoundEntity frontUserEntity)
			throws DaoAppException {
		final String sqlScript = "SELECT id FROM user_front_user_bound WHERE id=?";
		JdbcTemplate template = null;
		Integer val = -1;
		if (frontUserEntity == null)
			throw new DaoAppException("");

		template = this.getJdbcTemplate();
		val = template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, frontUserEntity.getId());
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
	public int save(Entity enity) throws DaoAppException {
		UserBoundEntity userBoundEntity = null;
		try {
			userBoundEntity = (UserBoundEntity) enity;
			if (!this.isExists(userBoundEntity))
				return this.add(userBoundEntity);
			return this.update(userBoundEntity);
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private int add(final UserBoundEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO user_front_user_bound(user_id,add_time,update_time,auth_data,type,tag,at,at_expired_time,at_data,at_expiry_date,at_update_time)VALUES(?,?,?,?,?,?,?,?,?,?,?);";
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		holder = new GeneratedKeyHolder();
		val = template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				statement.setString(1, entity.getUserId());
				statement.setTimestamp(2, new Timestamp(entity.getAddTime()
						.getTime()));
				statement.setTimestamp(3, new Timestamp(entity.getUpdateTime()
						.getTime()));
				statement.setString(4, entity.getAuthData());
				statement.setString(5, entity.getType());
				statement.setString(6, entity.getTag());
				statement.setString(7, entity.getAt());
				statement.setInt(8, entity.getAtExpiredTime());
				statement.setString(9, entity.getAtData());
				statement.setInt(10, entity.getAtExpiryDate());
				statement.setTimestamp(11, new Timestamp(entity
						.getAtUpdateTime().getTime()));
				System.out.println(statement.toString());
				return statement;
			}
		}, holder);
		if (val > 0)
			entity.setId(holder.getKey().intValue());
		return val;
	}

	private int update(final UserBoundEntity entity) throws DaoAppException {

		final String sqlScript = "UPDATE user_front_user_bound SET user_id=?,update_time=?,auth_data=?,type=?,tag=?,at=?,at_expired_time=?,at_data=?,at_expiry_date=?,at_update_time=? WHERE id=?;";
		JdbcTemplate template = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		val = template.update(sqlScript, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setString(1, entity.getUserId());
				ps.setTimestamp(2, new Timestamp(entity.getUpdateTime()
						.getTime()));
				ps.setString(3, entity.getAuthData());
				ps.setString(4, entity.getType());
				ps.setString(5, entity.getTag());
				ps.setInt(6, entity.getId());
				ps.setString(7, entity.getAt());
				ps.setInt(8, entity.getAtExpiredTime());
				ps.setString(9, entity.getAtData());
				ps.setInt(10, entity.getAtExpiryDate());
				ps.setTimestamp(11, new Timestamp(entity.getAtUpdateTime()
						.getTime()));
			}
		});
		return val;
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof QueryUserBound) {
			return queryData((QueryUserBound) entity);
		}
		return null;
	}

	// 根据电话号码查询用户
	private Entity queryData(final QueryUserBound entity)
			throws DaoAppException {
		final String sqlScript = "SELECT * FROM user_front_user_bound WHERE type=? and tag=?;";
		JdbcTemplate template = null;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, entity.getType());
				ps.setString(2, entity.getTag());
			}
		}, new ResultSetExtractor<UserBoundEntity>() {
			public UserBoundEntity extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				UserBoundEntity boundEntity = null;
				if (rs.next()) {
					boundEntity = new UserBoundEntity();
					boundEntity.setId(rs.getInt("id"));
					boundEntity.setAddTime(rs.getDate("add_time"));
					boundEntity.setUpdateTime(rs.getDate("update_time"));
					boundEntity.setAuthData(rs.getString("auth_data"));
					boundEntity.setType(rs.getString("type"));
					boundEntity.setTag(rs.getString("tag"));
					boundEntity.setUserId(rs.getString("user_id"));
					boundEntity.setAt(rs.getString("at"));
					boundEntity.setAtExpiredTime(rs.getInt("at_expired_time"));
					boundEntity.setAtData(rs.getString("at_data"));
					boundEntity.setAtExpiryDate(rs.getInt("at_expiry_date"));
					boundEntity.setAtUpdateTime(rs.getDate("at_update_time"));

				}
				return boundEntity;
			}
		});
	}

}
