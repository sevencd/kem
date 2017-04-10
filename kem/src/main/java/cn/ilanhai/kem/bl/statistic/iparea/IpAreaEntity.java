package cn.ilanhai.kem.bl.statistic.iparea;

public class IpAreaEntity {
	/**
	 * Ip地址
	 */
	private String ip;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 网络供应商
	 */
	private String operator;
	private String lat;
	private String lng;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
}
