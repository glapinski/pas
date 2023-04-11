package com.pas.repository;

import com.pas.model.Product.Book;
import com.pas.model.Product.Movie;
import com.pas.model.Product.Product;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProductRepository extends IRepositoryImpl<Product> {
    public List<Product> findByTitle(String title){
        return filter(product -> product.getTitle().equals(title));
    }

    public List<Product> findByAuthor(String author){
        return filter(product -> product.getAuthor().equals(author));
    }
    @PostConstruct
    public void init(){
        this.add(new Book(39.99, "tytulksiazki", "weronika", "dramat",500));
        this.add(new Movie(10.0, "tytul", "michal", 24.7, 4));
        this.add(new Movie(20.0, "tytul11", "michal23", 254.7, 4));
    }
}
