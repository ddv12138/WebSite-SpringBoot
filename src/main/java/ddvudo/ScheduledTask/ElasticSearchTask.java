package ddvudo.ScheduledTask;

import com.alibaba.fastjson.JSON;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.EnterpriseRegistrationMapper;
import ddvudo.ORM.POJO.EnterpriseRegistration;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class ElasticSearchTask {
	@Autowired
	RestHighLevelClient client;
	private static final String totalRedisKey = "ESTotal";
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	@Autowired
	EnterpriseRegistrationMapper enterpriseRegistrationMapper;
	// 1.获取事务控制管理器
	@Autowired
	private DataSourceTransactionManager transactionManager;

	//	@Scheduled(fixedDelay = 1000L * 60L * 60L * 24L * 30L)
	public void doTask(int start, int end) {
		Thread.currentThread().setName("currentESIndexAndLastESLoopTime-" + start + "-" + end);
		// 2.获取事务定义
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 3.设置事务隔离级别，开启新事务
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		// 4.获得事务状态
		TransactionStatus status = transactionManager.getTransaction(def);
		String cursorName = enterpriseRegistrationMapper.selectCursor("test");
		EnterpriseRegistration enterprise;
		String lastIndexStr = redisTemplate.opsForValue().get(Thread.currentThread().getName());
		int index = start;
		if (!StringUtils.isEmpty(lastIndexStr)) {
			index = Integer.parseInt(lastIndexStr.split("-")[0]) - 1;
		}
		while (null != (enterprise = enterpriseRegistrationMapper.fetchNext(cursorName, index)) && index <= end) {
			try {
				long startTime = System.currentTimeMillis();
				Global.Logger().trace(JSON.toJSONString(enterprise));
				IndexRequest request = new IndexRequest().id(String.valueOf(enterprise.getId())).type("_doc")
						.index("enterprise");
				request.source(JSON.toJSONString(enterprise), XContentType.JSON);
				client.index(request, RequestOptions.DEFAULT);
				int finalIndex = index;
				String threadKey = Thread.currentThread().getName();
				new Thread(() -> {
					redisTemplate.opsForValue()
							.set(threadKey, finalIndex + "-" + (System.currentTimeMillis() - startTime));
					redisTemplate.opsForValue().increment(totalRedisKey);
				}).start();
				index += 1;
			} catch (Exception e) {
				Global.Logger().error(e);
			}
		}
		transactionManager.commit(status);
	}

}
