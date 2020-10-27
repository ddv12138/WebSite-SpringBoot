import ddvudo.Application;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.EnterpriseRegistrationMapper;
import ddvudo.ORM.Mapper.HouseMapper;
import ddvudo.ORM.Mapper.IconMapper;
import ddvudo.ORM.Mapper.WireGuardConfigMapper;
import ddvudo.ORM.POJO.EnterpriseRegistrationExample;
import ddvudo.ORM.POJO.House;
import ddvudo.Service.Services.WireGuardService;
import freemarker.template.TemplateException;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.elasticsearch.common.Glob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.function.Consumer;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataTest {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	HouseMapper houseMapper;

	@Test
	public void test() {
		Cursor<House> houses = houseMapper.selectList();
		houses.forEach(new Consumer<House>() {
			@Override
			public void accept(House house) {
				Global.Logger(this).info(house);
			}
		});
	}
}
