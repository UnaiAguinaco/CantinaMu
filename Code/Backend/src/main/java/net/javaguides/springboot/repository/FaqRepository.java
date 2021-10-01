package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Faq;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer> {
}
