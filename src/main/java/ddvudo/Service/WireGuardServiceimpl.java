package ddvudo.Service;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
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

	@Override
	public boolean SaveConfigs(List<WireGuardConfig> configList) throws IOException, TemplateException {
		Assert.isTrue(StringUtils.isEmpty(configPath), "配置文件夹路径获取失败");
		File configDir = new File(configPath);
		Assert.isTrue(configDir.isDirectory(), "配置文件夹不存在，请检查是否有wireguard环境");
		Writer out;
		Template template = FreeMarkConfig.getTemplate("wgConfigTemplate.ftl");
		List<String> res = new LinkedList<>();
		for (int i = 0; i < configList.size(); i++) {
			OutputStream output = new ByteOutputStream();
			out = new BufferedWriter(new OutputStreamWriter(output));
			template.process(configList.get(i), out);
			out.flush();
			res.add(output.toString());
		}
		return true;
	}

	@Override
	public List<WireGuardConfig> newConfigList(String serverCIDR, int listeningPort, int numberOfClients,
											   String Endpoint, String ClientDns, String postUpRule,
											   String postDownRule) {
		List<WireGuardConfig> configs = new LinkedList<>();
		Curve25519KeyPair serverkeyPair = Curve25519.getInstance(Curve25519.JAVA).generateKeyPair();
		String serverPub = Base64.getEncoder().encodeToString(serverkeyPair.getPublicKey());
		String serverPri = Base64.getEncoder().encodeToString(serverkeyPair.getPrivateKey());
		WireGuardConfig config = new WireGuardConfig();
		String subNetShadow = serverCIDR.substring(serverCIDR.lastIndexOf("/") + 1);
		serverCIDR = serverCIDR.substring(0, serverCIDR.lastIndexOf("/"));
		String serverPrefix = serverCIDR.substring(0, serverCIDR.lastIndexOf("."));

		config.getInterface().setAddress(serverPrefix + "." + 1).setListenPort(listeningPort)
				.setPrivateKey(serverPri);
		if (!StringUtils.isEmpty(postUpRule)) {
			config.getInterface().setPostUp(postUpRule);
		}
		if (!StringUtils.isEmpty(postDownRule)) {
			config.getInterface().setPostDown(postDownRule);
		}
		configs.add(config);
		for (int i = 2; i < numberOfClients + 2; i++) {
			Curve25519KeyPair peerKeyPair = Curve25519.getInstance(Curve25519.JAVA).generateKeyPair();
			String peerPub = Base64.getEncoder().encodeToString(peerKeyPair.getPublicKey());
			String peerPri = Base64.getEncoder().encodeToString(peerKeyPair.getPrivateKey());

			WireGuardConfig.Peer peer = config.new Peer();
			peer.setPublicKey(peerPub).setAllowedIPs(serverPrefix + "." + i);
			config.getPeers().add(peer);

			WireGuardConfig peerConfig = new WireGuardConfig();
			peerConfig.getInterface().setPrivateKey(peerPri).setAddress(serverPrefix + "." + i + "/" + subNetShadow);
			if (!StringUtils.isEmpty(ClientDns))
				peerConfig.getInterface().setDNS(ClientDns);
			WireGuardConfig.Peer peerOfpeer = peerConfig.new Peer();
			peerOfpeer.setPublicKey(serverPub).setAllowedIPs("0.0.0.0/0").setEndpoint(Endpoint);
			peerConfig.getPeers().add(peerOfpeer);
			configs.add(peerConfig);
		}
		return configs;
	}
}
