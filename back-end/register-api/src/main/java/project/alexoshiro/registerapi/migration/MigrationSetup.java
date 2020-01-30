package project.alexoshiro.registerapi.migration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.mongobee.Mongobee;

@Component
public class MigrationSetup {

	@Value("${spring.data.mongodb.host}")
	private String host;

	@Value("${spring.data.mongodb.port}")
	private String port;

	@Value("${spring.data.mongodb.database}")
	private String database;

	@Bean
	@Autowired
	public Mongobee mongobee(Environment environment) {
		Mongobee runner = new Mongobee(constructUri());
		runner.setDbName(database);
		runner.setSpringEnvironment(environment);
		runner.setChangeLogsScanPackage("project.alexoshiro.registerapi.migration.changelogs");
		return runner;
	}

	private String constructUri() {
		return "mongodb://".concat(host).concat(":").concat(port);
	}
}
