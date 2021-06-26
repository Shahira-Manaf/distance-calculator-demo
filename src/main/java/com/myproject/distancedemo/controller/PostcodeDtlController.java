package com.myproject.distancedemo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.distancedemo.dto.PostcodeDtlResponse;
import com.myproject.distancedemo.exception.PostcodeDtlNotFoundException;
import com.myproject.distancedemo.model.PostcodeDtl;
import com.myproject.distancedemo.modelAssembler.PostcodeDtlModelAssembler;
import com.myproject.distancedemo.service.PostcodeDtlService;

@RestController
public class PostcodeDtlController {

	@Autowired
	private PostcodeDtlService service;
	
	@Autowired
	private PostcodeDtlModelAssembler assembler;
	
	@GetMapping("/findAll")
	public CollectionModel<EntityModel<PostcodeDtlResponse>> findAll() {
		List<EntityModel<PostcodeDtlResponse>> postcodes = service.findAll().stream().map(postcode -> convertToDto(postcode))
				.map(assembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(postcodes, linkTo(methodOn(PostcodeDtlController.class).findAll()).withSelfRel());
	}
	
	@GetMapping("/findById/{id}")
	public EntityModel<PostcodeDtlResponse> findById(@PathVariable Long id) {
		PostcodeDtl postcodeDtl = service.findById(id).orElseThrow(() -> new PostcodeDtlNotFoundException(id));
		return assembler.toModel(convertToDto(postcodeDtl));
	}
	
	@GetMapping("/findByPostcode/{postcode}")
	public EntityModel<PostcodeDtlResponse> findByPostcode(@PathVariable String postcode) {
		PostcodeDtl postcodeDtl = service.findByPostcode(postcode).orElseThrow(() -> new PostcodeDtlNotFoundException(postcode));
		return assembler.toModel(convertToDto(postcodeDtl));
	}
	
	@PostMapping("/createPostcode")
	ResponseEntity<?> createPostcode(@RequestBody PostcodeDtlResponse postcodeDtlResponse) {
		PostcodeDtl postcodeDtl = convertToEntity(postcodeDtlResponse);
		EntityModel<PostcodeDtlResponse> entityModel = assembler.toModel(convertToDto(service.createOrUpdate(postcodeDtl)));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/updateById/{id}")
	ResponseEntity<?> updateById(@RequestBody PostcodeDtlResponse newPostcode, @PathVariable Long id) {
		PostcodeDtl newPostcodeDtl = convertToEntity(newPostcode);

		PostcodeDtl updatedPostcode = service.findById(id).map(postcode -> {
			postcode.setPostcode(newPostcodeDtl.getPostcode());
			postcode.setLatitude(newPostcodeDtl.getLatitude());
			postcode.setLongitude(newPostcodeDtl.getLongitude());

			return service.createOrUpdate(postcode);
		}).orElseGet(() -> {
			return service.createOrUpdate(newPostcodeDtl);
		});

		EntityModel<PostcodeDtlResponse> entityModel = assembler.toModel(convertToDto(updatedPostcode));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/updateByPostcode/{postcodeStr}")
	ResponseEntity<?> updateByPostcode(@RequestBody PostcodeDtlResponse newPostcode, @PathVariable String postcodeStr) {
		PostcodeDtl newPostcodeDtl = convertToEntity(newPostcode);

		PostcodeDtl updatedPostcode = service.findByPostcode(postcodeStr).map(postcode -> {
			postcode.setPostcode(newPostcodeDtl.getPostcode());
			postcode.setLatitude(newPostcodeDtl.getLatitude());
			postcode.setLongitude(newPostcodeDtl.getLongitude());

			return service.createOrUpdate(postcode);
		}).orElseGet(() -> {
			return service.createOrUpdate(newPostcodeDtl);
		});

		EntityModel<PostcodeDtlResponse> entityModel = assembler.toModel(convertToDto(updatedPostcode));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/deleteById/{id}")
	ResponseEntity<?> deleteById(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@Transactional
	@DeleteMapping("/deleteByPostcode/{postcode}")
	ResponseEntity<?> deleteByPostcode(@PathVariable String postcode) {
		service.deleteByPostcode(postcode);
		return ResponseEntity.noContent().build();
	}
	
	private PostcodeDtlResponse convertToDto(PostcodeDtl postcodeDtl) {
		PostcodeDtlResponse postcodeDtlResponse = new PostcodeDtlResponse(postcodeDtl.getPostcode(), postcodeDtl.getLatitude(), postcodeDtl.getLongitude());
		return postcodeDtlResponse;
	}

	private PostcodeDtl convertToEntity(PostcodeDtlResponse postcodeDtlResponse) {
		PostcodeDtl postcodeDtl = new PostcodeDtl(postcodeDtlResponse.getPostcode(), postcodeDtlResponse.getLatitude(), postcodeDtlResponse.getLongitude());
		return postcodeDtl;
	}
}
