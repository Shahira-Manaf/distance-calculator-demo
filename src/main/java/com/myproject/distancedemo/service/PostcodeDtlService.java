package com.myproject.distancedemo.service;

import java.util.List;
import java.util.Optional;

import com.myproject.distancedemo.model.PostcodeDtl;

public interface PostcodeDtlService {
	
	List<PostcodeDtl> findAll();

	Optional<PostcodeDtl> findById(Long id);
	
	Optional<PostcodeDtl> findByPostcode(String postcode);

	PostcodeDtl createOrUpdate(PostcodeDtl postcodeDtl);

	void deleteById(Long id);
	
	Long deleteByPostcode(String postcode);
}
