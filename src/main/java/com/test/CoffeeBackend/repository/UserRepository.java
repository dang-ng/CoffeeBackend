package com.test.CoffeeBackend.repository;

import com.test.CoffeeBackend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Integer>
{
    Optional<AppUser> findByPhone(String phone);
    Boolean existsByPhone(String phone);

}
