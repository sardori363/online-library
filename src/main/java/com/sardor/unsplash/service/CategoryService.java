package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Category;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.CategoryDto;
import com.sardor.unsplash.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    final CategoryRepository categoryRepository;

    public ApiResponse add(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return new ApiResponse("added", true);
    }

    public ApiResponse edit(Integer id, CategoryDto categoryDto) {
        if (!categoryRepository.existsById(id)) return new ApiResponse("category not found", false);
        Category category = categoryRepository.getById(id);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return new ApiResponse("edited", true);
    }

    public ApiResponse getOne(Integer id) {
        if (!categoryRepository.existsById(id)) return new ApiResponse("category not found", false);
        return new ApiResponse("found", true, categoryRepository.findById(id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("found", true, categoryRepository.findAll());
    }

    public ApiResponse delete(Integer id) {
        if (!categoryRepository.existsById(id)) return new ApiResponse("category not found", false);
        categoryRepository.deleteById(id);
        return new ApiResponse("deleted", true);
    }
}
