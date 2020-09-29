package ddvudo.ORM.POJO;

public class Peer {
	Integer interfaceId;
	Integer id;
	String PublicKey;
	String allowedIPs;
	String Endpoint;
	Integer PersistentKeepalive;

	public Integer getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(Integer interfaceId) {
		this.interfaceId = interfaceId;
	}

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
		return allowedIPs;
	}

	public Peer setAllowedIPs(String allowedIPs) {
		this.allowedIPs = allowedIPs;
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
