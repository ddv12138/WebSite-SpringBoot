import com.alibaba.fastjson.JSON;
import ddvudo.Application;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.EnterpriseRegistrationMapper;
import ddvudo.ORM.Mapper.HouseMapper;
import ddvudo.ORM.Mapper.IconMapper;
import ddvudo.ORM.Mapper.WireGuardConfigMapper;
import ddvudo.ORM.POJO.EnterpriseRegistration;
import ddvudo.ORM.POJO.EnterpriseRegistrationExample;
import ddvudo.ORM.POJO.House;
import ddvudo.Service.Services.WireGuardService;
import freemarker.template.TemplateException;
import org.apache.http.HttpHost;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Glob;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.function.Consumer;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataTest {
	@Autowired
	EnterpriseRegistrationMapper enterpriseRegistrationMapper;
	// 1.获取事务控制管理器
	@Autowired
	private DataSourceTransactionManager transactionManager;

	private static final String ELASTICSEARCH_URL = "ddvudo.buzz";
	private static final short ELASTICSEARCH_PORT = 9200;
	private static final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
			new HttpHost(ELASTICSEARCH_URL, ELASTICSEARCH_PORT)));

	@Test
	public void test() throws IOException {
//		// 2.获取事务定义
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		// 3.设置事务隔离级别，开启新事务
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		// 4.获得事务状态
//		TransactionStatus status = transactionManager.getTransaction(def);
//		Global.Logger(this).info(enterpriseRegistrationMapper.selectCursor("test"));
//		for (int i = 1; i <= 1000000; i++) {
//			EnterpriseRegistration enterprise = enterpriseRegistrationMapper.fetchNext("test", i);
//			IndexRequest request = new IndexRequest().id(String.valueOf(enterprise.getId())).type("_doc")
//					.index("enterprise");
//			request.source(JSON.toJSONString(enterprise), XContentType.JSON);
//			IndexResponse res = client.index(request, RequestOptions.DEFAULT);
//			Global.Logger(this).info(JSON.toJSONString(res));
//		}
//		transactionManager.commit(status);
		Global.Logger().info("hello");

//		Cursor<Object> cursor = null;
//		SqlSession sqlSession = null;
//		sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession();
//		cursor = sqlSession.selectCursor(HouseMapper.class.getName() + ".selectList");
//		cursor.forEach(e -> {
//			Global.Logger(this).info(e);
//		});
//		cursor.close();
	}
}
