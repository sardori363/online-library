package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Category;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.CategoryDto;
import com.sardor.unsplash.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse add(Category categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return new ApiResponse("added",true);
    }
}
