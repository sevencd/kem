package cn.ilanhai.kem.common;

/**
 * 实际值为整数的枚举推荐实现该接口，以方便系统统一操作
 * @author Nature
 *
 */
public interface IntegerEnum{

	Enum valueOf(Integer value);
}
