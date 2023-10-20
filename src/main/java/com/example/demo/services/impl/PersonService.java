package com.example.demo.services.impl;

import com.example.demo.dto.PersonDTO;
import com.example.demo.exceptions.PersonNotFoundException;
import com.example.demo.models.Person;
import com.example.demo.repositories.PeopleRepository;
import com.example.demo.services.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PersonService implements IPersonService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    @Transactional
    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = mapToEntity(personDTO);
        Person newPerson = peopleRepository.save(person);
        return mapToDto(newPerson);
    }

    @Override
    public List<PersonDTO> findAll() {
        List<Person> listOfPeople = peopleRepository.findAll();
        List<PersonDTO> listOfPersonDTO = listOfPeople.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return listOfPersonDTO;
    }

    @Override
    public PersonDTO getPersonById(int id) {
        Person person = peopleRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Пользователь с таким id не найден"));
        return mapToDto(person);
    }

    @Override
    @Transactional
    public PersonDTO updatePerson(PersonDTO personDTO, int id) {
        Person person = peopleRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Пользователь с таким id не найден"));

        person.setLogin(personDTO.getLogin());
        person.setName(personDTO.getName());
        person.setLastname(personDTO.getLastname());
        person.setEmail(personDTO.getEmail());

        Person updatedPerson = peopleRepository.save(person);
        return mapToDto(updatedPerson);
    }

    @Override
    @Transactional
    public PersonDTO updatePassword(PersonDTO personDTO, int id) {
        Person person = peopleRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Пользователь с таким id не найден"));

        person.setPassword(personDTO.getPassword());

        Person updatedPerson = peopleRepository.save(person);
        return mapToDto(updatedPerson);
    }

    @Override
    @Transactional
    public void deletePerson(int id) {
        Person person = peopleRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Пользователь с таким id не найден"));
        peopleRepository.delete(person);
    }

    @Override
    public PersonDTO getPersonByLogin(String login) {
        Person person = peopleRepository.findByLogin(login).orElseThrow(() -> new PersonNotFoundException("Пользователь с таким логином не найден"));
        return mapToDto(person);
    }

    private PersonDTO mapToDto(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setLogin(person.getLogin());
        personDTO.setPassword(person.getPassword());
        personDTO.setName(person.getName());
        personDTO.setLastname(person.getLastname());
        personDTO.setEmail(person.getEmail());
        return personDTO;
    }

    private Person mapToEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setLogin(personDTO.getLogin());
        person.setPassword(personDTO.getPassword());
        person.setName(personDTO.getName());
        person.setLastname(personDTO.getLastname());
        person.setEmail(personDTO.getEmail());
        return person;
    }

}
