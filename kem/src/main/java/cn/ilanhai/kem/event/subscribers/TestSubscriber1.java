package cn.ilanhai.kem.event.subscribers;

import javax.jms.JMSException;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.kem.event.BaseEventSubscriber;
import cn.ilanhai.kem.event.DomainEvent;
import cn.ilanhai.kem.event.DomainEventArgs;
import cn.ilanhai.kem.event.args.*;

/**
 * 测试订阅者
 * @author Nature
 *
 */
@Component
public class TestSubscriber1 extends BaseEventSubscriber{

	protected TestSubscriber1() throws JMSException, JMSAppException {
		super(TestDomainEventArgs1.class);
		this.addRegistEvent(TestDomainEventArgs1.class);
		this.addRegistEvent(TestDomainEventArgs2.class);
	}
	
	public TestDomainEventArgs2 convert(TestDomainEventArgs1 srcArgs){
		
		TestDomainEventArgs2 result=new TestDomainEventArgs2();
		result.setMsg3(srcArgs.getMsg1());
		result.setMsg4(srcArgs.getMsg2());
		return result;
	}

	@Override
	protected void reciveMsg(DomainEvent event, DomainEventArgs args) {
		
		
	}

}
