package cn.ilanhai.kem.domain.ver;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.uitl.Str;

public abstract class VerEntity extends AbstractEntity implements
		Comparable<VerEntity> {
	private static final long serialVersionUID = 1L;
	protected Integer order = 0;
	protected int ver = 0;
	protected String name = null;
	protected String fileName = null;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) throws Exception {
		this.fileName = fileName;
		this.parserFileName();
	}

	private void parserFileName() throws Exception {
		String[] tmp = null;
		int dot = -1;
		String tmpStr = null;
		try {
			if (this.fileName == null || this.fileName.length() <= 0)
				throw new Exception("文件名错误");
			tmpStr = this.fileName;
			dot = tmpStr.lastIndexOf('.');
			if (dot > -1)
				tmpStr = tmpStr.substring(0, dot);
			if (!tmpStr.contains("@")) {
				this.name = tmpStr;
				return;
			}
			tmp = tmpStr.split("@");
			if (tmp == null || tmp.length != 2)
				return;
			tmpStr = tmp[0];
			if (Str.isNullOrEmpty(tmpStr))
				throw new Exception("文件名中名称错误");
			this.name = tmpStr;
			tmpStr = tmp[1];
			// tmpStr = tmpStr.substring(1);
			if (Str.isNullOrEmpty(tmpStr))
				return;
			tmpStr = tmpStr.toLowerCase().trim();
			if (!tmpStr.contains("v")) {
				this.order = Integer.parseInt(tmpStr);
				return;
			}
			tmp = tmpStr.split("v");
			if (tmp == null || tmp.length != 2)
				return;
			tmpStr = tmp[0];
			this.order = Integer.parseInt(tmpStr);
			tmpStr = tmp[1];
			// tmpStr = tmpStr.substring(1);
			this.ver = Integer.parseInt(tmpStr);
		} catch (NumberFormatException e) {
			throw new Exception("文件名中的排序或版本错误");
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int compareTo(VerEntity o) {
		return this.order.compareTo(o.order);
	}
}
