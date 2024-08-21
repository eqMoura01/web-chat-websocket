package com.tupinamba.springbootwebsocket.service;

import java.sql.Timestamp;
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

        chat.setDataHoraCriacao(Timestamp.valueOf(java.time.LocalDateTime.now()));

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

    public Chat findByUsersIds(Chat chat) {

        List<Long> userIds = new ArrayList<>();
        Long size = Long.valueOf(chat.getUsuarios().size());

        for (Usuario usuario : chat.getUsuarios()) {
            userIds.add(usuario.getId());
        }

        Long id = chatRepository.findByUsersIds(userIds, size);

        if (id == null) {
            chat = createNewChat(chat);
            return chat;
        }

        return findById(id);
    }

    public Chat createNewChat(Chat chat) {
        return save(chat);
    }
}
