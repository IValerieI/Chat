package com.example.demo.repositories;

import com.example.demo.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByLogin(String login);
    Boolean existsByLogin(String username);
}
