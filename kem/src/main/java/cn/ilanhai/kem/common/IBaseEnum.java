package cn.ilanhai.kem.common;
/**
 * 
 * @Description 基础Enum，mybatis类型转换。
 * @TypeName IBaseEnum
 * @time 2017年3月2日 上午10:05:21
 * @author csz
 */
public interface IBaseEnum {
	/**
	 * 获取枚举值
	* @Title: getCode 
	* @Description: TODO
	* @return
	* @return Integer    
	* @throws
	 */
	Integer getCode();
	/**
	 * 获取枚举显示值 
	* @Title: getName 
	* @Description: TODO
	* @return
	* @return String    
	* @throws
	 */
	String getName();
}
