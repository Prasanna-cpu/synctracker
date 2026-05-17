package com.spring.synctracker.user_service.repository;

import com.spring.synctracker.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select case when count(u) > 0 then true else false end from User u where u.email = ?1")
    Boolean existsByEmail(String email);

}
