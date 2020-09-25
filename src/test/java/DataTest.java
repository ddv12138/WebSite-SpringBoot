import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import ddvudo.Application;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.WireGuardConfigMapper;
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
	WireGuardConfigMapper wireGuardConfigMapper;

	@Test
	public void test() throws IOException, TemplateException {
		Global.Logger(this).info(wireGuardConfigMapper.selectWGList(1, 10));
	}
}
