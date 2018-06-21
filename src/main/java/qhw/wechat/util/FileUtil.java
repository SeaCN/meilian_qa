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
	 * 保存文件到本地
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public static String saveSpeex(String fileName, InputStream inputStream) {
		String filePath = "";
		String path = System.getProperty("webAppRootKey");
		String date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		File file = new File(path + "/speex/" + date + "/" + fileName);
		try {
			FileUtils.copyInputStreamToFile(inputStream, file);
			filePath = file.getPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return filePath;
	}
}