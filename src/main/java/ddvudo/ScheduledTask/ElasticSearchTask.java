package ddvudo.ScheduledTask;

import com.alibaba.fastjson.JSON;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.EnterpriseRegistrationMapper;
import ddvudo.ORM.POJO.EnterpriseRegistration;
import org.apache.http.HttpHost;
import org.apache.ibatis.session.SqlSessionFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Profile("server")
@Component
public class ElasticSearchTask {
	private static final String ELASTICSEARCH_URL = "127.0.0.1";
	private static final short ELASTICSEARCH_PORT = 9200;
	private static final short MAX_POOL_SIZE = 500;
	private static final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
			new HttpHost(ELASTICSEARCH_URL, ELASTICSEARCH_PORT)));
	@Resource
	RedisTemplate<String, String> redisTemplate;
	@Autowired
	EnterpriseRegistrationMapper enterpriseRegistrationMapper;
	// 1.获取事务控制管理器
	@Autowired
	private DataSourceTransactionManager transactionManager;
	long count = 0;

	//	@Scheduled(fixedDelay = 1000L * 60L * 60L * 24L * 30L)
	public void doTask() {
		// 2.获取事务定义
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 3.设置事务隔离级别，开启新事务
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		// 4.获得事务状态
		TransactionStatus status = transactionManager.getTransaction(def);
		Global.Logger(this).info(enterpriseRegistrationMapper.selectCursor("test"));
		EnterpriseRegistration enterprise;
		int index = 1;
		while (null != (enterprise = enterpriseRegistrationMapper.fetchNext("test", index))) {
			try {
				IndexRequest request = new IndexRequest().id(String.valueOf(enterprise.getId())).type("_doc")
						.index("enterprise");
				request.source(JSON.toJSONString(enterprise), XContentType.JSON);
				IndexResponse res = client.index(request, RequestOptions.DEFAULT);
				index += 1;
				redisTemplate.opsForValue().set("currentESIndex", index + "");
			} catch (IOException e) {
				Global.Logger(this).error(e);
			}
		}
		transactionManager.commit(status);
	}

}
