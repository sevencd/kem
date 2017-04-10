package cn.ilanhai.framework.InterfaceUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface InterfaceDocAnnotation {
	public String methodVersion();

	public String iteractionVersion() default "1.0.3";
}
