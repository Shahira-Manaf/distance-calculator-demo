package com.myproject.distancedemo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PostcodeDtl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String postcode;

	private double latitude;

	private double longitude;

	public PostcodeDtl() {
		super();
	}

	public PostcodeDtl(String postcode, double latitude, double longitude) {
		super();
		this.postcode = postcode;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

		if (!(obj instanceof PostcodeDtl))
			return false;

		PostcodeDtl postcodeDtl = (PostcodeDtl) obj;

		return Objects.equals(this.id, postcodeDtl.id) && Objects.equals(this.postcode, postcodeDtl.postcode)
				&& Objects.equals(this.latitude, postcodeDtl.latitude)
				&& Objects.equals(this.longitude, postcodeDtl.longitude);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.postcode, this.latitude, this.longitude);
	}

	@Override
	public String toString() {
		return "PostcodeDtl [id=" + id + ", postcode=" + postcode + ", latitude=" + latitude + ", longitude=" + longitude
				+ "]";
	}
}
