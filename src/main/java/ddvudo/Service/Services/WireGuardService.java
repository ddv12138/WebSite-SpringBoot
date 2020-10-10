package ddvudo.Service.Services;

import ddvudo.ORM.POJO.WireGuardConfig;
import freemarker.template.TemplateException;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface WireGuardService {
	boolean saveConfigs(List<WireGuardConfig> configList);

	List<String> SaveConfigsToFiles(List<WireGuardConfig> configList) throws IOException, TemplateException;

	List<WireGuardConfig> newConfigList(String serverCIDR, int port, int numberOfClients, String Endpoint,
										String ClientDns,
										String postUpRule, String postDownRule, String remark);

	ArrayList<WireGuardConfig> selectWGServerList(int pageNum, int pageSize);

	boolean deleteWireGuardConfig(WireGuardConfig config);
}
