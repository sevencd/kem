package cn.ilanhai.kem.domain.enums;
 
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;


/**
 * 枚举转换类
* @ClassName: : ValidateStatusConvertHandler 
* @Description: TODO
* @author csz
* @date 2017年1月10日 上午9:22:01 
*
 */
public class ValidateStatusConvertHandler extends BaseTypeHandler<ValidateStatus> {
	private Class<ValidateStatus> type;
	 
 
    /**
     * 设置配置文件设置的转换类以及枚举类内容，
     * 供其他方法更便捷高效的实现配置文件中设置的转换类
     * 配置文件中引用方法:
     * @param type
     */
    public ValidateStatusConvertHandler(Class<ValidateStatus> type) {
        if (type == null)
            throw new IllegalArgumentException("类型参数不能被转换");
        this.type = type;
    }
 
    @Override
    public ValidateStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getEnumCode(rs.getInt(columnName));

    }
 
    @Override
    public ValidateStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getEnumCode(rs.getInt(columnIndex));
    }
 
    @Override
    public ValidateStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getEnumCode(cs.getInt(columnIndex));
    }
 
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ValidateStatus parameter, JdbcType jdbcType)
            throws SQLException { 
        ps.setInt(i, parameter.getValue());
 
    }
     
   /**
     * 枚举类型转换
     * @param code 数据库中存储的自定义code属性
     * @return code对应的枚举类
     */
    private ValidateStatus getEnumCode(int code) {
    	ValidateStatus[] objs = type.getEnumConstants();  
        for (ValidateStatus em : objs) {  
            if (em.getValue() == code) {  
                return em;  
            }  
        }  
        throw new IllegalArgumentException("未知的枚举类型：" + code + ",请核对" + type.getSimpleName());
    }
}
