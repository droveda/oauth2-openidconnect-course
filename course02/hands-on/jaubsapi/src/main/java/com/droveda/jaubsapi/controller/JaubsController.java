package com.droveda.jaubsapi.controller;

import com.droveda.jaubsapi.service.BookItem;
import com.droveda.jaubsapi.service.JaubsBookService;
import com.droveda.jaubsapi.service.SoldItem;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/jaubsapi")
public class JaubsController {

    private final JaubsBookService service;

    public JaubsController(JaubsBookService service) {
        this.service = service;
    }

    @PostMapping("/admin/book")
    public ResponseEntity<BookItem> createBook(@Valid @RequestBody BookItem bookItem) {
        service.createBookItem(bookItem);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookItem.getId())
                .toUri();

        return ResponseEntity.created(uri).body(bookItem);
    }

    @PutMapping("/admin/book")
    public BookItem updateBook(@Valid @RequestBody BookItem bookItem) {
        service.updateBookItem(bookItem);
        return bookItem;
    }

    @GetMapping("/book/{id}")
    public BookItem getItem(@PathVariable("id") Long id) {
        return service.getItem(id);
    }

    @GetMapping("/open-items")
    public List<BookItem> getAllOpenItems() {
        return service.findAllOpenItems();
    }

    @GetMapping("/sold-items/{user}")
    public List<SoldItem> getSoldItems(@PathVariable("user") String user) {
        return service.findSoldItems(user);
    }

    @DeleteMapping("/admin/book/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buy/{id}")
    public ResponseEntity<Void> buyItem(@PathVariable("id") Long id) {
        service.buyItem(id);
        return ResponseEntity.noContent().build();
    }

}
