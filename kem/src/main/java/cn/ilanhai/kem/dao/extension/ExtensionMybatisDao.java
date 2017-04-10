
package cn.ilanhai.kem.dao.extension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.EnableState;
import cn.ilanhai.kem.domain.extension.ExtensionEntity;
import cn.ilanhai.kem.domain.extension.ExtensionSearchInfoDto;
import cn.ilanhai.kem.domain.extension.SearchExtensionRequestEntity;
import cn.ilanhai.kem.domain.special.ConfigEntity;

@Component("extensionMybatisDao")
public class ExtensionMybatisDao extends MybatisBaseDao {

	public ExtensionMybatisDao() throws DaoAppException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof SearchExtensionRequestEntity) {

			Iterator<Entity> responseInfo = loadExtensionInfo((SearchExtensionRequestEntity) entity).iterator();
			List<Entity> extensionEntitys = new ArrayList<Entity>();
			while (responseInfo.hasNext()) {
				ExtensionSearchInfoDto item = (ExtensionSearchInfoDto) responseInfo.next();
				extensionEntitys.add(buildExtensionModelList(item));
			}

			return extensionEntitys.iterator();
		}
		return null;
	}

	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof SearchExtensionRequestEntity) {
			IdEntity<Integer> entitys = new IdEntity<Integer>();
			entitys.setId(searchExtensionInfoCount((SearchExtensionRequestEntity) entity));
			return entitys;
		}
		return null;
	}

	private int searchExtensionInfoCount(SearchExtensionRequestEntity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("ExtensionSql.searchextensioninfocount", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	private List<Entity> loadExtensionInfo(SearchExtensionRequestEntity entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("ExtensionSql.loadextensioninfo", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	private ExtensionEntity buildExtensionModelList(ExtensionSearchInfoDto rs) throws DaoAppException {
		ExtensionEntity extensionEntity = new ExtensionEntity();
		Integer modelConfigId = rs.getModelConfigId();
		extensionEntity.setSpecialId(rs.getSpecial_id());
		extensionEntity.setCreatetime(rs.getCreatetime());
		// extensionEntity.setContext(rs.getString("context"));
		extensionEntity.setModelConfigId(modelConfigId);
		extensionEntity.setPublishTime(rs.getPublish_time());
		extensionEntity.setSpecialName(rs.getSpecial_name());
		extensionEntity.setExtensionId(rs.getExtension_id());
		extensionEntity.setExtensionName(rs.getExtension_name());
		extensionEntity.setExtensionImg(rs.getExtension_img());
		extensionEntity.setExtensionUrl(rs.getExtension_url());
		extensionEntity.setExtensionState(rs.getExtension_state());
		extensionEntity.setExtensionType(rs.getExtension_type());
		extensionEntity.setUserId(rs.getUser_id());
		String userName = rs.getUser_name();
		if (!Str.isNullOrEmpty(userName)) {
			extensionEntity.setUser(userName);
		} else {
			extensionEntity.setUser(rs.getUser_phone());
		}
		if (Str.isNullOrEmpty(rs.getManuscriptId())) {
			extensionEntity.setManuscriptEnable(false);
		} else {
			if (EnableState.enable.getValue().equals(rs.getManuscriptEnable())) {
				extensionEntity.setManuscriptEnable(true);
			} else {
				extensionEntity.setManuscriptEnable(false);
			}
			extensionEntity.setManuscriptId(rs.getManuscriptId());
		}		
		extensionEntity.setDisableReason(rs.getDisable_reason());
		ConfigEntity configEntity = new ConfigEntity();
		configEntity.setEndTime(rs.getEnd_time());
		configEntity.setStartTime(rs.getStart_time());
		configEntity.setMainColor(rs.getMain_color());
		extensionEntity.setConfig(configEntity);
		extensionEntity.setSummary(rs.getSummary());
		// extensionEntity.setConfigKeywords(this.queryKeyWordModel(modelConfigId));
		// extensionEntity.setConfigTags(this.queryTagModel(modelConfigId));
		return extensionEntity;
	}

}
