package ddvudo.ORM.POJO;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.List;

public class WireGuardConfig {
	int id;
	Interface anInterface = new Interface();
	List<Peer> peers = new LinkedList<Peer>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Peer> getPeer() {
		return peers;
	}

	public Interface getAnInterface() {
		return anInterface;
	}

	public void setAnInterface(Interface anInterface) {
		this.anInterface = anInterface;
	}

	public void setPeer(List<Peer> peers) {
		this.peers = peers;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
