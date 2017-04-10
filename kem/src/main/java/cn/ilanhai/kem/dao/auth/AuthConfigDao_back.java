package cn.ilanhai.kem.dao.auth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.auth.AuthConfigEntity;


public class AuthConfigDao_back extends BaseDao {
	public AuthConfigDao_back() {
		super();
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		SqlRowSet rs = null;
		String sql = "SELECT * FROM  auth_config;";
		rs = this.execQueryForRowSet(sql.toString());
		return this.rss(rs);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		SqlRowSet rs = null;
		String sql = "SELECT * FROM  auth_config WHERE type=?;";
		IdEntity<Integer> id = null;
		if (entity == null)
			return null;
		id = (IdEntity<Integer>) entity;
		rs = this.execQueryForRowSet(sql.toString(), id.getId());
		if (!rs.next())
			return null;
		return this.rs(rs);
	}

	private Iterator<Entity> rss(SqlRowSet rs) {
		List<Entity> ls = null;
		Entity entity = null;
		if (rs == null)
			return null;
		ls = new ArrayList<Entity>();
		while (rs.next()) {
			entity = this.rs(rs);
			if (entity != null)
				ls.add(entity);
		}
		return ls.iterator();
	}

	private Entity rs(SqlRowSet rs) {
		AuthConfigEntity authConfigEntity = null;
		authConfigEntity = new AuthConfigEntity();
		authConfigEntity.setId(rs.getInt("id"));
		authConfigEntity.setAddTime(rs.getTimestamp("add_time"));
		authConfigEntity.setUpdateTime(rs.getTimestamp("update_time"));
		authConfigEntity.setAppId(rs.getString("app_id"));
		authConfigEntity.setAppSecret(rs.getString("app_secret"));
		authConfigEntity.setAuthUri(rs.getString("auth_uri"));
		authConfigEntity.setEnable(rs.getBoolean("is_enable"));
		authConfigEntity.setRedirectUri(rs.getString("redirect_uri"));
		authConfigEntity.setType(rs.getInt("type"));
		authConfigEntity.setRemark(rs.getString("remark"));
		return authConfigEntity;
	}
}
