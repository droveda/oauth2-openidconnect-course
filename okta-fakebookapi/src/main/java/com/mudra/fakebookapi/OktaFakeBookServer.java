package com.mudra.fakebookapi;

import java.util.ArrayList;
import java.util.List;

// CHANGE : All javax imports need to change to jakarta
import jakarta.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a fake Resource server which returns FakeBook objects
 * 
 * @author viraj
 *
 */
@RestController
@RequestMapping(value = "/books")
public class OktaFakeBookServer {
	
	private List<FakeBook> listBooks = new ArrayList<>(); 

	@PostConstruct 
	void init() {
		
		// Add some fake books into the list during initialization
		this.listBooks.add(new FakeBook(1, "And Then There Were None", "Agatha Christie"	, 	7.99f,  300));
		this.listBooks.add(new FakeBook(2, "A Study in Scarlet", 		"Arthur Conan Doyle", 	7.99f,  108));
		this.listBooks.add(new FakeBook(3, "The Day of the Jackal", 	"Frederick Forsyth ", 	9.99f,  464));
		this.listBooks.add(new FakeBook(4, "The Wisdom of Father Brown", "G.K. Chesterton", 	7.99f,  136));
		this.listBooks.add(new FakeBook(5, "The Poet", 					"Michael Connelly", 	15.90f, 528));
	}
	
	/*
	 * Returns all FakeBook objects
	 */
	@GetMapping
	public List<FakeBook> getAllBooks(JwtAuthenticationToken authn) {
		System.out.println(((Jwt)authn.getPrincipal()).getClaims());
		return listBooks;
	}
	
	/*
	 * Returns a particular FakeBook object
	 */
	@GetMapping("/{id}")
	public FakeBook getBook(@PathVariable Integer id) {
		
		return this.listBooks.stream()
					.filter(b -> b.getId() == id)
					.findAny()
					.orElseThrow();
	}
	
	/*
	 * Creates a FakeBook object. This will be lost on restart.
	 */
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@RequestBody FakeBook book) {
        listBooks.add(book);
    }
}
