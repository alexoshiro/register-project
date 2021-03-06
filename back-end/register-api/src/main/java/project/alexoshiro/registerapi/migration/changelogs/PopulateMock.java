package project.alexoshiro.registerapi.migration.changelogs;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.bson.Document;
import org.springframework.context.annotation.Profile;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.javafaker.Faker;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import project.alexoshiro.registerapi.enums.GenderEnum;
import project.alexoshiro.registerapi.migration.CpfGenerator;

@Profile({"dev", "test"})
@ChangeLog
public class PopulateMock {

	@ChangeSet(order = "002", id = "1580409846", author = "Alex")
	public void massInsertPeopleInDatabse(MongoDatabase db) {
		MongoCollection<Document> peopleCollection = db.getCollection("person");
		List<Document> people = new ArrayList<>();
		Faker faker = new Faker(new Locale("pt", "BR"));

		GenderEnum[] genders = GenderEnum.values();
		Random generator = new Random();

		for (int i = 0; i < 1000; i++) {
			people.add(new Document("name", faker.name().fullName())
					.append("gender", genders[generator.nextInt(genders.length)].toString())
					.append("email", faker.internet().emailAddress())
					.append("birthDate", faker.date().birthday().toInstant().atZone(ZoneOffset.UTC).toLocalDate())
					.append("nationality", faker.nation().nationality())
					.append("citizenship", "brasileiro")
					.append("cpf", CpfGenerator.generateCPF())
					.append("creationDate", LocalDateTime.now(ZoneOffset.UTC))
					.append("updatedDate", LocalDateTime.now(ZoneOffset.UTC)));
		}
		peopleCollection.insertMany(people);
	}
}
