package com.iykno.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.iykno.modeling.init.ModelingInitializer;
import com.iykno.modeling.service.ModelingLoader;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		ModelingInitializer.init(event.getApplicationContext().getBean(ModelingLoader.class));

	}

}
