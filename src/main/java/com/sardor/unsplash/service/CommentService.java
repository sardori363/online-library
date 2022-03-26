package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Book;
import com.sardor.unsplash.entity.Comment;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.CommentDto;
import com.sardor.unsplash.repository.BookRepository;
import com.sardor.unsplash.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BookRepository bookRepository;

    public ApiResponse add(CommentDto commentDto) {
        Comment comment = new Comment();

        if (commentDto.getText().length() > 1000) return new ApiResponse("characters must be less than 1000", false);
        comment.setText(commentDto.getText());

        Optional<Book> optionalBook = bookRepository.findById(commentDto.getBookId());
        if (optionalBook.isEmpty()) return new ApiResponse("book not found", false);
        comment.setBook(optionalBook.get());

        commentRepository.save(comment);
        return new ApiResponse("saved", true);
    }

    public ApiResponse edit(Integer id, CommentDto commentDto) {
        if (!commentRepository.existsById(id)) return new ApiResponse("comment not found", false);
        Comment comment = commentRepository.getById(id);
        comment.setText(commentDto.getText());

        Optional<Book> optionalBook = bookRepository.findById(commentDto.getBookId());
        if (optionalBook.isEmpty()) return new ApiResponse("book not found", false);
        comment.setBook(optionalBook.get());

        commentRepository.save(comment);
        return new ApiResponse("edited", true);
    }

    public ApiResponse getOne(Integer id) {
        if (!commentRepository.existsById(id)) return new ApiResponse("comment not found", false);
        return new ApiResponse("found", true, commentRepository.findById(id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("found", true, commentRepository.findAll());
    }

    public ApiResponse delete(Integer id) {
        if (!commentRepository.existsById(id)) return new ApiResponse("comment not found", false);
        commentRepository.deleteById(id);
        return new ApiResponse("deleted", true);
    }

    public ApiResponse getByBook(Integer book_id) {
        List<Comment> allByBookId = commentRepository.findAllByBookId(book_id);
        if (allByBookId.isEmpty()) return new ApiResponse("comment not found", false);
        return new ApiResponse("found", true, commentRepository.findAllByBookId(book_id));
    }
}
