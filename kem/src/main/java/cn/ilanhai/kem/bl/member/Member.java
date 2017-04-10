package cn.ilanhai.kem.bl.member;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.domain.member.dto.MemberStatusDto;

/**
 * 会员相关接口均在此提供
 * 2016-11-16
 * @author Nature
 *
 */
public interface Member {

	/**
	 * 获取会员状态
	 * @param context
	 * @return 0：非会员，1：会员
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	MemberStatusDto status(RequestContext context) throws BlAppException, DaoAppException;
}
