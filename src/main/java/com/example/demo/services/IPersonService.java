package com.example.demo.services;

import com.example.demo.dto.PersonDTO;

import java.util.List;

public interface IPersonService {

    PersonDTO createPerson(PersonDTO personDTO);
    List<PersonDTO> findAll();
    PersonDTO getPersonById(int id);
    PersonDTO getPersonByLogin(String login);
    PersonDTO updatePerson(PersonDTO personDTO, int id);
    PersonDTO updatePassword(PersonDTO personDTO, int id);
    void deletePerson(int id);

}
