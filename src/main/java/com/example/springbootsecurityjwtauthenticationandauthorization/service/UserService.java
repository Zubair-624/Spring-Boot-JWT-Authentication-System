package com.example.springbootsecurityjwtauthenticationandauthorization.service;

import com.example.springbootsecurityjwtauthenticationandauthorization.dto.UserRequestDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.dto.UserResponseDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.model.Role;
import com.example.springbootsecurityjwtauthenticationandauthorization.model.User;
import com.example.springbootsecurityjwtauthenticationandauthorization.repository.RoleRepository;
import com.example.springbootsecurityjwtauthenticationandauthorization.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    //------------------------- CRUD Operation -------------------------//

    /**--------------------Crate Operation--------------------**/

    /// Register new user
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        log.info("creating user: {}", userRequestDTO.getEmail());

        // Check if email already exists before logging an error
        if (userRepository.existsByUserEmail(userRequestDTO.getEmail())){
            log.info("User already exits: {}", userRequestDTO.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exit");
        }

        // Fetch the default role "USER"
        Role role = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        // Create a new user
        User user = User.builder()
                .userFullName(userRequestDTO.getFullName())
                .userEmail(userRequestDTO.getEmail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .roles(Set.of(role))
                .build();

        // Save user to the database
        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getUserId());

        // Convert User entity to DTO & return
        return mapToUserResponseDTO(savedUser);


    }





}
