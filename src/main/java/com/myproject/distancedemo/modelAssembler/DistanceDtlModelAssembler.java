package com.myproject.distancedemo.modelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.myproject.distancedemo.controller.DistanceDtlController;
import com.myproject.distancedemo.dto.DistanceDtlResponse;
import com.myproject.distancedemo.dto.PostcodeDtlResponse;


@Component
public class DistanceDtlModelAssembler implements RepresentationModelAssembler<DistanceDtlResponse, EntityModel<DistanceDtlResponse>> {

	@Override
	public EntityModel<DistanceDtlResponse> toModel(DistanceDtlResponse distanceDtl) {
		List<PostcodeDtlResponse> postcodes = distanceDtl.getPostcodes();
			PostcodeDtlResponse dto1 = postcodes.get(0);
			PostcodeDtlResponse dto2 = postcodes.get(1);

		return EntityModel.of(distanceDtl,
				linkTo(methodOn(DistanceDtlController.class).findDistance(dto1.getPostcode(), dto2.getPostcode())).withSelfRel());
	}

}