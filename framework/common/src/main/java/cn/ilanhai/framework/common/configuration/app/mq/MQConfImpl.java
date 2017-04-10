package cn.ilanhai.framework.common.configuration.app.mq;

public  class MQConfImpl implements MQConf {
	private String id;
	private String userName;
	private String password;
	private String brokerUrl;
	private String producerClassName;
	private String consumerClassName;

	public MQConfImpl() {

	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public String getProducerClassName() {
		return producerClassName;
	}

	public void setProducerClassName(String producerClassName) {
		this.producerClassName = producerClassName;
	}

	public String getConsumerClassName() {
		return consumerClassName;
	}

	public void setConsumerClassName(String consumerClassName) {
		this.consumerClassName = consumerClassName;
	}


}
