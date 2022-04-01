package com.nip.transfer.service;


import com.nip.transfer.models.entity.User;
import com.nip.transfer.models.pojo.request.UserRegistration;

import java.util.List;

public interface UserService {

    User addUser(UserRegistration userRegistration);
    User getUser(String userId);
    List<User> getAllUsers();
    void deleteUser(String userId);
    User updateUser(User user);

}
