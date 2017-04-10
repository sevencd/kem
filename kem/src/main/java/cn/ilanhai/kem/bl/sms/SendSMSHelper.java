package cn.ilanhai.kem.bl.sms;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import cn.ilanhai.kem.util.SmsHelper;

public class SendSMSHelper implements Runnable {

	private Logger logger=Logger.getLogger(SendSMSHelper.class);
	
	private String phoneNo;
	private String smsContent;
	private String url;

	public SendSMSHelper(String phoneNo, String smsContent, String url) {
		this.phoneNo = phoneNo;
		this.smsContent = smsContent;
		this.url = url;
	}
	
	@Override
	public void run() {
		logger.info("发送短信：电话：" + phoneNo + "内容" + smsContent);
		try {
			SmsHelper.sendSms(phoneNo, smsContent, url);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("发送短信成功，电话：" + phoneNo);
	}

}
