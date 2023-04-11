package com.pas.controller.Product;

import com.pas.controller.Conversational;
import com.pas.controller.Utils.ViewUtils;
import com.pas.model.Product.Product;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.ProductApiClient;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
@Getter
@Setter
public class ListAllProductsController extends Conversational implements Serializable {

    @Inject
    ProductApiClient productApiClient;
    @Inject
    UserApiClient userApiClient;

    @Inject
    EditProductController editProductController;
    List<Product> currentProducts;
    @Getter
    @Setter
    String currentUserId;

    boolean isCreatingNewProduct = true;


    @PostConstruct
    public void initCurrentProducts()  {
        currentProducts = productApiClient.getAllProducts();
    }
    public String delete(Product product) {
        productApiClient.deleteProductById(product.getId());
        return "ListAllProducts";
    }

    public String editProduct(Product product) {
        beginNewConversation();
       editProductController.setCurrentProduct(product);
       editProductController.setProductType(ViewUtils.getClassName(product));
        return "EditProduct";
    }

    public String getDetails(Product product) {
        beginNewConversation();
        editProductController.setCurrentProduct(product);
        editProductController.setProductType(ViewUtils.getClassName(product));
        editProductController.setCurrentUsers(userApiClient.getAllUsers().stream().collect(Collectors.toMap(UserDTO::getId, UserDTO::getLogin)));
        editProductController.setCurrentProductRents(productApiClient.rentsContainingProduct(product.getId()));
        return "ProductDetails";
    }
}
