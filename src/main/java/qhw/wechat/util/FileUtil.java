package qhw.wechat.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * 保存文件到本地,返回保存的路径
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public static String saveToServer(String fileName, InputStream inputStream) {
		String path = System.getProperty("webAppRootKey");
		String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String relativePath = File.separator + "file" + File.separator + getSuffix(fileName) + File.separator + date + File.separator + fileName;
		File file = new File(path + relativePath);
		try {
			FileUtils.copyInputStreamToFile(inputStream, file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return relativePath;
	}
	
	private static String getSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}
