package com.application.chat.service;

import com.application.chat.model.Conversation;
import com.application.chat.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    public List<Conversation> getAllChatsByUsers(String chatId){
        return conversationRepository.findAllByChatRoomId(chatId);
    }
}
