package com.pas.endpoint;

import com.pas.model.Rent;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.pas.manager.ProductManager;
import com.pas.model.Product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/products")
@Consumes("application/json")
@Produces("application/json")
public class ProductApi {

    @Inject
    ProductManager productManager;
    @GET
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    public List<Product> getProducts(@QueryParam("author") Optional<String> author, @QueryParam("title") Optional<String> title) {
        return productManager.getProducts(author, title);
    }
    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    public Product getProductById(@PathParam("id") UUID id) {
        return productManager.findById(id);
    }
    @POST
    @RolesAllowed({"Manager"})
    public Product addProduct(@Valid Product product) {
        return productManager.addItem(product);
    }
    @PUT
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Product updateProduct(@PathParam("id") UUID id, @Valid Product product) {
        return productManager.updateProduct(id, product);
    }
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Response deleteProduct(@PathParam("id") UUID id) {
        productManager.removeItem(id);
        return Response.ok().build();
    }
    @GET
    @Path("/{id}/rentsContainingProduct")
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    public List<Rent> rentsContainingProduct(@PathParam("id") UUID id) {
        return productManager.rentsContainingProduct(id);
    }

    @GET
    @Path("/{id}/allRents")
    @RolesAllowed({"Admin", "Unauthorized", "BaseUser", "Manager"})
    public List<Rent> getAllProductRents(@PathParam("id") UUID productId) {
        return productManager.findAllProductRents(productId);
    }

}
