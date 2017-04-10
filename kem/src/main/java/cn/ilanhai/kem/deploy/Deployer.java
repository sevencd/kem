package cn.ilanhai.kem.deploy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import cn.ilanhai.kem.domain.ContextDataDto;
import cn.ilanhai.kem.domain.ContextPageDto;
import cn.ilanhai.kem.domain.deploy.DeployDto;
import cn.ilanhai.kem.util.HttpHelper;
import cn.ilanhai.kem.util.ZipCompressor;

public class Deployer {
	private final String DIR_NAME = "views";
	private final String FILE_NAME = "index.html";
	private DeployDto deployDto = null;
	private String baseDir = null;
	private String workDir = null;
	private String currDir = null;
	private String currDirName = null;
	private String zipFile = null;

	public Deployer(DeployDto deployDto) {
		this.deployDto = deployDto;
		this.baseDir = System.getProperty("user.dir");
		this.workDir = String.format("%s%s%s%s%s", this.baseDir,
				File.separatorChar, "cache", File.separatorChar, "deploy");

	}

	public boolean genFile() throws Exception {
		File dir = null;
		File file = null;
		FileOutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		// byte[] bytes = null;

		ContextDataDto contextDataDto = null;
		List<ContextPageDto> contextPageDtos = null;
		try {
			if (this.deployDto == null)
				return false;
			contextDataDto = this.deployDto.getData();
			if (contextDataDto == null)
				return false;
			contextPageDtos = contextDataDto.getPages();
			if (contextPageDtos == null || contextPageDtos.size() <= 0)
				return false;
			this.currDirName = String.format("%s%s",
					this.deployDto.getModeId(), System.currentTimeMillis());
			this.currDir = String.format("%s%s%s%s%s", this.workDir,
					File.separatorChar, currDirName, File.separatorChar,
					DIR_NAME);
			dir = new File(this.currDir);
			if (dir.exists())
				dir.delete();
			dir.mkdirs();
			file = new File(dir, FILE_NAME);
			if (file.exists())
				file.delete();
			file.createNewFile();
			outputStream = new FileOutputStream(file);
			outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
			// bytes = contextPageDtos.get(0).getHtmlData().getBytes();
			outputStreamWriter.write(contextPageDtos.get(0).getHtmlData());
			outputStreamWriter.flush();
			outputStream.flush();
			return true;
		} catch (Exception e) {
			throw e;
		} finally {
			if (outputStreamWriter != null)
				outputStreamWriter.close();
			if (outputStream != null)
				outputStream.close();
		}
	}

	public boolean genZip() throws Exception {
		ZipCompressor zip = null;
		try {
			this.zipFile = String.format("%s.zip", this.currDir);
			zip = new ZipCompressor(this.zipFile);
			zip.compress(this.currDir);
			return true;
		} catch (Exception e) {
			throw e;
		}

	}

	public String uploadFile(String strUrl) throws Exception {
		try {
			return HttpHelper.postFile(strUrl, this.zipFile);
		} catch (Exception e) {
			throw e;
		}
	}

	public void clear() {
		File dir = null;
		File file = null;
		dir = new File(this.currDir);
		if (dir.exists())
			dir.delete();
		file = new File(this.zipFile);
		if (file.exists())
			file.delete();

	}
}
