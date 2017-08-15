/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Druid配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-21 0:00
 * @since garnet-core-be-fe 1.0.0
 */
@Configuration
public class DruidConfig {

    /**
     * The Logger.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    /**
     * The Db url.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.url:#{null}}")
    private String dbUrl;

    /**
     * The Username.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.username: #{null}}")
    private String username;

    /**
     * The Password.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.password:#{null}}")
    private String password;

    /**
     * The Driver class name.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.driverClassName:#{null}}")
    private String driverClassName;

    /**
     * The Initial size.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.initialSize:#{null}}")
    private Integer initialSize;

    /**
     * The Min idle.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.minIdle:#{null}}")
    private Integer minIdle;

    /**
     * The Max active.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.maxActive:#{null}}")
    private Integer maxActive;

    /**
     * The Max wait.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.maxWait:#{null}}")
    private Integer maxWait;

    /**
     * The Time between eviction runs millis.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis:#{null}}")
    private Integer timeBetweenEvictionRunsMillis;

    /**
     * The Min evictable idle time millis.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.minEvictableIdleTimeMillis:#{null}}")
    private Integer minEvictableIdleTimeMillis;

    /**
     * The Validation query.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.validationQuery:#{null}}")
    private String validationQuery;

    /**
     * The Test while idle.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.testWhileIdle:#{null}}")
    private Boolean testWhileIdle;

    /**
     * The Test on borrow.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.testOnBorrow:#{null}}")
    private Boolean testOnBorrow;

    /**
     * The Test on return.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.testOnReturn:#{null}}")
    private Boolean testOnReturn;

    /**
     * The Pool prepared statements.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.poolPreparedStatements:#{null}}")
    private Boolean poolPreparedStatements;

    /**
     * The Max pool prepared statement per connection size.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize:#{null}}")
    private Integer maxPoolPreparedStatementPerConnectionSize;

    /**
     * The Filters.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("${spring.datasource.filters:#{null}}")
    private String filters;

    /**
     * The Connection properties.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Value("{spring.datasource.connectionProperties:#{null}}")
    private String connectionProperties;

    /**
     * Data source data source.
     *
     * @return the data source
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean
    @Primary
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        if(initialSize != null) {
            datasource.setInitialSize(initialSize);
        }
        if(minIdle != null) {
            datasource.setMinIdle(minIdle);
        }
        if(maxActive != null) {
            datasource.setMaxActive(maxActive);
        }
        if(maxWait != null) {
            datasource.setMaxWait(maxWait);
        }
        if(timeBetweenEvictionRunsMillis != null) {
            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }
        if(minEvictableIdleTimeMillis != null) {
            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }
        if(validationQuery!=null) {
            datasource.setValidationQuery(validationQuery);
        }
        if(testWhileIdle != null) {
            datasource.setTestWhileIdle(testWhileIdle);
        }
        if(testOnBorrow != null) {
            datasource.setTestOnBorrow(testOnBorrow);
        }
        if(testOnReturn != null) {
            datasource.setTestOnReturn(testOnReturn);
        }
        if(poolPreparedStatements != null) {
            datasource.setPoolPreparedStatements(poolPreparedStatements);
        }
        if(maxPoolPreparedStatementPerConnectionSize != null) {
            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        }
        if(connectionProperties != null) {
            datasource.setConnectionProperties(connectionProperties);
        }

        List<Filter> filters = new ArrayList<>();
        filters.add(statFilter());
        filters.add(wallFilter());
        datasource.setProxyFilters(filters);

        return datasource;
    }

    /**
     * Druid servlet servlet registration bean.
     *
     * @return the servlet registration bean
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }

    /**
     * Stat filter stat filter.
     *
     * @return the stat filter
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        statFilter.setSlowSqlMillis(1000);

        return statFilter;
    }

    /**
     * Wall filter wall filter.
     *
     * @return the wall filter
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();

        //允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);

        return wallFilter;
    }

}
