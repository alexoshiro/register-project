package project.alexoshiro.registerapi.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	private UUID id;
	private String username;
	private String password;
	private String email;

}
