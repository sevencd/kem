package cn.ilanhai.kem.dao.notify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.notify.NotifyEntity;
import cn.ilanhai.kem.domain.notify.NotifyType;
import cn.ilanhai.kem.domain.notify.QueryUserNotifyData;
import cn.ilanhai.kem.domain.notify.UserNotifyCount;

//@Component("notifyDao")
public class NotifyDao_back extends BaseDao {

	@Override
	public int save(Entity enity) throws DaoAppException {
		if (!this.isExists((NotifyEntity) enity))
			return this.add((NotifyEntity) enity);
		else
			return this.update((NotifyEntity) enity);

	}

	private boolean isExists(NotifyEntity entity) throws DaoAppException {
		String sql = "SELECT id FROM notify WHERE id=?;";
		Integer val = -1;
		List<Object> paramter = null;
		paramter = new ArrayList<Object>();
		paramter.add(entity.getId());
		val = this.execQueryForObject(sql, paramter.toArray(), Integer.class);
		return val == null ? false : val > 0;
	}

	private int add(final NotifyEntity entity) throws DaoAppException {
		final String sql = "INSERT INTO notify (add_time,update_time,is_read,read_time,content,type,source,target)VALUES(?,?,?,?,?,?,?,?);";
		int val = -1;
		KeyHolder keyHolder = null;
		keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator preparedStatementCreator = null;
		preparedStatementCreator = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement preparedStatement = null;
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setTimestamp(1, new Timestamp(entity
						.getAddTime().getTime()));
				preparedStatement.setTimestamp(2, new Timestamp(entity
						.getUpdateTime().getTime()));
				preparedStatement.setBoolean(3, entity.getRead());
				preparedStatement.setTimestamp(4, new Timestamp(entity
						.getReadTime().getTime()));
				preparedStatement.setString(5, entity.getContent());
				preparedStatement.setInt(6, entity.getNotifyType().getval());
				preparedStatement.setString(7, entity.getSource());
				preparedStatement.setString(8, entity.getTarget());
				return preparedStatement;
			}
		};
		val = this.execUpdate(preparedStatementCreator, keyHolder);
		if (val > 0)
			entity.setId(keyHolder.getKey().intValue());
		return val;
	}

	private int update(final NotifyEntity entity) throws DaoAppException {
		String sql = "UPDATE notify SET update_time=?,is_read=?,read_time=?,content=?,type=?,source=?,target=? WHERE id=?;";
		ArrayList<Object> args = null;
		args = new ArrayList<Object>();
		args.add(entity.getUpdateTime());
		args.add(entity.getRead());
		args.add(entity.getReadTime());
		args.add(entity.getContent());
		args.add(entity.getNotifyType().getval());
		args.add(entity.getSource());
		args.add(entity.getTarget());
		args.add(entity.getId());
		return this.execUpdate(sql, args.toArray());
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof IdDto)
			return query((IdDto) entity);
		else if (entity instanceof UserNotifyCount) {
			query((UserNotifyCount) entity);
			return null;
		}
		return null;
	}

	private Entity query(IdDto id) throws DaoAppException {
		SqlRowSet rs = null;
		String sql = "SELECT * FROM notify  WHERE id=?;";
		if (id == null)
			return null;
		rs = this.execQueryForRowSet(sql, id.getId());
		if (!rs.next())
			return null;
		return this.rowToEntity(rs);
	}

	private void query(UserNotifyCount count) throws DaoAppException {
		if (count == null)
			return;
		count.setCount(this.count(count.getTarget()));
		count.setReadCount(this.readCount(count.getTarget()));
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryUserNotifyData)
			return query((QueryUserNotifyData) entity);
		return null;

	}

	private int count(String target) throws DaoAppException {
		int val = -1;
		StringBuilder sql = null;
		sql = new StringBuilder();
		List<Object> paramter = null;
		paramter = new ArrayList<Object>();
		sql.append("SELECT COUNT(*) FROM notify WHERE target=?");
		paramter.add(target);
		val = this.execQueryForObject(sql.toString(), paramter.toArray(),
				Integer.class);
		return val;
	}

	private int readCount(String target) throws DaoAppException {
		int val = -1;
		StringBuilder sql = null;
		sql = new StringBuilder();
		List<Object> paramter = null;
		paramter = new ArrayList<Object>();
		sql.append("SELECT COUNT(*) FROM notify WHERE target=? AND is_read=0");
		paramter.add(target);
		val = this.execQueryForObject(sql.toString(), paramter.toArray(),
				Integer.class);
		return val;
	}

	public Iterator<Entity> query(QueryUserNotifyData entity)
			throws DaoAppException {
		int recordCount = -1;
		StringBuilder sql = null;
		List<Object> paramter = null;
		SqlRowSet rs = null;
		paramter = new ArrayList<Object>();
		sql = new StringBuilder();
		sql.append("SELECT * FROM notify WHERE target=?");
		sql.append(" ORDER BY add_time ");
		sql.append(entity.getOrder().toString());
		sql.append(" LIMIT ?,?;");
		recordCount = this.count(entity.getTarget());
		entity.setRecordCount(recordCount);
		paramter.add(entity.getTarget());
		paramter.add(entity.getStartCount());
		paramter.add(entity.getPageSize());
		rs = this.execQueryForRowSet(sql.toString(), paramter.toArray());
		return this.rowSetToCollection(rs);

	}

	@Override
	protected Entity rowToEntity(SqlRowSet rs) {
		NotifyEntity notifyEntity = null;
		if (rs == null)
			return null;
		notifyEntity = new NotifyEntity();
		notifyEntity.setId(rs.getInt("id"));
		notifyEntity.setAddTime(rs.getTimestamp("add_time"));
		notifyEntity.setUpdateTime(rs.getTimestamp("update_time"));
		notifyEntity.setSource(rs.getString("source"));
		notifyEntity.setTarget(rs.getString("target"));
		notifyEntity.setContent(rs.getString("content"));
		notifyEntity.setNotifyType(NotifyType.valueOf(rs.getInt("type")));
		notifyEntity.setRead(rs.getBoolean("is_read"));
		notifyEntity.setReadTime(rs.getTimestamp("read_time"));
		return notifyEntity;
	}
}
