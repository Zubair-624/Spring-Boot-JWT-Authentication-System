package com.example.springbootsecurityjwtauthenticationandauthorization.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    private Long id;

    private String name;
}
