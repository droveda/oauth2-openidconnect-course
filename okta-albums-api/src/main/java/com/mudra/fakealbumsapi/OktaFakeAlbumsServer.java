package com.mudra.fakealbumsapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//CHANGE : All javax imports need to change to jakarta
import jakarta.annotation.PostConstruct;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a fake Resource server which returns Fake Albums and Fake Photos. 
 * All the albums and their photos are created upfront during the initialization.
 * 
 * @author viraj
 *
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/fakealbums")
public class OktaFakeAlbumsServer {
	
	// Hardcoded Resource API location. If you run server on different port, change here too
	private static final String IMAGE_PREFIX 				= "http://localhost:8081";
	private static final String IMAGE_PREFIX_JACKSONVILLE 	= IMAGE_PREFIX + "/jacksonville";
	private static final String IMAGE_PREFIX_NEWYORK		= IMAGE_PREFIX + "/newyork";
	private static final String IMAGE_PREFIX_MIAMI 			= IMAGE_PREFIX + "/miami";
	private static final String IMAGE_PREFIX_RANDOM			= IMAGE_PREFIX + "/random";
	
	private List<FakeAlbum> listAlbums = new ArrayList<>(); 

	/*
	 * Construct the fake albums and add it to the list
	 */
	@PostConstruct 
	void init() {
		
		// Add some fake books into the list during initialization
		this.listAlbums.add(
			new FakeAlbum(
				"1", 
				"Jacksonville", 
				IMAGE_PREFIX_JACKSONVILLE + "/1.jpg",
				Arrays.asList(
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/1.jpg"),
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/2.jpg"),
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/3.jpg"),
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/4.jpg"),
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/5.jpg"),
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/6.jpg"),
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/7.jpg"),
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/8.jpg"),
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/9.jpg"),
					new FakePhoto(IMAGE_PREFIX_JACKSONVILLE + "/10.jpg")
				)
			)
		);
		
		this.listAlbums.add(
			new FakeAlbum(
				"2", 
				"New York", 
				IMAGE_PREFIX_NEWYORK + "/1.jpg",
				Arrays.asList(
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/1.jpg"),
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/2.jpg"),
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/3.jpg"),
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/4.jpg"),
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/5.jpg"),
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/6.jpg"),
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/7.jpg"),
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/8.jpg"),
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/9.jpg"),
					new FakePhoto(IMAGE_PREFIX_NEWYORK + "/10.jpg")
				)
			)
		);
		
		this.listAlbums.add(
			new FakeAlbum(
				"3", 
				"Miami", 
				IMAGE_PREFIX_MIAMI + "/1.jpg",
				Arrays.asList(
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/1.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/2.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/3.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/4.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/5.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/6.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/7.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/8.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/9.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/10.jpg"),
						new FakePhoto(IMAGE_PREFIX_MIAMI + "/11.jpg")
				)
			)
		);
		
		this.listAlbums.add(
				new FakeAlbum(
					"4", 
					"Random", 
					IMAGE_PREFIX_RANDOM + "/1.jpg",
					Arrays.asList(
							new FakePhoto(IMAGE_PREFIX_RANDOM + "/1.jpg"),
							new FakePhoto(IMAGE_PREFIX_RANDOM + "/2.jpg"),
							new FakePhoto(IMAGE_PREFIX_RANDOM + "/3.jpg"),
							new FakePhoto(IMAGE_PREFIX_RANDOM + "/4.jpg"),
							new FakePhoto(IMAGE_PREFIX_RANDOM + "/5.jpg"),
							new FakePhoto(IMAGE_PREFIX_RANDOM + "/6.jpg"),
							new FakePhoto(IMAGE_PREFIX_RANDOM + "/7.jpg")
					)
				)
			);
			
		
	}
	
	/*
	 * Return the albums when the client asks for it
	 * This is a GET call.
	 */
	@GetMapping("/albums")
	public Map<String, List<FakeAlbum>> retrieveAllAlbums(JwtAuthenticationToken authn) {
		System.out.println(((Jwt)authn.getPrincipal()).getClaims());
		
		var albumsMap = new HashMap<String, List<FakeAlbum>>();
		albumsMap.put("albums", this.listAlbums);
		return albumsMap;
	}
	
	/*
	 * Return the photos of the album when the client asks for it.
	 * This is a POST call.
	 */
    @PostMapping("/mediaItems")
    public Map<String, List<FakePhoto>> retrievePhotosForAlbum(@RequestBody Map fields) {
    	
    	String albumID = (String)fields.get("albumId");
    	
    	List<FakePhoto> photos = this.listAlbums.stream()
							    		.filter(a -> a.getId().equalsIgnoreCase(albumID))
							    		.findFirst()
							    		.orElseThrow()
							    		.getListPhotos();
    	
		var photosMap = new HashMap<String, List<FakePhoto>>();
		photosMap.put("mediaItems", photos);
		return photosMap;
    }
}
