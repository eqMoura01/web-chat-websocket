package com.tupinamba.springbootwebsocket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tupinamba.springbootwebsocket.model.Chat;
import com.tupinamba.springbootwebsocket.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<Chat> save(@RequestBody Chat chat) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.save(chat));
    }

    @GetMapping
    public ResponseEntity<List<Chat>> findAll() {
        return ResponseEntity.ok(chatService.findAll());
    }

    @GetMapping("/userId1/{user1Id}/userId2/{user2Id}")
    public ResponseEntity<Chat> findByUsersIds(@PathVariable Long user1Id,@PathVariable Long user2Id) {
        return ResponseEntity.ok(chatService.findByUsersIds(user1Id, user2Id));
    }
}
