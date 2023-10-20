package com.example.demo.controllers;

import com.example.demo.dto.PersonDTO;
import com.example.demo.services.impl.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    @Autowired
    public PeopleController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDTO>> showAll() {
        List<PersonDTO> listOfPeople = personService.findAll();
        return ResponseEntity.ok(listOfPeople);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> showById(@PathVariable("id") int id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @GetMapping("/person/{login}")
    public ResponseEntity<PersonDTO> showByLogin(@PathVariable("login") String login) {
        return ResponseEntity.ok(personService.getPersonByLogin(login));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO, @PathVariable("id") int personId) {
        PersonDTO response = personService.updatePerson(personDTO, personId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<PersonDTO> updatePassword(@RequestBody PersonDTO personDTO, @PathVariable("id") int personId) {
        PersonDTO response = personService.updatePassword(personDTO, personId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deletePerson(@PathVariable("id") int personId) {
        personService.deletePerson(personId);
        return new ResponseEntity<>("Person delete", HttpStatus.OK);
    }

}
