package com.iykno.modeling.common;

import java.io.File;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {
	private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

	private static String initAppPath(Class<?> cls) {
		try {
			URL urlPath = cls.getProtectionDomain().getCodeSource().getLocation();
			String realPath = java.net.URLDecoder.decode(urlPath.getPath(), "utf-8");
			if (realPath.endsWith(".jar")) {
				realPath = realPath.substring(0, realPath.lastIndexOf("/") + 1);
			}

			File file = new File(realPath);
			realPath = file.getAbsolutePath();
			return realPath;

		} catch (Exception ex) {
			logger.error("", ex);

			return null;
		}
	}

	public static String getConfigFromLibPath() {
		String appPath = initAppPath(FileHelper.class);
		String appConfigPath = appPath + File.separator + "config" + File.separator;
		return appConfigPath;
	}

}
