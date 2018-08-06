package com.luxsoft.tradingvalidatorapi.common.utils.file;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luxsoft.tradingvalidatorapi.common.utils.resource.Resources;

@Ignore
@Component
public class Files {
	
	@Autowired
	private Resources resources;
	
	
	public byte[] getFileContent(String filePathPropertyKey) throws IOException {
		return java.nio.file.Files.readAllBytes(
				Paths.get(
						resources.getResource(filePathPropertyKey).getURI()
						)
				);
	}

}
