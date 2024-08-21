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
       "WHERE u.id IN (:userIds) " +
       "GROUP BY c.id " +
       "HAVING COUNT(DISTINCT u.id) = :size " +
       "AND COUNT(u.id) = :size " +
       "AND SUM(CASE WHEN u.id IN (:userIds) THEN 1 ELSE 0 END) = :size")
    Long findByUsersIds(@Param("userIds") List<Long> usersIds, @Param("size") Long size);
}
