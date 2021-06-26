package com.myproject.distancedemo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.myproject.distancedemo.exception.PostcodeDtlNotFoundException;

@ControllerAdvice
public class PostcodeDtlNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(PostcodeDtlNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String postcodeNotFoundHandler(PostcodeDtlNotFoundException ex) {
		return ex.getMessage();
	}
}
