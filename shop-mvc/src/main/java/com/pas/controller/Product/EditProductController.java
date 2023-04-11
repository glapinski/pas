package com.pas.controller.Product;

import com.pas.controller.User.ChangePasswordController;
import com.pas.model.Rent;
import com.pas.model.Product.Product;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.ProductApiClient;
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
import java.util.Map;
import java.util.UUID;

@Named
@ConversationScoped
@Getter
@Setter
public class EditProductController implements Serializable {
    @Inject
    ProductApiClient productApiClient;
    @Inject
    UserApiClient userApiClient;

    Product currentProduct;
    String productType;

    List<Rent> currentProductRents;
    Map<UUID, String> currentUsers;
    String currentUserId;
    UserDTO currentUser;

    @PostConstruct
    public void initCurrentOrders(){
        Response res = userApiClient.findOneByLogin(ChangePasswordController.context().getUserPrincipal().getName());
        currentUser = res.readEntity(UserDTO.class);
    }

    public String update() throws CloneNotSupportedException {
        productApiClient.updateProduct(currentProduct.getId(), currentProduct);
        return "ListAllProducts";
    }

/*    public String rentProduct(Product product) {
        userApiClient.addToCart(currentUser.getId(), product.getId(), 1l);
        return "ListAllProducts";
    }*/
}
