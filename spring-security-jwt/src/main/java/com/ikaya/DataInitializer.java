package com.ikaya;

import com.ikaya.entity.User;
import com.ikaya.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setUsername("user");
        user.setPassword(this.passwordEncoder.encode("password"));
        user.setRoles(Arrays.asList("ROLE_USER"));
        this.userRepository.save(user);

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword(this.passwordEncoder.encode("password"));
        user2.setRoles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        this.userRepository.save(user2);

    }
}
