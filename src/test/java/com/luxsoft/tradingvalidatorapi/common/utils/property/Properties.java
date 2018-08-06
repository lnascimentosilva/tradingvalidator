package com.luxsoft.tradingvalidatorapi.common.utils.property;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Ignore
@Component
public class Properties {
	
	@Autowired
	private Environment env;
	
	public String getPropertyValue(String propertyKey) {
		return env.getProperty(propertyKey);
	}

}
