package com.sardor.unsplash.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor
public class AttachmentController {
    final
    AttachmentRepository attachmentRepository;
    final
    AttachmentContentRepository attachmentContentRepository;


    @PostMapping("/upload")
    public ApiResponse uploadFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();
            Attachment attachment = new Attachment();
            attachment.setFileOriginalName(originalFilename);
            attachment.setSize(size);
            attachment.setContentType(contentType);
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setMainContent(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            attachmentContentRepository.save(attachmentContent);
            return new ApiResponse("FILE SUCCESSFULLY SAVED", true);

        }
        return new ApiResponse("Error", false);
    }


    @GetMapping("/info")
    public List<Attachment> getInfo(HttpServletResponse response) {
        List<Attachment> all = attachmentRepository.findAll();
        return all;
    }


    @GetMapping("/info/{id}")
    public Attachment getInfo(@PathVariable Integer id, HttpServletResponse response) {
        Optional<Attachment> byId = attachmentRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }


    @GetMapping("/download/{id}")
    public void download(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> byId = attachmentRepository.findById(id);
        if (byId.isPresent()) {
            Attachment attachment = byId.get();
            Optional<AttachmentContent> byAttachmentId = attachmentContentRepository.findByAttachmentId(id);
            if (byAttachmentId.isPresent()) {
                AttachmentContent attachmentContent = byAttachmentId.get();
                response.setContentType(attachment.getContentType());
                response.setHeader("Content-Disposition", attachment.getFileOriginalName() + "/:" + attachment.getSize());
                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());

            }

        }
    }


    @GetMapping("/downloadWithName")
    public void downloadWithName(@RequestBody String name, HttpServletResponse response) throws IOException {
        Optional<Attachment> byId = attachmentRepository.findByName(name);
        if (byId.isPresent()) {
            Attachment attachment = byId.get();
            Optional<AttachmentContent> byAttachmentId = attachmentContentRepository.findByAttachmentId(attachment.getId());
            if (byAttachmentId.isPresent()) {
                AttachmentContent attachmentContent = byAttachmentId.get();
                response.setContentType(attachment.getContentType());
                response.setHeader("Content-Disposition", attachment.getFileOriginalName() + "/:" + attachment.getSize());
                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());
            }
        }
    }


    @DeleteMapping("/{id}")
    public ApiResponse deleteMedia(@PathVariable Integer id) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
            attachmentContentRepository.deleteById(optionalAttachmentContent.get().getId());
            attachmentRepository.deleteById(attachment.getId());
            return new ApiResponse("DELETED", true);
        }
        return new ApiResponse("NOT FOUND", false);
    }


    @PostMapping("/uploadAnyFile")
    public ApiResponse uploadAnyFiles(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                String originalFilename = file.getOriginalFilename();
                Attachment attachment = new Attachment();
                attachment.setName(originalFilename);
                attachment.setFileOriginalName(originalFilename);
                attachment.setSize(file.getSize());
                attachment.setContentType(file.getContentType());

                attachmentRepository.save(attachment);

                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setMainContent(file.getBytes());
                attachmentContent.setAttachment(attachment);
                attachmentContentRepository.save(attachmentContent);
            } else {
                return new ApiResponse("NOT SAVED", false);
            }
        }
        return new ApiResponse("FILES SUCCESSFULLY SAVED", true);
    }


}
