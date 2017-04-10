package cn.ilanhai.kem.dao.ver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.auth.AuthConfigEntity;
import cn.ilanhai.kem.domain.ver.DbVerEntity;

@Component("dbVerDao")
public class DbVerDao_back extends BaseDao {

	@Override
	public int save(Entity entity) throws DaoAppException {
		DbVerEntity dbVer = null;
		if (entity == null)
			return -1;
		dbVer = (DbVerEntity) entity;
		if (!isExists(dbVer))
			return this.add(dbVer);
		return this.update(dbVer);
	}

	private boolean isExists(DbVerEntity dbVer) throws DaoAppException {
		Integer ret = null;
		String sql = null;
		ArrayList<Object> args = null;
		if (dbVer == null)
			return false;
		args = new ArrayList<Object>();
		args.add(dbVer.getId());
		sql = "SELECT id FROM db_ver WHERE id=?;";
		ret = this.execQueryForObject(sql, args.toArray(), Integer.class);
		if (ret == null)
			return false;
		return ret.intValue() > 0;
	}

	private int add(DbVerEntity dbVer) throws DaoAppException {
		String sql = null;
		ArrayList<Object> args = null;
		args = new ArrayList<Object>();
		args.add(dbVer.getFileName());
		args.add(dbVer.getVer());
		args.add(dbVer.getAddTime());
		args.add(dbVer.getUpdateTime());
		sql = "INSERT INTO db_ver (file_name,ver,add_time,update_time)values(?,?,?,?);";
		return this.execUpdate(sql, args.toArray());
	}

	private int update(DbVerEntity dbVer) throws DaoAppException {
		String sql = null;
		ArrayList<Object> args = null;
		args = new ArrayList<Object>();
		args.add(dbVer.getFileName());
		args.add(dbVer.getVer());
		args.add(dbVer.getUpdateTime());
		args.add(dbVer.getId());
		sql = "UPDATE db_ver SET file_name=?,ver=?,update_time=? WHERE id=?;";
		return this.execUpdate(sql, args.toArray());
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		return this.load();
	}

	private Iterator<Entity> load() throws DaoAppException {
		String sql = null;
		SqlRowSet rs = null;
		sql = "SELECT * FROM db_ver";
		try {
			rs = this.execQueryForRowSet(sql);

			return this.rss(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private Iterator<Entity> rss(SqlRowSet rs) throws Exception {
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

	private Entity rs(SqlRowSet rs) throws Exception {
		DbVerEntity dbVer = null;
		dbVer = new DbVerEntity();
		dbVer.setId(rs.getInt("id"));
		dbVer.setAddTime(rs.getTimestamp("add_time"));
		dbVer.setUpdateTime(rs.getTimestamp("update_time"));
		dbVer.setFileName(rs.getString("file_name"));
		dbVer.setVer(rs.getInt("ver"));
		return dbVer;
	}
}
