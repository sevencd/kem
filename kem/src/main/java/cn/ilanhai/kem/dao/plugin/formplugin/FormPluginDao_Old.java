package cn.ilanhai.kem.dao.plugin.formplugin;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;

import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.plugin.formplugin.FormPluginEntity;
import cn.ilanhai.kem.domain.plugin.formplugin.SearchFormPluginData;

@Component("formpluginDao_Old")
public class FormPluginDao_Old extends MybatisBaseDao {
	public FormPluginDao_Old() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity enity) throws DaoAppException {
		if (!this.isExists((FormPluginEntity) enity))
			return this.add((FormPluginEntity) enity);
		return -1;
	}

	private boolean isExists(FormPluginEntity entity) throws DaoAppException {
		// String sql = "SELECT id FROM plugins_form_data WHERE id=?;";
		// Integer val = -1;
		// List<Object> paramter = null;
		// paramter = new ArrayList<Object>();
		// paramter.add(entity.getId());
		// val = this.execQueryForObject(sql, paramter.toArray(),
		// Integer.class);
		// return val == null ? false : val > 0;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.isExists", entity);
			return val == null ? false : val > 0;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int add(final FormPluginEntity entity) throws DaoAppException {
		// final String sql =
		// "INSERT INTO plugins_form_data (plugin_id,add_time,update_time,name,phone,email)VALUES(?,?,?,?,?,?);";
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
		// preparedStatement.setInt(1, entity.getPluginId());
		// preparedStatement.setTimestamp(2, new Timestamp(entity
		// .getAddTime().getTime()));
		// preparedStatement.setTimestamp(3, new Timestamp(entity
		// .getUpdateTime().getTime()));
		// preparedStatement.setString(4, entity.getName());
		// preparedStatement.setString(5, entity.getPhone());
		// preparedStatement.setString(6, entity.getEmail());
		// return preparedStatement;
		// }
		// };
		// val = this.execUpdate(preparedStatementCreator, keyHolder);
		// if (val > 0)
		// entity.setId(keyHolder.getKey().intValue());
		// return val;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.add", entity);
			if (val <= 0)
				return val;
			entity.setId(val);
			return val;
			// return val == null ? false : val > 0;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof SearchFormPluginData)
			return query((SearchFormPluginData) entity).iterator();
		return null;
	}

	private int count(SearchFormPluginData entity) throws DaoAppException {
		// int val = -1;
		// StringBuilder sql = null;
		// sql = new StringBuilder();
		// List<Object> paramter = null;
		// paramter = new ArrayList<Object>();
		// sql.append("SELECT COUNT(*) FROM plugins_form_data WHERE plugin_id=?");
		// if (entity.getWord() != null && entity.getWord().length() > 0)
		// sql.append(" AND (name like ? OR phone like ? OR email like ?)");
		// sql.append(" ORDER BY id  ");
		// sql.append(entity.getOrder().toString());
		// paramter.add(entity.getPluginId());
		// if (entity.getWord() != null && entity.getWord().length() > 0) {
		// String str = "%" + entity.getWord() + "%";
		// paramter.add(str);
		// paramter.add(str);
		// paramter.add(str);
		// }
		// val = this.execQueryForObject(sql.toString(), paramter.toArray(),
		// Integer.class);
		// return val;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne(
					"FormPlugin.formPluginEntityCountQuery", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	public List<Entity> query(final SearchFormPluginData entity)
			throws DaoAppException {
		// int recordCount = -1;
		// StringBuilder sql = null;
		// List<Object> paramter = null;
		// SqlRowSet rs = null;
		// paramter = new ArrayList<Object>();
		// sql = new StringBuilder();
		// sql.append("SELECT * FROM plugins_form_data WHERE plugin_id=?");
		// if (entity.getWord() != null && entity.getWord().length() > 0)
		// sql.append(" AND (name like ? OR phone like ? OR email like ?)");
		// sql.append(" ORDER BY id  ");
		// sql.append(entity.getOrder().toString());
		// sql.append(" LIMIT ?,?;");
		// recordCount = this.count(entity);
		// entity.setRecordCount(recordCount);
		// paramter.add(entity.getPluginId());
		// if (entity.getWord() != null && entity.getWord().length() > 0) {
		// String str = "%" + entity.getWord() + "%";
		// paramter.add(str);
		// paramter.add(str);
		// paramter.add(str);
		// }
		// paramter.add(entity.getStartCount());
		// paramter.add(entity.getPageSize());
		// rs = this.execQueryForRowSet(sql.toString(), paramter.toArray());
		// return this.rs(rs);
		SqlSession sqlSession = null;
		int recordCount = -1;
		try {
			sqlSession = this.getSqlSession();
			recordCount = this.count(entity);
			entity.setRecordCount(recordCount);

			return sqlSession.selectList("FormPlugin.formPluginEntityQuery",
					entity);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}

	}

	// private Iterator<Entity> rs(SqlRowSet rs) {
	// List<Entity> ls = null;
	// FormPluginEntity formPluginEntity = null;
	// if (rs == null)
	// return null;
	// ls = new ArrayList<Entity>();
	// while (rs.next()) {
	// formPluginEntity = new FormPluginEntity();
	// formPluginEntity.setId(rs.getInt("id"));
	// formPluginEntity.setAddTime(rs.getTimestamp("add_time"));
	// formPluginEntity.setUpdateTime(rs.getTimestamp("update_time"));
	// formPluginEntity.setPluginId(rs.getInt("plugin_id"));
	// formPluginEntity.setName(rs.getString("name"));
	// formPluginEntity.setPhone(rs.getString("phone"));
	// formPluginEntity.setEmail(rs.getString("email"));
	// ls.add(formPluginEntity);
	// }
	// return ls.iterator();
	// }
}
