package com.java.reto.repository;

import com.java.reto.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	boolean existsByIdentification(String identification);
}