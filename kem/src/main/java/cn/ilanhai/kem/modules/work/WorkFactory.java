package cn.ilanhai.kem.modules.work;

import org.springframework.context.ApplicationContext;

public class WorkFactory {

		private static ApplicationContext context;
		
		
		/*static{
			context = new ClassPathXmlApplicationContext("kem.xml");
		}*/
		public static void init(ApplicationContext theContext){
			context=theContext;
		}

		static public Work createWork(String workName){
			
			Work bean = (Work)context.getBean(workName);
			
			return bean;
		}
}
