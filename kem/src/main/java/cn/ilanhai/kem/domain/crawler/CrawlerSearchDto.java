package cn.ilanhai.kem.domain.crawler;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 
 * @Description 爬虫搜索返回的基本信息
 * @TypeName CrawlerSearchDto
 * @time 2017年3月2日 上午10:05:21
 * @author csz
 */
public class CrawlerSearchDto extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	/*
	 * 记录标识
	 */
	private String id;
	/*
	 * 姓名
	 */
	private String userName;
	/*
	 * 手机号
	 */
	private String phoneNo;
	/*
	 * 职位
	 */
	private String job;
	/*
	 * 一级行业
	 */
	private String industry;
	/*
	 * 二级行业
	 */
	private String industryTwo;
	/*
	 * 公司
	 */
	
	private String company;
	/*
	 * 地址
	 */
	private String address;
	/*
	 * QQ号码
	 */
	private String qq;
	/*
	 * 性别
	 */
	private String sex;
	/*
	 * 年龄
	 */
	private String age;
	/*
	 * QQ签名
	 */
	private String signature;
	/*
	 * 所在地的省
	 */
	private String province;
	/*
	 * 故乡所在的省
	 */
	private String hometownProvince;
	/*
	 * 邮箱地址
	 */
	private String email;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getIndustryTwo() {
		return industryTwo;
	}
	public void setIndustryTwo(String industryTwo) {
		this.industryTwo = industryTwo;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getHometownProvince() {
		return hometownProvince;
	}
	public void setHometownProvince(String hometownProvince) {
		this.hometownProvince = hometownProvince;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
