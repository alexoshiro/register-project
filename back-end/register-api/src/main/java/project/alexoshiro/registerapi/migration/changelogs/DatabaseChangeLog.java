package project.alexoshiro.registerapi.migration.changelogs;

import org.bson.Document;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@ChangeLog
public class DatabaseChangeLog {

	@ChangeSet(order = "001", id = "1580358097", author = "Alex")
	public void registerAdminUser(MongoDatabase db) {
		MongoCollection<Document> userCollection = db.getCollection("user");
		Document user = new Document("username", "admin")
				.append("password", "$2y$10$43Jnd84SAGyS5Cveyz5USO4AxjuBS0CSUQEfX3WepbBEiJzSW6lkO")
				.append("email", "mock@example.com");
		userCollection.insertOne(user);
	}

}
