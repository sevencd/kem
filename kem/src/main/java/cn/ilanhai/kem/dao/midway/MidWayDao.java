package cn.ilanhai.kem.dao.midway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.EnableState;
import cn.ilanhai.kem.domain.enums.ExtensionState;
import cn.ilanhai.kem.domain.midway.PullPublishedEntity;

@Component("midwayDao")
public class MidWayDao extends BaseDao {

	public MidWayDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof IdEntity) {
			IdEntity<String> theEntity = (IdEntity<String>) entity;
			return query(theEntity.getId());
		}
		return null;
	}

	private Iterator<Entity> query(String relationId) throws DaoAppException {
		StringBuilder sql = null;
		SqlRowSet rs = null;
		sql = new StringBuilder();
		sql.append("select kid,relation_type,type from (");
		sql.append(" select template_id as kid,1 as relation_type,template_type as type from prod_template ");
		sql.append(" union ");
		sql.append(" select special_id as kid,2 as relation_type,special_type as type from prod_special ");
		sql.append(" union ");
		sql.append(
				" select extension_id as kid,3 as relation_type,extension_type as type from prod_extension where extension_state <>"
						+ ExtensionState.HASDISABLE.getValue());
		sql.append(" union ");
		sql.append(
				" select manuscript_id as kid,4 as relation_type,manuscript_type as type from prod_manuscript where enable ="
						+ EnableState.enable.getValue());
		sql.append(") as r ");
		if (relationId != null && !relationId.isEmpty()) {
			sql.append(" where kid=?");
			rs = this.execQueryForRowSet(sql.toString(), relationId);
		} else {
			rs = this.execQueryForRowSet(sql.toString());
		}

		return this.rs(rs);

	}

	private Iterator<Entity> rs(SqlRowSet rs) {
		List<Entity> ls = null;
		PullPublishedEntity entity = null;
		if (rs == null)
			return null;
		ls = new ArrayList<Entity>();
		while (rs.next()) {
			entity = new PullPublishedEntity();
			entity.setKid(rs.getString("kid"));
			entity.setRelationType(rs.getInt("relation_type"));
			entity.setType(rs.getInt("type"));
			ls.add(entity);
		}
		return ls.iterator();
	}
}
