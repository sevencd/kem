package cn.ilanhai.kem.schedule;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.RequestContextImpl;
import cn.ilanhai.framework.app.dao.Dao;

/**
 * 基础计划任务类
 * @author Nature
 *
 */
public class ScheduleContext {
	
	private static Logger logger = Logger.getLogger(ScheduleContext.class);

	//应用程序上下文
	private static Application application;
	
	public static void init(Application application) throws SchedulerException{
		//获得应用程序上下问题
		ScheduleContext.application=application;
		
		JobDetail jobDetail= JobBuilder.newJob(MemberSchedule.class)
                .withIdentity("MemberSchedule","member")
                .build();

        Trigger trigger= TriggerBuilder
                .newTrigger()
                .withIdentity("MemberSchedule","member")
                .startNow()
                //.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(13, 59))
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 1))
                .build();
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        sched.scheduleJob(jobDetail,trigger);

        sched.start();
	}
	
	public static Dao getDao(Class<?> clazz){
		Dao dao=(Dao) application.getApplicationContext().getBean(clazz);
		dao.setApplication(application);
		return dao;
	}
	
	public static RequestContext getRequestContext(){
		
		RequestContext context=new RequestContextImpl();
		context.setApplication(application);
		
		return context;
	}

}
