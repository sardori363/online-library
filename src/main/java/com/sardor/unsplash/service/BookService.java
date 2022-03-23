package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Attachment;
import com.sardor.unsplash.entity.Book;
import com.sardor.unsplash.entity.Category;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.BookDto;
import com.sardor.unsplash.repository.AttachmentRepository;
import com.sardor.unsplash.repository.BookRepository;
import com.sardor.unsplash.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public ApiResponse add(BookDto bookDto) {
        Book book = new Book();
        return create(book, bookDto);
    }

    public ApiResponse edit(Integer id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) return new ApiResponse("book not found");

        Book book = bookRepository.getById(id);
        ApiResponse apiResponse = create(book, bookDto);

        if (!apiResponse.isSuccess()) return new ApiResponse("error", false);
        return new ApiResponse("edited", true);
    }

    public ApiResponse create(Book book, BookDto bookDto) {
        book.setName(bookDto.getName());
        book.setDescription(bookDto.getDescription());

        Optional<Attachment> optionalPdf = attachmentRepository.findById(bookDto.getPdfFileId());
        if (optionalPdf.isEmpty()) return new ApiResponse("pdf not found", false);
        book.setPdfFile(optionalPdf.get());

        Optional<Category> optionalCategory = categoryRepository.findById(bookDto.getCategoryId());
        if (optionalCategory.isEmpty()) return new ApiResponse("category not found", false);
        book.setCategory(optionalCategory.get());

        Optional<Attachment> optionalPhoto = attachmentRepository.findById(bookDto.getPhotoId());
        if (optionalPhoto.isEmpty()) return new ApiResponse("photo not found", false);
        book.setPhoto(optionalPhoto.get());

        bookRepository.save(book);
        return new ApiResponse("saved", true);
    }

    public ApiResponse getOne(Integer id) {
        if (!bookRepository.existsById(id)) return new ApiResponse("book not found", false);
        return new ApiResponse("book found", true, bookRepository.findById(id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("found", true, bookRepository.findAll());
    }

    public ApiResponse delete(Integer id) {
        if (!bookRepository.existsById(id)) return new ApiResponse("book not found", false);
        bookRepository.deleteById(id);
        return new ApiResponse("deleted", true);
    }
}
