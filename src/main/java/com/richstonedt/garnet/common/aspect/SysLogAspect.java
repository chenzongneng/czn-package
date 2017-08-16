/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.aspect;

import com.google.gson.Gson;
import com.richstonedt.garnet.common.annotation.SysLog;
import com.richstonedt.garnet.common.utils.HttpContextUtils;
import com.richstonedt.garnet.common.utils.IPUtils;
import com.richstonedt.garnet.modules.sys.entity.SysLogEntity;
import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import com.richstonedt.garnet.modules.sys.service.SysLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志，切面处理类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年3月8日 上午11:07:35
 * @since garnet-core-be-fe 1.0.0
 */
@Aspect
@Component
public class SysLogAspect {

	/**
	 * The Sys log service.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysLogService sysLogService;

	/**
	 * The LOG.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private static Logger LOG = LoggerFactory.getLogger(SysLogAspect.class);

	/**
	 * Log point cut.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Pointcut("@annotation(com.richstonedt.garnet.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	/**
	 * Around object.
	 *
	 * @param point the point
	 * @return the object
	 * @throws Throwable the throwable
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time);

		return result;
	}

	/**
	 * Save sys log.
	 *
	 * @param joinPoint the join point
	 * @param time      the time
	 *                  @since garnet-core-be-fe 1.0.0
	 */
	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLogEntity sysLog = new SysLogEntity();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){
			LOG.error(e.getMessage());
		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		//用户名
		String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
		sysLog.setUsername(username);

		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		//保存系统日志
		sysLogService.save(sysLog);
	}

	
}
