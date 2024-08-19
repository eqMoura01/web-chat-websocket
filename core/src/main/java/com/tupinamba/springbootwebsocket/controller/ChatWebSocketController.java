package com.tupinamba.springbootwebsocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tupinamba.springbootwebsocket.model.Mensagem;
import com.tupinamba.springbootwebsocket.service.MensagemService;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MensagemService mensagemService;

    // Registra o usuário em um chat específico
    @MessageMapping("/chat.register")
    public void register(@Payload Mensagem mensagem, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", mensagem.getUsuario().getUsername());
        System.out.println("Usuário registrado: " + mensagem.getUsuario().getUsername());
    }

    // Envia mensagem para um chat específico
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload Mensagem mensagem) {

        // A mensagem é enviada para um tópico específico, baseado no ID do chat
        messagingTemplate.convertAndSend("/topic/chat/" + mensagem.getChat().getId(), mensagem);

        System.out.println(mensagem.getUsuario().getUsername() + " enviou uma mensagem: " + mensagem.getConteudo());
        // Persistindo a mensagem no banco de dados
        mensagemService.save(mensagem);
    }

}
