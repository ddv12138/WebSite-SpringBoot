package ddvudo.Controller;

import ddvudo.ScheduledTask.ElasticSearchTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
	@Autowired
	ElasticSearchTask task;

	public static Thread transThread = null;

	@RequestMapping("/getEnterpriseList")
	@ResponseBody
	public HashMap<String, String> listEnterprise(@RequestBody HashMap<String, String> pars) {
		return pars;
	}

	@RequestMapping("/transDataToEs")
	public boolean transDataToEs() {
		transThread = new Thread(() -> task.doTask());
		transThread.setName("dataThread");
		transThread.start();
		return true;
	}

	@RequestMapping("/transStates")
	public Thread.State transStates() {
		return transThread.getState();
	}
}
