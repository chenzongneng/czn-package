
/* Drop Tables */

DROP TABLE IF EXISTS gar_application_tenants;
DROP TABLE IF EXISTS gar_applications;
DROP TABLE IF EXISTS gar_group_roles;
DROP TABLE IF EXISTS gar_group_users;
DROP TABLE IF EXISTS gar_groups;
DROP TABLE IF EXISTS gar_role_permissions;
DROP TABLE IF EXISTS gar_permissions;
DROP TABLE IF EXISTS gar_resources;
DROP TABLE IF EXISTS gar_resource_dynamic_props;
DROP TABLE IF EXISTS gar_roles;
DROP TABLE IF EXISTS gar_router_group;
DROP TABLE IF EXISTS gar_system_config;
DROP TABLE IF EXISTS gar_user_tenants;
DROP TABLE IF EXISTS gar_tenants;
DROP TABLE IF EXISTS gar_tokens;
DROP TABLE IF EXISTS gar_user_credentials;
DROP TABLE IF EXISTS gar_users;
DROP TABLE IF EXISTS gar_user_tenant_applications;
DROP TABLE IF EXISTS gar_logs;


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
ALTER TABLE gar_applications ADD COLUMN default_index_url varchar(256) DEFAULT '' NOT NULL;


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
	name varchar(256) NOT NULL,
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
	action VARCHAR(100) NOT NULL,
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


CREATE TABLE gar_resource_dynamic_props
(
	id bigint NOT NULL UNIQUE,
	type varchar(50) DEFAULT '0' NOT NULL,
	remark varchar(256) DEFAULT '' NOT NULL,
	application_id bigint DEFAULT 0 NOT NULL,
	filed_name varchar(256) DEFAULT '' NOT NULL,
	description varchar(256) DEFAULT '' NOT NULL,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;
ALTER TABLE gar_resource_dynamic_props ADD COLUMN actions varchar(256) DEFAULT '' NOT NULL;
ALTER TABLE gar_resource_dynamic_props ADD COLUMN tenant_id bigint DEFAULT 0 NOT NULL;

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


CREATE TABLE gar_router_group
(
	id bigint NOT NULL,
	group_name varchar(256) NOT NULL,
	remark varchar(256) DEFAULT '' NOT NULL,
	app_code varchar(256) NOT NULL,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,

	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_system_config
(
	id bigint NOT NULL UNIQUE,
	parameter varchar(256),
	value varchar(256),
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE gar_tenants
(
	id bigint NOT NULL UNIQUE,
	name varchar(256) DEFAULT '' NOT NULL,
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
-- 新建租户时是否关联所有已存在用户
ALTER TABLE gar_tenants ADD COLUMN related_all_users VARCHAR(5) DEFAULT 'N' NOT NULL;

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
	user_name varchar(256) DEFAULT '' NOT NULL,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	mobile_number varchar(256) DEFAULT '' NOT NULL,
	email varchar(256) DEFAULT '' NOT NULL,
	status int DEFAULT 1 NOT NULL,
	belong_to_garnet varchar(5) NOT NULL,
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


CREATE TABLE gar_logs
(
	id bigint DEFAULT 0 NOT NULL UNIQUE,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	user_name VARCHAR(256) DEFAULT '' NOT NULL ,
	message VARCHAR(256) DEFAULT '' NOT NULL ,
	tenant_id bigint,
	application_id bigint,
	ip VARCHAR(20),
	operation VARCHAR(256),

	PRIMARY KEY (id)
) WITHOUT OIDS;

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



-- 初始化数据
-- gar_system_config
INSERT INTO gar_system_config VALUES (1, 'mode', 'all');

-- gar_users
INSERT INTO gar_users(id, user_name, created_time, modified_time, status, belong_to_garnet, updated_by_user_name) VALUES ('1', 'admin', '1522252800000', '1522252800000', '1', 'Y', 'admin');

-- gar_user_credentials
INSERT INTO gar_user_credentials(id, created_time, modified_time, credential, expired_date_time, user_id) VALUES ('1', '1522252800000', '1522252800000', 'admin', '7959830400000', '1');

-- gar_tenants
INSERT INTO gar_tenants(id, name, created_time, modified_time, description, status, service_mode, updated_by_user_name, related_all_users) VALUES ('1', 'garnet', '1522252800000', '1522252800000', '超级租户', '1', 'paas', 'admin', 'Y');

-- gar_user_tenants
INSERT INTO gar_user_tenants(user_id, tenant_id, id) VALUES ('1', '1', '1');

-- gar_application
INSERT INTO gar_applications(id, name, company, app_code, refresh_resources_api, hosts, created_time, modified_time, status, service_mode, updated_by_user_name) VALUES ('1', 'garnet', 'garnet', 'garnet', '', '', '1522252800000', '1522252800000', '1', 'saas', 'admin');

-- gar_application_tenants
INSERT INTO gar_application_tenants(application_id, tenant_id, id) VALUES ('1', '1', '1');

-- gar_groups
INSERT INTO gar_groups(id, name, created_time, modified_time, application_id, tenant_id, status, updated_by_user_name) VALUES ('1', '超级组', '1522252800000', '1522252800000', '1', '1', '1', 'admin');

-- gar_roles
INSERT INTO gar_roles(id, name, remark, created_time, modified_time, tenant_id, application_id, status, updated_by_user_name) VALUES('1', '超级角色', '超级角色', '1522252800000', '1522252800000', '1', '1', '1', 'admin');

-- gar_permissions
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('1', '%', '超级权限', '超级权限', '1522252800000', '1522252800000', '1', '1', 'read', '1', 'admin');

-- gar_role_permissions
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '1', '1');

-- gar_group_role
INSERT INTO gar_group_roles(role_id, group_id, id) VALUES ('1', '1', '1');

-- gar_group_user
INSERT INTO gar_group_users(group_id, user_id, id) VALUES ('1', '1', '1');

-- gar_resource_dynamic_props
-- appCode
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('1', 'garnet_appCode', '1', '1', 'varchar00', '填 code name', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('2', 'garnet_appCode', '1', '1', 'varchar01', '填 true/false', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- superAdmin
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('3', 'superAdmin', '1', '1', 'varchar00', '拥有的权限', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- isBelongToGarnet
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('4', 'garnet_system_isBelongToGarnet', '1', '1', 'varchar00', '是否属于属于Garnet', 'read', '是否属于属于Garnet用户标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- sysMenu
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('5', 'garnet_sysMenu', '1', '1', 'varchar00', 'menuId', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('6', 'garnet_sysMenu', '1', '1', 'varchar01', 'parentId', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('7', 'garnet_sysMenu', '1', '1', 'varchar02', 'parentName', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('8', 'garnet_sysMenu', '1', '1', 'varchar03', 'name', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('9', 'garnet_sysMenu', '1', '1', 'varchar04', 'url', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('10', 'garnet_sysMenu', '1', '1', 'varchar05', 'type', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('11', 'garnet_sysMenu', '1', '1', 'varchar06', 'icon', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('12', 'garnet_sysMenu', '1', '1', 'varchar07', 'code', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('13', 'garnet_sysMenu', '1', '1', 'varchar08', 'orderNum', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('14', 'garnet_sysMenu', '1', '1', 'varchar09', 'open', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('15', 'garnet_sysMenu', '1', '1', 'varchar10', 'list', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- Garnet数据权限
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('16', 'garnet_data_permission', '1', '1', 'varchar00', 'level', 'read', 'Garnet数据权限等级设置', '1522252800000', '1522252800000', 'admin');
-- Garnet系统选项
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('17', 'garnet_system_option', '1', '1', 'varchar00', 'type', 'read;edit', 'Garnet系统提供的选项', '1522252800000', '1522252800000', 'admin');
-- Garnet系统按钮
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('18', 'garnet_button', '1', '1', 'varchar00', 'type', 'read;', 'Garnet系统的按钮可用性', '1522252800000', '1522252800000', 'admin');
-- 输入框是否可编辑
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('16', 'garnet_tenantUpdateButton', '1', '1', 'varchar00', 'tenant_name', 'read', 'Garnet系统按钮_租户更新', '1528041600000', '1528041600000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('17', 'garnet_tenantUpdateButton', '1', '1', 'varchar01', '填true/false', 'read', 'Garnet系统按钮_租户更新', '1528041600000', '1528041600000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('18', 'garnet_applicationUpdateButton', '1', '1', 'varchar00', 'application_name', 'read', 'Garnet系统按钮_应用更新', '1528041600000', '1528041600000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('19', 'garnet_applicationUpdateButton', '1', '1', 'varchar01', '填true/false', 'read', 'Garnet系统按钮_应用更新', '1528041600000', '1528041600000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('20', 'garnet_groupUpdateButton', '1', '1', 'varchar00', 'group_name', 'read', 'Garnet系统按钮_组更新', '1528041600000', '1528041600000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('21', 'garnet_groupUpdateButton', '1', '1', 'varchar01', '填true/false', 'read', 'Garnet系统按钮_组更新', '1528041600000', '1528041600000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('22', 'garnet_roleUpdateButton', '1', '1', 'varchar00', 'role_name', 'read', 'Garnet系统按钮_角色更新', '1528041600000', '1528041600000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('23', 'garnet_roleUpdateButton', '1', '1', 'varchar01', '填true/false', 'read', 'Garnet系统按钮_角色更新', '1528041600000', '1528041600000', 'admin');
-- 类型

-- gar_resources
-- appCode
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('1', '1', '/garnet/SysManagementApplicationUpdate', 'appCode_garnetSysManagementApplicationUpdate', '应用管理-配置按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('2', '1', '/garnet/DevelopmentResourceDelete', 'appCode_garnetDevelopmentResourceDelete', '资源管理-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('3', '1', '/garnet/SysManagementApplicationDelete', 'appCode_garnetSysManagementApplicationDelete', '应用管理-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('4', '1', '/garnet/SysManagementRoleDelete', 'appCode_garnetSysManagementRoleDelete', '角色管理-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('5', '1', '/garnet/DevelopmentResourceAdd', 'appCode_garnetDevelopmentResourceAdd', '资源管理-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('6', '1', '/garnet/SysManagement', 'appCode_garnetSysManagement', '系统管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagement', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('7', '1', '/garnet/SysManagementGroupDelete', 'appCode_garnetSysManagementGroupDelete', '组管理-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupDelete', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('8', '1', '/garnet/DevelopmentApiDelete', 'appCode_garnetDevelopmentApiDelete', 'Api管理-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('9', '1', '/garnet/DevelopmentResource', 'appCode_garnetDevelopmentResource', '资源管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResource', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('10', '1', '/garnet/SysManagementRole', 'appCode_garnetSysManagementRole', '角色管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRole', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('11', '1', '/garnet/SysManagementRoleAdd', 'appCode_garnetSysManagementRoleAdd', '角色管理-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('12', '1', '/garnet/SysManagementRoleUpdate', 'appCode_garnetSysManagementRoleUpdate', '角色管理-配置按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('13', '1', '/garnet/garnet', 'appCode_garnet', '控制菜单显示-garnet', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnet', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('14', '1', '/garnet/SysManagementGroup', 'appCode_garnetSysManagementGroup', '组管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroup', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('15', '1', '/garnet/DevelopmentResourceUpdate', 'appCode_garnetDevelopmentResourceUpdate', '资源管理-配置按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceUpdate', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('16', '1', '/garnet/DevelopmentApiUpdate', 'appCode_garnetDevelopmentApiUpdate', 'Api管理-配置按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('17', '1', '/garnet/SysManagementGroupUpdate', 'appCode_garnetSysManagementGroupUpdate', '组管理-配置按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('18', '1', '/garnet/SysManagementApplicationAdd', 'appCode_garnetSysManagementApplicationAdd', '应用管理-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('19', '1', '/garnet/SysManagementLog', 'appCode_garnetSysManagementLog', '日志管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementLog', 'false', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('20', '1', '/garnet/SysManagementTenantDelete', 'appCode_garnetSysManagementTenantDelete', '租户管理-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('21', '1', '/garnet/SysManagementPermissionAdd', 'appCode_garnetSysManagementPermissionAdd', '权限管理-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionAdd', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('22', '1', '/garnet/DevelopmentApiAdd', 'appCode_garnetDevelopmentApiAdd', 'Api管理-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiAdd', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('23', '1', '/garnet/SysManagementTenantSearchBox', 'appCode_garnetSysManagementTenantSearchBox', '控制菜单显示-SysManagementTenantSearchBox', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantSearchBox', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('24', '1', '/garnet/SysManagementGroupAdd', 'appCode_garnetSysManagementGroupAdd', '组管理-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('25', '1', '/garnet/SysManagementUser', 'appCode_garnetSysManagementUser', '用户管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUser', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('26', '1', '/garnet/SysManagementUserDelete', 'appCode_garnetSysManagementUserDelete', '用户管理-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUserDelete', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('27', '1', '/garnet/DevelopmentAllApi', 'appCode_garnetDevelopmentAllApi', '控制菜单显示-DevelopmentAllApi', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentAllApi', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('28', '1', '/garnet/SysManagementPermissionDelete', 'appCode_garnetSysManagementPermissionDelete', '权限管理-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('29', '1', '/garnet/SysManagementPermissionUpdate', 'appCode_garnetSysManagementPermissionUpdate', '权限管理-更新按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionUpdate', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('30', '1', '/garnet/DevelopmentApi', 'appCode_garnetDevelopmentApi', 'Api管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApi', 'false', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('31', '1', '/garnet/Development', 'appCode_garnetDevelopment', '开发选项管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopment', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('32', '1', '/garnet/SysManagementUserUpdate', 'appCode_garnetSysManagementUserUpdate', '用户管理-配置按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUserUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('33', '1', '/garnet/SysManagementTenantAdd', 'appCode_garnetSysManagementTenantAdd', '租户管理-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('34', '1', '/garnet/SysManagementTenant', 'appCode_garnetSysManagementTenant', '租户管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenant', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('35', '1', '/garnet/SysManagementApplication', 'appCode_garnetSysManagementApplication', '应用管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplication', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('36', '1', '/garnet/SysManagementTenantUpdate', 'appCode_garnetSysManagementTenantUpdate', '租户管理-配置按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('37', '1', '/garnet/SysManagementPermission', 'appCode_garnetSysManagementPermission', '权限管理菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermission', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('38', '1', '/garnet/SysManagementUsernAdd', 'appCode_garnetSysManagementUsernAdd', '用户管理-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUsernAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('39', '1', '/garnet/DevelopmentresourceDynamicProperty', 'appCode_garnetDevelopmentresourceDynamicProperty', '资源类型配置显示', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicProperty', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('40', '1', '/garnet/DevelopmentresourceDynamicPropertyAdd', 'appCode_garnetDevelopmentresourceDynamicPropertyAdd', '资源类型配置-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('41', '1', '/garnet/DevelopmentresourceDynamicPropertyUpdate', 'appCode_garnetDevelopmentresourceDynamicPropertyUpdate', '资源类型配置-配置按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('42', '1', '/garnet/DevelopmentresourceDynamicPropertyDelete', 'appCode_garnetDevelopmentresourceDynamicPropertyDelete', '资源类型配置-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('43', '1', '/garnet/DevelopmentRouterGroup', 'appCode_garnetDevelopmentRouterGroup', '单点登录应用组菜单', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroup', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('44', '1', '/garnet/DevelopmentRouterGroupAdd', 'appCode_garnetDevelopmentRouterGroupAdd', '单点登录应用组-新增按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('45', '1', '/garnet/DevelopmentRouterGroupUpdate', 'appCode_garnetDevelopmentRouterGroupUpdate', '单点登录应用组-配置按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('46', '1', '/garnet/DevelopmentRouterGroupDelete', 'appCode_garnetDevelopmentRouterGroupDelete', '单点登录应用组-删除按钮', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupDelete', 'true', 'admin');
-- superAdmin
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('60', '1', '/garnet/superAll', '', '超级管理员标志', '1522252800000', '1522252800000', 'superAdmin', '1', 'all_permission', 'admin');
-- isBelongToGarnet
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('91', '1', '/garnet/isBelongToGarnet', '', '是否属于Garnet用户标志', '1522252800000', '1522252800000', 'garnet_system_isBelongToGarnet', '1', 'Y', 'admin');
-- sysMenu
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('47', '1', '/garnet/SysManagement',  '', '菜单配置-系统管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '1', '0', '', '系统管理', '', '0', 'fa fa-cog', 'garnetSysManagement', '0', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('48', '1', '/garnet/SysManagementTenant',  '', '菜单配置-租户管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '2', '1', '', '租户管理', 'modules/tenant.html', '1', 'fa fa-address-book', 'garnetSysManagementTenant', '1', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('49', '1', '/garnet/SysManagementApplication',  '', '菜单配置-应用管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '3', '1', '', '应用管理', 'modules/application.html', '1', 'fa fa-th-large', 'garnetSysManagementApplication', '2', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('50', '1', '/garnet/SysManagementUser',  '', '菜单配置-用户管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '4', '1', '', '用户管理', 'modules/user.html', '1', 'fa fa fa-user', 'garnetSysManagementUser', '3', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('51', '1', '/garnet/SysManagementGroup',  '', '菜单配置-组管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '5', '1', '', '组管理', 'modules/group.html', '1', 'fa fa-institution', 'garnetSysManagementGroup', '4', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('52', '1', '/garnet/SysManagementRole',  '', '菜单配置-角色管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '6', '1', '', '角色管理', 'modules/role.html', '1', 'fa fa-group', 'garnetSysManagementRole', '5', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('53', '1', '/garnet/SysManagementPermission',  '', '菜单配置-权限管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '7', '1', '', '权限管理', 'modules/permission.html', '1', 'fa fa-th-list', 'garnetSysManagementPermission', '6', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('54', '1', '/garnet/SysManagementLog',  '', '菜单配置-日志管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '8', '1', '', '系统日志', 'modules/log.html', '1', 'fa fa-file-text-o', 'garnetSysManagementLog', '7', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('55', '1', '/garnet/Development',  '', '菜单配置-开发选项', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '9', '0', '', '开发选项', '', '0', 'fa fa-cog', 'garnetDevelopment', '0', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('56', '1', '/garnet/DevelopmentResource',  '', '菜单配置-资源管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '10', '9', '', '资源管理', 'modules/resource.html', '1', 'fa fa-th-list', 'garnetDevelopmentResource', '1', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('57', '1', '/garnet/DevelopmentApi',  '', '菜单配置-Api管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '11', '9', '', 'API', 'modules/api.html', '1', 'fa fa-th-list', 'garnetDevelopmentApi', '2', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('58', '1', '/garnet/DevelopmentresourceDynamicProperty',  '', '菜单配置-资源类型配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '12', '9', '', '资源类型配置', 'modules/resourceDynamicProperty.html', '1', 'fa fa-th-list', 'garnetDevelopmentresourceDynamicProperty', '3', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('59', '1', '/garnet/DevelopmentRouterGroup',  '', '菜单配置-单点登录应用组', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '13', '9', '', '单点登录应用组', 'modules/routerGroup.html', '1', 'fa fa-th-list', 'garnetDevelopmentRouterGroup', '4', '', '', 'admin');
-- 输入框是否可编辑
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES (84, 1, '/garnet/button/tenant/tenantUpdateButton', '', '租户编辑确认按钮读写权限', 1522252800000, 1522252800000, 'garnet_tenantUpdateButton', 1, 'tenantUpdateButton', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES (85, 1, '/garnet/button/application/appUpdateButton', '', '应用编辑确认按钮读写权限', 1522252800000, 1522252800000, 'garnet_applicationUpdateButton', 1, 'appUpdateButton', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES (86, 1, '/garnet/button/group/groupUpdateButton', '', '组编辑确认按钮读写权限', 1522252800000, 1522252800000, 'garnet_groupUpdateButton', 1, 'groupUpdateButton', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES (87, 1, '/garnet/button/role/roleUpdateButton', '', '角色编辑确认按钮读写权限', 1522252800000, 1522252800000, 'garnet_roleUpdateButton', 1, 'roleUpdateButton', 'true', 'admin');
-- 类型（应用级、租户级、租户+应用）控制
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES (88, 1, '/garnet/type/app', '', '应用级权限', 1522252800000, 1522252800000, 'level_app', 1, 'levelApp', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES (89, 1, '/garnet/type/tenant', '', '租户级权限', 1522252800000, 1522252800000, 'level_tenant', 1, 'levelTenant', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES (90, 1, '/garnet/type/tenant_app', '', '租户+应用权限', 1522252800000, 1522252800000, 'level_tenant_app', 1, 'levelTenantApp', 'true', 'admin');


-- gar_router_group
INSERT INTO gar_router_group (id, group_name, app_code, remark, created_time, modified_time, updated_by_user_name) VALUES ('1', '超级应用组', 'garnet', '不能删除garnet应用', '1522252800000', '1522252800000', 'admin');

