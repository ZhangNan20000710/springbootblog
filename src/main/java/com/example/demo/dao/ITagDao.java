package com.example.demo.dao;

import com.example.demo.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ITagDao extends JpaRepository<Tag,Long>, JpaSpecificationExecutor<Tag>{
    Long countByName(String name);
}
