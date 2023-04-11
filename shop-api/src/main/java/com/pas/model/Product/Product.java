package com.pas.model.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pas.model.IdTrait;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Book.class, name = "Book"),
        @JsonSubTypes.Type(value = Movie.class, name = "Movie"),
})
public abstract class Product extends IdTrait implements Cloneable{
    @JsonProperty
    private boolean isAvailable;
    @JsonProperty
    private Double price;
    @JsonProperty
    @Size(min = 2, max = 20)
    private String title;
    @JsonProperty
    @Size(min = 2, max = 20)
    private String author;

    public Product(Double price, String title, String author) {
        this.isAvailable = true;
        this.price = price;
        this.title = title;
        this.author = author;
    }

    @Override
    public Product clone() throws CloneNotSupportedException {
        return (Product) super.clone();
    }

    public boolean getIsAvailable() {
        return this.isAvailable;
    }

    public Double getPrice() {
        return this.price;
    }

    public @Size(min = 2, max = 20) String getTitle() {
        return this.title;
    }

    public @Size(min = 2, max = 20) String getAuthor() {
        return this.author;
    }

}
