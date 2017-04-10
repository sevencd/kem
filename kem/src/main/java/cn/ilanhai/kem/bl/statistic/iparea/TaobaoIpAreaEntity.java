package cn.ilanhai.kem.bl.statistic.iparea;
/**
 * 淘宝网
 * @author dgj
 *
 */
public class TaobaoIpAreaEntity {
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
	private String region;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 网络供应商
	 */
	private String isp;

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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

}
