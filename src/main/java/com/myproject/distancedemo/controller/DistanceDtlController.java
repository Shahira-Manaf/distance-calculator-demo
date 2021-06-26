package com.myproject.distancedemo.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.distancedemo.dto.DistanceDtlResponse;
import com.myproject.distancedemo.dto.PostcodeDtlResponse;
import com.myproject.distancedemo.exception.PostcodeDtlNotFoundException;
import com.myproject.distancedemo.model.PostcodeDtl;
import com.myproject.distancedemo.modelAssembler.DistanceDtlModelAssembler;
import com.myproject.distancedemo.service.PostcodeDtlService;

@RestController
public class DistanceDtlController {

	private final static double EARTH_RADIUS = 6371;

	@Autowired
	private PostcodeDtlService service;

	@Autowired
	private DistanceDtlModelAssembler assembler;
	
	@GetMapping("/findDistance")
	public EntityModel<DistanceDtlResponse> findDistance(@RequestParam String postcodeStr1,
			@RequestParam String postcodeStr2) {
		PostcodeDtl postcode1 = service.findByPostcode(postcodeStr1).orElseThrow(() -> new PostcodeDtlNotFoundException(postcodeStr1));
		PostcodeDtl postcode2 = service.findByPostcode(postcodeStr2).orElseThrow(() -> new PostcodeDtlNotFoundException(postcodeStr2));
		
		PostcodeDtlResponse postcodeDto1 = convertToDto(postcode1);
		PostcodeDtlResponse postcodeDto2 = convertToDto(postcode2);

		DistanceDtlResponse distanceDtlResp = new DistanceDtlResponse();
		List<PostcodeDtlResponse> postcodes = Stream.of(postcodeDto1, postcodeDto2).collect(Collectors.toList());
		distanceDtlResp.setPostcodes(postcodes);
		distanceDtlResp.setDistance(calculateDistance(postcode1.getLatitude(), postcode1.getLongitude(),
				postcode2.getLatitude(), postcode2.getLongitude()));

		return assembler.toModel(distanceDtlResp);
	}
	
	private double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
		double lon1Radians = Math.toRadians(longitude);
		double lon2Radians = Math.toRadians(longitude2);
		double lat1Radians = Math.toRadians(latitude);
		double lat2Radians = Math.toRadians(latitude2);
		double a = haversine(lat1Radians, lat2Radians)
				+ Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return (EARTH_RADIUS * c);
	}

	private double haversine(double deg1, double deg2) {
		return square(Math.sin((deg1 - deg2) / 2.0));
	}

	private double square(double x) {
		return x * x;
	}

	private PostcodeDtlResponse convertToDto(PostcodeDtl postcodeDtl) {
		PostcodeDtlResponse postcodeDtlResponse = new PostcodeDtlResponse(postcodeDtl.getPostcode(), postcodeDtl.getLatitude(), postcodeDtl.getLongitude());
		return postcodeDtlResponse;
	}
}
