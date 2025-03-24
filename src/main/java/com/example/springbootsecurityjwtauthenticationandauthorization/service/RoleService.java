package com.example.springbootsecurityjwtauthenticationandauthorization.service;

import com.example.springbootsecurityjwtauthenticationandauthorization.dto.RoleDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.model.Role;
import com.example.springbootsecurityjwtauthenticationandauthorization.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    /** -------------------- Create Operation: Assign a New Role -------------------- **/
    public RoleDTO createRole(String roleName){
        log.info("Creating a new role named", roleName);

        // Check if role already exists
        if (roleRepository.findByRoleName(roleName).isPresent()){
            log.error("Role already exists: {}", roleName);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role already exists");

        }

        // Create and save new role
        Role role = roleRepository.save(Role.builder().roleName(roleName).build());
        log.info("Role '{}' created successfully", roleName);

        return new RoleDTO(role.getRoleName());
    }

    /** -------------------- Read Operation: Get All Roles -------------------- **/
    public List<RoleDTO> getAllRoles(){
        log.info("Fetching all roles");
        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleDTO(role.getRoleName()))
                .collect(Collectors.toList());
    }



}
