
/* Drop Tables */

DROP TABLE IF EXISTS gar_application_tenants;
DROP TABLE IF EXISTS gar_applications;
DROP TABLE IF EXISTS gar_group_roles;
DROP TABLE IF EXISTS gar_group_users;
DROP TABLE IF EXISTS gar_groups;
DROP TABLE IF EXISTS gar_role_permissions;
DROP TABLE IF EXISTS gar_permissions;
DROP TABLE IF EXISTS gar_resources;
DROP TABLE IF EXISTS gar_resource_dynamic_properties;
DROP TABLE IF EXISTS gar_roles;
DROP TABLE IF EXISTS gar_user_tenants;
DROP TABLE IF EXISTS gar_tenants;
DROP TABLE IF EXISTS gar_tokens;
DROP TABLE IF EXISTS gar_user_credentials;
DROP TABLE IF EXISTS gar_users;
DROP TABLE IF EXISTS gar_user_tenant_applications;




/* Create Tables */

CREATE TABLE gar_applications
(
	id bigint NOT NULL UNIQUE,
	name varchar(256),
	company varchar(256),
	-- 调用接口时应用唯一标识
	app_code varchar(256) NOT NULL,
	refresh_resources_api varchar(256) DEFAULT '' NOT NULL,
	-- 此application所在的所有ip:ports，用分号分隔。如: 192.168.6.97:8080;192.168.6.98:8080
	-- 
	-- 加入已经作为微服务加入服务注册中心，无需填写此字段。系统会自动把app_code当做服务id，通过负载均衡器在服务中心找到对应服务。
	hosts varchar(256) DEFAULT '' NOT NULL,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	status int DEFAULT 1 NOT NULL,
	-- 属于paas服务还是saas服务
	service_mode varchar(5) DEFAULT 'paas' NOT NULL,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_application_tenants
(
	application_id bigint NOT NULL,
	tenant_id bigint NOT NULL,
	id bigint NOT NULL UNIQUE,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_groups
(
	id bigint NOT NULL UNIQUE,
	name varchar(256) NOT NULL UNIQUE,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	application_id bigint DEFAULT 0 NOT NULL,
	tenant_id bigint DEFAULT 0 NOT NULL,
	status int DEFAULT 1 NOT NULL,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_group_roles
(
	role_id bigint NOT NULL,
	group_id bigint NOT NULL,
	id bigint NOT NULL UNIQUE,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_group_users
(
	group_id bigint NOT NULL,
	user_id bigint NOT NULL,
	id bigint NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_permissions
(
	id bigint NOT NULL UNIQUE,
	resource_path_wildcard varchar(256) DEFAULT '' NOT NULL,
	name varchar(256) DEFAULT '' NOT NULL,
	description varchar(250) DEFAULT '' NOT NULL,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	application_id bigint DEFAULT 0 NOT NULL,
	tenant_id bigint DEFAULT 0 NOT NULL,
	status int DEFAULT 1 NOT NULL,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_resources
(
	id bigint NOT NULL UNIQUE,
	application_id bigint DEFAULT 0 NOT NULL,
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
	path varchar(256) DEFAULT '' NOT NULL,
	actions varchar(256) DEFAULT '' NOT NULL,
	name varchar(256) DEFAULT '' NOT NULL,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	-- 资源类型例如， 
	-- ui-UI组件，如button, input, textarea, table, drop down list等
	-- openApi-OpenApi, 如提供给其他应用调用的REST API。不是指应用内部的REST API
	-- function-功能项，如页面，菜单模块等
	type varchar(50) DEFAULT '' NOT NULL,
	tenant_id bigint DEFAULT 0 NOT NULL,
	varchar_00 varchar(256) DEFAULT '' NOT NULL,
	varchar_01 varchar(256) DEFAULT '' NOT NULL,
	varchar_02 varchar(256) DEFAULT '' NOT NULL,
	varchar_03 varchar(256) DEFAULT '' NOT NULL,
	varchar_04 varchar(256) DEFAULT '' NOT NULL,
	varchar_05 varchar(256) DEFAULT '' NOT NULL,
	varchar_06 varchar(256) DEFAULT '' NOT NULL,
	varchar_07 varchar(256) DEFAULT '' NOT NULL,
	varchar_08 varchar(256) DEFAULT '' NOT NULL,
	varchar_09 varchar(256) DEFAULT '' NOT NULL,
	varchar_10 varchar(256) DEFAULT '' NOT NULL,
	varchar_11 varchar(256) DEFAULT '' NOT NULL,
	varchar_12 varchar(256) DEFAULT '' NOT NULL,
	varchar_13 varchar(256) DEFAULT '' NOT NULL,
	varchar_14 varchar(256) DEFAULT '' NOT NULL,
	varchar_15 varchar(256) DEFAULT '' NOT NULL,
	varchar_16 varchar(256) DEFAULT '' NOT NULL,
	varchar_17 varchar(256) DEFAULT '' NOT NULL,
	varchar_18 varchar(256) DEFAULT '' NOT NULL,
	varchar_19 varchar(256) DEFAULT '' NOT NULL,
	int_01 int,
	int_02 int,
	int_03 int,
	int_04 int,
	int_05 int,
	boolean_01 boolean,
	boolean_02 boolean,
	boolean_03 boolean,
	boolean_04 boolean,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;

CREATE TABLE gar_resource_dynamic_properties
(
	id bigint NOT NULL UNIQUE,
	type VARCHAR(50) DEFAULT 0 NOT NULL,
	application_id bigint DEFAULT 0 NOT NULL,
	filed_name varchar(256) DEFAULT '' NOT NULL,
	description varchar(256) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_roles
(
	id bigint NOT NULL UNIQUE,
	name varchar(256) DEFAULT '' NOT NULL,
	remark varchar(256) DEFAULT '' NOT NULL,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	tenant_id bigint DEFAULT 0 NOT NULL,
	application_id bigint DEFAULT 0 NOT NULL,
	status int DEFAULT 1 NOT NULL,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_role_permissions
(
	role_id bigint NOT NULL,
	permission_id bigint NOT NULL,
	id bigint NOT NULL UNIQUE,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_tenants
(
	id bigint NOT NULL UNIQUE,
	name varchar(256) DEFAULT '' NOT NULL UNIQUE,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	description varchar(250) DEFAULT '' NOT NULL,
	status int DEFAULT 1 NOT NULL,
	-- 属于paas服务还是saas服务
	service_mode varchar(5) DEFAULT 'paas' NOT NULL,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_tokens
(
	id bigint NOT NULL UNIQUE,
	token varchar(256) DEFAULT '' NOT NULL UNIQUE,
	user_name varchar(256) DEFAULT '' NOT NULL UNIQUE,
	router_group_name varchar(256) DEFAULT '' NOT NULL,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	expire_time bigint DEFAULT 0 NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_users
(
	id bigint NOT NULL UNIQUE,
	user_name varchar(256) DEFAULT '' NOT NULL UNIQUE,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	mobile_number varchar(256) DEFAULT '' NOT NULL,
	email varchar(256) DEFAULT '' NOT NULL,
	status int DEFAULT 1  NOT NULL,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_user_credentials
(
	id bigint NOT NULL UNIQUE,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	credential varchar(256) DEFAULT '' NOT NULL,
	expired_date_time bigint DEFAULT 0 NOT NULL,
	user_id bigint NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_user_tenants
(
	user_id bigint NOT NULL,
	tenant_id bigint NOT NULL,
	id bigint NOT NULL UNIQUE,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_user_tenant_applications
(
	id bigint DEFAULT 0 NOT NULL UNIQUE,
	user_id bigint,
	tenant_id bigint,
	application_id bigint,
	status int DEFAULT 1 NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;

DROP TABLE IF EXISTS gar_router_group;
CREATE TABLE gar_router_group
(
	id bigint DEFAULT 0 NOT NULL UNIQUE,
	group_name VARCHAR(256) NOT NULL,
	app_code VARCHAR(256) NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;

DROP TABLE IF EXISTS gar_system_config;
CREATE TABLE gar_system_config
(
  id bigint DEFAULT 0 NOT NULL UNIQUE,
  parameter VARCHAR(256) NOT NULL,
  value VARCHAR(256) NOT NULL,
  PRIMARY KEY (id)
) WITHOUT OIDS;

-- gar_system_config
INSERT INTO gar_system_config VALUES (1, 'mode', 'paas');

-- gar_users
INSERT INTO gar_users(id, user_name, created_time, modified_time, status) VALUES ('1', 'admin', '1522252800000', '1522252800000', '1');

-- gar_user_credentials
INSERT INTO gar_user_credentials(id, created_time, modified_time, credential, expired_date_time, user_id) VALUES ('1', '1522252800000', '1522252800000', 'admin', '7959830400000', '1');

-- gar_tenants
INSERT INTO gar_tenants(id, name, created_time, modified_time, description, status, service_mode) VALUES ('1', '超级租户paas', '1522252800000', '1522252800000', '超级paas租户', '1', 'paas');

-- gar_user_tenants
INSERT INTO gar_user_tenants(user_id, tenant_id, id) VALUES ('1', '1', '1');

-- gar_user_application
INSERT INTO gar_applications(id, name, company, app_code, refresh_resources_api, hosts, created_time, modified_time, status, service_mode) VALUES ('1', 'garnet', 'garnet', 'garnet', '', '', '1522252800000', '1522252800000', '1', 'paas');

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

-- gar_resource_dynamic_properties
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('1', 'garnet_appCode', '0', 'varchar00', '填 code name');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('2', 'garnet_appCode', '0', 'varchar01', '填 true/false');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('3', 'garnet_appCode', '0', 'varchar02', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('4', 'garnet_appCode', '0', 'varchar03', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('5', 'garnet_appCode', '0', 'varchar04', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('6', 'garnet_appCode', '0', 'varchar05', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('7', 'garnet_appCode', '0', 'varchar06', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('8', 'garnet_appCode', '0', 'varchar07', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('9', 'garnet_appCode', '0', 'varchar08', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('10', 'garnet_appCode', '0', 'varchar09', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('11', 'garnet_appCode', '0', 'varchar10', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('12', 'garnet_appCode', '0', 'varchar11', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('13', 'garnet_appCode', '0', 'varchar12', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('14', 'garnet_appCode', '0', 'varchar13', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('15', 'garnet_appCode', '0', 'varchar14', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('16', 'garnet_appCode', '0', 'varchar15', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('17', 'garnet_appCode', '0', 'varchar16', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('18', 'garnet_appCode', '0', 'varchar17', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('19', 'garnet_appCode', '0', 'varchar18', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('20', 'garnet_appCode', '0', 'varchar19', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('21', 'garnet_appCode', '0', 'int01', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('22', 'garnet_appCode', '0', 'int02', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('23', 'garnet_appCode', '0', 'int03', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('24', 'garnet_appCode', '0', 'int04', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('25', 'garnet_appCode', '0', 'int05', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('26', 'garnet_appCode', '0', 'boolean01', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('27', 'garnet_appCode', '0', 'boolean02', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('28', 'garnet_appCode', '0', 'boolean03', '');
INSERT INTO gar_resource_dynamic_properties (id, type, application_id, filed_name, description) VALUES ('29', 'garnet_appCode', '0', 'boolean04', '');

-- gar_resources
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('1', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementApplicationUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('2', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentResourceDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('3', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementApplicationDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('4', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementRoleDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('5', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentResourceAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('6', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagement', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagement', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('7', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementGroupDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('8', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentApiDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('9', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentResource', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResource', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('10', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementRole', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRole', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('11', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementRoleAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('12', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementRoleUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('13', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnet', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnet', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('14', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementGroup', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroup', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('15', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentResourceUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('16', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentApiUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('17', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementGroupUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('18', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementApplicationAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('19', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementLog', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementLog', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('20', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementTenantDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('21', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementPermissionAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('22', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentApiAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('23', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementTenantSearchBox', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantSearchBox', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('24', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementGroupAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('25', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementUser', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUser', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('26', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementUserDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUserDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('27', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentAllApi', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentAllApi', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('28', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementPermissionDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('29', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementPermissionUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('30', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentApi', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApi', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('31', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopment', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopment', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('32', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementUserUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUserUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('33', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementTenantAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('34', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementTenant', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenant', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('35', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementApplication', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplication', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('36', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementTenantUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('37', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementPermission', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermission', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('38', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetSysManagementUsernAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUsernAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('39', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentresourceDynamicProperty', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicProperty', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('40', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentresourceDynamicPropertyAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('41', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentresourceDynamicPropertyUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('42', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentresourceDynamicPropertyDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyDelete', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('43', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentRouterGroup', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroup', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('44', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentRouterGroupAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupAdd', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('45', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentRouterGroupUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupUpdate', 'true');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01) VALUES ('46', '1', '/index.html', '控制菜单显示', 'garnet_appCode_garnetDevelopmentRouterGroupDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupDelete', 'true');

-- gar_router_group
INSERT INTO gar_router_group (id, group_name, app_code) VALUES ('1', '超级应用组', 'garnet');


/* Comments */

COMMENT ON COLUMN gar_applications.app_code IS '调用接口时应用唯一标识';
COMMENT ON COLUMN gar_applications.hosts IS '此application所在的所有ip:ports，用分号分隔。如: 192.168.6.97:8080;192.168.6.98:8080

加入已经作为微服务加入服务注册中心，无需填写此字段。系统会自动把app_code当做服务id，通过负载均衡器在服务中心找到对应服务。';
COMMENT ON COLUMN gar_applications.service_mode IS '属于paas服务还是saas服务';
COMMENT ON COLUMN gar_applications.updated_by_user_name IS '更新的人';
COMMENT ON COLUMN gar_groups.updated_by_user_name IS '更新的人';
COMMENT ON COLUMN gar_permissions.updated_by_user_name IS '更新的人';
COMMENT ON COLUMN gar_resources.path IS 'ui - UI组件
/{page_name}/{element_name}

openApi - open api，对外开放的api，并非内部使用的api;

/api/v1/users
此时需要与dynamic properties配合，假如没有注册中心，直接指定某个property为ip:port;假如使用注册中心，直接指定某个property为service id

function - function opint,对应的是功能项，例如用户查询；
例如
一个具体的页面：
/{page_name}

某一个页面中的某个功能，例如用户组管理页面中的增加用户组的功能

/{page_name}/{function_name}';
COMMENT ON COLUMN gar_resources.type IS '资源类型例如， 
ui-UI组件，如button, input, textarea, table, drop down list等
openApi-OpenApi, 如提供给其他应用调用的REST API。不是指应用内部的REST API
function-功能项，如页面，菜单模块等';
COMMENT ON COLUMN gar_resources.updated_by_user_name IS '更新的人';
COMMENT ON COLUMN gar_roles.updated_by_user_name IS '更新的人';
COMMENT ON COLUMN gar_tenants.service_mode IS '属于paas服务还是saas服务';
COMMENT ON COLUMN gar_tenants.updated_by_user_name IS '更新的人';
COMMENT ON COLUMN gar_users.updated_by_user_name IS '更新的人';



