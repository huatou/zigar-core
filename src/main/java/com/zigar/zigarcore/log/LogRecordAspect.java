package com.zigar.zigarcore.log;//package com.huatou.system.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过配置切面打印请求日志
 */

@Aspect
@Component
public class LogRecordAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);

    // 定义切点Pointcut
    @Pointcut("execution(* com.zigar.*.controller.*.*(..))")
    public void logControllerUrlAndParams() {
    }

    // 定义切点Pointcut
    @Pointcut("execution(* com.zigar.*.controller.*.*(..))")
    public void logRestControllerUrlAndParams() {
    }


    @Around("logControllerUrlAndParams() || logRestControllerUrlAndParams()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        ObjectMapper objectMapper = new ObjectMapper();

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String contentType = request.getContentType();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        Object[] args = pjp.getArgs();
        Map<Object, Object> params = new HashMap<>();
        //获取请求参数集合并进行遍历拼接
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            if (o instanceof HttpServletRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) o;
                Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
                Map<String, String> parameterStrMap = new HashMap<>();
                parameterMap.forEach((a, b) -> {
                    parameterStrMap.put(a, b[0]);
                });
                params.putAll(parameterStrMap);
            } else if (o instanceof HttpServletResponse) {
                continue;
            } else {
                Object queryEntity = o;
                params.putAll(getKeyAndValue(queryEntity));
            }
        }
        logger.info("请求开始================================");
        logger.info("请求开始===地址:" + url);
        logger.info("请求开始===类型:" + method);
        logger.info("请求开始===参数:" + params);

        // result的值就是被拦截方法的返回值
        Object result = null;
        result = pjp.proceed();

        logger.info("请求结束===返回值:" + result);
        return result;
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {
                // 得到此属性的值
                Object val = f.get(obj);
                if (val != null) {
                    map.put(f.getName(), val);// 设置键值
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }
}
