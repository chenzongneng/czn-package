/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.log.config;

import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.modules.sys.entity.UserLoginEntity;
import com.richstonedt.garnet.system.log.entity.LogEntity;
import com.richstonedt.garnet.system.log.entity.OperationEntity;
import com.richstonedt.garnet.system.log.service.LogOperationService;
import com.richstonedt.garnet.system.log.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b><code>LogAop</code></b>
 * <p> 对controller 中所有的方法进行切面
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/20 12:13
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since gempile-core-cs 0.1.0
 */
@Aspect
@Component
public class LogAopConfig {

    /**
     * The Operation service.
     *
     * @since gempile-core-cs 1.0.0
     */
    @Autowired
    private LogOperationService operationService;

    /**
     * The Log service.
     *
     * @since gempile-core-cs 1.0.0
     */
    @Autowired
    private LogService logService;

    /**
     * The Operations.
     *
     * @since gempile-core-cs 1.0.0
     */
    private static List<OperationEntity> operations;

    /**
     * Login aspect.
     *
     * @param point       the point
     * @param returnValue the return value
     * @since gempile-core-cs 1.0.0
     */
    @AfterReturning(pointcut = "execution(* com.richstonedt.garnet.modules.sys.controller.SysLoginController.login(..))",
            returning = "returnValue")
    public void loginAspect(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        Result result = (Result) returnValue;
        UserLoginEntity user = (UserLoginEntity) args[1];
        Object garnetToken = result.get("garnetToken");
        if (garnetToken == null) {
            return;
        }
        // 用户登录成功
        String userName = user.getUsername();
        LogEntity log = getLog();
        log.setUserName(userName);
        log.setOperation(userName + "登录系统");
        logService.saveLog(log);
    }

    /**
     * Get log.
     *
     * @since gempile-core-cs 0.1.0
     */
    public LogEntity getLog() {
        // get HttpServletRequest
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        //get log info
        String userName = request.getParameter("userName");
        String method = request.getMethod();
        String url = request.getRequestURI();
        String operation = getOperation(method, url);
        String IP = request.getRemoteHost();
        //set log info
        LogEntity log = new LogEntity();
        log.setUserName(userName);
        log.setMethod(method);
        log.setOperation(operation);
        log.setUrl(url);
        log.setIp(IP);
        //get this request SQL
        Map<String, List<String>> sourceSql = SqlConfig.getSqlWithRequest(url + method);
        String finalSql = decorateSql(sourceSql);
        log.setSql(finalSql);
        return log;
    }

    /**
     * Gets operation.
     *
     * @param method the method
     * @param url    the url
     * @return the operation
     */
    private String getOperation(String method, String url) {
        if (CollectionUtils.isEmpty(operations)) {
            operations = operationService.getAllOperations();
        }
        List<OperationEntity> matchedOperations = new ArrayList<>();
        for (OperationEntity operation : operations) {
            if (url.contains(operation.getUrl()) && method.toUpperCase().equals(operation.getMethod().toUpperCase())) {
                matchedOperations.add(operation);
            }
        }
        String operation;
        if (matchedOperations.size() == 1) {
            operation = matchedOperations.get(0).getOperation();
        } else {
            operation = "没有匹配到具体操作";
        }
        return operation;
    }

    /**
     * Decorate sql string.
     *
     * @param maps the list
     * @return the string
     * @since gempile-core-cs 1.0.0
     */
    private String decorateSql(Map<String, List<String>> maps) {
        List<String> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(maps)) {
            Set<Map.Entry<String, List<String>>> entrySets = maps.entrySet();
            for (Map.Entry<String, List<String>> entrySet : entrySets) {
                List<String> value = entrySet.getValue();
                List<String> tmpList = new ArrayList<>();
                for (int i = 0; i < value.size(); i++) {
                    if ((i + 1) % 3 == 0) {
                        resultList.add(formatOneSql(tmpList));
                        tmpList.clear();
                    } else {
                        if ((value.get(i).contains("Preparing") || value.get(i).contains("Parameters"))
                                && !value.get(i).contains("gar_operation") && !value.get(i).contains("gar_log")) {
                            tmpList.add(value.get(i));
                        }
                    }
                }
            }
        }
        StringBuilder result = new StringBuilder();
        for (String str : resultList) {
            result.append(str);
        }
        return result.toString();
    }

    /**
     * Format one sql string.
     *
     * @param list the sql
     * @return the string
     * @since gempile-core-cs 1.0.0
     */
    private String formatOneSql(List<String> list) {
        if (CollectionUtils.isEmpty(list) || list.size() != 2) {
            return "";
        }
        String prepareSql = list.get(0);
        if (!prepareSql.contains(";")) {
            prepareSql += ";";
        }
        String parametersSql = list.get(1);
        prepareSql = prepareSql.replace("==>  Preparing:", "");
        parametersSql = parametersSql.replace("==> Parameters:", "");
        List<String> parametersList = new ArrayList<>();
        if (parametersSql.contains(",")) {
            String tmpArray[] = parametersSql.split(",");
            for (String value : tmpArray) {
                parametersList.add(removeStr(value));
            }
        } else {
            parametersList.add(removeStr(parametersSql));
        }
        for (String parameter : parametersList) {
            prepareSql = prepareSql.replaceFirst("\\?", parameter);
        }
        return prepareSql;
    }

    /**
     * Remove str string.
     *
     * @param name the name
     * @return the string
     * @since gempile-core-cs 1.0.0
     */
    private String removeStr(String name) {
        int start = 0;
        if (name.contains("(")) {
            start = name.indexOf("(");
        }
        if (start != 0) {
            return name.substring(0, start);
        } else {
            return name;
        }
    }
}
