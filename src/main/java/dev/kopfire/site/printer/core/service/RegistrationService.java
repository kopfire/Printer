package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.model.PersonDTO;
import dev.kopfire.site.printer.db.entity.Person;
import dev.kopfire.site.printer.db.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationService {

    private ModelMapper modelMapper = new ModelMapper();

    public String code_reg = "test";

    private final PasswordEncoder passwordEncoder;

    private final PersonRepository personRepository;

    public RegistrationService(PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    @Transactional
    public void register(PersonDTO person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(modelMapper.map(person, Person.class));
    }
}
