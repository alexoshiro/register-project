package project.alexoshiro.registerapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import project.alexoshiro.registerapi.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	public List<User> findByUsername(String username);
}
