import ddvudo.Application;
import ddvudo.ORM.Mapper.EnterpriseRegistrationMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataTest {
	@Autowired
	EnterpriseRegistrationMapper enterpriseRegistrationMapper;
	// 1.获取事务控制管理器
	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Autowired
	RestHighLevelClient client;
	@Autowired
	StringEncryptor encryptor;
	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@Test
	public void test() throws IOException, InterruptedException {
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
//		String url = encryptor.encrypt(
//				"jdbc:postgresql://127.0.0.1:5432/how2jdb?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8");
//		String name = encryptor.encrypt("postgres");
//		String password = encryptor.encrypt("liukang951006");
//		System.out.println("----------------");
//		System.out.println(url);
//		System.out.println(name);
//		System.out.println(password);
//		System.out.println(encryptor.encrypt("194.156.133.226"));
//		System.out.println(encryptor.encrypt("6379"));
//		System.out.println(encryptor.encrypt(
//				"jdbc:postgresql://localhost:5432/how2jdb?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8"));
//		Assert.assertTrue(name.length() > 0);
//		Assert.assertTrue(password.length() > 0);
//		final int totalSIze = 5888628;
//		int coreSize = Runtime.getRuntime().availableProcessors();
//		int dataPreThread = (int) Math.ceil((double)totalSIze/(double)coreSize);
//		for(int i=0;i<coreSize;i++){
//			int end = Math.min(((dataPreThread * i) + dataPreThread), totalSIze);
//			Global.Logger().info(((dataPreThread*i)+1)+"#"+(end));
//		}
//		Cursor<Object> cursor = null;
//		SqlSession sqlSession = null;
//		sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession();
//		cursor = sqlSession.selectCursor(HouseMapper.class.getName() + ".selectList");
//		cursor.forEach(e -> {
//			Global.Logger(this).info(e);
//		});
//		cursor.close();

//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("name","*腾讯*"));
//		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//		SearchRequest searchRequest = new SearchRequest();
//		searchRequest.indices("enterprise");
//		searchRequest.types("_doc");
//		searchRequest.source(searchSourceBuilder);
//		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
//		Global.Logger().info(JSON.toJSONString(searchResponse));
		//wxRobotTask.pushMessage();
	}
}
