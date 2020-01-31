package project.alexoshiro.registerapi.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
public class EmbeddedMongoConfig {
	
	private int serverPort = 61828;
	
	@Bean
    public IMongodConfig mongodConfig() throws IOException {

        return new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(serverPort, Network.localhostIsIPv6()))
                .build();
    }
}
