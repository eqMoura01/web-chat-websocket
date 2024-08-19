package com.tupinamba.springbootwebsocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tupinamba.springbootwebsocket.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    
}
