package com.pas.manager;

import com.pas.exception.PasswordMismatchExcpetion;
import com.pas.model.Rent;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import com.pas.model.dto.ChangePasswordDTO;
import com.pas.model.dto.RegisterDTO;
import com.pas.model.dto.UserDTO;
import com.pas.repository.RentRepository;
import com.pas.repository.ProductRepository;
import com.pas.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static com.pas.utils.ErrorInfo.ENTITY_NOT_FOUND_MESSAGE;
import static com.pas.utils.ErrorInfo.PASSWORD_MISMATCH;

@ApplicationScoped
@Slf4j
public class UserManager {

    @Inject
    private UserRepository userRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private RentRepository rentRepository;

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
    }

    public User findOneByLogin(String login) {
        return userRepository.findOneByLogin(login).stream().findAny().orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
    }

    public List<Rent> findOngoingUserRents(UUID userId) {
        return rentRepository.filter(order -> order.getCustomer().getId().equals(userId)).stream().filter(rent -> !rent.isFinished()).collect(Collectors.toList());
    }

    public List<Rent> findFinishedUserRents(UUID userId) {
        return rentRepository.filter(order -> order.getCustomer().getId().equals(userId)).stream().filter(Rent::isFinished).collect(Collectors.toList());
    }

    public User register(RegisterDTO user) {
        return userRepository.add(RegisterDTO.fromDTOToEntity(user));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID id, UserDTO updatedUser) {
        User user = findById(updatedUser.getId());
        User updated = UserDTO.fromDTOToEntity(updatedUser);
        updated.setPassword(user.getPassword());
        return userRepository.update(id, updated);
    }

    public void suspendOrResumeUser(UUID userId) {
        Optional<User> found = userRepository.findById(userId);
        if (found.isPresent()) {
            found.get().setSuspended(!found.get().isSuspended());
            userRepository.update(found.get().getId(), found.get());
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue());
        }
    }

    public List<Rent> findUserRents(UUID userId) {
        return userRepository.findUserRentss(userId);
    }

    public List<User> findUsers(Optional<String> allMatchingByLogin, Optional<String> oneByLogin) {
        if (allMatchingByLogin.isPresent()) {
            return userRepository.findByLogin(allMatchingByLogin.get());
        } else if (oneByLogin.isPresent()) {
            return userRepository.findOneByLogin(oneByLogin.get());
        } else {
            return findAllUsers();
        }
    }

    public List<Rent> findAllUserRents(UUID userId) {
        return rentRepository.filter(rent -> rent.getCustomer().getId().equals(userId));
    }

    public void changeUserPassword(UUID id, ChangePasswordDTO changePasswordDTO) {
        User user = findById(id);
        if (changePasswordDTO.getCurrentPassword().equals(user.getPassword())) {
            user.setPassword(changePasswordDTO.getNewPassword());
        } else {
            throw new PasswordMismatchExcpetion(PASSWORD_MISMATCH.getValue());
        }
        userRepository.update(id, user);
    }

    public String getRole(UUID id) {
        List<User> user = (List<User>) userRepository.findById(id).orElse(null);
        if(user instanceof BaseUser)
            return "BaseUser";
        if(user instanceof Manager)
            return "Manager";
        if(user instanceof Admin)
            return "Admin";
        else return "Unauthorized";
    }
}
