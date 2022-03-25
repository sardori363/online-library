package com.sardor.unsplash.controller;

import com.sardor.unsplash.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.sardor.unsplash.annotations.CheckPermission;
import com.sardor.unsplash.entity.Attachment;
import com.sardor.unsplash.entity.AttachmentContent;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.repository.AttachmentContentRepository;
import com.sardor.unsplash.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/upload")
    public HttpEntity<?> uploadFile(MultipartHttpServletRequest request) throws IOException {
        ApiResponse apiResponse = attachmentService.uploadFile(request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @GetMapping("/info")
    public HttpEntity<?> getAllInfo() {
        return attachmentService.getAllInfo();
    }


    @GetMapping("/info/{id}")
    public HttpEntity<?> getInfo(@PathVariable Integer id, HttpServletResponse response) {
        return attachmentService.getInfoById(id);
    }


    @GetMapping("/download/{id}")
    public void download(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        attachmentService.downloadFile(id,response);
    }


    @GetMapping("/downloadWithName")
    public void downloadWithName(@RequestBody String name, HttpServletResponse response) throws IOException {
        attachmentService.downloadByName(name,response);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteMedia(@PathVariable Integer id) {
        ApiResponse apiResponse = attachmentService.deleteMedia(id);
        return ResponseEntity.status(apiResponse.isSuccess()?204:409).body(apiResponse);
    }


    @PostMapping("/uploadAnyFile")
    public HttpEntity<?> uploadAnyFiles(MultipartHttpServletRequest request) throws IOException {
        ApiResponse apiResponse = attachmentService.uploadAnyFiles(request);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


}
