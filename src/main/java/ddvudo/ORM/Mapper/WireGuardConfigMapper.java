package ddvudo.ORM.Mapper;

import ddvudo.ORM.POJO.Interface;
import ddvudo.ORM.POJO.Peer;
import ddvudo.ORM.POJO.WireGuardConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface WireGuardConfigMapper {

	int insertWGInterface(Interface wgInterface);

	int insertWGPeer(Peer peer);

	Interface selectWGInterfaceById(int id);

	ArrayList<Peer> selectWGPeerByInterfaceId(int interfaceId);

	ArrayList<WireGuardConfig> selectWGServerList(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

	int deleteWireGuardConfigInterface(int interfaceId);

	int deleteWireGuardConfigPeersByInterfaceId(int interfaceId);

	ArrayList<WireGuardConfig> selectWGSubPeerList(int interfaceId);
}
