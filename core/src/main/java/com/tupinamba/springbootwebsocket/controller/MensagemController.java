package com.tupinamba.springbootwebsocket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tupinamba.springbootwebsocket.model.Mensagem;
import com.tupinamba.springbootwebsocket.service.MensagemService;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {
    

    @Autowired
    private MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<Mensagem> save(@RequestBody Mensagem mensagem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagemService.save(mensagem));
    }

    @GetMapping
    public ResponseEntity<List<Mensagem>> findAll() {
        return ResponseEntity.ok(mensagemService.findAll());
    }
}
