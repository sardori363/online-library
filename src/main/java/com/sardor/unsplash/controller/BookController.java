package com.sardor.unsplash.controller;

import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.BookDto;
import com.sardor.unsplash.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;


    //                 CRUD METHODS


    @PostMapping
    public HttpEntity<?> add(@RequestBody BookDto bookDto) {
        ApiResponse apiResponse = bookService.add(bookDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody BookDto bookDto) {
        ApiResponse apiResponse = bookService.edit(id, bookDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        ApiResponse apiResponse = bookService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAll() {
        ApiResponse apiResponse = bookService.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = bookService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    //                                  OTHER METHODS


    @GetMapping("get-by-lang")
    public HttpEntity<?> getByLang(@RequestParam String lang) {
        ApiResponse apiResponse = bookService.getByLang(lang);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("get-by-name")
    public HttpEntity<?> getByName(@RequestParam String name) {
        ApiResponse apiResponse = bookService.getByName(name);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("get-by-categoryId/{category_id}")
    public HttpEntity<?> getByCategoryId(@PathVariable Integer category_id) {
        ApiResponse apiResponse = bookService.getByCategoryId(category_id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("get-by-authorId/{author_id}")
    public HttpEntity<?> getByAuthorId(@PathVariable Integer author_id) {
        ApiResponse apiResponse = bookService.getByAuthorId(author_id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
