/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.config;

import com.richstonedt.garnet.common.oauth2.OAuth2Filter;
import com.richstonedt.garnet.common.oauth2.OAuth2Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-20 18:33
 * @since garnet-core-be-fe 1.0.0
 */
@Configuration
public class ShiroConfig {

    /**
     * Session manager session manager.
     *
     * @return the session manager
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean("sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager;
    }

    /**
     * Security manager security manager.
     *
     * @param oAuth2Realm    the o auth 2 realm
     * @param sessionManager the session manager
     * @return the security manager
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * Shir filter shiro filter factory bean.
     *
     * @param securityManager the security manager
     * @return the shiro filter factory bean
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2Filter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/webjars/**", "anon");
//        filterMap.put("/druid/**", "anon");
//        filterMap.put("/api/**", "anon");
        filterMap.put("/v1.0/login", "anon");
        filterMap.put("/v1.0/sysMenu", "anon");
        filterMap.put("/**/*.css", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/**/*.js", "anon");
        filterMap.put("/**/*.html", "anon");
        filterMap.put("/fonts/**", "anon");
        filterMap.put("/plugins/**", "anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/v1.0/kaptcha", "anon");
        filterMap.put("/test/**", "anon");
        filterMap.put("/", "anon");
//        filterMap.put("/v1.0/**", "anon");
        filterMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    /**
     * Lifecycle bean post processor lifecycle bean post processor.
     *
     * @return the lifecycle bean post processor
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Default advisor auto proxy creator default advisor auto proxy creator.
     *
     * @return the default advisor auto proxy creator
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * Authorization attribute source advisor authorization attribute source advisor.
     *
     * @param securityManager the security manager
     * @return the authorization attribute source advisor
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
