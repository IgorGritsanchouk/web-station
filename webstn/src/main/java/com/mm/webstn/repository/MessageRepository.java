package com.mm.webstn.repository;

import com.mm.webstn.domain.HistoryMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<HistoryMessage, Long> {
}