package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Contacts;
import com.sardor.unsplash.repository.ContactsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sardor.unsplash.entity.Attachment;
import com.sardor.unsplash.entity.Role;
import com.sardor.unsplash.entity.User;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.ProfileDto;
import com.sardor.unsplash.payload.UserDto;
import com.sardor.unsplash.repository.AttachmentRepository;
import com.sardor.unsplash.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    ContactsRepository contactsRepository;

    public ApiResponse add(UserDto userDto) {
        boolean b = userRepository.existsByUsername(userDto.getUsername());
        if (b) return new ApiResponse("USER ALREADY EXISTS", false);

        ApiResponse response = roleService.get((int) userDto.getRoleId().longValue());
        if (!response.isSuccess())
            return response;

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setBio(userDto.getBio());

        List<Contacts> allById = contactsRepository.findAllById(userDto.getContactsId());
        if (allById.isEmpty()) return new ApiResponse("contacts not found", false);
        user.setContacts(allById);


        user.setRole((Role) response.getObject());

        user.setEnabled(userDto.getEnabled());

        Optional<Attachment> optionalPhoto = attachmentRepository.findById(userDto.getPhotoId());
        if (optionalPhoto.isEmpty()) return new ApiResponse("PHOTO NOT FOUND", false);
        user.setPhoto(optionalPhoto.get());

        userRepository.save(user);
        return new ApiResponse("ADDED", true);
    }

    public ApiResponse edit(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) return new ApiResponse("USER NOT FOUND", false);
        boolean b = userRepository.existsByUsername(userDto.getUsername());

        if (b) return new ApiResponse("USERNAME ALREADY EXISTS", false);

        ApiResponse response = roleService.get((int) userDto.getRoleId().longValue());
        if (!response.isSuccess())
            return response;

        User user = optionalUser.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setBio(userDto.getBio());

        List<Contacts> allById = contactsRepository.findAllById(userDto.getContactsId());
        if (allById.isEmpty()) return new ApiResponse("contacts not found", false);
        user.setContacts(allById);

        user.setRole((Role) response.getObject());
        user.setEnabled(userDto.getEnabled());

        Optional<Attachment> optionalPhoto = attachmentRepository.findById(userDto.getPhotoId());
        if (optionalPhoto.isEmpty()) return new ApiResponse("PHOTO NOT FOUND", false);

        user.setPhoto(optionalPhoto.get());
        userRepository.save(user);
        return new ApiResponse("EDITED", true);
    }

    public ApiResponse get(Integer id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) return new ApiResponse("NOT FOUND", false);

        return new ApiResponse("FOUND", true, userRepository.findById(id).get());
    }

    public ApiResponse delete(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) return new ApiResponse("USER NOT FOUND", false);
        userRepository.deleteById(id);
        return new ApiResponse("DELETED", true);
    }

    public ApiResponse editMyProfile(ProfileDto profileDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.existsByUsernameAndIdNot(profileDto.getUsername(), user.getId()))
            return new ApiResponse("USERNAME ALREADY EXISTS", false);

        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setUsername(profileDto.getUsername());
        user.setPassword(passwordEncoder.encode(profileDto.getPassword()));
        user.setBio(profileDto.getBio());

        List<Contacts> allById = contactsRepository.findAllById(profileDto.getContactsId());
        if (allById.isEmpty()) return new ApiResponse("contacts not found", false);
        user.setContacts(allById);

        Optional<Attachment> optionalPhoto = attachmentRepository.findById(profileDto.getPhotoId());
        if (optionalPhoto.isEmpty()) return new ApiResponse("PHOTO NOT FOUND", false);

        user.setPhoto(optionalPhoto.get());

        userRepository.save(user);
        return new ApiResponse("UPDATED", true);
    }


    public ApiResponse getByRole(Integer role_id) {
        List<User> allByRole_id = userRepository.findAllByRole_Id(role_id);
        if (allByRole_id.isEmpty()) return new ApiResponse("NOT FOUND", false);

        return new ApiResponse("FOUND", true, allByRole_id);
    }

}
