package ddvudo.ScheduledTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ddvudo.GlobalUtils.Global;
import org.elasticsearch.common.Glob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WXRobotTask {
	@Value("${QYWX.robot.webhook}")
	String QYWX_webhook;
	SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Scheduled(fixedRate = 1000 * 60 * 5)
	public void pushMessage() {
		JSONObject body = new JSONObject();
		JSONObject text = new JSONObject();
		text.put("content", "现在开始报时:" + ymdhms.format(new Date()));
		body.put("msgtype", "text");
		body.put("text", text);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.POST;
		// 以表单的方式提交
		headers.setContentType(MediaType.APPLICATION_JSON);
		//将请求头部和参数合成一个请求
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(body, headers);
		//执行HTTP请求，将返回的结构使用ResultVO类格式化
		ResponseEntity<?> response = restTemplate.exchange(QYWX_webhook, method, requestEntity, Object.class);
		Global.Logger().info(QYWX_webhook);
		Global.Logger().info(JSON.toJSONString(response));
	}
}
