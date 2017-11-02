/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.config.log;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.richstonedt.garnet.model.GarLog;
import com.richstonedt.garnet.model.GarLogOperation;
import com.richstonedt.garnet.model.view.model.GarLoginResult;
import com.richstonedt.garnet.model.view.model.GarUserLogin;
import com.richstonedt.garnet.service.GarLogOperationService;
import com.richstonedt.garnet.service.GarLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b><code>GarLogAopConfig</code></b>
 * <p> 对controller 中所有的方法进行切面
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/20 12:13
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
//@Aspect
@Component
public class GarLogAopConfig {

    /**
     * The Operation service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private GarLogOperationService operationService;

    /**
     * The Log service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private GarLogService logService;

    /**
     * The Operations.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static List<GarLogOperation> operations;

    /**
     * The constant ONE_VALUE.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final int ONE_VALUE = 1;

    /**
     * The constant TWO_VALUE.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final int TWO_VALUE = 2;

    /**
     * The constant PARENTHESES_LEFT.
     * 左边括号
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final String PARENTHESES_LEFT = "(";

    /**
     * The constant SEMICOLON.
     * 分号
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final String SEMICOLON = ";";

    /**
     * The constant COMMA.
     * 逗号
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final String COMMA = ",";

    /**
     * Login aspect.
     * 登录接口单独处理
     *
     * @param point       the point
     * @param returnValue the return value
     * @since garnet-core-be-fe 1.0.0
     */
    @AfterReturning(pointcut = "execution(* com.richstonedt.garnet.controller.GarLoginController.login(..))",
            returning = "returnValue")
    public void loginAspect(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        ResponseEntity responseEntity = (ResponseEntity) returnValue;
        GarLoginResult loginResult = (GarLoginResult) responseEntity.getBody();
        if (StringUtils.isEmpty(loginResult.getGarnetToken())) {
            // 登录失败
            return;
        }
        // 用户登录成功
        GarUserLogin userLogin = (GarUserLogin) args[1];
        String userName = userLogin.getUserName();
        GarLog log = getLog();
        log.setUserName(userName);
        log.setOperation(userName + "登录系统");
        logService.save(log);
    }

    /**
     * Controller aspect.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @After("execution(* com.richstonedt.garnet.controller..*(..))")
    public void controllerAspect(JoinPoint point) {
        String loginName = "login";
        String methodName = point.getSignature().getName();
        // 登录方法由上面单独拦截处理
        if (!loginName.equals(methodName)) {
            logService.save(getLog());
        }
    }

    /**
     * Get log.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public GarLog getLog() {
        // get HttpServletRequest
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        //get log info
        String gempileToken = request.getHeader("gempileToken");
        String userName = "";
        if (!StringUtils.isEmpty(gempileToken)) {
            try {
                userName = JWT.decode(gempileToken).getClaim("una").asString();
            } catch (JWTDecodeException e) {
                userName = "";
            }
        }
        String method = request.getMethod();
        String url = request.getRequestURI();
        String operation = getOperation(method, url);
        String ip = request.getRemoteHost();
        //set log info
        GarLog log = new GarLog();
        log.setUserName(userName);
        log.setMethod(method);
        log.setOperation(operation);
        log.setUrl(url);
        log.setIp(ip);
        //get this request SQL
        Map<String, List<String>> sourceSql = GarSqlConfig.getSqlWithRequest(url + method);
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
     * @since garnet-core-be-fe 1.0.0
     */
    private String getOperation(String method, String url) {
        if (CollectionUtils.isEmpty(operations)) {
            operations = operationService.getAllOperations();
        }
        List<GarLogOperation> matchedOperations = new ArrayList<>();
        for (GarLogOperation operation : operations) {
            if (url.contains(operation.getUrl()) && method.toUpperCase().equals(operation.getMethod().toUpperCase())) {
                matchedOperations.add(operation);
            }
        }
        String operation;
        if (matchedOperations.size() == ONE_VALUE) {
            operation = matchedOperations.get(0).getOperation();
        } else if (matchedOperations.size() == TWO_VALUE) {
            String url1 = matchedOperations.get(0).getUrl();
            String url2 = matchedOperations.get(1).getUrl();
            String operation1 = matchedOperations.get(0).getOperation();
            String operation2 = matchedOperations.get(1).getOperation();
            operation = url1.contains(url2) ? operation1 : operation2;
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
     * @since garnet-core-be-fe 1.0.0
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
                        boolean isNeedLog = (value.get(i).contains("Preparing") || value.get(i).contains("Parameters") ||
                                (value.get(i).contains("gar_log") && value.get(i).toUpperCase().contains("SELECT"))) && !value.get(i).contains("gar_operation");
                        if (isNeedLog) {
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
     * @since garnet-core-be-fe 1.0.0
     */
    private String formatOneSql(List<String> list) {
        if (CollectionUtils.isEmpty(list) || list.size() != TWO_VALUE) {
            return "";
        }
        String prepareSql = list.get(0);
        if (!prepareSql.contains(SEMICOLON)) {
            prepareSql += SEMICOLON;
        }
        String parametersSql = list.get(1);
        prepareSql = prepareSql.replace("==>  Preparing:", "");
        parametersSql = parametersSql.replace("==> Parameters:", "");
        List<String> parametersList = new ArrayList<>();
        if (parametersSql.contains(COMMA)) {
            String[] tmpArray = parametersSql.split(COMMA);
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
     * @since garnet-core-be-fe 1.0.0
     */
    private String removeStr(String name) {
        int start = 0;
        if (name.contains(PARENTHESES_LEFT)) {
            start = name.indexOf(PARENTHESES_LEFT);
        }
        if (start != 0) {
            return name.substring(0, start);
        } else {
            return name;
        }
    }
}
