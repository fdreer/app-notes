package com.franco.appnotes.repository;

import com.franco.appnotes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

//    @Override
//    @Query("SELECT u.id, u.username, u.password FROM user u WHERE u.id = ?1")
//    Optional<User> findById(Long id);

    @Transactional
    @Modifying
    @Query("update user u set u.username = ?1 where u.id = ?2")
    void updateUsernameById(String username, Long id);

    Optional<User> findByUsername(String email);
}