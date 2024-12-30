package com.mm.webstn.repository;

import com.mm.webstn.domain.Post;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}

//public interface PostRepository extends JpaRepository<Post, Long> {
//}

