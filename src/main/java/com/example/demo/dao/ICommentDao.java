package com.example.demo.dao;

import com.example.demo.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICommentDao extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Comment> {
    Page<Comment> findByBlogIdAndParentCommentId(Long id,Long parentId,Pageable pageable);
    List<Comment> findByParentCommentId(List<Long> longs);

    @Query("from Comment where blog.id=?1 and parentComment.id<>null")
    List<Comment> find(Long blogId);

    Long countByParentCommentId(Long id);

}
