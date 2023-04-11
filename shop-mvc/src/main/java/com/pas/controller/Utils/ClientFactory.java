package com.pas.controller.Utils;


import com.pas.model.Product.Product;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

public class ClientFactory {
    private static String API_URL = "https://localhost:8181/shop-rest-1.0-SNAPSHOT/";

    public static Client client() {
        return ClientBuilder.newClient();
    }

    public static List<Product> getRequest(){
        return client().target(API_URL+"products")
                .request()
                .get(new GenericType<List<Product>>() {
                });
    }
}
