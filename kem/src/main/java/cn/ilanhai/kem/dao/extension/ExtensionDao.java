package cn.ilanhai.kem.dao.extension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
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
import cn.ilanhai.kem.domain.extension.ExtensionEntity;
import cn.ilanhai.kem.domain.extension.SearchExtensionRequestEntity;
import cn.ilanhai.kem.domain.special.AuditLogEntity;
import cn.ilanhai.kem.domain.special.ConfigEntity;
import cn.ilanhai.kem.domain.special.ConfigKeywordEntity;
import cn.ilanhai.kem.domain.special.ConfigTagEntity;
import cn.ilanhai.kem.domain.special.ModelConfigEntity;

/**
 * 推广数据访问
 * 
 * @author he
 *
 */
@Component("extensionDao")
public class ExtensionDao extends BaseDao {
	public ExtensionDao() {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		try {
			if (entity instanceof ExtensionEntity) {
				ExtensionEntity ExtensionEntity = (ExtensionEntity) entity;
				if (ExtensionEntity.getExtensionId() != null) {
					if (this.queryExtensionModel(ExtensionEntity.getExtensionId()) != null) {
						return this.updateExtensionModel(ExtensionEntity);
					} else {
						return this.saveExtensionModel(ExtensionEntity);
					}
				}
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
		try {
			if (entity instanceof SearchExtensionRequestEntity) {
				return this.queryExtensionModel((SearchExtensionRequestEntity) entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		try {
			if (entity instanceof SearchExtensionRequestEntity) {
				return this.querySpecialModelForCount((SearchExtensionRequestEntity) entity);
			} else if (entity instanceof IdEntity) {
				IdEntity<String> extensionId = (IdEntity<String>) entity;
				return this.queryExtensionModel(extensionId.getId());
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
		if (entity instanceof IdEntity) {
			IdEntity<String> extensionId = (IdEntity<String>) entity;
			return this.deleteExtensionModel(extensionId.getId());
		}
		return 0;
	}

	private int deleteExtensionModel(String extensionId) throws DaoAppException {
		// ModelConfigEntity modelConfigEntity =
		// this.queryModelConfig(extensionId, ModelType.EXTENSION.getValue());
		// this.deleteModelConfig(modelConfigEntity);
		// this.deleteAuditLogModel(modelConfigEntity.getModelConfigId());
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  prod_extension  WHERE extension_id = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(extensionId);
		return this.execUpdate(sql.toString(), params.toArray());
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
	 * 保存推广模型
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveExtensionModel(final ExtensionEntity entity) throws DaoAppException {
		final String sqlScript = "INSERT INTO prod_extension (extension_id, extension_name, extension_img, special_id, special_name, publish_time, model_config_id, extension_url, context, extension_type, extension_state, createtime, user_id,summary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setString(i++, entity.getExtensionId());
				statement.setString(i++, entity.getExtensionName());
				statement.setString(i++, entity.getExtensionImg());
				statement.setObject(i++, entity.getSpecialId());
				statement.setString(i++, entity.getSpecialName());
				statement.setTimestamp(i++, new Timestamp(entity.getPublishTime().getTime()));
				statement.setObject(i++, entity.getModelConfigId());
				statement.setString(i++, entity.getExtensionUrl());
				statement.setString(i++, entity.getContext());
				statement.setObject(i++, entity.getExtensionType());
				statement.setObject(i++, entity.getExtensionState());
				statement.setTimestamp(i++, new Timestamp(entity.getCreatetime().getTime()));
				statement.setObject(i++, entity.getUserId());
				statement.setString(i++, entity.getSummary());
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 根据id刷新值
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int updateExtensionModel(final ExtensionEntity entity) throws DaoAppException {
		final String sqlScript = "UPDATE prod_extension SET  extension_name=?, extension_img=?, special_id=?, special_name=?, publish_time=?, model_config_id=?, extension_url=?, context=?, extension_type=?, extension_state=?, createtime=?, user_id=?, summary=? ,manuscript_id = ? WHERE extension_id =?;";
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = null;
				statement = con.prepareStatement(sqlScript);
				int i = 1;
				statement.setString(i++, entity.getExtensionName());
				statement.setString(i++, entity.getExtensionImg());
				statement.setObject(i++, entity.getSpecialId());
				statement.setString(i++, entity.getSpecialName());
				statement.setTimestamp(i++, new Timestamp(entity.getPublishTime().getTime()));
				statement.setObject(i++, entity.getModelConfigId());
				statement.setString(i++, entity.getExtensionUrl());
				statement.setString(i++, entity.getContext());
				statement.setObject(i++, entity.getExtensionType());
				statement.setObject(i++, entity.getExtensionState());
				statement.setTimestamp(i++, new Timestamp(entity.getCreatetime().getTime()));
				statement.setObject(i++, entity.getUserId());
				statement.setString(i++, entity.getSummary());
				statement.setObject(i++, entity.getManuscriptId());
				statement.setString(i++, entity.getExtensionId());
				return statement;
			}
		};
		KeyHolder holder = new GeneratedKeyHolder();
		return this.execUpdate(preparedStatementCreator, holder);
	}

	/**
	 * 查询推广
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 * @throws SQLException
	 */
	private ExtensionEntity queryExtensionModel(final String extensionId) throws DaoAppException, SQLException {
		final String sqlScript = "SELECT DISTINCT S.*,U.*,S.model_config_id AS MCMODELCONFIGID,ST.disable_reason FROM prod_extension S LEFT JOIN model_config MC ON S.extension_id = MC.model_id AND MC.model_type = "+ManuscriptType.EXTENSION.getValue()+" LEFT JOIN user_front_user U ON s.user_id = u.user_id LEFT JOIN audit_log ST ON ST.model_config_id = MC.model_config_id WHERE S.extension_id = ?;";
		List<Object> params = new ArrayList<Object>();
		params.add(extensionId);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(sqlScript, params.toArray());
		if (sqlRowSet.next()) {
			ExtensionEntity extensionEntity = buildExtensionModel(sqlRowSet);
			return extensionEntity;
		} else {
			return null;
		}
	}

	/**
	 * 查询推广
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 * @throws SQLException
	 */
	private Iterator<Entity> queryExtensionModel(final SearchExtensionRequestEntity entity)
			throws DaoAppException, SQLException {
		StringBuilder SQL = new StringBuilder(
				"SELECT DISTINCT S.extension_id,S.extension_name,S.extension_img,S.special_id,S.special_name,S.publish_time,S.extension_url,S.extension_type,S.extension_state,S.createtime,S.user_id,S.summary,U.*,S.model_config_id AS MCMODELCONFIGID,ST.disable_reason,sc.*,ifnull(sc.start_time,S.publish_time) as orderTime,S.manuscript_id, pm.enable FROM prod_extension S LEFT JOIN model_config MC ON S.extension_id = MC.model_id AND MC.model_type = "+ManuscriptType.EXTENSION.getValue()+" LEFT JOIN user_front_user U ON s.user_id = u.user_id LEFT JOIN audit_log ST ON ST.model_config_id = MC.model_config_id left join special_config sc on sc.model_config_id =MC.model_config_id  LEFT JOIN prod_manuscript pm on S.manuscript_id = pm.manuscript_id WHERE 1=1");
		List<Object> params = new ArrayList<Object>();
		buildExtensionSQL(entity, SQL, params);
		if (entity.getOrderType() != null) {
			switch (entity.getOrderType()) {
			case DATEASC:
				SQL.append(" ORDER BY S.CREATETIME ASC");
				break;
			case DATEDESC:
				SQL.append(" ORDER BY S.CREATETIME DESC");
				break;
			case CLICKASC:
				SQL.append(" ORDER BY S.CREATETIME ASC");
				break;
			case CLICKDESC:
				SQL.append(" ORDER BY S.CREATETIME DESC");
				break;
			default:
				SQL.append(" ORDER BY S.CREATETIME DESC");
				break;
			}
		} else {
			SQL.append(" ORDER BY orderTime DESC");
		}

		SQL.append(" LIMIT ?,? ;");
		params.add(entity.getStartCount());
		params.add(entity.getPageSize());
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		List<Entity> extensionEntitys = new ArrayList<Entity>();
		while (sqlRowSet.next()) {
			extensionEntitys.add(buildExtensionModelList(sqlRowSet));
		}
		return extensionEntitys.iterator();
	}

	/**
	 * 查询专题总行数
	 * 
	 * @param entity
	 * @return
	 * @throws InvalidResultSetAccessException
	 * @throws DaoAppException
	 */
	private Entity querySpecialModelForCount(final SearchExtensionRequestEntity entity)
			throws InvalidResultSetAccessException, DaoAppException {
		StringBuilder SQL = new StringBuilder(
				" SELECT count(DISTINCT S.extension_id) AS Count FROM prod_extension S LEFT JOIN model_config MC ON S.extension_id = MC.model_id AND MC.model_type = 2 LEFT JOIN user_front_user U ON s.user_id = u.user_id WHERE 1=1");
		List<Object> params = new ArrayList<Object>();
		buildExtensionSQL(entity, SQL, params);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		CountDto count = new CountDto();
		if (sqlRowSet.next()) {
			count.setCount(sqlRowSet.getInt("Count"));
		}
		return count;
	}

	/**
	 * 构建查询SQL
	 * 
	 * @param entity
	 * @param SQL
	 * @param params
	 * @throws DaoAppException
	 */
	private void buildExtensionSQL(final SearchExtensionRequestEntity entity, StringBuilder SQL, List<Object> params)
			throws DaoAppException {
		/**
		 * 推广名
		 */
		if (!Str.isNullOrEmpty(entity.getExtensionName())) {
			SQL.append(" AND S.extension_name LIKE ?");
			params.add("%" + entity.getExtensionName() + "%");
		}

		/**
		 * 创建人
		 */
		if (entity.getUserId() != null) {
			SQL.append(" AND S.user_id = ?");
			params.add(entity.getUserId());
		} else {
			/**
			 * 推广状态
			 */
			if (entity.getExtensionState() != null) {
				SQL.append(" AND S.extension_state = ?");
				params.add(entity.getExtensionState());
			} else {
				throw new DaoAppException("后台用户查询 状态不能为空");
			}
		}

		/**
		 * 推广类型
		 */
		if (entity.getExtensionType() != null) {
			SQL.append(" AND S.extension_type = ?");
			params.add(entity.getExtensionType());
		}

		/**
		 * 起始时间
		 */
		if (entity.getTimeStart() != null) {
			SQL.append(" AND S.createtime >= ?");
			params.add(entity.getTimeStart());
		}
		/**
		 * 结束时间
		 */
		if (entity.getTimeEnd() != null) {
			SQL.append(" AND S.createtime <=DATE_ADD(?,INTERVAL 1 DAY)");
			params.add(entity.getTimeEnd());
		}
	}

	/**
	 * 封装推广
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws DaoAppException
	 */
	private ExtensionEntity buildExtensionModel(SqlRowSet rs) throws SQLException, DaoAppException {
		ExtensionEntity extensionEntity = new ExtensionEntity();
		Integer modelConfigId = rs.getInt("MCMODELCONFIGID");
		extensionEntity.setSpecialId(rs.getString("special_id"));
		extensionEntity.setCreatetime(rs.getTimestamp("createtime"));
		extensionEntity.setContext(rs.getString("context"));
		extensionEntity.setModelConfigId(modelConfigId);
		extensionEntity.setPublishTime(rs.getTimestamp("publish_time"));
		extensionEntity.setSpecialName(rs.getString("special_name"));
		extensionEntity.setExtensionId(rs.getString("extension_id"));
		extensionEntity.setExtensionName(rs.getString("extension_name"));
		extensionEntity.setExtensionImg(rs.getString("extension_img"));
		extensionEntity.setExtensionUrl(rs.getString("extension_url"));
		extensionEntity.setExtensionState(rs.getInt("extension_state"));
		extensionEntity.setExtensionType(rs.getInt("extension_type"));
		extensionEntity.setUserId(rs.getString("user_id"));
		String userName = rs.getString("user_name");
		extensionEntity.setManuscriptId(rs.getString("manuscript_id"));
		if (!Str.isNullOrEmpty(userName)) {
			extensionEntity.setUser(userName);
		} else {
			extensionEntity.setUser(rs.getString("user_phone"));
		}
		extensionEntity.setSummary(rs.getString("summary"));
		extensionEntity.setDisableReason(rs.getString("disable_reason"));
		extensionEntity.setConfig(this.queryConfigModel(modelConfigId));
		extensionEntity.setConfigKeywords(this.queryKeyWordModel(modelConfigId));
		extensionEntity.setConfigTags(this.queryTagModel(modelConfigId));
		return extensionEntity;
	}

	private ExtensionEntity buildExtensionModelList(SqlRowSet rs) throws SQLException, DaoAppException {
		ExtensionEntity extensionEntity = new ExtensionEntity();
		Integer modelConfigId = rs.getInt("MCMODELCONFIGID");
		extensionEntity.setSpecialId(rs.getString("special_id"));
		extensionEntity.setCreatetime(rs.getTimestamp("createtime"));
		// extensionEntity.setContext(rs.getString("context"));
		extensionEntity.setModelConfigId(modelConfigId);
		extensionEntity.setPublishTime(rs.getTimestamp("publish_time"));
		extensionEntity.setSpecialName(rs.getString("special_name"));
		extensionEntity.setExtensionId(rs.getString("extension_id"));
		extensionEntity.setExtensionName(rs.getString("extension_name"));
		extensionEntity.setExtensionImg(rs.getString("extension_img"));
		extensionEntity.setExtensionUrl(rs.getString("extension_url"));
		extensionEntity.setExtensionState(rs.getInt("extension_state"));
		extensionEntity.setExtensionType(rs.getInt("extension_type"));
		extensionEntity.setUserId(rs.getString("user_id"));
		if (Str.isNullOrEmpty(rs.getString("manuscript_id"))) {
			extensionEntity.setManuscriptEnable(false);
		} else {
			if (EnableState.enable.getValue().equals(rs.getInt("enable"))) {
				extensionEntity.setManuscriptEnable(true);
			} else {
				extensionEntity.setManuscriptEnable(false);
			}
			extensionEntity.setManuscriptId(rs.getString("manuscript_id"));
		}
		String userName = rs.getString("user_name");
		if (!Str.isNullOrEmpty(userName)) {
			extensionEntity.setUser(userName);
		} else {
			extensionEntity.setUser(rs.getString("user_phone"));
		}
		extensionEntity.setDisableReason(rs.getString("disable_reason"));
		ConfigEntity configEntity = new ConfigEntity();
		configEntity.setEndTime(rs.getTimestamp("end_time"));
		configEntity.setStartTime(rs.getTimestamp("start_time"));
		configEntity.setMainColor(rs.getString("main_color"));
		extensionEntity.setConfig(configEntity);
		extensionEntity.setSummary(rs.getString("summary"));
		// extensionEntity.setConfigKeywords(this.queryKeyWordModel(modelConfigId));
		// extensionEntity.setConfigTags(this.queryTagModel(modelConfigId));
		return extensionEntity;
	}

	/**
	 * 查询标签
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private List<ConfigTagEntity> queryTagModel(final Integer modelConfigId) throws DaoAppException {
		final String sqlScript = "SELECT DISTINCT * FROM special_config_tag WHERE model_config_id = ?;";
		List<Object> params = new ArrayList<Object>();
		params.add(modelConfigId);
		SqlRowSet sqlRowSet = this.execQueryForRowSet(sqlScript, params.toArray());
		List<ConfigTagEntity> configTagEntitys = new ArrayList<ConfigTagEntity>();
		while (sqlRowSet.next()) {
			ConfigTagEntity configTagEntity = new ConfigTagEntity();
			configTagEntity.setTagId(sqlRowSet.getInt("tag_id"));
			configTagEntity.setModelConfigId(sqlRowSet.getInt("model_config_id"));
			configTagEntity.setTag(sqlRowSet.getString("tag_name"));
			configTagEntitys.add(configTagEntity);
		}
		return configTagEntitys;
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
}
