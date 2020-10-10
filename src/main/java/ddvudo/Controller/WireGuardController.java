package ddvudo.Controller;

import ddvudo.ORM.POJO.WireGuardConfig;
import ddvudo.Service.Services.WireGuardService;
import freemarker.template.TemplateException;
import io.swagger.annotations.*;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/wg")
@Api(tags = "wireguard相关服务接口")
public class WireGuardController {
	final WireGuardService wireGuardService;

	public WireGuardController(WireGuardService wireGuardService) {
		this.wireGuardService = wireGuardService;
	}

	@PostMapping("/genconfig")
	@ApiOperation("生成wireguard服务端配置和相应数量的客户端配置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "params",
					dataType = "ApplicationProperties",
					examples = @Example({
							@ExampleProperty(
									value = "{\"dns\":\"114.114.114.114\",\"endPoint\":\"your.domian.address:9003\",\"numberOfClients\":5,\"port\":9003,\"postDownRule\":\"iptables -D FORWARD -i %i -j ACCEPT; iptables -t nat -D POSTROUTING -o eth0 -j MASQUERADE\",\"postUpRule\":\"iptables -A FORWARD -i %i -j ACCEPT; iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE\",\"serverCIDR\":\"10.0.0.0/24\"}", mediaType = "application/json")
					}))
	})
	public List<WireGuardConfig> generatorNewConfigPair(@RequestBody Map<String, String> params) {
		String ServerCIDR = params.get("ServerCIDR");
		Integer port = Integer.parseInt(params.get("port"));
		Integer numberOfClients = Integer.parseInt(params.get("numberOfClients"));
		String endPoint = params.get("endPoint");

		Assert.notNull(ServerCIDR, "服务器CIDR地址为空");
		Assert.notNull(port, "服务器监听端口为空");
		Assert.notNull(numberOfClients, "客户端数量为空");
		Assert.notNull(endPoint, "客户端使用的服务器端的端点为空");
		Assert.isTrue(numberOfClients > 0, "客户端数量必须大于0");

		String dns = params.get("dns");
		String postUpRule = params.get("postUpRule");
		String postDownRule = params.get("postDownRule");
		String remark = params.get("remark");

		List<WireGuardConfig> res = wireGuardService
				.newConfigList(ServerCIDR, port, numberOfClients, endPoint, dns, postUpRule, postDownRule, remark);
		if (wireGuardService.saveConfigs(res)) {
			return res;
		} else {
			throw new RuntimeException("wireguard配置保存失败");
		}
	}

	@GetMapping("/listServer")
	@ApiOperation("获取服务端列表")
	public ArrayList<WireGuardConfig> listWireGuardConfig(@RequestParam(defaultValue = "1") int pageNum,
														  @RequestParam(defaultValue = "10") int pageSize) {
		return wireGuardService.selectWGServerList(pageNum, pageSize);
	}

	@PostMapping("/listClients")
	@ApiOperation("获取客户端列表")
	public List<String> listWireGuardConfig(@RequestBody WireGuardConfig config)
			throws IOException, TemplateException {
		List<String> serverStr = wireGuardService.SaveConfigsToFiles(Collections.singletonList(config));
		int interfaceId = config.getAnInterface().getId();
		Assert.notNull(interfaceId, "服务端id不能为null");
		List<WireGuardConfig> clients = wireGuardService.selectWGSubPeerList(interfaceId);
		serverStr.addAll(wireGuardService.SaveConfigsToFiles(clients));
		return serverStr;
	}

	@DeleteMapping
	@ApiOperation("删除配置")
	public boolean deleteWireGuardConfig(@RequestBody WireGuardConfig config) {
		return wireGuardService.deleteWireGuardConfig(config);
	}
}
