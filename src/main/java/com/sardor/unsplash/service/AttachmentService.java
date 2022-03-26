package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Attachment;
import com.sardor.unsplash.entity.AttachmentContent;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.repository.AttachmentContentRepository;
import com.sardor.unsplash.repository.AttachmentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @SneakyThrows
    public ApiResponse uploadFile(MultipartHttpServletRequest request){
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

    public HttpEntity<?> getAllInfo(){
        return ResponseEntity.ok(attachmentRepository.findAll());
    }

    public HttpEntity<?> getInfoById(Integer id){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        return ResponseEntity.status(optionalAttachment.isPresent()?200:409).body(optionalAttachment.orElse(null));
    }

    @SneakyThrows
    public void downloadFile(Integer id, HttpServletResponse response){
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

    @SneakyThrows
    public void downloadByName(String name, HttpServletResponse response){
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

    public ApiResponse deleteMedia(Integer id) {
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

    @SneakyThrows
    public ApiResponse uploadAnyFiles(MultipartHttpServletRequest request) {
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

                Attachment savedAttachment = attachmentRepository.save(attachment);

                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setMainContent(file.getBytes());
                attachmentContent.setAttachment(savedAttachment);
                attachmentContentRepository.save(attachmentContent);
            } else {
                return new ApiResponse("NOT SAVED", false);
            }
        }
        return new ApiResponse("FILES SUCCESSFULLY SAVED", true);
    }
}
