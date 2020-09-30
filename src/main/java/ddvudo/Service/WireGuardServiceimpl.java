package ddvudo.Service;

import ddvudo.ORM.Mapper.WireGuardConfigMapper;
import ddvudo.ORM.POJO.Peer;
import ddvudo.ORM.POJO.WireGuardConfig;
import ddvudo.Service.Services.WireGuardService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.Curve25519KeyPair;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Service(value = "WireGuardService")
@Transactional
public class WireGuardServiceimpl implements WireGuardService {
	@Autowired
	Configuration FreeMarkConfig;
	@Value("${wireguard.config.dir:/etc/wireguard/}")
	private String configPath;
	@Autowired
	WireGuardConfigMapper wireGuardConfigMapper;

	@Override
	public boolean SaveConfigs(List<WireGuardConfig> configs) {
		Integer currServerId = null;
		for (WireGuardConfig config : configs) {
			if (null != currServerId) {
				config.getAnInterface().setServerId(currServerId);
			}
			wireGuardConfigMapper.insertWGInterface(config.getAnInterface());
			if (configs.indexOf(config) == 0) {
				currServerId = config.getAnInterface().getId();
			}
			for (Peer peer : config.getPeers()) {
				peer.setInterfaceId(config.getAnInterface().getId());
				wireGuardConfigMapper.insertWGPeer(peer);
			}
		}
		return true;
	}

	@Override
	public List<String> SaveConfigsToFiles(List<WireGuardConfig> configList) throws IOException, TemplateException {
		Assert.isTrue(StringUtils.isEmpty(configPath), "配置文件夹路径获取失败");
		File configDir = new File(configPath);
		Assert.isTrue(configDir.isDirectory(), "配置文件夹不存在，请检查是否有wireguard环境");
		Writer out;
		Template template = FreeMarkConfig.getTemplate("wgConfigTemplate.ftl");
		List<String> res = new LinkedList<>();
		for (int i = 0; i < configList.size(); i++) {
			OutputStream output = new ByteArrayOutputStream();
			out = new BufferedWriter(new OutputStreamWriter(output));
			template.process(configList.get(i), out);
			out.flush();
			res.add(output.toString());
		}
		return res;
	}

	@Override
	public List<WireGuardConfig> newConfigList(String serverCIDR, int listeningPort, int numberOfClients,
											   String Endpoint, String dns, String postUpRule,
											   String postDownRule, String remark) {
		List<WireGuardConfig> configs = new LinkedList<>();
		Curve25519KeyPair serverkeyPair = Curve25519.getInstance(Curve25519.JAVA).generateKeyPair();
		String serverPub = Base64.getEncoder().encodeToString(serverkeyPair.getPublicKey());
		String serverPri = Base64.getEncoder().encodeToString(serverkeyPair.getPrivateKey());
		WireGuardConfig config = new WireGuardConfig();
		String subNetShadow = "24";
		if (serverCIDR.lastIndexOf("/") >= 0) {
			subNetShadow = serverCIDR.substring(serverCIDR.lastIndexOf("/") + 1);
		}
		String serverPrefix = serverCIDR.substring(0, serverCIDR.lastIndexOf("."));

		config.getAnInterface().setAddress(serverPrefix + "." + 1 + "/" + subNetShadow).setListenPort(listeningPort)
				.setPrivateKey(serverPri).setRemark(remark);
		if (!StringUtils.isEmpty(postUpRule)) {
			config.getAnInterface().setPostUp(postUpRule);
		}
		if (!StringUtils.isEmpty(postDownRule)) {
			config.getAnInterface().setPostDown(postDownRule);
		}
		configs.add(config);
		if (!StringUtils.isEmpty(dns))
			config.getAnInterface().setDNS(dns);
		for (int i = 2; i < numberOfClients + 2; i++) {
			Curve25519KeyPair peerKeyPair = Curve25519.getInstance(Curve25519.JAVA).generateKeyPair();
			String peerPub = Base64.getEncoder().encodeToString(peerKeyPair.getPublicKey());
			String peerPri = Base64.getEncoder().encodeToString(peerKeyPair.getPrivateKey());

			Peer peer = new Peer();
			peer.setPublicKey(peerPub);
			peer.setAllowedIPs(serverPrefix + "." + i);
			config.getPeers().add(peer);

			WireGuardConfig peerConfig = new WireGuardConfig();
			String clientCIDR = serverPrefix + "." + i + "/" + subNetShadow;
			peerConfig.getAnInterface().setPrivateKey(peerPri).setAddress(clientCIDR).setRemark(remark);
			Peer peerOfpeer = new Peer();
			if (!Endpoint.contains(":")) {
				Endpoint += ":" + listeningPort;
			}
			peerOfpeer.setPublicKey(serverPub);
			peerOfpeer.setAllowedIPs(serverPrefix + "." + 0 + "/" + subNetShadow);
			peerOfpeer.setEndpoint(Endpoint);
			peerConfig.getPeers().add(peerOfpeer);
			configs.add(peerConfig);
		}
		return configs;
	}

	@Override
	public ArrayList<WireGuardConfig> selectWGServerList(int pageNum, int pageSize) {
		return wireGuardConfigMapper.selectWGServerList(pageNum, pageSize);
	}
}
