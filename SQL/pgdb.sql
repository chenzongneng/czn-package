
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
INSERT INTO gar_tenants(id, name, created_time, modified_time, description, status, service_mode, updated_by_user_name) VALUES ('1', 'garnet', '1522252800000', '1522252800000', '超级租户', '1', 'paas', 'admin');

-- gar_user_tenants
INSERT INTO gar_user_tenants(user_id, tenant_id, id) VALUES ('1', '1', '1');

-- gar_application
INSERT INTO gar_applications(id, name, company, app_code, refresh_resources_api, hosts, created_time, modified_time, status, service_mode, updated_by_user_name) VALUES ('1', 'garnet', 'garnet', 'garnet', '', '', '1522252800000', '1522252800000', '1', 'paas', 'admin');

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
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('3', 'garnet_appCode', '1', '1', 'varchar02', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('4', 'garnet_appCode', '1', '1', 'varchar03', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('5', 'garnet_appCode', '1', '1', 'varchar04', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('6', 'garnet_appCode', '1', '1', 'varchar05', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('7', 'garnet_appCode', '1', '1', 'varchar06', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('8', 'garnet_appCode', '1', '1', 'varchar07', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('9', 'garnet_appCode', '1', '1', 'varchar08', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('10', 'garnet_appCode', '1', '1', 'varchar09', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('11', 'garnet_appCode', '1', '1', 'varchar10', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('12', 'garnet_appCode', '1', '1', 'varchar11', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('13', 'garnet_appCode', '1', '1', 'varchar12', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('14', 'garnet_appCode', '1', '1', 'varchar13', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('15', 'garnet_appCode', '1', '1', 'varchar14', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('16', 'garnet_appCode', '1', '1', 'varchar15', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('17', 'garnet_appCode', '1', '1', 'varchar16', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('18', 'garnet_appCode', '1', '1', 'varchar17', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('19', 'garnet_appCode', '1', '1', 'varchar18', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('20', 'garnet_appCode', '1', '1', 'varchar19', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('21', 'garnet_appCode', '1', '1', 'int01', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('22', 'garnet_appCode', '1', '1', 'int02', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('23', 'garnet_appCode', '1', '1', 'int03', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('24', 'garnet_appCode', '1', '1', 'int04', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('25', 'garnet_appCode', '1', '1', 'int05', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('26', 'garnet_appCode', '1', '1', 'boolean01', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('27', 'garnet_appCode', '1', '1', 'boolean02', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('28', 'garnet_appCode', '1', '1', 'boolean03', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('29', 'garnet_appCode', '1', '1', 'boolean04', '', 'read', '控制功能按钮是否显示', '1522252800000', '1522252800000', 'admin');
-- superAdmin
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('30', 'superAdmin', '1', '1', 'varchar00', '拥有的权限', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('31', 'superAdmin', '1', '1', 'varchar01', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('32', 'superAdmin', '1', '1', 'varchar02', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('33', 'superAdmin', '1', '1', 'varchar03', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('34', 'superAdmin', '1', '1', 'varchar04', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('35', 'superAdmin', '1', '1', 'varchar05', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('36', 'superAdmin', '1', '1', 'varchar06', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('37', 'superAdmin', '1', '1', 'varchar07', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('38', 'superAdmin', '1', '1', 'varchar08', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('39', 'superAdmin', '1', '1', 'varchar09', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('40', 'superAdmin', '1', '1', 'varchar10', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('41', 'superAdmin', '1', '1', 'varchar11', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('42', 'superAdmin', '1', '1', 'varchar12', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('43', 'superAdmin', '1', '1', 'varchar13', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('44', 'superAdmin', '1', '1', 'varchar14', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('45', 'superAdmin', '1', '1', 'varchar15', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('46', 'superAdmin', '1', '1', 'varchar16', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('47', 'superAdmin', '1', '1', 'varchar17', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('48', 'superAdmin', '1', '1', 'varchar18', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('49', 'superAdmin', '1', '1', 'varchar19', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('50', 'superAdmin', '1', '1', 'int01', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('51', 'superAdmin', '1', '1', 'int02', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('52', 'superAdmin', '1', '1', 'int03', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('53', 'superAdmin', '1', '1', 'int04', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('54', 'superAdmin', '1', '1', 'int05', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('55', 'superAdmin', '1', '1', 'boolean01', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('56', 'superAdmin', '1', '1', 'boolean02', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('57', 'superAdmin', '1', '1', 'boolean03', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('58', 'superAdmin', '1', '1', 'boolean04', '', 'read', 'Garnet超级管理员标志，不可更改', '1522252800000', '1522252800000', 'admin');
-- sysMenu
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('59', 'garnet_sysMenu', '1', '1', 'varchar00', 'menuId', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('60', 'garnet_sysMenu', '1', '1', 'varchar01', 'parentId', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('61', 'garnet_sysMenu', '1', '1', 'varchar02', 'parentName', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('62', 'garnet_sysMenu', '1', '1', 'varchar03', 'name', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('63', 'garnet_sysMenu', '1', '1', 'varchar04', 'url', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('64', 'garnet_sysMenu', '1', '1', 'varchar05', 'type', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('65', 'garnet_sysMenu', '1', '1', 'varchar06', 'icon', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('66', 'garnet_sysMenu', '1', '1', 'varchar07', 'code', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('67', 'garnet_sysMenu', '1', '1', 'varchar08', 'orderNum', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('68', 'garnet_sysMenu', '1', '1', 'varchar09', 'open', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('69', 'garnet_sysMenu', '1', '1', 'varchar10', 'list', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('70', 'garnet_sysMenu', '1', '1', 'varchar11', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('71', 'garnet_sysMenu', '1', '1', 'varchar12', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('72', 'garnet_sysMenu', '1', '1', 'varchar13', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('73', 'garnet_sysMenu', '1', '1', 'varchar14', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('74', 'garnet_sysMenu', '1', '1', 'varchar15', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('75', 'garnet_sysMenu', '1', '1', 'varchar16', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('76', 'garnet_sysMenu', '1', '1', 'varchar17', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('77', 'garnet_sysMenu', '1', '1', 'varchar18', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('78', 'garnet_sysMenu', '1', '1', 'varchar19', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('79', 'garnet_sysMenu', '1', '1', 'int01', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('80', 'garnet_sysMenu', '1', '1', 'int02', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('81', 'garnet_sysMenu', '1', '1', 'int03', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('82', 'garnet_sysMenu', '1', '1', 'int04', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('83', 'garnet_sysMenu', '1', '1', 'int05', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('84', 'garnet_sysMenu', '1', '1', 'boolean01', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('85', 'garnet_sysMenu', '1', '1', 'boolean02', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('86', 'garnet_sysMenu', '1', '1', 'boolean03', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, remark, created_time, modified_time, updated_by_user_name) VALUES ('87', 'garnet_sysMenu', '1', '1', 'boolean04', '', 'read', '系统菜单配置', '1522252800000', '1522252800000', 'admin');

-- gar_resources
-- appCode
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('1', '1', '/garnet/SysManagementApplicationUpdate', 'appCode_garnetSysManagementApplicationUpdate', '控制菜单显示-SysManagementApplicationUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('2', '1', '/garnet/DevelopmentResourceDelete', 'appCode_garnetDevelopmentResourceDelete', '控制菜单显示-DevelopmentResourceDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('3', '1', '/garnet/SysManagementApplicationDelete', 'appCode_garnetSysManagementApplicationDelete', '控制菜单显示-SysManagementApplicationDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('4', '1', '/garnet/SysManagementRoleDelete', 'appCode_garnetSysManagementRoleDelete', '控制菜单显示-SysManagementRoleDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('5', '1', '/garnet/DevelopmentResourceAdd', 'appCode_garnetDevelopmentResourceAdd', '控制菜单显示-DevelopmentResourceAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('6', '1', '/garnet/SysManagement', 'appCode_garnetSysManagement', '控制菜单显示-SysManagement', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagement', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('7', '1', '/garnet/SysManagementGroupDelete', 'appCode_garnetSysManagementGroupDelete', '控制菜单显示-SysManagementGroupDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('8', '1', '/garnet/DevelopmentApiDelete', 'appCode_garnetDevelopmentApiDelete', '控制菜单显示-DevelopmentApiDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('9', '1', '/garnet/DevelopmentResource', 'appCode_garnetDevelopmentResource', '控制菜单显示-DevelopmentResource', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResource', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('10', '1', '/garnet/SysManagementRole', 'appCode_garnetSysManagementRole', '控制菜单显示-SysManagementRole', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRole', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('11', '1', '/garnet/SysManagementRoleAdd', 'appCode_garnetSysManagementRoleAdd', '控制菜单显示-SysManagementRoleAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('12', '1', '/garnet/SysManagementRoleUpdate', 'appCode_garnetSysManagementRoleUpdate', '控制菜单显示-SysManagementRoleUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementRoleUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('13', '1', '/garnet/garnet', 'appCode_garnet', '控制菜单显示-garnet', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnet', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('14', '1', '/garnet/SysManagementGroup', 'appCode_garnetSysManagementGroup', '控制菜单显示-SysManagementGroup', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroup', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('15', '1', '/garnet/DevelopmentResourceUpdate', 'appCode_garnetDevelopmentResourceUpdate', '控制菜单显示-DevelopmentResourceUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentResourceUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('16', '1', '/garnet/DevelopmentApiUpdate', 'appCode_garnetDevelopmentApiUpdate', '控制菜单显示-DevelopmentApiUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('17', '1', '/garnet/SysManagementGroupUpdate', 'appCode_garnetSysManagementGroupUpdate', '控制菜单显示-SysManagementGroupUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('18', '1', '/garnet/SysManagementApplicationAdd', 'appCode_garnetSysManagementApplicationAdd', '控制菜单显示-SysManagementApplicationAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplicationAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('19', '1', '/garnet/SysManagementLog', 'appCode_garnetSysManagementLog', '控制菜单显示-SysManagementLog', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementLog', 'false', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('20', '1', '/garnet/SysManagementTenantDelete', 'appCode_garnetSysManagementTenantDelete', '控制菜单显示-SysManagementTenantDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('21', '1', '/garnet/SysManagementPermissionAdd', 'appCode_garnetSysManagementPermissionAdd', '控制菜单显示-SysManagementPermissionAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('22', '1', '/garnet/DevelopmentApiAdd', 'appCode_garnetDevelopmentApiAdd', '控制菜单显示-DevelopmentApiAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApiAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('23', '1', '/garnet/SysManagementTenantSearchBox', 'appCode_garnetSysManagementTenantSearchBox', '控制菜单显示-SysManagementTenantSearchBox', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantSearchBox', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('24', '1', '/garnet/SysManagementGroupAdd', 'appCode_garnetSysManagementGroupAdd', '控制菜单显示-SysManagementGroupAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementGroupAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('25', '1', '/garnet/SysManagementUser', 'appCode_garnetSysManagementUser', '控制菜单显示-SysManagementUser', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUser', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('26', '1', '/garnet/SysManagementUserDelete', 'appCode_garnetSysManagementUserDelete', '控制菜单显示-SysManagementUserDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUserDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('27', '1', '/garnet/DevelopmentAllApi', 'appCode_garnetDevelopmentAllApi', '控制菜单显示-DevelopmentAllApi', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentAllApi', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('28', '1', '/garnet/SysManagementPermissionDelete', 'appCode_garnetSysManagementPermissionDelete', '控制菜单显示-SysManagementPermissionDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('29', '1', '/garnet/SysManagementPermissionUpdate', 'appCode_garnetSysManagementPermissionUpdate', '控制菜单显示-SysManagementPermissionUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermissionUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('30', '1', '/garnet/DevelopmentApi', 'appCode_garnetDevelopmentApi', '控制菜单显示-DevelopmentApi', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentApi', 'false', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('31', '1', '/garnet/Development', 'appCode_garnetDevelopment', '控制菜单显示-Development', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopment', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('32', '1', '/garnet/SysManagementUserUpdate', 'appCode_garnetSysManagementUserUpdate', '控制菜单显示-SysManagementUserUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUserUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('33', '1', '/garnet/SysManagementTenantAdd', 'appCode_garnetSysManagementTenantAdd', '控制菜单显示-SysManagementTenantAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('34', '1', '/garnet/SysManagementTenant', 'appCode_garnetSysManagementTenant', '控制菜单显示-SysManagementTenant', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenant', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('35', '1', '/garnet/SysManagementApplication', 'appCode_garnetSysManagementApplication', '控制菜单显示-SysManagementApplication', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementApplication', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('36', '1', '/garnet/SysManagementTenantUpdate', 'appCode_garnetSysManagementTenantUpdate', '控制菜单显示-SysManagementTenantUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementTenantUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('37', '1', '/garnet/SysManagementPermission', 'appCode_garnetSysManagementPermission', '控制菜单显示-SysManagementPermission', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementPermission', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('38', '1', '/garnet/SysManagementUsernAdd', 'appCode_garnetSysManagementUsernAdd', '控制菜单显示-SysManagementUsernAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetSysManagementUsernAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('39', '1', '/garnet/DevelopmentresourceDynamicProperty', 'appCode_garnetDevelopmentresourceDynamicProperty', '控制菜单显示-DevelopmentresourceDynamicProperty', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicProperty', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('40', '1', '/garnet/DevelopmentresourceDynamicPropertyAdd', 'appCode_garnetDevelopmentresourceDynamicPropertyAdd', '控制菜单显示-DevelopmentresourceDynamicPropertyAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('41', '1', '/garnet/DevelopmentresourceDynamicPropertyUpdate', 'appCode_garnetDevelopmentresourceDynamicPropertyUpdate', '控制菜单显示-DevelopmentresourceDynamicPropertyUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('42', '1', '/garnet/DevelopmentresourceDynamicPropertyDelete', 'appCode_garnetDevelopmentresourceDynamicPropertyDelete', '控制菜单显示-DevelopmentresourceDynamicPropertyDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentresourceDynamicPropertyDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('43', '1', '/garnet/DevelopmentRouterGroup', 'appCode_garnetDevelopmentRouterGroup', '控制菜单显示-DevelopmentRouterGroup', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroup', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('44', '1', '/garnet/DevelopmentRouterGroupAdd', 'appCode_garnetDevelopmentRouterGroupAdd', '控制菜单显示-DevelopmentRouterGroupAdd', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('45', '1', '/garnet/DevelopmentRouterGroupUpdate', 'appCode_garnetDevelopmentRouterGroupUpdate', '控制菜单显示-DevelopmentRouterGroupUpdate', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('46', '1', '/garnet/DevelopmentRouterGroupDelete', 'appCode_garnetDevelopmentRouterGroupDelete', '控制菜单显示-DevelopmentRouterGroupDelete', '1522252800000', '1522252800000', 'garnet_appCode', '1', 'garnetDevelopmentRouterGroupDelete', 'true', 'admin');
-- superAdmin
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('60', '1', '/garnet/superAll', '', '超级管理员标志', '1522252800000', '1522252800000', 'superAdmin', '1', 'all_permission', 'admin');
-- sysMenu
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('47', '1', '/garnet/SysManagement',  '', '菜单配置-SysManagement', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '1', '0', '', '系统管理', '', '0', 'fa fa-cog', 'garnetSysManagement', '0', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('48', '1', '/garnet/SysManagementTenant',  '', '菜单配置-SysManagementTenant', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '2', '1', '', '租户管理', 'modules/tenant.html', '1', 'fa fa-address-book', 'garnetSysManagementTenant', '1', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('49', '1', '/garnet/SysManagementApplication',  '', '菜单配置-SysManagementApplication', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '3', '1', '', '应用管理', 'modules/application.html', '1', 'fa fa-th-large', 'garnetSysManagementApplication', '2', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('50', '1', '/garnet/SysManagementUser',  '', '菜单配置-SysManagementUser', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '4', '1', '', '用户管理', 'modules/user.html', '1', 'fa fa fa-user', 'garnetSysManagementUser', '3', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('51', '1', '/garnet/SysManagementGroup',  '', '菜单配置-SysManagementGroup', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '5', '1', '', '组管理', 'modules/group.html', '1', 'fa fa-institution', 'garnetSysManagementGroup', '4', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('52', '1', '/garnet/SysManagementRole',  '', '菜单配置-SysManagementRole', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '6', '1', '', '角色管理', 'modules/role.html', '1', 'fa fa-group', 'garnetSysManagementRole', '5', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('53', '1', '/garnet/SysManagementPermission',  '', '菜单配置-SysManagementPermission', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '7', '1', '', '权限管理', 'modules/permission.html', '1', 'fa fa-th-list', 'garnetSysManagementPermission', '6', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('54', '1', '/garnet/SysManagementLog',  '', '菜单配置-SysManagementLog', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '8', '1', '', '系统日志', 'modules/log.html', '1', 'fa fa-file-text-o', 'garnetSysManagementLog', '7', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('55', '1', '/garnet/Development',  '', '菜单配置-Development', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '9', '0', '', '开发选项', '', '0', 'fa fa-cog', 'garnetDevelopment', '0', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('56', '1', '/garnet/DevelopmentResource',  '', '菜单配置-DevelopmentResource', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '10', '9', '', '资源管理', 'modules/resource.html', '1', 'fa fa-th-list', 'garnetDevelopmentResource', '1', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('57', '1', '/garnet/DevelopmentApi',  '', '菜单配置-DevelopmentApi', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '11', '9', '', 'API', 'modules/api.html', '1', 'fa fa-th-list', 'garnetDevelopmentApi', '2', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('58', '1', '/garnet/DevelopmentresourceDynamicProperty',  '', '菜单配置-DevelopmentresourceDynamicProperty', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '12', '9', '', '资源类型配置', 'modules/resourceDynamicProperty.html', '1', 'fa fa-th-list', 'garnetDevelopmentresourceDynamicProperty', '3', '', '', 'admin');
INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('59', '1', '/garnet/DevelopmentRouterGroup',  '', '菜单配置-DevelopmentRouterGroup', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '13', '9', '', '单点登录应用组', 'modules/routerGroup.html', '1', 'fa fa-th-list', 'garnetDevelopmentRouterGroup', '4', '', '', 'admin');

-- gar_router_group
INSERT INTO gar_router_group (id, group_name, app_code, remark, created_time, modified_time, updated_by_user_name) VALUES ('1', '超级应用组', 'garnet', '不能删除garnet应用', '1522252800000', '1522252800000', 'admin');

