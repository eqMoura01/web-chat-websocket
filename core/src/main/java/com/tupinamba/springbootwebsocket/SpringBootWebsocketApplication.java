package com.tupinamba.springbootwebsocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tupinamba.springbootwebsocket.controller.UsuarioController;
import com.tupinamba.springbootwebsocket.model.Chat;
import com.tupinamba.springbootwebsocket.model.Mensagem;
import com.tupinamba.springbootwebsocket.model.Usuario;
import com.tupinamba.springbootwebsocket.service.ChatService;
import com.tupinamba.springbootwebsocket.service.MensagemService;

@SpringBootApplication
public class SpringBootWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebsocketApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UsuarioController usuarioController, ChatService chatService,
            MensagemService mensagemService) {
        return args -> {

            // Create some users
            Usuario u1 = new Usuario(null, "eqmoura", "teste");
            usuarioController.save(u1);
            Usuario u2 = new Usuario(null, "eqwillian", "teste");
            usuarioController.save(u2);

            // Create a chat with the users
            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(u1);
            usuarios.add(u2);
            Chat c1 = new Chat(null, usuarios);
            chatService.save(c1);

            Mensagem m1 = new Mensagem(null, c1, u1, u2, "Olá, tudo bem?", null);
            Mensagem m2 = new Mensagem(null, c1, u2, u1, "Oi, tudo sim e você?", null);
            Mensagem m3 = new Mensagem(null, c1, u1, u2, "Tudo ótimo!", null);

            mensagemService.save(m1);
            mensagemService.save(m2);
            mensagemService.save(m3);
        };
    }

}
