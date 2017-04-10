package cn.ilanhai.kem.upgrade;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.ver.DbVerDao_back;
import cn.ilanhai.kem.dao.ver.MyBatisDbVerDao;
import cn.ilanhai.kem.dao.ver.SqlScriptDao;
import cn.ilanhai.kem.domain.ver.DbVerEntity;
import cn.ilanhai.kem.domain.ver.ScriptFile;

/**
 * 
 *
 */
public class DbUpdater extends Updater {
	// db_ver表dao
	private MyBatisDbVerDao dbVerDao = null;
	// 脚本执行dao
	private SqlScriptDao sqlScriptDao = null;
	// 应用程序上下文
	private Application application = null;
	// 存放所有数据库文件
	private Map<String, Map<Integer, ScriptFile>> scriptFile = null;
	// 所有表记录
	private Map<String, DbVerEntity> dbVer = null;
	// 日志
	private Logger logger = Logger.getLogger(DbUpdater.class);

	public DbUpdater() {

		this.scriptFile = new HashMap<String, Map<Integer, ScriptFile>>();
		this.dbVer = new HashMap<String, DbVerEntity>();
	}

	// 初始化升级数据
	@Override
	public boolean init(Application app) throws AppException {
		this.logger.info("begin init");
		ApplicationContext ctx = null;
		if (app == null)
			throw new AppException("升级-应用错误");
		this.application = app;
		ctx = this.application.getApplicationContext();
		// 获得dao
		this.dbVerDao = ctx.getBean(MyBatisDbVerDao.class);
		this.sqlScriptDao = ctx.getBean(SqlScriptDao.class);
		// 测试数据库连接
		if (!this.sqlScriptDao.testConnectDb())
			throw new AppException("升级-数据库连接错误");
		// 加载
		this.loadDbVer();
		this.loadScriptFile();
		return true;
	}

	/**
	 * 加载本地脚本数据
	 * 
	 * @throws AppException
	 */
	private void loadScriptFile() throws AppException {
		Resource[] res = null;
		ApplicationContext ctx = null;
		ScriptFile sf = null;
		Map<Integer, ScriptFile> tmp = null;
		try {
			this.logger.info("begin load script file");
			ctx = this.application.getApplicationContext();
			res = ctx.getResources("classpath:upgrade/dbupdater/*.sql");
			if (res == null || res.length <= 0)
				return;
			for (int i = 0; i < res.length; i++) {
				sf = new ScriptFile();
				sf.setFileName(res[i].getFilename());
				sf.setResource(res[i]);
				this.logger.info(res[i].getURI());
				tmp = this.scriptFile.get(sf.getName());
				if (tmp == null) {
					tmp = new HashMap<Integer, ScriptFile>();
					this.scriptFile.put(sf.getName(), tmp);
				}
				if (tmp.containsKey(sf.getVer()))
					tmp.remove(sf.getVer());
				tmp.put(sf.getVer(), sf);
			}
			this.logger.info("end load script file");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BlAppException(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BlAppException(e.getMessage(), e);
		}
	}

	/**
	 * 加载dbver
	 * 
	 * @throws DaoAppException
	 */
	private void loadDbVer() throws DaoAppException {
		Iterator<Entity> iterator = null;
		DbVerEntity dv = null;
		// 检查dbver表是否存在
		if (!this.sqlScriptDao.dbVerTableIsExists())
			return;
		// 查询表中所有记录
		iterator = this.dbVerDao.query(null);
		if (iterator == null)
			return;
		while (iterator.hasNext()) {
			dv = (DbVerEntity) iterator.next();
			if (dv == null)
				continue;
			if (dv.getName() == null)
				continue;
			if (this.dbVer.containsKey(dv.getName()))
				this.dbVer.remove(dv.getName());
			this.dbVer.put(dv.getName(), dv);
		}
	}

	/**
	 * @param sf
	 * @throws BlAppException
	 * @throws IOException
	 */
	private void loadFileContent(ScriptFile sf) throws BlAppException,
			IOException {
		Resource resource = null;
		InputStream fis = null;
		if (sf == null)
			return;
		resource = sf.getResource();
		if (resource == null)
			return;
		if (!resource.isReadable())
			return;
		try {
			fis = resource.getInputStream();
			int size = fis.available();
			byte[] buffer = new byte[size];
			fis.read(buffer);
			sf.setScript(new String(buffer, "utf-8"));
		} catch (IOException e) {

			e.printStackTrace();
			throw new BlAppException(e.getMessage(), e);
		} finally {
			if (fis != null)
				fis.close();
		}
	}

	@Override
	public boolean update() throws AppException {
		List<ScriptFile> scriptFiles = null;
		Iterator<ScriptFile> scriptFileIterator = null;
		ScriptFile scriptFile = null;
		try {
			// 获取一个脚本多版本时的最新版本
			scriptFiles = this.getVersionLastUpdateScriptFile();
			if (scriptFiles == null || scriptFiles.size() <= 0)
				return true;
			// 与当前的脚本进行比较，获取要升级的脚本
			scriptFiles = this.getUpdateScriptFile(scriptFiles);
			if (scriptFiles == null || scriptFiles.size() <= 0)
				return true;
			// 对脚本文件进行排序
			this.logger.info("Sort");
			Collections.sort(scriptFiles);
			for (ScriptFile sf : scriptFiles) {
				this.logger.info(sf.getFileName());
			}
			scriptFileIterator = scriptFiles.iterator();
			while (scriptFileIterator.hasNext()) {
				scriptFile = scriptFileIterator.next();
				this.loadFileContent(scriptFile);
				if (scriptFile.getScript() != null) {
					this.logger.info(scriptFile.getFileName());
					this.sqlScriptDao
							.execScript("DROP PROCEDURE IF EXISTS exe;");
					this.sqlScriptDao.execScript(scriptFile.getScript());
					this.sqlScriptDao.execScript("CALL exe();");
				}
			}
			this.saveDbVer();
			return true;
		} catch (Exception e) {
			throw new AppException(e.getMessage(), e);
		}
	}

	/**
	 * 获取一个脚本多版本时的最新版本
	 * 
	 * @return
	 */
	private List<ScriptFile> getVersionLastUpdateScriptFile() {
		Map<Integer, ScriptFile> map = null;
		List<ScriptFile> scriptFiles = null;
		List<Integer> vers = null;
		Iterator<String> scriptFileIterator = null;
		Iterator<Integer> verIterator = null;
		ScriptFile scriptFile = null;
		this.logger.info("GetVersionLastUpdateScriptFile");
		if (this.scriptFile == null || this.scriptFile.size() <= 0)
			return null;
		scriptFileIterator = this.scriptFile.keySet().iterator();
		scriptFiles = new ArrayList<ScriptFile>();
		vers = new ArrayList<Integer>();
		while (scriptFileIterator.hasNext()) {
			map = this.scriptFile.get(scriptFileIterator.next());
			verIterator = map.keySet().iterator();
			vers.clear();
			while (verIterator.hasNext())
				vers.add(verIterator.next());
			Collections.sort(vers);
			scriptFile = map.get(vers.get(vers.size() - 1));
			if (scriptFile == null)
				continue;
			this.logger.info(scriptFile.getFileName());
			scriptFiles.add(scriptFile);
		}
		return scriptFiles;
	}

	/**
	 * 与当前的脚本进行比较，获取要升级的脚本
	 * 
	 * @param sf
	 * @param dv
	 * @return
	 * @throws Exception
	 */
	private List<ScriptFile> getUpdateScriptFile(List<ScriptFile> sf)
			throws Exception {
		List<ScriptFile> tmp = null;
		Iterator<ScriptFile> scriptFileIterator = null;
		ScriptFile scriptFile = null;
		DbVerEntity dbVerEntity = null;
		this.logger.info("GetUpdateScriptFile");
		if (sf == null || sf.size() <= 0)
			return null;
		tmp = new ArrayList<ScriptFile>();
		if (this.dbVer == null || this.dbVer.size() <= 0)
			this.dbVer = new HashMap<String, DbVerEntity>();
		scriptFileIterator = sf.iterator();
		while (scriptFileIterator.hasNext()) {
			scriptFile = scriptFileIterator.next();
			// 在当前版本找不到脚本文件
			if (!this.dbVer.containsKey(scriptFile.getName())) {
				dbVerEntity = new DbVerEntity();
				dbVerEntity.setFileName(scriptFile.getFileName());
				dbVerEntity.setVerChange(true);
				this.dbVer.put(dbVerEntity.getName(), dbVerEntity);
				tmp.add(scriptFile);
				this.logger.info(scriptFile.getFileName());
				continue;
			}
			// 在当前版本找到了,但脚本文为空
			dbVerEntity = this.dbVer.get(scriptFile.getName());
			if (dbVerEntity == null) {
				dbVerEntity = new DbVerEntity();
				dbVerEntity.setFileName(scriptFile.getFileName());
				dbVerEntity.setVerChange(true);
				this.dbVer.remove(dbVerEntity.getName());
				this.dbVer.put(dbVerEntity.getName(), dbVerEntity);
				tmp.add(scriptFile);
				this.logger.info(scriptFile.getFileName());
				continue;
			}
			// 进行版本对比
			if (dbVerEntity.getName().equalsIgnoreCase(dbVerEntity.getName())
					&& (dbVerEntity.getVer() < scriptFile.getVer())) {
				dbVerEntity.setFileName(scriptFile.getFileName());
				dbVerEntity.setVerChange(true);
				tmp.add(scriptFile);
			}
			this.logger.info(scriptFile.getFileName());
		}
		return tmp;
	}

	/**
	 * 保存相当版本
	 * 
	 * @throws DaoAppException
	 */
	private void saveDbVer() throws DaoAppException {
		Iterator<DbVerEntity> dvIterator = null;
		DbVerEntity dv1 = null;
		dvIterator = this.dbVer.values().iterator();
		while (dvIterator.hasNext()) {
			dv1 = dvIterator.next();
			if (dv1 != null && dv1.isVerChange())
				this.dbVerDao.save(dv1);
		}
	}
}
