package com.pas.controller.User;

import com.pas.model.User.BaseUser;
import com.pas.model.User.User;
import com.pas.model.dto.RegisterDTO;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Named
@ViewScoped
@Getter
@Setter
public class AddUserController implements Serializable {
    @Inject
    UserApiClient userApiClient;
    @Inject CommonUserController commonUserController;
    RegisterDTO currentUser;
    String userType = "BaseUser";

    @PostConstruct()
    public void init(){
        currentUser = new RegisterDTO();
    }

    public String add(){
        userApiClient.addUser(currentUser);
        return "ListAllUsers";
    }

    public String register(){
        currentUser.setRole("BaseUser");
        userApiClient.register(currentUser);
        return "Index";
    }
}
