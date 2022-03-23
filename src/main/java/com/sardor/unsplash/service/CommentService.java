package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Comment;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.CommentDto;
import com.sardor.unsplash.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    final
    CommentRepository commentRepository;

    public ApiResponse add(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());

        commentRepository.save(comment);
        return new ApiResponse("saved",true);
    }
}
