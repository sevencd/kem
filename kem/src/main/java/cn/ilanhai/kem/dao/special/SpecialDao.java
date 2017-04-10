package cn.ilanhai.kem.dao.special;

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
import org.springframework.jdbc.InvalidResultSetAccessException;
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
import cn.ilanhai.kem.domain.enums.EnableState;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ActivePluginEntity;
import cn.ilanhai.kem.domain.special.AuditLogEntity;
import cn.ilanhai.kem.domain.special.ConfigActiveEntity;
import cn.ilanhai.kem.domain.special.ConfigEntity;
import cn.ilanhai.kem.domain.special.ConfigKeywordEntity;
import cn.ilanhai.kem.domain.special.ConfigTagEntity;
import cn.ilanhai.kem.domain.special.ModelConfigEntity;
import cn.ilanhai.kem.domain.special.SearchSpecialRequestEntity;
import cn.ilanhai.kem.domain.special.SpecialEntity;
import cn.ilanhai.kem.domain.template.TagResponseDataEntity;

/**
 * 专题数据访问
 * 
 * @author he
 *
 */
@Component("specialDao")
public class SpecialDao extends BaseDao {
	public SpecialDao() {
		super();
	}

	private Logger logger = Logger.getLogger(SpecialDao.class);

	@Override
	public int save(Entity entity) throws DaoAppException {
		try {
			if (entity instanceof SpecialEntity) {
				SpecialEntity specialEntity = (SpecialEntity) entity;
				if (specialEntity.getSpecialId() != null) {
					if (this.querySpecialModel(specialEntity.getSpecialId()) != null) {
						return this.updateSpecialModel(specialEntity);
					} else {
						return this.saveSpecialModel(specialEntity);
					}
				}
			} else if (entity instanceof ConfigTagEntity) {
				this.saveTagModel((ConfigTagEntity) entity);
			} else if (entity instanceof ConfigKeywordEntity) {
				this.saveKeyWordModel((ConfigKeywordEntity) entity);
			} else if (entity instanceof ConfigEntity) {
				this.saveConfigModel((ConfigEntity) entity);
			} else if (entity instanceof ModelConfigEntity) {
				this.saveModelConfig((ModelConfigEntity) entity);
			} else if (entity instanceof ConfigActiveEntity) {
				this.saveActiveConfigModel((ConfigActiveEntity) entity);
			} else if (entity instanceof AuditLogEntity) {
				this.saveAuditLogModel((AuditLogEntity) entity);
			}
		} catch (DaoAppException e) {
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof SearchSpecialRequestEntity) {
			return this.querySpecialModel((SearchSpecialRequestEntity) entity);
		} else if (entity instanceof IdEntity) {
			return queryDataForTemplateTag((IdEntity<String>) entity);
		}

		return null;
	}

	/**
	 * 查询模版标签
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private Iterator<Entity> queryDataForTemplateTag(IdEntity<String> entity) throws DaoAppException {
		String SQL = "SELECT DISTINCT ST.tag_name"
				+ " FROM prod_special S LEFT JOIN model_config MC ON S.special_id = MC.model_id AND MC.model_type = 1 LEFT JOIN special_config AS SC ON SC.model_config_id = MC.model_config_id"
				+ " LEFT JOIN special_config_tag ST ON ST.model_config_id = MC.model_config_id LEFT JOIN user_front_user U ON s.user_id = u.user_id WHERE 1=1 AND s.user_id =? AND ST.TAG_NAME IS NOT NULL;";
		final String sqlScript = SQL;
		JdbcTemplate template = null;
		final IdEntity<String> idDto = entity;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				ps.setString(i++, idDto.getId());
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

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		try {
			if (entity instanceof SearchSpecialRequestEntity) {
				return this.querySpecialModelForCount((SearchSpecialRequestEntity) entity);
			} else if (entity instanceof IdEntity) {
				@SuppressWarnings("unchecked")
				IdEntity<String> specialId = (IdEntity<String>) entity;
				return this.querySpecialModel(specialId.getId());
			} else if (entity instanceof ActivePluginEntity) {
				return this.queryActiveConfigModel(((ActivePluginEntity) entity).getRelationId());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof ConfigTagEntity) {
			return this.deleteTagModel(((ConfigTagEntity) entity).getModelConfigId());
		} else if (entity instanceof ConfigKeywordEntity) {
			return this.deleteKeyWordModel(((ConfigKeywordEntity) entity).getModelConfigId());
		} else if (entity instanceof IdEntity) {
			return this.deleteSpecialModel(((IdEntity<String>) entity).getId());
		} else if (entity instanceof ModelConfigEntity) {
			return this.deleteModelConfig((ModelConfigEntity) entity);
		} else if (entity instanceof ConfigEntity) {
			return this.deleteConfigModel(((ConfigEntity) entity).getModelConfigId());
		} else if (entity instanceof ConfigActiveEntity) {
			this.deleteActiveConfigModel(((ConfigActiveEntity) entity).getSpecialId());
		}
		return 0;
	}

	/**
	 * 保存专题模型
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveSpecialModel(final SpecialEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO prod_special (special_id, special_name, template_id, user_id, createtime, "
				+ "cover_img, context, summary, publish_time, publish_name, publish_state, model_config_id, active_type, "
				+ "active_config, special_state, special_type, extension_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setString(i++, entity.getSpecialId());
				statement.setString(i++, entity.getSpecialName());
				statement.setString(i++, entity.getTemplateId());
				statement.setObject(i++, entity.getUserId());
				statement.setTimestamp(i++, new Timestamp(entity.getCreatetime().getTime()));
				statement.setString(i++, entity.getCoverImg());
				statement.setString(i++, entity.getContext());
				statement.setString(i++, entity.getSummary());
				if (entity.getPublishTime() == null) {
					statement.setTimestamp(i++, null);
				} else {
					statement.setTimestamp(i++, new Timestamp(entity.getPublishTime().getTime()));
				}
				statement.setString(i++, entity.getPublishName());
				statement.setObject(i++, entity.getPublishState());
				statement.setObject(i++, entity.getModelConfigId());
				statement.setString(i++, entity.getActiveType());
				statement.setObject(i++, entity.getActiveConfig());
				statement.setObject(i++, entity.getSpecialState());
				statement.setObject(i++, entity.getSpecialType());
				statement.setObject(i++, entity.getExtensionId());
				logger.info(statement.toString());
				System.out.println(statement.toString());
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 删除关键字
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int deleteSpecialModel(final String specialId) throws DaoAppException {
		ModelConfigEntity modelConfigEntity = this.queryModelConfig(specialId, ManuscriptType.SPECIAL.getValue());
		if (modelConfigEntity == null) {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM  prod_special  WHERE special_id = ?");
			List<Object> params = new ArrayList<Object>();
			params.add(specialId);
			return this.execUpdate(sql.toString(), params.toArray());
		}
		Integer modelConfigId = modelConfigEntity.getModelConfigId();
		this.deleteActiveConfigModel(specialId);
		this.deleteConfigModel(modelConfigId);
		this.deleteKeyWordModel(modelConfigId);
		this.deleteTagModel(modelConfigId);
		this.deleteModelConfig(modelConfigEntity);

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  prod_special  WHERE special_id = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(specialId);
		return this.execUpdate(sql.toString(), params.toArray());
	}

	/**
	 * 根据id刷新值
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int updateSpecialModel(final SpecialEntity entity) throws DaoAppException {
		final String sqlScript = "UPDATE prod_special SET special_name=?, template_id=?, user_id=?, createtime=?, cover_img=?, context=?, summary=?, publish_time=?, publish_name=?, publish_state=?, model_config_id=?, active_type=?, active_config=?, special_state=?, special_type=?, extension_id=? ,manuscript_id = ? WHERE special_id=?;";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setString(i++, entity.getSpecialName());
				statement.setString(i++, entity.getTemplateId());
				statement.setObject(i++, entity.getUserId());
				statement.setTimestamp(i++, new Timestamp(entity.getCreatetime().getTime()));
				statement.setString(i++, entity.getCoverImg());
				statement.setString(i++, entity.getContext());
				statement.setString(i++, entity.getSummary());
				if (entity.getPublishTime() == null) {
					statement.setTimestamp(i++, null);
				} else {
					statement.setTimestamp(i++, new Timestamp(entity.getPublishTime().getTime()));
				}
				statement.setString(i++, entity.getPublishName());
				statement.setObject(i++, entity.getPublishState());
				statement.setObject(i++, entity.getModelConfigId());
				statement.setString(i++, entity.getActiveType());
				statement.setObject(i++, entity.getActiveConfig());
				statement.setObject(i++, entity.getSpecialState());
				statement.setObject(i++, entity.getSpecialType());
				statement.setObject(i++, entity.getExtensionId());
				statement.setObject(i++, entity.getManuscriptId());
				statement.setString(i++, entity.getSpecialId());
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 查询
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private ModelConfigEntity queryModelConfig(final String model_id, Integer model_type) throws DaoAppException {
		final String sqlScript = "SELECT * FROM  model_config  WHERE model_id = ? AND model_type = ?;";
		List<Object> params = new ArrayList<Object>();
		params.add(model_id);
		params.add(model_type);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(sqlScript, params.toArray());
		if (sqlRowSet.next()) {
			ModelConfigEntity modelConfigEntity = new ModelConfigEntity();
			modelConfigEntity.setModelConfigId(sqlRowSet.getInt("model_config_id"));
			modelConfigEntity.setModelId(sqlRowSet.getString("model_id"));
			modelConfigEntity.setModelType(sqlRowSet.getInt("model_type"));
			return modelConfigEntity;
		}
		return null;

	}

	/**
	 * 查询
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int deleteModelConfig(final ModelConfigEntity modelConfigEntity) throws DaoAppException {
		if (modelConfigEntity == null) {
			return 0;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  model_config  WHERE 1=1");
		List<Object> params = new ArrayList<Object>();
		if (modelConfigEntity.getModelConfigId() != null) {
			sql.append(" AND model_config_id =?");
			params.add(modelConfigEntity.getModelConfigId());
		} else if (!Str.isNullOrEmpty(modelConfigEntity.getModelId()) && modelConfigEntity.getModelType() != null) {
			sql.append(" AND model_id ? AND model_type = ?");
			params.add(modelConfigEntity.getModelId());
			params.add(modelConfigEntity.getModelType());
		} else {
			return 0;
		}
		return this.execUpdate(sql.toString(), params.toArray());

	}

	/**
	 * 保存模块配置
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveModelConfig(final ModelConfigEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO model_config (model_id, model_type) VALUES (?, ?);";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setString(i++, entity.getModelId());
				statement.setInt(i++, entity.getModelType());
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		int val = this.execUpdate(preparedStatementCreator, holder);
		if (val > 0)
			entity.setModelConfigId(holder.getKey().intValue());
		return val;
	}

	/**
	 * 保存关键字
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveKeyWordModel(final ConfigKeywordEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO special_config_keyword (model_config_id, keyword) VALUES (?, ?);";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setInt(i++, entity.getModelConfigId());
				statement.setString(i++, entity.getKeyword());
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 查询关键字
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private List<ConfigKeywordEntity> queryKeyWordModel(final Integer modelConfigId) throws DaoAppException {
		final String sqlScript = "SELECT DISTINCT * FROM special_config_keyword WHERE model_config_id = ?;";
		List<Object> params = new ArrayList<Object>();
		params.add(modelConfigId);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(sqlScript, params.toArray());
		List<ConfigKeywordEntity> configKeywordEntitys = new ArrayList<ConfigKeywordEntity>();
		while (sqlRowSet.next()) {
			ConfigKeywordEntity configKeywordEntity = new ConfigKeywordEntity();
			configKeywordEntity.setKeywordId(sqlRowSet.getInt("keyword_id"));
			configKeywordEntity.setModelConfigId(sqlRowSet.getInt("model_config_id"));
			configKeywordEntity.setKeyword(sqlRowSet.getString("keyword"));
			configKeywordEntitys.add(configKeywordEntity);
		}
		return configKeywordEntitys;
	}

	/**
	 * 删除关键字
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int deleteKeyWordModel(final Integer modelConfigId) throws DaoAppException {
		final String sqlScript = "DELETE FROM special_config_keyword WHERE model_config_id = ?;";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setInt(i++, modelConfigId);
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 保存标签
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveTagModel(final ConfigTagEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO special_config_tag (model_config_id, tag_name) VALUES (?, ?);";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setInt(i++, entity.getModelConfigId());
				statement.setString(i++, entity.getTag());
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 查询标签
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private List<ConfigTagEntity> queryTagModel(final String modelConfigId) throws DaoAppException {
		final String sqlScript = "SELECT DISTINCT * FROM prod_parameter WHERE manuscript_id = ? and parameter_type = 1;";
		List<Object> params = new ArrayList<Object>();
		params.add(modelConfigId);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(sqlScript, params.toArray());
		List<ConfigTagEntity> configTagEntitys = new ArrayList<ConfigTagEntity>();
		while (sqlRowSet.next()) {
			ConfigTagEntity configTagEntity = new ConfigTagEntity();
			configTagEntity.setTagId(sqlRowSet.getInt("parameter_id"));
			configTagEntity.setTag(sqlRowSet.getString("parameter"));
			configTagEntitys.add(configTagEntity);
		}
		return configTagEntitys;
	}

	/**
	 * 删除标签
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int deleteTagModel(final Integer modelConfigId) throws DaoAppException {
		final String sqlScript = "DELETE FROM special_config_tag WHERE model_config_id = ?;";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setInt(i++, modelConfigId);
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 保存审核记录
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveAuditLogModel(final AuditLogEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO audit_log (model_config_id, disable_reason,createtime) VALUES (?, ?,?);";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setInt(i++, entity.getModelConfigId());
				statement.setString(i++, entity.getDisable_reason());
				statement.setTimestamp(i++, new Timestamp(entity.getCreatetime().getTime()));
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 查询审核记录
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private List<AuditLogEntity> queryAuditLogModel(final Integer modelConfigId) throws DaoAppException {
		final String sqlScript = "SELECT DISTINCT * FROM audit_log WHERE model_config_id = ?;";
		List<Object> params = new ArrayList<Object>();
		params.add(modelConfigId);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(sqlScript, params.toArray());
		List<AuditLogEntity> auditLogEntitys = new ArrayList<AuditLogEntity>();
		while (sqlRowSet.next()) {
			AuditLogEntity auditLogEntity = new AuditLogEntity();
			auditLogEntity.setAuditId(sqlRowSet.getInt("audit_id"));
			auditLogEntity.setModelConfigId(sqlRowSet.getInt("model_config_id"));
			auditLogEntity.setDisable_reason(sqlRowSet.getString("disable_reason"));
			auditLogEntity.setCreatetime(sqlRowSet.getTimestamp("createtime"));
			auditLogEntitys.add(auditLogEntity);
		}
		return auditLogEntitys;
	}

	/**
	 * 删除审核记录
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int deleteAuditLogModel(final Integer modelConfigId) throws DaoAppException {
		final String sqlScript = "DELETE FROM audit_log WHERE model_config_id = ?;";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setInt(i++, modelConfigId);
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 保存活动设置
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveActiveConfigModel(final ConfigActiveEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO special_config_active (special_id, start_time, end_time) VALUES (?, ?, ?);";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setString(i++, entity.getSpecialId());
				if (entity.getStartTime() == null) {
					statement.setTimestamp(i++, null);
				} else {
					statement.setTimestamp(i++, new Timestamp(entity.getStartTime().getTime()));
				}
				if (entity.getEndTime() == null) {
					statement.setTimestamp(i++, null);
				} else {
					statement.setTimestamp(i++, new Timestamp(entity.getEndTime().getTime()));
				}
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 查询活动设置
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private ConfigActiveEntity queryActiveConfigModel(final String specialId) throws DaoAppException {
		final String sqlScript = "SELECT DISTINCT * FROM special_config_active WHERE special_id = ?;";
		List<Object> params = new ArrayList<Object>();
		params.add(specialId);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(sqlScript, params.toArray());
		if (sqlRowSet.next()) {
			ConfigActiveEntity configActiveEntity = new ConfigActiveEntity();
			configActiveEntity.setActiveId(sqlRowSet.getInt("active_id"));
			configActiveEntity.setSpecialId(sqlRowSet.getString("special_id"));
			configActiveEntity.setStartTime(sqlRowSet.getTimestamp("start_time"));
			configActiveEntity.setEndTime(sqlRowSet.getTimestamp("end_time"));
			return configActiveEntity;
		}
		return null;
	}

	/**
	 * 删除活动设置
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int deleteActiveConfigModel(final String specialId) throws DaoAppException {
		final String sqlScript = "DELETE FROM special_config_active WHERE special_id = ?;";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setString(i++, specialId);
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 保存发布设置
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveConfigModel(final ConfigEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO special_config (model_config_id, start_time, end_time, main_color) VALUES (?, ?, ?, ?);";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setInt(i++, entity.getModelConfigId());
				if (entity.getStartTime() == null) {
					statement.setTimestamp(i++, null);
				} else {
					statement.setTimestamp(i++, new Timestamp(entity.getStartTime().getTime()));
				}
				if (entity.getEndTime() == null) {
					statement.setTimestamp(i++, null);
				} else {
					statement.setTimestamp(i++, new Timestamp(entity.getEndTime().getTime()));
				}
				// statement.setTimestamp(i++, new
				// Timestamp(entity.getStartTime().getTime()));
				// statement.setTimestamp(i++, new
				// Timestamp(entity.getEndTime().getTime()));
				statement.setString(i++, entity.getMainColor());
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 查询发布设置
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private ConfigEntity queryConfigModel(final Integer modelConfigId) throws DaoAppException {
		final String sqlScript = "SELECT DISTINCT * FROM special_config WHERE model_config_id = ?;";
		List<Object> params = new ArrayList<Object>();
		params.add(modelConfigId);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(sqlScript, params.toArray());
		if (sqlRowSet.next()) {
			ConfigEntity configEntity = new ConfigEntity();
			configEntity.setConfigId(sqlRowSet.getInt("config_id"));
			configEntity.setModelConfigId(sqlRowSet.getInt("model_config_id"));
			configEntity.setStartTime(sqlRowSet.getTimestamp("start_time"));
			configEntity.setEndTime(sqlRowSet.getTimestamp("end_time"));
			configEntity.setMainColor(sqlRowSet.getString("main_color"));
			return configEntity;
		}
		return null;
	}

	/**
	 * 删除发布设置
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int deleteConfigModel(final Integer modelConfigId) throws DaoAppException {
		final String sqlScript = "DELETE FROM special_config WHERE model_config_id = ?;";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setInt(i++, modelConfigId);
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 查询专题
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 * @throws SQLException
	 */
	private SpecialEntity querySpecialModel(final String specialId) throws DaoAppException, SQLException {
		final String sqlScript = "SELECT DISTINCT S.*,U.*,S.model_config_id AS MCMODELCONFIGID FROM prod_special S LEFT JOIN model_config MC ON S.special_id = MC.model_id AND MC.model_type = "
				+ ManuscriptType.SPECIAL.getValue()
				+ " LEFT JOIN user_front_user U ON s.user_id = u.user_id WHERE special_id = ?;";
		List<Object> params = new ArrayList<Object>();
		params.add(specialId);
		SpecialEntity specialEntity = getSpecialModel(this.execQueryForRowSet(sqlScript, params.toArray()));
		if (specialEntity != null) {
			Integer modelConfigId = specialEntity.getModelConfigId();
			specialEntity.setAuditLog(this.queryAuditLogModel(modelConfigId));
			specialEntity.setConfig(this.queryConfigModel(modelConfigId));
			specialEntity.setConfigActive(this.queryActiveConfigModel(specialEntity.getSpecialId()));
			specialEntity.setConfigKeywords(this.queryKeyWordModel(modelConfigId));
			specialEntity.setConfigTags(this.queryTagModel(specialEntity.getSpecialId()));
		}
		return specialEntity;
	}

	/**
	 * 查询专题
	 * 
	 * @param entity
	 * @return
	 */
	private Iterator<Entity> querySpecialModel(final SearchSpecialRequestEntity entity) {
		String SQL = "SELECT * FROM (SELECT DISTINCT S.special_id,S.special_name,S.template_id,S.user_id,S.createtime,S.cover_img,S.summary,S.publish_time,S.publish_name,S.publish_state,S.model_config_id,S.active_type,S.active_config,S.special_state,S.special_type,S.extension_id,U.user_name,"
				+ "u.user_phone"
				+ ",SC.end_time AS SCENDTIME,SC.main_color,SC.start_time AS SCSTARTTIME,S.model_config_id AS MCMODELCONFIGID ,S.manuscript_id, pm.enable"
				+ " FROM prod_special S LEFT JOIN model_config MC ON S.special_id = MC.model_id AND MC.model_type = "
				+ ManuscriptType.SPECIAL.getValue()
				+ " LEFT JOIN special_config AS SC ON SC.model_config_id = MC.model_config_id"
				+ " LEFT JOIN special_config_tag ST ON ST.model_config_id = MC.model_config_id ";
				/**
				 * 标签名称
				 */
				if (!Str.isNullOrEmpty(entity.getTagName())) {
					SQL += " AND ST.tag_name = ?";
				}
					
				SQL+= "LEFT JOIN user_front_user U ON s.user_id = u.user_id LEFT JOIN prod_manuscript pm on S.manuscript_id = pm.manuscript_id ) S WHERE 1=1";
		SQL = specialSearchSQLadd(entity, SQL);
		SQL += " ORDER BY S.CREATETIME DESC LIMIT ?,? ;";

		final String sqlScript = SQL;
		JdbcTemplate template = null;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				/**
				 * 标签名称
				 */
				if (!Str.isNullOrEmpty(entity.getTagName())) {
					ps.setString(i++, entity.getTagName());
				}
				ps.setString(i++, entity.getUserId());
				/**
				 * 专题名
				 */
				if (!Str.isNullOrEmpty(entity.getSpecialName())) {
					ps.setString(i++, "%" + entity.getSpecialName() + "%");
				}
				/**
				 * 专题类型
				 */
				if (entity.getSpecialType() != null) {
					ps.setInt(i++, entity.getSpecialType());
				}
				/**
				 * 专题状态
				 */
				if (entity.getSpecialState() != null) {
					ps.setInt(i++, entity.getSpecialState());
				}

				/**
				 * 起始时间
				 */
				if (entity.getTimeStart() != null) {
					ps.setTimestamp(i++, new Timestamp(entity.getTimeStart().getTime()));
				}
				/**
				 * 结束时间
				 */
				if (entity.getTimeEnd() != null) {
					ps.setTimestamp(i++, new Timestamp(entity.getTimeEnd().getTime()));
				}
				ps.setInt(i++, entity.getStartCount());
				ps.setInt(i++, entity.getPageSize());
			}
		}, new RowMapper<Entity>() {
			public SpecialEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
				SpecialEntity specialEntity = getSpecialModel(rs);
				return specialEntity;
			}
		}).iterator();
	}

	/**
	 * 封装专题
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private SpecialEntity getSpecialModel(ResultSet rs) throws SQLException {
		SpecialEntity specialEntity = new SpecialEntity();
		specialEntity.setSpecialId(rs.getString("special_id"));
		specialEntity.setCreatetime(rs.getTimestamp("createtime"));
		// specialEntity.setContext(rs.getString("context"));
		specialEntity.setCoverImg(rs.getString("cover_img"));
		specialEntity.setModelConfigId(rs.getInt("MCMODELCONFIGID"));
		specialEntity.setPublishName(rs.getString("publish_name"));
		specialEntity.setPublishTime(rs.getTimestamp("publish_time"));
		specialEntity.setPublishState(rs.getInt("publish_state"));
		specialEntity.setActiveType(rs.getString("active_type"));
		specialEntity.setActiveConfig(rs.getInt("active_config"));
		specialEntity.setSpecialName(rs.getString("special_name"));
		specialEntity.setTemplateId(rs.getString("template_id"));
		specialEntity.setSummary(rs.getString("summary"));
		specialEntity.setSpecialState(rs.getInt("special_state"));
		specialEntity.setSpecialType(rs.getInt("special_type"));
		specialEntity.setUserId(rs.getString("user_id"));
		specialEntity.setExtensionId(rs.getString("extension_id"));
		if (Str.isNullOrEmpty(rs.getString("manuscript_id"))) {
			specialEntity.setManuscriptEnable(false);
		} else {
			if (EnableState.enable.getValue().equals(rs.getInt("enable"))) {
				specialEntity.setManuscriptEnable(true);
			} else {
				specialEntity.setManuscriptEnable(false);
			}
			specialEntity.setManuscriptId(rs.getString("manuscript_id"));
		}
		String userName = rs.getString("user_name");
		if (!Str.isNullOrEmpty(userName)) {
			specialEntity.setUser(userName);
		} else {
			specialEntity.setUser(rs.getString("user_phone"));
		}
		return specialEntity;
	}

	/**
	 * 封装专题
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private SpecialEntity getSpecialModel(SqlRowSet rs) throws SQLException {
		if (rs.next()) {
			SpecialEntity specialEntity = new SpecialEntity();
			specialEntity.setSpecialId(rs.getString("special_id"));
			specialEntity.setCreatetime(rs.getTimestamp("createtime"));
			specialEntity.setContext(rs.getString("context"));
			specialEntity.setCoverImg(rs.getString("cover_img"));
			specialEntity.setModelConfigId(rs.getInt("MCMODELCONFIGID"));
			specialEntity.setPublishName(rs.getString("publish_name"));
			specialEntity.setPublishTime(rs.getTimestamp("publish_time"));
			specialEntity.setPublishState(rs.getInt("publish_state"));
			specialEntity.setActiveType(rs.getString("active_type"));
			specialEntity.setActiveConfig(rs.getInt("active_config"));
			specialEntity.setSpecialName(rs.getString("special_name"));
			specialEntity.setTemplateId(rs.getString("template_id"));
			specialEntity.setSummary(rs.getString("summary"));
			specialEntity.setSpecialState(rs.getInt("special_state"));
			specialEntity.setSpecialType(rs.getInt("special_type"));
			specialEntity.setUserId(rs.getString("user_id"));
			specialEntity.setExtensionId(rs.getString("extension_id"));
			specialEntity.setManuscriptId(rs.getString("manuscript_id"));
			String userName = rs.getString("user_name");
			if (!Str.isNullOrEmpty(userName)) {
				specialEntity.setUser(userName);
			} else {
				specialEntity.setUser(rs.getString("user_phone"));
			}
			return specialEntity;
		}
		return null;
	}

	/**
	 * 查询专题总行数
	 * 
	 * @param entity
	 * @return
	 * @throws InvalidResultSetAccessException
	 * @throws DaoAppException
	 */
	private Entity querySpecialModelForCount(final SearchSpecialRequestEntity entity)
			throws InvalidResultSetAccessException, DaoAppException {
		String SQL = "SELECT COUNT(1) AS COUNT FROM (SELECT DISTINCT S.special_id,S.special_name,S.template_id,S.user_id,S.createtime,S.cover_img,S.summary,S.publish_time,S.publish_name,S.publish_state,S.model_config_id,S.active_type,S.active_config,S.special_state,S.special_type,S.extension_id,U.user_name,"
				+ "u.user_phone"
				+ ",SC.end_time AS SCENDTIME,SC.main_color,SC.start_time AS SCSTARTTIME,S.model_config_id AS MCMODELCONFIGID ,S.manuscript_id, pm.enable"
				+ " FROM prod_special S LEFT JOIN model_config MC ON S.special_id = MC.model_id AND MC.model_type = "
				+ ManuscriptType.SPECIAL.getValue()
				+ " LEFT JOIN special_config AS SC ON SC.model_config_id = MC.model_config_id"
				+ " LEFT JOIN special_config_tag ST ON ST.model_config_id = MC.model_config_id ";
				/**
				 * 标签名称
				 */
				if (!Str.isNullOrEmpty(entity.getTagName())) {
					SQL += " AND ST.tag_name = ?";
				}
				
				SQL += "LEFT JOIN user_front_user U ON s.user_id = u.user_id LEFT JOIN prod_manuscript pm on S.manuscript_id = pm.manuscript_id ) S WHERE 1=1";
		SQL = specialSearchSQLadd(entity, SQL);
		final String sqlScript = SQL;
		JdbcTemplate template = null;
		template = this.getJdbcTemplate();
		return template.query(sqlScript, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				/**
				 * 标签名称
				 */
				if (!Str.isNullOrEmpty(entity.getTagName())) {
					ps.setString(i++, entity.getTagName());
				}
				ps.setString(i++, entity.getUserId());
				/**
				 * 专题名
				 */
				if (!Str.isNullOrEmpty(entity.getSpecialName())) {
					ps.setString(i++, "%" + entity.getSpecialName() + "%");
				}
				/**
				 * 专题类型
				 */
				if (entity.getSpecialType() != null) {
					ps.setInt(i++, entity.getSpecialType());
				}
				/**
				 * 专题状态
				 */
				if (entity.getSpecialState() != null) {
					ps.setInt(i++, entity.getSpecialState());
				}

				/**
				 * 起始时间
				 */
				if (entity.getTimeStart() != null) {
					ps.setTimestamp(i++, new Timestamp(entity.getTimeStart().getTime()));
				}
				/**
				 * 结束时间
				 */
				if (entity.getTimeEnd() != null) {
					ps.setTimestamp(i++, new Timestamp(entity.getTimeEnd().getTime()));
				}
			}
		}, new ResultSetExtractor<CountDto>() {
			public CountDto extractData(ResultSet rs) throws SQLException, DataAccessException {
				CountDto countDto = new CountDto();
				if (rs.next()) {
					countDto.setCount(rs.getInt("Count"));
				}
				return countDto;
			}
		});
	}

	private String specialSearchSQLadd(final SearchSpecialRequestEntity entity, String SQL) {
		SQL += " AND S.user_id =?";

		/**
		 * 专题名
		 */
		if (!Str.isNullOrEmpty(entity.getSpecialName())) {
			SQL += " AND S.special_name like ?";
		}
		/**
		 * 专题类型
		 */
		if (entity.getSpecialType() != null) {
			SQL += " AND S.special_type = ?";
		}
		/**
		 * 专题状态
		 */
		if (entity.getSpecialState() != null) {
			SQL += " AND S.special_state = ?";
		}

		/**
		 * 起始时间
		 */
		if (entity.getTimeStart() != null) {
			SQL += " AND S.createtime >=?";
		}
		/**
		 * 结束时间
		 */
		if (entity.getTimeEnd() != null) {
			SQL += " AND S.createtime <=DATE_ADD(?,INTERVAL 1 DAY)";
		}
		return SQL;
	}
}
