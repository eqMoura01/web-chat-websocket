package com.tupinamba.springbootwebsocket.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tupinamba.springbootwebsocket.model.Mensagem;
import com.tupinamba.springbootwebsocket.repository.MensagemRepository;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Mensagem save(Mensagem mensagem) {
        System.out.println("Mensagem registrada: " + mensagem.getRemetente().getUsername());
        mensagem.setDataEnvio(Timestamp.valueOf(java.time.LocalDateTime.now()));
        mensagem.setRemetente(usuarioService.findById(mensagem.getRemetente().getId()));
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
