package com.iykno.modeling.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iykno.modeling.bean.ModelingConfig;
import com.iykno.modeling.common.FileHelper;
import com.iykno.modeling.common.XStreamUtils;

public class ModelingConfigFactory {

	private Logger logger = LoggerFactory.getLogger(ModelingConfigFactory.class);

	private static volatile ModelingConfigFactory instance;

	private List<ModelingConfig> modelingConfigList;

	private ModelingConfigFactory() {
		init();
	}

	public List<ModelingConfig> getConfigList() {
		return modelingConfigList;
	}

	public static ModelingConfigFactory getInstance() {
		if (instance == null) {
			synchronized (ModelingConfigFactory.class) {
				if (instance == null) {
					instance = new ModelingConfigFactory();
				}
			}
		}
		return instance;
	}

	/**
	 * 初始化加载规则
	 * 
	 * @return
	 */
	private void init() {
		modelingConfigList = new ArrayList<ModelingConfig>();

		File baseFile = new File(FileHelper.getConfigFromLibPath() + File.separator + "modeling");
		if (baseFile.isFile() || !baseFile.exists()) {
			// return itemList;
		}
		File[] files = baseFile.listFiles();
		if(files != null) {
			for (File file : files) {
				// 如果是文件，且是以.xml结尾
				if (file.isFile() && file.getName().endsWith(".xml")) {
					try {
						ModelingConfig modelingConfig = XStreamUtils.xmlToBean(new FileInputStream(file),
								ModelingConfig.class);
						modelingConfigList.add(modelingConfig);
					} catch (FileNotFoundException e) {
						logger.error("ModelingConfigFactory:init {}", e);
					}
				}

			}
		}else {
			logger.error("ModelingConfigFactory init config not found. ");
		}
		
	}

	/**
	 * 
	 * @param topicName
	 * @return
	 */
	public ModelingConfig getConfigByTopic(String topic) {
		if (modelingConfigList != null && topic != null) {
			for (ModelingConfig modelingConfig : modelingConfigList) {
				if (topic.equals(modelingConfig.getTopic())) {
					return modelingConfig;
				}
			}
		}
		return null;
	}

}