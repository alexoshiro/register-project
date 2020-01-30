package project.alexoshiro.registerapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import project.alexoshiro.registerapi.model.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

}
