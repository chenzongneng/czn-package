**项目说明** 
- **Garnet安全管理模块为独立的安全管理模块，负责管理系统用户、角色、权限以及用户日志等的单点登录系统。系统包括了前后端，其中后端使用了Springboot搭建，前端基于Vue2框架进行开发。**

**具有如下特点** 
- 实现前后端分离，通过token进行数据交互，前端再也不用关注后端技术
- 灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
- 引入quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复及日志查看等功能
- 引入API模板，根据token作为登录令牌，极大的方便了APP接口开发
- 引入Hibernate Validator校验框架，轻松实现后端校验
- 引入swagger文档支持，方便编写API接口文档
- 引入路由机制，刷新页面会停留在当前页

**数据权限设计思想** 
- 管理员管理、角色管理、部门管理，可操作本部门及子部门数据
- 菜单管理、定时任务、参数管理、系统日志，没有数据权限
- 业务功能，按照用户数据权限，查询、操作数据
- 没有本部门数据权限，也能查询、操作本人数据

**项目结构** 
```
garnet
├─SQL  项目SQL语句
│
├─common 公共模块
│  ├─aspect 系统日志
│  ├─exception 异常处理
│  ├─validator 后台校验
│  └─xss XSS过滤
│ 
├─config 配置信息
│ 
├─modules 功能模块
│  ├─api API接口模块(APP调用)
│  ├─job 定时任务模块
│  └─sys 权限模块
│ 
├─Application 项目启动类
│  
├─resources
   ├─mapper SQL对应的XML文件
   ├─static 第三方库、插件等静态资源
   └─views  项目静态页面
```

**技术选型：** 
- 核心框架：Spring Boot 1.5
- 安全框架：Apache Shiro 1.3
- 视图框架：Spring MVC 4.3
- 持久层框架：MyBatis 3.3
- 定时器：Quartz 2.3
- 数据库连接池：Druid 1.0
- 日志管理：SLF4J 1.7、Log4j
- 页面交互：Vue2.x

 **本地部署**
- 下载源码
- 使用Postgres，创建数据库garnet，数据库编码为UTF-8
- 执行doc/db_postgre.sql文件，初始化数据
- 修改application-dev.yml的数据库账号和密码
- 运行Application.java，则可启动项目
- 项目访问路径：http://localhost:8082/garnet
- 账号密码：admin/admin
- Swagger路径：http://localhost:8082/garnet/swagger/index.html