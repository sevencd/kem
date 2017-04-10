package cn.ilanhai.kem.dao.key;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.dao.AbstractJdbcDao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.key.KeyConfigEntity;

//@Component("keyConfigDao")
public class KeyConfigDao_back extends BaseDao {

	public KeyConfigDao_back() {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity == null)
			return -1;
		if (entity instanceof KeyConfigEntity)
			return this.save((KeyConfigEntity) entity);
		return -1;
	}

	private int save(KeyConfigEntity configEntity) throws DaoAppException {
		String sql = "UPDATE  key_config SET is_enable=?,prefix=?,seed=?,update_time=?,len=?,step=? WHERE id=?";
		ArrayList<Object> ls = null;
		ls = new ArrayList<Object>();
		ls.add(configEntity.isEnable());
		ls.add(configEntity.getPrefix());
		ls.add(configEntity.getSeed());
		ls.add(configEntity.getUpdateTime());
		ls.add(configEntity.getLen());
		ls.add(configEntity.getStep());
		ls.add(configEntity.getId());
		return this.execUpdate(sql, ls.toArray());
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		KeyConfigEntity e = null;
		String sql = "SELECT * FROM  key_config WHERE id=?";
		SqlRowSet rs = null;
		if (entity instanceof KeyConfigEntity)
			e = (KeyConfigEntity) entity;
		if (e == null)
			return null;
		ArrayList<Object> ls = null;
		ls = new ArrayList<Object>();
		ls.add(e.getId());
		rs = this.execQueryForRowSet(sql, ls.toArray());
		if (!rs.next())
			return null;
		return this.rs(rs);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		String sql = "SELECT * FROM  key_config ";
		SqlRowSet rs = null;
		rs = this.execQueryForRowSet(sql);
		return this.rss(rs);
	}

	private KeyConfigEntity rs(SqlRowSet rs) {
		KeyConfigEntity configEntity = null;
		if (rs == null)
			return null;
		configEntity = new KeyConfigEntity();
		configEntity.setId(rs.getInt("id"));
		configEntity.setAddTime(rs.getDate("add_time"));
		configEntity.setEnable(rs.getBoolean("is_enable"));
		configEntity.setPrefix(rs.getString("prefix"));
		configEntity.setSeed(rs.getLong("seed"));
		configEntity.setUpdateTime(rs.getDate("update_time"));
		configEntity.setLen(rs.getInt("len"));
		configEntity.setStep(rs.getInt("step"));
		return configEntity;
	}

	private Iterator<Entity> rss(SqlRowSet rs) {
		ArrayList<Entity> ls = null;
		KeyConfigEntity configEntity = null;
		if (rs == null)
			return null;
		ls = new ArrayList<Entity>();
		while (rs.next()) {
			configEntity = this.rs(rs);
			if (configEntity != null)
				ls.add(configEntity);
		}
		return ls.iterator();
	}
}
