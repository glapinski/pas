package com.pas.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pas.model.Product.Product;
import com.pas.model.Rent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentDTO {
    @JsonProperty
    UUID id;

    @JsonProperty
    private Product product;
    @JsonProperty
    private UserDTO customer;
    @JsonProperty
    private Date rentDate;

    @JsonProperty
    private Date endRentDate;
    @JsonProperty
    private boolean isFinished;

    public static RentDTO fromEntityToDTO(Rent rent) {
        return new RentDTO(rent.getId(),rent.getProduct(),UserDTO.fromEntityToDTO(rent.getCustomer()),rent.getRentDate(),rent.getEndRentDate(),rent.isFinished());
    }

    public static List<RentDTO> entityListToDTO(List<Rent> rents) {
        return rents.stream().map(RentDTO::fromEntityToDTO).collect(Collectors.toList());
    }

}
