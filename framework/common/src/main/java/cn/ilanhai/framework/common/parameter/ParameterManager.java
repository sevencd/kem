package cn.ilanhai.framework.common.parameter;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.ParameterException;

/**
 * 管理参数
 * 启动项目时传入的参数，参数的名称均加-在名字前面
 * 在参数名称后面的值一定是该参数的值，必须有值，并且只能有一个
 * @author Nature
 *
 */
public final class ParameterManager {
	
	public static final String KEY_NAME="name";
	
	private static ParameterManager manager;
	
	static {
		manager=new ParameterManager();
	}
	
	public static ParameterManager getInstance(){
		return manager;
	}
	
	private Logger logger=Logger.getLogger(ParameterManager.class);

	private Map<String,String> parameters=new HashMap<String,String>();
	
	public void init(String[] parameters) throws ParameterException{
		if(parameters==null)return;
		if(parameters.length%2!=0){
			logger.error("参数数量不正确");
			throw new ParameterException("参数数量不正确");

		}
		//从0开始，0一定是参数名称，1一定是值，如此反复
		int index=0;
		while(index<parameters.length){
			//校验参数名称
			if(!parameters[index].startsWith("-")){
				logger.error("参数名称不合法："+parameters[index]);
				throw new ParameterException("参数名称不合法："+parameters[index]);
			}
			this.parameters.put(parameters[index].substring(1), parameters[index+1]);
			index+=2;
		}
		
	} 

	public String getParameterValue(String parameterName){
		if(parameterName==null||parameterName.isEmpty())return null;
		if(this.parameters.containsKey(parameterName)){
			return parameters.get(parameterName);
		}else{
			return null;
		}
	}
}
