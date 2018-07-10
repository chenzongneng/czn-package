
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
	remark varchar(256) DEFAULT '' NOT NULL,
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
-- ALTER TABLE gar_resources ADD COLUMN remark varchar(256) DEFAULT '' NOT NULL;


CREATE TABLE gar_resource_dynamic_props
(
	id bigint NOT NULL UNIQUE,
	name varchar(50) DEFAULT '' NOT NULL,
	type varchar(50) DEFAULT '0' NOT NULL,
	remark varchar(256) DEFAULT '' NOT NULL,
	actions varchar(256) DEFAULT '' NOT NULL,
	tenant_id bigint DEFAULT 0 NOT NULL,
	application_id bigint DEFAULT 0 NOT NULL,
	filed_name varchar(256) DEFAULT '' NOT NULL,
	description varchar(256) DEFAULT '' NOT NULL,
	created_time bigint DEFAULT 0 NOT NULL,
	modified_time bigint DEFAULT 0 NOT NULL,
	-- 更新的人
	updated_by_user_name varchar(100) DEFAULT '' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;
-- ALTER TABLE gar_resource_dynamic_props ADD COLUMN actions varchar(256) DEFAULT '' NOT NULL;
-- ALTER TABLE gar_resource_dynamic_props ADD COLUMN tenant_id bigint DEFAULT 0 NOT NULL;
-- ALTER TABLE gar_resource_dynamic_props ADD COLUMN name varchar(50) DEFAULT '' NOT NULL;

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
COMMENT ON COLUMN gar_resource_dynamic_props.type IS '代号';


-- 初始化数据
-- gar_system_config
INSERT INTO gar_system_config VALUES (1, 'mode', 'all');

-- gar_users
INSERT INTO gar_users(id, user_name, created_time, modified_time, status, belong_to_garnet, updated_by_user_name) VALUES ('1', 'admin', '1522252800000', '1522252800000', '1', 'Y', 'admin');

-- gar_user_credentials
INSERT INTO gar_user_credentials(id, created_time, modified_time, credential, expired_date_time, user_id) VALUES ('1', '1522252800000', '1522252800000', 'admin', '7959830400000', '1');

-- gar_tenants
INSERT INTO gar_tenants(id, name, created_time, modified_time, description, status, service_mode, updated_by_user_name, related_all_users) VALUES ('1', 'garnet', '1522252800000', '1522252800000', '超级租户', '1', 'saas', 'admin', 'Y');

-- gar_user_tenants
INSERT INTO gar_user_tenants(user_id, tenant_id, id) VALUES ('1', '1', '1');

-- gar_application
INSERT INTO gar_applications(id, name, company, app_code, refresh_resources_api, hosts, created_time, modified_time, status, service_mode, updated_by_user_name, default_index_url) VALUES ('1', 'garnet', 'garnet', 'garnet', '', '', '1522252800000', '1522252800000', '1', 'saas', 'admin', 'http://192.168.111.100:12306/garnet/index.html');

-- gar_application_tenants
INSERT INTO gar_application_tenants(application_id, tenant_id, id) VALUES ('1', '1', '1');

-- gar_groups
INSERT INTO gar_groups(id, name, created_time, modified_time, application_id, tenant_id, status, updated_by_user_name) VALUES ('1', '平台管理员组', '1522252800000', '1522252800000', '1', '1', '1', 'admin');
INSERT INTO gar_groups(id, name, created_time, modified_time, application_id, tenant_id, status, updated_by_user_name) VALUES ('2', 'SaaS模式的租户管理员组', '1522252800001', '1522252800000', '1', '1', '1', 'admin');
INSERT INTO gar_groups(id, name, created_time, modified_time, application_id, tenant_id, status, updated_by_user_name) VALUES ('3', '普通用户组', '1522252800002', '1522252800000', '1', '1', '1', 'admin');

-- gar_roles
INSERT INTO gar_roles(id, name, remark, created_time, modified_time, tenant_id, application_id, status, updated_by_user_name) VALUES('1', '平台管理员', '平台管理员', '1522252800000', '1522252800000', '1', '1', '1', 'admin');
INSERT INTO gar_roles(id, name, remark, created_time, modified_time, tenant_id, application_id, status, updated_by_user_name) VALUES('2', 'SaaS模式的租户管理员', 'SaaS模式的租户管理员', '1522252800001', '1522252800000', '1', '1', '1', 'admin');
INSERT INTO gar_roles(id, name, remark, created_time, modified_time, tenant_id, application_id, status, updated_by_user_name) VALUES('3', '普通用户', '普通用户', '1522252800002', '1522252800000', '1', '1', '1', 'admin');

-- gar_permissions
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('1', '/garnet/module/systemManage', '模块分类-系统管理', '模块分类部分的系统管理模块的可访问性', '1522252800000', '1522252800000', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('2', '/garnet/module/developerOption', '模块分类-开发选项', '模块分类部分的开发选项模块的可访问性', '1522252800001', '1522252800001', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('3', '/garnet/module/tenantManage', '模块-租户管理', '租户模块的可访问性', '1522252800002', '1522252800002', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('4', '/garnet/module/applicationManage', '模块-应用管理', '应用模块的可访问性', '1522252800003', '1522252800003', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('5', '/garnet/module/userManage', '模块-用户管理', '用户模块的可访问性', '1522252800004', '1522252800004', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('6', '/garnet/module/groupManage', '模块-组管理', '组模块的可访问性', '1522252800005', '1522252800005', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('7', '/garnet/module/roleManage', '模块-角色管理', '角色模块的可访问性', '1522252800006', '1522252800006', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('8', '/garnet/module/permissionManage', '模块-权限管理', '权限模块的可访问性', '1522252800007', '1522252800007', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('9', '/garnet/module/logManage', '模块-日志管理', '日志模块的可访问性', '1522252800008', '1522252800008', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('10', '/garnet/module/resourceManage', '模块-资源管理', '资源模块的可访问性', '1522252800009', '1522252800009', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('11', '/garnet/module/resourceTypeManage', '模块-资源类型管理', '资源类型模块的可访问性', '1522252800010', '1522252800010', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('12', '/garnet/module/ssoManage', '模块-单点登录管理', '单点登录管理模块的可访问性', '1522252800011', '1522252800011', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('13', '/garnet/button/tenantManage/add', '按钮-租户新增', '租户模块新增按钮的可用性', '1522252800012', '1522252800012', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('14', '/garnet/button/tenantManage/update', '按钮-租户配置', '租户模块配置按钮的可用性', '1522252800013', '1522252800013', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('15', '/garnet/button/tenantManage/delete', '按钮-租户删除', '租户模块删除按钮的可用性', '1522252800014', '1522252800014', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('16', '/garnet/button/applicationManage/add', '按钮-应用新增', '应用模块新增按钮的可用性', '1522252800015', '1522252800015', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('17', '/garnet/button/applicationManage/update', '按钮-应用配置', '应用模块配置按钮的可用性', '1522252800016', '1522252800016', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('18', '/garnet/button/applicationManage/delete', '按钮-应用删除', '应用模块删除按钮的可用性', '1522252800017', '1522252800017', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('19', '/garnet/button/userManage/add', '按钮-用户新增', '用户模块新增按钮的可用性', '1522252800018', '1522252800018', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('20', '/garnet/button/userManage/update', '按钮-用户配置', '用户模块配置按钮的可用性', '1522252800019', '1522252800019', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('21', '/garnet/button/userManage/delete', '按钮-用户删除', '用户模块删除按钮的可用性', '1522252800020', '1522252800020', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('22', '/garnet/button/groupManage/add', '按钮-组新增', '组模块新增按钮的可用性', '1522252800021', '1522252800021', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('23', '/garnet/button/groupManage/update', '按钮-组配置', '组模块配置按钮的可用性', '1522252800022', '1522252800022', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('24', '/garnet/button/groupManage/delete', '按钮-组删除', '组模块删除按钮的可用性', '1522252800023', '1522252800023', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('25', '/garnet/button/roleManage/add', '按钮-角色新增', '角色模块新增按钮的可用性', '1522252800024', '1522252800024', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('26', '/garnet/button/roleManage/update', '按钮-角色配置', '角色模块配置按钮的可用性', '1522252800025', '1522252800025', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('27', '/garnet/button/roleManage/delete', '按钮-角色删除', '角色模块删除按钮的可用性', '1522252800026', '1522252800026', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('28', '/garnet/button/permissionManage/add', '按钮-权限新增', '权限模块新增按钮的可用性', '1522252800027', '1522252800027', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('29', '/garnet/button/permissionManage/update', '按钮-权限删除', '权限模块配置按钮的可用性', '1522252800028', '1522252800028', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('30', '/garnet/button/permissionManage/delete', '按钮-权限更新', '权限模块删除按钮的可用性', '1522252800029', '1522252800029', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('31', '/garnet/button/resourceManage/add', '按钮-资源新增', '资源模块新增按钮的可用性', '1522252800030', '1522252800030', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('32', '/garnet/button/resourceManage/update', '按钮-资源配置', '资源模块配置按钮的可用性', '1522252800031', '1522252800031', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('33', '/garnet/button/resourceManage/delete', '按钮-资源删除', '资源模块删除按钮的可用性', '1522252800032', '1522252800032', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('34', '/garnet/button/resourceTypeManage/add', '按钮-资源类型新增', '资源类型模块新增按钮的可用性', '1522252800033', '1522252800033', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('35', '/garnet/button/resourceTypeManage/update', '按钮-资源类型配置', '资源类型模块配置按钮的可用性', '1522252800034', '1522252800034', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('36', '/garnet/button/resourceTypeManage/delete', '按钮-资源类型删除', '资源类型模块删除按钮的可用性', '1522252800035', '1522252800035', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('37', '/garnet/button/ssoManage/add', '按钮-单点登录新增', '单点登录新增按钮的可用性', '1522252800036', '1522252800036', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('38', '/garnet/button/ssoManage/update', '按钮-单点登录配置', '单点登录配置按钮的可用性', '1522252800037', '1522252800037', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('39', '/garnet/button/ssoManage/delete', '按钮-单点登录删除', '单点登录删除按钮的可用性', '1522252800038', '1522252800038', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('40', '/garnet/data/tenantManage/query/1', '数据-租户数据列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800039', '1522252800039', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('41', '/garnet/data/tenantManage/query/2', '数据-租户数据列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800040', '1522252800040', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('42', '/garnet/data/tenantManage/query/3', '数据-租户数据列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800041', '1522252800041', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('43', '/garnet/data/tenantManage/applicationList/1', '数据-租户管理应用选择列表-level1', '新增/配置时加载应用列表的数据', '1522252800042', '1522252800042', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('44', '/garnet/data/tenantManage/applicationList/2', '数据-租户管理应用选择列表-level2', '新增/配置时加载应用列表的数据', '1522252800043', '1522252800043', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('45', '/garnet/option/tenantManage/platformTypes/01', '选项-租户管理平台模式-type01-read', '新建租户时平台模式的选项', '1522252800044', '1522252800044', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('46', '/garnet/option/tenantManage/platformTypes/01', '选项-租户管理平台模式-type01-edit', '新建租户时平台模式的选项', '1522252800045', '1522252800045', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('47', '/garnet/option/tenantManage/platformTypes/10', '选项-租户管理平台模式-type10-read', '新建租户时平台模式的选项', '1522252800046', '1522252800046', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('48', '/garnet/option/tenantManage/platformTypes/10', '选项-租户管理平台模式-type10-edit', '新建租户时平台模式的选项', '1522252800047', '1522252800047', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('49', '/garnet/option/tenantManage/platformTypes/11', '选项-租户管理平台模式-type11-read', '新建租户时平台模式的选项', '1522252800048', '1522252800048', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('50', '/garnet/option/tenantManage/platformTypes/11', '选项-租户管理平台模式-type11-edit', '新建租户时平台模式的选项', '1522252800049', '1522252800049', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('51', '/garnet/option/tenantManage/userRelation', '选项-租户默认关联所有用户-read', '默认关联所有用户的选项，不可见则默认为N', '1522252800050', '1522252800050', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('176', '/garnet/option/tenantManage/userRelation', '选项-租户默认关联所有用户-edit', '默认关联所有用户的选项，不可见则默认为N', '1522252800051', '1522252800051', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('52', '/garnet/data/applicationManage/query/1', '数据-应用列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800052', '1522252800052', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('53', '/garnet/data/applicationManage/query/2', '数据-应用列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800053', '1522252800053', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('54', '/garnet/data/applicationManage/query/3', '数据-应用列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800054', '1522252800054', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('55', '/garnet/data/applicationManage/tenantList/1', '数据-应用管理租户选择列表-level1', '新增/配置应用时加载租户列表的数据', '1522252800055', '1522252800055', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('56', '/garnet/data/applicationManage/tenantList/2', '数据-应用管理租户选择列表-level2', '新增/配置应用时加载租户列表的数据', '1522252800056', '1522252800056', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('57', '/garnet/data/applicationManage/tenantList/3', '数据-应用管理租户选择列表-level3', '新增/配置应用时加载租户列表的数据', '1522252800057', '1522252800057', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('58', '/garnet/option/applicationManage/platformTypes/01', '选项-应用管理平台模式-type01-read', '新建应用时平台模式的选项', '1522252800058', '1522252800058', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('59', '/garnet/option/applicationManage/platformTypes/01', '选项-应用管理平台模式-type01-edit', '新建应用时平台模式的选项', '1522252800059', '1522252800059', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('60', '/garnet/option/applicationManage/platformTypes/10', '选项-应用管理平台模式-type10-read', '新建应用时平台模式的选项', '1522252800060', '1522252800060', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('61', '/garnet/option/applicationManage/platformTypes/10', '选项-应用管理平台模式-type10-edit', '新建应用时平台模式的选项', '1522252800061', '1522252800061', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('62', '/garnet/option/applicationManage/platformTypes/11', '选项-应用管理平台模式-type11-read', '新建应用时平台模式的选项', '1522252800062', '1522252800062', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('63', '/garnet/option/applicationManage/platformTypes/11', '选项-应用管理平台模式-type11-edit', '新建应用时平台模式的选项', '1522252800063', '1522252800063', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('64', '/garnet/data/userManage/query/1', '数据-用户列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800064', '1522252800064', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('65', '/garnet/data/userManage/query/2', '数据-用户列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800065', '1522252800065', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('66', '/garnet/data/userManage/query/3', '数据-用户列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800066', '1522252800066', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('67', '/garnet/data/userManage/query/4', '数据-用户列表-level4', '加载列表的数据，行为决定有无，level决定级别', '1522252800067', '1522252800067', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('68', '/garnet/data/userManage/tenantList/1', '数据-用户管理关联租户列表-level1', '新增/配置用户时加载关联租户列表的数据', '1522252800068', '1522252800068', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('69', '/garnet/data/userManage/tenantList/2', '数据-用户管理关联租户列表-level2', '新增/配置用户时加载关联租户列表的数据', '1522252800069', '1522252800069', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('70', '/garnet/data/userManage/tenantList/3', '数据-用户管理关联租户列表-level3', '新增/配置用户时加载关联租户列表的数据', '1522252800070', '1522252800070', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('71', '/garnet/data/userManage/garnetGroupList/1', '数据-为用户关联Garnet租户的组-level1', '新增/配置用户时加载关联Garnet租户的组列表的数据', '1522252800071', '1522252800071', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('72', '/garnet/data/userManage/garnetGroupList/2', '数据-为用户关联Garnet租户的组-level2', '新增/配置用户时加载关联Garnet租户的组列表的数据', '1522252800072', '1522252800072', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('73', '/garnet/data/userManage/garnetGroupList/3', '数据-为用户关联Garnet租户的组-level3', '新增/配置用户时加载关联Garnet租户的组列表的数据', '1522252800073', '1522252800073', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('74', '/garnet/data/groupManage/query/1', '数据-组列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800074', '1522252800074', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('75', '/garnet/data/groupManage/query/2', '数据-组列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800075', '1522252800075', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('76', '/garnet/data/groupManage/query/3', '数据-组列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800076', '1522252800076', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('77', 'garnet/option/groupManage/types/001', '选项-组管理新增页类型-type001-read', '新增页面的类型选项', '1522252800077', '1522252800077', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('78', 'garnet/option/groupManage/types/001', '选项-组管理新增页类型-type001-edit', '新增页面的类型选项', '1522252800078', '1522252800078', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('79', '/garnet/option/groupManage/types/010', '选项-组管理新增页类型-type010-read', '新增页面的类型选项', '1522252800079', '1522252800079', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('80', '/garnet/option/groupManage/types/010', '选项-组管理新增页类型-type010-edit', '新增页面的类型选项', '1522252800080', '1522252800080', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('81', '/garnet/option/groupManage/types/011', '选项-组管理新增页类型-type011-read', '新增页面的类型选项', '1522252800081', '1522252800081', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('82', '/garnet/option/groupManage/types/011', '选项-组管理新增页类型-type011-edit', '新增页面的类型选项', '1522252800082', '1522252800082', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('83', '/garnet/option/groupManage/types/100', '选项-组管理新增页类型-type100-read', '新增页面的类型选项', '1522252800083', '1522252800083', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('84', '/garnet/option/groupManage/types/100', '选项-组管理新增页类型-type100-edit', '新增页面的类型选项', '1522252800084', '1522252800084', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('85', '/garnet/option/groupManage/types/101', '选项-组管理新增页类型-type101-read', '新增页面的类型选项', '1522252800085', '1522252800085', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('86', '/garnet/option/groupManage/types/101', '选项-组管理新增页类型-type101-edit', '新增页面的类型选项', '1522252800086', '1522252800086', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('87', '/garnet/option/groupManage/types/110', '选项-组管理新增页类型-type110-read', '新增页面的类型选项', '1522252800087', '1522252800087', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('88', '/garnet/option/groupManage/types/110', '选项-组管理新增页类型-type110-edit', '新增页面的类型选项', '1522252800088', '1522252800088', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('89', '/garnet/option/groupManage/types/111', '选项-组管理新增页类型-type111-read', '新增页面的类型选项', '1522252800089', '1522252800089', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('90', '/garnet/option/groupManage/types/111', '选项-组管理新增页类型-type111-edit', '新增页面的类型选项', '1522252800090', '1522252800090', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('91', '/garnet/data/groupManage/tenantList/1', '数据-组管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800091', '1522252800091', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('92', '/garnet/data/groupManage/tenantList/2', '数据-组管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800092', '1522252800092', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('93', '/garnet/data/groupManage/tenantList/3', '数据-组管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800093', '1522252800093', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('94', '/garnet/data/roleManage/query/1', '数据-角色列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800094', '1522252800094', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('95', '/garnet/data/roleManage/query/2', '数据-角色列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800095', '1522252800095', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('96', '/garnet/data/roleManage/query/3', '数据-角色列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800096', '1522252800096', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('97', '/garnet/option/roleManage/types/001', '选项-角色管理新增页类型-type001-read', '新增页面的类型选项', '1522252800097', '1522252800097', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('98', '/garnet/option/roleManage/types/001', '选项-角色管理新增页类型-type001-edit', '新增页面的类型选项', '1522252800098', '1522252800098', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('99', '/garnet/option/roleManage/types/010', '选项-角色管理新增页类型-type010-read', '新增页面的类型选项', '1522252800099', '1522252800099', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('100', '/garnet/option/roleManage/types/010', '选项-角色管理新增页类型-type010-edit', '新增页面的类型选项', '1522252800100', '1522252800100', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('101', '/garnet/option/roleManage/types/011', '选项-角色管理新增页类型-type011-read', '新增页面的类型选项', '1522252800101', '1522252800101', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('102', '/garnet/option/roleManage/types/011', '选项-角色管理新增页类型-type011-edit', '新增页面的类型选项', '1522252800102', '1522252800102', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('103', '/garnet/option/roleManage/types/100', '选项-角色管理新增页类型-type100-read', '新增页面的类型选项', '1522252800103', '1522252800103', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('104', '/garnet/option/roleManage/types/100', '选项-角色管理新增页类型-type100-edit', '新增页面的类型选项', '1522252800104', '1522252800104', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('105', '/garnet/option/roleManage/types/101', '选项-角色管理新增页类型-type101-read', '新增页面的类型选项', '1522252800105', '1522252800105', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('106', '/garnet/option/roleManage/types/101', '选项-角色管理新增页类型-type101-edit', '新增页面的类型选项', '1522252800106', '1522252800106', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('107', '/garnet/option/roleManage/types/110', '选项-角色管理新增页类型-type110-read', '新增页面的类型选项', '1522252800107', '1522252800107', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('108', '/garnet/option/roleManage/types/110', '选项-角色管理新增页类型-type110-edit', '新增页面的类型选项', '1522252800108', '1522252800108', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('109', '/garnet/option/roleManage/types/111', '选项-角色管理新增页类型-type111-read', '新增页面的类型选项', '1522252800109', '1522252800109', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('110', '/garnet/option/roleManage/types/111', '选项-角色管理新增页类型-type111-edit', '新增页面的类型选项', '1522252800110', '1522252800110', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('111', '/garnet/data/roleManage/tenantList/1', '数据-角色管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800111', '1522252800111', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('112', '/garnet/data/roleManage/tenantList/2', '数据-角色管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800112', '1522252800112', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('113', '/garnet/data/roleManage/tenantList/3', '数据-角色管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800113', '1522252800113', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('114', '/garnet/data/permissionManage/query/1', '数据-权限列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800114', '1522252800114', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('115', '/garnet/data/permissionManage/query/2', '数据-权限列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800115', '1522252800115', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('116', '/garnet/data/permissionManage/query/3', '数据-权限列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800116', '1522252800116', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('117', '/garnet/option/permissionManage/types/001', '选项-权限管理新增页类型-type001-read', '新增页面的类型选项', '1522252800117', '1522252800117', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('118', '/garnet/option/permissionManage/types/001', '选项-权限管理新增页类型-type001-edit', '新增页面的类型选项', '1522252800118', '1522252800118', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('119', '/garnet/option/permissionManage/types/010', '选项-权限管理新增页类型-type010-read', '新增页面的类型选项', '1522252800119', '1522252800119', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('120', '/garnet/option/permissionManage/types/010', '选项-权限管理新增页类型-type010-edit', '新增页面的类型选项', '1522252800120', '1522252800120', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('121', '/garnet/option/permissionManage/types/011', '选项-权限管理新增页类型-type011-read', '新增页面的类型选项', '1522252800121', '1522252800121', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('122', '/garnet/option/permissionManage/types/011', '选项-权限管理新增页类型-type011-edit', '新增页面的类型选项', '1522252800122', '1522252800122', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('123', '/garnet/option/permissionManage/types/100', '选项-权限管理新增页类型-type100-read', '新增页面的类型选项', '1522252800123', '1522252800123', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('124', '/garnet/option/permissionManage/types/100', '选项-权限管理新增页类型-type100-edit', '新增页面的类型选项', '1522252800124', '1522252800124', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('125', '/garnet/option/permissionManage/types/101', '选项-权限管理新增页类型-type101-read', '新增页面的类型选项', '1522252800125', '1522252800125', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('126', '/garnet/option/permissionManage/types/101', '选项-权限管理新增页类型-type101-edit', '新增页面的类型选项', '1522252800126', '1522252800126', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('127', '/garnet/option/permissionManage/types/110', '选项-权限管理新增页类型-type110-read', '新增页面的类型选项', '1522252800127', '1522252800127', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('128', '/garnet/option/permissionManage/types/110', '选项-权限管理新增页类型-type110-edit', '新增页面的类型选项', '1522252800128', '1522252800128', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('129', '/garnet/option/permissionManage/types/111', '选项-权限管理新增页类型-type111-read', '新增页面的类型选项', '1522252800129', '1522252800129', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('130', '/garnet/option/permissionManage/types/111', '选项-权限管理新增页类型-type111-edit', '新增页面的类型选项', '1522252800130', '1522252800130', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('131', '/garnet/data/permissionManage/tenantList/1', '数据-权限管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800131', '1522252800131', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('132', '/garnet/data/permissionManage/tenantList/2', '数据-权限管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800132', '1522252800132', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('133', '/garnet/data/permissionManage/tenantList/3', '数据-权限管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800133', '1522252800133', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('134', '/garnet/data/resourceManage/query/1', '数据-资源列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800134', '1522252800134', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('135', '/garnet/data/resourceManage/query/2', '数据-资源列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800135', '1522252800135', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('136', '/garnet/data/resourceManage/query/3', '数据-资源列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800136', '1522252800136', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('137', '/garnet/option/resourceManage/types/001', '选项-资源管理新增页类型-type001-read', '新增页面的类型选项', '1522252800137', '1522252800137', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('138', '/garnet/option/resourceManage/types/001', '选项-资源管理新增页类型-type001-edit', '新增页面的类型选项', '1522252800138', '1522252800138', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('139', '/garnet/option/resourceManage/types/010', '选项-资源管理新增页类型-type010-read', '新增页面的类型选项', '1522252800139', '1522252800139', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('140', '/garnet/option/resourceManage/types/010', '选项-资源管理新增页类型-type010-edit', '新增页面的类型选项', '1522252800140', '1522252800140', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('141', '/garnet/option/resourceManage/types/011', '选项-资源管理新增页类型-type011-read', '新增页面的类型选项', '1522252800141', '1522252800141', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('142', '/garnet/option/resourceManage/types/011', '选项-资源管理新增页类型-type011-edit', '新增页面的类型选项', '1522252800142', '1522252800142', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('143', '/garnet/option/resourceManage/types/100', '选项-资源管理新增页类型-type100-read', '新增页面的类型选项', '1522252800143', '1522252800143', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('144', '/garnet/option/resourceManage/types/100', '选项-资源管理新增页类型-type100-edit', '新增页面的类型选项', '1522252800144', '1522252800144', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('145', '/garnet/option/resourceManage/types/101', '选项-资源管理新增页类型-type101-read', '新增页面的类型选项', '1522252800145', '1522252800145', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('146', '/garnet/option/resourceManage/types/101', '选项-资源管理新增页类型-type101-edit', '新增页面的类型选项', '1522252800146', '1522252800146', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('147', '/garnet/option/resourceManage/types/110', '选项-资源管理新增页类型-type110-read', '新增页面的类型选项', '1522252800147', '1522252800147', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('148', '/garnet/option/resourceManage/types/110', '选项-资源管理新增页类型-type110-edit', '新增页面的类型选项', '1522252800148', '1522252800148', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('149', '/garnet/option/resourceManage/types/111', '选项-资源管理新增页类型-type111-read', '新增页面的类型选项', '1522252800149', '1522252800149', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('150', '/garnet/option/resourceManage/types/111', '选项-资源管理新增页类型-type111-edit', '新增页面的类型选项', '1522252800150', '1522252800150', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('151', '/garnet/data/resourceManage/tenantList/1', '数据-资源管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800151', '1522252800151', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('152', '/garnet/data/resourceManage/tenantList/2', '数据-资源管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800152', '1522252800152', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('153', '/garnet/data/resourceManage/tenantList/3', '数据-资源管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800153', '1522252800153', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('154', '/garnet/data/resourceTypeManage/query/1', '数据-资源类型列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800154', '1522252800154', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('155', '/garnet/data/resourceTypeManage/query/2', '数据-资源类型列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800155', '1522252800155', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('156', '/garnet/data/resourceTypeManage/query/3', '数据-资源类型列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800156', '1522252800156', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('157', '/garnet/option/resourceTypeManage/types/001', '选项-资源类型管理新增页类型-type001-read', '新增页面的类型选项', '1522252800157', '1522252800157', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('158', '/garnet/option/resourceTypeManage/types/001', '选项-资源类型管理新增页类型-type001-edit', '新增页面的类型选项', '1522252800158', '1522252800158', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('159', '/garnet/option/resourceTypeManage/types/010', '选项-资源类型管理新增页类型-type010-read', '新增页面的类型选项', '1522252800159', '1522252800159', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('160', '/garnet/option/resourceTypeManage/types/010', '选项-资源类型管理新增页类型-type010-edit', '新增页面的类型选项', '1522252800160', '1522252800160', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('161', '/garnet/option/resourceTypeManage/types/011', '选项-资源类型管理新增页类型-type011-read', '新增页面的类型选项', '1522252800161', '1522252800161', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('162', '/garnet/option/resourceTypeManage/types/011', '选项-资源类型管理新增页类型-type011-edit', '新增页面的类型选项', '1522252800162', '1522252800162', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('163', '/garnet/option/resourceTypeManage/types/100', '选项-资源类型管理新增页类型-type100-read', '新增页面的类型选项', '1522252800163', '1522252800163', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('164', '/garnet/option/resourceTypeManage/types/100', '选项-资源类型管理新增页类型-type100-edit', '新增页面的类型选项', '1522252800164', '1522252800164', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('165', '/garnet/option/resourceTypeManage/types/101', '选项-资源类型管理新增页类型-type101-read', '新增页面的类型选项', '1522252800165', '1522252800165', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('166', '/garnet/option/resourceTypeManage/types/101', '选项-资源类型管理新增页类型-type101-edit', '新增页面的类型选项', '1522252800166', '1522252800166', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('167', '/garnet/option/resourceTypeManage/types/110', '选项-资源类型管理新增页类型-type110-read', '新增页面的类型选项', '1522252800167', '1522252800167', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('168', '/garnet/option/resourceTypeManage/types/110', '选项-资源类型管理新增页类型-type110-edit', '新增页面的类型选项', '1522252800168', '1522252800168', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('169', '/garnet/option/resourceTypeManage/types/111', '选项-资源类型管理新增页类型-type111-read', '新增页面的类型选项', '1522252800169', '1522252800169', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('170', '/garnet/option/resourceTypeManage/types/111', '选项-资源类型管理新增页类型-type111-edit', '新增页面的类型选项', '1522252800170', '1522252800170', '1', '1', 'edit', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('171', '/garnet/data/resourceTypeManage/tenantList/1', '数据-资源类型管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800171', '1522252800171', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('172', '/garnet/data/resourceTypeManage/tenantList/2', '数据-资源类型管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800172', '1522252800172', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('173', '/garnet/data/resourceTypeManage/tenantList/3', '数据-资源类型管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800173', '1522252800173', '1', '1', 'read', '1', 'admin');
INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('174', '/garnet/data/ssoManage/query/1', '数据-单点登录列表-level1', '单点登录应用组的数据，行为决定有无，level决定级别', '1522252800174', '1522252800174', '1', '1', 'read', '1', 'admin');
-- INSERT INTO gar_permissions(id, resource_path_wildcard, name, description, created_time, modified_time, application_id, tenant_id, action, status, updated_by_user_name) VALUES ('175', '/garnet/tenantManage/%', '本用户为租户管理员的租户', '标记用户为租户管理员的权限', '1522252800175', '1522252800175', '0', '1', 'read', '1', 'admin');

-- gar_role_permissions
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '1', '1');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '2', '2');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '3', '3');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '4', '4');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '5', '5');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '6', '6');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '7', '7');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '8', '8');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '9', '9');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '10', '10');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '11', '11');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '12', '12');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '13', '13');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '14', '14');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '15', '15');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '16', '16');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '17', '17');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '18', '18');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '19', '19');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '20', '20');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '21', '21');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '22', '22');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '23', '23');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '24', '24');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '25', '25');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '26', '26');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '27', '27');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '28', '28');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '29', '29');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '30', '30');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '31', '31');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '32', '32');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '33', '33');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '34', '34');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '35', '35');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '36', '36');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '37', '37');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '38', '38');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '39', '39');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '40', '40');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '43', '41');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '49', '42');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '50', '43');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '176', '44');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '52', '45');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '55', '46');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '62', '47');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '63', '48');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '64', '49');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '68', '50');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '71', '51');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '74', '52');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '89', '53');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '90', '54');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '91', '55');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '94', '56');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '109', '57');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '110', '58');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '111', '59');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '114', '60');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '129', '61');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '130', '62');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '131', '63');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '134', '64');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '149', '65');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '150', '66');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '151', '67');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '152', '68');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '153', '69');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '154', '70');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '169', '71');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '170', '72');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '171', '73');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '174', '74');
-- INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('1', '175', '75');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '1', '76');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '5', '77');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '6', '78');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '19', '79');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '20', '80');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '21', '81');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '22', '82');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '23', '83');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '24', '84');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '67', '85');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '66', '86');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '70', '87');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '72', '88');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '76', '89');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '88', '90');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('2', '93', '91');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('3', '5', '92');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('3', '20', '93');
INSERT INTO gar_role_permissions(role_id, permission_id, id) VALUES ('3', '67', '94');

-- gar_group_role
INSERT INTO gar_group_roles(role_id, group_id, id) VALUES ('1', '1', '1');
INSERT INTO gar_group_roles(role_id, group_id, id) VALUES ('2', '2', '2');
INSERT INTO gar_group_roles(role_id, group_id, id) VALUES ('3', '3', '3');

-- gar_group_user
INSERT INTO gar_group_users(group_id, user_id, id) VALUES ('1', '1', '1');

-- gar_resource_dynamic_props
-- Garnet系统按钮
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('1', 'garnet_button', '1', '1', 'varchar00', '填 code name', 'read', 'Garnet系统按钮', 'Garnet系统按钮', '1522252800000', '1522252800000', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('2', 'garnet_button', '1', '1', 'varchar01', '填 true/false', 'read', 'Garnet系统按钮', 'Garnet系统按钮', '1522252800001', '1522252800001', 'admin');
-- Garnet系统模块
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('3', 'garnet_module', '1', '1', 'varchar00', 'menuId', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800002', '1522252800002', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('4', 'garnet_module', '1', '1', 'varchar01', 'parentId', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800003', '1522252800003', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('5', 'garnet_module', '1', '1', 'varchar02', 'parentName', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800004', '1522252800004', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('6', 'garnet_module', '1', '1', 'varchar03', 'name', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800005', '1522252800005', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('7', 'garnet_module', '1', '1', 'varchar04', 'url', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800006', '1522252800006', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('8', 'garnet_module', '1', '1', 'varchar05', 'type', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800007', '1522252800007', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('9', 'garnet_module', '1', '1', 'varchar06', 'icon', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800008', '1522252800008', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('10', 'garnet_module', '1', '1', 'varchar07', 'code', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800009', '1522252800009', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('11', 'garnet_module', '1', '1', 'varchar08', 'orderNum', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800010', '1522252800010', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('12', 'garnet_module', '1', '1', 'varchar09', 'open', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800011', '1522252800011', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('13', 'garnet_module', '1', '1', 'varchar10', 'list', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800012', '1522252800012', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('14', 'garnet_module', '1', '1', 'varchar11', '填 code name', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800013', '1522252800013', 'admin');
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('15', 'garnet_module', '1', '1', 'varchar12', '填 true/false', 'read', 'Garnet系统模块', 'Garnet系统的模块可访问性', '1522252800014', '1522252800014', 'admin');
-- Garnet数据权限
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('16', 'garnet_data_permission', '1', '1', 'varchar00', 'level', 'read', 'Garnet数据权限', 'Garnet数据权限等级设置', '1522252800015', '1522252800015', 'admin');
-- Garnet数据选项
INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('17', 'garnet_system_option', '1', '1', 'varchar00', 'type', 'read,edit', 'Garnet数据选项', 'Garnet系统提供的选项', '1522252800016', '1522252800016', 'admin');
-- Garnet租户管理员
-- INSERT INTO gar_resource_dynamic_props (id, type, application_id, tenant_id, filed_name, description, actions, name, remark, created_time, modified_time, updated_by_user_name) VALUES ('18', 'tenant_manage', '1', '1', 'varchar00', 'type', 'read,edit', '租户管理的权限', '租户管理的权限', '1522252800017', '1522252800017', 'admin');

-- gar_resources
-- Garnet系统模块
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('1', '1', '/garnet/module/systemManage', '模块分类-系统管理', '模块分类部分的系统管理模块的可访问性', '1522252800000', '1522252800000', 'garnet_module', '1', '1', '0', '', '系统管理', '', '0', 'fa fa-cog', 'garnetSysManagement', '0', '', '', 'garnetSysManagement', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('2', '1', '/garnet/module/tenantManage', '模块分类-开发选项', '模块分类部分的开发选项模块的可访问性', '1522252800001', '1522252800001', 'garnet_module', '1', '2', '1', '', '租户管理', 'modules/tenant.html', '1', 'fa fa-address-book', 'garnetSysManagementTenant', '1', '', '', 'garnetSysManagementTenant', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('3', '1', '/garnet/module/applicationManage', '模块-租户管理', '租户模块的可访问性', '1522252800002', '1522252800002', 'garnet_module', '1', '3', '1', '', '应用管理', 'modules/application.html', '1', 'fa fa-th-large', 'garnetSysManagementApplication', '2', '', '', 'garnetSysManagementApplication', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('4', '1', '/garnet/module/userManage', '模块-应用管理', '应用模块的可访问性', '1522252800003', '1522252800003', 'garnet_module', '1', '4', '1', '', '用户管理', 'modules/user.html', '1', 'fa fa fa-user', 'garnetSysManagementUser', '3', '', '', 'garnetSysManagementUser', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('5', '1', '/garnet/module/groupManage', '模块-用户管理', '用户模块的可访问性', '1522252800004', '1522252800004', 'garnet_module', '1', '5', '1', '', '组管理', 'modules/group.html', '1', 'fa fa-institution', 'garnetSysManagementGroup', '4', '', '', 'garnetSysManagementGroup', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('6', '1', '/garnet/module/roleManage', '模块-组管理', '组模块的可访问性', '1522252800005', '1522252800005', 'garnet_module', '1', '6', '1', '', '角色管理', 'modules/role.html', '1', 'fa fa-group', 'garnetSysManagementRole', '5', '', '', 'garnetSysManagementRole', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('7', '1', '/garnet/module/permissionManage', '模块-角色管理', '角色模块的可访问性', '1522252800006', '1522252800006', 'garnet_module', '1', '7', '1', '', '权限管理', 'modules/permission.html', '1', 'fa fa-th-list', 'garnetSysManagementPermission', '6', '', '', 'garnetSysManagementPermission', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('8', '1', '/garnet/module/logManage', '模块-权限管理', '权限模块的可访问性', '1522252800007', '1522252800007', 'garnet_module', '1', '8', '1', '', '系统日志', 'modules/log.html', '1', 'fa fa-file-text-o', 'garnetSysManagementLog', '7', '', '', 'garnetSysManagementLog', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('9', '1', '/garnet/module/developerOption', '模块-日志管理', '日志模块的可访问性', '1522252800008', '1522252800008', 'garnet_module', '1', '9', '0', '', '开发选项', '', '0', 'fa fa-cog', 'garnetDevelopment', '0', '', '', 'garnetDevelopment', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('10', '1', '/garnet/module/resourceManage', '模块-资源管理', '资源模块的可访问性', '1522252800009', '1522252800009', 'garnet_module', '1', '10', '9', '', '资源管理', 'modules/resource.html', '1', 'fa fa-th-list', 'garnetDevelopmentResource', '1', '', '', 'garnetDevelopmentResource', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('11', '1', '/garnet/module/resourceTypeManage', '模块-资源类型管理', '资源类型模块的可访问性', '1522252800010', '1522252800010', 'garnet_module', '1', '12', '9', '', '资源类型配置', 'modules/resourceDynamicProperty.html', '1', 'fa fa-th-list', 'garnetDevelopmentresourceDynamicProperty', '3', '', '', 'garnetDevelopmentresourceDynamicProperty', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, varchar_11, varchar_12, updated_by_user_name) VALUES ('12', '1', '/garnet/module/ssoManage', '模块-单点登录管理', '单点登录管理模块的可访问性', '1522252800011', '1522252800011', 'garnet_module', '1', '13', '9', '', '单点登录应用组', 'modules/routerGroup.html', '1', 'fa fa-th-list', 'garnetDevelopmentRouterGroup', '4', '', '', 'garnetDevelopmentRouterGroup', 'true', 'admin');

--  Garnet系统按钮
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('13', '1', '/garnet/button/tenantManage/add', '按钮-租户新增', '租户模块新增按钮的可用性', '1522252800012', '1522252800012', 'garnet_button', '1', 'garnetSysManagementTenantAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('14', '1', '/garnet/button/tenantManage/update', '按钮-租户配置', '租户模块配置按钮的可用性', '1522252800013', '1522252800013', 'garnet_button', '1', 'garnetSysManagementTenantUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('15', '1', '/garnet/button/tenantManage/delete', '按钮-租户删除', '租户模块删除按钮的可用性', '1522252800014', '1522252800014', 'garnet_button', '1', 'garnetSysManagementTenantDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('16', '1', '/garnet/button/applicationManage/add', '按钮-应用新增', '应用模块新增按钮的可用性', '1522252800015', '1522252800015', 'garnet_button', '1', 'garnetSysManagementApplicationAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('17', '1', '/garnet/button/applicationManage/update', '按钮-应用配置', '应用模块配置按钮的可用性', '1522252800016', '1522252800016', 'garnet_button', '1', 'garnetSysManagementApplicationUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('18', '1', '/garnet/button/applicationManage/delete', '按钮-应用删除', '应用模块删除按钮的可用性', '1522252800017', '1522252800017', 'garnet_button', '1', 'garnetSysManagementApplicationDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('19', '1', '/garnet/button/userManage/add', '按钮-用户新增', '用户模块新增按钮的可用性', '1522252800018', '1522252800018', 'garnet_button', '1', 'garnetSysManagementUsernAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('20', '1', '/garnet/button/userManage/update', '按钮-用户配置', '用户模块配置按钮的可用性', '1522252800019', '1522252800019', 'garnet_button', '1', 'garnetSysManagementUserUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('21', '1', '/garnet/button/userManage/delete', '按钮-用户删除', '用户模块删除按钮的可用性', '1522252800020', '1522252800020', 'garnet_button', '1', 'garnetSysManagementUserDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('22', '1', '/garnet/button/groupManage/add', '按钮-组新增', '组模块新增按钮的可用性', '1522252800021', '1522252800021', 'garnet_button', '1', 'garnetSysManagementGroupAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('23', '1', '/garnet/button/groupManage/update', '按钮-组配置', '组模块配置按钮的可用性', '1522252800022', '1522252800022', 'garnet_button', '1', 'garnetSysManagementGroupUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('24', '1', '/garnet/button/groupManage/delete', '按钮-组删除', '组模块删除按钮的可用性', '1522252800023', '1522252800023', 'garnet_button', '1', 'garnetSysManagementGroupDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('25', '1', '/garnet/button/roleManage/add', '按钮-角色新增', '角色模块新增按钮的可用性', '1522252800024', '1522252800024', 'garnet_button', '1', 'garnetSysManagementRoleAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('26', '1', '/garnet/button/roleManage/update', '按钮-角色配置', '角色模块配置按钮的可用性', '1522252800025', '1522252800025', 'garnet_button', '1', 'garnetSysManagementRoleUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('27', '1', '/garnet/button/roleManage/delete', '按钮-角色删除', '角色模块删除按钮的可用性', '1522252800026', '1522252800026', 'garnet_button', '1', 'garnetSysManagementRoleDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('28', '1', '/garnet/button/permissionManage/add', '按钮-权限新增', '权限模块新增按钮的可用性', '1522252800027', '1522252800027', 'garnet_button', '1', 'garnetSysManagementPermissionAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('29', '1', '/garnet/button/permissionManage/update', '按钮-权限删除', '权限模块配置按钮的可用性', '1522252800028', '1522252800028', 'garnet_button', '1', 'garnetSysManagementPermissionDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('30', '1', '/garnet/button/permissionManage/delete', '按钮-权限更新', '权限模块删除按钮的可用性', '1522252800029', '1522252800029', 'garnet_button', '1', 'garnetSysManagementPermissionUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('31', '1', '/garnet/button/resourceManage/add', '按钮-资源新增', '资源模块新增按钮的可用性', '1522252800030', '1522252800030', 'garnet_button', '1', 'garnetDevelopmentResourceAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('32', '1', '/garnet/button/resourceManage/update', '按钮-资源配置', '资源模块配置按钮的可用性', '1522252800031', '1522252800031', 'garnet_button', '1', 'garnetDevelopmentResourceUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('33', '1', '/garnet/button/resourceManage/delete', '按钮-资源删除', '资源模块删除按钮的可用性', '1522252800032', '1522252800032', 'garnet_button', '1', 'garnetDevelopmentResourceDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('34', '1', '/garnet/button/resourceTypeManage/add', '按钮-资源类型新增', '资源类型模块新增按钮的可用性', '1522252800033', '1522252800033', 'garnet_button', '1', 'garnetDevelopmentresourceDynamicPropertyAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('35', '1', '/garnet/button/resourceTypeManage/update', '按钮-资源类型配置', '资源类型模块配置按钮的可用性', '1522252800034', '1522252800034', 'garnet_button', '1', 'garnetDevelopmentresourceDynamicPropertyUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('36', '1', '/garnet/button/resourceTypeManage/delete', '按钮-资源类型删除', '资源类型模块删除按钮的可用性', '1522252800035', '1522252800035', 'garnet_button', '1', 'garnetDevelopmentresourceDynamicPropertyDelete', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('37', '1', '/garnet/button/ssoManage/add', '按钮-单点登录新增', '单点登录新增按钮的可用性', '1522252800036', '1522252800036', 'garnet_button', '1', 'garnetDevelopmentRouterGroupAdd', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('38', '1', '/garnet/button/ssoManage/update', '按钮-单点登录配置', '单点登录配置按钮的可用性', '1522252800037', '1522252800037', 'garnet_button', '1', 'garnetDevelopmentRouterGroupUpdate', 'true', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('39', '1', '/garnet/button/ssoManage/delete', '按钮-单点登录删除', '单点登录删除按钮的可用性', '1522252800038', '1522252800038', 'garnet_button', '1', 'garnetDevelopmentRouterGroupDelete', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('40', '1', '/garnet/DevelopmentApi', 'appCode_garnetDevelopmentApi', 'Api管理菜单', '1522252800039', '1522252800039', 'garnet_appCode', '1', 'garnetDevelopmentApi', 'false', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('41', '1', '/garnet/DevelopmentApiAdd', 'appCode_garnetDevelopmentApiAdd', 'Api管理-新增按钮', '1522252800040', '1522252800040', 'garnet_appCode', '1', 'garnetDevelopmentApiAdd', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('42', '1', '/garnet/DevelopmentApiUpdate', 'appCode_garnetDevelopmentApiUpdate', 'Api管理-配置按钮', '1522252800041', '1522252800041', 'garnet_appCode', '1', 'garnetDevelopmentApiUpdate', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('43', '1', '/garnet/DevelopmentApiDelete', 'appCode_garnetDevelopmentApiDelete', 'Api管理-删除按钮', '1522252800042', '1522252800042', 'garnet_appCode', '1', 'garnetDevelopmentApiDelete', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('44', '1', '/garnet/garnet', 'appCode_garnet', '控制菜单显示-garnet', '1522252800043', '1522252800043', 'garnet_appCode', '1', 'garnet', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('45', '1', '/garnet/DevelopmentAllApi', 'appCode_garnetDevelopmentAllApi', '控制菜单显示-DevelopmentAllApi', '1522252800044', '1522252800044', 'garnet_appCode', '1', 'garnetDevelopmentAllApi', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('46', '1', '/garnet/SysManagementTenantSearchBox', 'appCode_garnetSysManagementTenantSearchBox', '控制菜单显示-SysManagementTenantSearchBox', '1522252800045', '1522252800045', 'garnet_appCode', '1', 'garnetSysManagementTenantSearchBox', 'true', 'admin');
-- 菜单配置 sysMenu
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('47', '1', '/garnet/config/systemManage', '菜单配置-系统管理', '系统管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '1', '0', '', '系统管理', '', '0', 'fa fa-cog', 'garnetSysManagement', '0', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('48', '1', '/garnet/config/tenantManage', '菜单配置-租户管理', '租户管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '2', '1', '', '租户管理', 'modules/tenant.html', '1', 'fa fa-address-book', 'garnetSysManagementTenant', '1', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('49', '1', '/garnet/config/applicationManage', '菜单配置-应用管理', '管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '3', '1', '', '应用管理', 'modules/application.html', '1', 'fa fa-th-large', 'garnetSysManagementApplication', '2', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('50', '1', '/garnet/config/userManage', '菜单配置-用户管理', '用户管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '4', '1', '', '用户管理', 'modules/user.html', '1', 'fa fa fa-user', 'garnetSysManagementUser', '3', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('51', '1', '/garnet/config/groupManage', '菜单配置-组管理', '组管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '5', '1', '', '组管理', 'modules/group.html', '1', 'fa fa-institution', 'garnetSysManagementGroup', '4', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('52', '1', '/garnet/config/roleManage', '菜单配置-角色管理', '角色管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '6', '1', '', '角色管理', 'modules/role.html', '1', 'fa fa-group', 'garnetSysManagementRole', '5', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('53', '1', '/garnet/config/permissionManage', '菜单配置-权限管理', '权限管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '7', '1', '', '权限管理', 'modules/permission.html', '1', 'fa fa-th-list', 'garnetSysManagementPermission', '6', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('54', '1', '/garnet/config/logManage', '菜单配置-日志管理', '日志管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '8', '1', '', '系统日志', 'modules/log.html', '1', 'fa fa-file-text-o', 'garnetSysManagementLog', '7', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('55', '1', '/garnet/config/developerOption', '菜单配置-开发选项', '开发选项配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '9', '0', '', '开发选项', '', '0', 'fa fa-cog', 'garnetDevelopment', '0', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('56', '1', '/garnet/config/resourceManage', '菜单配置-资源管理', '资源管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '10', '9', '', '资源管理', 'modules/resource.html', '1', 'fa fa-th-list', 'garnetDevelopmentResource', '1', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('57', '1', '/garnet/config/resourceTypeManage', '菜单配置-资源类型配置', '资源类型管理配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '12', '9', '', '资源类型配置', 'modules/resourceDynamicProperty.html', '1', 'fa fa-th-list', 'garnetDevelopmentresourceDynamicProperty', '3', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('58', '1', '/garnet/config/ssoManage', '菜单配置-单点登录应用组', '单点登录应用组配置', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '13', '9', '', '单点登录应用组', 'modules/routerGroup.html', '1', 'fa fa-th-list', 'garnetDevelopmentRouterGroup', '4', '', '', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, actions, name, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, varchar_02, varchar_03, varchar_04, varchar_05, varchar_06, varchar_07, varchar_08, varchar_09, varchar_10, updated_by_user_name) VALUES ('59', '1', '/garnet/DevelopmentApi',  '', '菜单配置-Api管理', '1522252800000', '1522252800000', 'garnet_sysMenu', '1', '11', '9', '', 'API', 'modules/api.html', '1', 'fa fa-th-list', 'garnetDevelopmentApi', '2', '', '', 'admin');
-- 配置页面确认按钮
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('60', '1', '/garnet/button/tenantManage/submit', '按钮-租户配置确认', '租户配置页面确认按钮的可用性', '1522252800000', '1522252800000', 'garnet_tenantUpdateButton', '1', 'tenantUpdateButton', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('61', '1', '/garnet/button/applicationManage/submit', '按钮-应用配置确认', '应用配置页面确认按钮的可用性', '1522252800000', '1522252800000', 'garnet_applicationUpdateButton', '1', 'appUpdateButton', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('62', '1', '/garnet/button/groupManage/submit', '按钮-组配置确认', '组配置页面确认按钮的可用性', '1522252800000', '1522252800000', 'garnet_groupUpdateButton', '1', 'groupUpdateButton', 'true', 'admin');
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, varchar_01, updated_by_user_name) VALUES ('63', '1', '/garnet/button/roleManage/submit', '按钮-角色配置确认', '角色配置页面确认按钮的可用性', '1522252800000', '1522252800000', 'garnet_roleUpdateButton', '1', 'roleUpdateButton', 'true', 'admin');
-- tenant
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('64', '1', '/garnet/data/tenantManage/query/1', '数据-租户数据列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800046', '1522252800046', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('65', '1', '/garnet/data/tenantManage/query/2', '数据-租户数据列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800047', '1522252800047', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('66', '1', '/garnet/data/tenantManage/query/3', '数据-租户数据列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800048', '1522252800048', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('67', '1', '/garnet/data/tenantManage/applicationList/1', '数据-租户管理应用选择列表-level1', '新增/配置时加载应用列表的数据', '1522252800049', '1522252800049', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('68', '1', '/garnet/data/tenantManage/applicationList/2', '数据-租户管理应用选择列表-level2', '新增/配置时加载应用列表的数据', '1522252800050', '1522252800050', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('69', '1', '/garnet/option/tenantManage/platformTypes/01', '选项-租户管理平台模式-type01', '新建租户时平台模式的选项', '1522252800051', '1522252800051', 'garnet_system_option', '1', '01', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('70', '1', '/garnet/option/tenantManage/platformTypes/10', '选项-租户管理平台模式-type10', '新建租户时平台模式的选项', '1522252800052', '1522252800052', 'garnet_system_option', '1', '10', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('71', '1', '/garnet/option/tenantManage/platformTypes/11', '选项-租户管理平台模式-type11', '新建租户时平台模式的选项', '1522252800053', '1522252800053', 'garnet_system_option', '1', '11', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('72', '1', '/garnet/option/tenantManage/userRelation', '选项-租户默认关联所有用户', '默认关联所有用户的选项，不可见则默认为N', '1522252800054', '1522252800054', 'garnet_system_option', '1', '', 'admin');
-- application
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('73', '1', '/garnet/data/applicationManage/query/1', '数据-应用列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800055', '1522252800055', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('74', '1', '/garnet/data/applicationManage/query/2', '数据-应用列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800056', '1522252800056', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('75', '1', '/garnet/data/applicationManage/query/3', '数据-应用列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800057', '1522252800057', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('76', '1', '/garnet/data/applicationManage/tenantList/1', '数据-应用管理租户选择列表-level1', '新增/配置应用时加载租户列表的数据', '1522252800058', '1522252800058', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('77', '1', '/garnet/data/applicationManage/tenantList/2', '数据-应用管理租户选择列表-level2', '新增/配置应用时加载租户列表的数据', '1522252800059', '1522252800059', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('78', '1', '/garnet/data/applicationManage/tenantList/3', '数据-应用管理租户选择列表-level3', '新增/配置应用时加载租户列表的数据', '1522252800060', '1522252800060', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('79', '1', '/garnet/option/applicationManage/platformTypes/01', '选项-应用管理平台模式-type01', '新建应用时平台模式的选项', '1522252800061', '1522252800061', 'garnet_system_option', '1', '01', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('80', '1', '/garnet/option/applicationManage/platformTypes/10', '选项-应用管理平台模式-type10', '新建应用时平台模式的选项', '1522252800062', '1522252800062', 'garnet_system_option', '1', '10', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('81', '1', '/garnet/option/applicationManage/platformTypes/11', '选项-应用管理平台模式-type11', '新建应用时平台模式的选项', '1522252800063', '1522252800063', 'garnet_system_option', '1', '11', 'admin');
-- user
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('82', '1', '/garnet/data/userManage/query/1', '数据-用户列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800064', '1522252800064', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('83', '1', '/garnet/data/userManage/query/2', '数据-用户列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800065', '1522252800065', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('84', '1', '/garnet/data/userManage/query/3', '数据-用户列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800066', '1522252800066', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('85', '1', '/garnet/data/userManage/query/4', '数据-用户列表-level4', '加载列表的数据，行为决定有无，level决定级别', '1522252800067', '1522252800067', 'garnet_data_permission', '1', '4', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('86', '1', '/garnet/data/userManage/tenantList/1', '数据-用户管理关联租户列表-level1', '新增/配置用户时加载关联租户列表的数据', '1522252800068', '1522252800068', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('87', '1', '/garnet/data/userManage/tenantList/2', '数据-用户管理关联租户列表-level2', '新增/配置用户时加载关联租户列表的数据', '1522252800069', '1522252800069', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('88', '1', '/garnet/data/userManage/tenantList/3', '数据-用户管理关联租户列表-level3', '新增/配置用户时加载关联租户列表的数据', '1522252800070', '1522252800070', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('89', '1', '/garnet/data/userManage/garnetGroupList/1', '数据-为用户关联Garnet租户的组-level1', '新增/配置用户时加载关联Garnet租户的组列表的数据', '1522252800071', '1522252800071', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('90', '1', '/garnet/data/userManage/garnetGroupList/2', '数据-为用户关联Garnet租户的组-level2', '新增/配置用户时加载关联Garnet租户的组列表的数据', '1522252800072', '1522252800072', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('91', '1', '/garnet/data/userManage/garnetGroupList/3', '数据-为用户关联Garnet租户的组-level3', '新增/配置用户时加载关联Garnet租户的组列表的数据', '1522252800073', '1522252800073', 'garnet_data_permission', '1', '3', 'admin');
-- group
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('92', '1', '/garnet/data/groupManage/query/1', '数据-组列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800074', '1522252800074', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('93', '1', '/garnet/data/groupManage/query/2', '数据-组列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800075', '1522252800075', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('94', '1', '/garnet/data/groupManage/query/3', '数据-组列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800076', '1522252800076', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('95', '1', '/garnet/option/groupManage/types/001', '选项-组管理新增页类型-type001', '新增页面的类型选项', '1522252800077', '1522252800077', 'garnet_system_option', '1', '001', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('96', '1', '/garnet/option/groupManage/types/010', '选项-组管理新增页类型-type010', '新增页面的类型选项', '1522252800078', '1522252800078', 'garnet_system_option', '1', '010', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('97', '1', '/garnet/option/groupManage/types/011', '选项-组管理新增页类型-type011', '新增页面的类型选项', '1522252800079', '1522252800079', 'garnet_system_option', '1', '011', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('98', '1', '/garnet/option/groupManage/types/100', '选项-组管理新增页类型-type100', '新增页面的类型选项', '1522252800080', '1522252800080', 'garnet_system_option', '1', '100', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('99', '1', '/garnet/option/groupManage/types/101', '选项-组管理新增页类型-type101', '新增页面的类型选项', '1522252800081', '1522252800081', 'garnet_system_option', '1', '101', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('100', '1', '/garnet/option/groupManage/types/110', '选项-组管理新增页类型-type110', '新增页面的类型选项', '1522252800082', '1522252800082', 'garnet_system_option', '1', '110', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('101', '1', '/garnet/option/groupManage/types/111', '选项-组管理新增页类型-type111', '新增页面的类型选项', '1522252800083', '1522252800083', 'garnet_system_option', '1', '111', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('102', '1', '/garnet/data/groupManage/tenantList/1', '数据-组管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800084', '1522252800084', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('103', '1', '/garnet/data/groupManage/tenantList/2', '数据-组管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800085', '1522252800085', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('104', '1', '/garnet/data/groupManage/tenantList/3', '数据-组管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800086', '1522252800086', 'garnet_data_permission', '1', '3', 'admin');
-- role
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('105', '1', '/garnet/data/roleManage/query/1', '数据-角色列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800087', '1522252800087', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('106', '1', '/garnet/data/roleManage/query/2', '数据-角色列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800088', '1522252800088', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('107', '1', '/garnet/data/roleManage/query/3', '数据-角色列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800089', '1522252800089', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('108', '1', '/garnet/option/roleManage/types/001', '选项-角色管理新增页类型-type001', '新增页面的类型选项', '1522252800090', '1522252800090', 'garnet_system_option', '1', '001', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('109', '1', '/garnet/option/roleManage/types/010', '选项-角色管理新增页类型-type010', '新增页面的类型选项', '1522252800091', '1522252800091', 'garnet_system_option', '1', '010', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('110', '1', '/garnet/option/roleManage/types/011', '选项-角色管理新增页类型-type011', '新增页面的类型选项', '1522252800092', '1522252800092', 'garnet_system_option', '1', '011', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('111', '1', '/garnet/option/roleManage/types/100', '选项-角色管理新增页类型-type100', '新增页面的类型选项', '1522252800093', '1522252800093', 'garnet_system_option', '1', '100', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('112', '1', '/garnet/option/roleManage/types/101', '选项-角色管理新增页类型-type101', '新增页面的类型选项', '1522252800094', '1522252800094', 'garnet_system_option', '1', '101', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('113', '1', '/garnet/option/roleManage/types/110', '选项-角色管理新增页类型-type110', '新增页面的类型选项', '1522252800095', '1522252800095', 'garnet_system_option', '1', '110', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('114', '1', '/garnet/option/roleManage/types/111', '选项-角色管理新增页类型-type111', '新增页面的类型选项', '1522252800096', '1522252800096', 'garnet_system_option', '1', '111', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('115', '1', '/garnet/data/roleManage/tenantList/1', '数据-角色管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800097', '1522252800097', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('116', '1', '/garnet/data/roleManage/tenantList/2', '数据-角色管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800098', '1522252800098', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('117', '1', '/garnet/data/roleManage/tenantList/3', '数据-角色管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800099', '1522252800099', 'garnet_data_permission', '1', '3', 'admin');
-- permission
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('118', '1', '/garnet/data/permissionManage/query/1', '数据-权限列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800100', '1522252800100', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('119', '1', '/garnet/data/permissionManage/query/2', '数据-权限列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800101', '1522252800101', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('120', '1', '/garnet/data/permissionManage/query/3', '数据-权限列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800102', '1522252800102', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('121', '1', '/garnet/option/permissionManage/types/001', '选项-权限管理新增页类型-type001', '新增页面的类型选项', '1522252800103', '1522252800103', 'garnet_system_option', '1', '001', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('122', '1', '/garnet/option/permissionManage/types/010', '选项-权限管理新增页类型-type010', '新增页面的类型选项', '1522252800104', '1522252800104', 'garnet_system_option', '1', '010', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('123', '1', '/garnet/option/permissionManage/types/011', '选项-权限管理新增页类型-type011', '新增页面的类型选项', '1522252800105', '1522252800105', 'garnet_system_option', '1', '011', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('124', '1', '/garnet/option/permissionManage/types/100', '选项-权限管理新增页类型-type100', '新增页面的类型选项', '1522252800106', '1522252800106', 'garnet_system_option', '1', '100', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('125', '1', '/garnet/option/permissionManage/types/101', '选项-权限管理新增页类型-type101', '新增页面的类型选项', '1522252800107', '1522252800107', 'garnet_system_option', '1', '101', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('126', '1', '/garnet/option/permissionManage/types/110', '选项-权限管理新增页类型-type110', '新增页面的类型选项', '1522252800108', '1522252800108', 'garnet_system_option', '1', '110', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('127', '1', '/garnet/option/permissionManage/types/111', '选项-权限管理新增页类型-type111', '新增页面的类型选项', '1522252800109', '1522252800109', 'garnet_system_option', '1', '111', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('128', '1', '/garnet/data/permissionManage/tenantList/1', '数据-权限管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800110', '1522252800110', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('129', '1', '/garnet/data/permissionManage/tenantList/2', '数据-权限管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800111', '1522252800111', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('130', '1', '/garnet/data/permissionManage/tenantList/3', '数据-权限管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800112', '1522252800112', 'garnet_data_permission', '1', '3', 'admin');
-- resource
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('131', '1', '/garnet/data/resourceManage/query/1', '数据-资源列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800113', '1522252800113', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('132', '1', '/garnet/data/resourceManage/query/2', '数据-资源列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800114', '1522252800114', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('133', '1', '/garnet/data/resourceManage/query/3', '数据-资源列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800115', '1522252800115', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('134', '1', '/garnet/option/resourceManage/types/001', '选项-资源管理新增页类型-type001', '新增页面的类型选项', '1522252800116', '1522252800116', 'garnet_system_option', '1', '001', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('135', '1', '/garnet/option/resourceManage/types/010', '选项-资源管理新增页类型-type010', '新增页面的类型选项', '1522252800117', '1522252800117', 'garnet_system_option', '1', '010', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('136', '1', '/garnet/option/resourceManage/types/011', '选项-资源管理新增页类型-type011', '新增页面的类型选项', '1522252800118', '1522252800118', 'garnet_system_option', '1', '011', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('137', '1', '/garnet/option/resourceManage/types/100', '选项-资源管理新增页类型-type100', '新增页面的类型选项', '1522252800119', '1522252800119', 'garnet_system_option', '1', '100', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('138', '1', '/garnet/option/resourceManage/types/101', '选项-资源管理新增页类型-type101', '新增页面的类型选项', '1522252800120', '1522252800120', 'garnet_system_option', '1', '101', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('139', '1', '/garnet/option/resourceManage/types/110', '选项-资源管理新增页类型-type110', '新增页面的类型选项', '1522252800121', '1522252800121', 'garnet_system_option', '1', '110', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('140', '1', '/garnet/option/resourceManage/types/111', '选项-资源管理新增页类型-type111', '新增页面的类型选项', '1522252800122', '1522252800122', 'garnet_system_option', '1', '111', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('141', '1', '/garnet/data/resourceManage/tenantList/1', '数据-资源管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800123', '1522252800123', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('142', '1', '/garnet/data/resourceManage/tenantList/2', '数据-资源管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800124', '1522252800124', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('143', '1', '/garnet/data/resourceManage/tenantList/3', '数据-资源管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800125', '1522252800125', 'garnet_data_permission', '1', '3', 'admin');
-- resourcetype
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('144', '1', '/garnet/data/resourceTypeManage/query/1', '数据-资源类型列表-level1', '加载列表的数据，行为决定有无，level决定级别', '1522252800126', '1522252800126', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('145', '1', '/garnet/data/resourceTypeManage/query/2', '数据-资源类型列表-level2', '加载列表的数据，行为决定有无，level决定级别', '1522252800127', '1522252800127', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('146', '1', '/garnet/data/resourceTypeManage/query/3', '数据-资源类型列表-level3', '加载列表的数据，行为决定有无，level决定级别', '1522252800128', '1522252800128', 'garnet_data_permission', '1', '3', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('147', '1', '/garnet/option/resourceTypeManage/types/001', '选项-资源类型管理新增页类型-type001', '新增页面的类型选项', '1522252800129', '1522252800129', 'garnet_system_option', '1', '001', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('148', '1', '/garnet/option/resourceTypeManage/types/010', '选项-资源类型管理新增页类型-type010', '新增页面的类型选项', '1522252800130', '1522252800130', 'garnet_system_option', '1', '010', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('149', '1', '/garnet/option/resourceTypeManage/types/011', '选项-资源类型管理新增页类型-type011', '新增页面的类型选项', '1522252800131', '1522252800131', 'garnet_system_option', '1', '011', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('150', '1', '/garnet/option/resourceTypeManage/types/100', '选项-资源类型管理新增页类型-type100', '新增页面的类型选项', '1522252800132', '1522252800132', 'garnet_system_option', '1', '100', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('151', '1', '/garnet/option/resourceTypeManage/types/101', '选项-资源类型管理新增页类型-type101', '新增页面的类型选项', '1522252800133', '1522252800133', 'garnet_system_option', '1', '101', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('152', '1', '/garnet/option/resourceTypeManage/types/110', '选项-资源类型管理新增页类型-type110', '新增页面的类型选项', '1522252800134', '1522252800134', 'garnet_system_option', '1', '110', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('153', '1', '/garnet/option/resourceTypeManage/types/111', '选项-资源类型管理新增页类型-type111', '新增页面的类型选项', '1522252800135', '1522252800135', 'garnet_system_option', '1', '111', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('154', '1', '/garnet/data/resourceTypeManage/tenantList/1', '数据-资源类型管理新增编辑页租户列表-level1', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800136', '1522252800136', 'garnet_data_permission', '1', '1', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('155', '1', '/garnet/data/resourceTypeManage/tenantList/2', '数据-资源类型管理新增编辑页租户列表-level2', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800137', '1522252800137', 'garnet_data_permission', '1', '2', 'admin');
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('156', '1', '/garnet/data/resourceTypeManage/tenantList/3', '数据-资源类型管理新增编辑页租户列表-level3', '选择租户或租户+应用类型时，租户的可加载列表', '1522252800138', '1522252800138', 'garnet_data_permission', '1', '3', 'admin');
-- sso
INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('157', '1', '/garnet/data/ssoManage/query/1', '数据-单点登录列表-level1', '单点登录应用组的数据，行为决定有无，level决定级别', '1522252800139', '1522252800139', 'garnet_data_permission', '1', '1', 'admin');
-- tenantManager
-- INSERT INTO gar_resources (id, application_id, path, name, remark, created_time, modified_time, type, tenant_id, varchar_00, updated_by_user_name) VALUES ('158', '0', '/garnet/tenantManage', '本用户为租户管理员的租户', '标记用户为租户管理员的权限', '1522252800140', '1522252800140', 'garnet_data_permission', '1', '1', 'admin');

-- gar_router_group
INSERT INTO gar_router_group (id, group_name, app_code, remark, created_time, modified_time, updated_by_user_name) VALUES ('1', '超级应用组', 'garnet', '不能删除garnet应用', '1522252800000', '1522252800000', 'admin');

