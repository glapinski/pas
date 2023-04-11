package com.pas.controller.User;

import com.pas.controller.Conversational;
import com.pas.model.Rent;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.RentApiClient;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
public class RentUserListController extends Conversational implements Serializable {
    @Inject
    RentApiClient rentApiClient;
    @Inject
    UserApiClient userApiClient;
    UserDTO currentUser;
    List<Rent> currentRents;

    @PostConstruct
    public void initCurrentOrders(){
        Response res = userApiClient.findOneByLogin(ChangePasswordController.context().getUserPrincipal().getName());
        currentUser = res.readEntity(UserDTO.class);
        currentRents = userApiClient.getUserRents(currentUser.getId());
    }
}
