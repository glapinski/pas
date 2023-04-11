package com.pas.model.Product;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Movie extends Product {
    private Double length;

    private int rate;


    public Movie(Double price, String title, String author, Double length, int rate) {
        super(price, title, author);
        this.length=length;
        this.rate=rate;
    }
}
