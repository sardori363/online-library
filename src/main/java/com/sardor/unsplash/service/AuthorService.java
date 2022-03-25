package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Author;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.AuthorDto;
import com.sardor.unsplash.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;


    public ApiResponse add(AuthorDto authorDto) {
        Author author = new Author();
        author.setFullname(authorDto.getFullname());
        author.setDateOfBirthAndDeath(authorDto.getDateOfBirthAndDeath());
        author.setBio(authorDto.getBio());

        authorRepository.save(author);
        return new ApiResponse("saved", true);
    }

    public ApiResponse edit(Integer id, AuthorDto authorDto) {
        if (!authorRepository.existsById(id)) return new ApiResponse("Author not found", false);

        Author author = authorRepository.getById(id);
        author.setFullname(authorDto.getFullname());
        author.setDateOfBirthAndDeath(authorDto.getDateOfBirthAndDeath());
        author.setBio(authorDto.getBio());

        authorRepository.save(author);
        return new ApiResponse("edit", true);
    }

    public ApiResponse getOne(Integer id) {
        if (!authorRepository.existsById(id)) return new ApiResponse("author not found", false);
        return new ApiResponse("found", true, authorRepository.findById(id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("found", true, authorRepository.findAll());
    }

    public ApiResponse delete(Integer id) {
        if (!authorRepository.existsById(id)) return new ApiResponse("author not found", false);
        authorRepository.deleteById(id);
        return new ApiResponse("deleted", true);
    }
}
