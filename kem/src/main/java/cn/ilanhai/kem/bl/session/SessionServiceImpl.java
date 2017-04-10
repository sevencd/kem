package cn.ilanhai.kem.bl.session;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.session.enums.ClientTypes;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.mail.protocol.sohu.SohuMailInfo;
import cn.ilanhai.kem.queue.QueueManager;
import cn.ilanhai.kem.queue.msg.MailMsg;
@Component("session")
public class SessionServiceImpl extends BaseBl implements SessionService {

	private Logger logger = Logger.getLogger(SessionServiceImpl.class);

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public String gettoken(RequestContext context) throws BlAppException {
		logger.info("开始创建会话");
		// 校验是否是合法的登录客户端
		if (!ClientTypes.contains(context.getQueryString("ClientType"))) {
			throw new BlAppException(CodeTable.BL_SESSION_WRONGCLIENT.getValue(),
					CodeTable.BL_SESSION_WRONGCLIENT.getDesc());
		}
		logger.info("结束创建会话");
//		List<String> to=new ArrayList<String>();
//		to.add("linghuanxu@qq.com");
//		MailMsg mailMsg=new MailMsg();
//		SohuMailInfo info=new SohuMailInfo();
//		info.setSubject("这是一封测试邮件");
//		info.setHtml("徐哲，你好：\n\t这是一封测试邮件");
//		info.setTo(to);
//		mailMsg.setMsgContent(info);
//		QueueManager.getInstance().put(mailMsg);
		
		// 返回创建的session
		return context.getSession().getSessionId();
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public Integer getsessionstate(RequestContext context) throws BlAppException {
		return SessionImplManger.getSessionState(context.getSession().getSessionState());
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public String logout(RequestContext context) {

		logger.info("开始登出会话");
		SessionImplManger.logout(context);
		logger.info("登出会话成功");
		return null;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public String gethosturl(RequestContext context) {
		return this.getValue(context, "hostUrl");
	}

}
