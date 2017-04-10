package cn.ilanhai.kem.dao.plugin;

import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;

import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdEntity;

import cn.ilanhai.kem.domain.plugin.PluginEntity;
import cn.ilanhai.kem.domain.plugin.QueryPlugin;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ActivePluginEntity;

@Component("pluginDao_Old")
public class PluginDao_Old extends MybatisBaseDao {
	public PluginDao_Old() throws DaoAppException {
		super();

	}

	@SuppressWarnings("unchecked")
	@Override
	public int save(Entity enity) throws DaoAppException {
		if (enity instanceof ActivePluginEntity) {
			if (!this.isExists((ActivePluginEntity) enity))
				return this.add((ActivePluginEntity) enity);
			return this.update((ActivePluginEntity) enity);
		} else if (enity instanceof PluginEntity) {
			if (!this.isExists((PluginEntity) enity))
				return this.add((PluginEntity) enity);
			return this.update((PluginEntity) enity);
		} else if (enity instanceof IdEntity) {
			disablePlugin(((IdEntity<String>) enity).getId());
		}
		return -1;
	}

	private boolean isExists(ActivePluginEntity entity) throws DaoAppException {
		// String sql =
		// "SELECT upi.plugin_id FROM user_plugin_info as upi left join user_plugin_activeplugin as upa on upi.plugin_id = upa.plugin_id  WHERE upi.relation_id=? AND upi.relation_type=? AND upi.plugin_type=2 AND upa.type=?;";
		// Integer val = -1;
		// List<Object> paramter = null;
		// paramter = new ArrayList<Object>();
		// paramter.add(entity.getRelationId());
		// paramter.add(entity.getRelationType().getValue());
		// paramter.add(entity.getActivePluginType().getValue());
		// val = this.execQueryForObject(sql, paramter.toArray(),
		// Integer.class);
		// return val == null ? false : val > 0;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("Plugin.activePluginIsExists", entity);
			return val == null ? false : val > 0;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	private boolean isExists(PluginEntity entity) throws DaoAppException {
		// String sql =
		// "SELECT plugin_id FROM user_plugin_info WHERE relation_id=? AND relation_type=? AND plugin_type=1;";
		// Integer val = -1;
		// List<Object> paramter = null;
		// paramter = new ArrayList<Object>();
		// paramter.add(entity.getRelationId());
		// paramter.add(entity.getRelationType().getValue());
		// val = this.execQueryForObject(sql, paramter.toArray(),
		// Integer.class);
		// return val == null ? false : val > 0;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("Plugin.pluginIsExists", entity);
			return val == null ? false : val > 0;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private void disablePlugin(String relationId) throws DaoAppException {
		// final String sqlScript =
		// "UPDATE user_plugin_info SET is_used=0 WHERE relation_id=?;";
		// List<Object> paramter = null;
		// paramter = new ArrayList<Object>();
		// paramter.add(relationId);
		// this.execUpdate(sqlScript, paramter.toArray());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			sqlSession.update("Plugin.disablePlugin", relationId);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int update(final PluginEntity entity) throws DaoAppException {
		// 修改插件表
		// final String sqlScript =
		// "UPDATE user_plugin_info SET plugin_type=?,create_time=?,user_id=?,relation_id=?,relation_type=?,is_used=? WHERE plugin_id=?;";
		// List<Object> paramter = null;
		// paramter = new ArrayList<Object>();
		// paramter.add(entity.getPluginType().getValue());
		// paramter.add(entity.getCreatetime());
		// paramter.add(entity.getUserId());
		// paramter.add(entity.getRelationId());
		// paramter.add(entity.getRelationType().getValue());
		// paramter.add(entity.isUsed());
		// paramter.add(entity.getPluginId());
		// return this.execUpdate(sqlScript, paramter.toArray());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("Plugin.update", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	private int add(final PluginEntity entity) throws DaoAppException {
		// final String sql =
		// "INSERT INTO user_plugin_info (plugin_type,create_time,user_id,relation_id,relation_type,is_used)VALUES(?,?,?,?,?,?);";
		// int val = -1;
		// KeyHolder keyHolder = null;
		// keyHolder = new GeneratedKeyHolder();
		// PreparedStatementCreator preparedStatementCreator = null;
		// preparedStatementCreator = new PreparedStatementCreator() {
		// @Override
		// public PreparedStatement createPreparedStatement(Connection con)
		// throws SQLException {
		// PreparedStatement preparedStatement = null;
		// preparedStatement = con.prepareStatement(sql);
		// preparedStatement.setInt(1, entity.getPluginType().getValue());
		// preparedStatement.setTimestamp(2, new Timestamp(entity
		// .getCreatetime().getTime()));
		//
		// preparedStatement.setString(3, entity.getUserId());
		// preparedStatement.setString(4, entity.getRelationId());
		// preparedStatement
		// .setInt(5, entity.getRelationType().getValue());
		// preparedStatement.setBoolean(6, entity.isUsed());
		// return preparedStatement;
		// }
		// };
		// val = this.execUpdate(preparedStatementCreator, keyHolder);
		// if (val > 0)
		// entity.setPluginId(keyHolder.getKey().intValue());
		// return val;
		SqlSession sqlSession = null;
		int val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.insert("Plugin.addPlugin", entity);
			if (val > 0)
				entity.setPluginId(val);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int add(final ActivePluginEntity entity) throws DaoAppException {
		// // 插入插件表
		// final String sqlScript =
		// "insert into user_plugin_info (plugin_type,create_time,user_id,relation_id,relation_type,is_used) values (?,?,?,?,?,?);";
		// JdbcTemplate template = null;
		// KeyHolder holder = null;
		// int val = -1;
		// if (entity == null)
		// return val;
		// template = this.getJdbcTemplate();
		// holder = new GeneratedKeyHolder();
		// val = template.update(new PreparedStatementCreator() {
		// public PreparedStatement createPreparedStatement(Connection con)
		// throws SQLException {
		// PreparedStatement statement = null;
		// statement = con.prepareStatement(sqlScript);
		// statement.setInt(1, entity.getPluginType().getValue());
		// statement.setTimestamp(2, new Timestamp(entity.getCreatetime()
		// .getTime()));
		// statement.setString(3, entity.getUserId());
		// statement.setString(4, entity.getRelationId());
		// statement.setInt(5, entity.getRelationType().getValue());
		// statement.setBoolean(6, entity.isUsed());
		// return statement;
		// }
		// }, holder);
		// if (val > 0)
		// entity.setPluginId(holder.getKey().intValue());
		// // 插入活动插件表
		// final String sqlScriptAcitve =
		// "insert into user_plugin_activeplugin (plugin_id,type,drawtime,wintime,intervaltime,intervaltime_type,prize_collect_info,prize_collect_required_info,outer_url,merchant_phone) values (?,?,?,?,?,?,?,?,?,?);";
		//
		// val = -1;
		// if (entity.getPluginId() == null)
		// return val;
		// template = this.getJdbcTemplate();
		// holder = new GeneratedKeyHolder();
		// val = template.update(new PreparedStatementCreator() {
		// public PreparedStatement createPreparedStatement(Connection con)
		// throws SQLException {
		// PreparedStatement statement = null;
		// statement = con.prepareStatement(sqlScriptAcitve);
		// statement.setInt(1, entity.getPluginId());
		// if (entity.getActivePluginType() == null) {
		// statement.setObject(2, null);
		// } else {
		// statement
		// .setInt(2, entity.getActivePluginType().getValue());
		// }
		// if (entity.getDrawTime() == null) {
		// statement.setObject(3, null);
		// } else {
		// statement.setInt(3, entity.getDrawTime());
		// }
		//
		// if (entity.getWinTime() == null) {
		// statement.setObject(4, null);
		// } else {
		// statement.setInt(4, entity.getWinTime());
		// }
		//
		// if (entity.getIntervalTime() == null) {
		// statement.setObject(5, null);
		// } else {
		// statement.setInt(5, entity.getIntervalTime());
		// }
		//
		// if (entity.getIntervalTimeType() == null) {
		// statement.setObject(6, null);
		// } else {
		// statement.setInt(6, entity.getIntervalTimeType());
		// }
		//
		// statement.setObject(7,
		// FastJson.bean2Json(entity.getPrizeCollectInfo()));
		// statement.setObject(8, FastJson.bean2Json(entity
		// .getPrizeCollectRequiredInfo()));
		// statement.setObject(9, entity.getOuterUrl());
		// statement.setObject(10, entity.getMerchantPhone());
		//
		// return statement;
		// }
		// }, holder);
		// // 插入奖项设置
		// // this.insertPrizeSettings(entity);
		// return val;
		SqlSession sqlSession = null;
		int val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.insert("Plugin.addActivePlugin", entity);
			if (val <= 0)
				return -1;
			entity.setPluginId(val);
			val = sqlSession.insert("Plugin.addActivePlugin1", entity);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof QueryPlugin) {
			return query((QueryPlugin) entity);
		} else if (entity instanceof ActivePluginEntity) {
			return query((ActivePluginEntity) entity);
		}
		return null;

	}

	private Entity query(QueryPlugin entity) throws DaoAppException {
		// String sql =
		// "SELECT * FROM user_plugin_info  WHERE relation_id=? AND plugin_type=?";
		// List<Object> para = null;
		// SqlRowSet rs = null;
		// para = new ArrayList<Object>();
		// para.add(entity.getRelationId());
		// para.add(entity.getPluginType().getValue());
		// if (entity.getIsUsed() != null) {
		// sql += " AND is_used=?";
		// para.add(entity.getIsUsed());
		// }
		// rs = this.execQueryForRowSet(sql, para.toArray());
		// if (!rs.next())
		// return null;
		// return this.rsToEntity(rs);
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("Plugin.pluginQuery", entity);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private Entity query(ActivePluginEntity entity) throws DaoAppException {
		// String sql =
		// "SELECT * FROM user_plugin_info as upi left join user_plugin_activeplugin as upa on upi.plugin_id = upa.plugin_id  WHERE upi.relation_id=? AND upi.plugin_type=2 AND upa.type=?";
		// List<Object> para = null;
		// SqlRowSet rs = null;
		// para = new ArrayList<Object>();
		// para.add(entity.getRelationId());
		// para.add(entity.getActivePluginType().getValue());
		// if (entity.isUsed() != null) {
		// sql += " AND is_used=?";
		// para.add(entity.isUsed());
		// }
		// rs = this.execQueryForRowSet(sql, para.toArray());
		// if (!rs.next())
		// return null;
		// return this.rsToActivePluginEntity(rs);
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("Plugin.activePluginQuery", entity);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	// private Entity rsToEntity(SqlRowSet rs) {
	// PluginEntity entity = null;
	// if (rs == null)
	// return null;
	// entity = new PluginEntity();
	// entity.setCreatetime(rs.getTimestamp("create_time"));
	// entity.setPluginId(rs.getInt("plugin_id"));
	// entity.setPluginType(PluginType.getEnum(rs.getInt("plugin_type")));
	// entity.setRelationId(rs.getString("relation_id"));
	// entity.setRelationType(ManuscriptType.getEnum(rs
	// .getInt("relation_type")));
	// entity.setUsed(rs.getBoolean("is_used"));
	// entity.setUserId(rs.getString("user_id"));
	// return entity;
	//
	// }

	// private Entity rsToActivePluginEntity(SqlRowSet rs) {
	// ActivePluginEntity entity = null;
	// if (rs == null)
	// return null;
	// entity = new ActivePluginEntity();
	// entity.setCreatetime(rs.getTimestamp("create_time"));
	// entity.setPluginId(rs.getInt("plugin_id"));
	// entity.setPluginType(PluginType.getEnum(rs.getInt("plugin_type")));
	// entity.setRelationId(rs.getString("relation_id"));
	// entity.setRelationType(ManuscriptType.getEnum(rs
	// .getInt("relation_type")));
	// entity.setUsed(rs.getBoolean("is_used"));
	// entity.setUserId(rs.getString("user_id"));
	// entity.setActivePluginType(ActivePluginType.getEnum(rs.getInt("type")));
	// return entity;
	//
	// }
}
