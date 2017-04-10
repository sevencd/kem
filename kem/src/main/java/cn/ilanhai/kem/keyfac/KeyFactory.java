package cn.ilanhai.kem.keyfac;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.cache.CacheUtil;
import cn.ilanhai.kem.dao.key.KeyConfigDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.key.KeyConfigEntity;
import cn.ilanhai.kem.domain.user.frontuser.dto.SaveUserCodeDto;
import cn.ilanhai.kem.util.LockUtil;

/**
 * 键生存器
 * 
 * @author he
 *
 */
public final class KeyFactory {
	public static final String KEY_TEMPLATE = "1001";// 模板
	public static final String KEY_SPECIAL = "1002";// 专题
	public static final String KEY_EXTENSION = "1003";// 推广

	public static final String KEY_USER = "user";// 用户
	public static final String KEY_MANUSCRIPT = "1004";// 稿件
	public static final String KEY_DET = "1005";// 未登录稿件
	public static final String KEY_ORDER = "odr";// 支付订单
	public static final String KEY_MEMBER = "member";// 会员

	public static final String KEY_SVG = "svg";// svg
	public static final String KEY_CONTACT = "contact";// 联系人
	public static final String KEY_MATERIAL = "mtr";// 素材分类
	public static final String KEY_COMPOSITEMATERIAL = "cmtr";// 组合素材
	public static final String KEY_RESOURCE_IMG = "img";// 素材
	public static final String KEY_EMAIL = "email";// 素材
	public static final String KEY_SMS = "sms";// 素材
	public static final String KEY_CONTACTS_GROUP = "ctg";// 群组
	public static final String KEY_CUSTOMER = "customer";// 客户

	private static final int KEY_REFIX_LEN = 4;
	private static final int DEFAULT_STEP = 64;
	private static final int DEFAULT_SEED = 0;
	private static final int DEFAULT_LEN = 12;
	private static final String digit = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static KeyFactory instance = null;
	private Dao dao = null;
	private boolean flag = false;
	private Cache cache = null;
	// 键值配置
	private Map<String, KeyConfigEntity> conf = null;
	// 剩余可生成键
	private Map<String, Long> counter = null;

	private KeyFactory() {
	}

	// 初始化
	public void init(App app) throws AppException {
		if (this.flag)
			return;
		Iterator<Entity> ls = null;
		KeyConfigEntity configEntity = null;
		long tmp = -1;
		if (app == null)
			throw new AppException("初始化键生成器错误");
		// 获取dao
		this.dao = app.getApplicationContext().getBean(KeyConfigDao.class);
		if (this.dao == null)
			throw new AppException("初始化键生成器数据访问对象错误");
		// 查询键值生成器结果
		ls = this.dao.query(null);
		if (ls == null)
			throw new AppException("初始化键生成器数据错误");
		this.conf = new HashMap<String, KeyConfigEntity>();
		this.counter = new HashMap<String, Long>();
		// 遍历初始化key值配置
		while (ls.hasNext()) {
			configEntity = (KeyConfigEntity) ls.next();
			if (configEntity == null)
				continue;
			if (this.conf.containsKey(configEntity.getPrefix()))
				this.conf.remove(configEntity.getPrefix());
			if (this.counter.containsKey(configEntity.getPrefix()))
				this.counter.remove(configEntity.getPrefix());
			tmp = configEntity.getStep();
			if (tmp <= 0)
				configEntity.setStep(DEFAULT_STEP);
			tmp = configEntity.getSeed();
			if (tmp <= 0)
				configEntity.setSeed(DEFAULT_SEED);
			this.counter.put(configEntity.getPrefix(), configEntity.getSeed());
			tmp = configEntity.getLen();
			if (tmp <= 0)
				configEntity.setLen(DEFAULT_LEN);

			this.conf.put(configEntity.getPrefix(), configEntity);
		}
		if (this.conf == null || this.conf.size() <= 0)
			throw new AppException("初始化键生成器数据错误");
		// 将标志位设置为true，标识着key工厂生效
		try {
			cache = CacheUtil.getInstance().getCache(Integer.parseInt(getValue(app, "cacheIndex")));
		} catch (NumberFormatException | CacheContainerException e) {
			throw new AppException("初始redis化键生成器错误");
		}
		this.flag = true;
	}

	public void inspect() throws DaoAppException, BlAppException {
		synchronized (this) {
			Iterator<Entity> ls = null;
			KeyConfigEntity configEntity = null;
			long tmp = -1;
			ls = this.dao.query(null);
			if (ls == null)
				throw new BlAppException("键生成器数据错误");
			while (ls.hasNext()) {
				configEntity = (KeyConfigEntity) ls.next();
				if (configEntity == null)
					continue;
				if (this.conf.containsKey(configEntity.getPrefix()))
					this.conf.remove(configEntity.getPrefix());
				if (this.counter.containsKey(configEntity.getPrefix()))
					this.counter.remove(configEntity.getPrefix());
				tmp = configEntity.getStep();
				if (tmp <= 0)
					configEntity.setStep(DEFAULT_STEP);
				tmp = configEntity.getSeed();
				if (tmp <= 0)
					configEntity.setSeed(DEFAULT_SEED);
				this.counter.put(configEntity.getPrefix(), configEntity.getSeed());
				tmp = configEntity.getLen();
				if (tmp <= 0)
					configEntity.setLen(DEFAULT_LEN);

				this.conf.put(configEntity.getPrefix(), configEntity);
			}
		}
	}

	public static KeyFactory getInstance() {
		if (instance == null)
			instance = new KeyFactory();
		return instance;
	}

	// 获取新的key
	private String getKey1(String prefix) throws AppException {
		KeyConfigEntity configEntity = null;
		KeyConfigEntity entity = null;

		long c = -1;// 剩余可生成键
		long seed = -1;// 种子，新键值的尾数
		int len = -1;// 新键值的长度
		int step = -1;// 新键值每次更新获得的剩余可生成键数
		if (!this.flag)
			return null;
		if (prefix == null || prefix.length() <= 0)
			return null;
		if (!this.counter.containsKey(prefix))
			return null;
		if (!this.conf.containsKey(prefix))
			return null;
		// 线程同步获得新key
		synchronized (this) {
			configEntity = this.conf.get(prefix);
			if (configEntity == null)
				return null;
			len = configEntity.getLen();
			step = configEntity.getStep();
			if (step <= 0)
				step = DEFAULT_STEP;
			// 获取剩余可生成键
			c = this.counter.get(prefix);
			// 获取配置
			seed = configEntity.getSeed();
			// 如果剩余可生成键等于步长
			if (c == step) {
				// 修改配置文件
				configEntity.setSeed(seed + step);
				this.dao.save(configEntity);
				// 重新读取配置
				entity = (KeyConfigEntity) this.dao.query(configEntity, false);
				if (entity == null)
					throw new AppException("同数据出错");
				configEntity.setSeed(entity.getSeed());
				// 重置剩余可用键值
				c = 0;
			}
			// 剩余可用键值加1
			++c;
			// 种子加一
			seed += 1;
			configEntity.setSeed(seed);
			this.counter.remove(prefix);
			this.counter.put(prefix, c);
		}
		// 生成新的键值
		String formate = "%s%0";
		formate = formate.concat(String.valueOf(len));
		formate = formate.concat("d");
		return String.format(formate, prefix, seed);
	}

	// 获取新的key
	public String getKey(String prefix) throws AppException {
		KeyConfigEntity configEntity = null;
		KeyConfigEntity entity = null;
		long c = -1;// 剩余可生成键
		long tmp = -1;// 种子，新键值的尾数
		logger.info("key flag:" + this.flag);
		if (!this.flag)
			return null;
		if (prefix == null || prefix.length() <= 0) {
			logger.info("key prefix:" + prefix);
			return null;
		}
		if (!this.counter.containsKey(prefix)) {
			logger.info("key counter: false");
			return null;
		}
		if (!this.conf.containsKey(prefix)) {
			logger.info("key conf:false");
			return null;
		}
		// 线程同步获得新key
		synchronized (this) {
			configEntity = this.conf.get(prefix);
			if (configEntity == null) {
				logger.info("key conf:null");
				return null;
			}
			tmp = configEntity.getSeed();
			c = this.counter.get(prefix);
			logger.info("key c:" + c);
			logger.info("key tmp:" + tmp);
			// 准备
			if (c >= tmp) {
				logger.info("更新前configEntity:" + configEntity);
				long step = c - tmp;// 超出的步长
				tmp = configEntity.getSeed();
				tmp += configEntity.getStep();
				tmp += step;
				configEntity.setSeed(tmp);
				try {
					configEntity.setUpdateTime(new Date());
					logger.info("更新后configEntity:" + configEntity);
					int i = this.dao.save(configEntity);
					logger.info("更新后configEntity结果:" + i);
					entity = (KeyConfigEntity) this.dao.query(configEntity, false);
					logger.info("更新后configEntity结果:" + entity);
					if (i <= 0) {
						c = tmp;
						this.counter.put(prefix, c);
						throw new AppException("主键生成同数据出错");
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}
				logger.info("key seed rest");
			}
			// 剩余可用键值加1
			c++;
			this.counter.put(prefix, c);
			logger.info("key new c:" + c);
			// 生成新的键值
		}
		// StringBuilder sb = null;
		// sb = new StringBuilder(prefix);
		// sb.append(System.currentTimeMillis());
		// logger.info("key mac:" + getLocalMac());
		// sb.append(getLocalMac());
		// String formate = "%0";
		// tmp = configEntity.getLen();
		// formate = formate.concat(String.valueOf(tmp));
		// formate = formate.concat("d");
		// sb.append(String.format(formate, c));
		// logger.info("key:" + sb.toString());
		// return sb.toString();
		String str = "%0";
		logger.info("key create");
		tmp = configEntity.getLen();
		logger.info("key len:" + tmp);
		str = str.concat(String.valueOf(tmp));
		str = str.concat("d");
		logger.info("key formate:" + str);
		str = String.format(str, c);
		logger.info("key value:" + str);
		str = String.format("%s%s%s", prefix, getLocalMac(), str);
		logger.info("key:" + str);
		return str;

	}

	public static String newKey(String keyType) {
		try {
			return newKey(Key.getEnum(keyType));
		} catch (Exception e) {
			return null;
		}
	}

	public static String newKey(Key key) {
		try {
			return KeyFactory.getInstance().getKeyByRedis(key);
		} catch (Exception e) {
			return null;
		}
	}

	public static List<String> newKey(Key key, int amount) {
		try {
			return KeyFactory.getInstance().getKeyByRedis(key, amount);
		} catch (Exception e) {
			return null;
		}
	}

	public static void inspects() throws DaoAppException, BlAppException {
		KeyFactory.getInstance().inspect();
		logger.info("doinspects");
	}

	// 获取键值的头
	public static String getKeyHeadByKey(String key) {
		if (key == null || key.length() < KEY_REFIX_LEN)
			return null;
		return key.substring(0, KEY_REFIX_LEN);
	}

	private static long getLocalMac() {
		InetAddress inetAddress = null;
		byte[] mac = null;
		int val = 0;
		try {
			logger.info("begin");
			logger.info("getLocalHost");
			inetAddress = InetAddress.getLocalHost();
			logger.info("getByInetAddress");
			mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
			logger.info("getHardwareAddress");
			// return mac[5] & 0xFF | (mac[4] & 0xFF) << 8 | (mac[3] & 0xFF) <<
			// 16
			// | (mac[2] & 0xFF) << 24 | (mac[1] & 0xFF) << 32
			// | (mac[0] & 0xFF) << 40;
			if (mac == null || mac.length < 6)
				return 0;
			val += Math.abs(mac[5]);
			val += Math.abs(mac[4]);
			val += Math.abs(mac[3]);
			val += Math.abs(mac[2]);
			val += Math.abs(mac[1]);
			val += Math.abs(mac[0]);
			logger.info("end");
			return val;
		} catch (SocketException e) {
			e.printStackTrace();
			return 0;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public String getKeyByRedis(Key key) {
		if (key == null) {
			return null;
		}
		try {
			List<String> temp = cache.evalsha(key.getKey(), 1);
			return temp.get(0);
		} catch (CacheContainerException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getKeyByRedis(Key key, int amuont) {
		List<String> strs = new ArrayList<String>();
		if (amuont <= 0 || key == null) {
			return strs;
		}
		try {
			strs = cache.evalsha(key.getKey(), amuont);
		} catch (CacheContainerException e) {
			e.printStackTrace();
			return strs;
		}
		return strs;
	}

	protected String getValue(App app, String key) {
		Map<String, Object> settings = null;
		if (key == null || key.length() <= 0)
			return null;
		settings = app.getConfigure().getSettings();
		if (settings == null)
			return null;
		if (!settings.containsKey(key))
			return null;
		return (String) settings.get(key);
	}

	/**
	 * baseString 递归调用
	 * 
	 * @param num
	 *            十进制数
	 * @param base
	 *            要转换成的进制数
	 */
	private static String baseString(int num, int base) {
		String str = "";
		if (num == 0) {
			return "";
		} else {
			str = baseString(num / base, base);
			return str + digit.charAt(num % base);
		}
	}

	public String newCode(String userId) {
		if (Str.isNullOrEmpty(userId)) {
			return null;
		}
		IdEntity<String> id = new IdEntity<String>();
		id.setId(userId);
		SaveUserCodeDto code;
		synchronized (LockUtil.getUserLock(userId)) {
			try {
				code = (SaveUserCodeDto) dao.query(id, false);
			} catch (DaoAppException e1) {
				e1.printStackTrace();
				return null;
			}
			if (code != null && !Str.isNullOrEmpty(code.getCode())) {
				return code.getCode();
			}
			code = new SaveUserCodeDto();
			long temp = 0;
			try {
				temp = cache.incr(Key.KEY_COMPOSITEMATERIAL.getKey());
			} catch (CacheContainerException e) {
				e.printStackTrace();
			}
			int a = (int) ((temp / 1000) + 1);
			String str = "%0";
			str = str.concat(String.valueOf(Key.KEY_COMPOSITEMATERIAL.getLength()));
			str = str.concat("d");
			str = String.format(str, temp % 1000);
			str = baseString(a, 26) + str;
			code.setCode(str);
			code.setUserId(userId);
			try {
				IdEntity<String> strCode = (IdEntity<String>) dao.query(code, false);
				if (strCode != null && str.equals(strCode.getId())) {
					return newCode(userId);
				}
				dao.save(code);
			} catch (DaoAppException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	private static Logger logger = Logger.getLogger(KeyFactory.class);
}
