package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Attachment;
import com.sardor.unsplash.entity.Category;
import com.sardor.unsplash.entity.RequestBook;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.RequestDto;
import com.sardor.unsplash.repository.AttachmentRepository;
import com.sardor.unsplash.repository.CategoryRepository;
import com.sardor.unsplash.repository.RequestBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    RequestBookRepository requestRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse add(RequestDto requestDto) {
        RequestBook requestBook = new RequestBook();

        return create(requestBook, requestDto);
    }

    public ApiResponse edit(Integer id, RequestDto requestDto) {
        if (!requestRepository.existsById(id)) return new ApiResponse("request not found", false);
        RequestBook requestBook = requestRepository.getById(id);
        ApiResponse apiResponse = create(requestBook, requestDto);

        if (!apiResponse.isSuccess()) return new ApiResponse("error", false);
        return new ApiResponse("edited", true);
    }

    public ApiResponse create(RequestBook requestBook, RequestDto requestDto) {

        requestBook.setName(requestDto.getName());
        requestBook.setDescription(requestDto.getDescription());
        requestBook.setAuthor(requestDto.getAuthor());
        requestBook.setFirstPublished(requestDto.getFirstPublished());

        if (requestDto.getLanguage().length()>5) return new ApiResponse("characters must be less than 5",false);
        requestBook.setLanguage(requestDto.getLanguage());

        Optional<Attachment> optionalPdf = attachmentRepository.findById(requestDto.getPdfFileId());
        if (optionalPdf.isEmpty()) return new ApiResponse("Pdf not found", false);
        requestBook.setPdfFile(optionalPdf.get());

        Optional<Category> optionalCategory = categoryRepository.findById(requestDto.getCategoryId());
        if (optionalCategory.isEmpty()) return new ApiResponse("category not found", false);
        requestBook.setCategory(optionalCategory.get());

        Optional<Attachment> optionalPhoto = attachmentRepository.findById(requestDto.getPhotoId());
        if (optionalPhoto.isEmpty()) return new ApiResponse("photo not found", false);
        requestBook.setPhoto(optionalPhoto.get());

        requestRepository.save(requestBook);
        return new ApiResponse("request saved", true);
    }

    public ApiResponse getOne(Integer id) {
        if (!requestRepository.existsById(id)) return new ApiResponse("request book not found", false);
        return new ApiResponse("found", true, requestRepository.findById(id));
    }

    public ApiResponse getAll() {
        return new ApiResponse("found", true, requestRepository.findAll());
    }

    public ApiResponse deleteOne(Integer id) {
        if (!requestRepository.existsById(id)) return new ApiResponse("request book not found", false);
        requestRepository.deleteById(id);
        return new ApiResponse("deleted", true);
    }

    public ApiResponse deleteAll() {
        requestRepository.deleteAll();
        return new ApiResponse("requests removed", true);
    }
}
