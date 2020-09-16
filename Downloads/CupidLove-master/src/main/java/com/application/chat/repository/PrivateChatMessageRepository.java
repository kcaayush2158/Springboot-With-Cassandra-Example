package com.application.chat.repository;

import com.application.chat.model.PrivateChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatMessageRepository  extends JpaRepository<PrivateChatMessage,Integer> {
}
