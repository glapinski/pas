package com.pas.manager;

import com.pas.model.Rent;
import com.pas.model.User.User;
import com.pas.repository.RentRepository;
import com.pas.repository.ProductRepository;
import com.pas.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import com.pas.exception.BusinessLogicException;
import com.pas.model.Product.Product;

import java.util.*;
import java.util.stream.Collectors;

import static com.pas.utils.ErrorInfo.*;


@ApplicationScoped
public class RentManager {

    @Inject
    private RentRepository rentRepository;

    @Inject
    private UserManager userManager;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductManager productManager;

    @Inject
    private UserRepository userRepository;

    public Rent findById(UUID id) {
        return rentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
    }

    public List<Rent> findAllRents() {
        return rentRepository.findAll();
    }

    public Rent createRent(UUID userId, UUID productId) {
        User customer = userManager.findById(userId);
        Product product = productRepository.findById(productId).orElse(null);
        Double rentValue = product.getPrice();

        if (shouldRent(userId,rentValue,product)) {
            payForRent(customer, rentValue);
            product.setAvailable(false);
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 31);
            date = calendar.getTime();
            return rentRepository.add(new Rent(customer,product,false,new Date(), date));
        } else {
            throw new BusinessLogicException(ORDER_VIOLATED_BUSINESS_LOGIC.getValue());
        }
    }

    public void payForRent(User user, Double rentValue) {
        user.setAccountBalance(user.getAccountBalance() - rentValue);
    }

    public boolean shouldRent(UUID userId, Double rentValue, Product product) {
        return product.getIsAvailable() && !isUserSuspended(userId) && checkUserBalance(userId, rentValue);
    }

    private boolean checkUserBalance(UUID userId, Double rentValue) {
        User found = userRepository.findById(userId).orElse(null);
        return isEnoughMoney(found.getAccountBalance(), rentValue);
    }

    private Map<Product, Long> toProductMap(Map<UUID, Long> orderItemsIds) {
        return orderItemsIds.entrySet()
                .stream()
                .collect(Collectors.toMap( e -> productManager.findById(e.getKey()), Map.Entry::getValue));
    }

    private Map<UUID, Long> toIdMap(Map<Product, Long> orderItems) {
        return orderItems.entrySet()
                .stream()
                .collect(Collectors.toMap( e -> e.getKey().getId(), Map.Entry::getValue));
    }

    private boolean isUserSuspended(UUID userId) {
        User found = userManager.findById(userId);
        return Optional.ofNullable(found)
                .map(User::isSuspended)
                .filter(result -> result.equals(false))
                .orElseThrow(() -> new BusinessLogicException(RENT_CUSTOMER_SUSPENDED.getValue()));
    }

    private boolean isEnoughMoney(Double userMoney, Double orderValue) {
        return userMoney - orderValue >= 0;
    }

    public void endRent(UUID rentId) {
        Optional<Rent> rent = rentRepository.findById(rentId);
        if(!rent.get().isFinished()) {
            rent.get().getProduct().setAvailable(true);
            rent.get().setFinished(true);
            rent.get().setEndRentDate(new Date());
            rentRepository.update(rent.get().getId(),rent.get());
        }
    }

    public List<Rent> findOngoingRents() {
        return rentRepository.findOngoingRents();
    }

    public List<Rent> findFinishedRents() {
        return rentRepository.findFinishedRents();
    }

    public User findUserInRent(UUID orderId){
        return rentRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue())).getCustomer();
    }
}
