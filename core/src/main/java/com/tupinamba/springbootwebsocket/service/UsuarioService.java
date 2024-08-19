package com.tupinamba.springbootwebsocket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tupinamba.springbootwebsocket.model.Usuario;
import com.tupinamba.springbootwebsocket.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario login(Usuario usuario) {
        Usuario usuarioEncontrado = usuarioRepository.findByUsername(usuario.getUsername());

        if (!usuario.getPassword().equals(usuarioEncontrado.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        return usuarioEncontrado;
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }
}
