package com.pas.manager;

import com.pas.exception.BusinessLogicException;
import com.pas.model.Product.Product;
import com.pas.model.Rent;
import com.pas.repository.RentRepository;
import com.pas.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pas.utils.ErrorInfo.*;

@ApplicationScoped
public class ProductManager {

    @Inject
    private ProductRepository productRepository;

    @Inject
    RentRepository rentRepository;

    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    public List<Product> findByAuthor(String author) {
        return productRepository.findByAuthor(author);
    }

    public Product addItem(Product product) {
        product.setAvailable(true);
        return productRepository.add(product);
    }

    public Product updateProduct(UUID id, Product product) {
        return productRepository.update(id, product);
    }

    public void removeItem(UUID id) {
        if (isInOngoingRent(id)) {
            productRepository.delete(id);
        } else {
            throw new BusinessLogicException(PRODUCT_IN_UNFINISHED_RENT.getValue());
        }
    }

    private boolean isInOngoingRent(UUID productId) {
        Product product = productRepository.findById(productId).orElse(null);
        return product.getIsAvailable();
    }

    public List<Product> getProducts(Optional<String> author, Optional<String> title) {
        if (author.isPresent()) {
            return findByAuthor(author.get());
        } else if (title.isPresent()) {
            return findByTitle(title.get());
        } else {
            return findAllProducts();
        }
    }

    public List<Rent> rentsContainingProduct(UUID id){
        List<Rent> allRents = rentRepository.findAll();
        List<Rent> result = new ArrayList<>();
        for (Rent rent : allRents) {
            if ((rent.getProduct()).getId()==id) {
                result.add(rent);
            }
        }
        return result;
    }

    public List<Rent> findAllProductRents(UUID productId) {
        return rentRepository.filter(rent -> rent.getProduct().getId().equals(productId));
    }
}
