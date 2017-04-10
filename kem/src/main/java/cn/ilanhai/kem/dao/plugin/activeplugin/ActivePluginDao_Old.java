package cn.ilanhai.kem.dao.plugin.activeplugin;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;

import cn.ilanhai.kem.domain.CountDto;

import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.*;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.*;

@Component("activepluginDao_Old")
public class ActivePluginDao_Old extends MybatisBaseDao {

	public ActivePluginDao_Old() throws DaoAppException {
		super();

	}

	@Override
	public int save(Entity entity) throws DaoAppException {

		if (entity instanceof ActivePluginEntity) {
			return this.save((ActivePluginEntity) entity);
		} else if (entity instanceof DrawPrizeRecordEntity) {
			return this.save((DrawPrizeRecordEntity) entity);
		} else if (entity instanceof ActivePluginPrizeSettingEntity) {
			return updatePrizeSetting((ActivePluginPrizeSettingEntity) entity);
		}
		return 0;

	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof DeleteDrawprizeDataDto) {
			return deleteDrawprizeData((DeleteDrawprizeDataDto) entity);
		}
		return super.delete(entity);
	}

	private int deleteDrawprizeData(
			DeleteDrawprizeDataDto deleteDrawprizeDataDto)
			throws DaoAppException {
		// StringBuilder sql = new StringBuilder();
		// sql.append("delete  from  user_plugin_activeplugin_drawprizedata where record_id=?");
		// return this.execUpdate(sql.toString(),
		// deleteDrawprizeDataDto.getRecordId());
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.deleteDrawprizeData",
					deleteDrawprizeDataDto);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int save(DrawPrizeRecordEntity entity) throws DaoAppException {
		DrawPrizeRecordEntity activePluginEntity = null;
		try {
			activePluginEntity = (DrawPrizeRecordEntity) entity;
			if (!this.isExists(activePluginEntity))
				return this.add(activePluginEntity);
			return this.update(activePluginEntity);
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private int updatePrizeSetting(
			ActivePluginPrizeSettingEntity activePluginPrizeSettingEntity)
			throws DaoAppException {
		// String sql =
		// " UPDATE  user_plugin_activeplugin_prizesetting set amount = amount - 1 where record_id = "
		// + activePluginPrizeSettingEntity.getRecordId();
		// return this.execUpdate(sql);
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.updatePrizeSetting",
					activePluginPrizeSettingEntity);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int update(DrawPrizeRecordEntity entity) throws DaoAppException {

		// StringBuilder sql = new StringBuilder();
		// sql.append("update user_plugin_activeplugin_drawprizedata set ");
		// sql.append(" plugin_id=?, ");
		// sql.append(" phone_no=?, ");
		// sql.append(" name=?, ");
		// sql.append(" address=?, ");
		// sql.append(" qq=?, ");
		// sql.append(" createtime=?, ");
		// sql.append(" prize_name=?, ");
		// sql.append(" prize_no=?, ");
		// sql.append(" exchange_state=?, ");
		// sql.append(" exchangetime=? ");
		// sql.append(" where record_id=? ");
		//
		// return this.execUpdate(sql.toString(), entity.getPluginId(),
		// entity.getPhoneNo(), entity.getName(), entity.getAddress(),
		// entity.getQq(), entity.getCreatetime(), entity.getPrizeName(),
		// entity.getPrizeNo(), entity.getExchangeState(),
		// entity.getExchangeTime(), entity.getRecordId());
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.update", entity);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int add(final DrawPrizeRecordEntity entity) throws DaoAppException {

		// final String sql =
		// "insert into user_plugin_activeplugin_drawprizedata (plugin_id,phone_no,name,address,qq,createtime,prize_name,prize_no,exchange_state) values (?,?,?,?,?,?,?,?,?)";
		//
		// JdbcTemplate template = null;
		// KeyHolder holder = null;
		// int val = -1;
		// if (entity == null)
		// return val;
		// template = this.getJdbcTemplate();
		// holder = new GeneratedKeyHolder();
		// val = template.update(new PreparedStatementCreator() {
		// public PreparedStatement createPreparedStatement(Connection con)
		// throws SQLException {
		// PreparedStatement statement = null;
		// statement = con.prepareStatement(sql);
		// statement.setInt(1, entity.getPluginId());
		// statement.setString(2, entity.getPhoneNo());
		// statement.setString(3, entity.getName());
		// statement.setString(4, entity.getAddress());
		// statement.setString(5, entity.getQq());
		// statement.setTimestamp(6, new Timestamp(entity.getCreatetime()
		// .getTime()));
		// statement.setString(7, entity.getPrizeName());
		// statement.setString(8, entity.getPrizeNo());
		// statement.setInt(9, entity.getExchangeState());
		// System.out.println(statement.toString());
		// return statement;
		// }
		// }, holder);
		// if (val > 0)
		// entity.setRecordId(holder.getKey().intValue());
		//
		// return val;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.add", entity);
			entity.setRecordId(val);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private boolean isExists(DrawPrizeRecordEntity entity)
			throws DaoAppException {
		// if (entity.getRecordId() == null)
		// return false;
		// String sql =
		// "SELECT record_id FROM user_plugin_activeplugin_drawprizedata WHERE record_id=?;";
		// Integer val = -1;
		// List<Object> paramter = null;
		// paramter = new ArrayList<Object>();
		// paramter.add(entity.getRecordId());
		// val = this.execQueryForObject(sql, paramter.toArray(),
		// Integer.class);
		// return val == null ? false : val > 0;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.isExists", entity);
			entity.setRecordId(val);
			return val == null ? false : val > 0;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int save(ActivePluginEntity entity) throws DaoAppException {
		ActivePluginEntity activePluginEntity = null;
		try {
			activePluginEntity = (ActivePluginEntity) entity;
			if (!this.isExists(activePluginEntity))
				return this.add(activePluginEntity);
			return this.update(activePluginEntity);
		} catch (DaoAppException e) {
			throw e;
		}
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof QueryActivePluginByRelationId) {
			return queryActivePluginByRelationId((QueryActivePluginByRelationId) entity);
		} else if (entity instanceof QueryDrawPrizeRecordByRecordIdDto) {
			return queryDrawPrizeRecordByRecordId(((QueryDrawPrizeRecordByRecordIdDto) entity)
					.getRecordId());
		} else if (entity instanceof QueryDrawprizeUserDataDto) {
			return queryUserDrawPrizeForCount((QueryDrawprizeUserDataDto) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryDrawprizeDataDto) {
			return queryUserDrawPrizeCount((QueryDrawprizeDataDto) entity)
					.iterator();
		} else if (entity instanceof QueryDrawprizeUserDataDto) {
			return queryUserDrawPrize((QueryDrawprizeUserDataDto) entity)
					.iterator();
		}
		return super.query(entity);
	}

	private List<Entity> queryUserDrawPrizeCount(
			QueryDrawprizeDataDto queryDrawprizeDataDto) throws DaoAppException {
		// StringBuilder builder = new StringBuilder(
		// "SELECT * from user_plugin_activeplugin_drawprizedata where plugin_id=? and phone_no=? order by createtime desc");
		// List<Object> args = new ArrayList<Object>();
		// args.add(queryDrawprizeDataDto.getPluginId());
		// args.add(queryDrawprizeDataDto.getPhoneNo());
		// SqlRowSet row = this.execQueryForRowSet(builder.toString(),
		// args.toArray());
		// List<Entity> results = new ArrayList<Entity>();
		// while (row.next()) {
		// DrawPrizeRecordEntity result = new DrawPrizeRecordEntity();
		// result.setRecordId(row.getInt("record_id"));
		// result.setPluginId(row.getInt("plugin_id"));
		// result.setPhoneNo(row.getString("phone_no"));
		// result.setName(row.getString("name"));
		// result.setAddress(row.getString("address"));
		// result.setQq(row.getString("qq"));
		// result.setCreatetime(row.getTimestamp("createtime"));
		// result.setPrizeName(row.getString("prize_name"));
		// result.setPrizeNo(row.getString("prize_no"));
		// result.setExchangeState(row.getInt("exchange_state"));
		// result.setExchangeTime(row.getTimestamp("exchangetime"));
		// results.add(result);
		// }
		//
		// return results.iterator();
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("FormPlugin.queryUserDrFawPrizeCount",
					queryDrawprizeDataDto);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private List<Entity> queryUserDrawPrize(
			QueryDrawprizeUserDataDto queryDrawprizeDataDto)
			throws DaoAppException {
		// StringBuilder builder = new StringBuilder(
		// "SELECT * from user_plugin_activeplugin_drawprizedata A LEFT JOIN user_plugin_info B ON A.plugin_id = B.plugin_id  where A.prize_no IS NOT NULL AND B.relation_id=?");
		// List<Object> args = new ArrayList<Object>();
		// args.add(queryDrawprizeDataDto.getRelationId());
		// if (!Str.isNullOrEmpty(queryDrawprizeDataDto.getPrizeNo())) {
		// builder.append(" and A.prize_no like ?");
		// args.add("%" + queryDrawprizeDataDto.getPrizeNo() + "%");
		// }
		// builder.append(" order by A.createtime desc LIMIT ?,? ;");
		// args.add(queryDrawprizeDataDto.getStartCount());
		// args.add(queryDrawprizeDataDto.getPageSize());
		// SqlRowSet row = this.execQueryForRowSet(builder.toString(),
		// args.toArray());
		// List<Entity> results = new ArrayList<Entity>();
		// while (row.next()) {
		// DrawPrizeRecordEntity result = new DrawPrizeRecordEntity();
		// result.setRecordId(row.getInt("record_id"));
		// result.setPluginId(row.getInt("plugin_id"));
		// result.setPhoneNo(row.getString("phone_no"));
		// result.setName(row.getString("name"));
		// result.setAddress(row.getString("address"));
		// result.setQq(row.getString("qq"));
		// result.setCreatetime(row.getTimestamp("createtime"));
		// result.setPrizeName(row.getString("prize_name"));
		// result.setPrizeNo(row.getString("prize_no"));
		// result.setExchangeState(row.getInt("exchange_state"));
		// result.setExchangeTime(row.getTimestamp("exchangetime"));
		// results.add(result);
		// }
		//
		// return results.iterator();
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("FormPlugin.queryUserDrawPrize",
					queryDrawprizeDataDto);

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private Entity queryUserDrawPrizeForCount(
			QueryDrawprizeUserDataDto queryDrawprizeDataDto)
			throws DaoAppException {
		// StringBuilder builder = new StringBuilder(
		// "SELECT count(*) as count from user_plugin_activeplugin_drawprizedata A LEFT JOIN user_plugin_info B ON A.plugin_id = B.plugin_id  where A.prize_no IS NOT NULL AND B.relation_id=?");
		// List<Object> args = new ArrayList<Object>();
		// args.add(queryDrawprizeDataDto.getRelationId());
		// if (!Str.isNullOrEmpty(queryDrawprizeDataDto.getPrizeNo())) {
		// builder.append(" and A.prize_no like ?");
		// args.add("%" + queryDrawprizeDataDto.getPrizeNo() + "%");
		// }
		// SqlRowSet row = this.execQueryForRowSet(builder.toString(),
		// args.toArray());
		// CountDto countDto = new CountDto();
		// while (row.next()) {
		// countDto.setCount(row.getInt("count"));
		// }
		//
		// return countDto;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			CountDto countDto = null;
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.queryUserDrawPrizeForCount",
					queryDrawprizeDataDto);
			countDto = new CountDto();
			countDto.setCount(val);
			return countDto;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	/**
	 * 根据记录ID查询中间记录
	 * 
	 * @param recordId
	 * @return
	 * @throws DaoAppException
	 */
	private DrawPrizeRecordEntity queryDrawPrizeRecordByRecordId(
			Integer recordId) throws DaoAppException {
		// StringBuilder builder = new StringBuilder();
		// builder.append("select record_id ");
		// builder.append(" ,plugin_id ");
		// builder.append(" ,phone_no ");
		// builder.append(" ,name ");
		// builder.append(" ,address ");
		// builder.append(" ,qq ");
		// builder.append(" ,createtime ");
		// builder.append(" ,prize_name ");
		// builder.append(" ,prize_no ");
		// builder.append(" ,exchange_state ");
		// builder.append(" ,exchangetime ");
		// builder.append(" from user_plugin_activeplugin_drawprizedata where record_id=? ");
		//
		// // List<Object> args=new ArrayList<Object>();
		// // args.add(recordId);
		// SqlRowSet row = this.execQueryForRowSet(builder.toString(),
		// recordId);
		//
		// DrawPrizeRecordEntity result = new DrawPrizeRecordEntity();
		// if (row == null || !row.next())
		// return null;
		//
		// result.setRecordId(row.getInt("record_id"));
		// result.setPluginId(row.getInt("plugin_id"));
		// result.setPhoneNo(row.getString("phone_no"));
		// result.setName(row.getString("name"));
		// result.setAddress(row.getString("address"));
		// result.setQq(row.getString("qq"));
		// result.setCreatetime(row.getTimestamp("createtime"));
		// result.setPrizeName(row.getString("prize_name"));
		// result.setPrizeNo(row.getString("prize_no"));
		// result.setExchangeState(row.getInt("exchange_state"));
		// result.setExchangeTime(row.getTimestamp("exchangetime"));
		//
		// return result;
		SqlSession sqlSession = null;
		DrawPrizeRecordEntity result;
		try {
			//CountDto countDto = null;
			sqlSession = this.getSqlSession();
			result = sqlSession.selectOne(
					"FormPlugin.queryDrawPrizeRecordByRecordId", recordId);
			return result;

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private ActivePluginEntity queryActivePluginByRelationId(
			final QueryActivePluginByRelationId condition)
			throws DaoAppException {
		// StringBuilder builder = new StringBuilder();
		// List<Object> args = new ArrayList<Object>();
		// builder.append("select upi.plugin_id as plugin_id -- 插件ID \n");
		// builder.append(",upi.plugin_type as plugin_type -- 插件类型 \n");
		// builder.append(",upi.create_time as create_time -- 创建时间 \n");
		// builder.append(",upi.user_id as user_id -- 用户ID \n");
		// builder.append(",upi.relation_id as relation_id -- 关联ID \n");
		// builder.append(",upi.relation_type as relation_type -- 关联类型 \n");
		// builder.append(",upi.is_used as is_used -- 是否已使用 \n");
		// builder.append(",upa.type as type -- 活动插件类型 \n");
		// builder.append(",upa.drawtime as drawtime -- 抽奖次数 \n");
		// builder.append(",upa.wintime as wintime -- 中奖次数 \n");
		// builder.append(",upa.intervaltime as intervaltime -- 中奖间隔时间 \n");
		// builder.append(",upa.intervaltime_type as intervaltime_type -- 中奖间隔时间单位 \n");
		// builder.append(",upa.prize_collect_info as prize_collect_info -- 收集用户信息 \n");
		// builder.append(",upa.prize_collect_required_info as prize_collect_required_info -- 必填收集用户信息 \n");
		// builder.append(",upa.outer_url as outer_url -- 外部链接 \n");
		// builder.append(",upa.merchant_phone as merchant_phone -- 商家电话 \n");
		// builder.append(" from user_plugin_info upi  \n");
		// builder.append(" left join user_plugin_activeplugin upa on upa.plugin_id=upi.plugin_id  \n");
		// if (condition.getIsUsed() != null) {
		// builder.append(" where 1=1");
		// } else {
		// builder.append(" where upi.is_used=1");
		// }
		// builder.append(" and upi.relation_id=? and upi.plugin_type ="
		// + PluginType.ACTIVEPLUGIN.getValue());
		// args.add(condition.getRelationId());
		// if (condition.getActiveType() != null) {
		// builder.append(" and upa.type=? \n");
		// args.add(condition.getActiveType().getValue());
		// }
		// builder.append(" order by upi.create_time desc LIMIT 1;");
		// final String sqlScript = builder.toString();
		// SqlRowSet row = this.execQueryForRowSet(sqlScript, args.toArray());
		//
		// ActivePluginEntity entity = new ActivePluginEntity();
		// if (row == null || !row.next())
		// return null;
		//
		// entity.setPluginId(row.getInt("plugin_id"));
		// entity.setPluginType(PluginType.getEnum(row.getInt("plugin_type")));
		// entity.setCreatetime(row.getTimestamp("create_time"));
		// entity.setUserId(row.getString("user_id"));
		// entity.setRelationId(row.getString("relation_id"));
		// entity.setRelationType(ManuscriptType.getEnum(row
		// .getInt("relation_type")));
		// entity.setUsed(row.getBoolean("is_used"));
		// entity.setActivePluginType(ActivePluginType.getEnum(row.getInt("type")));
		// entity.setDrawTime(row.getInt("drawtime"));
		// entity.setWinTime(row.getInt("wintime"));
		// entity.setIntervalTime(row.getInt("intervaltime"));
		// entity.setIntervalTimeType(row.getInt("intervaltime_type"));
		// entity.setPrizeCollectInfo(FastJson.json2Bean(row
		// .getString("prize_collect_info"), entity.getPrizeCollectInfo()
		// .getClass()));
		// entity.setPrizeCollectRequiredInfo(FastJson.json2Bean(row
		// .getString("prize_collect_required_info"), entity
		// .getPrizeCollectRequiredInfo().getClass()));
		// entity.setOuterUrl(row.getString("outer_url"));
		// entity.setMerchantPhone(row.getString("merchant_phone"));
		//
		// String recordSql =
		// "select record_id,option_name,prize_name,amount,rate from user_plugin_activeplugin_prizesetting where plugin_id=?;";
		// row = this.execQueryForRowSet(recordSql, entity.getPluginId());
		//
		// List<ActivePluginPrizeSettingEntity> settings = new
		// ArrayList<ActivePluginPrizeSettingEntity>();
		// while (row.next()) {
		// ActivePluginPrizeSettingEntity record = new
		// ActivePluginPrizeSettingEntity(
		// entity.getPluginId());
		// record.setRecordId(row.getInt("record_id"));
		// record.setOptionName(row.getString("option_name"));
		// record.setPrizeName(row.getString("prize_name"));
		// record.setAmount(row.getInt("amount"));
		// record.setRate(row.getInt("rate"));
		//
		// settings.add(record);
		// }
		//
		// entity.setActivePluginPrizeSettings(settings);
		//
		// return entity;
		SqlSession sqlSession = null;
		try {
			ActivePluginEntity activePluginEntity = null;
			List<ActivePluginPrizeSettingEntity> settings = null;
			sqlSession = this.getSqlSession();
			activePluginEntity = sqlSession.selectOne(
					"FormPlugin.queryActivePluginByRelationId", condition);
			settings = sqlSession.selectList(
					"FormPlugin.queryActivePluginByRelationId1",
					activePluginEntity);
			activePluginEntity.setActivePluginPrizeSettings(settings);
			return activePluginEntity;

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
		// JdbcTemplate template = null;
		//
		// template = this.getJdbcTemplate();
		// return template.query(sqlScript, new PreparedStatementSetter() {
		// public void setValues(PreparedStatement ps) throws SQLException {
		// ps.setString(1, condition.getRelationId());
		// }
		// }, new ResultSetExtractor<ActivePluginEntity>() {
		// public ActivePluginEntity extractData(ResultSet rs) throws
		// SQLException, DataAccessException {
		// ActivePluginEntity entity = null;
		// if (rs.next()) {
		// entity = new ActivePluginEntity();
		// entity.setPluginId(rs.getInt("plugin_id"));
		// entity.setPluginType(PluginType.getEnum(rs.getInt("plugin_type")));
		// entity.setCreatetime(rs.getTimestamp("create_time"));
		// entity.setUserId(rs.getInt("user_id"));
		// entity.setRelationId(rs.getString("relation_id"));
		// entity.setRelationType(RelationType.getEnum(rs.getInt("relation_type")));
		// entity.setUsed(rs.getBoolean("is_used"));
		// entity.setActivePluginType(ActivePluginType.getEnum(rs.getInt("type")));
		// entity.setDrawTime(rs.getInt("drawtime"));
		// entity.setWinTime(rs.getInt("wintime"));
		// entity.setIntervalTime(rs.getInt("intervaltime"));
		// entity.setIntervalTimeType(rs.getInt("intervaltime_type"));
		// entity.setPrizeCollectInfo(FastJson.json2Bean(rs.getString("prize_collect_info"),
		// entity.getPrizeCollectInfo().getClass()));
		// entity.setPrizeCollectRequiredInfo(FastJson.json2Bean(rs.getString("prize_collect_required_info"),
		// entity.getPrizeCollectRequiredInfo().getClass()));
		// entity.setOuterUrl(rs.getString("outer_url"));
		// entity.setMerchantPhone(rs.getString("merchant_phone"));
		// }
		// return entity;
		// }
		// });
	}

	private int add(final ActivePluginEntity entity) throws DaoAppException {
		// 插入插件表
		// final String sqlScript =
		// "insert into user_plugin_info (plugin_type,create_time,user_id,relation_id,relation_type,is_used) values (?,?,?,?,?,?);";
		// JdbcTemplate template = null;
		// KeyHolder holder = null;
		// int val = -1;
		// if (entity == null)
		// return val;
		// template = this.getJdbcTemplate();
		// holder = new GeneratedKeyHolder();
		// val = template.update(new PreparedStatementCreator() {
		// public PreparedStatement createPreparedStatement(Connection con)
		// throws SQLException {
		// PreparedStatement statement = null;
		// statement = con.prepareStatement(sqlScript);
		// statement.setObject(1, entity.getPluginType().getValue());
		// statement.setObject(2, new Timestamp(entity.getCreatetime()
		// .getTime()));
		// statement.setObject(3, entity.getUserId());
		// statement.setObject(4, entity.getRelationId());
		// if (entity.getRelationType() == null) {
		// statement.setObject(5, null);
		// } else {
		// statement.setObject(5, entity.getRelationType().getValue());
		// }
		// statement.setObject(6, entity.isUsed());
		// return statement;
		// }
		// }, holder);
		// if (val > 0)
		// entity.setPluginId(holder.getKey().intValue());
		// // 插入活动插件表
		// final String sqlScriptAcitve =
		// "insert into user_plugin_activeplugin (plugin_id,type,drawtime,wintime,intervaltime,intervaltime_type,prize_collect_info,prize_collect_required_info,outer_url,merchant_phone) values (?,?,?,?,?,?,?,?,?,?);";
		//
		// val = -1;
		// if (entity.getPluginId() == null)
		// return val;
		// template = this.getJdbcTemplate();
		// holder = new GeneratedKeyHolder();
		// val = template.update(new PreparedStatementCreator() {
		// public PreparedStatement createPreparedStatement(Connection con)
		// throws SQLException {
		// PreparedStatement statement = null;
		// statement = con.prepareStatement(sqlScriptAcitve);
		// statement.setInt(1, entity.getPluginId());
		// if (entity.getActivePluginType() == null) {
		// statement.setObject(2, null);
		// } else {
		// statement
		// .setInt(2, entity.getActivePluginType().getValue());
		// }
		// if (entity.getDrawTime() == null) {
		// statement.setNull(3, entity.getDrawTime());
		// } else {
		// statement.setInt(3, entity.getDrawTime());
		// }
		//
		// if (entity.getWinTime() == null) {
		// statement.setNull(4, entity.getWinTime());
		// } else {
		// statement.setInt(4, entity.getWinTime());
		// }
		//
		// if (entity.getIntervalTime() == null) {
		// statement.setInt(5, entity.getIntervalTime());
		// } else {
		// statement.setInt(5, entity.getIntervalTime());
		// }
		//
		// if (entity.getIntervalTimeType() == null) {
		// statement.setNull(6, entity.getIntervalTimeType());
		// } else {
		// statement.setInt(6, entity.getIntervalTimeType());
		// }
		//
		// statement.setString(7,
		// FastJson.bean2Json(entity.getPrizeCollectInfo()));
		// statement.setString(8, FastJson.bean2Json(entity
		// .getPrizeCollectRequiredInfo()));
		// statement.setString(9, entity.getOuterUrl());
		// statement.setString(10, entity.getMerchantPhone());
		//
		// return statement;
		// }
		// }, holder);
		// // 插入奖项设置
		// this.insertPrizeSettings(entity);
		//
		// return val;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.add", entity);
			if (val <= 0)
				return val;
			entity.setPluginId(val);
			val = sqlSession.update("FormPlugin.add1", entity);
			this.insertPrizeSettings(entity);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private int update(final ActivePluginEntity entity) throws DaoAppException {
		// 修改插件表
		// final String sqlScript =
		// "UPDATE user_plugin_info SET plugin_type=?,create_time=?,user_id=?,relation_id=?,relation_type=?,is_used=? WHERE plugin_id=?;";
		// JdbcTemplate template = null;
		// int val = -1;
		// if (entity == null)
		// return val;
		// template = this.getJdbcTemplate();
		// val = template.update(sqlScript, new PreparedStatementSetter() {
		// public void setValues(PreparedStatement statement)
		// throws SQLException {
		// statement.setObject(1, entity.getPluginType().getValue());
		// statement.setObject(2, new Timestamp(entity.getCreatetime()
		// .getTime()));
		// if (entity.getUserId() == null) {
		// statement.setObject(3, entity.getUserId());
		// } else {
		// statement.setString(3, entity.getUserId());
		// }
		// statement.setObject(4, entity.getRelationId());
		// if (entity.getRelationType() == null) {
		// statement.setObject(5, null);
		// } else {
		// statement.setObject(5, entity.getRelationType().getValue());
		// }
		// statement.setObject(6, entity.isUsed());
		// statement.setObject(7, entity.getPluginId());
		// }
		// });

		// 修改活动插件表
		// final String sqlScriptActive =
		// "UPDATE user_plugin_activeplugin SET drawtime=?,wintime=?,intervaltime=?,intervaltime_type=?,prize_collect_info=?,prize_collect_required_info=?,outer_url=?,merchant_phone=? WHERE plugin_id=?;";
		//
		// val = -1;
		//
		// template = this.getJdbcTemplate();
		// val = template.update(sqlScriptActive, new PreparedStatementSetter()
		// {
		// public void setValues(PreparedStatement statement)
		// throws SQLException {
		// statement.setInt(1, entity.getDrawTime());
		// statement.setInt(2, entity.getWinTime());
		// statement.setInt(3, entity.getIntervalTime());
		// statement.setInt(4, entity.getIntervalTimeType());
		// statement.setString(5,
		// FastJson.bean2Json(entity.getPrizeCollectInfo()));
		// statement.setString(6, FastJson.bean2Json(entity
		// .getPrizeCollectRequiredInfo()));
		// statement.setString(7, entity.getOuterUrl());
		// statement.setString(8, entity.getMerchantPhone());
		// statement.setInt(9, entity.getPluginId());
		// }
		// });
		// // 插入奖项设置
		// this.insertPrizeSettings(entity);
		//
		// return val;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.update", entity);
			if (val <= 0)
				return val;
			entity.setPluginId(val);
			val = sqlSession.update("FormPlugin.update1", entity);
			this.insertPrizeSettings(entity);
			return val;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	/**
	 * 判断插件是否存在
	 * 
	 * @param entity
	 * @return true 存在，false 不存在
	 * @throws DaoAppException
	 */
	private boolean isExists(final ActivePluginEntity entity)
			throws DaoAppException {
		// final String sqlScript =
		// "SELECT plugin_id FROM user_plugin_info WHERE plugin_id=?";
		// JdbcTemplate template = null;
		// Integer val = -1;
		// if (entity == null)
		// throw new DaoAppException("实体为null");
		// if (entity.getPluginId() == null) {
		// return false;
		// }
		// template = this.getJdbcTemplate();
		// val = template.query(sqlScript, new PreparedStatementSetter() {
		// public void setValues(PreparedStatement ps) throws SQLException {
		// ps.setInt(1, entity.getPluginId());
		// }
		// }, new ResultSetExtractor<Integer>() {
		// public Integer extractData(ResultSet rs) throws SQLException,
		// DataAccessException {
		// if (rs.next())
		// return new Integer(rs.getInt("plugin_id"));
		// return new Integer(-1);
		// }
		// });
		// return val > 0;
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.activePluginEntityIsExists",
					entity);
			return val == null ? false : val > 0;

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	private void insertPrizeSettings(final ActivePluginEntity pluginEntity)
			throws DaoAppException {

		// List<ActivePluginPrizeSettingEntity> entities = pluginEntity
		// .getActivePluginPrizeSettings();
		// if (entities == null || entities.isEmpty()) {
		// return;
		// }
		//
		// // 删除原有数据
		// final String delSqlScript =
		// "DELETE FROM user_plugin_activeplugin_prizesetting WHERE plugin_id=?;";
		// JdbcTemplate template = null;
		// int val = -1;
		//
		// template = this.getJdbcTemplate();
		// val = template.update(delSqlScript, new PreparedStatementSetter() {
		// public void setValues(PreparedStatement ps) throws SQLException {
		// ps.setInt(1, pluginEntity.getPluginId());
		// }
		// });
		//
		// for (final ActivePluginPrizeSettingEntity entity : entities) {
		//
		// final String sqlScript =
		// "insert into user_plugin_activeplugin_prizesetting (plugin_id,option_name,prize_name,amount,rate) values (?,?,?,?,?);";
		// KeyHolder holder = null;
		// val = -1;
		//
		// template = this.getJdbcTemplate();
		// holder = new GeneratedKeyHolder();
		// try {
		// val = template.update(new PreparedStatementCreator() {
		// public PreparedStatement createPreparedStatement(
		// Connection con) throws SQLException {
		// PreparedStatement statement = null;
		// statement = con.prepareStatement(sqlScript);
		// statement.setInt(1, pluginEntity.getPluginId());
		// statement.setString(2, entity.getOptionName());
		// statement.setString(3, entity.getPrizeName());
		// statement.setInt(4, entity.getAmount());
		// statement.setInt(5, entity.getRate());
		// System.out.println(statement.toString());
		// return statement;
		// }
		// }, holder);
		// } catch (Throwable ex) {
		// System.out.println(ex.toString());
		// }
		// if (val > 0)
		// entity.setRecordId(holder.getKey().intValue());
		// }
		SqlSession sqlSession = null;
		Integer val = -1;
		try {
			List<ActivePluginPrizeSettingEntity> entities = pluginEntity
					.getActivePluginPrizeSettings();
			if (entities == null || entities.isEmpty()) {
				return;
			}
			sqlSession = this.getSqlSession();
			val = sqlSession.selectOne("FormPlugin.insertPrizeSettings",
					pluginEntity);
			for (final ActivePluginPrizeSettingEntity entity : entities) {
				val = -1;
				val = sqlSession.selectOne("FormPlugin.insertPrizeSettings",
						entity);
				if (val > 0)
					entity.setRecordId(val);
			}

		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}
}
