package com.ks.pointgame.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.ks.pointgame.dto.CMRespDto;
import com.ks.pointgame.handler.ex.CustomApiException;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(CustomApiException.class) // runtime exception 발생할경우 
	public ResponseEntity<CMRespDto<?>> validationApiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
}
