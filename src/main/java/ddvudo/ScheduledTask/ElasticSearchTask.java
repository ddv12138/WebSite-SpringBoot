package ddvudo.ScheduledTask;

import com.alibaba.fastjson.JSON;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.EnterpriseRegistrationMapper;
import ddvudo.ORM.POJO.EnterpriseRegistration;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.ibatis.session.SqlSessionFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Glob;
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

@Component
public class ElasticSearchTask {
	private static final String ELASTICSEARCH_URL = "127.0.0.1";
	private static final short ELASTICSEARCH_PORT = 9200;
	private static final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
			new HttpHost(ELASTICSEARCH_URL, ELASTICSEARCH_PORT)));
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	@Autowired
	EnterpriseRegistrationMapper enterpriseRegistrationMapper;
	// 1.获取事务控制管理器
	@Autowired
	private DataSourceTransactionManager transactionManager;

	//	@Scheduled(fixedDelay = 1000L * 60L * 60L * 24L * 30L)
	public void doTask() {
		// 2.获取事务定义
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 3.设置事务隔离级别，开启新事务
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		// 4.获得事务状态
		TransactionStatus status = transactionManager.getTransaction(def);
		Global.Logger().info(enterpriseRegistrationMapper.selectCursor("test"));
		EnterpriseRegistration enterprise;
		String lastIndexStr = redisTemplate.opsForValue().get("currentESIndex");
		int index = 1;
		if (!StringUtils.isEmpty(lastIndexStr)) {
			index = Integer.parseInt(lastIndexStr) - 1;
		}
		while (null != (enterprise = enterpriseRegistrationMapper.fetchNext("test", index))) {
			try {
				long start = System.currentTimeMillis();
				Global.Logger().trace(JSON.toJSONString(enterprise));
				redisTemplate.opsForValue().set("lastResult", JSON.toJSONString(enterprise));
				IndexRequest request = new IndexRequest().id(String.valueOf(enterprise.getId())).type("_doc")
						.index("enterprise");
				request.source(JSON.toJSONString(enterprise), XContentType.JSON);
				client.index(request, RequestOptions.DEFAULT);
				redisTemplate.opsForValue().set("currentESIndex", index + "");
				redisTemplate.opsForValue().set("lastESLoopTime", String.valueOf(System.currentTimeMillis() - start));
				index += 1;
			} catch (Exception e) {
				Global.Logger().error(e);
			}
		}
		transactionManager.commit(status);
	}

}
