package com.example.socialnetwork.repository;

import com.example.socialnetwork.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Long> {
    Optional<UserDto> findByUserId(String userId);
}