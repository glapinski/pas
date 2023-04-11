package com.pas.repository;

import com.pas.model.Rent;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RentRepository extends IRepositoryImpl<Rent> {

    public List<Rent> findOngoingRents(){
        return filter(rent -> !rent.isFinished());
    }

    public List<Rent> findFinishedRents(){
        return filter(rent -> rent.isFinished());
    }

}
