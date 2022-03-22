package com.sardor.unsplash.component;

import com.sardor.unsplash.entity.Role;
import com.sardor.unsplash.entity.User;
import com.sardor.unsplash.enums.Permissions;
import com.sardor.unsplash.repository.RoleRepository;
import com.sardor.unsplash.repository.UserRepository;
import com.sardor.unsplash.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {

        if (initMode.equals("always")) {
            Permissions[] permissions = Permissions.values();
            Role user = roleRepository.save(new Role(Constants.USER, Arrays.asList(permissions)));
            Role admin = roleRepository.save(new Role(Constants.ADMIN, Arrays.asList(permissions)));


            List<User> all = userRepository.findAll();
            if (all.isEmpty()) {
                userRepository.save(new User(
                        "Sardor",
                        "Odiljonov",
                        "sardor363",
                        passwordEncoder.encode("123"),
                        admin,
                        true
                ));
            }
        }


    }
}
