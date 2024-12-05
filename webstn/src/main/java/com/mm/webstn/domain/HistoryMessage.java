package com.mm.webstn.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "history_message")
public class HistoryMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "message")
    private String message;

    @Column(name = "message_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> messageJson;

}