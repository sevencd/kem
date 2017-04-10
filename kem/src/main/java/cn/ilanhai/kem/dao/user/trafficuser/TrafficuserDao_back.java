package cn.ilanhai.kem.dao.user.trafficuser;

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
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.TrafficuserType;
import cn.ilanhai.kem.domain.user.trafficuser.QueryTrafficUserEntity;
import cn.ilanhai.kem.domain.user.trafficuser.TrafficuserEntity;

@Component("trafficuserDao")
public class TrafficuserDao_back extends BaseDao {
	public TrafficuserDao_back() {
		super();
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryTrafficUserEntity) {
			return this.queryTrafficuser((QueryTrafficUserEntity) entity);
		}
		return super.query(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof IdEntity) {
			return this.queryTrafficuser((IdEntity) entity);
		} else if (entity instanceof QueryTrafficUserEntity) {
			return this.queryTrafficuserForCount((QueryTrafficUserEntity) entity);
		} else if(entity instanceof TrafficuserEntity){
			return this.queryTrafficuser((TrafficuserEntity) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof TrafficuserEntity) {
			IdEntity<Integer> id = new IdEntity<Integer>();
			Integer trafficuserId = ((TrafficuserEntity) entity).getTrafficuserId();
			if (trafficuserId != null) {
				id.setId(trafficuserId);
				if (queryTrafficuser(id) != null) {
					return this.updateTrafficuser((TrafficuserEntity) entity);
				}
			}
			return this.saveTrafficuser((TrafficuserEntity) entity);
		}
		return super.save(entity);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof IdEntity) {
			return this.deleteTrafficuser((IdEntity) entity);
		}
		return super.delete(entity);
	}

	private int saveTrafficuser(final TrafficuserEntity entity) throws DaoAppException {
		final StringBuilder SQL = new StringBuilder(
				"INSERT INTO user_traffic_user (name, phoneNo, qqNo, email, trafficuserType, wechatNo, vocation, industry, remark,extensionId,createtime,address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?);");
		// List<Object> params = new ArrayList<Object>();
		// params.add(entity.getName());
		// params.add(entity.getPhoneNo());
		// params.add(entity.getQqNo());
		// params.add(entity.getEmail());
		// params.add(entity.getTrafficuserType().getValue());
		// params.add(entity.getWechatNo());
		// params.add(entity.getVocation());
		// params.add(entity.getIndustry());
		// params.add(entity.getRemark());
		// params.add(entity.getExtensionId());
		// params.add(entity.getCreatetime());
		int val = -1;
		KeyHolder keyHolder = null;
		keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator preparedStatementCreator = null;
		preparedStatementCreator = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = null;
				preparedStatement = con.prepareStatement(SQL.toString());
				int i = 1;
				preparedStatement.setObject(i++, entity.getName());
				preparedStatement.setObject(i++, entity.getPhoneNo());
				preparedStatement.setObject(i++, entity.getQqNo());
				preparedStatement.setObject(i++, entity.getEmail());
				if (entity.getTrafficuserType() != null) {
					preparedStatement.setObject(i++, entity.getTrafficuserType().getValue());
				} else {
					preparedStatement.setObject(i++, null);
				}
				preparedStatement.setObject(i++, entity.getWechatNo());
				preparedStatement.setObject(i++, entity.getVocation());
				preparedStatement.setObject(i++, entity.getIndustry());
				preparedStatement.setObject(i++, entity.getRemark());
				preparedStatement.setObject(i++, entity.getExtensionId());
				preparedStatement.setObject(i++, entity.getCreatetime());
				preparedStatement.setObject(i++, entity.getAddress());
				return preparedStatement;
			}
		};
		val = this.execUpdate(preparedStatementCreator, keyHolder);
		if (val > 0)
			entity.setTrafficuserId(keyHolder.getKey().intValue());
		return val;
	}

	private int updateTrafficuser(TrafficuserEntity entity) throws DaoAppException {
		StringBuilder SQL = new StringBuilder(
				"UPDATE user_traffic_user SET name=?, phoneNo=?, qqNo=?, email=?, trafficuserType=?, wechatNo=?, vocation=?, industry=?, remark=?, extensionId=?,createtime=?,address=? WHERE trafficuserId=?;");
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getName());
		params.add(entity.getPhoneNo());
		params.add(entity.getQqNo());
		params.add(entity.getEmail());
		params.add(entity.getTrafficuserType().getValue());
		params.add(entity.getWechatNo());
		params.add(entity.getVocation());
		params.add(entity.getIndustry());
		params.add(entity.getRemark());
		params.add(entity.getExtensionId());
		params.add(entity.getCreatetime());
		params.add(entity.getAddress());
		params.add(entity.getTrafficuserId());
		return this.execUpdate(SQL.toString(), params.toArray());
	}

	private int deleteTrafficuser(IdEntity<Integer> id) throws DaoAppException {
		StringBuilder SQL = new StringBuilder("DELETE FROM user_traffic_user WHERE trafficuserId = ?;");
		List<Object> params = new ArrayList<Object>();
		params.add(id.getId());
		return this.execUpdate(SQL.toString(), params.toArray());
	}

	private Iterator<Entity> queryTrafficuser(QueryTrafficUserEntity trafficuser) throws DaoAppException {
		StringBuilder SQL = new StringBuilder("SELECT * FROM user_traffic_user WHERE extensionId = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(trafficuser.getExtensionId());
		if (!Str.isNullOrEmpty(trafficuser.getSearchInfo())) {
			String info = "%" + trafficuser.getSearchInfo() + "%";
			SQL.append(" AND phoneNo like ? OR qqNo like ? OR email like ? ");
			params.add(info);
			params.add(info);
			params.add(info);
		}
		if (trafficuser.getTrafficuserType() != null) {
			SQL.append(" AND trafficuserType =?");
			params.add(trafficuser.getTrafficuserType());
		}
		SQL.append(" ORDER BY CREATETIME DESC LIMIT ?,?;");
		params.add(trafficuser.getStartCount());
		params.add(trafficuser.getPageSize());
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		List<Entity> list = new ArrayList<Entity>();
		TrafficuserEntity trafficuserEntity = null;
		while (sqlRowSet.next()) {
			trafficuserEntity = buildTrafficuser(sqlRowSet);
			list.add(trafficuserEntity);
		}
		return list.iterator();
	}

	private Entity queryTrafficuserForCount(QueryTrafficUserEntity trafficuser) throws DaoAppException {
		StringBuilder SQL = new StringBuilder("SELECT count(*) as count  FROM user_traffic_user WHERE extensionId = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(trafficuser.getExtensionId());
		if (!Str.isNullOrEmpty(trafficuser.getSearchInfo())) {
			String info = "%" + trafficuser.getSearchInfo() + "%";
			SQL.append(" AND phoneNo like ? OR qqNo like ? OR email like ? ");
			params.add(info);
			params.add(info);
			params.add(info);
		}
		if (trafficuser.getTrafficuserType() != null) {
			SQL.append(" AND trafficuserType =?");
			params.add(trafficuser.getTrafficuserType());
		}
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		CountDto countDto = new CountDto();
		if (sqlRowSet.next()) {
			countDto.setCount(sqlRowSet.getInt("count"));
		}
		return countDto;
	}

	private TrafficuserEntity queryTrafficuser(IdEntity<Integer> id) throws DaoAppException {
		StringBuilder SQL = new StringBuilder("SELECT * FROM user_traffic_user WHERE trafficuserId = ?;");
		List<Object> params = new ArrayList<Object>();
		params.add(id.getId());
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		TrafficuserEntity trafficuserEntity = null;
		if (sqlRowSet.next()) {
			trafficuserEntity = buildTrafficuser(sqlRowSet);
		}
		return trafficuserEntity;
	}
	
	
	private TrafficuserEntity queryTrafficuser(TrafficuserEntity trafficuserEntity) throws DaoAppException {
		StringBuilder SQL = new StringBuilder("SELECT * FROM user_traffic_user WHERE extensionId = ? and phoneNo = ? ;");
		List<Object> params = new ArrayList<Object>();
		params.add(trafficuserEntity.getExtensionId());
		params.add(trafficuserEntity.getPhoneNo());
		SqlRowSet sqlRowSet = this.execQueryForRowSet(SQL.toString(), params.toArray());
		TrafficuserEntity newtrafficuserEntity = null;
		if (sqlRowSet.next()) {
			newtrafficuserEntity = buildTrafficuser(sqlRowSet);
		}
		return newtrafficuserEntity;
	}

	private TrafficuserEntity buildTrafficuser(SqlRowSet sqlRowSet) {
		TrafficuserEntity trafficuserEntity;
		trafficuserEntity = new TrafficuserEntity();
		trafficuserEntity.setTrafficuserId(sqlRowSet.getInt("trafficuserId"));
		trafficuserEntity.setName(sqlRowSet.getString("name"));
		trafficuserEntity.setPhoneNo(sqlRowSet.getString("phoneNo"));
		trafficuserEntity.setQqNo(sqlRowSet.getString("qqNo"));
		trafficuserEntity.setEmail(sqlRowSet.getString("email"));
		trafficuserEntity.setTrafficuserType(TrafficuserType.getEnum(sqlRowSet.getInt("trafficuserType")));
		trafficuserEntity.setTrafficuserTypeCode(sqlRowSet.getInt("trafficuserType"));
		trafficuserEntity.setWechatNo(sqlRowSet.getString("wechatNo"));
		trafficuserEntity.setVocation(sqlRowSet.getString("vocation"));
		trafficuserEntity.setIndustry(sqlRowSet.getString("industry"));
		trafficuserEntity.setRemark(sqlRowSet.getString("remark"));
		trafficuserEntity.setExtensionId(sqlRowSet.getString("extensionId"));
		trafficuserEntity.setCreatetime(sqlRowSet.getTimestamp("createtime"));
		trafficuserEntity.setAddress(sqlRowSet.getString("address"));
		return trafficuserEntity;
	}
}
