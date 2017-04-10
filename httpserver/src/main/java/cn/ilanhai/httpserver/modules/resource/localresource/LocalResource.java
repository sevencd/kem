package cn.ilanhai.httpserver.modules.resource.localresource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cn.ilanhai.httpserver.modules.resource.Resource;
import cn.ilanhai.httpserver.modules.resource.ResourceEntry;

public class LocalResource implements Resource {

	private String getBaseDir() {
		return System.getProperty("user.dir");
	}

	private String getPrefix(String fileName) throws IOException {
		if (fileName == null || fileName.length() <= 0)
			throw new IOException("fileName error");
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return prefix;
	}

	private String createNewFileName() {
		SimpleDateFormat simpleDateFormat;
		Date date;
		String str;
		Random random;
		int rannum;
		simpleDateFormat = new SimpleDateFormat("yyyyMMddhhss");
		date = new Date();
		str = simpleDateFormat.format(date);
		random = new Random();
		rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
		return rannum + str;
	}

	private File getPath(String path) {
		File file = null;
		file = new File(path);
		String baseDir = null;
		if (!file.exists())
			file = new File(baseDir, path);
		file.mkdirs();
		return file;
	}

	@Override
	public ResourceEntry save(String path, String fileName,
			InputStream inputStream) throws IOException {
		ResourceEntry entry = null;
		FileOutputStream outputStream = null;
		File file = null;
		String newFileName = null;
		int len = -1;
		byte[] buffer = null;
		try {
			if (path == null || path.length() <= 0)
				throw new IOException("path error");
			if (fileName == null || fileName.length() <= 0)
				throw new IOException("fileName error");
			if (inputStream == null)
				throw new IOException("inputStream error");
			newFileName = String.format("%s.%s", this.createNewFileName(),
					this.getPrefix(fileName));
			file = this.getPath(path);
			file = new File(file, newFileName);
			outputStream = new FileOutputStream(file);
			buffer = new byte[1024];
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			entry = new ResourceEntry();
			entry.setNewName(newFileName);
			entry.setType(this.getPrefix(fileName));
			entry.setRawName(fileName);
			entry.setPath(file.toString());
			outputStream.flush();
			return entry;
		} catch (IOException e) {
			throw e;
		} finally {
			if (outputStream != null)
				outputStream.close();
			if (inputStream != null)
				inputStream.close();
		}

	}
}
