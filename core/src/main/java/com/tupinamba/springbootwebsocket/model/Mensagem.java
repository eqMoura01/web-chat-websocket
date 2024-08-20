package com.tupinamba.springbootwebsocket.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "usuario_remetente_id")
    private Usuario remetente;

    @ManyToOne
    @JoinColumn(name = "usuario_destinatario_id")
    private Usuario destinatario;

    private String conteudo;

    private Timestamp dataEnvio;

    public Mensagem() {
    }

    public Mensagem(Long id, Chat chat, Usuario remetente, Usuario destinatario, String conteudo, Timestamp dataEnvio) {
        this.id = id;
        this.chat = chat;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.conteudo = conteudo;
        this.dataEnvio = dataEnvio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario usuario) {
        this.remetente = usuario;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Timestamp getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Timestamp dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

}
