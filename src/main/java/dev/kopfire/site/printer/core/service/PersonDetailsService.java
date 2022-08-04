package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.PersonMapper;
import dev.kopfire.site.printer.core.model.PersonDTO;
import dev.kopfire.site.printer.db.entity.Person;
import dev.kopfire.site.printer.db.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    public PersonDetailsService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> personNew = personRepository.findByUsername(username);

        if (personNew.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return personMapper.map(personNew.get(), PersonDTO.class);
    }

    public boolean checkUserByUsername(String username){
        Optional<Person> personNew = personRepository.findByUsername(username);
        return personNew.isEmpty();
    }
}
