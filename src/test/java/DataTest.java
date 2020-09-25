import ddvudo.Application;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.WireGuardConfigMapper;
import ddvudo.ORM.POJO.WireGuardConfig;
import ddvudo.Service.Services.WireGuardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataTest {
	@Autowired
	WireGuardConfigMapper wireGuardConfigMapper;
	@Autowired
	WireGuardService wireGuardService;

	@Test
	public void test() {
		List<WireGuardConfig> configs = wireGuardService
				.newConfigList("10.1.1.1", 9002, 1, "ddvudo.buzz", "114.114.114.114", null, null);
		Global.Logger(this).info(configs);
		wireGuardConfigMapper.insertWireguardConfig(configs.get(0));
		wireGuardConfigMapper.insertWGInterface(configs.get(0).getInterface());
		for (WireGuardConfig.Peer peer : configs.get(0).getPeer())
			wireGuardConfigMapper.insertWGPeer(peer);
		//Global.Logger(this).info(wireGuardConfigMapper.selectWGList(1, 10));
	}
}
