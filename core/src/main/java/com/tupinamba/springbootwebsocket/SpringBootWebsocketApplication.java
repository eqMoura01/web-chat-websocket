package com.tupinamba.springbootwebsocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tupinamba.springbootwebsocket.controller.UsuarioController;
import com.tupinamba.springbootwebsocket.model.Chat;
import com.tupinamba.springbootwebsocket.model.Usuario;
import com.tupinamba.springbootwebsocket.service.ChatService;

@SpringBootApplication
public class SpringBootWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebsocketApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UsuarioController usuarioController, ChatService chatService) {
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
        };
    }

}
