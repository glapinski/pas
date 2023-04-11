package com.pas.controller.Product;

import com.pas.model.Product.Book;
import com.pas.model.Product.Movie;
import com.pas.model.Product.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ApplicationScoped
public class CommonProductController implements Serializable {
    public Product changeProductType(String productType){
        if(productType.equals("Movie")){
            Movie prod = new Movie();
            return prod;
        } else {
            Book prod = new Book();
            return prod;
        }
    }
}
