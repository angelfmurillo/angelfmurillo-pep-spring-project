package com.example.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Message;

/**
 * MessageRepository provides data access methods for the Message entity using Spring Data JPA.
 * It extends JpaRepository to support basic CRUD operations and includes a custom method 
 * to retrieve all messages posted by a specific user based on their account ID.
 */

public interface MessageRepository extends JpaRepository<Message, Integer> {
   public abstract List<Message> findByPostedBy(Integer postedBy);
}
