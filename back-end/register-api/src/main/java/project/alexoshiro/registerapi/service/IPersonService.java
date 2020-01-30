package project.alexoshiro.registerapi.service;

import java.util.List;
import java.util.Optional;

import project.alexoshiro.registerapi.model.Person;

public interface IPersonService {

	List<Person> getPeople();

	void savePerson(Person person);

	Optional<Person> updatePerson(String id, Person person);

	Optional<Person> getPersonById(String id);

	Optional<Person> deletePersonById(String id);
}
