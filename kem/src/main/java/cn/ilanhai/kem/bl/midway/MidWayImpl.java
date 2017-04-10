package cn.ilanhai.kem.bl.midway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.midway.PullPublishedEntity;
import cn.ilanhai.kem.domain.midway.PullPublishedRequestDto;
import cn.ilanhai.kem.domain.midway.PullPublishedResponseDto;

@Component("midway")
public class MidWayImpl extends BaseBl implements MidWay {

	private String desc = "MidWay";

	private String pcurl = "";
	private String wapurl = "";
	private String pcpreurl = "";
	private String wappreurl = "";

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public List<PullPublishedResponseDto> pullpublished(RequestContext context)
			throws BlAppException, DaoAppException {
		pcurl = this.getValue(context, "pcurl");
		wapurl = this.getValue(context, "wapurl");
		pcpreurl = this.getValue(context, "pcpreurl");
		wappreurl = this.getValue(context, "wappreurl");
		// 校验请求合法性
		String relationId = context.getDomain(String.class);
		// 获取所有内容，并返回
		Dao dao = this.daoProxyFactory.getDao(context);
		valiDaoIsNull(dao, desc);
		IdEntity<String> queryParams = new IdEntity<String>();
		queryParams.setId(relationId);
		Iterator<Entity> result = dao.query(queryParams);

		List<PullPublishedResponseDto> response = new ArrayList<PullPublishedResponseDto>();
		while (result.hasNext()) {
			PullPublishedResponseDto dto = new PullPublishedResponseDto();

			PullPublishedEntity entity = (PullPublishedEntity) result.next();

			dto.setKid(entity.getKid());
			dto.setMain_domain(this.getMain_domain(entity));
			dto.setMobile_host(this.getMobile_host(entity));
			dto.setApi_host(this.getValue(context, "apihost"));

			response.add(dto);
		}

		return response;
	}

	private String getMain_domain(PullPublishedEntity entity) {
		StringBuilder url = new StringBuilder();
		url.append(entity.getKid());
		switch (entity.getRelationType()) {
		case 1:
			url.append(this.pcpreurl);
			break;
		case 2:
			url.append(this.pcpreurl);
			break;
		case 3:
			url.append(this.pcurl);
			break;
		}

		return url.toString();
	}

	private String getMobile_host(PullPublishedEntity entity) {
		StringBuilder url = new StringBuilder();
		url.append(entity.getKid());
		switch (entity.getRelationType()) {
		case 1:
			url.append(this.wappreurl);
			break;
		case 2:
			url.append(this.wappreurl);
			break;
		case 3:
			url.append(this.wapurl);
			break;
		}

		return url.toString();
	}

}
