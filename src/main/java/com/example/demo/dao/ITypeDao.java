package com.example.demo.dao;


import com.example.demo.domain.Type;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ITypeDao extends JpaRepository<Type, Long>, JpaSpecificationExecutor<Type> {
   Long countByName(String name);
}
