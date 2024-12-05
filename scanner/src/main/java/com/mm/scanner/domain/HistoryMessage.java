package com.mm.scanner.domain;

//import jakarta.persistence.*;
//import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
//@Entity
//@Table(name = "history_message")
public class HistoryMessage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    private Long id;

//    @Column(name = "date")
    private Instant date;

//    @Size(max = 255)
//    @Column(name = "message")
    private String message;

}