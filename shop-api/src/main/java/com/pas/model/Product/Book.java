package com.pas.model.Product;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends Product {
    private String genre;

    private int pageAmount;

    public Book(Double price, String title, String author, String genre, int pageAmount) {
        super(price, title, author);
        this.genre=genre;
        this.pageAmount=pageAmount;
    }
}
