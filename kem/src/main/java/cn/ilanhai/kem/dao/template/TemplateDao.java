package cn.ilanhai.kem.dao.template;

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
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.TagType;
import cn.ilanhai.kem.domain.enums.TemplateState;
import cn.ilanhai.kem.domain.template.LoadpublishinfoDto;
import cn.ilanhai.kem.domain.template.LoadpublishinfoResponseDto;
import cn.ilanhai.kem.domain.template.QueryTemplate;
import cn.ilanhai.kem.domain.template.SavePublishTemplateEntity;
import cn.ilanhai.kem.domain.template.SaveSysTagTemplateEntity;
import cn.ilanhai.kem.domain.template.SearchCollectionTemplateDataEntity;
import cn.ilanhai.kem.domain.template.SearchCollectionTemplateEntity;
import cn.ilanhai.kem.domain.template.SearchTemplateDataEntity;
import cn.ilanhai.kem.domain.template.SearchTemplateDto;
import cn.ilanhai.kem.domain.template.SearchTemplateTagDataEntity;
import cn.ilanhai.kem.domain.template.SearchusedtemplateDataEntity;
import cn.ilanhai.kem.domain.template.SearchusedtemplateEntity;
import cn.ilanhai.kem.domain.template.TagResponseDataEntity;
import cn.ilanhai.kem.domain.template.TemplateCollectionEntity;
import cn.ilanhai.kem.domain.template.TemplateEntity;
import cn.ilanhai.kem.domain.template.TemplateUseLogEntity;

/**
 * 模板数据访问
 * 
 * @author he
 *
 */
@Component("templateDao")
public class TemplateDao extends BaseDao {

	public TemplateDao() {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		try {
			if (entity instanceof TemplateEntity) {
				return save((TemplateEntity) entity);
			} else if (entity instanceof SavePublishTemplateEntity) {
				return save((SavePublishTemplateEntity) entity);
			} else if (entity instanceof TemplateUseLogEntity) {
				return saveTemplateUseLog((TemplateUseLogEntity) entity);
			} else if (entity instanceof TemplateCollectionEntity) {
				TemplateCollectionEntity templateCollectionEntity = (TemplateCollectionEntity) entity;
				TemplateCollectionEntity templateCollectionEntityNew = queryTmplateCollectionModel(
						templateCollectionEntity);
				if (templateCollectionEntityNew != null) {
					templateCollectionEntityNew.setCollectionState(templateCollectionEntity.getCollectionState());
					return this.updateteTmplateCollectionModel(templateCollectionEntityNew);
				} else {
					return this.saveTmplateCollectionModel(templateCollectionEntity);
				}
			} else if (entity instanceof SaveSysTagTemplateEntity) {
				return save((SaveSysTagTemplateEntity) entity);
			}
		} catch (

		DaoAppException e) {
			throw e;
		}
		return -1;

	}

	private TemplateCollectionEntity queryTmplateCollectionModel(TemplateCollectionEntity templateCollectionEntity)
			throws DaoAppException {
		final String sqlScript = "SELECT * FROM template_collection WHERE template_id = ? AND user_id = ? AND manuscript_type = ?;";
		List<Object> params = new ArrayList<Object>();
		params.add(templateCollectionEntity.getTemplateId());
		params.add(templateCollectionEntity.getUserId());
		params.add(templateCollectionEntity.getManuscriptType());

		SqlRowSet SqlRowSet = this.execQueryForRowSet(sqlScript, params.toArray());
		if (SqlRowSet.next()) {
			TemplateCollectionEntity templateCollectionEntityNow = new TemplateCollectionEntity();
			templateCollectionEntityNow.setCollectionId(SqlRowSet.getInt("collection_id"));
			templateCollectionEntityNow.setTemplateId(SqlRowSet.getString("template_id"));
			templateCollectionEntityNow.setUserId(SqlRowSet.getString("user_id"));
			templateCollectionEntityNow.setCollectionState(SqlRowSet.getInt("collection_state"));
			templateCollectionEntityNow.setCreatetime(SqlRowSet.getDate("createtime"));
			templateCollectionEntityNow.setManuscriptType(SqlRowSet.getInt("manuscript_type"));
			return templateCollectionEntityNow;
		} else {
			return null;
		}
	}

	private int saveTmplateCollectionModel(TemplateCollectionEntity templateCollectionEntity) throws DaoAppException {
		final String sqlScript = "INSERT INTO template_collection (template_id, user_id, collection_state,createtime,manuscript_type) VALUES (?, ?, ?,?,?);";
		List<Object> params = new ArrayList<Object>();
		params.add(templateCollectionEntity.getTemplateId());
		params.add(templateCollectionEntity.getUserId());
		params.add(templateCollectionEntity.getCollectionState());
		params.add(templateCollectionEntity.getCreatetime());
		params.add(templateCollectionEntity.getManuscriptType());
		return this.execUpdate(sqlScript, params.toArray());
	}

	private int updateteTmplateCollectionModel(TemplateCollectionEntity templateCollectionEntity)
			throws DaoAppException {
		final String sqlScript = "UPDATE template_collection SET template_id=?, user_id=?, collection_state=?,createtime =? WHERE collection_id=?;";
		List<Object> params = new ArrayList<Object>();
		params.add(templateCollectionEntity.getTemplateId());
		params.add(templateCollectionEntity.getUserId());
		params.add(templateCollectionEntity.getCollectionState());
		params.add(templateCollectionEntity.getCreatetime());
		params.add(templateCollectionEntity.getCollectionId());
		return this.execUpdate(sqlScript, params.toArray());
	}

	private int saveTemplateUseLog(final TemplateUseLogEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO prod_template_uselog(user_id,user_type,template_id,createtime)VALUES(?,?,?,?);";
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
				statement.setString(1, entity.getUserId());
				statement.setInt(2, entity.getUserType().getValue());
				statement.setString(3, entity.getTemplateId());
				statement.setTimestamp(4, new Timestamp(entity.getCreateTime().getTime()));
				return statement;
			}
		}, holder);
		if (val > 0)
			entity.setLogId(holder.getKey().intValue());
		return val;
	}

	/**
	 * 保存发布模版数据信息
	 * 
	 * @param enity
	 * @return
	 * @throws DaoAppException
	 */
	private int save(SavePublishTemplateEntity enity) throws DaoAppException {
		int val = -1;
		if (save(enity.getObj()) < 0 || addTag(enity) < 0 || addKeyWord(enity) < 0) {
			throw new DaoAppException();
		} else {
			val = 1;
		}
		return val;
	}

	/**
	 * 保存发布模版数据信息
	 * 
	 * @param enity
	 * @return
	 * @throws DaoAppException
	 */
	private int save(SaveSysTagTemplateEntity enity) throws DaoAppException {
		int val = -1;
		if (save(enity.getObj()) < 0 || addTag(enity) < 0) {
			throw new DaoAppException();
		} else {
			val = 1;
		}
		return val;
	}

	private int save(TemplateEntity enity) throws DaoAppException {
		try {
			if (!this.isExists(enity))
				return this.add(enity);
			return this.update(enity);
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private int add(final TemplateEntity entity) {
		final String sqlScript = "INSERT INTO prod_template(user_id,cover_img,template_name,main_color,summary,createtime,template_state,template_content,template_type,template_id,verify_name,verify_time,shelf_time,bounced_reason)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
				statement.setString(1, entity.getUserId());
				statement.setString(2, entity.getCoverImg());
				statement.setString(3, entity.getTemplateName());
				statement.setString(4, entity.getMainColor());
				statement.setString(5, entity.getSummary());
				statement.setTimestamp(6, new Timestamp(entity.getCreatetime().getTime()));
				statement.setInt(7, entity.getTemplateState().getValue());
				statement.setString(8, entity.getTemplateContent());
				statement.setInt(9, entity.getTemplateType());
				statement.setString(10, entity.getTemplateId());
				statement.setObject(11, entity.getVerifyName());
				if (entity.getVerifytime() != null) {
					statement.setTimestamp(12, new Timestamp(entity.getVerifytime().getTime()));
				} else {
					statement.setTimestamp(12, null);
				}
				if (entity.getShelftime() != null) {
					statement.setTimestamp(13, new Timestamp(entity.getShelftime().getTime()));
				} else {
					statement.setTimestamp(13, null);
				}
				statement.setString(14, entity.getBouncedReason());
				return statement;
			}
		}, holder);

		return val;
	}

	private int addTag(final SavePublishTemplateEntity entity) {
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (entity == null)
			return val;
		// 删除标签 再添加标签
		this.deleteTag(entity.getObj().getTemplateId(), TagType.USER.getValue());
		template = this.getJdbcTemplate();
		for (final String tagName : entity.getTagNames()) {
			holder = new GeneratedKeyHolder();
			final String sqlScript = "INSERT INTO prod_template_tag(template_id,tag_name,tag_type)VALUES(?,?,?);";
			val += template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement statement = null;
					statement = con.prepareStatement(sqlScript);
					statement.setString(1, entity.getObj().getTemplateId());
					statement.setString(2, tagName);
					statement.setInt(3, TagType.USER.getValue());
					return statement;
				}
			}, holder);
		}
		return val;
	}

	private int addTag(final SaveSysTagTemplateEntity entity) {
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (entity == null)
			return val;
		// 删除标签 再添加标签
		this.deleteTag(entity.getObj().getTemplateId(), TagType.SYS.getValue());
		template = this.getJdbcTemplate();
		for (final String tagName : entity.getTagNames()) {
			holder = new GeneratedKeyHolder();
			final String sqlScript = "INSERT INTO prod_template_tag(template_id,tag_name,tag_type)VALUES(?,?,?);";
			val += template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement statement = null;
					statement = con.prepareStatement(sqlScript);
					statement.setString(1, entity.getObj().getTemplateId());
					statement.setString(2, tagName);
					statement.setInt(3, TagType.SYS.getValue());
					return statement;
				}
			}, holder);
		}
		return val;
	}

	/**
	 * 删除 发布模版标签
	 * 
	 * @param template_id
	 * @return
	 */
	private int deleteTag(final String template_id, final Integer type) {
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (template_id == null)
			return val;
		template = this.getJdbcTemplate();
		holder = new GeneratedKeyHolder();
		final String sqlScript = "DELETE FROM prod_template_tag WHERE template_id = ? AND tag_type =? ;";
		val += template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				statement.setString(1, template_id);
				statement.setInt(2, type);
				return statement;
			}
		}, holder);
		return val;
	}

	/**
	 * 删除 发布模版关键字
	 * 
	 * @param template_id
	 * @return
	 */
	private int deleteKeyword(final String template_id) {
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (template_id == null)
			return val;
		template = this.getJdbcTemplate();
		holder = new GeneratedKeyHolder();
		final String sqlScript = "DELETE FROM prod_template_keyword WHERE template_id = ?;";
		val += template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				statement.setString(1, template_id);
				return statement;
			}
		}, holder);
		return val;
	}

	/**
	 * 查询发布模版标签
	 * 
	 * @param template_id
	 * @return
	 */
	private List<String> queryTag(final String template_id) {
		JdbcTemplate template = null;
		if (template_id == null)
			return new ArrayList<String>();
		template = this.getJdbcTemplate();
		final String sqlScript = "SELECT tag_name  FROM  prod_template_tag WHERE template_id = ?;";
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, template_id);
			}
		}, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
	}

	/**
	 * 查询发布模版关键字
	 * 
	 * @param template_id
	 * @return
	 */
	private List<String> queryKeyword(final String template_id) {
		JdbcTemplate template = null;
		if (template_id == null)
			return new ArrayList<String>();
		template = this.getJdbcTemplate();
		final String sqlScript = "SELECT keyword  FROM  prod_template_keyword WHERE template_id = ?;";
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, template_id);
			}
		}, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
	}

	private int addKeyWord(final SavePublishTemplateEntity entity) {
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (entity == null)
			return val;
		// 删除标签 再添加标签
		this.deleteKeyword(entity.getObj().getTemplateId());
		template = this.getJdbcTemplate();
		for (final String keyword : entity.getKeywords()) {
			holder = new GeneratedKeyHolder();
			final String sqlScript = "INSERT INTO prod_template_keyword(template_id,keyword)VALUES(?,?);";
			val += template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement statement = null;
					statement = con.prepareStatement(sqlScript);
					statement.setString(1, entity.getObj().getTemplateId());
					statement.setString(2, keyword);
					return statement;
				}
			}, holder);
		}
		return val;
	}

	private int update(final TemplateEntity entity) {
		final String sqlScript = "UPDATE prod_template SET user_id=?,cover_img=?,template_name=?,main_color=?,summary=?,template_state=?,template_content=? ,template_type=?,publish_state=?,verify_name=?,verify_time=?,shelf_time=?,bounced_reason=? WHERE template_ID=?;";
		JdbcTemplate template = null;
		int val = -1;
		if (entity == null)
			return val;
		template = this.getJdbcTemplate();
		val = template.update(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, entity.getUserId());
				ps.setString(2, entity.getCoverImg());
				ps.setString(3, entity.getTemplateName());
				ps.setString(4, entity.getMainColor());
				ps.setString(5, entity.getSummary());
				ps.setInt(6, entity.getTemplateState().getValue());
				ps.setString(7, entity.getTemplateContent());
				ps.setInt(8, entity.getTemplateType());
				ps.setInt(9, entity.getPublish_state());
				ps.setObject(10, entity.getVerifyName());
				if (entity.getVerifytime() != null) {
					ps.setObject(11, new Timestamp(entity.getVerifytime().getTime()));
				} else {
					ps.setObject(11, null);
				}
				if (entity.getShelftime() != null) {
					ps.setObject(12, new Timestamp(entity.getShelftime().getTime()));
				} else {
					ps.setObject(12, null);
				}
				ps.setString(13, entity.getBouncedReason());
				ps.setString(14, entity.getTemplateId());
			}
		});
		return val;
	}

	private boolean isExists(final TemplateEntity entity) throws DaoAppException {
		final String sqlScript = "SELECT template_id FROM prod_template WHERE template_id=?";
		JdbcTemplate template = null;
		Integer val = -1;
		if (entity == null || entity.getTemplateId() == null || entity.getTemplateId().isEmpty())
			return false;

		template = this.getJdbcTemplate();
		val = template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, entity.getTemplateId());
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				int size = 0;
				if (rs.next())
					size++;
				return size;
			}
		});
		return val > 0;
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof SearchTemplateDto) {
			return queryData((SearchTemplateDto) entity);
		} else if (entity instanceof SearchTemplateTagDataEntity) {
			return queryDataForTemplateTag((SearchTemplateTagDataEntity) entity);
		} else if (entity instanceof SearchusedtemplateEntity) {
			return queryDataForUsedTemplate((SearchusedtemplateEntity) entity);
		} else if (entity instanceof SearchCollectionTemplateEntity) {
			return queryCollection((SearchCollectionTemplateEntity) entity);
		}
		return null;
	}

	private Iterator<Entity> queryData(SearchTemplateDto entity) {
		String SQL = "SELECT * FROM (SELECT DISTINCT A.template_id,A.user_id,A.cover_img,A.template_name,A.main_color,A.summary,A.createtime,A.template_state,A.publish_state,A.template_type,A.verify_name,A.verify_time,A.shelf_time,A.bounced_reason,C.user_name,C.user_phone ";
		if (entity.getCurrentLoginUserId() != null) {
			SQL += " ,D.collection_state ";
		}
		SQL += " FROM prod_template A";

		if (entity.isBackUser() && entity.getUserId() != null) {
			SQL += "";
		} else {
			SQL += " LEFT JOIN prod_template_tag B ON A.template_id = B.template_id ";
		}
		if (entity.getUserId() != null && !entity.isBackUser()) {
			SQL += " AND B.tag_type = " + TagType.USER.getValue();
		} else if (!entity.isBackUser()) {
			SQL += " AND B.tag_type = " + TagType.SYS.getValue();
		}
		SQL += " LEFT JOIN user_front_user C ON A.user_id = C.user_id ";

		if (entity.getCurrentLoginUserId() != null) {
			SQL += " LEFT JOIN template_collection D ON A.template_id = D.template_id AND D.user_id =?";
		}
		SQL += "  WHERE 1=1";

		if (!Str.isNullOrEmpty(entity.getUserId())) {
			SQL += " AND A.user_id =?";
		}

		/**
		 * 起始时间
		 */
		if (entity.getTimeStart() != null) {
			SQL += " AND A.createtime >=?";
		}
		/**
		 * 结束时间
		 */
		if (entity.getTimeEnd() != null) {
			SQL += " AND A.createtime <=DATE_ADD(?,INTERVAL 1 DAY)";
		}

		if (!Str.isNullOrEmpty(entity.getTagName())) {
			SQL += " AND B.tag_name =?";
		}
		if (!Str.isNullOrEmpty(entity.getTemplateName())) {
			if (entity.getUserId() != null) {
				SQL += " AND A.template_name like ?";
			} else {
				if (TemplateState.VERIFY.getValue().equals(entity.getTemplateState())
						|| TemplateState.UNSUBMITTED.getValue().equals(entity.getTemplateState())) {
					SQL += " AND A.template_name like ?";
				} else {
					SQL += " AND A.verify_name like ?";
				}
			}
		}
		SQL += ") A WHERE 1=1";
		if (entity.isBackUser() && entity.getUserId() == null) {
			SQL += " AND A.template_state =?";
		} else if (entity.getTemplateState() != null && entity.getTemplateState() != 0) {
			SQL += " AND A.template_state =?";
		}
		/**
		 * 模块类型
		 */
		if (entity.getTemplateType() != null) {
			SQL += " AND A.template_type =?";
		}
		SQL += " ORDER BY A.CREATETIME DESC LIMIT ?,? ;";
		final String sqlScript = SQL;
		JdbcTemplate template = null;
		final SearchTemplateDto searchTemplateDto = entity;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				if (searchTemplateDto.getCurrentLoginUserId() != null) {
					ps.setString(i++, searchTemplateDto.getCurrentLoginUserId());
				}

				if (!Str.isNullOrEmpty(searchTemplateDto.getUserId())) {
					ps.setString(i++, searchTemplateDto.getUserId());
				}

				/**
				 * 起始时间
				 */
				if (searchTemplateDto.getTimeStart() != null) {
					ps.setTimestamp(i++, new Timestamp(searchTemplateDto.getTimeStart().getTime()));
				}

				/**
				 * 结束时间
				 */
				if (searchTemplateDto.getTimeEnd() != null) {
					ps.setTimestamp(i++, new Timestamp(searchTemplateDto.getTimeEnd().getTime()));
				}

				if (!Str.isNullOrEmpty(searchTemplateDto.getTagName())) {
					ps.setString(i++, searchTemplateDto.getTagName());
				}
				if (!Str.isNullOrEmpty(searchTemplateDto.getTemplateName())) {
					ps.setString(i++, "%" + searchTemplateDto.getTemplateName() + "%");
				}

				if (searchTemplateDto.isBackUser() && searchTemplateDto.getUserId() == null) {
					ps.setInt(i++, searchTemplateDto.getTemplateState());
				} else if (searchTemplateDto.getTemplateState() != null && searchTemplateDto.getTemplateState() != 0) {
					ps.setInt(i++, searchTemplateDto.getTemplateState());
				}
				/**
				 * 模块类型
				 */
				if (searchTemplateDto.getTemplateType() != null) {
					ps.setInt(i++, searchTemplateDto.getTemplateType());
				}

				ps.setInt(i++, searchTemplateDto.getStartCount());
				ps.setInt(i++, searchTemplateDto.getPageSize());
			}
		}, new RowMapper<Entity>() {
			public SearchTemplateDataEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
				SearchTemplateDataEntity searchTemplateDataEntity = null;
				searchTemplateDataEntity = new SearchTemplateDataEntity();
				searchTemplateDataEntity.setTemplateId(rs.getString("template_id"));
				searchTemplateDataEntity.setTemplateName(rs.getString("template_name"));
				searchTemplateDataEntity.setTemplateImg(rs.getString("cover_img"));
				searchTemplateDataEntity.setCreatetime(rs.getTimestamp("createtime"));
				searchTemplateDataEntity.setTemplateType(rs.getInt("template_type"));
				searchTemplateDataEntity.setTemplateState(rs.getInt("template_state"));
				searchTemplateDataEntity.setPublishState(rs.getInt("publish_state"));
				String userName = rs.getString("user_name");
				if (!Str.isNullOrEmpty(userName)) {
					searchTemplateDataEntity.setUser(userName);
				} else {
					searchTemplateDataEntity.setUser(rs.getString("user_phone"));
				}
				if (isExistColumn(rs, "collection_state")) {
					searchTemplateDataEntity.setIsCollection(rs.getInt("collection_state"));
				}
				searchTemplateDataEntity.setSummary(rs.getString("summary"));
				// TODO
				searchTemplateDataEntity.setPublishedURL("");
				searchTemplateDataEntity.setVerifyName(rs.getString("verify_name"));
				searchTemplateDataEntity.setVerifytime(rs.getTimestamp("verify_time"));
				searchTemplateDataEntity.setShelftime(rs.getTimestamp("shelf_time"));
				return searchTemplateDataEntity;
			}
		}).iterator();
	}

	private boolean isExistColumn(ResultSet rs, String columnName) {
		try {
			if (rs.findColumn(columnName) > 0) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}

		return false;
	}

	private Iterator<Entity> queryCollection(SearchCollectionTemplateEntity entity) throws DaoAppException {
		StringBuilder SQL = new StringBuilder(
				"SELECT * FROM (SELECT DISTINCT A.collection_id ,A.manuscript_type,A.createtime AS ollectionCreatetime ,a.collection_state,B.template_id,B.cover_img,B.template_name,B.main_color,B.summary,B.template_state,B.publish_state,B.template_type,B.verify_name,B.verify_time,B.shelf_time,B.bounced_reason,B.createtime as templateCreatetime,C.* FROM prod_template B LEFT JOIN template_collection A ON A.template_id = B.template_id "
						+ "LEFT JOIN user_front_user C ON B.user_id = C.user_id WHERE A.user_id =? ) A WHERE A.collection_state =1");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getUserId());

		SQL.append(" AND A.template_state =" + TemplateState.ADDED.getValue());
		if (entity.getTemplateType() != null) {
			SQL.append(" AND A.template_type =?");
			params.add(entity.getTemplateType());
		}
		if (!Str.isNullOrEmpty(entity.getTemplateName())) {
			SQL.append(" AND A.template_name like ?");
			params.add("%" + entity.getTemplateName() + "%");
		}
		SQL.append(" union ");
		SQL.append(
				" SELECT DISTINCT  * FROM ( SELECT A.collection_id ,A.manuscript_type, A.createtime AS ollectionCreatetime ,a.collection_state,B.manuscript_id AS template_id ,");
		SQL.append(" s.manuscript_img AS cover_img,");
		SQL.append(" s.manuscript_name AS template_name,");
		SQL.append(" s.manuscript_maincolor AS main_color,");
		SQL.append(" s.manuscript_summary AS summary,");
		SQL.append(" B.state AS template_state,");
		SQL.append(" 1 AS publish_state,");
		SQL.append(" B.terminal_type AS template_type,");
		SQL.append(" s.manuscript_name AS verify_name,");
		SQL.append(" B.createtime AS verify_time,");
		SQL.append(" B.createtime AS shelf_time,");
		SQL.append(" '' AS bounced_reason,");
		SQL.append(" B.createtime AS templateCreatetime,");
		SQL.append(" C.* ");
		SQL.append(" FROM");
		SQL.append(" template_collection A");
		SQL.append(" LEFT JOIN prod_manuscript B ON A.template_id = B.manuscript_id");
		SQL.append(" AND B. ENABLE = 0");
		SQL.append(" LEFT JOIN prod_setting s ON S.manuscript_id = B.manuscript_id");
		SQL.append(" AND s. ENABLE = 0");
		SQL.append(" LEFT JOIN user_front_user C ON B.user_id = C.user_id");
		SQL.append(" WHERE");
		SQL.append(" A.collection_state = 1");
		SQL.append(" AND A.manuscript_type = 4");
		SQL.append(" AND A.user_id =?");
		params.add(entity.getUserId());
		if (entity.getTemplateType() != null) {
			SQL.append(" AND B.terminal_type =?");
			params.add(entity.getTemplateType());
		}
		if (!Str.isNullOrEmpty(entity.getTemplateName())) {
			SQL.append(" AND s.manuscript_name like ?");
			params.add("%" + entity.getTemplateName() + "%");
		}
		SQL.append(" ) AS temp ");

		SQL.append(" ORDER BY ollectionCreatetime DESC LIMIT ?,? ;");
		params.add(entity.getStartCount());
		params.add(entity.getPageSize());
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		List<Entity> searchTemplateDataEntitys = new ArrayList<Entity>();
		while (sqlRowSet.next()) {
			SearchCollectionTemplateDataEntity searchTemplateDataEntity = null;
			searchTemplateDataEntity = new SearchCollectionTemplateDataEntity();
			searchTemplateDataEntity.setTemplateId(sqlRowSet.getString("template_id"));
			searchTemplateDataEntity.setTemplateName(sqlRowSet.getString("template_name"));
			searchTemplateDataEntity.setTemplateImg(sqlRowSet.getString("cover_img"));
			searchTemplateDataEntity.setCreatetime(sqlRowSet.getTimestamp("templateCreatetime"));
			searchTemplateDataEntity.setTemplateType(sqlRowSet.getInt("template_type"));
			searchTemplateDataEntity.setTemplateState(sqlRowSet.getInt("template_state"));
			searchTemplateDataEntity.setPublishState(sqlRowSet.getInt("publish_state"));
			String userName = sqlRowSet.getString("user_name");
			if (!Str.isNullOrEmpty(userName)) {
				searchTemplateDataEntity.setUser(userName);
			} else {
				searchTemplateDataEntity.setUser(sqlRowSet.getString("user_phone"));
			}

			searchTemplateDataEntity.setSummary(sqlRowSet.getString("summary"));
			searchTemplateDataEntity.setIsCollection(sqlRowSet.getInt("collection_state"));
			searchTemplateDataEntity.setVerifyName(sqlRowSet.getString("verify_name"));
			searchTemplateDataEntity.setVerifytime(sqlRowSet.getTimestamp("verify_time"));
			searchTemplateDataEntity.setShelftime(sqlRowSet.getTimestamp("shelf_time"));
			searchTemplateDataEntity.setBouncedReason(sqlRowSet.getString("bounced_reason"));
			searchTemplateDataEntity.setManuscriptType(sqlRowSet.getInt("manuscript_type"));
			searchTemplateDataEntitys.add(searchTemplateDataEntity);
		}
		return searchTemplateDataEntitys.iterator();
	}

	private int queryCollectionCount(SearchCollectionTemplateEntity entity) throws DaoAppException {
		StringBuilder SQL = new StringBuilder(
				"SELECT count(DISTINCT R.collection_id) AS count FROM (SELECT DISTINCT A.collection_id ,A.manuscript_type,A.createtime AS ollectionCreatetime ,a.collection_state,B.template_id,B.cover_img,B.template_name,B.main_color,B.summary,B.template_state,B.publish_state,B.template_type,B.verify_name,B.verify_time,B.shelf_time,B.bounced_reason,B.createtime as templateCreatetime,C.* FROM template_collection A LEFT JOIN prod_template B ON A.template_id = B.template_id "
						+ "LEFT JOIN user_front_user C ON B.user_id = C.user_id WHERE A.collection_state =1");
		List<Object> params = new ArrayList<Object>();
		SQL.append(" AND A.user_id =?");
		params.add(entity.getUserId());

		SQL.append(" AND B.template_state =" + TemplateState.ADDED.getValue());
		if (entity.getTemplateType() != null) {
			SQL.append(" AND B.template_type =?");
			params.add(entity.getTemplateType());
		}
		if (!Str.isNullOrEmpty(entity.getTemplateName())) {
			SQL.append(" AND B.template_name like ?");
			params.add("%" + entity.getTemplateName() + "%");
		}
		SQL.append(" union ");
		SQL.append(
				" SELECT DISTINCT  * FROM ( SELECT A.collection_id ,A.manuscript_type, A.createtime AS ollectionCreatetime ,a.collection_state,B.manuscript_id AS template_id ,");
		SQL.append(" s.manuscript_img AS cover_img,");
		SQL.append(" s.manuscript_name AS template_name,");
		SQL.append(" s.manuscript_maincolor AS main_color,");
		SQL.append(" s.manuscript_summary AS summary,");
		SQL.append(" B.state AS template_state,");
		SQL.append(" 1 AS publish_state,");
		SQL.append(" B.terminal_type AS template_type,");
		SQL.append(" s.manuscript_name AS verify_name,");
		SQL.append(" B.createtime AS verify_time,");
		SQL.append(" B.createtime AS shelf_time,");
		SQL.append(" '' AS bounced_reason,");
		SQL.append(" B.createtime AS templateCreatetime,");
		SQL.append(" C.* ");
		SQL.append(" FROM");
		SQL.append(" template_collection A");
		SQL.append(" LEFT JOIN prod_manuscript B ON A.template_id = B.manuscript_id");
		SQL.append(" AND B. ENABLE = 0");
		SQL.append(" LEFT JOIN prod_setting s ON S.manuscript_id = B.manuscript_id");
		SQL.append(" AND s. ENABLE = 0");
		SQL.append(" LEFT JOIN user_front_user C ON B.user_id = C.user_id");
		SQL.append(" WHERE");
		SQL.append(" A.collection_state = 1");
		SQL.append(" AND A.manuscript_type = 4");
		SQL.append(" AND A.user_id =?");
		params.add(entity.getUserId());
		if (entity.getTemplateType() != null) {
			SQL.append(" AND B.terminal_type =?");
			params.add(entity.getTemplateType());
		}
		if (!Str.isNullOrEmpty(entity.getTemplateName())) {
			SQL.append(" AND s.manuscript_name like ?");
			params.add("%" + entity.getTemplateName() + "%");
		}
		SQL.append(" ) AS temp ) AS R");

		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		if (sqlRowSet.next()) {
			return sqlRowSet.getInt("count");
		} else {
			return 0;
		}
	}

	private int queryDataForCount(SearchTemplateDto entity) {
		String SQL = "SELECT COUNT(1) AS count FROM (SELECT DISTINCT A.template_id,A.user_id,A.cover_img,A.template_name,A.main_color,A.summary,A.createtime,A.template_state,A.publish_state,A.template_type,A.verify_name,A.verify_time,A.shelf_time,A.bounced_reason,C.user_name,C.user_phone ";
		if (entity.getCurrentLoginUserId() != null) {
			SQL += " ,D.collection_state ";
		}
		SQL += " FROM prod_template A";

		if (entity.isBackUser() && entity.getUserId() != null) {
			SQL += "";
		} else {
			SQL += " LEFT JOIN prod_template_tag B ON A.template_id = B.template_id ";
		}
		if (entity.getUserId() != null && !entity.isBackUser()) {
			SQL += " AND B.tag_type = " + TagType.USER.getValue();
		} else if (!entity.isBackUser()) {
			SQL += " AND B.tag_type = " + TagType.SYS.getValue();
		}
		SQL += " LEFT JOIN user_front_user C ON A.user_id = C.user_id ";

		if (entity.getCurrentLoginUserId() != null) {
			SQL += " LEFT JOIN template_collection D ON A.template_id = D.template_id AND D.user_id =?";
		}
		SQL += "  WHERE 1=1";

		if (entity.getUserId() != null) {
			SQL += " AND A.user_id =?";
		}

		/**
		 * 起始时间
		 */
		if (entity.getTimeStart() != null) {
			SQL += " AND A.createtime >=?";
		}
		/**
		 * 结束时间
		 */
		if (entity.getTimeEnd() != null) {
			SQL += " AND A.createtime <=DATE_ADD(?,INTERVAL 1 DAY)";
		}

		if (!Str.isNullOrEmpty(entity.getTagName())) {
			SQL += " AND B.tag_name =?";
		}
		if (!Str.isNullOrEmpty(entity.getTemplateName())) {
			if (entity.getUserId() != null) {
				SQL += " AND A.template_name like ?";
			} else {
				if (TemplateState.VERIFY.getValue().equals(entity.getTemplateState())
						|| TemplateState.UNSUBMITTED.getValue().equals(entity.getTemplateState())) {
					SQL += " AND A.template_name like ?";
				} else {
					SQL += " AND A.verify_name like ?";
				}
			}
		}
		SQL += ") A WHERE 1=1";
		if (entity.isBackUser() && entity.getUserId() == null) {
			SQL += " AND A.template_state =?";
		} else if (entity.getTemplateState() != null && entity.getTemplateState() != 0) {
			SQL += " AND A.template_state =?";
		}
		/**
		 * 模块类型
		 */
		if (entity.getTemplateType() != null) {
			SQL += " AND A.template_type =?";
		}
		final String sqlScript = SQL;
		JdbcTemplate template = null;
		final SearchTemplateDto searchTemplateDto = entity;
		template = this.getJdbcTemplate();

		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;

				if (searchTemplateDto.getCurrentLoginUserId() != null) {
					ps.setString(i++, searchTemplateDto.getCurrentLoginUserId());
				}

				if (searchTemplateDto.getUserId() != null) {
					ps.setString(i++, searchTemplateDto.getUserId());
				}

				/**
				 * 起始时间
				 */
				if (searchTemplateDto.getTimeStart() != null) {
					ps.setTimestamp(i++, new Timestamp(searchTemplateDto.getTimeStart().getTime()));
				}

				/**
				 * 结束时间
				 */
				if (searchTemplateDto.getTimeEnd() != null) {
					ps.setTimestamp(i++, new Timestamp(searchTemplateDto.getTimeEnd().getTime()));
				}

				if (!Str.isNullOrEmpty(searchTemplateDto.getTagName())) {
					ps.setString(i++, searchTemplateDto.getTagName());
				}
				if (!Str.isNullOrEmpty(searchTemplateDto.getTemplateName())) {
					ps.setString(i++, "%" + searchTemplateDto.getTemplateName() + "%");
				}

				if (searchTemplateDto.isBackUser() && searchTemplateDto.getUserId() == null) {
					ps.setInt(i++, searchTemplateDto.getTemplateState());
				} else if (searchTemplateDto.getTemplateState() != null && searchTemplateDto.getTemplateState() != 0) {
					ps.setInt(i++, searchTemplateDto.getTemplateState());
				}

				/**
				 * 模块类型
				 */
				if (searchTemplateDto.getTemplateType() != null) {
					ps.setInt(i++, searchTemplateDto.getTemplateType());
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

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		CountDto count = new CountDto();
		if (entity.getClass() == QueryTemplate.class)
			return this.queryByQueryTemplate((QueryTemplate) entity);
		else if (entity.getClass() == IdEntity.class) {
			return this.queryById((IdEntity<String>) entity);
		} else if (entity.getClass() == LoadpublishinfoDto.class) {

			LoadpublishinfoDto loadpublishinfoDto = (LoadpublishinfoDto) entity;
			IdEntity<String> idDto = new IdEntity<String>();
			idDto.setId(loadpublishinfoDto.getTemplateId());
			TemplateEntity templateEntity = (TemplateEntity) this.queryById(idDto);
			if (templateEntity == null) {
				return null;
			}
			LoadpublishinfoResponseDto LoadpublishinfoResponseDto = new LoadpublishinfoResponseDto();
			LoadpublishinfoResponseDto.setCoverImg(templateEntity.getCoverImg());
			LoadpublishinfoResponseDto.setMainColor(templateEntity.getMainColor());
			LoadpublishinfoResponseDto.setPublishName(templateEntity.getTemplateName());
			LoadpublishinfoResponseDto.setSummary(templateEntity.getSummary());
			LoadpublishinfoResponseDto.setTemplateId(loadpublishinfoDto.getTemplateId());
			LoadpublishinfoResponseDto.setKeywords(this.queryKeyword(loadpublishinfoDto.getTemplateId()));
			LoadpublishinfoResponseDto.setTagNames(this.queryTag(loadpublishinfoDto.getTemplateId()));
			return LoadpublishinfoResponseDto;
		} else if (entity instanceof SearchTemplateDto) {
			count.setCount(queryDataForCount((SearchTemplateDto) entity));
		} else if (entity instanceof SearchusedtemplateEntity) {
			count.setCount(queryDataForUsedTemplateForCount((SearchusedtemplateEntity) entity));
		} else if (entity instanceof SearchCollectionTemplateEntity) {
			count.setCount(queryCollectionCount((SearchCollectionTemplateEntity) entity));
		} else {
			return null;
		}
		return count;
	}

	private Entity queryByQueryTemplate(final QueryTemplate queryTemplate) throws DaoAppException {
		final String sqlScript = "SELECT * FROM prod_template WHERE template_id=? AND user_id=?;";
		JdbcTemplate template = null;
		if (queryTemplate == null)
			throw new DaoAppException("请指定查询参数");
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, queryTemplate.getTemplateId());
				ps.setInt(2, queryTemplate.getUserId());
			}
		}, new ResultSetExtractor<TemplateEntity>() {
			public TemplateEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				TemplateEntity templateEntity = null;
				if (rs.next()) {
					templateEntity = new TemplateEntity();
					templateEntity.setTemplateId(rs.getString("template_id"));
					templateEntity.setUserId(rs.getString("user_id"));
					templateEntity.setCoverImg(rs.getString("cover_img"));
					templateEntity.setTemplateName(rs.getString("template_name"));
					templateEntity.setMainColor(rs.getString("main_color"));
					templateEntity.setSummary(rs.getString("summary"));
					if (rs.getDate("createtime") != null) {
						templateEntity.setCreatetime(new java.sql.Date(rs.getTimestamp("createtime").getTime()));
					}
					templateEntity.setTemplateState(TemplateState.valueOf(rs.getInt("template_state")));
					templateEntity.setTemplateContent(rs.getString("template_content"));
					templateEntity.setPublish_state(rs.getInt("publish_state"));
					templateEntity.setTemplateType(rs.getInt("template_type"));
					templateEntity.setVerifyName(rs.getString("verify_name"));
					if (rs.getTimestamp("verify_time") != null) {
						templateEntity.setVerifytime(new java.sql.Date(rs.getTimestamp("verify_time").getTime()));
					}
					if (rs.getTimestamp("shelf_time") != null) {
						templateEntity.setShelftime(new java.sql.Date(rs.getTimestamp("shelf_time").getTime()));
					}
					templateEntity.setBouncedReason(rs.getString("bounced_reason"));
				}
				return templateEntity;
			}
		});
	}

	private Entity queryById(final IdEntity<String> queryTemplate) throws DaoAppException {
		final String sqlScript = "SELECT * FROM prod_template WHERE template_id=?;";
		JdbcTemplate template = null;
		if (queryTemplate == null)
			throw new DaoAppException("请指定查询参数");
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, queryTemplate.getId());
			}
		}, new ResultSetExtractor<TemplateEntity>() {
			public TemplateEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
				TemplateEntity templateEntity = null;
				if (rs.next()) {
					templateEntity = new TemplateEntity();
					templateEntity.setTemplateId(rs.getString("template_id"));
					templateEntity.setUserId(rs.getString("user_id"));
					templateEntity.setCoverImg(rs.getString("cover_img"));
					templateEntity.setTemplateName(rs.getString("template_name"));
					templateEntity.setMainColor(rs.getString("main_color"));
					templateEntity.setSummary(rs.getString("summary"));
					if (rs.getTimestamp("createtime") != null) {
						templateEntity.setCreatetime(new java.sql.Date(rs.getTimestamp("createtime").getTime()));
					}
					templateEntity.setTemplateState(TemplateState.valueOf(rs.getInt("template_state")));
					templateEntity.setTemplateContent(rs.getString("template_content"));
					templateEntity.setPublish_state(rs.getInt("publish_state"));
					templateEntity.setTemplateType(rs.getInt("template_type"));
					templateEntity.setVerifyName(rs.getString("verify_name"));
					if (rs.getTimestamp("verify_time") != null) {
						templateEntity.setVerifytime(new java.sql.Date(rs.getTimestamp("verify_time").getTime()));
					}
					if (rs.getTimestamp("shelf_time") != null) {
						templateEntity.setShelftime(new java.sql.Date(rs.getTimestamp("shelf_time").getTime()));
					}
					templateEntity.setBouncedReason(rs.getString("bounced_reason"));
				}
				return templateEntity;
			}
		});
	}

	/**
	 * 查询模版标签
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private Iterator<Entity> queryDataForTemplateTag(SearchTemplateTagDataEntity entity) throws DaoAppException {
		String SQL = "Select * from (SELECT DISTINCT tag_name,tag_type,user_id FROM prod_template A LEFT JOIN prod_template_tag B ON A.template_id = B.template_id) A WHERE 1=1 AND A.user_id =? AND A.tag_type=? AND A.TAG_NAME IS NOT NULL;";
		final String sqlScript = SQL;
		JdbcTemplate template = null;
		final SearchTemplateTagDataEntity searchTagRequestEntity = entity;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setString(i++, searchTagRequestEntity.getUserId());
				ps.setInt(i++, searchTagRequestEntity.getTag_type());
			}
		}, new RowMapper<Entity>() {
			public TagResponseDataEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
				TagResponseDataEntity tagEntity = null;
				tagEntity = new TagResponseDataEntity();
				tagEntity.setTagName(rs.getString("tag_name"));
				return tagEntity;
			}
		}).iterator();
	}

	/**
	 * 查询使用过的模版
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private Iterator<Entity> queryDataForUsedTemplate(final SearchusedtemplateEntity entity) throws DaoAppException {
//		StringBuilder SQL = new StringBuilder("Select * from (SELECT DISTINCT ");
		StringBuilder SQL = new StringBuilder("SELECT DISTINCT ");
		SQL.append("A.manuscript_id as template_id, ");
		SQL.append("A.user_id, ");
		SQL.append("B.user_id AS currentuser, ");
		SQL.append("p24.parameter as cover_img, ");
		SQL.append("p23.parameter as template_name, ");
		SQL.append("p25.parameter as main_color, ");
		SQL.append("p26.parameter as summary, ");
		SQL.append("A.createtime, ");
		SQL.append("A.state as template_state, ");
		SQL.append("p2.parameter as publish_state, ");
		SQL.append("A.terminal_type as template_type, ");
		SQL.append("p9.parameter as verify_name, ");
		SQL.append("p5.parameter as verify_time, ");
		SQL.append("p6.parameter as shelf_time, ");
		SQL.append("p7.parameter as bounced_reason, ");
		SQL.append("C.user_name, ");
		SQL.append("C.user_phone ");
		SQL.append("FROM ");
		SQL.append("prod_template_uselog B ");
		SQL.append("LEFT JOIN prod_manuscript A ON A.manuscript_id = B.template_id ");
		SQL.append("LEFT JOIN prod_parameter p25 on p25.manuscript_id = a.manuscript_id and p25.parameter_type = 25 ");
		SQL.append("LEFT JOIN prod_parameter p26 on p26.manuscript_id = a.manuscript_id and p26.parameter_type = 26 ");
		SQL.append("LEFT JOIN prod_parameter p24 on p24.manuscript_id = a.manuscript_id and p24.parameter_type = 24 ");
		SQL.append("LEFT JOIN prod_parameter p23 on p23.manuscript_id = a.manuscript_id and p23.parameter_type = 23 ");
		SQL.append("LEFT JOIN prod_parameter p2 on p2.manuscript_id = a.manuscript_id and p2.parameter_type = 2 ");
		SQL.append("LEFT JOIN prod_parameter p9 on p9.manuscript_id = a.manuscript_id and p9.parameter_type = 9 ");
		SQL.append("LEFT JOIN prod_parameter p5 on p5.manuscript_id = a.manuscript_id and p5.parameter_type = 5	");
		SQL.append("LEFT JOIN prod_parameter p6 on p6.manuscript_id = a.manuscript_id and p6.parameter_type = 6 ");
		SQL.append("LEFT JOIN prod_parameter p7 on p7.manuscript_id = a.manuscript_id and p7.parameter_type = 7	");
//		SQL.append("LEFT JOIN user_front_user C ON A.user_id = C.user_id) a  WHERE 1=1 AND a.currentuser =? ");
		SQL.append("LEFT JOIN user_front_user C ON A.user_id = C.user_id  WHERE 1=1 AND B.user_id =? ");
		if (entity.getTemplateType() != null) {
			SQL.append(" AND A.terminal_type =? ");
		}
		SQL.append(" ORDER BY a.CREATETIME DESC LIMIT ?,?;");
		final String sqlScript = SQL.toString();
		JdbcTemplate template = null;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setString(i++, entity.getUserId());
				if (entity.getTemplateType() != null) {
					ps.setInt(i++, entity.getTemplateType());
				}
				ps.setInt(i++, entity.getStartCount());
				ps.setInt(i++, entity.getPageSize());
			}
		}, new RowMapper<Entity>() {
			public SearchusedtemplateDataEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
				SearchusedtemplateDataEntity reslut = null;
				reslut = new SearchusedtemplateDataEntity();
				reslut.setTemplateId(rs.getString("template_id"));
				reslut.setTemplateName(rs.getString("template_name"));
				reslut.setTemplateImg(rs.getString("cover_img"));
				reslut.setTemplateType(rs.getInt("template_type"));
				reslut.setTemplateState(rs.getInt("template_state"));
				reslut.setCreatetime(rs.getTimestamp("createtime"));
				reslut.setPublishState(rs.getInt("publish_state"));
				String userName = rs.getString("user_name");
				if (!Str.isNullOrEmpty(userName)) {
					reslut.setUser(userName);
				} else {
					reslut.setUser(rs.getString("user_phone"));
				}
				reslut.setSummary(rs.getString("summary"));
				// TODO 添加发布路径
				reslut.setPublishedURL("");
				reslut.setVerifyName(rs.getString("verify_name"));
				reslut.setVerifytime(rs.getDate("verify_time"));
				reslut.setShelftime(rs.getDate("shelf_time"));
				reslut.setBouncedReason(rs.getString("bounced_reason"));
				return reslut;
			}
		}).iterator();
	}

	/**
	 * 查询使用过的模版
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int queryDataForUsedTemplateForCount(final SearchusedtemplateEntity entity) throws DaoAppException {
//		StringBuilder SQL = new StringBuilder("Select count(1) as count from (SELECT DISTINCT ");
		StringBuilder SQL = new StringBuilder("Select count( DISTINCT ");
		SQL.append("A.manuscript_id");
//		SQL.append("A.manuscript_id as template_id, ");
//		SQL.append("A.user_id, ");
//		SQL.append("B.user_id AS currentuser, ");
//		SQL.append("p24.parameter as cover_img, ");
//		SQL.append("p23.parameter as template_name, ");
//		SQL.append("p25.parameter as main_color, ");
//		SQL.append("p26.parameter as summary, ");
//		SQL.append("A.createtime, ");
//		SQL.append("A.state as template_state, ");
//		SQL.append("p2.parameter as publish_state, ");
//		SQL.append("A.terminal_type as template_type, ");
//		SQL.append("p9.parameter as verify_name, ");
//		SQL.append("p5.parameter as verify_time, ");
//		SQL.append("p6.parameter as shelf_time, ");
//		SQL.append("p7.parameter as bounced_reason, ");
//		SQL.append("C.user_name,");
//		SQL.append("C.user_phone ");
		SQL.append(" ) as count ");
		SQL.append(" FROM ");
		SQL.append("prod_template_uselog B ");
		SQL.append("LEFT JOIN prod_manuscript A ON A.manuscript_id = B.template_id ");
		SQL.append("LEFT JOIN prod_parameter p25 on p25.manuscript_id = a.manuscript_id and p25.parameter_type = 25 ");
		SQL.append("LEFT JOIN prod_parameter p26 on p26.manuscript_id = a.manuscript_id and p26.parameter_type = 26 ");
		SQL.append("LEFT JOIN prod_parameter p24 on p24.manuscript_id = a.manuscript_id and p24.parameter_type = 24 ");
		SQL.append("LEFT JOIN prod_parameter p23 on p23.manuscript_id = a.manuscript_id and p23.parameter_type = 23 ");
		SQL.append("LEFT JOIN prod_parameter p2 on p2.manuscript_id = a.manuscript_id and p2.parameter_type = 2 ");
		SQL.append("LEFT JOIN prod_parameter p9 on p9.manuscript_id = a.manuscript_id and p9.parameter_type = 9 ");
		SQL.append("LEFT JOIN prod_parameter p5 on p5.manuscript_id = a.manuscript_id and p5.parameter_type = 5	");
		SQL.append("LEFT JOIN prod_parameter p6 on p6.manuscript_id = a.manuscript_id and p6.parameter_type = 6 ");
		SQL.append("LEFT JOIN prod_parameter p7 on p7.manuscript_id = a.manuscript_id and p7.parameter_type = 7	");
//		SQL.append("LEFT JOIN user_front_user C ON A.user_id = C.user_id) a  WHERE 1=1 AND a.currentuser =? ");
		SQL.append("LEFT JOIN user_front_user C ON A.user_id = C.user_id  WHERE 1=1 AND B.user_id =? ");
		if (entity.getTemplateType() != null) {
			SQL.append(" AND A.terminal_type =? ");
		}
		final String sqlScript = SQL.toString();
		JdbcTemplate template = null;
		template = this.getJdbcTemplate();

		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setString(i++, entity.getUserId());
				if (entity.getTemplateType() != null) {
					ps.setInt(i++, entity.getTemplateType());
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

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity == null) {
			throw new DaoAppException(-1, "参数为空");
		}

		if (entity instanceof IdEntity<?>) {
			String template_id = ((IdEntity<String>) entity).getId();
			deleteTag(template_id, TagType.SYS.getValue());
			deleteTag(template_id, TagType.USER.getValue());
			deleteKeyword(template_id);
			deleteTemplateUseLog(template_id);
			return deleteTemplate(template_id);
		}
		return -1;
	}

	/**
	 * 删除 模版
	 * 
	 * @param template_id
	 * @return
	 * @throws DaoAppException
	 */
	private int deleteTemplate(final String template_id) throws DaoAppException {
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (template_id == null)
			return val;
		template = this.getJdbcTemplate();
		holder = new GeneratedKeyHolder();
		final String sqlScript = "DELETE FROM prod_template WHERE template_id = ?;";
		val += template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				statement.setString(1, template_id);
				return statement;
			}
		}, holder);
		return val;
	}

	/**
	 * 删除 模版使用记录
	 * 
	 * @param template_id
	 * @return
	 * @throws DaoAppException
	 */
	private int deleteTemplateUseLog(final String template_id) throws DaoAppException {
		JdbcTemplate template = null;
		KeyHolder holder = null;
		int val = -1;
		if (template_id == null)
			return val;
		template = this.getJdbcTemplate();
		holder = new GeneratedKeyHolder();
		final String sqlScript = "DELETE FROM prod_template_uselog WHERE template_id = ?;";
		val += template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				statement.setString(1, template_id);
				return statement;
			}
		}, holder);
		return val;
	}
}
