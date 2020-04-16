package ddvudo.ScheduledTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.POJO.City;
import ddvudo.ORM.POJO.Community;
import ddvudo.ORM.POJO.District;
import ddvudo.WebComponent.Service.Services.CityService;
import ddvudo.WebComponent.Service.Services.CommunityService;
import ddvudo.WebComponent.Service.Services.DistrictService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("server")
public class LianjiaDataTask {
	final CommunityService communityService;
	final DistrictService districtService;
	final CityService cityService;
	final String[] cities = {"武汉"};

	public LianjiaDataTask(CommunityService communityService, CityService cityService, DistrictService districtService) {
		this.communityService = communityService;
		this.cityService = cityService;
		this.districtService = districtService;
	}

	public void getCommunityDataByCity() {
		String cityName = "武汉";
		City city = cityService.selectByName(cityName);
		List<District> districts = districtService.selectByCity(city);
		//todo
	}

	public void transCoordSys() {
		String citystr = "武汉";
		City city = cityService.selectByName(citystr);
		List<Community> communities = communityService.selectHetMapDataByCity(city);
		for (Community community : communities) {
			String url = "https://restapi.amap.com/v3/assistant/coordinate/convert?" +
					"locations=" + community.getLongitude() + "," + community.getLatitude() + "&" +
					"coordsys=baidu&" +
					"output=json&" +
					"key=5e842e2d890e0361743c15a6e1ec168a";
			String resStr = Global.doGetHttpRequest(url);
			JSONObject res = JSON.parseObject(resStr);
			if (res.getIntValue("status") == 1) {
				community.setGaode_lng(res.getString("locations").split(",")[0]);
				community.setGaode_lat(res.getString("locations").split(",")[1]);
				int i = communityService.updateOneLoc(community);
				Global.Logger(this).info(communities.size() + "/" + communities.indexOf(community));
			}
		}
	}
}
