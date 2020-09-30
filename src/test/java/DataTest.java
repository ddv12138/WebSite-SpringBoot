import ddvudo.Application;
import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.Mapper.WireGuardConfigMapper;
import ddvudo.ORM.POJO.Peer;
import ddvudo.ORM.POJO.WireGuardConfig;
import ddvudo.Service.Services.WireGuardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
//		ArrayList<Peer> peers = wireGuardConfigMapper.selectWGPeerByInterfaceId(69);
//		Global.Logger(this).info(peers);
		ArrayList<WireGuardConfig> wireGuardConfigs = wireGuardConfigMapper.selectWGServerList(1, 10);
		Global.Logger(this).info(wireGuardConfigs);
	}
}
