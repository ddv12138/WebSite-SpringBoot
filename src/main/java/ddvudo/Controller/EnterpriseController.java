package ddvudo.Controller;

import ddvudo.GlobalUtils.Global;
import ddvudo.ScheduledTask.ElasticSearchTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
		return "Speed:" + speed + "/s,Remain:" + ((5888628 - end) / speed / 60 / 60) + " hours,handled: " + end + " row,last " + (5888628 - end) + " row";
	}
}
