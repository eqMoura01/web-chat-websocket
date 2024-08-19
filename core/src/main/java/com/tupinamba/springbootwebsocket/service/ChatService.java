package com.tupinamba.springbootwebsocket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tupinamba.springbootwebsocket.model.Chat;
import com.tupinamba.springbootwebsocket.repository.ChatRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }
}
