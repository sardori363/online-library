package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Book;
import com.sardor.unsplash.entity.SavedBooks;
import com.sardor.unsplash.entity.User;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.repository.BookRepository;
import com.sardor.unsplash.repository.SavedBooksRepository;
import com.sardor.unsplash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavedBooksService {

    @Autowired
    SavedBooksRepository savedBooksRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse addBookToSavedBooks(Integer bookId, Integer userId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty())
            return new ApiResponse("Book not found",false);
        Book book = optionalBook.get();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found",false);
        User user = optionalUser.get();
        user.getSavedBooks().getBookList().add(book);
        userRepository.save(user);
        return new ApiResponse("Book added",true);
    }

    public HttpEntity<?> getSavedBooksById(Integer id) {
        Optional<SavedBooks> optionalSavedBooks = savedBooksRepository.findById(id);
        return ResponseEntity.status(optionalSavedBooks.isPresent()?200:409).body(optionalSavedBooks.orElse(null));
    }

    public HttpEntity<?> getSavedBooksByUserId(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("Usr not found",false));
        User user = optionalUser.get();
        return ResponseEntity.ok(user.getSavedBooks());
    }

    public ApiResponse deleteSavedBooks(Integer id) {
        try {
            savedBooksRepository.deleteById(id);
            return new ApiResponse("SavedBooks deleted", true);
        } catch (Exception e) {
            return new ApiResponse(e.getMessage(),false);
        }
    }

    public ApiResponse deleteBookFromSavedBooks(Integer id,Integer bookId){
        Optional<SavedBooks> optionalSavedBooks = savedBooksRepository.findById(id);
        if (optionalSavedBooks.isEmpty())
            return new ApiResponse("SavedBooks not found",false);
        SavedBooks savedBooks = optionalSavedBooks.get();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty())
            return new ApiResponse("Book not found",false);
        Book book = optionalBook.get();
        savedBooks.getBookList().remove(book);
        return new ApiResponse("Book deleted",true);
    }
}
