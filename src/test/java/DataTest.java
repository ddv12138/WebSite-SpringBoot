import ddvudo.Application;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.EnterpriseRegistrationMapper;
import ddvudo.ORM.Mapper.HouseMapper;
import ddvudo.ORM.Mapper.IconMapper;
import ddvudo.ORM.Mapper.WireGuardConfigMapper;
import ddvudo.ORM.POJO.EnterpriseRegistrationExample;
import ddvudo.Service.Services.WireGuardService;
import freemarker.template.TemplateException;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataTest {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Test
	public void test() {
		sqlSessionTemplate.select("ddvudo.ORM.Mapper.CityMapper.selectAvaliableCity", null, new ResultHandler() {
			@Override
			public void handleResult(ResultContext resultContext) {
				Global.Logger(this).info(resultContext.getResultObject());
			}
		});
	}
}
