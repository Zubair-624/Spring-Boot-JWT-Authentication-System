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


    /** --------------------Read by ID Operation // READ: Fetch user by ID-------------------- **/

    // Case - 1
   @Transactional(readOnly = true)
   public UserResponseDTO getByUserId(Long userId){
       log.info("Fetching user with ID: {}", userId);

       // Fetch user or throw exception if not found
       User user = userRepository.findByUserId(userId)
               .orElseThrow(() -> {
                       log.error("User with ID: {} not found", userId);
               return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

   });

       log.info("User with ID: {} retrieved successfully", userId);
       return mapToUserResponseDTO(user);
   }


    /*// Case - 2
    public Optional<UserResponseDTO> getUserByUserId(Long userId){
        return userRepository.findByUserId(userId).map(this::mapToUserResponseDTO);
    }*/


    /**-------------------- Read All Users With Pagination // READ: Fetch all users--------------------**/

    // Case - 1
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(int page, int size){
        log.info("Fetching all users - Page: {}, Size: {} ", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(this::mapToUserResponseDTO);

    }

    /*//Case - 2
    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }*/


    /**-------------------- Update Operation --------------------**/
















    private UserResponseDTO mapToUserResponseDTO(User user){
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .userFullName(user.getUserFullName())
                .userEmail(user.getUserEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()))
                .build();
    }




}
