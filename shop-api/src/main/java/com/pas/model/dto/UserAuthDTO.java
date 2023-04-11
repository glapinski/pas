package com.pas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserAuthDTO {
    private String role;
    private String login;
    private UUID id;
}
