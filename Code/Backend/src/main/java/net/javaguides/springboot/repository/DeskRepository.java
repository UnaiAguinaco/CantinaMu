package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Desk;

@Repository
public interface DeskRepository extends JpaRepository<Desk, Integer> {
}
