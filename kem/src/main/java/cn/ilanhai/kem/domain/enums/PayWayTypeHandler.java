package cn.ilanhai.kem.domain.enums;
 
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import cn.ilanhai.kem.domain.enums.PayWayEnum;
import cn.ilanhai.kem.domain.enums.TrafficuserType; 

/**
 * 支付方式由mysql的int转换为java的String
* @ClassName: : PayWayTypeHandler 
* @Description: TODO
* @author csz
* @date 2017年2月14日 上午9:22:01 
*
 */
public class PayWayTypeHandler extends BaseTypeHandler<String> {
	
 
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getName(rs.getInt(columnName));

    }
 
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getName(rs.getInt(columnIndex));
    }
 
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getName(cs.getInt(columnIndex));
    }
 
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException { 
 
    }
     
   /**
     * 枚举类型转换
     * @param code 数据库中存储的自定义code属性
     * @return code对应的枚举类对对应的值值
     */
    private String getName(int code) {
        return PayWayEnum.getName(code);
    }
}
