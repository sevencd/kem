package cn.ilanhai.framework.common.configuration.app.mq;

public interface MQConf {
	String getId();

	void setId(String id);

	String getUserName();

	void setUserName(String userName);

	String getPassword();

	void setPassword(String passworld);

	String getBrokerUrl();

	void setBrokerUrl(String brokerUrl);

	String getProducerClassName();

	void setProducerClassName(String producerClassName);

	String getConsumerClassName();

	void setConsumerClassName(String consumerClassName);

}
