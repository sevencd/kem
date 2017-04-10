package cn.ilanhai.kem.bl.manuscript;

import java.util.HashMap;
import java.util.Map;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.kem.domain.template.SearchTemplateDto;
import cn.ilanhai.kem.domain.template.SearchTemplateResponseEntity;

/**
 * 由于各种稿件查询的方法已经非常复杂了
 * 故抽象该provider集中管理查询复杂度
 * @since 2016-11-14
 * @author Nature
 *
 */
public class ManuscriptSearchProvider{

	private RequestContext context;

	//该provider需要请求上下文处理其内容，需要在构造时传入
	public ManuscriptSearchProvider(RequestContext context) {
		this.context = context;
	}

	//该接口将可以完全替代模板查询
	public SearchTemplateResponseEntity searchTemplate(SearchTemplateDto request) {
		SearchTemplateResponseEntity response = null;
		
		//将请求转换为map装的条件
		Map<String,Object> conditions=new HashMap<String,Object>();
		conditions.put("tagName", request.getTagName());
		conditions.put("templateName", request.getTemplateName());
		conditions.put("isCurrentUser", request.getIsCurrentUser());
		conditions.put("startCount", request.getStartCount());
		conditions.put("pageSize", request.getPageSize());
		conditions.put("userId", request.getUserId());
		conditions.put("currentLoginUserId", request.getCurrentLoginUserId());
		conditions.put("isBackUser", request.isBackUser());
		

		return response;
	}

	//处理前台用户查询模板
	private SearchTemplateResponseEntity frontUserSearchTemplate(Map<String,Object> conditions) {
		SearchTemplateResponseEntity response = null;

		return response;
	}

	//处理后台管理员查询模板
	private SearchTemplateResponseEntity backManagerSearchTemplate(Map<String,Object> conditions) {
		SearchTemplateResponseEntity response = null;

		return response;
	}
}
