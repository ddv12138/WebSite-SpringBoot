package ddvudo.Controller;

import ddvudo.GlobalUtils.Global;
import ddvudo.ScheduledTask.ElasticSearchTask;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
	@Autowired
	ElasticSearchTask task;
	@Autowired
	RestHighLevelClient elasticSearchClient;
	public static final ExecutorService pool = Executors.newCachedThreadPool();
	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@RequestMapping("/getEnterpriseList")
	@ResponseBody
	public HashMap<String, String> listEnterprise(@RequestBody HashMap<String, String> pars) {
		return pars;
	}

	@RequestMapping("/transDataToEs")
	public boolean transDataToEs() {
		final int totalSIze = 5888628;
		int coreSize = Runtime.getRuntime().availableProcessors();
		int dataPreThread = (int) Math.ceil((double) totalSIze / (double) coreSize);
		for (int i = 0; i < coreSize; i++) {
			int start = ((dataPreThread * i) + 1);
			int end = Math.min(((dataPreThread * i) + dataPreThread), totalSIze);
			pool.execute(() -> task.doTask(start, end));
		}
		return true;
	}

	@RequestMapping("/transSpeed")
	public String TransSpeed() throws InterruptedException {
		int start = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get("ESTotal")));
		Thread.sleep(2000);
		int end = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get("ESTotal")));
		int speed = (end - start) / 2;
		if (speed == 0) {
			return "no Speed";
		}
		return "Speed:" + speed + "/s,Remain:" + ((5888628 - end) / speed / 60 / 60) + " hours,handled: " + end + " row,last " + (5888628 - end) + " row";
	}

	@GetMapping("/search")
	public SearchResponse searcEnterpriseByName(@RequestBody HashMap<String, String> pars) throws IOException {
		Assert.notNull(pars.get("keyword"), "关键词不能为空");
		String keyword = pars.get("keyword");
		int pageNo = (pars.containsKey("pageNo") && Integer.parseInt(pars.get("pageNo")) > 0) ? Integer
				.parseInt(pars.get("pageNo")) : 1;
		int pageSize = (pars.containsKey("pageSize") && Integer.parseInt(pars.get("pageSize")) > 0) ? Integer
				.parseInt(pars.get("pageSize")) : 15;
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("name", keyword));
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.scroll(TimeValue.timeValueMinutes(30));
		searchRequest.indices("enterprise");
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = elasticSearchClient.search(searchRequest, RequestOptions.DEFAULT);
		return searchResponse;
	}
}
