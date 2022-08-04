package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.PersonMapper;
import dev.kopfire.site.printer.core.model.PersonDTO;
import dev.kopfire.site.printer.db.entity.Person;
import dev.kopfire.site.printer.db.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationService {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public void register(PersonDTO person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(personMapper.map(person, Person.class));
    }
}
