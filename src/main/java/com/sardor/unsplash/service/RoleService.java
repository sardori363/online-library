package com.sardor.unsplash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sardor.unsplash.entity.Role;
import com.sardor.unsplash.exeptions.RescuersNotFoundEx;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.RoleDto;
import com.sardor.unsplash.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    final
    RoleRepository roleRepository;


    public ApiResponse add(RoleDto roleDto) {
        boolean b = roleRepository.existsByName(roleDto.getName());
        if (b) return new ApiResponse("ROLE ALREADY EXIXTS", false);
        Role role = new Role();
        role.setName(roleDto.getName());
        role.setPermissions(roleDto.getPermissions());
        role.setDescription(roleDto.getDescription());


        roleRepository.save(role);
        return new ApiResponse("ADDED", true);
    }

    public ApiResponse edit(Integer id, RoleDto roleDto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) return new ApiResponse("USER NOT FOUND", false);

        Role role = optionalRole.get();
        role.setName(roleDto.getName());
        role.setPermissions(roleDto.getPermissions());
        role.setDescription(roleDto.getDescription());

        roleRepository.save(role);
        return new ApiResponse("EDITED", true);
    }

    public ApiResponse get(Integer id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.map(role -> new ApiResponse("FOUND", true, role)).orElseThrow(() -> new RescuersNotFoundEx("Role", "id", id));
    }

    public ApiResponse delete(Integer id) {
        boolean b = roleRepository.existsById(id);
        if (!b) return new ApiResponse("ROLE NOT FOUND", false);
        roleRepository.deleteById(id);
        return new ApiResponse("DELETED", true);
    }
}
