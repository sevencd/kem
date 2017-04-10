package cn.ilanhai.kem.domain.crawler;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 
 * @Description 爬虫搜索请求Dto
 * @TypeName CrawlerSearchRequestDto
 * @time 2017年3月2日 上午10:05:21
 * @author csz
 */
public class CrawlerSearchRequestDto extends AbstractEntity {

	private static final long serialVersionUID = 1L;
    private Integer startCount;
    private Integer pageSize;
    /*
     * 省
     */
    private String province;
    /*
     * 市
     */
    private String city;
    /*
     * 一级行业num
     */
    private String typeNumOne;
    /*
     * 二级行业num
     */
    private String typeNumTwo;
    /*
     * 网址
     */
    private String url;
    /*
     * 故乡所在的省
     */
    private String hometownProvince;
    /*
     * 故乡所在的市
     */
    private String hometownCity;
    /*
     * 关键字  
     */
    private String keyWord;
    /*
     * 采集来源  
     */
    private String dataFrom;
	public Integer getStartCount() {
		return startCount;
	}
	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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
	public String getTypeNumOne() {
		return typeNumOne;
	}
	public void setTypeNumOne(String typeNumOne) {
		this.typeNumOne = typeNumOne;
	}
	public String getTypeNumTwo() {
		return typeNumTwo;
	}
	public void setTypeNumTwo(String typeNumTwo) {
		this.typeNumTwo = typeNumTwo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHometownProvince() {
		return hometownProvince;
	}
	public void setHometownProvince(String hometownProvince) {
		this.hometownProvince = hometownProvince;
	}
	public String getHometownCity() {
		return hometownCity;
	}
	public void setHometownCity(String hometownCity) {
		this.hometownCity = hometownCity;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getDataFrom() {
		return dataFrom;
	}
	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
}
