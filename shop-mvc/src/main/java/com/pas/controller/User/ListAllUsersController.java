package com.pas.controller.User;

import com.pas.controller.Conversational;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.GenericType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Named
@ViewScoped
@Getter
@Setter
public class ListAllUsersController extends Conversational implements Serializable {

    @Inject
    UserApiClient userApiClient;

    @Inject
    EditUserController editUserController;
    @Inject
    UserRentController userRentController;

    List<UserDTO> currentUsers;
    String searchInput = "";

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(
            "messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void initCurrentUsers(){
       currentUsers = userApiClient.getAllUsers();
    }
    public String suspendOrResumeUser(UserDTO user){
        userApiClient.suspendOrResumeUser(user.getId());
        return "ListAllUsers";
    }

    public void getSearchedUsers(){
        currentUsers = userApiClient.getSearchedUsers(Optional.of(searchInput));
    }

    public String editUser(UserDTO user){
        beginNewConversation();
        editUserController.setCurrentUser(user);
        editUserController.setUserType(user.getClass().getSimpleName());
        return "EditUser";
    }

    public String getDetails(UserDTO user){
        beginNewConversation();
        editUserController.setCurrentUser(user);
        editUserController.setUserType(user.getClass().getSimpleName());
/*        editUserController.setCurrentUserRents(userApiClient.findUserRents(user.getId()));*/
        return "UserDetails";
    }

    public void loginValidator(FacesContext context, UIComponent component, Object value){
        String login = (String) value;
        if (!userApiClient.findOneByLogin(login).readEntity(new GenericType<List<UserDTO>>(){}).isEmpty()) {
            throw new ValidatorException(new FacesMessage(resourceBundle.getString("validatorMessageLoginUsed")));
        }
    }
}
