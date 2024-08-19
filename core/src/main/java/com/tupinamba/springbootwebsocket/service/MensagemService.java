package com.tupinamba.springbootwebsocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tupinamba.springbootwebsocket.model.Mensagem;
import com.tupinamba.springbootwebsocket.repository.MensagemRepository;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    public Mensagem save(Mensagem mensagem) {
        System.out.println("Mensagem registrada: " + mensagem.getUsuario().getUsername());
        return mensagemRepository.save(mensagem);
    }
}
