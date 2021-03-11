package com.example.demo.controller;

import com.example.demo.dao.IBlogDao;
import com.example.demo.dao.ITagDao;
import com.example.demo.dao.ITypeDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.domain.*;
import com.example.demo.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class BlogPublishController {
    @Autowired
    ITagDao tagMapper;
    @Autowired
    IUserDao userMapper;
    @Autowired
    ITypeDao typeMapper;
    @Autowired
    IBlogDao blogMapper;

    @PostMapping("/addBlog")
    public Msg addBlog(Blog blog, HttpSession session,String tagIds,@RequestParam("file") MultipartFile file) throws IOException {

        blog.setViews(0);
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
//        if(blog.getType().getId()==null){
//            return Msg.error("分类为空");
//        }
//        if(tagIds==null){
//            return Msg.error("标签为空");
//        }

//        file.transferTo(new );
        blog.setType(typeMapper.findOne(blog.getType().getId()));
        List<Long> tagIdList=new ArrayList<>();
        for (String i:tagIds.split(",")){
            tagIdList.add(Long.parseLong(i));
        }
        blog.setTags(tagMapper.findAll(tagIdList));
        blog.setUser(userMapper.findByUsername(session.getAttribute("username").toString()));
        /**
         * 文件上传
         */
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_HH_mm_ss");
        String time = sdf.format(new Date());
        String fileName=blog.getTitle()+blog.getUser().getUsername()+time+file.getOriginalFilename();
        String s = FileUploadUtils.FileUpload(file, "/images/blog/", fileName);
        blog.setFirstPicture(s);
        blogMapper.save(blog);
        return Msg.success();
    }
    @GetMapping("/showBlog/{i}")
    public Msg showBlog(@PathVariable int i,String username){
//        Specification<Blog> specification=new Specification<Blog>() {
//            @Override
//            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Path<Object> path = root.get("user.username");
//                Predicate equal = criteriaBuilder.equal(path, username);
//                return equal;
//            }
//        };
        Pageable page=new PageRequest(i,7);
        Page<Blog> all = blogMapper.findByUserUsername(username, page);
        return Msg.success().add("list",all);
    }
    @DeleteMapping("/delBlog/{i}")
    public Msg delBlog(@PathVariable Long i){
        blogMapper.delete(i);
        return Msg.success();
    }
    @GetMapping("/blogSearch/{i}")
    public Msg blogSearch(@PathVariable int i,Blog blog,String username) {
        Specification<Blog> specification = new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listAnd = new ArrayList<>();
                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    Predicate title = criteriaBuilder.like(root.get("title"), "%"+ blog.getTitle()+ "%");
                    listAnd.add(title);
                }
                if (blog.getType().getId() != null) {
                    Predicate like = criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getType().getId());
                    listAnd.add(like);
                }
                Predicate user = criteriaBuilder.equal(root.<User>get("user").get("username"), username);
                listAnd.add(user);
                Predicate recommened = criteriaBuilder.equal(root.<Boolean>get("recommened"), blog.isRecommened());
                listAnd.add(recommened);
                return criteriaBuilder.and(listAnd.toArray(new Predicate[listAnd.size()]));
            }
        };
        Pageable page=new PageRequest(i,7);
        Page<Blog> all = blogMapper.findAll(specification, page);
        return Msg.success().add("list",all);
    }
    @GetMapping("/showContent/{id}")
    public Msg showContent(@PathVariable Long id){
        Blog one = blogMapper.findOne(id);
        return Msg.success().add("list",one);
    }

}
