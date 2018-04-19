
/* Drop Tables */

DROP TABLE  gar_application_tenants;
DROP TABLE  gar_applications;
DROP TABLE  gar_group_roles;
DROP TABLE  gar_group_users;
DROP TABLE  gar_groups;
DROP TABLE  gar_role_permissions;
DROP TABLE  gar_permissions;
DROP TABLE  gar_resources;
DROP TABLE  gar_resource_dynamic_props;
DROP TABLE  gar_roles;
DROP TABLE  gar_router_group;
DROP TABLE  gar_system_config;
DROP TABLE  gar_user_tenants;
DROP TABLE  gar_tenants;
DROP TABLE  gar_tokens;
DROP TABLE  gar_user_credentials;
DROP TABLE  gar_users;
DROP TABLE  gar_user_tenant_applications;



/* Create Tables */

CREATE TABLE gar_applications
(
  id number(15) not null primary key,
  name varchar(256),
  company varchar(256),
  -- 调用接口时应用唯一标识
  app_code varchar(256) NOT NULL,
  refresh_resources_api varchar(256) DEFAULT ' ' NOT NULL,
  -- 此application所在的所有ip:ports，用分号分隔。如: 192.168.6.97:8080;192.168.6.98:8080
  --
  -- 加入已经作为微服务加入服务注册中心，无需填写此字段。系统会自动把app_code当做服务id，通过负载均衡器在服务中心找到对应服务。
  hosts varchar(256) DEFAULT ' ' NOT NULL,
  created_time number(15) DEFAULT 0 NOT NULL,
  modified_time number(15) DEFAULT 0 NOT NULL,
  status int DEFAULT 1 NOT NULL,
  -- 属于paas服务还是saas服务
  service_mode varchar(5) DEFAULT 'paas' NOT NULL,
  -- 更新的人
  updated_by_user_name varchar(100) DEFAULT ' ' NOT NULL
) ;


CREATE TABLE gar_application_tenants
(
  application_id number(15) NOT NULL,
  tenant_id number(15) NOT NULL,
  id number(15) not null primary key
) ;


CREATE TABLE gar_groups
(
  id number(15) not null primary key,
  name varchar(256) not null,
  created_time number(15) DEFAULT 0 NOT NULL,
  modified_time number(15) DEFAULT 0 NOT NULL,
  application_id number(15) DEFAULT 0 NOT NULL,
  tenant_id number(15) DEFAULT 0 NOT NULL,
  status int DEFAULT 1 NOT NULL,
  -- 更新的人
  updated_by_user_name varchar(100) DEFAULT ' ' NOT NULL
) ;


CREATE TABLE gar_group_roles
(
  role_id number(15) NOT NULL,
  group_id number(15) NOT NULL,
  id number(15) not null primary key
) ;


CREATE TABLE gar_group_users
(
  group_id number(15) NOT NULL,
  user_id number(15) NOT NULL,
  id number(15) NOT NULL
) ;


CREATE TABLE gar_permissions
(
  id number(15) not null primary key,
  resource_path_wildcard varchar(256) DEFAULT ' ' NOT NULL,
  name varchar(256) DEFAULT ' ' NOT NULL,
  description varchar(250) DEFAULT ' ' NOT NULL,
  created_time number(15) DEFAULT 0 NOT NULL,
  modified_time number(15) DEFAULT 0 NOT NULL,
  application_id number(15) DEFAULT 0 NOT NULL,
  tenant_id number(15) DEFAULT 0 NOT NULL,
  status int DEFAULT 1 NOT NULL,
  -- 更新的人
  updated_by_user_name varchar(100) DEFAULT ' ' NOT NULL
) ;


CREATE TABLE gar_resources
(
  id number(15) not null primary key,
  application_id number(15) DEFAULT 0 NOT NULL,
  -- ui - UI组件
  -- /{page_name}/{element_name}
  --
  -- openApi - open api，对外开放的api，并非内部使用的api;
  --
  -- /api/v1/users
  -- 此时需要与dynamic properties配合，假如没有注册中心，直接指定某个property为ip:port;假如使用注册中心，直接指定某个property为service id
  --
  -- function - function opint,对应的是功能项，例如用户查询；
  -- 例如
  -- 一个具体的页面：
  -- /{page_name}
  --
  -- 某一个页面中的某个功能，例如用户组管理页面中的增加用户组的功能
  --
  -- /{page_name}/{function_name}
  path varchar(256) DEFAULT ' ' NOT NULL,
  actions varchar(256) DEFAULT ' ' NOT NULL,
  name varchar(256) DEFAULT ' ' NOT NULL,
  created_time number(15) DEFAULT 0 NOT NULL,
  modified_time number(15) DEFAULT 0 NOT NULL,
  -- 资源类型例如，
  -- ui-UI组件，如button, input, textarea, table, drop down list等
  -- openApi-OpenApi, 如提供给其他应用调用的REST API。不是指应用内部的REST API
  -- function-功能项，如页面，菜单模块等
  type varchar(50) DEFAULT ' ' NOT NULL,
  tenant_id number(15) DEFAULT 0 NOT NULL,
  varchar_00 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_01 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_02 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_03 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_04 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_05 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_06 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_07 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_08 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_09 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_10 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_11 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_12 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_13 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_14 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_15 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_16 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_17 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_18 varchar(256) DEFAULT ' ' NOT NULL,
  varchar_19 varchar(256) DEFAULT ' ' NOT NULL,
  int_01 int,
  int_02 int,
  int_03 int,
  int_04 int,
  int_05 int,
  boolean_01 NUMBER(1),
  boolean_02 NUMBER(1),
  boolean_03 NUMBER(1),
  boolean_04 NUMBER(1),
  -- 更新的人
  updated_by_user_name varchar(100) DEFAULT ' ' NOT NULL
) ;


CREATE TABLE gar_resource_dynamic_props
(
  id number(15) not null primary key,
  type varchar(50) DEFAULT '0' NOT NULL,
  application_id number(15) DEFAULT 0 NOT NULL,
  filed_name varchar(256) DEFAULT ' ' NOT NULL,
  description varchar(256) DEFAULT ' ' NOT NULL
) ;


CREATE TABLE gar_roles
(
  id number(15) not null primary key,
  name varchar(256) DEFAULT ' ' NOT NULL,
  remark varchar(256) DEFAULT ' ' NOT NULL,
  created_time number(15) DEFAULT 0 NOT NULL,
  modified_time number(15) DEFAULT 0 NOT NULL,
  tenant_id number(15) DEFAULT 0 NOT NULL,
  application_id number(15) DEFAULT 0 NOT NULL,
  status int DEFAULT 1 NOT NULL,
  -- 更新的人
  updated_by_user_name varchar(100) DEFAULT ' ' NOT NULL
) ;


CREATE TABLE gar_role_permissions
(
  role_id number(15) NOT NULL,
  permission_id number(15) NOT NULL,
  id number(15) not null primary key
) ;


CREATE TABLE gar_router_group
(
  id number(15) NOT NULL,
  group_name varchar(256) NOT NULL,
  app_code varchar(256) NOT NULL
) ;


CREATE TABLE gar_system_config
(
  id number(15) not null primary key,
  parameter varchar(256),
  value varchar(256)
) ;


CREATE TABLE gar_tenants
(
  id number(15) not null primary key,
  name varchar(256) NOT NULL ,
  created_time number(15) DEFAULT 0 NOT NULL,
  modified_time number(15) DEFAULT 0 NOT NULL,
  description varchar(250) DEFAULT ' ' NOT NULL,
  status int DEFAULT 1 NOT NULL,
  -- 属于paas服务还是saas服务
  service_mode varchar(5) DEFAULT 'paas' NOT NULL,
  -- 更新的人
  updated_by_user_name varchar(100) DEFAULT ' ' NOT NULL
) ;


CREATE TABLE gar_tokens
(
  id number(15) not null primary key,
  token varchar(256) NOT NULL ,
  user_name varchar(256) NOT NULL ,
  router_group_name varchar(256),
  created_time number(15) DEFAULT 0 NOT NULL,
  modified_time number(15) DEFAULT 0 NOT NULL,
  expire_time number(15) DEFAULT 0 NOT NULL
) ;


CREATE TABLE gar_users
(
  id number(15) not null primary key,
  user_name varchar(256) NOT NULL ,
  created_time number(15) DEFAULT 0 NOT NULL,
  modified_time number(15) DEFAULT 0 NOT NULL,
  mobile_number varchar(256) DEFAULT ' ' NOT NULL,
  email varchar(256) DEFAULT ' ' NOT NULL,
  status int DEFAULT 1 NOT NULL,
  belong_to_garnet varchar(5) NOT NULL,
  -- 更新的人
  updated_by_user_name varchar(100) DEFAULT ' ' NOT NULL
) ;


CREATE TABLE gar_user_credentials
(
  id number(15) not null primary key,
  created_time number(15) DEFAULT 0 NOT NULL,
  modified_time number(15) DEFAULT 0 NOT NULL,
  credential varchar(256) DEFAULT ' ' NOT NULL,
  expired_date_time number(15) DEFAULT 0 NOT NULL,
  user_id number(15) NOT NULL
) ;


CREATE TABLE gar_user_tenants
(
  user_id number(15) NOT NULL,
  tenant_id number(15) NOT NULL,
  id number(15) not null primary key
) ;


CREATE TABLE gar_user_tenant_applications
(
  id number(15) DEFAULT 0 not null primary key,
  user_id number(15),
  tenant_id number(15),
  application_id number(15),
  status int DEFAULT 1 NOT NULL
) ;

COMMIT ;



-- 初始化数据
-- gar_system_config
INSERT INTO gar_system_config VALUES (1, 'mode', 'all');

-- gar_users
INSERT INTO gar_users(id, user_name, created_time, modified_time, status, belong_to_garnet) VALUES ('1', 'admin', '1522252800000', '1522252800000', '1', 'Y');

-- gar_user_credentials
INSERT INTO gar_user_credentials(id, created_time, modified_time, credential, expired_date_time, user_id) VALUES ('1', '1522252800000', '1522252800000', 'admin', '7959830400000', '1');

-- gar_tenants
INSERT INTO gar_tenants(id, name, created_time, modified_time, description, status, service_mode) VALUES ('1', 'garnet', '1522252800000', '1522252800000', '超级租户', '1', 'paas');

-- gar_user_tenants
INSERT INTO gar_user_tenants(user_id, tenant_id, id) VALUES ('1', '1', '1');

-- gar_user_application
INSERT INTO gar_applications(id, name, company, app_code, refresh_resources_api, hosts, created_time, modified_time, status, service_mode) VALUES ('1', 'garnet', 'garnet', 'garnet', ' ', ' ', '1522252800000', '1522252800000', '1', 'paas');

-- gar_application_tenants
INSERT INTO gar_application_tenants(application_id, tenant_id, id) VALUES ('1', '1', '1');

-- gar_groups
INSERT INTO gar_groups(id, name, created_time, modified_time, application_id, tenant_id, status) VALUES ('1', '超级组', '1522252800000', '1522252800000', '1', '1', '1');

-- gar_roles
INSERT INTO gar_roles(id, name, remark, created_time, modified_time, tenant_id, application_id, status) VALUES('1', '超级角色', '超级角色', '1522252800000', '1522252800000', '1', '1', '1');

-- gar_permissions
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, status) VALUES ('1', '%', '超级权限', '超级权限', '1522252800000', '1522252800000', '1', '1','1');

-- gar_role_permissions
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '1', '1');

-- gar_group_role
INSERT INTO gar_group_roles(role_id, group_id, id) VALUES ('1', '1', '1');

-- gar_group_user
INSERT INTO gar_group_users(group_id, user_id, id) VALUES ('1', '1', '1');

-- gar_resource_dynamic_props
-- appCode
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('1', 'garnet_appCode', '0', 'varchar00', '填 code name');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('2', 'garnet_appCode', '0', 'varchar01', '填 true/false');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('3', 'garnet_appCode', '0', 'varchar02', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('4', 'garnet_appCode', '0', 'varchar03', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('5', 'garnet_appCode', '0', 'varchar04', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('6', 'garnet_appCode', '0', 'varchar05', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('7', 'garnet_appCode', '0', 'varchar06', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('8', 'garnet_appCode', '0', 'varchar07', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('9', 'garnet_appCode', '0', 'varchar08', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('10', 'garnet_appCode', '0', 'varchar09', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('11', 'garnet_appCode', '0', 'varchar10', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('12', 'garnet_appCode', '0', 'varchar11', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('13', 'garnet_appCode', '0', 'varchar12', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('14', 'garnet_appCode', '0', 'varchar13', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('15', 'garnet_appCode', '0', 'varchar14', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('16', 'garnet_appCode', '0', 'varchar15', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('17', 'garnet_appCode', '0', 'varchar16', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('18', 'garnet_appCode', '0', 'varchar17', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('19', 'garnet_appCode', '0', 'varchar18', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('20', 'garnet_appCode', '0', 'varchar19', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('21', 'garnet_appCode', '0', 'int01', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('22', 'garnet_appCode', '0', 'int02', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('23', 'garnet_appCode', '0', 'int03', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('24', 'garnet_appCode', '0', 'int04', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('25', 'garnet_appCode', '0', 'int05', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('26', 'garnet_appCode', '0', 'boolean01', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('27', 'garnet_appCode', '0', 'boolean02', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('28', 'garnet_appCode', '0', 'boolean03', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('29', 'garnet_appCode', '0', 'boolean04', ' ');
-- superAdmin
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('30', 'superAdmin', '0', 'varchar00', '拥有的权限');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('31', 'superAdmin', '0', 'varchar01', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('32', 'superAdmin', '0', 'varchar02', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('33', 'superAdmin', '0', 'varchar03', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('34', 'superAdmin', '0', 'varchar04', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('35', 'superAdmin', '0', 'varchar05', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('36', 'superAdmin', '0', 'varchar06', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('37', 'superAdmin', '0', 'varchar07', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('38', 'superAdmin', '0', 'varchar08', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('39', 'superAdmin', '0', 'varchar09', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('40', 'superAdmin', '0', 'varchar10', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('41', 'superAdmin', '0', 'varchar11', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('42', 'superAdmin', '0', 'varchar12', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('43', 'superAdmin', '0', 'varchar13', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('44', 'superAdmin', '0', 'varchar14', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('45', 'superAdmin', '0', 'varchar15', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('46', 'superAdmin', '0', 'varchar16', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('47', 'superAdmin', '0', 'varchar17', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('48', 'superAdmin', '0', 'varchar18', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('49', 'superAdmin', '0', 'varchar19', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('50', 'superAdmin', '0', 'int01', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('51', 'superAdmin', '0', 'int02', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('52', 'superAdmin', '0', 'int03', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('53', 'superAdmin', '0', 'int04', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('54', 'superAdmin', '0', 'int05', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('55', 'superAdmin', '0', 'boolean01', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('56', 'superAdmin', '0', 'boolean02', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('57', 'superAdmin', '0', 'boolean03', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('58', 'superAdmin', '0', 'boolean04', ' ');
-- sysMenu
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('59', 'garnet_sysMenu', '0', 'varchar00', 'menuId');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('60', 'garnet_sysMenu', '0', 'varchar01', 'parentId');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('61', 'garnet_sysMenu', '0', 'varchar02', 'parentName');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('62', 'garnet_sysMenu', '0', 'varchar03', 'name');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('63', 'garnet_sysMenu', '0', 'varchar04', 'url');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('64', 'garnet_sysMenu', '0', 'varchar05', 'type');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('65', 'garnet_sysMenu', '0', 'varchar06', 'icon');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('66', 'garnet_sysMenu', '0', 'varchar07', 'code');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('67', 'garnet_sysMenu', '0', 'varchar08', 'orderNum');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('68', 'garnet_sysMenu', '0', 'varchar09', 'open');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('69', 'garnet_sysMenu', '0', 'varchar10', 'list');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('70', 'garnet_sysMenu', '0', 'varchar11', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('71', 'garnet_sysMenu', '0', 'varchar12', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('72', 'garnet_sysMenu', '0', 'varchar13', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('73', 'garnet_sysMenu', '0', 'varchar14', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('74', 'garnet_sysMenu', '0', 'varchar15', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('75', 'garnet_sysMenu', '0', 'varchar16', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('76', 'garnet_sysMenu', '0', 'varchar17', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('77', 'garnet_sysMenu', '0', 'varchar18', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('78', 'garnet_sysMenu', '0', 'varchar19', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('79', 'garnet_sysMenu', '0', 'int01', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('80', 'garnet_sysMenu', '0', 'int02', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('81', 'garnet_sysMenu', '0', 'int03', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('82', 'garnet_sysMenu', '0', 'int04', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('83', 'garnet_sysMenu', '0', 'int05', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('84', 'garnet_sysMenu', '0', 'boolean01', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('85', 'garnet_sysMenu', '0', 'boolean02', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('86', 'garnet_sysMenu', '0', 'boolean03', ' ');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, filed_name, description) VALUES ('87', 'garnet_sysMenu', '0', 'boolean04', ' ');

-- gar_resources
-- appCode
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('1', '1', '/garnet', 'appCode_garnetSysManagementApplicationUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('2', '1', '/garnet', 'appCode_garnetDevelopmentResourceDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('3', '1', '/garnet', 'appCode_garnetSysManagementApplicationDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('4', '1', '/garnet', 'appCode_garnetSysManagementRoleDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('5', '1', '/garnet', 'appCode_garnetDevelopmentResourceAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('6', '1', '/garnet', 'appCode_garnetSysManagement', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagement', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('7', '1', '/garnet', 'appCode_garnetSysManagementGroupDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('8', '1', '/garnet', 'appCode_garnetDevelopmentApiDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('9', '1', '/garnet', 'appCode_garnetDevelopmentResource', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResource', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('10', '1', '/garnet', 'appCode_garnetSysManagementRole', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRole', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('11', '1', '/garnet', 'appCode_garnetSysManagementRoleAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('12', '1', '/garnet', 'appCode_garnetSysManagementRoleUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('13', '1', '/garnet', 'appCode_garnet', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnet', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('14', '1', '/garnet', 'appCode_garnetSysManagementGroup', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroup', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('15', '1', '/garnet', 'appCode_garnetDevelopmentResourceUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('16', '1', '/garnet', 'appCode_garnetDevelopmentApiUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('17', '1', '/garnet', 'appCode_garnetSysManagementGroupUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('18', '1', '/garnet', 'appCode_garnetSysManagementApplicationAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('19', '1', '/garnet', 'appCode_garnetSysManagementLog', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementLog', 'false');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('20', '1', '/garnet', 'appCode_garnetSysManagementTenantDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('21', '1', '/garnet', 'appCode_garnetSysManagementPermissionAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('22', '1', '/garnet', 'appCode_garnetDevelopmentApiAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('23', '1', '/garnet', 'appCode_garnetSysManagementTenantSearchBox', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantSearchBox', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('24', '1', '/garnet', 'appCode_garnetSysManagementGroupAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('25', '1', '/garnet', 'appCode_garnetSysManagementUser', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUser', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('26', '1', '/garnet', 'appCode_garnetSysManagementUserDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUserDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('27', '1', '/garnet', 'appCode_garnetDevelopmentAllApi', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentAllApi', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('28', '1', '/garnet', 'appCode_garnetSysManagementPermissionDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('29', '1', '/garnet', 'appCode_garnetSysManagementPermissionUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('30', '1', '/garnet', 'appCode_garnetDevelopmentApi', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApi', 'false');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('31', '1', '/garnet', 'appCode_garnetDevelopment', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopment', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('32', '1', '/garnet', 'appCode_garnetSysManagementUserUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUserUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('33', '1', '/garnet', 'appCode_garnetSysManagementTenantAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('34', '1', '/garnet', 'appCode_garnetSysManagementTenant', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenant', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('35', '1', '/garnet', 'appCode_garnetSysManagementApplication', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplication', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('36', '1', '/garnet', 'appCode_garnetSysManagementTenantUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('37', '1', '/garnet', 'appCode_garnetSysManagementPermission', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermission', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('38', '1', '/garnet', 'appCode_garnetSysManagementUsernAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUsernAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('39', '1', '/garnet', 'appCode_garnetDevelopmentresourceDynamicProperty', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicProperty', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('40', '1', '/garnet', 'appCode_garnetDevelopmentresourceDynamicPropertyAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('41', '1', '/garnet', 'appCode_garnetDevelopmentresourceDynamicPropertyUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('42', '1', '/garnet', 'appCode_garnetDevelopmentresourceDynamicPropertyDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('43', '1', '/garnet', 'appCode_garnetDevelopmentRouterGroup', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroup', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('44', '1', '/garnet', 'appCode_garnetDevelopmentRouterGroupAdd', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('45', '1', '/garnet', 'appCode_garnetDevelopmentRouterGroupUpdate', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('46', '1', '/garnet', 'appCode_garnetDevelopmentRouterGroupDelete', '控制菜单显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupDelete', 'true');
-- superAdmin
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00) VALUES ('60', '1', '/superAll', '控制菜单显示', 'superAdmin_permission', '1522252800000', '1522252800000', 'superAdmin', '1', 'all_permission');
-- sysMenu
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('47', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '1', '0', ' ', '系统管理', ' ', '0', 'fa fa-cog', 'garnetSysManagement', '0', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('48', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '2', '1', ' ', '租户管理', 'modules/tenant.html', '1', 'fa fa-address-book', 'garnetSysManagementTenant', '1', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('49', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '3', '1', ' ', '应用管理', 'modules/application.html', '1', 'fa fa-th-large', 'garnetSysManagementApplication', '2', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('50', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '4', '1', ' ', '用户管理', 'modules/user.html', '1', 'fa fa fa-user', 'garnetSysManagementUser', '3', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('51', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '5', '1', ' ', '组管理', 'modules/group.html', '1', 'fa fa-institution', 'garnetSysManagementGroup', '4', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('52', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '6', '1', ' ', '角色管理', 'modules/role.html', '1', 'fa fa-group', 'garnetSysManagementRole', '5', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('53', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '7', '1', ' ', '权限管理', 'modules/permission.html', '1', 'fa fa-th-list', 'garnetSysManagementPermission', '6', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('54', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '8', '1', ' ', '系统日志', 'modules/log.html', '1', 'fa fa-file-text-o', 'garnetSysManagementLog', '7', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('55', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '9', '0', ' ', '开发选项', ' ', '0', 'fa fa-cog', 'garnetDevelopment', '0', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('56', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '10', '9', ' ', '资源管理', 'modules/resource.html', '1', 'fa fa-th-list', 'garnetDevelopmentResource', '1', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('57', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '11', '9', ' ', 'API', 'modules/api.html', '1', 'fa fa-th-list', 'garnetDevelopmentApi', '2', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('58', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '12', '9', ' ', '资源类型配置', 'modules/resourceDynamicProperty.html', '1', 'fa fa-th-list', 'garnetDevelopmentresourceDynamicProperty', '3', ' ', ' ');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10) VALUES ('59', '1', '/garnet',  ' ', '菜单配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '13', '9', ' ', '应用白名单管理', 'modules/routerGroup.html', '1', 'fa fa-th-list', 'garnetDevelopmentRouterGroup', '4', ' ', ' ');

-- gar_router_group
INSERT INTO gar_router_group (id, group_name, app_code) VALUES ('1', '超级应用组', 'garnet');

COMMIT ;