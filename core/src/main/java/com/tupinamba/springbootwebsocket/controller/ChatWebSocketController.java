package com.tupinamba.springbootwebsocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.tupinamba.springbootwebsocket.model.Mensagem;
import com.tupinamba.springbootwebsocket.model.Usuario;
import com.tupinamba.springbootwebsocket.service.MensagemService;
import com.tupinamba.springbootwebsocket.service.UsuarioService;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MensagemService mensagemService;

    @Autowired
    UsuarioService usuarioService;

    // Registra o usuário em um chat específico
    @MessageMapping("/chat.register")
    public void register(@Payload Mensagem mensagem, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("id_usuario", mensagem.getRemetente().getId());
        System.out.println("Usuário registrado: " + mensagem.getRemetente().getUsername());
    }

    // Envia mensagem para um chat específico
    @MessageMapping("/chat.send")
    public Mensagem sendMessage(@Payload Mensagem mensagem) {

        for (Usuario usuario : mensagem.getChat().getUsuarios()) {
            if (usuario.getId() != mensagem.getRemetente().getId()) {
                messagingTemplate.convertAndSend("/topic/usuario/" + usuario.getId(), mensagem);
                ;
            }
        }

        System.out.println(mensagem.getRemetente().getUsername() + " enviou uma mensagem: " + mensagem.getConteudo());

        // Persistindo a mensagem no banco de dados
        return mensagemService.save(mensagem);
    }

}
