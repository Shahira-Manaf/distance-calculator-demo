package com.myproject.distancedemo.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.distancedemo.model.PostcodeDtl;
import com.myproject.distancedemo.repository.PostcodeDtlRepository;
import com.myproject.distancedemo.service.PostcodeDtlService;

@Service
public class PostcodeDtlServiceImpl implements PostcodeDtlService {

	@Autowired
	private PostcodeDtlRepository postcodeDtlRepository;
	
	@Override
	public List<PostcodeDtl> findAll() {
		return postcodeDtlRepository.findAll();
	}

	@Override
	public Optional<PostcodeDtl> findById(Long id) {
		return postcodeDtlRepository.findById(id);
	}
	
	@Override
	public Optional<PostcodeDtl> findByPostcode(String postcode) {
		return postcodeDtlRepository.findByPostcode(postcode);
	}

	@Override
	public PostcodeDtl createOrUpdate(PostcodeDtl postcodeDtl) {
		return postcodeDtlRepository.save(postcodeDtl);
	}

	@Override
	public void deleteById(Long id) {
		postcodeDtlRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public Long deleteByPostcode(String postcode) {
		return postcodeDtlRepository.deleteByPostcode(postcode);
	}
}
