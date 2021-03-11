package com.example.demo.controller;

import com.example.demo.dao.IBlogDao;
import com.example.demo.dao.ITagDao;
import com.example.demo.dao.ITypeDao;
import com.example.demo.domain.Blog;
import com.example.demo.domain.Tag;
import com.example.demo.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.*;
import java.util.*;


@Controller
@RequestMapping("/index")
public class IndexBlogController {


    @Autowired
    IBlogDao blogMapper;
    @Autowired
    ITypeDao typeMapper;
    @Autowired
    ITagDao tagMapper;
    @RequestMapping("/aboutMe")
    public String aboutMe(){
        return "aboutMe";
    }
    @RequestMapping("/Archive")
    public String Archive(Model model){
        List<Blog> id = blogMapper.findAll(new Sort(Sort.Direction.DESC, "id"));
        Set<Integer> year=new HashSet<>();
        Set<Integer> month=new HashSet<>();
        for (Blog blog:id){
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(blog.getUpdateTime());
            year.add(calendar.get(Calendar.YEAR));
            month.add(calendar.get(Calendar.MONTH)+1);
        }
        List<Blog> byUpdateTimeLike=blogMapper.findAll(new Sort(Sort.Direction.ASC,"updateTime"));
        HashMap<String, ArrayList<Blog>> map = new HashMap<>();
        for(Blog blog:byUpdateTimeLike){
            if(map.containsKey(blog.getUpdateTime().toString().trim().substring(0,4))){
                map.get(blog.getUpdateTime().toString().trim().substring(0,4)).add(blog);
            }else {
                map.put(blog.getUpdateTime().toString().trim().substring(0,4),new ArrayList<>());
                map.get(blog.getUpdateTime().toString().trim().substring(0,4)).add(blog);
            }
        }
        model.addAttribute("count",blogMapper.count());
        model.addAttribute("list",map);
        model.addAttribute("year",year);
        return "Archive";
    }
    @RequestMapping("/ArchiveBlog")
    public String ArchiveBlog(Model model,String text){
        String text1=(text.substring(0,4)+"%").trim();
        HashMap<String, ArrayList<Blog>> map = new HashMap<>();
        List<Blog> byUpdateTimeLike=new ArrayList<>();
        if("全部".equals(text)){
            byUpdateTimeLike=blogMapper.findAll(new Sort(Sort.Direction.ASC,"updateTime"));
        }else {
            byUpdateTimeLike = blogMapper.findAll(new Specification<Blog>() {
                @Override
                public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<String> updateTime = root.<String>get("updateTime");
                    Predicate like = criteriaBuilder.like(updateTime.as(String.class),text1);
                    return like;
                }
            });
        }
        for(Blog blog:byUpdateTimeLike){
            int b=0;
            if(map.containsKey(blog.getUpdateTime().toString().trim().substring(0,4))){
                map.get(blog.getUpdateTime().toString().trim().substring(0,4)).add(blog);
            }else {
                map.put(blog.getUpdateTime().toString().trim().substring(0,4),new ArrayList<>());
                map.get(blog.getUpdateTime().toString().trim().substring(0,4)).add(blog);
            }
        }
        model.addAttribute("list",map);
        return "Archive :: blog";
    }


    @RequestMapping("/BlogDetails")
    public String BlogDetails(){
        return "IndexBlogDetails";
    }
    @RequestMapping("/tag")
    public String tag(Model model,@RequestParam(value = "i",defaultValue = "0") int i,@PageableDefault
                               (size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        List<Tag> all = tagMapper.findAll();
        int j=0;
        for(Tag tag:all){
            if(j==i){
                Page<Blog> blogs = blogMapper.findAll(new Specification<Blog>() {
                    @Override
                    public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        Join join = root.join("tags");
                        Predicate id = criteriaBuilder.equal(join.get("id"), tag.getId());
                        return id;
                    }
                }, pageable);
                model.addAttribute("blogs",blogs);
            }
            j++;
        }
        model.addAttribute("all",all);
        model.addAttribute("i",i);
        model.addAttribute("count",tagMapper.count());
        return "tag";
    }


    @RequestMapping("/type")
    public String type(Model model,@RequestParam(name = "i",defaultValue = "0") int i,
                       @PageableDefault
                               (size = 5,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        List<Type> all = typeMapper.findAll();
        int j=0;
        for (Type type:all) {
            type.setTypeCount(blogMapper.countByTypeId(type.getId()));
            if(j==i) {
                model.addAttribute("blogs",blogMapper.findByTypeId(type.getId(),pageable));
                model.addAttribute("i",i);
            }
            j++;
        }
        model.addAttribute("all",all);
        model.addAttribute("count",typeMapper.count());
        return "type";
    }
}
