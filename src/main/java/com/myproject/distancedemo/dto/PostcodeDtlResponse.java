package com.myproject.distancedemo.dto;

import java.util.Objects;

public class PostcodeDtlResponse {

	private String postcode;

	private double latitude;

	private double longitude;

	public PostcodeDtlResponse() {
		super();
	}
	
	public PostcodeDtlResponse(String postcode, double latitude, double longitude) {
		super();
		this.postcode = postcode;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof PostcodeDtlResponse))
			return false;

		PostcodeDtlResponse postcodeDtlResp = (PostcodeDtlResponse) obj;

		return Objects.equals(this.postcode, postcodeDtlResp.postcode)
				&& Objects.equals(this.latitude, postcodeDtlResp.latitude)
				&& Objects.equals(this.longitude, postcodeDtlResp.longitude);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.postcode, this.latitude, this.longitude);
	}

	@Override
	public String toString() {
		return "PostcodeDto [postcode=" + postcode + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}
}
