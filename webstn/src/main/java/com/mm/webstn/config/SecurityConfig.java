package com.mm.webstn.config;

import com.mm.webstn.services.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService){
        this.jpaUserDetailsService= jpaUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        //.requestMatchers("/api/posts/**").permitAll()
                        .requestMatchers("/api/posts/all").permitAll()
                        //.mvcMatchers("/api/posts").permitAll()
                        .anyRequest().authenticated())
                .userDetailsService(jpaUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                //.httpBasic(Customizer.withDefaults())
                //.formLogin(Customizer.withDefaults())
                .formLogin(withDefaults())
                .build();
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
//                .authorizeRequests(auth -> auth
//                        .antMatchers("/h2-console/**").permitAll()
//                        .mvcMatchers("/api/posts/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .userDetailsService(myUserDetailsService)
//                .headers(headers -> headers.frameOptions().sameOrigin())
//                .httpBasic(withDefaults())
//                .build();
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
