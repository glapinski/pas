package com.pas.controller.User;

import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ApplicationScoped
public class CommonUserController implements Serializable {
    public UserDTO createUserOfType(UserDTO user, String userType){
        if(userType.equals("BaseUser")){
            user = UserDTO.baseUser(user);
        } else if(userType.equals("Manager")){
            user = UserDTO.manager(user);
        } else {
            user = UserDTO.admin(user);
        }
        return user;
    }

}
