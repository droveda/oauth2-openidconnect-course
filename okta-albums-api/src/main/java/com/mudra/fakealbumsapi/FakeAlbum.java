package com.mudra.fakealbumsapi;

import java.util.List;

/**
 * Represents a Fake Album.
 * 
 * @author viraj
 *
 */
public class FakeAlbum {
	
	private String id;
	private String title;
	private String coverPhotoBaseUrl;
	
	private List<FakePhoto> listPhotos;
	
	public FakeAlbum() {
		// do nothing here
	}
	
	public FakeAlbum(String id, String title, String coverPhotoBaseUrl, List<FakePhoto> photos) {
		super();
		this.id = id;
		this.title = title;
		this.coverPhotoBaseUrl = coverPhotoBaseUrl;
		this.setListPhotos(photos);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverPhotoBaseUrl() {
		return coverPhotoBaseUrl;
	}

	public void setCoverPhotoBaseUrl(String coverPhotoBaseUrl) {
		this.coverPhotoBaseUrl = coverPhotoBaseUrl;
	}

	public List<FakePhoto> getListPhotos() {
		return listPhotos;
	}

	public void setListPhotos(List<FakePhoto> listPhotos) {
		this.listPhotos = listPhotos;
	}
	
	

}
