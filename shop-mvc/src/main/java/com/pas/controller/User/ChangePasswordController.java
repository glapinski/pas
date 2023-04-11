package com.pas.controller.User;

import com.pas.controller.Conversational;
import com.pas.model.User.User;
import com.pas.model.dto.ChangePasswordDTO;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
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

@ViewScoped
@Named
@Getter
@Setter
public class ChangePasswordController implements Serializable {

    UserDTO currentUser;

    @Inject
    UserApiClient userApiClient;

    ChangePasswordDTO changePasswordDTO;
    @PostConstruct()
    public void init(){
        changePasswordDTO = new ChangePasswordDTO();
        Response res = userApiClient.findOneByLogin(context().getUserPrincipal().getName());
        currentUser = res.readEntity(UserDTO.class);
    }

    public static ExternalContext context(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public String changePassword() {
        userApiClient.changePassword(currentUser.getId(), changePasswordDTO);
        return "Index";
    }
}
