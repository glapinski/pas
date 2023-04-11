package com.pas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Rent extends IdTrait {
    @JsonProperty
    private User customer;

    @JsonProperty
    private Product product;

    @JsonProperty
    private boolean isFinished;

    @JsonProperty
    private Date rentDate;

    @JsonProperty
    private Date endRentDate;

    public Rent(User customer, Product product, boolean isFinished, Date rentDate, Date endRentDate) {
        this.customer = customer;
        this.product = product;
        this.isFinished = isFinished;
        this.rentDate = rentDate;
        this.endRentDate = endRentDate;
    }

}
