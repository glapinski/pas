package com.pas.controller.User;

import com.pas.model.Rent;
import com.pas.model.Product.Product;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.RentApiClient;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
@Getter
@Setter
public class UserRentController implements Serializable {
    @Inject
    UserApiClient userApiClient;
    @Inject
    RentApiClient rentApiClient;
    @Inject
    CommonUserController commonUserController;
    UserDTO currentUser;

    List<Rent> currentUserRents;

    public String createRent(Product product){
        rentApiClient.createRent(currentUser.getId(),product.getId());
        return "UserRents";
    }

    @PostConstruct()
    public void init(){
        Response res = userApiClient.findOneByLogin(ChangePasswordController.context().getUserPrincipal().getName());
        currentUser = res.readEntity(UserDTO.class);
    }
}
