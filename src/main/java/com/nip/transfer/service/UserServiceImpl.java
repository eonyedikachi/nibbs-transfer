package com.nip.transfer.service;


import com.nip.transfer.exception.TransferException;
import com.nip.transfer.models.entity.User;
import com.nip.transfer.repository.UserRepository;
import com.nip.transfer.models.pojo.request.UserRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(UserRegistration userRegistration) {
        log.info("About to add new user record: {}", userRegistration);
        String uuid = UUID.randomUUID().toString();
        User user = new User();
        user.setUserId(uuid);
        user.setDateCreated(LocalDate.now());
        user.setDateOfBirth(userRegistration.getDateOfBirth());
        user.setEmail(userRegistration.getEmail());
        user.setFirstName(userRegistration.getFirstName());
        user.setMiddleName(userRegistration.getMiddleName());
        user.setLastName(userRegistration.getLastName());
        user.setBalance(userRegistration.getBalance());

        User user1 = userRepository.save(user);
        log.info("New user record created");

        return user1;
    }

    @Override
    public User getUser(String userId) {
        log.info("Fetching user record with userId: {}", userId);
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users' record");
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String userId) {
        log.info("About to delete user record with userId: {}", userId);
        try{
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException | IllegalArgumentException ex) {
            log.error("An error occurred - {} :trying to delete user with userId: {}", ex.getMessage(), userId);
            throw new TransferException("An error occurred - "+ ex.getMessage() + " trying to delete user with userId: " + userId, ex.getCause());
        }
    }

    @Override
    public User updateUser(User user){
     User result = userRepository.findByUserId(user.getUserId());
            log.info("About to update user record: {}", user);
            if (result != null){
            return userRepository.save(user);
            }else {
            log.error("Error updating user record: {}", user);
            throw new TransferException("Error updating user record: " + user);
        }
    }
}