package cn.ilanhai.kem.dao.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import cn.ilanhai.framework.uitl.FastJson;

public class MapTypeHandler implements TypeHandler<Map<String, Boolean>> {

	@Override
	public void setParameter(PreparedStatement ps, int i,
			Map<String, Boolean> parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, FastJson.bean2Json(parameter));
	}

	@Override
	public Map<String, Boolean> getResult(ResultSet rs, String columnName)
			throws SQLException {
		String tmp = rs.getString(columnName);
		return FastJson.json2Bean(tmp, Map.class);
	}

	@Override
	public Map<String, Boolean> getResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String tmp = rs.getString(columnIndex);
		return FastJson.json2Bean(tmp, Map.class);
	}

	@Override
	public Map<String, Boolean> getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String tmp = cs.getString(columnIndex);
		return FastJson.json2Bean(tmp, Map.class);
	}

}
