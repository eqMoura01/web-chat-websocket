package com.tupinamba.springbootwebsocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tupinamba.springbootwebsocket.model.Mensagem;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    public List<Mensagem> findByChatId(Long chatId);
}
