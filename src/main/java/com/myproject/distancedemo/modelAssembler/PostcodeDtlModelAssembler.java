package com.myproject.distancedemo.modelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.myproject.distancedemo.controller.PostcodeDtlController;
import com.myproject.distancedemo.dto.PostcodeDtlResponse;

@Component
public class PostcodeDtlModelAssembler implements RepresentationModelAssembler<PostcodeDtlResponse, EntityModel<PostcodeDtlResponse>> {

	@Override
	public EntityModel<PostcodeDtlResponse> toModel(PostcodeDtlResponse postcodeDtlResponse) {
		return EntityModel.of(postcodeDtlResponse,
				linkTo(methodOn(PostcodeDtlController.class).findByPostcode(postcodeDtlResponse.getPostcode())).withSelfRel(),
				linkTo(methodOn(PostcodeDtlController.class).findAll()).withRel("findAll"));
	}
}
