package com.myproject.distancedemo.exception;

public class PostcodeDtlNotFoundException extends RuntimeException {

	public PostcodeDtlNotFoundException(Long id) {
		super("Could not find postcode details with id " + id);
	}
	
	public PostcodeDtlNotFoundException(String postcode) {
		super("Could not find postcode details with postcode " + postcode);
	}
}
