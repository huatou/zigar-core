package com.zigar.zigarcore.log;

import com.zigar.zigarcore.utils.lang.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过配置切面打印请求日志
 */

@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class LogRecordAspect {

    // 定义切点Pointcut
    @Pointcut("execution(* com.zigar.*.controller.*.*(..))")
    public void logControllerUrlAndParams() {
    }

    // 定义切点Pointcut
    @Pointcut("execution(* com.zigar.*.rest.*.*(..))")
    public void logRestControllerUrlAndParams() {
    }

    /**
     * 在切面使用around
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("logControllerUrlAndParams() || logRestControllerUrlAndParams()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String requestUrl = request.getRequestURL().toString();
        String requestMethod = request.getMethod();
        Object[] proceedingJoinPointArgs = proceedingJoinPoint.getArgs();
        Map<Object, Object> params = new HashMap<>();
        //获取请求参数集合并进行遍历拼接
        for (int i = 0; i < proceedingJoinPointArgs.length; i++) {
            Object proceedingJoinPointArg = proceedingJoinPointArgs[i];
            if (proceedingJoinPointArg instanceof HttpServletRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) proceedingJoinPointArg;
                Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
                Map<String, String> parameterStrMap = new HashMap<>();
                parameterMap.forEach((a, b) -> {
                    parameterStrMap.put(a, b[0]);
                });
                params.putAll(parameterStrMap);
            } else if (proceedingJoinPointArg instanceof HttpServletResponse) {
                continue;
            } else {
                params.putAll(ObjectUtils.objectToHashMap(proceedingJoinPointArg));
            }
        }
        log.info("请求开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("请求开始===接口:" + requestUrl);
        log.info("请求开始===方法:" + requestMethod);
        log.info("请求开始===参数:" + params);

        // result的值就是被拦截方法的返回值
        Object result = proceedingJoinPoint.proceed();
        log.info("请求结束===返回值:" + result);
        log.info("请求结束<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return result;
    }


}
