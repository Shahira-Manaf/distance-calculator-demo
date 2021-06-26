package com.myproject.distancedemo.dto;

import java.util.List;
import java.util.Objects;

public class DistanceDtlResponse {

	private List<PostcodeDtlResponse> postcodes;

	private double distance;

	private String unit = "km";

	public DistanceDtlResponse() {
		super();
	}

	public DistanceDtlResponse(List<PostcodeDtlResponse> postcodes, double distance, String unit) {
		super();
		this.postcodes = postcodes;
		this.distance = distance;
		this.unit = unit;
	}

	public List<PostcodeDtlResponse> getPostcodes() {
		return postcodes;
	}

	public void setPostcodes(List<PostcodeDtlResponse> postcodes) {
		this.postcodes = postcodes;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof DistanceDtlResponse))
			return false;

		DistanceDtlResponse distanceDtlResponse = (DistanceDtlResponse) obj;

		return Objects.equals(this.postcodes, distanceDtlResponse.postcodes) && Objects.equals(this.distance, distanceDtlResponse.distance)
				&& Objects.equals(this.unit, distanceDtlResponse.unit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.postcodes, this.distance, this.unit);
	}

	@Override
	public String toString() {
		return "DistanceDtlResponse [postcodes=" + postcodes + ", distance=" + distance + ", unit=" + unit + "]";
	}
}
