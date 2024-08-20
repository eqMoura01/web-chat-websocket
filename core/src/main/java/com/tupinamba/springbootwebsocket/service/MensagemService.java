package com.tupinamba.springbootwebsocket.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tupinamba.springbootwebsocket.model.Mensagem;
import com.tupinamba.springbootwebsocket.repository.MensagemRepository;
import java.sql.Timestamp;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    public Mensagem save(Mensagem mensagem) {
        System.out.println("Mensagem registrada: " + mensagem.getUsuario().getUsername());
        mensagem.setDataEnvio(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return mensagemRepository.save(mensagem);
    }

    public List<Mensagem> findAll() {
        return mensagemRepository.findAll();
    }

    public List<Mensagem> findByChatId(Long chatId) {
        return mensagemRepository.findByChatId(chatId);
    }

    public Mensagem findById(Long id) {

        Optional<Mensagem> mensagem = mensagemRepository.findById(id);

        if (!mensagem.isPresent()) {
            throw new EntityNotFoundException("Mensagem com o id " + id + " não encontrada");
        }

        return mensagemRepository.findById(id).orElse(null);
    }
}
