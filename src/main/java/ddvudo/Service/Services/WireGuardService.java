package ddvudo.Service.Services;

import ddvudo.ORM.POJO.WireGuardConfig;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;

public interface WireGuardService {
	boolean SaveConfigs(List<WireGuardConfig> configList);

	boolean SaveConfigsTOFiles(List<WireGuardConfig> configList) throws IOException, TemplateException;

	List<WireGuardConfig> newConfigList(String serverCIDR, int port, int numberOfClients, String Endpoint,
										String ClientDns,
										String postUpRule, String postDownRule, String remark);
}
