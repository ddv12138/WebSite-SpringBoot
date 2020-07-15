package ddvudo.WebComponent.Controller;

import ddvudo.WebComponent.Service.Services.EnterpriseRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

	EnterpriseRegistrationService service;

	@RequestMapping("/getEnterpriseList")
	@ResponseBody
	public HashMap<String, String> listEnterprise(@RequestBody HashMap<String, String> pars) {
		return pars;
	}
}
