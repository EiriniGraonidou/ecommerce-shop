/*
Copyright [2020] [Eirini Graonidou], All rights reserved.
*/
package net.graonidou.assignment.shop.commons;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * Exception controller advice. 
 * 
 * @author Eirini Graonidou
 *
 */
@ControllerAdvice
public class EShopExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BusinessRuntimeException.class)
	public ResponseEntity<Map<String, Object>> unrecognizedParameter(final BusinessRuntimeException e) {

		final Map<String, Object> errorInfo = new LinkedHashMap<>();
		errorInfo.put("timestamp", LocalDateTime.now());
		errorInfo.put("originalMessage", e.message);

		return new ResponseEntity<Map<String, Object>>(errorInfo, HttpStatus.BAD_REQUEST);

	}
	

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFound(final EntityNotFoundException e) {

		final Map<String, Object> errorInfo = new LinkedHashMap<>();
		errorInfo.put("timestamp", LocalDateTime.now());
		
		errorInfo.put("errorMessage", "The requested entity could not be found");
		return new ResponseEntity<Map<String, Object>>(errorInfo, HttpStatus.NOT_FOUND);

	}
}
