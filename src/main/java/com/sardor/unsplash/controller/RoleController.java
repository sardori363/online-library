package com.sardor.unsplash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sardor.unsplash.annotations.CheckPermission;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.RoleDto;
import com.sardor.unsplash.service.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /**
     * ROLE (LAVOZIM) QOSHISH
     *
     * @param roleDto
     * @return ApiResponse(success - > true, message - > ADDED)
     */
    @CheckPermission("ADD_ROLE")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.add(roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * ID ORQALI LAVOZIMNI TAHRIRLASH
     *
     * @param id
     * @param roleDto
     * @return ApiResponse(success - > true, message - > EDITED)
     */
    @CheckPermission("EDIT_ROLE")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.edit(id, roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * ID ORQALI LAVOZIMNI OLIB CHIQISH
     *
     * @param id
     * @return ApiResponse(success - > true, message - > FOUND)
     */
    @CheckPermission("VIEW_ROLE")
    @GetMapping("/{id}")
    public HttpEntity<?> get(@PathVariable Integer id) {
        ApiResponse apiResponse = roleService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    /**
     * ID ORQALI LAVOZIMNI DELETE QILISH
     *
     * @param id
     * @return ApiResponse(success - > true, message - > DELETED)
     */
    @CheckPermission("DELETE_ROLE")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = roleService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
