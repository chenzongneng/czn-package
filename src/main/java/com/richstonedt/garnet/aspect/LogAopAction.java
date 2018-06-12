package com.richstonedt.garnet.aspect;
import java.lang.reflect.Method;
import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;

import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.interceptory.LogRequired;
import com.richstonedt.garnet.model.Log;
import com.richstonedt.garnet.model.LogEntity;
import com.richstonedt.garnet.service.CommonService;
import com.richstonedt.garnet.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAopAction {

    @Autowired
    private LogService logService;

    @Autowired
    private CommonService commonService;

//    @Pointcut("execution(* com.richstonedt.garnet.controller..*.*(..))")
    @Pointcut("execution(* com.richstonedt.garnet.service.impl..*.*(..))")
    private void controllerAspect(){}

    @After("controllerAspect()")
    public boolean after(JoinPoint joinPoint) throws Throwable {

        Log log = new Log();
        //获取登录用户账户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String cookie = request.getHeader("Cookie");

        if (StringUtils.isEmpty(cookie)) {
            return false;
        }

        String userName = this.getCookieValue(cookie, "userName");
        log.setUserName(userName);
        //获取系统时间
        Long time = System.currentTimeMillis();
        log.setCreatedTime(time);
        log.setModifiedTime(time);

        // 拦截的实体类，就是当前正在执行的controller
        Object target = joinPoint.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = joinPoint.getSignature().getName();

        // 拦截的方法参数
        Object[] args = joinPoint.getArgs();

        // 拦截的放参数类型
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();

//        Object object = null;
        // 获得被拦截的方法
        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        if (null != method) {
            // 判断是否包含自定义的注解
            if (method.isAnnotationPresent(LogRequired.class)) {
                LogRequired logRequired = method.getAnnotation(LogRequired.class);
                String module = logRequired.module();
                String method1 = logRequired.method();
                String ip = InetAddress.getLocalHost().getHostAddress();
                log.setMessage(module);
                log.setOperation(method1);
                log.setIp(ip);
                log.setId(IdGeneratorUtil.generateId());

                //将操作日志插入数据库
                logService.insertSelective(log);
            } else {//没有包含注解
                System.out.println("没有包含注解...");
                return false;
            }
        } else { //不需要拦截直接执行
            System.out.println("方法是空的...");
            return false;
        }
        return true;
    }

    /**
     * 获取Cookie中存的值
     * @param cookie
     * @param name
     * @return
     */
    private String getCookieValue(String cookie, String name) {

        String n = name + "=";

        String[] c = cookie.split(";");

        for (String s : cookie.split(";")) {
            String s1 = s.trim();
            if (s1.indexOf(n) == 0) {
                return s1.substring(n.length(), s1.length());
            }
        }

        return "";
    }

}
