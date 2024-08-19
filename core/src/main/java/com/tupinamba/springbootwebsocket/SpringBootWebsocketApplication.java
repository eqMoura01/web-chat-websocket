package com.tupinamba.springbootwebsocket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tupinamba.springbootwebsocket.controller.UsuarioController;
import com.tupinamba.springbootwebsocket.model.Usuario;

@SpringBootApplication
public class SpringBootWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebsocketApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UsuarioController usuarioController) {
        return args -> {

            Usuario u1 = new Usuario(null, "eqmoura", "teste");
            usuarioController.save(u1);

            Usuario u2 = new Usuario(null, "eqwillian", "teste");
            usuarioController.save(u2);
        };
    }

}
