package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.covid19_infections;

@Repository
public interface Covid19InfectionsRepository extends JpaRepository<covid19_infections, Integer> {
}
