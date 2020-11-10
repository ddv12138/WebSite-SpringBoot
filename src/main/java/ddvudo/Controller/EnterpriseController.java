package ddvudo.Controller;

import ddvudo.ScheduledTask.ElasticSearchTask;
import ddvudo.Service.Services.EnterpriseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@RequestMapping("/getEnterpriseList")
	@ResponseBody
	public HashMap<String, String> listEnterprise(@RequestBody HashMap<String, String> pars) {
		return pars;
	}

	@RequestMapping("/transDataToEs")
	public boolean transDataToEs() {
		new Thread(() -> task.doTask()).start();
		return true;
	}
}
