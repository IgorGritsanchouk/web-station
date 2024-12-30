package com.mm.webstn.controllers;

import com.mm.webstn.domain.Post;
import com.mm.webstn.repository.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mm.webstn.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private final Logger logger= LoggerFactory.getLogger(PostRestController.class);
    private final PostRepository postRepository;

    public PostRestController(PostRepository postRepository){
        this.postRepository= postRepository;
    }

    @GetMapping
    public String hello(){
        logger.info("calling hello");
        return "calling test end point";
    }
    @GetMapping("/all")
    public Iterable<Post> findAll(){
        logger.info("calling find all");
        return postRepository.findAll();
    }

//    @GetMapping("/{id}")
//    public Post findById(@PathVariable ("id") Post post){
//        return post;
//    }
    @GetMapping("/{id}")
    public Post findById(@PathVariable Long id) throws NotFoundException{
        logger.info("calling find by id: "+ id);
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found post with id :"+ id));
    }

}
