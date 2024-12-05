package com.mm.webstn.services;

import com.mm.webstn.domain.HistoryMessage;
import java.util.List;

public interface MessageConsumerService {

    List<HistoryMessage> getAllHistoryMessages();

    HistoryMessage saveHistoryMessage(HistoryMessage historyMessage);

//    HistoryMessage getHistorryMessageById(Long id);

//    HistoryMessage updateHistoryMessage(HistoryMessage historyMessage);

//    void deleteHistoryMessageById(Long id);
}
