package ddvudo.GlobalUtils.Config;

import ddvudo.GlobalUtils.Config.Propertis.MinIOPropertis;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class FileStorageConfig {

	@Bean
	public MinioClient MinioClient(MinIOPropertis minIOPropertis)
			throws InvalidPortException, InvalidEndpointException {
		return new MinioClient(minIOPropertis.getEndPoint(), minIOPropertis.getAccessKey(),
				minIOPropertis.getSecretKey());
	}

	@Bean
	public freemarker.template.Configuration FreeMarkConfig() throws IOException {
		freemarker.template.Configuration configuration = new freemarker.template.Configuration(
				freemarker.template.Configuration.VERSION_2_3_0);
		configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
		return configuration;
	}
}
