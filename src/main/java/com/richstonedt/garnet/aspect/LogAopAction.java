package com.richstonedt.garnet.aspect;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
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

    @Pointcut("execution(* com.richstonedt.garnet.service.impl..*.*(..))")
    private void controllerAspect(){}

    @After("controllerAspect()")
    public boolean after(JoinPoint joinPoint) {

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
                String ip = null;
                try {
                    ip = this.getLinuxLocalIp();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
//                try {
//                    ip = InetAddress.getLocalHost().getHostAddress();
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                }
                log.setMessage(module);
                log.setOperation(method1);
                log.setIp(ip);
                log.setId(IdGeneratorUtil.generateId());

                //将操作日志插入数据库
                logService.insertSelective(log);
            } else {//没有包含注解
                return false;
            }
        } else { //不需要拦截直接执行
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

    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    private String getLinuxLocalIp() throws SocketException {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                                System.out.println(ipaddress);
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("获取ip地址异常");
            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        System.out.println("IP:"+ip);
        return ip;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
