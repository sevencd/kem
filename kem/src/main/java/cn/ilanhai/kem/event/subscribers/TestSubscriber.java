package cn.ilanhai.kem.event.subscribers;

import javax.jms.JMSException;

import org.springframework.stereotype.Component;

import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.kem.event.BaseEventSubscriber;
import cn.ilanhai.kem.event.DomainEvent;
import cn.ilanhai.kem.event.DomainEventArgs;
import cn.ilanhai.kem.event.args.FrontUserRegistEvent;

@Component
public class TestSubscriber extends BaseEventSubscriber{

	protected TestSubscriber() throws JMSException, JMSAppException {
		super(FrontUserRegistEvent.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void reciveMsg(DomainEvent event, DomainEventArgs args) {
		// TODO Auto-generated method stub
		
	}

}
