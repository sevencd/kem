package cn.ilanhai.httpserver.modules.requestoverlap;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.activemq.filter.function.inListFunction;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;

import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;

/**
 * 过滤规则
 * 
 * @author he
 *
 */
public class FiltersRule {
	/**
	 * 文件名
	 */
	private static final String FILE_NAME = "rquestOverlap.json";

	public FiltersRule() {
		this.rules = new ArrayList<Rule>();
	}

	public static FiltersRule getFiltersRule() throws IOException {
		ClassLoader classLoader = null;
		InputStream inputStream = null;
		FiltersRule filtersRule = null;
		byte[] buf = null;
		String tmp = null;
		try {
			classLoader = FiltersRule.class.getClassLoader();
			inputStream = classLoader.getResourceAsStream(FILE_NAME);
			buf = new byte[inputStream.available()];
			inputStream.read(buf);
			if (buf == null || buf.length <= 0)
				return null;
			tmp = new String(buf, "utf-8");
			if (tmp == null || tmp.length() <= 0)
				return null;
			filtersRule = FastJson.json2Bean(tmp, FiltersRule.class);
			return filtersRule;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

	}

	/**
	 * 是否匹配
	 * 
	 * @param url
	 * @return
	 */
	public boolean isMatching(String url) {
		Rule rule = null;
		Iterator<Rule> iterator = null;
		boolean flag = false;
		if (Str.isNullOrEmpty(url))
			return false;
		if (this.rules == null || this.rules.size() <= 0)
			return false;
		iterator = this.rules.iterator();
		while (iterator.hasNext()) {
			rule = iterator.next();
			if (rule == null)
				continue;
			flag = rule.isMatching(this.ignoreCase, url);
			logger.info(String.format(
					"ignoreCase:%s url:%s impreciseMatching:%s url:%s flag:%s",
					this.ignoreCase, url, rule.isImpreciseMatching(),
					rule.getUrl(), flag));
			if (flag)
				break;
		}
		return flag;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public ArrayList<Rule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}

	private static final Logger logger = Logger.getLogger(FiltersRule.class);
	/**
	 * 是否忽略大小写
	 */
	private boolean ignoreCase = true;
	/**
	 * 规则
	 */
	private ArrayList<Rule> rules = null;
}
