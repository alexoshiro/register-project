package project.alexoshiro.registerapi.migration.changelogs;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.bson.Document;
import org.springframework.context.annotation.Profile;

import com.github.javafaker.Faker;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import project.alexoshiro.registerapi.enums.GenderEnum;
import project.alexoshiro.registerapi.migration.CpfGenerator;

@Profile("dev")
@ChangeLog
public class DatabaseChangeLog {

	@ChangeSet(order = "001", id = "1580358097", author = "Alex")
	public void registerAdminUser(MongoDatabase db) {
		MongoCollection<Document> userCollection = db.getCollection("user");
		Document user = new Document("username", "mock")
				.append("password", "$2y$10$43Jnd84SAGyS5Cveyz5USO4AxjuBS0CSUQEfX3WepbBEiJzSW6lkO")
				.append("email", "mock@example.com");
		userCollection.insertOne(user);
	}

	@ChangeSet(order = "002", id = "1580409846", author = "Alex")
	public void massInsertPeopleInDatabse(MongoDatabase db) {
		MongoCollection<Document> peopleCollection = db.getCollection("person");
		List<Document> people = new ArrayList<>();
		Faker faker = new Faker(new Locale("pt", "BR"));

		GenderEnum[] genders = GenderEnum.values();
		Random generator = new Random();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		for (int i = 0; i < 1000; i++) {
			people.add(new Document("name", faker.name().fullName())
					.append("gender", genders[generator.nextInt(genders.length)].toString())
					.append("email", faker.internet().emailAddress())
					.append("birth_date", sdf.format(faker.date().birthday()))
					.append("nationality", faker.nation().nationality())
					.append("citizenship", "brasileiro")
					.append("cpf", CpfGenerator.generateCPF())
					.append("creation_date", LocalDateTime.now(ZoneOffset.UTC).format(formatter))
					.append("updated_date", LocalDateTime.now(ZoneOffset.UTC).format(formatter)));
		}
		peopleCollection.insertMany(people);
	}
}
