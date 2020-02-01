package project.alexoshiro.registerapi.service;

import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;

import project.alexoshiro.registerapi.dto.PeoplePaginationResultDTO;
import project.alexoshiro.registerapi.model.Person;

public interface IPersonService {

	PeoplePaginationResultDTO getPeople(String baseUrl, Integer page, Integer pageItems);

	Optional<Person> savePerson(Person person) throws DuplicateKeyException;

	Optional<Person> updatePerson(String id, Person person);

	Optional<Person> getPersonById(String id);

	Optional<Person> deletePersonById(String id);
}
