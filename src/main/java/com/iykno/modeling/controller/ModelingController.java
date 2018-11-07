package com.iykno.modeling.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iykno.modeling.bean.ModelingConditionGroup;
import com.iykno.modeling.bean.ModelingReload;
import com.iykno.modeling.common.Result;
import com.iykno.modeling.service.ModelingOperateService;
import com.iykno.modeling.service.ModelingSelectService;

/**
 * 
 * @author iykno
 *
 */
@RestController
@RequestMapping(value = "/modeling")
public class ModelingController {
	private Logger logger = LoggerFactory.getLogger(ModelingController.class);

	@Autowired
	private ModelingSelectService modelingSelectService;

	@Autowired
	private ModelingOperateService modelingOperateService;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result search(@RequestBody ModelingConditionGroup conditionGroup) {
		Result result = null;
		long startTime_flag = System.currentTimeMillis();
		result = modelingSelectService.getListByCond(conditionGroup);
		long endTime_flag = System.currentTimeMillis();
		logger.info("查询总共用时:{}", String.valueOf(endTime_flag - startTime_flag));

		return result;
	}

	@RequestMapping(value = "/reload", method = RequestMethod.POST)
	public Result reload(@RequestBody ModelingReload modelingReload) {
		Result result = null;
		long startTime_flag = System.currentTimeMillis();
		modelingOperateService.reload(modelingReload);
		long endTime_flag = System.currentTimeMillis();
		logger.info("查询总共用时:{}", String.valueOf(endTime_flag - startTime_flag));

		return result;
	}

}
