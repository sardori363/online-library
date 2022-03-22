package com.sardor.unsplash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sardor.unsplash.annotations.CheckPermission;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.ProfileDto;
import com.sardor.unsplash.payload.UserDto;
import com.sardor.unsplash.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * YANGI USER QOSHISH
     *
     * @param userDto
     * @return ApiResponse(success - > true, message - > ADDED)
     */
    @CheckPermission("ADD_USER")
    @PostMapping()
    public HttpEntity<?> add(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.add(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * USERNI TAXRIRLASH
     *
     * @param id
     * @param userDto
     * @return ApiResponse(success - > true, message - > EDITED)
     */
    @CheckPermission("EDIT_USER")
    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.edit(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * ID OQALI BITTA USERNI OLIB CHIQISH
     *
     * @param id
     * @return ApiResponse(success - > true object - > value)
     */
    @CheckPermission("VIEW_USER")
    @GetMapping("/{id}")
    public HttpEntity<?> get(@PathVariable Integer id) {
        ApiResponse apiResponse = userService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * ID ORQALI BITTA USERNI DELETE QILISH
     *
     * @param id
     * @return ApiResponse(success - > true, message - > DELETED)
     */
    @CheckPermission("DELETE_USER")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Integer id) {
        ApiResponse apiResponse = userService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 404).body(apiResponse);
    }

    /**
     * OZINI PROFILINI TAXRIRLASH
     *
     * @param profileDto
     * @return ApiResponse(success - > true, message - > UPDATED)
     */
    @CheckPermission("EDIT_MY_PROFILE")
    @PutMapping
    public ResponseEntity<?> editMyProfile(@Valid @RequestBody ProfileDto profileDto) {
        ApiResponse apiResponse = userService.editMyProfile(profileDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * ROLE_ID ORQALI USERNI OLIB CHIQISH
     *
     * @param role_id
     * @return ApiResponse(success - > true, message - > FOUND)
     */
    @CheckPermission("VIEW_USER")
    @GetMapping("/get-by-role/{role_id}")
    public HttpEntity<?> getByRole(@PathVariable Integer role_id) {
        ApiResponse apiResponse = userService.getByRole(role_id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
