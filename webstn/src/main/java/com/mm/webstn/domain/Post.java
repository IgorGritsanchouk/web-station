package com.mm.webstn.domain;

import com.mm.shared.domain.Message;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Post extends Message {

    @Id @GeneratedValue
    private Long Id;
    private String title;
    private String text;
    private String author;
    private LocalDateTime sendOn;
    private LocalDateTime updateOn;

    public Post(){
        super("default");
    }
    public Post(String text){
        super(text);
    }
    public Post(String title, String text, String author){
        super(text);
        this.title= title;
        this.text= text;
        this.author= author;
        this.sendOn= LocalDateTime.now();
        //this.updateOn= LocalDateTime.now();
    }

}
