package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.User;
import com.sardor.unsplash.exeptions.RescuersNotFoundEx;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.ProfileDto;
import com.sardor.unsplash.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sardor.unsplash.repository.RoleRepository;
import com.sardor.unsplash.repository.UserRepository;

@Service
public class  AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;


    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
    }

    public ApiResponse registerUser(ProfileDto registerDto) {

        boolean b = userRepository.existsByUsername(registerDto.getUsername());

        if (!registerDto.getPassword().equals(registerDto.getPrePassword()))
            return new ApiResponse("Password not equals", false);

        if (b) return new ApiResponse("User already exist", false);

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(roleRepository.findByName(Constants.USER).orElseThrow(() -> new RescuersNotFoundEx("role", "name", Constants.USER)));
        user.setEnabled(true);

        userRepository.save(user);
        return new ApiResponse("Saved", true);
    }
}
