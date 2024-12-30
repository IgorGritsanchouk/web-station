package com.mm.webstn.config;

import com.mm.webstn.domain.Post;
import com.mm.webstn.domain.User;
import com.mm.webstn.repository.PostRepository;
import com.mm.webstn.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    CommandLineRunner commandLineRunner(PostRepository postRepository, UserRepository userRepository, PasswordEncoder encoder){
        return args -> {
            userRepository.save(new User("user",encoder.encode("password"),"ROLE_USER"));
            userRepository.save(new User("admin",encoder.encode("password"),"ROLE_USER,ROLE_ADMIN"));
            postRepository.save(new Post("snr msg1", "container 123 arrived", "user11"));
            postRepository.save(new Post("snr msg2", "container 2345 arrived", "user11"));
            postRepository.save(new Post("snr msg3", "container 2345 arrived", "user11"));
            postRepository.save(new Post("inl msg1", "registration num 2764", "usr221"));
        };
    }
}

//Post(String title, String text, String author){