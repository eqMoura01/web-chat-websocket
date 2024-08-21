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
           "JOIN c.usuarios u " +
           "WHERE u.id IN (:id1, :id2) " +
           "GROUP BY c.id " +
           "HAVING COUNT(DISTINCT u.id) = 2 " + 
           "AND COUNT(u.id) = 2 " +
           "AND SUM(CASE WHEN u.id = :id1 THEN 1 ELSE 0 END) = 1 " +
           "AND SUM(CASE WHEN u.id = :id2 THEN 1 ELSE 0 END) = 1")
    Long findByUsersIds(@Param("id1") Long usuarioId1, @Param("id2") Long usuarioId2);
}
