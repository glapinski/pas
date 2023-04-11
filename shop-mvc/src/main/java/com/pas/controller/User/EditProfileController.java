package com.pas.controller.User;

import com.pas.controller.Conversational;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static com.pas.controller.User.ChangePasswordController.context;

@ViewScoped
@Named
@Getter
@Setter
public class EditProfileController implements Serializable {

    UserDTO currentUser;

    @Inject
    UserApiClient userApiClient;
    String ifMatch="";

    @PostConstruct()
    public void init(){
        Response res = userApiClient.findOneByLogin(context().getUserPrincipal().getName());
        currentUser = res.readEntity(UserDTO.class);
        ifMatch = res.getEntityTag().getValue();
    }

    public static ExternalContext context(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public String update() {
        userApiClient.updateUser(currentUser.getId(), currentUser, ifMatch);
        return "Start";
    }
}
