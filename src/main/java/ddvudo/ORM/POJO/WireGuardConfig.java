package ddvudo.ORM.POJO;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.List;

public class WireGuardConfig {
	Integer id;
	Interface Interface = new Interface();
	List<Peer> peers = new LinkedList<Peer>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Peer> getPeer() {
		return peers;
	}

	public Interface getInterface() {
		return Interface;
	}

	public void setInterface(Interface anInterface) {
		this.Interface = anInterface;
	}

	public void setPeer(List<Peer> peers) {
		this.peers = peers;
	}

	public class Interface {
		Integer id;
		String PrivateKey;
		String Address;
		Integer ListenPort;
		String PostUp;
		String PostDown;
		String DNS;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getPrivateKey() {
			return PrivateKey;
		}

		public Interface setPrivateKey(String privateKey) {
			PrivateKey = privateKey;
			return this;
		}

		public String getAddress() {
			return Address;
		}

		public Interface setAddress(String address) {
			Address = address;
			return this;
		}

		public Integer getListenPort() {
			return ListenPort;
		}

		public Interface setListenPort(Integer listenPort) {
			ListenPort = listenPort;
			return this;
		}

		public String getPostUp() {
			return PostUp;
		}

		public Interface setPostUp(String postUp) {
			PostUp = postUp;
			return this;
		}

		public String getPostDown() {
			return PostDown;
		}

		public Interface setPostDown(String postDown) {
			PostDown = postDown;
			return this;
		}

		public String getDNS() {
			return DNS;
		}

		public Interface setDNS(String DNS) {
			this.DNS = DNS;
			return this;
		}
	}

	public class Peer {
		Integer id;
		String PublicKey;
		String AllowedIPs;
		String Endpoint;
		Integer PersistentKeepalive;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getPublicKey() {
			return PublicKey;
		}

		public Peer setPublicKey(String publicKey) {
			PublicKey = publicKey;
			return this;
		}

		public String getAllowedIPs() {
			return AllowedIPs;
		}

		public Peer setAllowedIPs(String allowedIPs) {
			AllowedIPs = allowedIPs;
			return this;
		}

		public String getEndpoint() {
			return Endpoint;
		}

		public Peer setEndpoint(String endpoint) {
			Endpoint = endpoint;
			return this;
		}

		public Integer getPersistentKeepalive() {
			return PersistentKeepalive;
		}

		public Peer setPersistentKeepalive(Integer persistentKeepalive) {
			PersistentKeepalive = persistentKeepalive;
			return this;
		}
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
