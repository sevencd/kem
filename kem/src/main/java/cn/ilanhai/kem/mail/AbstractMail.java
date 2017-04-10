package cn.ilanhai.kem.mail;

import cn.ilanhai.framework.common.exception.AppException;

import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.Result;

/**
 * 邮件
 * 
 * @author he
 *
 */
public abstract class AbstractMail {

	/**
	 * 发送邮件
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result sendTo(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 查询地址
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result pageQueryAddress(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 添加地址
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result addAddress(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 删除地址
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result deleteAddress(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 修改地址
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result updateAddress(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 查询会员
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result pageQueryMember(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 查询会员
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result queryMember(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 添加会员
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result addMember(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 删除会员
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result deleteMember(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 修改会员
	 * 
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result updateMember(MailConfig config, MailInfo info) throws AppException;

	/**
	 * 按天查询会员信息
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result statdayList(MailConfig config, MailInfo info) throws AppException;

	/**
	 * @param config
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public abstract Result addLabel(MailConfig config, MailInfo info)
			throws AppException;

}
