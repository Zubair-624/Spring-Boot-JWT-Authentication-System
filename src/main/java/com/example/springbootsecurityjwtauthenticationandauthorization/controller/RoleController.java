package com.example.springbootsecurityjwtauthenticationandauthorization.controller;

import com.example.springbootsecurityjwtauthenticationandauthorization.dto.RoleDTO;
import com.example.springbootsecurityjwtauthenticationandauthorization.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // Create a new role (Only ADMIN can access)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestAttribute String roleName){
        log.info("Creating a new role: {}", roleName);

        return ResponseEntity.ok(roleService.createRole(roleName));
    }

    // Get all roles (Only ADMIN can access)
    public ResponseEntity<List<RoleDTO>> getAllRoles(){
        log.info("Fetching all roles");

        return ResponseEntity.ok(roleService.getAllRoles());
    }





}
