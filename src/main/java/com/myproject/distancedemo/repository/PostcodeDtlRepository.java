package com.myproject.distancedemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.distancedemo.model.PostcodeDtl;

@Repository
public interface PostcodeDtlRepository extends JpaRepository<PostcodeDtl, Long> {
	Optional<PostcodeDtl> findByPostcode(String postcode);
	
	Long deleteByPostcode(String postcode);
}