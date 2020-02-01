package project.alexoshiro.registerapi.migration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Component
public class MigrationSetup {

	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	@Value("${spring.data.mongodb.database}")
	private String database;

	@Bean
	@Autowired
	public SpringMongock mongock(ApplicationContext springContext, MongoClient mongoClient, Environment environment) {
		MongoClient mongoclient = new MongoClient(new MongoClientURI(mongoUri));

		return new SpringMongockBuilder(mongoclient, database, "project.alexoshiro.registerapi.migration.changelogs")
				.setSpringEnvironment(environment)
				.setLockQuickConfig()
				.build();

	}
}
