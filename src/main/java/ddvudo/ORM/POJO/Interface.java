package ddvudo.ORM.POJO;

public class Interface {
	Integer id;
	Integer serverId;
	String privateKey;
	String address;
	Integer listenPort;
	String postUp;
	String postDown;
	String DNS;

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public Interface setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Interface setAddress(String address) {
		this.address = address;
		return this;
	}

	public Integer getListenPort() {
		return listenPort;
	}

	public Interface setListenPort(Integer listenPort) {
		this.listenPort = listenPort;
		return this;
	}

	public String getPostUp() {
		return postUp;
	}

	public Interface setPostUp(String postUp) {
		this.postUp = postUp;
		return this;
	}

	public String getPostDown() {
		return postDown;
	}

	public Interface setPostDown(String postDown) {
		this.postDown = postDown;
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
