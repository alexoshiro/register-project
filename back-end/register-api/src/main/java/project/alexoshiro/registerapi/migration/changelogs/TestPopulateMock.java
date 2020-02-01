package project.alexoshiro.registerapi.migration.changelogs;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Random;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.javafaker.Faker;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import project.alexoshiro.registerapi.enums.GenderEnum;
import project.alexoshiro.registerapi.migration.CpfGenerator;

@Profile("test")
@ChangeLog
public class TestPopulateMock {

	@ChangeSet(order = "003", id = "1580529304", author = "Alex")
	public void massInsertPeopleInDatabse(MongoDatabase db) {
		MongoCollection<Document> peopleCollection = db.getCollection("person");

		Faker faker = new Faker(new Locale("pt", "BR"));

		GenderEnum[] genders = GenderEnum.values();
		Random generator = new Random();

		Document document = new Document("_id", new ObjectId("5e32fbb40d71210d2c4c2ab5"))
				.append("name", "Teste")
				.append("gender", genders[generator.nextInt(genders.length)].toString())
				.append("email", faker.internet().emailAddress())
				.append("birthDate", faker.date().birthday().toInstant().atZone(ZoneOffset.UTC).toLocalDate())
				.append("nationality", faker.nation().nationality())
				.append("citizenship", "brasileiro")
				.append("cpf", CpfGenerator.generateCPF())
				.append("creationDate", LocalDateTime.now(ZoneOffset.UTC))
				.append("updatedDate", LocalDateTime.now(ZoneOffset.UTC));

		peopleCollection.insertOne(document);
	}
}
