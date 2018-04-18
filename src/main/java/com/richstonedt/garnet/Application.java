/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import javax.sql.DataSource;

/**
 * <b><code>Application</code></b>
 * <p/>
 * 程序入口
 * <p/>
 * <b>Creation Time:</b> 2017/8/8 11:01.
 *
 * @author chenzechao
 * @version $Revision$ 2017/8/8 11:01.
 * @since garnet-core-be-fe 1.0.0
 */
@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
//@MapperScan(value = "com.richstonedt.garnet.mapper")
@EnableAspectJAutoProxy
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@ComponentScan(basePackages = {"com.richstonedt.garnet.controller","com.richstonedt.garnet.mapper","com.richstonedt.garnet.service.impl","com.richstonedt.garnet.service"})
public class Application extends SpringBootServletInitializer {


    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    /**
     * Configure spring application builder.
     *
     * @param application the application
     * @return the spring application builder
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @since garnet-core-be-fe 1.0.0
     */
    public static void main(String[] args) {
        LOG.info("Application Start!");
        SpringApplication.run(Application.class, args);
    }
//
//    @Autowired
//    private Environment env;
//
    //destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
//    @Bean
//    public DataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl("jdbc:oracle:thin:@192.168.0.10:1521:whxy");
//        dataSource.setUsername("ming");//用户名
//        dataSource.setPassword("123456");//密码
//        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
//        dataSource.setInitialSize(2);//初始化时建立物理连接的个数
//        dataSource.setMaxActive(20);//最大连接池数量
//        dataSource.setMinIdle(0);//最小连接池数量
//        dataSource.setMaxWait(60000);//获取连接时最大等待时间，单位毫秒。
//        dataSource.setPoolPreparedStatements(false);//是否缓存preparedStatement，也就是PSCache
//        return dataSource;
//    }


//    @Bean
//    public DataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl("jdbc:postgresql://192.168.0.157:5432/garnet");
//        dataSource.setUsername("postgres");//用户名
//        dataSource.setPassword("root");//密码
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setInitialSize(2);//初始化时建立物理连接的个数
//        dataSource.setMaxActive(20);//最大连接池数量
//        dataSource.setMinIdle(0);//最小连接池数量
//        dataSource.setMaxWait(60000);//获取连接时最大等待时间，单位毫秒。
//        dataSource.setPoolPreparedStatements(false);//是否缓存preparedStatement，也就是PSCache
//        return dataSource;
//    }

}
