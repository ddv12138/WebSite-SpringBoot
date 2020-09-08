package ddvudo.Service;

import ddvudo.ORM.POJO.WireGuardConfig;
import ddvudo.Service.Services.WireGuardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.Curve25519KeyPair;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Service(value = "WireGuardService")
@Transactional
public class WireGuardServiceimpl implements WireGuardService {
	public List<WireGuardConfig> newConfigList(String serverCIDR, int listeningPort, int numberOfClients,
											   String Endpoint, String postUpRule, String postDownRule) {
		List<WireGuardConfig> configs = new LinkedList<>();
		Curve25519KeyPair serverkeyPair = Curve25519.getInstance(Curve25519.JAVA).generateKeyPair();
		String serverPub = Base64.getEncoder().encodeToString(serverkeyPair.getPublicKey());
		String serverPri = Base64.getEncoder().encodeToString(serverkeyPair.getPrivateKey());
		WireGuardConfig config = new WireGuardConfig();
		String subNetShadow = serverCIDR.substring(serverCIDR.lastIndexOf("/") + 1);
		serverCIDR = serverCIDR.substring(0, serverCIDR.lastIndexOf("/"));
		String serverPrefix = serverCIDR.substring(0, serverCIDR.lastIndexOf("."));

		config.getInterface().setAddress(serverPrefix + "." + 1).setListenPort(listeningPort)
				.setPrivateKey(serverPri).setPostUp(postUpRule).setPostDown(postDownRule);
		configs.add(config);
		for (int i = 2; i < numberOfClients + 2; i++) {
			Curve25519KeyPair peerKeyPair = Curve25519.getInstance(Curve25519.JAVA).generateKeyPair();
			String peerPub = Base64.getEncoder().encodeToString(peerKeyPair.getPublicKey());
			String peerPri = Base64.getEncoder().encodeToString(peerKeyPair.getPrivateKey());

			WireGuardConfig.Peer peer = config.new Peer();
			peer.setPublicKey(peerPub).setAllowedIPs(serverPrefix + "." + i);
			config.getPeers().add(peer);

			WireGuardConfig peerConfig = new WireGuardConfig();
			peerConfig.getInterface().setPrivateKey(peerPri).setAddress(serverPrefix + "." + i + "/" + subNetShadow)
					.setDNS("114.114.114.114");
			WireGuardConfig.Peer peerOfpeer = peerConfig.new Peer();
			peerOfpeer.setPublicKey(serverPub).setAllowedIPs("0.0.0.0/0").setEndpoint(Endpoint);
			peerConfig.getPeers().add(peerOfpeer);
			configs.add(peerConfig);
		}
		return configs;
	}
}
