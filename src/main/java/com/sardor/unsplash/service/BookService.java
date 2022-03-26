package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Attachment;
import com.sardor.unsplash.entity.Author;
import com.sardor.unsplash.entity.Book;
import com.sardor.unsplash.entity.Category;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.BookDto;
import com.sardor.unsplash.repository.AttachmentRepository;
import com.sardor.unsplash.repository.AuthorRepository;
import com.sardor.unsplash.repository.BookRepository;
import com.sardor.unsplash.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AuthorRepository authorRepository;


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
        book.setFirstPublished(bookDto.getFirstPublished());

        if (bookDto.getLanguage().length() > 5) return new ApiResponse("characters must be less than 5", false);
        book.setLanguage(bookDto.getLanguage());

        Optional<Author> optionalAuthor = authorRepository.findById(bookDto.getAuthorId());
        if (optionalAuthor.isEmpty()) return new ApiResponse("author not found");
        book.setAuthor(optionalAuthor.get());

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

    public ApiResponse getByLang(String lang) {
        List<Book> allByLanguage = bookRepository.findAllByLanguage(lang);
        if (allByLanguage.isEmpty()) return new ApiResponse("book not found", false);
        return new ApiResponse("found", true, bookRepository.findAllByLanguage(lang));
    }

    public ApiResponse getByName(String name) {
        List<Book> allByName = bookRepository.findAllByName(name);
        if (allByName.isEmpty()) return new ApiResponse("book not found", false);
        return new ApiResponse("found", true, bookRepository.findAllByName(name));
    }

    public ApiResponse getByCategoryId(Integer category_id) {
        List<Book> allByCategoryId = bookRepository.findAllByCategoryId(category_id);
        if (allByCategoryId.isEmpty()) return new ApiResponse("book not found", false);
        return new ApiResponse("found",true,bookRepository.findAllByCategoryId(category_id));
    }

    public ApiResponse getByAuthorId(Integer author_id) {
        List<Book> allByAuthorId = bookRepository.findAllByAuthorId(author_id);
        if (allByAuthorId.isEmpty()) return new ApiResponse("book not found", false);
        return new ApiResponse("found",true,bookRepository.findAllByAuthorId(author_id));
    }
}
