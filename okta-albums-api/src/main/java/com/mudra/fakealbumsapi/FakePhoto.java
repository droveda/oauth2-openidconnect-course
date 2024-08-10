package com.mudra.fakealbumsapi;

/**
 * Represents a Fake Photo.
 * 
 * @author viraj
 *
 */
public class FakePhoto {
	
	private String baseUrl;
	
	public FakePhoto() {
		// do not do anything
	}

	public FakePhoto(String baseUrl) {
		super();
		this.baseUrl = baseUrl;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
}
