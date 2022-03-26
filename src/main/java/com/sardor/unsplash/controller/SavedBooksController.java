package com.sardor.unsplash.controller;

import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.BookDto;
import com.sardor.unsplash.service.SavedBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saved")
public class SavedBooksController {

    @Autowired
    SavedBooksService savedBooksService;

    @PutMapping
    public HttpEntity<?> addBookToSavedBooks(@RequestParam Integer bookId, @RequestParam Integer userId) {
        ApiResponse apiResponse = savedBooksService.addBookToSavedBooks(bookId, userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getSavedBooksById(@PathVariable Integer id) {
        return savedBooksService.getSavedBooksById(id);
    }

    @GetMapping("/byuserid/{userId}")
    public HttpEntity<?> getSavedBooksByUserId(@PathVariable Integer userId) {
        return savedBooksService.getSavedBooksByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSavedBooks(@PathVariable Integer id, @RequestParam Integer bookId) {
        ApiResponse apiResponse;
        if (bookId == null) {
            apiResponse = savedBooksService.deleteSavedBooks(id);
        }else {
            apiResponse=savedBooksService.deleteBookFromSavedBooks(id,bookId);
        }
        return ResponseEntity.status(apiResponse.isSuccess() ? 204 : 409).body(apiResponse);
    }
}
