package cn.ilanhai.kem.util;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.keyfac.KeyFactory;

public class KeyUtil {
	public static String getKey(ManuscriptType type) {
		switch (type) {
		case TEMPLATE:
			return KeyFactory.KEY_TEMPLATE;
		case SPECIAL:
			return KeyFactory.KEY_SPECIAL;
		case EXTENSION:
			return KeyFactory.KEY_EXTENSION;
		case EXCELLENTCASE:
			return KeyFactory.KEY_MANUSCRIPT;
		default:
			return null;
		}
	}

	public static ManuscriptType getKey(String manuscriptId) {
		if (Str.isNullOrEmpty(manuscriptId)) {
			return ManuscriptType.DEF;
		}
		if (manuscriptId.startsWith(KeyFactory.KEY_TEMPLATE)) {
			return ManuscriptType.TEMPLATE;
		} else if (manuscriptId.startsWith(KeyFactory.KEY_SPECIAL)) {
			return ManuscriptType.SPECIAL;
		} else if (manuscriptId.startsWith(KeyFactory.KEY_EXTENSION)) {
			return ManuscriptType.EXTENSION;
		} else if (manuscriptId.startsWith(KeyFactory.KEY_MANUSCRIPT)) {
			return ManuscriptType.EXCELLENTCASE;
		} else if (manuscriptId.startsWith(KeyFactory.KEY_DET)) {
			return ManuscriptType.DEF;
		}
		return null;
	}

	public static String getName(String manuscriptId) {
		if (Str.isNullOrEmpty(manuscriptId)) {
			return "临时稿件";
		}
		if (manuscriptId.startsWith(KeyFactory.KEY_TEMPLATE)) {
			return "模板";
		} else if (manuscriptId.startsWith(KeyFactory.KEY_SPECIAL)) {
			return "专题";
		} else if (manuscriptId.startsWith(KeyFactory.KEY_EXTENSION)) {
			return "推广";
		} else if (manuscriptId.startsWith(KeyFactory.KEY_MANUSCRIPT)) {
			return "优秀案例";
		} else if (manuscriptId.startsWith(KeyFactory.KEY_DET)) {
			return "临时稿件";
		}
		return null;
	}

	public static String getName(ManuscriptType type) {
		if (type == null) {
			return "临时稿件";
		}
		if (ManuscriptType.TEMPLATE.equals(type)) {
			return "模板";
		} else if (ManuscriptType.SPECIAL.equals(type)) {
			return "专题";
		} else if (ManuscriptType.EXTENSION.equals(type)) {
			return "推广";
		} else if (ManuscriptType.EXCELLENTCASE.equals(type)) {
			return "优秀案例";
		} else if (ManuscriptType.DEF.equals(type)) {
			return "临时稿件";
		}
		return null;
	}
}
