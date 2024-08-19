package com.tupinamba.springbootwebsocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tupinamba.springbootwebsocket.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    

    public Usuario findByUsername(String username);
}
