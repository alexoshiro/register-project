package project.alexoshiro.registerapi.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import project.alexoshiro.registerapi.model.Person;
import project.alexoshiro.registerapi.repository.PersonRepository;
import project.alexoshiro.registerapi.service.IPersonService;
import project.alexoshiro.registerapi.util.CopyUtils;

@Service
public class PersonService implements IPersonService {

	@Autowired
	private PersonRepository personRepository;

	public List<Person> getPeople() {
		return personRepository.findAll();
	}

	public Optional<Person> savePerson(Person person) throws DuplicateKeyException {
		LocalDateTime date = LocalDateTime.now();
		person.setCreationDate(date);
		person.setUpdatedDate(date);
		Person savedPerson = personRepository.save(person);
		
		return Optional.of(savedPerson);
	}

	public Optional<Person> updatePerson(String id, Person person) {
		Optional<Person> savedPerson = getPersonById(id);
		if (savedPerson.isPresent()) {
			Person toSave = savedPerson.get();

			person.setCreationDate(null);
			CopyUtils.copyNonNullProperties(person, toSave);

			toSave.setUpdatedDate(LocalDateTime.now());

			Person updatedPerson = personRepository.save(toSave);
			return Optional.of(updatedPerson);
		}
		return Optional.empty();
	}

	public Optional<Person> getPersonById(String id) {
		return personRepository.findById(id);
	}

	public Optional<Person> deletePersonById(String id) {
		personRepository.deleteById(id);
		return getPersonById(id);
	}
}
