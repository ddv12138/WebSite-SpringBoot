import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import ddvudo.Application;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.POJO.WireGuardConfig;
import ddvudo.Service.Services.WireGuardService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataTest {
	@Autowired
	WireGuardService wireGuardService;

	@Test
	public void test() throws IOException, TemplateException {
		List<WireGuardConfig> configList =
				wireGuardService.newConfigList("10.1.1.0/24", 9002, 5,
						"ddvudo.buzz:9002", "114.114.114.114",
						"iptables -A FORWARD -i %i -j ACCEPT; iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE",
						"iptables -D FORWARD -i %i -j ACCEPT; iptables -t nat -D POSTROUTING -o eth0 -j MASQUERADE");
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
		Writer out = null;
		configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
		Template template = configuration.getTemplate("wgConfigTemplate.ftl");
		List<String> res = new LinkedList<>();
		for (int i = 0; i < configList.size(); i++) {
			OutputStream output = new ByteOutputStream();
			out = new BufferedWriter(new OutputStreamWriter(output));
			template.process(configList.get(i), out);
			out.flush();
			res.add(output.toString());
		}
		Global.Logger(DataTest.class).info(JSON.toJSONString(res));
	}
}
