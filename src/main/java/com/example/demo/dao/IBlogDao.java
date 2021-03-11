package com.example.demo.dao;

import com.example.demo.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface IBlogDao extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    Page<Blog> findByUserUsername(String username, Pageable pageable);

    Long countByTypeId(Long id);
    Page<Blog> findByTypeId(Long id,Pageable pageable);
    List<Blog> findByUpdateTimeStartingWith(String like);
}
