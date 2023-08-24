package com.example.backend.user;

import com.example.backend.user.dtos.UserDetails;
import com.example.backend.user.entities.User;

import jakarta.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Integer> {

    @Query(value = """
            SELECT user_id, email, username, password_hash
            FROM users
            WHERE username = ?1
            """, nativeQuery = true)
    Optional<Tuple> findByUserName(String userName);
}
