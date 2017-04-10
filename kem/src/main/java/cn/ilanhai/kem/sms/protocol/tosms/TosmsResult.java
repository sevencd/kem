package cn.ilanhai.kem.sms.protocol.tosms;

import cn.ilanhai.kem.sms.protocol.Result;

/**
 * 发送邮件返回值
 * 
 * @author he
 *
 */
public class TosmsResult extends Result {

	public TosmsResult() {

	}

	public ReturnValue getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(ReturnValue returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * 反回值
	 */
	private ReturnValue returnValue;

	/**
	 * 
	 * @author he
	 *
	 */
	public enum ReturnValue {

		/**
		 * 成功
		 */
		Success(1),
		/**
		 * 其他系统错误
		 */
		OtherSystemError(0),
		/**
		 * 短信余额不足
		 */
		ShortMessage(-1),
		/**
		 * 资金账户被锁定
		 */
		TheFundsAccountIsLocked(-2),
		/**
		 * 用户被锁定
		 */
		TheUserIsLocked(-3),
		/**
		 * 号码在黑名单内
		 */
		NumberInTheBlackList(-4),
		/**
		 * 用户名或密码不正确
		 */
		TheUserNameOrPasswordIsIncorrect(-5),
		/**
		 * 号码不正确
		 */
		TheNumberIsNotCorrect(-6),
		/**
		 * 接口连接失败
		 */
		InterfaceConnectionFails(-7),
		/**
		 * 号码格式错误
		 */
		TheNumberFormatIsIncorrect(-8),
		/**
		 * 通道编号错误
		 */
		ChannelNumberIsWrong(-9),
		/**
		 * 定时发送时间不正确
		 */
		TimedTransmissionTimeIsIncorrect(-10),
		/**
		 * 没有输入短信内容
		 */
		DidNotEnterTheMessageContent(-11),
		/**
		 * 短信内容超出长度限制
		 */
		TheMessageContentExceedsTheLengthLimit(-12),
		/**
		 * 内容含非法关键字
		 */
		ContentContainsIllegalKeywords(-15),
		/**
		 * 超出发送时间范围
		 */
		OutOfRangeTransmissionTimeRange(-16),
		/**
		 * 通道被关闭
		 */
		ChannelIsTurnedOff(-17),
		/**
		 * 短信内容没有签名
		 */
		SMSContentIsNotSigned(-18),
		/**
		 * 手机未认证
		 */
		ThePhoneIsNotCertified(-30),
		/**
		 * 身份未认证
		 */
		IsNotCertified(-31);
		private int val;

		private ReturnValue(int v) {
			this.val = v;
		}

		public static ReturnValue valueOf(int v) {
			switch (v) {
			case 1:
				return Success;
			case 0:
				return OtherSystemError;
			case -1:
				return ShortMessage;
			case -2:
				return TheFundsAccountIsLocked;
			case -3:
				return TheUserIsLocked;
			case -4:
				return NumberInTheBlackList;
			case -5:
				return TheUserNameOrPasswordIsIncorrect;
			case -6:
				return TheNumberIsNotCorrect;
			case -7:
				return InterfaceConnectionFails;
			case -8:
				return ChannelNumberIsWrong;
			case -9:
				return ChannelNumberIsWrong;
			case -10:
				return TimedTransmissionTimeIsIncorrect;
			case -11:
				return DidNotEnterTheMessageContent;
			case -12:
				return TheMessageContentExceedsTheLengthLimit;
			case -15:
				return ContentContainsIllegalKeywords;
			case -16:
				return OutOfRangeTransmissionTimeRange;
			case -17:
				return ChannelIsTurnedOff;
			case -18:
				return SMSContentIsNotSigned;
			case -30:
				return ThePhoneIsNotCertified;
			case -31:
				return IsNotCertified;
			default:
				return null;
			}
		}
	}
}
