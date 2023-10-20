package com.example.demo.security;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.exceptions.PersonNotFoundException;
import com.example.demo.models.Person;
import com.example.demo.models.Role;
import com.example.demo.repositories.PeopleRepository;
import com.example.demo.repositories.RolesRepository;
import com.example.demo.repositories.TokenRepository;
import com.example.demo.token.Token;
import com.example.demo.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PeopleRepository peopleRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    public ResponseEntity<AuthResponseDTO> login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        Person person = peopleRepository.findByLogin(loginDTO.getLogin()).orElseThrow(() -> new PersonNotFoundException("Пользователь с таким id не найден"));
        saveUserToken(person, token);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    public ResponseEntity<String> register(RegisterDTO registerDTO) {
        if (peopleRepository.existsByLogin(registerDTO.getLogin())) {
            return new ResponseEntity<>("Пользователь с таким логином уже существует", HttpStatus.BAD_REQUEST);
        }

        Person person = new Person();
        person.setLogin(registerDTO.getLogin());
        person.setPassword(passwordEncoder.encode((registerDTO.getPassword())));
        person.setName(registerDTO.getName());
        person.setLastname(registerDTO.getLastname());
        person.setEmail(registerDTO.getEmail());

        Role roles = rolesRepository.findByName("GUEST").get();
        person.setRoles(Collections.singletonList(roles));

        peopleRepository.save(person);

        return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK);
    }

    private void saveUserToken(Person person, String jwtToken) {
        var token = Token.builder()
                .person(person)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

}
