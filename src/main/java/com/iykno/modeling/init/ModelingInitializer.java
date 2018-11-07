package com.iykno.modeling.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iykno.modeling.bean.ModelingConfig;
import com.iykno.modeling.config.ModelingConfigFactory;
import com.iykno.modeling.service.ModelingLoader;

/**
 * 可以在监听中启动
 * ModelingInitializer.init(event.getApplicationContext().getBean(ModelingLoader.class));
 * 
 * 建模实例化
 * 
 * @author iykno
 *
 */
public class ModelingInitializer {

	private static Logger logger = LoggerFactory.getLogger(ModelingInitializer.class);

	public static void init(ModelingLoader modelingLoader) {

		logger.info("ModelingInitializer.init begin....");

		List<ModelingConfig> configList = ModelingConfigFactory.getInstance().getConfigList();

		if (configList != null && !configList.isEmpty()) {

			for (ModelingConfig c : configList) {
				modelingLoader.load(c);
			}

		}
		logger.info("ModelingInitializer.init end ....");

	}

}
