package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
