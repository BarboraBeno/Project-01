package com.example.project01.Repositories;

import com.example.project01.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.loginName = :loginName")
    List<User> findAllByLoginName(String loginName);

    @Query("select u from User u where u.loginName = :loginName")
    User findByLoginName(String loginName);

    @Query("select u from User u where u.password = :password and u.loginName = :loginName")
    User findByLoginNameAndPassword(String loginName, String password);
}
