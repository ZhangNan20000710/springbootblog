package com.example.demo.controller;

import com.example.demo.dao.IBlogDao;
import com.example.demo.dao.ICommentDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.domain.Blog;
import com.example.demo.domain.Comment;
import com.example.demo.domain.Msg;
import com.example.demo.domain.User;
import com.sun.deploy.net.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class CommentsController {
    @Autowired
    IUserDao userMapper;
    @Autowired
    IBlogDao blogMapper;
    @Autowired
    ICommentDao commentMapper;

    @PostMapping("/addComment")
    public Msg addComment(Long blogId, Long userId,String content, Long parentId){
        User user = userMapper.findOne(userId);
        Blog blog = blogMapper.findOne(blogId);
        Comment comment = new Comment();
        if(parentId!=null){
            Comment one = commentMapper.findOne(parentId);
            comment.setParentComment(one);
        }
        comment.setBlog(blog);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setUser(user);
        commentMapper.save(comment);
        return Msg.success();
    }
    //显示所有评论
    @GetMapping("/showComments/{i}")
    public Msg showComments(@PathVariable int i,Long blogId){
        PageRequest pageable=new PageRequest(i,10,new Sort(Sort.Direction.DESC,"createTime"));
        Page<Comment> all = commentMapper.findByBlogIdAndParentCommentId(blogId,null,pageable);
        List<Comment> noNullAll = commentMapper.find(blogId);
        for (Comment c : all.getContent()) {
            c.setReplyCount(commentMapper.countByParentCommentId(c.getId()));
        }
        return Msg.success().add("list",all).add("noNullAll",noNullAll);
    }
}
