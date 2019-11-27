package WebComponent.Controller;

import GlobalUtils.CommonResult;
import GlobalUtils.Global;
import Service.Services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class CityController {
	@Autowired
	CityService cityService;

	@RequestMapping("/getCityInfo")
	@ResponseBody
	public CommonResult getCityInfo(String cityName) {
		String res = Global.doGetHttpRequest("http://api.map.baidu.com/place/v2/search?query=" + cityName + "&region=全国&output=json&ak=pB1cQmp3mKHrI8PMYQGoogGvGnpahqNn");
		return new CommonResult(true, "success", res);
	}
}
