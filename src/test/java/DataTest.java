import ddvudo.Application;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.WireGuardConfigMapper;
import ddvudo.Service.Services.WireGuardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataTest {
	@Autowired
	WireGuardConfigMapper wireGuardConfigMapper;
	@Autowired
	WireGuardService wireGuardService;

	@Value("${freeMark.templates.path}")
	String path;

	@Test
	public void test() {
		PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		Resource[] paths = Stream
				.of(Optional.ofNullable(new String[]{path}).orElse(new String[0])).flatMap((location) -> {
					try {
						return Stream.of(resourceResolver.getResources(location));
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}).toArray((x$0) -> new Resource[x$0]);
		Global.Logger(this).info(paths[0]);
	}
}
