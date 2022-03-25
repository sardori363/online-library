package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Book;
import com.sardor.unsplash.entity.Collection;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.CollectionDto;
import com.sardor.unsplash.repository.BookRepository;
import com.sardor.unsplash.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {
    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    BookRepository bookRepository;

    public ApiResponse add(CollectionDto collectionDto) {
        Collection collection = new Collection();
        return getApiResponse(collectionDto, collection);
    }

    public ApiResponse edit(Integer id, CollectionDto collectionDto) {
        if (!collectionRepository.existsById(id)) return new ApiResponse("collection not found", false);
        Collection collection = collectionRepository.getById(id);
        ApiResponse apiResponse = getApiResponse(collectionDto, collection);
        if (!apiResponse.isSuccess()) return new ApiResponse("error", false);
        return new ApiResponse("edited", true);
    }

    private ApiResponse getApiResponse(CollectionDto collectionDto, Collection collection) {
        collection.setName(collectionDto.getName());
        collection.setDescription(collectionDto.getDescription());
        collection.setIsPrivate(collectionDto.getIsPrivate());

        List<Book> allById = bookRepository.findAllById(collectionDto.getBooks());
        if (allById.isEmpty()) return new ApiResponse("books not found");
        collection.setBooks(allById);

        collectionRepository.save(collection);
        return new ApiResponse("saved", true);
    }

    public ApiResponse getOne(Integer id) {
        if (!collectionRepository.existsById(id)) return new ApiResponse("collection not found", false);
        return new ApiResponse("found", true, collectionRepository.findById(id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("found", true, collectionRepository.findAll());
    }

    public ApiResponse delete(Integer id) {
        if (!collectionRepository.existsById(id)) return new ApiResponse("collection not found", false);
        collectionRepository.deleteById(id);
        return new ApiResponse("deleted", true);
    }
}
