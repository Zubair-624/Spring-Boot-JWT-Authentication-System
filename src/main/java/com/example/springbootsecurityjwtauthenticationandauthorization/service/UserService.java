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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    /**--------------------Crate Operation // Register new user--------------------**/
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        log.info("creating user: {}", userRequestDTO.getEmail());

        // Check if email already exists in the database
        if (userRepository.existsByUserEmail(userRequestDTO.getEmail())){
            log.info("User already exits: {}", userRequestDTO.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exit");
        }

        // Fetch the default "USER" role from the database
        Role role = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        // Encrypt the user's password before storing it
        String hashedPassword = passwordEncoder.encode(UserRequestDTO.getPassword())

        // Create a new User entity (but NOT saved yet)
        User user = User.builder()
                .userFullName(userRequestDTO.getFullName())
                .userEmail(userRequestDTO.getEmail())
                .password(hashedPassword)
                .roles(Set.of(role))
                .build();

        // Save the new user in the database
        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getUserId());

        // Convert the User entity to a UserResponseDTO (hides sensitive info)
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

       // Log success
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

        // Create pageable request
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        // Convert user entities to DTOs and return
        return userPage.map(this::mapToUserResponseDTO);

    }

    /*//Case - 2
    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }*/


    /**-------------------- Update Operation // UPDATE: Update user details --------------------**/
    @Transactional
    public UserResponseDTO updateUserByUserId(Long userId, UserRequestDTO userRequestDTO){
        log.info("Updating user with ID: {}", userId);

        // Fetch user or throw exception if not found
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("user with ID: {} not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        // Update user details
        user.setUserFullName(userRequestDTO.getFullName());
        user.setUserEmail(userRequestDTO.getEmail());

        // Save the updated user
        User updatedUser = userRepository.save(user);
        log.info("User with ID: {} updated successfully", userId);

        return mapToUserResponseDTO(updatedUser);
    }

    /**-------------------- Delete Operation // DELETE: Delete a user by ID --------------------**/

    // Case -1
    @Transactional
    public void deleteUserByUserId(Long userId){
        log.info("Deleting user with ID: {}", userId);

        // Fetch user or throw exception if not found
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("user with ID: {} not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        // Delete user from database
        userRepository.delete(user);
        log.info("User with ID: {} deleted successfully", userId);

    }

   /* //Case - 2
    public void deleteUserByUserId(Long userId){
        if (userRepository.findByUserId(userId).isEmpty()){
            throw new RuntimeException("User not found with ID: " + userId);

        }

        userRepository.deleteUserByUserId(userId);
    }*/

    /**-------------------- Convert Entity to DTO // Convert User entity to UserResponseDTO--------------------**/
    private UserResponseDTO mapToUserResponseDTO(User user){
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .userFullName(user.getUserFullName())
                .userEmail(user.getUserEmail())
                .roles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()))
                .build();
    }




}
