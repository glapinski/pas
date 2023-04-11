package com.pas.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pas.model.User.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {
    @JsonProperty
    @Size(min = 2, max = 20)
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String login;
    @JsonProperty
    private String password;
    @JsonProperty
    private Double accountBalance;
    @JsonProperty
    private boolean suspended;
    @JsonProperty
    private String role;

    public RegisterDTO() {

    }

    public static User fromDTOToEntity(RegisterDTO userDTO) {
        if (userDTO.getRole().equals("BaseUser")) {
            return new BaseUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(),false, userDTO.getAccountBalance());
        } else if (userDTO.getRole().equals("Manager")) {
            return new Manager(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(), false, userDTO.getAccountBalance());
        } else if (userDTO.getRole().equals("Admin")) {
            return new Admin(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(), false, userDTO.getAccountBalance());
        } else {
            throw new IllegalStateException("User is not instance of proper class");
        }
    }
}
