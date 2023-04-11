package com.pas.repository;

import com.pas.exception.LoginAlreadyTakenException;
import com.pas.model.Rent;
import com.pas.model.User.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepository extends IRepositoryImpl<User> {

    @Inject
    RentRepository rentRepository;

    @Override
    public synchronized User add(User entity) {
        if (!findByLogin(entity.getLogin()).isEmpty()) {
            throw new LoginAlreadyTakenException("Login already taken");
        } else {
            return super.add(entity);
        }
    }

    public List<User> findByLogin(String login) {
        return filter(user -> user.getLogin().contains(login));
    }

    public List<User> findOneByLogin(String login) {
        return new ArrayList<>(filter(user -> user.getLogin().equals(login)));
    }

    public List<Rent> findUserRentss(UUID login) {
        return rentRepository.filter(order -> order.getCustomer().getId().equals(login));
    }

    @PostConstruct
    public void init() {
        User baseUser = new BaseUser("michal", "glapinski", "user", "user", false, 700d);
        User manager = new Manager("michal", "glapinski", "manager", "manager", false, 200d);
        User admin = new Admin("michal", "glapinski", "admin", "admin", false, 200d);
        User baseUser2 = new BaseUser("michal","glapinski","user2","user",false,1000d);

        this.add(baseUser);
        this.add(manager);
        this.add(admin);
        this.add(baseUser2);
    }
}
