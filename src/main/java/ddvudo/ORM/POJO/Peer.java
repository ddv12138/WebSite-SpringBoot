package ddvudo.ORM.POJO;

import com.alibaba.fastjson.JSON;

public class Peer {
	Integer interfaceId;
	Integer id;
	String publicKey;
	String allowedIPs;
	String endpoint;
	Integer persistentKeepalive;

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
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getAllowedIPs() {
		return allowedIPs;
	}

	public void setAllowedIPs(String allowedIPs) {
		this.allowedIPs = allowedIPs;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public Integer getPersistentKeepalive() {
		return persistentKeepalive;
	}

	public void setPersistentKeepalive(Integer persistentKeepalive) {
		this.persistentKeepalive = persistentKeepalive;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
