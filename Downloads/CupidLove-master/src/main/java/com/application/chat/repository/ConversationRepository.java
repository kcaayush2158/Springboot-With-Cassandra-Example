package com.application.chat.repository;

import com.application.chat.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository  extends JpaRepository<Conversation, Integer> {

    List<Conversation> findAllByChatRoomId(String chatroomId);


}
