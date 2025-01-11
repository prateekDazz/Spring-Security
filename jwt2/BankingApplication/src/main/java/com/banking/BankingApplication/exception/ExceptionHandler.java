package com.banking.BankingApplication.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandler  {
	
	 @org.springframework.web.bind.annotation.ExceptionHandler(InsufficientBalanceException.class)
	    public ResponseEntity<Map<String, Object>> notFoundHandler(InsufficientBalanceException ex) {
	        Map map = new HashMap<String, Object>();
	        map.put("message", ex.getMessage());
	        map.put("success", false);
	        map.put("status", HttpStatus.NOT_FOUND);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	    }	
	 @org.springframework.web.bind.annotation.ExceptionHandler(AccountNotExistsException.class)
	    public ResponseEntity<Map<String, Object>> accountNotFound(AccountNotExistsException ex) {
	        Map map = new HashMap<String, Object>();
	        map.put("message", ex.getMessage());
	        map.put("success", false);
	        map.put("status", HttpStatus.NOT_FOUND);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	    }
	 @org.springframework.web.bind.annotation.ExceptionHandler(InvalidOperationException.class)
	    public ResponseEntity<Map<String, Object>> invalidOperationEx(InvalidOperationException ex) {
	        Map map = new HashMap<String, Object>();
	        map.put("message", ex.getMessage());
	        map.put("success", false);
	        map.put("status", HttpStatus.BAD_REQUEST);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	    }
	 @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String, Object>>handleglobalException(Exception e, WebRequest request)
	    {
		 Map map = new HashMap<String, Object>();
	        map.put("message", e.getLocalizedMessage());
	        map.put("success", false);
	        map.put("status", HttpStatus.BAD_REQUEST);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	    }	
}
