package com.tupinamba.springbootwebsocket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tupinamba.springbootwebsocket.model.ChatMessage;
import com.tupinamba.springbootwebsocket.repository.ChatMessageRepository;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        System.out.println("Usuário registrado: " + chatMessage.getSender());
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findAll() {
        return chatMessageRepository.findAll();
    }
}
