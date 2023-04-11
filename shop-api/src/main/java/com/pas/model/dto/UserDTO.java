package com.pas.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pas.model.User.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDTO {
    @JsonProperty
    UUID id;
    @JsonProperty
    @Size(min = 2, max = 20)
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty
    private Double accountBalance;
    @JsonProperty
    private boolean suspended;

    public UserDTO(){

    }
    public UserDTO(UUID id, String firstName, String lastName, String login, String password, Double accountBalance, boolean suspended, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.accountBalance = accountBalance;
        this.suspended = suspended;
        this.role = role;
    }

    public UserDTO(String firstName, String lastName, String login, String password, Double accountBalance, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.accountBalance = accountBalance;
        this.role = role;
    }

    @JsonProperty
    private String role;

    public UserDTO(UserDTO dto) {
        this.id = dto.id;
        this.firstName = dto.firstName;
        this.lastName = dto.lastName;
        this.login = dto.login;
        this.password = dto.password;
        this.accountBalance = dto.accountBalance;
        this.suspended = dto.suspended;
        this.role = dto.role;
    }

    public UserDTO(UUID id, String firstName, String lastName, String login, Double accountBalance, boolean suspended, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.accountBalance = accountBalance;
        this.suspended = suspended;
        this.role = role;
    }

    public UserDTO(String firstName, String lastName, String login,Double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.accountBalance = accountBalance;
    }

    public static UserDTO fromEntityToDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getAccountBalance(), user.isSuspended(), user.getClass().getSimpleName());
    }

    public static List<UserDTO> entityListToDTO(List<User> users) {
        return users.stream().map(UserDTO::fromEntityToDTO).collect(Collectors.toList());
    }

    public static User fromDTOToEntity(UserDTO userDTO) {
        if (userDTO.getRole().equals("BaseUser")) {
            return new BaseUser(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(),false, userDTO.getAccountBalance());
        } else if (userDTO.getRole().equals("Manager")) {
            return new Manager(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(), false, userDTO.getAccountBalance());
        } else if (userDTO.getRole().equals("Admin")) {
            return new Admin(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(), false, userDTO.getAccountBalance());
        } else {
            throw new IllegalStateException("User is not instance of proper class");
        }
    }



    public static UserDTO baseUser(UserDTO userDTO){
        UserDTO user = new UserDTO(userDTO);
        user.setRole("BaseUser");
        return user;
    }

    public static UserDTO baseUser(){
        UserDTO user = new UserDTO();
        user.setRole("BaseUser");
        return user;
    }
    public static UserDTO admin(UserDTO userDTO){
        UserDTO user = new UserDTO(userDTO);
        user.setRole("Admin");
        return user;
    }

    public static UserDTO manager(UserDTO userDTO){
        UserDTO user = new UserDTO(userDTO);
        user.setRole("Manager");
        return user;
    }
}
