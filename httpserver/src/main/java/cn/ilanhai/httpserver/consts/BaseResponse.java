package cn.ilanhai.httpserver.consts;

import java.net.URI;

import cn.ilanhai.framework.common.CodeTable;

/**
 * http接口返回值基类
 * @author Nature
 *
 * @param <DataType>
 */
public class BaseResponse<DataType> {

	/**
	 * 返回结果中的数据
	 */
	private DataType data;
	/**
	 * 返回编码
	 * 正确为0
	 */
	private int code;
	/**
	 * 返回编码的描述
	 */
	private String desc;
	/**
	 * 当前时间戳
	 */
	private long currentTimeMillis;
	/**
	 * 获取服务定位
	 */
	private URI location;
	
	/**
	 * 根据返回错误码枚举设置返回信息
	 * @param responseCode
	 */
	public void setResponseInfo(ResponseCode responseCode){
		this.code=responseCode.getCode();
		this.desc=responseCode.toString();
	}

	public DataType getData() {
		return data;
	}

	public void setData(DataType data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getCurrentTimeMillis() {
		return currentTimeMillis;
	}

	public void setCurrentTimeMillis(long currentTimeMillis) {
		this.currentTimeMillis = currentTimeMillis;
	}

	public URI getLocation() {
		return location;
	}

	public void setLocation(URI location) {
		this.location = location;
	}
	
	
}
