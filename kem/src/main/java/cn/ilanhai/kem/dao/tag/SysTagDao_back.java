package cn.ilanhai.kem.dao.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.tag.DeleteTagRequestEntity;
import cn.ilanhai.kem.domain.tag.ResponseTagEntity;
import cn.ilanhai.kem.domain.tag.SearchTagRequestEntity;
import cn.ilanhai.kem.domain.tag.SetSelectionTagRequestEntity;
import cn.ilanhai.kem.domain.tag.SysTagEntity;

@Component("tagDao")
public class SysTagDao_back extends BaseDao {
	public SysTagDao_back() {
		super();
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof SearchTagRequestEntity) {
			return queryData((SearchTagRequestEntity) entity);
		}
		return null;
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		CountDto count = new CountDto();
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof SysTagEntity) {
			return queryData((SysTagEntity) entity);
		}
		if (entity instanceof SetSelectionTagRequestEntity) {
			return queryData((SetSelectionTagRequestEntity) entity);
		}
		if (entity instanceof SearchTagRequestEntity) {
			count.setCount(queryDataForCount((SearchTagRequestEntity) entity));
			return count;
		}
		return null;
	}

	private Iterator<Entity> queryData(SearchTagRequestEntity entity) throws DaoAppException {
		// 获取排序模式
		final String orderMode = SearchTagRequestEntity.choseModle(entity.getOrderMode());
		StringBuilder SQL = new StringBuilder("SELECT * FROM sys_tags WHERE ");
		if (entity.getUserId() != null) {
			SQL.append(" (USER_ID = '0' OR USER_ID = '" + entity.getUserId() + "')");
		} else {
			SQL.append(" USER_ID = '0'");
		}
		if (!Str.isNullOrEmpty(entity.getTagName())) {
			SQL.append(" AND TAG_NAME LIKE ?");
		}
		if (entity.getIsSelection() != null) {
			SQL.append(" AND IS_SELECTION='" + entity.getIsSelection() + "'");
			if (entity.getIsSelection() && orderMode == null) {
				SQL.append(" ORDER BY ORDER_NUM ASC ,CREATETIME DESC ,tag_id asc");
			} else if (orderMode != null) {
				SQL.append(" ORDER BY QUOTE_NUM " + orderMode+",tag_id asc");
			}
		} else {
			if (orderMode == null) {
				SQL.append(" ORDER BY CREATETIME DESC ,tag_id asc");
			} else {
				SQL.append(" ORDER BY QUOTE_NUM " + orderMode+",tag_id asc");
			}
		}
		SQL.append(" LIMIT ?,? ;");
		final String sqlScript = SQL.toString();
		JdbcTemplate template = null;
		final SearchTagRequestEntity searchTagRequestEntity = entity;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				if (!Str.isNullOrEmpty(searchTagRequestEntity.getTagName())) {
					ps.setString(i++, "%" + searchTagRequestEntity.getTagName() + "%");
				}
				ps.setInt(i++, searchTagRequestEntity.getStartCount());
				ps.setInt(i++, searchTagRequestEntity.getPageSize());
			}
		}, new RowMapper<Entity>() {
			public ResponseTagEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
				ResponseTagEntity tagEntity = null;
				tagEntity = new ResponseTagEntity();
				tagEntity.setIsSelection("true".equals(rs.getString("is_selection")) ? true : false);
				tagEntity.setOrderNum(rs.getInt("order_num"));
				tagEntity.setQuoteNum(rs.getInt("quote_num"));
				tagEntity.setTagId(rs.getInt("tag_id"));
				tagEntity.setTagName(rs.getString("tag_name"));
				tagEntity.setCreatetime(rs.getTimestamp("createtime"));
				return tagEntity;
			}
		}).iterator();
	}

	/**
	 * 查询标签的条数
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int queryDataForCount(SearchTagRequestEntity entity) throws DaoAppException {
		final String orderMode = SearchTagRequestEntity.choseModle(entity.getOrderMode());
		StringBuilder SQL = new StringBuilder("SELECT Count(tag_id) as count FROM sys_tags WHERE ");
		if (entity.getUserId() != null) {
			SQL.append(" (USER_ID = '0' OR USER_ID = '" + entity.getUserId() + "')");
		} else {
			SQL.append(" USER_ID = '0'");
		}
		if (!Str.isNullOrEmpty(entity.getTagName())) {
			SQL.append(" AND TAG_NAME LIKE ?");
		}
		if (entity.getIsSelection() != null) {
			SQL.append(" AND IS_SELECTION='" + entity.getIsSelection() + "'");
			if (entity.getIsSelection() && orderMode == null) {
				SQL.append(" ORDER BY ORDER_NUM ASC ,CREATETIME DESC ");
			} else if (orderMode != null) {
				SQL.append(" ORDER BY QUOTE_NUM " + orderMode);
			}
		} else {
			if (orderMode == null) {
				SQL.append(" ORDER BY CREATETIME DESC ");
			} else {
				SQL.append(" ORDER BY QUOTE_NUM " + orderMode);
			}
		}
		final String sqlScript = SQL.toString();
		JdbcTemplate template = null;
		final SearchTagRequestEntity searchTagRequestEntity = entity;
		template = this.getJdbcTemplate();
		
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				if (!Str.isNullOrEmpty(searchTagRequestEntity.getTagName())) {
					ps.setString(i++, "%" + searchTagRequestEntity.getTagName() + "%");
				}
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getInt("count");
				} else {
					return 0;
				}
			}
		});
	}

	private Entity queryData(SysTagEntity entity) throws DaoAppException {
		final String sqlScript = "SELECT * FROM sys_tags WHERE tag_name = ? AND user_id = ?;";
		JdbcTemplate template = null;

		final SysTagEntity sysTagEntity = entity;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysTagEntity.getTagName());
				ps.setString(2, sysTagEntity.getUserId());
			}
		}, new ResultSetExtractor<ResponseTagEntity>() {
			public ResponseTagEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				ResponseTagEntity tagEntity = null;
				if (rs.next()) {
					tagEntity = new ResponseTagEntity();
					tagEntity.setIsSelection("true".equals(rs.getString("is_selection")) ? true : false);
					tagEntity.setOrderNum(rs.getInt("order_num"));
					tagEntity.setQuoteNum(rs.getInt("quote_num"));
					tagEntity.setTagId(rs.getInt("tag_id"));
				}
				return tagEntity;
			}
		});
	}

	private Entity queryData(SetSelectionTagRequestEntity entity) throws DaoAppException {
		final String sqlScript = "SELECT * FROM sys_tags WHERE tag_id = ?;";
		JdbcTemplate template = null;

		final SetSelectionTagRequestEntity selectionTagRequestEntity = entity;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, selectionTagRequestEntity.getTagId());
			}
		}, new ResultSetExtractor<SysTagEntity>() {
			public SysTagEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				SysTagEntity tagEntity = null;
				if (rs.next()) {
					tagEntity = new SysTagEntity();
					tagEntity.setIsSelection("true".equals(rs.getString("is_selection")) ? true : false);
					tagEntity.setOrderNum(rs.getInt("order_num"));
					tagEntity.setQuoteNum(rs.getInt("quote_num"));
					tagEntity.setTagId(rs.getInt("tag_id"));
					tagEntity.setUserId(rs.getString("user_id"));
					tagEntity.setTagName(rs.getString("tag_name"));
					tagEntity.setCreatetime(rs.getTimestamp("createtime"));
				}
				return tagEntity;
			}
		});
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		final String sqlScript = "DELETE FROM sys_tags WHERE tag_id=?;";
		JdbcTemplate template = null;
		int val = -1;
		if (entity == null)
			return val;
		final DeleteTagRequestEntity deleteTagRequestEntity = (DeleteTagRequestEntity) entity;
		template = this.getJdbcTemplate();
		val = template.update(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, deleteTagRequestEntity.getTagId());
			}
		});
		return val;
	}

	@SuppressWarnings("unchecked")
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof SysTagEntity) {
			return save((SysTagEntity) entity);
		} else if (entity instanceof IdEntity) {
			return addQuteNum(((IdEntity<String>) entity).getId());
		}
		return -1;
	}

	private int save(SysTagEntity entity) throws DaoAppException {
		SysTagEntity sysTagEntity = null;
		try {
			sysTagEntity = (SysTagEntity) entity;
			if (entity.getTagId() == null || entity.getTagId() == 0) {
				if (!this.isExists(sysTagEntity))
					return this.add(sysTagEntity);
				return 1;
			} else {
				if (!this.isExistsForId(sysTagEntity))
					return this.add(sysTagEntity);
				return this.update(sysTagEntity);
			}

		} catch (DaoAppException e) {
			throw e;
		}
	}

	private int addQuteNum(String tagName) throws DaoAppException {
		StringBuilder SQL = new StringBuilder();
		SQL.append("UPDATE sys_tags set quote_num= (quote_num+1)  WHERE tag_name=?;");
		List<Object> params = new ArrayList<Object>();
		params.add(tagName);
		return this.execUpdate(SQL.toString(), params.toArray());
	}

	private int add(final SysTagEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO sys_tags(tag_name,order_num,quote_num,is_selection,user_id,createtime)VALUES(?,?,?,?,?,?);";
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
				statement.setString(1, entity.getTagName());
				statement.setInt(2, entity.getOrderNum());
				statement.setInt(3, entity.getQuoteNum());
				statement.setString(4, entity.getIsSelection() + "");
				statement.setString(5, entity.getUserId());
				statement.setTimestamp(6, new Timestamp(entity.getCreatetime().getTime()));
				return statement;
			}
		}, holder);
		if (val > 0)
			entity.setTagId(holder.getKey().intValue());
		return val;
	}

	private int update(final SysTagEntity entity) throws DaoAppException {
		final String sqlScript = "UPDATE sys_tags SET tag_name=?,order_num=?,quote_num=?,is_selection=?,user_id=? WHERE tag_id=?;";
		JdbcTemplate template = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		val = template.update(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, entity.getTagName());
				ps.setInt(2, entity.getOrderNum());
				ps.setInt(3, entity.getQuoteNum());
				ps.setString(4, entity.getIsSelection() + "");
				ps.setString(5, entity.getUserId());
				ps.setInt(6, entity.getTagId());
			}
		});
		return val;
	}

	/**
	 * 判断该标签是否存在
	 * 
	 * @param phoneNo
	 *            电话号码
	 * @return true 存在，false 不存在
	 * @throws DaoAppException
	 */
	private boolean isExists(final SysTagEntity sysTagEntity) throws DaoAppException {
		final String sqlScript = "SELECT tag_id FROM sys_tags WHERE tag_name=?";
		JdbcTemplate template = null;
		Integer val = -1;
		if (sysTagEntity == null)
			throw new DaoAppException("");
		if (sysTagEntity.getTagId() == null) {
			return false;
		}
		template = this.getJdbcTemplate();
		val = template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, sysTagEntity.getTagName());
				// System.out.println(ps.toString());
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next())
					return new Integer(rs.getInt("tag_id"));
				return new Integer(-1);
			}
		});
		return val > 0;
	}

	/**
	 * 判断该标签是否存在
	 * 
	 * @param phoneNo
	 *            电话号码
	 * @return true 存在，false 不存在
	 * @throws DaoAppException
	 */
	private boolean isExistsForId(final SysTagEntity sysTagEntity) throws DaoAppException {
		final String sqlScript = "SELECT tag_id FROM sys_tags WHERE tag_id=?";
		JdbcTemplate template = null;
		Integer val = -1;
		if (sysTagEntity == null)
			throw new DaoAppException("");
		if (sysTagEntity.getTagId() == null) {
			return false;
		}
		template = this.getJdbcTemplate();
		val = template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, sysTagEntity.getTagId());
				// System.out.println(ps.toString());
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next())
					return new Integer(rs.getInt("tag_id"));
				return new Integer(-1);
			}
		});
		return val > 0;
	}
}
