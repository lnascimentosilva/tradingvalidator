package com.luxsoft.tradingvalidatorapi.common.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.luxsoft.tradingvalidatorapi.common.exception.IntegrationException;

@RestControllerAdvice
public class IntegrationExceptionHandler {	

	@ExceptionHandler(value = { IntegrationException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> unknownException(Exception ex, WebRequest req) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", ex.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
