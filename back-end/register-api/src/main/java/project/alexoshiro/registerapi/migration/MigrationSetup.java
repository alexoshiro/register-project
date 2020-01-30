package project.alexoshiro.registerapi.migration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.mongobee.Mongobee;

@Component
public class MigrationSetup {

	@Bean
	@Autowired
	public Mongobee mongobee(Environment environment) {
		Mongobee runner = new Mongobee("mongodb://localhost:27017");
		runner.setDbName("registerDB");
		runner.setSpringEnvironment(environment);
		runner.setChangeLogsScanPackage("project.alexoshiro.registerapi.migration.changelogs");
		return runner;
	}
}
