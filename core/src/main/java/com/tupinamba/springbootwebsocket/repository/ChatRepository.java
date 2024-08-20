package com.tupinamba.springbootwebsocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tupinamba.springbootwebsocket.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c.id " +
           "FROM Chat c " +
           "JOIN c.usuarios u1 " +
           "JOIN c.usuarios u2 " +
           "WHERE u1.id = :id1 " +
           "AND u2.id = :id2 " +
           "GROUP BY c.id " +
           "HAVING COUNT(DISTINCT u1.id) = 1 " +
           "AND COUNT(DISTINCT u2.id) = 1")
    Long findByUsersIds(@Param("id1") Long usuarioId1, @Param("id2") Long usuarioId2);
}
