package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.sql.Date;

/**
 * 地址
 * 
 * @author he
 *
 */
public class Address {

	public Address() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ListType getListType() {
		return listType;
	}

	public void setListType(ListType listType) {
		this.listType = listType;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtUpdated() {
		return gmtUpdated;
	}

	public void setGmtUpdated(Date gmtUpdated) {
		this.gmtUpdated = gmtUpdated;
	}

	/**
	 * 地址列表的名称
	 */
	private String name;
	/**
	 * 列表别称地址, 使用该别称地址进行调用, 格式为xxx@maillist.sendcloud.org
	 */
	private String address;
	/**
	 * 地址列表包含的地址个数
	 */
	private int memberCount;
	/**
	 * 地址列表描述
	 */
	private String description;
	/**
	 * 地址列表类型
	 */
	private ListType listType;
	/**
	 * 地址列表创建时间
	 */
	private Date gmtCreated;
	/**
	 * 地址列表修改时间
	 */
	private Date gmtUpdated;

	/**
	 * 列表的类型
	 * 
	 * @author he
	 *
	 */
	public enum ListType {

		/**
		 * 0: 普通地址列表, 默认为0
		 */
		GeneralAddressList;

	}

}
