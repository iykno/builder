package com.iykno.modeling.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iykno.modeling.bean.ModelingReload;

/**
 * 建模服务：提供操作,如更新、删除等
 * 
 * @author iykno
 *
 */
@Service
public class ModelingOperateService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ModelingLoader modelingLoader;

	/**
	 * 重载
	 * 
	 * @param topic
	 */
	public void reload(String topic) {

		logger.info("ModelingService:reload::topic={}, startTime={}", topic, new Date());
		long startTime = System.currentTimeMillis();

		modelingLoader.reload(new ModelingReload(topic, null));

		logger.info("ModelingService:reload::endTime={}, used={}", new Date(), System.currentTimeMillis() - startTime);
	}

	/**
	 * 
	 * @param modelingReload
	 */
	public void reload(ModelingReload modelingReload) {

		logger.info("ModelingService:reload::modelingReload={}, startTime={}", modelingReload, new Date());
		long startTime = System.currentTimeMillis();

		modelingLoader.reload(modelingReload);

		logger.info("ModelingService:reload::endTime={}, used={}", new Date(), System.currentTimeMillis() - startTime);
	}

}
