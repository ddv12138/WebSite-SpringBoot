package ddvudo.ORM.Mapper;

import ddvudo.ORM.POJO.WireGuardConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WireGuardConfigMapper {
	int insertWireguardConfig(@Param("config") WireGuardConfig config);

	int insertWGInterface(WireGuardConfig.Interface wgInterface);

	int insertWGPeer(WireGuardConfig.Peer peer);

	WireGuardConfig selectWGList(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
