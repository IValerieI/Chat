package com.example.demo.controllers;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.models.Person;
import com.example.demo.models.Role;
import com.example.demo.repositories.PeopleRepository;
import com.example.demo.repositories.RolesRepository;
import com.example.demo.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private PeopleRepository peopleRepository;
    private RolesRepository rolesRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PeopleRepository peopleRepository,
                          RolesRepository rolesRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.peopleRepository = peopleRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
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

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }


}
