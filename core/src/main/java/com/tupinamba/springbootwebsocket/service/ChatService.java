package com.tupinamba.springbootwebsocket.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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

    public Chat findById(Long id) {
        Optional<Chat> chat = chatRepository.findById(id);
        if(!chat.isPresent()) {
          throw new EntityNotFoundException("Chat com id " + id + " não encontrado");
        }

        return chat.get();
    }

    public Chat findByUsersIds(Long user1Id, Long user2Id) {
        return findById(chatRepository.findByUsersIds(user1Id, user2Id));
    }
}
