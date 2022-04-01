package com.nip.transfer.repository;

import com.nip.transfer.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUserId(String accountNumber);
}
