package com.pas.restClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import lombok.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestScoped
public class AuthApiClient extends RestClient{

    private static final String AUTH_HEADER_NAME="Authentication";

    public AuthApiClient() {}

    public String login(String login, String password){
        Credentials credentials = new Credentials(login, password);
            Optional<String> header = Optional.ofNullable(client.path("/auth/login")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(credentials)).getHeaderString(AUTH_HEADER_NAME));
            return header.isPresent() ? header.get() : "";

    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Credentials {
        private String login;
        private String password;
    }
}
