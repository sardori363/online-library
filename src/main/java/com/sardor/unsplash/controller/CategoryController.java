package com.sardor.unsplash.controller;

import com.sardor.unsplash.entity.Category;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
@Validated
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public HttpEntity<?> add(@RequestBody @Valid Category category) {
        ApiResponse apiResponse = categoryService.add(category);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
