package ddvudo.GlobalUtils.SecurityHandlers;

import com.alibaba.fastjson.JSON;
import ddvudo.GlobalUtils.CommonResult;
import ddvudo.GlobalUtils.Global;
import ddvudo.GlobalUtils.SystemCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestUtil {
	public static void response(HttpServletResponse response, SystemCode systemCode) {
		response(response, systemCode, systemCode.getMessage());
	}

	public static void response(HttpServletResponse response, SystemCode systemCode, String msg) {
		response(response, systemCode, msg, null);
	}


	public static void response(HttpServletResponse response, SystemCode systemCode, String msg, Object content) {
		try {
			CommonResult res = new CommonResult<>(systemCode, msg, content);
			String resStr = JSON.toJSONString(res);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(resStr);
		} catch (IOException e) {
			Global.logger().error(e.getMessage(), e);
		}
	}
}
