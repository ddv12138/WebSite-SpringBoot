package WebComponent.Controller;

import Services.HouseService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class HouseController {
	@Resource
	HouseService service;

}