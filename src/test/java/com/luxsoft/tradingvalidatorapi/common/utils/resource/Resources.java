package com.luxsoft.tradingvalidatorapi.common.utils.resource;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.luxsoft.tradingvalidatorapi.common.utils.property.Properties;

@Ignore
@Component
public class Resources {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private Properties props;
	
	public Resource getResource(String filePathPropertiesKey) {
		return resourceLoader.getResource(
					props.getPropertyValue(filePathPropertiesKey)
				);
	}

}
