package GlobalUtils.AspectJ;

import GlobalUtils.Global;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointCut {

	@Around("execution(* WebComponent.Controller.*.*(..))")
	public Object controllerAnalysis(ProceedingJoinPoint joinPoint) throws Throwable {
		return processPointCut(joinPoint, "Controller");
	}

	private Object processPointCut(ProceedingJoinPoint joinPoint, String cutType) throws Throwable {
		Global.Logger(this).info("开始调用" + cutType + ":" + joinPoint.getSignature().getName());
		long start = System.currentTimeMillis();
		Object obj = joinPoint.proceed(joinPoint.getArgs());
		long end = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append("调用" + cutType + ":" + joinPoint.getSignature().getName() + ",");
		sb.append("耗时：" + (end - start) + "ms" + "");
		Global.Logger(this).info(sb.toString());
		return obj;
	}
}
