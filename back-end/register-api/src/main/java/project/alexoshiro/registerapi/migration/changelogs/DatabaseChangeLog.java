package project.alexoshiro.registerapi.migration.changelogs;

import java.util.UUID;

import org.bson.Document;
import org.springframework.context.annotation.Profile;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Profile("dev")
@ChangeLog
public class DatabaseChangeLog {

	@ChangeSet(order = "001", id = "1580358097", author = "Alex")
	public void registerAdminUser(MongoDatabase db) {
		MongoCollection<Document> userCollection = db.getCollection("user");
		Document user = new Document("id", UUID.randomUUID().toString())
				.append("username", "mock")
				.append("password", "123")
				.append("email", "mock@example.com");
		userCollection.insertOne(user);
	}
}
