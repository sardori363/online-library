package com.sardor.unsplash.controller;

import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.ConfirmDto;
import com.sardor.unsplash.service.ConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/confirmation")
public class Confirmation {
    @Autowired
    ConfirmService confirmService;

    @PostMapping("/{requestId}")
    public HttpEntity<?> confirm(@PathVariable Integer requestId, @RequestBody ConfirmDto confirmDto) {
        ApiResponse apiResponse = confirmService.confirm(requestId, confirmDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
