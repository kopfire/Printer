package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.model.PersonDTO;
import dev.kopfire.site.printer.db.entity.Person;
import dev.kopfire.site.printer.db.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private ModelMapper modelMapper = new ModelMapper();

    private final PersonRepository personRepository;

    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> personNew = personRepository.findByUsername(username);

        if (personNew.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return modelMapper.map(personNew.get(), PersonDTO.class);
    }

    public boolean checkUserByUsername(String username) {
        Optional<Person> personNew = personRepository.findByUsername(username);
        return personNew.isEmpty();
    }

    public void changePerson(int id, String role) {

        Person person = personRepository.findById(id).get();

        person.setRole(role);

        personRepository.save(person);
    }

    public void deletePerson(Integer id) {
        personRepository.delete(personRepository.getReferenceById(id));
    }

    public List<PersonDTO> findAll() {
        return modelMapper.map(personRepository.findAll(), new TypeToken<List<PersonDTO>>() {}.getType());
    }
}
