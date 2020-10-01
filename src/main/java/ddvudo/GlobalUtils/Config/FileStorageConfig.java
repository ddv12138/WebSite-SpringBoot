package ddvudo.GlobalUtils.Config;

import ddvudo.GlobalUtils.Config.Propertis.MinIOPropertis;
import ddvudo.GlobalUtils.Global;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Configuration
public class FileStorageConfig {

	@Bean
	public MinioClient MinioClient(MinIOPropertis minIOPropertis)
			throws InvalidPortException, InvalidEndpointException {
		return new MinioClient(minIOPropertis.getEndPoint(), minIOPropertis.getAccessKey(),
				minIOPropertis.getSecretKey());
	}

	@Value("${freeMark.templates.path}")
	String templatePath;

	@Bean
	public freemarker.template.Configuration FreeMarkConfig() throws IOException {
		PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		Resource[] paths = Stream
				.of(Optional.ofNullable(new String[]{templatePath}).orElse(new String[0])).flatMap((location) -> {
					try {
						return Stream.of(resourceResolver.getResources(location));
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}).toArray((x$0) -> new Resource[x$0]);
		if (paths.length > 0) {
			freemarker.template.Configuration configuration = new freemarker.template.Configuration(
					freemarker.template.Configuration.VERSION_2_3_0);
			configuration.setDirectoryForTemplateLoading(paths[0].getFile());
			return configuration;
		} else {
			Global.Logger(this).error("模板初始化失败，文件未找到");
			return null;
		}
	}
}
