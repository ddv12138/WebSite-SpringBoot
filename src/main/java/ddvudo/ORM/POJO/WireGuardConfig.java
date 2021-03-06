package ddvudo.ORM.POJO;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.List;

public class WireGuardConfig {
	Interface anInterface = new Interface();
	List<Peer> peers = new LinkedList<Peer>();

	public List<Peer> getPeers() {
		return peers;
	}

	public Interface getAnInterface() {
		return anInterface;
	}

	public void setAnInterface(Interface anInterface) {
		this.anInterface = anInterface;
	}

	public void setPeers(List<Peer> peers) {
		this.peers = peers;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
