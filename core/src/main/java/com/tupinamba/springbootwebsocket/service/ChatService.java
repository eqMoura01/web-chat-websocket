package com.tupinamba.springbootwebsocket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tupinamba.springbootwebsocket.model.Chat;
import com.tupinamba.springbootwebsocket.model.Usuario;
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
        if (!chat.isPresent()) {
            throw new EntityNotFoundException("Chat com id " + id + " não encontrado");
        }

        return chat.get();
    }

    public Chat findByUsersIds(Long user1Id, Long user2Id) {
        Long id = chatRepository.findByUsersIds(user1Id, user2Id);

        Chat chat = null;
        if (id == null) {
            chat = createNewChat(user1Id, user2Id);
            return chat;
        }

        return findById(id);
    }

    public Chat createNewChat(Long user1Id, Long user2Id) {

        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(new Usuario(user1Id, null, null));
        usuarios.add(new Usuario(user2Id, null, null));
        return save(new Chat(null, usuarios));
    }
}
