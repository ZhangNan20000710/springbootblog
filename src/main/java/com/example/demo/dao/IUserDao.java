package com.example.demo.dao;


import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
   Long countByUsernameAndPassword(String name,String pwd);
   Long countByUsername(String name);
   User findByUsername(String name);
}
