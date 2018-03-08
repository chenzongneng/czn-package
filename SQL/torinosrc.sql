
--------------------- 系统菜单 ----------------------
DROP TABLE IF EXISTS "public"."gar_sys_menus";
CREATE TABLE "public"."gar_sys_menus" (
  menu_id     INT8 PRIMARY KEY,
  parent_id    INT8,
  name    VARCHAR(50),
  url       VARCHAR(200),
  type      INT4,
  icon      VARCHAR(50),
  code      VARCHAR(50),
  order_num INT4
);

ALTER TABLE "public"."gar_sys_menus" ADD UNIQUE ("code");

COMMENT ON TABLE "public"."gar_sys_menus" IS '系统菜单';
COMMENT ON COLUMN "public"."gar_sys_menus"."menu_id" IS '菜单ID';
COMMENT ON COLUMN "public"."gar_sys_menus"."parent_id" IS '父菜单ID';
COMMENT ON COLUMN "public"."gar_sys_menus"."name" IS '菜单名称';
COMMENT ON COLUMN "public"."gar_sys_menus"."url" IS '邮箱';
COMMENT ON COLUMN "public"."gar_sys_menus"."type" IS '类型';
COMMENT ON COLUMN "public"."gar_sys_menus"."icon" IS '图标';
COMMENT ON COLUMN "public"."gar_sys_menus"."code" IS '标志';
COMMENT ON COLUMN "public"."gar_sys_menus"."order_num" IS '排序';

-- 初始化系统菜单
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (1, 0, '系统管理', null, 0, 'fa fa-cog', 'garnetSysManagement', 0);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (2, 1, '租户管理', 'modules/tenant.html', 1, 'fa fa-address-book', 'garnetSysManagementTenant', 1);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (3, 1, '应用管理', 'modules/application.html', 1, 'fa fa-th-large', 'garnetSysManagementApplication', 2);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (4, 1, '用户管理', 'modules/user.html', 1, 'fa fa fa-user', 'garnetSysManagementUser', 3);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (5, 1, '部门管理', 'modules/group.html', 1, 'fa fa-institution', 'garnetSysManagementDepartment', 4);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (6, 1, '角色管理', 'modules/role.html', 1, 'fa  fa-group ', 'garnetSysManagementRole', 5);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (8, 1, '系统日志', 'modules/log.html', 1, 'fa fa-file-text-o', 'garnetSysManagementLog', 7);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (9, 0, '开发选项', '', 0, 'fa fa-cog', 'garnetDevelopment', 0);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (7, 1, '权限管理', 'modules/permission.html', 1, 'fa fa-th-list', 'garnetSysManagementPermission', 6);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (11, 9, 'API', 'modules/api.html', 1, 'fa fa-th-list', 'garnetDevelopmentApi', 2);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (10, 9, '资源管理', 'modules/resource.html', 1, 'fa fa-th-list', 'garnetDevelopmentResource', 1);


--------------------- token表 ----------------------
DROP TABLE IF EXISTS "public"."gar_tokens";
CREATE TABLE "public"."gar_tokens" (
  user_id     INT8,
  token       VARCHAR(200) COLLATE "default" NOT NULL,
  expire_time TIMESTAMP,
  update_time TIMESTAMP
);

COMMENT ON TABLE "public"."gar_tokens" IS '用户token';
COMMENT ON COLUMN "public"."gar_tokens"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."gar_tokens"."token" IS 'token';
COMMENT ON COLUMN "public"."gar_tokens"."expire_time" IS '过期时间';
COMMENT ON COLUMN "public"."gar_tokens"."update_time" IS '更新时间';


--------------------- 租户表 ----------------------
DROP TABLE IF EXISTS "public"."gar_tenants";
CREATE TABLE "public"."gar_tenants"(
  tenant_id   INT8   PRIMARY KEY,
  name        VARCHAR(100) COLLATE "default",
  remark        VARCHAR(100) COLLATE "default",
  create_time     TIMESTAMP(6)
);
COMMENT ON TABLE "public"."gar_tenants" IS '租户';
COMMENT ON COLUMN "public"."gar_tenants"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_tenants"."name" IS '租户名称';
COMMENT ON COLUMN "public"."gar_tenants"."remark" IS '备注';
COMMENT ON COLUMN "public"."gar_tenants"."create_time" IS '创建时间';

INSERT INTO public.gar_tenants (tenant_id, name, remark, create_time) VALUES (1, 'Garnet', 'Garnet安全模块管理', '2017-10-20 16:13:05.152868');


--------------------- 应用表 ----------------------
DROP TABLE IF EXISTS "public"."gar_applications";
CREATE TABLE "public"."gar_applications"(
  application_id      BIGSERIAL PRIMARY KEY,
  name        VARCHAR(50) COLLATE "default" NOT NULL,
  code        VARCHAR(50) COLLATE "default" NOT NULL,
  company     VARCHAR(100) COLLATE "default",
  remark      VARCHAR(100) COLLATE "default"
);

ALTER TABLE "public"."gar_applications" ADD UNIQUE ("code");

COMMENT ON TABLE "public"."gar_applications" IS '应用';
COMMENT ON COLUMN "public"."gar_applications"."application_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_applications"."name" IS '名称';
COMMENT ON COLUMN "public"."gar_applications"."code" IS '标识';
COMMENT ON COLUMN "public"."gar_applications"."company" IS '公司';
COMMENT ON COLUMN "public"."gar_applications"."remark" IS '备注';

-- 初始化应用
INSERT INTO public.gar_applications (name, code, company, remark) VALUES ('Garnet', 'garnet', '丰石科技', 'Garnet安全模块');


--------------------- 用户表 ----------------------
DROP TABLE IF EXISTS "public"."gar_users";
CREATE TABLE "public"."gar_users" (
  user_id     BIGSERIAL PRIMARY KEY,
  username    VARCHAR(50) COLLATE "default" NOT NULL,
  password    VARCHAR(200) COLLATE "default",
  name        VARCHAR(20) COLLATE "default",
  email       VARCHAR(100) COLLATE "default",
  mobile      VARCHAR(100) COLLATE "default",
  status      INT4,
  create_time TIMESTAMP(6)
);

ALTER TABLE "public"."gar_users" ADD UNIQUE ("username");

COMMENT ON TABLE "public"."gar_users" IS '用户';
COMMENT ON COLUMN "public"."gar_users"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."gar_users"."username" IS '用户账号';
COMMENT ON COLUMN "public"."gar_users"."password" IS '用户密码';
COMMENT ON COLUMN "public"."gar_users"."name" IS '用户姓名';
COMMENT ON COLUMN "public"."gar_users"."email" IS '邮箱';
COMMENT ON COLUMN "public"."gar_users"."mobile" IS '电话';
COMMENT ON COLUMN "public"."gar_users"."status" IS '状态';
COMMENT ON COLUMN "public"."gar_users"."create_time" IS '创建时间';

-- 初始化用户，账号：admin  密码：admin
INSERT INTO public.gar_users (user_id, username, password, name, email, mobile, status, create_time)
VALUES (1, 'admin', '$2a$12$rFhdbcz5igsiwi45dt5S.uvg36BIT4Hk1AgV5QWl5NWQ0k0b2SrdO', '管理员', 'admin@you.xiang', '18812345678', 1, '2017-10-19 17:39:58.848979');

--------------------- 部门表 ----------------------
DROP TABLE IF EXISTS "public"."gar_departments";
CREATE TABLE "public"."gar_departments" (
  department_id   INT8 PRIMARY KEY,
  parent_department_id INT8,
  tenant_id INT8,
  application_id    INT8,
  name  VARCHAR(50) COLLATE "default",
  order_num  INT4
);
COMMENT ON TABLE "public"."gar_departments" IS '部门';
COMMENT ON COLUMN "public"."gar_departments"."department_id" IS '部门ID';
COMMENT ON COLUMN "public"."gar_departments"."parent_department_id" IS '父部门ID';
COMMENT ON COLUMN "public"."gar_departments"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_departments"."application_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_departments"."name" IS '部门名称';
COMMENT ON COLUMN "public"."gar_departments"."order_num" IS '排序';

INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (1, 0, 1, 1, '丰石科技', 0);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (2, 1, 1, 1, '研发部', 0);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (3, 1, 1, 1, '产品部', 1);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (4, 1, 1, 1, '人事部', 2);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (5, 1, 1, 1, '财务部', 3);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (6, 1, 1, 1, '商务部', 4);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (7, 1, 1, 1, '运营管理部', 5);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (8, 1, 1, 1, 'PMO', 6);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (9, 3, 1, 1, '实时客流组', 0);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (10, 3, 1, 1, '网络质量组', 1);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (11, 3, 1, 1, 'BDP产品组', 2);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (12, 3, 1, 1, '零售应用组', 3);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (13, 3, 1, 1, '品牌传播组', 4);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (14, 2, 1, 1, '架构组', 0);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (15, 2, 1, 1, '测试组', 1);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (16, 2, 1, 1, 'IT运维组', 4);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (17, 2, 1, 1, 'Gempile研发组', 3);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (18, 2, 1, 1, 'Gempile部署组', 4);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (19, 2, 1, 1, 'Hadoop研发组', 5);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (20, 2, 1, 1, '摇折扣研发组', 6);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (21, 2, 1, 1, '客流研发组', 7);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (22, 2, 1, 1, '物联网研发组', 8);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (23, 2, 1, 1, 'SmartPush研发组', 9);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (24, 0, 1, 1, '丰石科技 - 摇折扣', 2);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (25, 24, 1, 1, '研发部', 1);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (26, 24, 1, 1, '测试部', 2);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (27, 24, 1, 1, '产品部', 0);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (28, 0, 1, 1, '丰石科技 - Gempile系统', 1);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (29, 28, 1, 1, '研发部', 0);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (30, 28, 1, 1, '产品部', 1);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (31, 28, 1, 1, '产品部', 1);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (32, 28, 1, 1, '测试部', 2);
INSERT INTO public.gar_departments (department_id, parent_department_id, tenant_id, application_id, name, order_num) VALUES (33, 1, 1, 1, 'Garnet', 1);


--------------------- 角色表 ----------------------
DROP TABLE IF EXISTS "public"."gar_roles";
CREATE TABLE "public"."gar_roles" (
  role_id     INT8 PRIMARY KEY,
  tenant_id   INT8,
  application_id      INT8,
  name        VARCHAR(100),
  remark      VARCHAR(100) COLLATE "default",
  create_time TIMESTAMP
);

COMMENT ON TABLE "public"."gar_roles" IS '角色';
COMMENT ON COLUMN "public"."gar_roles"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_roles"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_roles"."application_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_roles"."name" IS '角色名字';
COMMENT ON COLUMN "public"."gar_roles"."remark" IS '备注';
COMMENT ON COLUMN "public"."gar_roles"."create_time" IS '创建时间';

INSERT INTO public.gar_roles (role_id, tenant_id, application_id, name, remark, create_time) VALUES (1, 1, 1, '超级管理员', '拥有最高权限', '2018-01-18 13:14:20.656193');



--------------------- 权限表 ----------------------
DROP TABLE IF EXISTS "public"."gar_permissions";
CREATE TABLE "public"."gar_permissions" (
  permission_id BIGSERIAL PRIMARY KEY,
  application_id INT8,
  name         VARCHAR(100) COLLATE "default" NOT NULL,
  wildcard  VARCHAR(512) COLLATE "default",
  description  VARCHAR(512) COLLATE "default",
  status       INT4
);
COMMENT ON TABLE "public"."gar_permissions" IS '权限';
COMMENT ON COLUMN "public"."gar_permissions"."permission_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_permissions"."name" IS '具体名称';
COMMENT ON COLUMN "public"."gar_permissions"."wildcard" IS '通配符';
COMMENT ON COLUMN "public"."gar_permissions"."description" IS '详细说明';
COMMENT ON COLUMN "public"."gar_permissions"."status" IS '状态';


--------------------- 系统LOG信息表 ----------------------
DROP TABLE IF EXISTS "public"."gar_logs";
CREATE TABLE gar_logs
(
  id          INT8 PRIMARY KEY,
  username    VARCHAR(50) COLLATE "default",
  operation   VARCHAR(50) COLLATE "default",
  method      VARCHAR(50) COLLATE "default",
  url         VARCHAR(255) COLLATE "default",
  ip          VARCHAR(255) COLLATE "default",
  sql         TEXT,
  create_time TIMESTAMP
);
COMMENT ON TABLE "public"."gar_logs" IS '系统LOG信息';
COMMENT ON COLUMN "public"."gar_logs"."id" IS 'LogID';
COMMENT ON COLUMN "public"."gar_logs"."username" IS '用户名';
COMMENT ON COLUMN "public"."gar_logs"."operation" IS '用户操作';
COMMENT ON COLUMN "public"."gar_logs"."method" IS '请求方法';
COMMENT ON COLUMN "public"."gar_logs"."url" IS '请求URL';
COMMENT ON COLUMN "public"."gar_logs"."ip" IS '用户ip';
COMMENT ON COLUMN "public"."gar_logs"."sql" IS '执行sql';
COMMENT ON COLUMN "public"."gar_logs"."create_time" IS '创建时间';


--------------------- LOG操作信息表 ----------------------
DROP TABLE IF EXISTS "public"."gar_log_operations";
CREATE TABLE gar_log_operations
(
  id        INT8 PRIMARY KEY,
  url       VARCHAR(200),
  method    VARCHAR(50),
  operation VARCHAR(100)
);
COMMENT ON TABLE "public"."gar_log_operations" IS 'LOG操作信息';
COMMENT ON COLUMN "public"."gar_log_operations"."id" IS 'LogID';
COMMENT ON COLUMN "public"."gar_log_operations"."operation" IS '用户操作';
COMMENT ON COLUMN "public"."gar_log_operations"."method" IS '请求方法';
COMMENT ON COLUMN "public"."gar_log_operations"."url" IS '请求URL';


--------------------- 资源表 ----------------------
DROP TABLE IF EXISTS "public"."gar_resources";
CREATE TABLE "public"."gar_resources" (
  resource_id        BIGSERIAL PRIMARY KEY,
  application_id INT8,
  name           VARCHAR(255) COLLATE "default",
  description    VARCHAR(255) COLLATE "default",
  code           VARCHAR(255) COLLATE "default",
  parent_code    VARCHAR(255) COLLATE "default",
  path           VARCHAR(255) COLLATE "default",
  status         INT4
);

ALTER TABLE "public"."gar_resources"
  ADD UNIQUE ("code");
CREATE INDEX "gar_resource_parent_code_idx" on gar_resources(parent_code);

COMMENT ON TABLE "public"."gar_resources" IS '资源';
COMMENT ON COLUMN "public"."gar_resources"."resource_id" IS '资源ID';
COMMENT ON COLUMN "public"."gar_resources"."application_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_resources"."name" IS '资源名称';
COMMENT ON COLUMN "public"."gar_resources"."description" IS '说明';
COMMENT ON COLUMN "public"."gar_resources"."code" IS '资源标识';
COMMENT ON COLUMN "public"."gar_resources"."parent_code" IS '父资源标识';
COMMENT ON COLUMN "public"."gar_resources"."path" IS '路径标识';
COMMENT ON COLUMN "public"."gar_resources"."status" IS '状态';

INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '全部API', '全部API，供开发使用', 'garnetDevelopmentAllApi', 'garnetDevelopment', '/garnet/development/all', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, 'Garnet', 'Garnet项目', 'garnet', NULL, '/garnet', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '系统管理', 'Garnet系统管理', 'garnetSysManagement', 'garnet', '/garnet/sysManagement', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '开发选项', 'Garnet开发选项', 'garnetDevelopment', 'garnet', '/garnet/development', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '租户管理', 'Garnet租户管理', 'garnetSysManagementTenant', 'garnetSysManagement', '/garnet/sysManagement/tenant', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '应用管理', 'Garnet应用管理', 'garnetSysManagementApplication', 'garnetSysManagement', '/garnet/sysManagement/application', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '用户管理', 'Garnet用户管理', 'garnetSysManagementUser', 'garnetSysManagement', '/garnet/sysManagement/user', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '部门管理', 'Garnet部门管理', 'garnetSysManagementDepartment', 'garnetSysManagement', '/garnet/sysManagement/department', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '角色管理', 'Garnet角色管理', 'garnetSysManagementRole', 'garnetSysManagement', '/garnet/sysManagement/role', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '权限管理', 'Garnet权限管理', 'garnetSysManagementPermission', 'garnetSysManagement', '/garnet/sysManagement/permission', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '系统日志', 'Garnet系统日志', 'garnetSysManagementLog', 'garnetSysManagement', '/garnet/sysManagement/log', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '资源管理', 'Garnet资源管理', 'garnetDevelopmentResource', 'garnetDevelopment', '/garnet/development/menu', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, 'API管理', 'GarnetAPI管理', 'garnetDevelopmentApi', 'garnetDevelopment', '/garnet/development/api', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '租户管理新增', 'Garnet租户管理新增按钮', 'garnetSysManagementTenantAdd', 'garnetSysManagementTenant', '/garnet/sysManagement/tenant/add', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '租户管理修改', 'Garnet租户管理修改按钮', 'garnetSysManagementTenantUpdate', 'garnetSysManagementTenant', '/garnet/sysManagement/tenant/update', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '租户管理删除', 'Garnet租户管理删除按钮', 'garnetSysManagementTenantDelete', 'garnetSysManagementTenant', '/garnet/sysManagement/tenant/delete', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '应用管理新增', 'Garnet应用管理新增按钮', 'garnetSysManagementApplicationAdd', 'garnetSysManagementApplication', '/garnet/sysManagement/application/add', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '应用管理修改', 'Garnet应用管理修改按钮', 'garnetSysManagementApplicationUpdate', 'garnetSysManagementApplication', '/garnet/sysManagement/application/update', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '应用管理删除', 'Garnet应用管理删除按钮', 'garnetSysManagementApplicationDelete', 'garnetSysManagementApplication', '/garnet/sysManagement/application/delete', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '用户管理新增', 'Garnet用户管理新增按钮', 'garnetSysManagementUsernAdd', 'garnetSysManagementUser', '/garnet/sysManagement/user/add', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '用户管理修改', 'Garnet用户管理修改按钮', 'garnetSysManagementUserUpdate', 'garnetSysManagementUser', '/garnet/sysManagement/user/update', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '用户管理删除', 'Garnet用户管理删除按钮', 'garnetSysManagementUserDelete', 'garnetSysManagementUser', '/garnet/sysManagement/user/delete', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '部门管理新增', 'Garnet部门管理新增按钮', 'garnetSysManagementDepartmentAdd', 'garnetSysManagementDepartment', '/garnet/sysManagement/department/add', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '部门管理修改', 'Garnet部门管理修改按钮', 'garnetSysManagementDepartmentUpdate', 'garnetSysManagementDepartment', '/garnet/sysManagement/department/update', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '部门管理删除', 'Garnet部门管理删除按钮', 'garnetSysManagementDepartmentDelete', 'garnetSysManagementDepartment', '/garnet/sysManagement/department/delete', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '角色管理新增', 'Garnet角色管理新增按钮', 'garnetSysManagementRoleAdd', 'garnetSysManagementRole', '/garnet/sysManagement/role/add', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '角色管理修改', 'Garnet角色管理修改按钮', 'garnetSysManagementRoleUpdate', 'garnetSysManagementRole', '/garnet/sysManagement/role/update', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '角色管理删除', 'Garnet角色管理删除按钮', 'garnetSysManagementRoleDelete', 'garnetSysManagementRole', '/garnet/sysManagement/role/delete', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '权限管理新增', 'Garnet权限管理新增按钮', 'garnetSysManagementPermissionAdd', 'garnetSysManagementPermission', '/garnet/sysManagement/permission/add', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '权限管理修改', 'Garnet权限管理修改按钮', 'garnetSysManagementPermissionUpdate', 'garnetSysManagementPermission', '/garnet/sysManagement/permission/update', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '权限管理删除', 'Garnet权限管理删除按钮', 'garnetSysManagementPermissionDelete', 'garnetSysManagementPermission', '/garnet/sysManagement/permission/delete', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '资源管理新增', 'Garnet资源管理新增按钮', 'garnetDevelopmentResourceAdd', 'garnetDevelopmentResource', '/garnet/development/menu/add', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '资源管理修改', 'Garnet资源管理修改按钮', 'garnetDevelopmentResourceUpdate', 'garnetDevelopmentResource', '/garnet/development/menu/update', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '资源管理删除', 'Garnet资源管理删除按钮', 'garnetDevelopmentResourceDelete', 'garnetDevelopmentResource', '/garnet/development/menu/delete', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, 'API管理新增', 'GarnetAPI管理新增按钮', 'garnetDevelopmentApiAdd', 'garnetDevelopmentApi', '/garnet/development/api/add', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, 'API管理修改', 'GarnetAPI管理修改按钮', 'garnetDevelopmentApiUpdate', 'garnetDevelopmentApi', '/garnet/development/api/update', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, 'API管理删除', 'GarnetAPI管理删除按钮', 'garnetDevelopmentApiDelete', 'garnetDevelopmentApi', '/garnet/development/api/delete', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '租户管理搜索框', '租户管理上方的搜索框', 'garnetSysManagementTenantSearchBox', 'garnetSysManagementTenant', '/garnet/sysManagement/tenant/searchBox', 1);


--------------------- API表 ----------------------
DROP TABLE IF EXISTS "public"."gar_apis";
CREATE TABLE "public"."gar_apis" (
  api_id  BIGSERIAL PRIMARY KEY,
  application_id INT8,
  parent_id      INT8,
  name           VARCHAR(100) COLLATE "default",
  permission     VARCHAR(100) COLLATE "default",
  description    VARCHAR(512) COLLATE "default",
  url            VARCHAR(100) COLLATE "default",
  method         VARCHAR(32) COLLATE "default",
  status         INT4 NOT NULL
);
COMMENT ON TABLE "public"."gar_apis" IS 'API';
COMMENT ON COLUMN "public"."gar_apis"."application_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_apis"."parent_id" IS '父访问权限ID';
COMMENT ON COLUMN "public"."gar_apis"."name" IS 'API名称';
COMMENT ON COLUMN "public"."gar_apis"."permission" IS 'Shiro的权限标识符';
COMMENT ON COLUMN "public"."gar_apis"."description" IS '说明';
COMMENT ON COLUMN "public"."gar_apis"."url" IS '对应的链接';
COMMENT ON COLUMN "public"."gar_apis"."method" IS '方法';
COMMENT ON COLUMN "public"."gar_apis"."status" IS '状态';


INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (1, 1, 0, '全部访问权限', '*', '全部API的访问权限', NULL, 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (2, 1, 0, '[Garnet]API管理接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (3, 1, 2, '[Garnet]通过应用ID查询API列表', 'api:list:application', 'Get api list by application id', '/v1.0/apis/applicationId/{applicationId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (4, 1, 2, '[Garnet]查询API列表', 'api:list', 'Get api list ', '/v1.0/apis', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (5, 1, 2, '[Garnet]根据id查询API信息', 'api:info', 'Get api info by api Id ', '/v1.0/api/{apiId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (6, 1, 2, '[Garnet]新增API', 'api:create', 'Create api', '/v1.0/api', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (7, 1, 2, '[Garnet]根据ID更新API信息', 'api:update', 'Update api info', '/v1.0/api', 'PUT', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (8, 1, 2, '[Garnet]根据id批量删除API', 'api:delete:batch', 'Delete api', '/v1.0/api', 'DELETE', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (9, 1, 2, '[Garnet]获取可导入的API列表数据，仅供开发使用', 'api:export', 'get apis which can import to database', '/v1.0/exportApis', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (10, 1, 2, '[Garnet]导入API列表', 'api:import', 'import apis ', '/v1.0/importApis/{appCode}', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (11, 1, 0, '[Garnet]应用管理接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (12, 1, 11, '[Garnet]根据id批量删除应用', 'application:delete:batch', 'Delete applications', '/v1.0/application', 'DELETE', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (13, 1, 11, '[Garnet]根据id查询应用', 'application:info', 'Get application by id ', '/v1.0/application/{applicationId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (14, 1, 11, '[Garnet]查询应用列表', 'application:list', 'Get application list ', '/v1.0/applications', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (15, 1, 11, '[Garnet]根据ID更新应用', 'application:update', 'Update application', '/v1.0/application', 'PUT', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (16, 1, 11, '[Garnet]新增应用', 'application:add', 'Create application', '/v1.0/application', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (17, 1, 0, '[Garnet]部门管理接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (19, 1, 17, '[Garnet]查询部门列表用于增加部门', 'department:list:toAdd:byUser', 'Get department list to add by user id', '/v1.0/departments/add', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (18, 1, 17, '[Garnet]查询部门列表', 'department:info', 'Get department list', '/v1.0/departments', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (20, 1, 17, '[Garnet]新增部门', 'department:create', 'Create department', '/v1.0/department', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (21, 1, 17, '[Garnet]更新部门信息', 'department:update', 'Update department', '/v1.0/department', 'PUT', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (22, 1, 17, '[Garnet]根据id删除部门', 'department:delete:batch', 'Delete department', '/v1.0/department', 'DELETE', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (23, 1, 17, '[Garnet]查询该用户的部门列表', 'department:list:byUser', 'Get department list by user id ', '/v1.0/departments/{userId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (24, 1, 0, '[Garnet]系统日志查询接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (25, 1, 24, '[Garnet]根据id查询详细log信息', 'log:info', 'Get detail log info ', '/v1.0/log/{id}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (26, 1, 24, '[Garnet]查询log列表', 'log:list', 'Get log list ', '/v1.0/logs', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (27, 1, 0, '[Garnet]登录相关接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (28, 1, 0, '[Garnet]Log操作管理接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (29, 1, 28, '[Garnet]根据ID更新log Operation 信息', 'log:operation:update', 'Update log Operation info', '/v1.0/logOperation', 'PUT', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (31, 1, 28, '[Garnet]新增log操作', 'log:operation:create', 'Create operation', '/v1.0/logOperation', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (32, 1, 28, '[Garnet]根据id删除log操作', 'log:operation:delete', 'Delete log Operation', '/v1.0/logOperation/{id}', 'DELETE', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (33, 1, 28, '[Garnet]根据id批量删除 log Operation', 'log:operation:delete:batch', 'Delete log Operations', '/v1.0/logOperations', 'DELETE', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (30, 1, 28, '[Garnet]根据id查询log Operation信息', 'log:operation:info', 'Get log Operation info by id ', '/v1.0/logOperation/{id}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (34, 1, 0, '[Garnet]权限管理接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (35, 1, 34, '[Garnet]根据id查询权限信息', 'permission:info', 'Get permission info by permissionId ', '/v1.0/permission/{permissionId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (36, 1, 34, '[Garnet]新增权限', 'permission:create', 'Create permission', '/v1.0/permission', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (37, 1, 34, '[Garnet]根据ID更新权限信息', 'permission:update', 'Update permission info', '/v1.0/permission', 'PUT', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (38, 1, 34, '[Garnet]查询权限列表', 'permission:list', 'Get permission list ', '/v1.0/permissions', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (39, 1, 34, '[Garnet]根据id批量删除权限', 'permission:delete:batch', 'Delete permissions', '/v1.0/permission', 'DELETE', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (40, 1, 34, '[Garnet]查询通过应用ID权限列表', 'permission:list:application', 'Get permission list by application id', '/v1.0/permissions/applicationId/{applicationId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (41, 1, 0, '[Garnet]公用的api', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (42, 1, 0, '[Garnet]资源管理接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (43, 1, 42, '[Garnet]根据id批量删除资源', 'resource:delete:batch', 'Delete resources', '/v1.0/resource', 'DELETE', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (44, 1, 42, '[Garnet]新增资源', 'resource:create', 'Create resource', '/v1.0/resource', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (45, 1, 42, '[Garnet]查询资源列表', 'resource:list', 'Get resource list ', '/v1.0/resources', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (46, 1, 42, '[Garnet]根据id查询资源信息', 'resource:info', 'Get resource info by resourceId ', '/v1.0/resource/{resourceId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (47, 1, 42, '[Garnet]根据更新资源信息', 'resource:update', 'Update resource info', '/v1.0/resource', 'PUT', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (48, 1, 42, '[Garnet]通过应用查询应用资源列表', 'resource:list:application', 'Get application resource list by app id', '/v1.0/resources/applicationId/{applicationId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (49, 1, 0, '[Garnet]角色管理接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (50, 1, 49, '[Garnet]新增角色', 'role:create', 'Create role', '/v1.0/role', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (51, 1, 49, '[Garnet]根据id查询角色信息', 'role:info', 'Get role info by roleId ', '/v1.0/role/{roleId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (52, 1, 49, '[Garnet]查询角色列表', 'role:list', 'Get role list ', '/v1.0/roles', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (53, 1, 49, '[Garnet]根据id批量删除角色', 'role:delete:batch', 'Delete roles', '/v1.0/role', 'DELETE', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (54, 1, 49, '[Garnet]根据ID更新角色信息', 'role:update', 'Update role info', '/v1.0/role', 'PUT', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (55, 1, 0, '[Garnet]系统菜单查询接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (56, 1, 0, '[Garnet]租户管理接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (57, 1, 56, '[Garnet]根据id批量删除租户', 'tenant:delete:batch', 'Delete tenants', '/v1.0/tenant', 'DELETE', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (58, 1, 56, '[Garnet]新增租户', 'tenant:create', 'Create tenant', '/v1.0/tenant', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (59, 1, 56, '[Garnet]根据id查询租户信息', 'tenant:info', 'Get tenant by id ', '/v1.0/tenant/{tenantId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (60, 1, 56, '[Garnet]查询租户列表', 'tenant:list', 'Get tenant list ', '/v1.0/tenants', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (61, 1, 56, '[Garnet]根据ID更新租户', 'tenant:update', 'Update tenant', '/v1.0/tenant', 'PUT', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (62, 1, 0, '[Garnet]Token相关接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (63, 1, 0, '[Garnet]用户管理接口', NULL, NULL, NULL, NULL, 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (64, 1, 63, '[Garnet]根据ID更新用户信息', 'user:update', 'Update user info', '/v1.0/user', 'PUT', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (65, 1, 63, '[Garnet]查询用户列表', 'user:list', 'Get user list ', '/v1.0/users', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (66, 1, 63, '[Garnet]根据id查询用户信息', 'user:info', 'Get user info by userId ', '/v1.0/user/{userId}', 'GET', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (67, 1, 63, '[Garnet]新增用户', 'user:create', 'Create user', '/v1.0/user', 'POST', 1);
INSERT INTO public.gar_apis (api_id, application_id, parent_id, name, permission, description, url, method, status) VALUES (68, 1, 63, '[Garnet]根据id批量删除用户', 'user:delete:batch', 'Delete users', '/v1.0/user', 'DELETE', 1);
SELECT setval('gar_apis_api_id_seq', (SELECT max(api_id) FROM gar_apis));

--------------------- 应用-租户关联表 ----------------------
DROP TABLE IF EXISTS "public"."gar_application_tenant";
CREATE TABLE "public"."gar_application_tenant" (
  application_id INT8,
  tenant_id  INT8
);
ALTER TABLE "public"."gar_application_tenant"
  ADD PRIMARY KEY ("application_id", "tenant_id");
COMMENT ON COLUMN "public"."gar_application_tenant"."application_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_application_tenant"."tenant_id" IS '租户ID';

INSERT INTO public.gar_application_tenant (application_id, tenant_id) VALUES (1, 1);


--------------------- 用户-应用关联表 ----------------------
DROP TABLE IF EXISTS "public"."gar_user_application";
CREATE TABLE "public"."gar_user_application" (
  user_id INT8,
  application_id  INT8
);
ALTER TABLE "public"."gar_user_application"
  ADD PRIMARY KEY ("user_id", "application_id");
COMMENT ON COLUMN "public"."gar_user_application"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."gar_user_application"."application_id" IS '应用ID';

INSERT INTO public.gar_user_application (user_id, application_id) VALUES (1, 1);

--------------------- 用户-部门关联表 ----------------------
DROP TABLE IF EXISTS "public"."gar_user_department";
CREATE TABLE "public"."gar_user_department" (
  user_id INT8,
  department_id  INT8
);
ALTER TABLE "public"."gar_user_department"
  ADD PRIMARY KEY ("user_id", "department_id");
COMMENT ON COLUMN "public"."gar_user_department"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."gar_user_department"."department_id" IS '部门ID';

INSERT INTO public.gar_user_department (user_id, department_id) VALUES (1, 33);


--------------------- 角色-部门关联表 ----------------------
DROP TABLE IF EXISTS "public"."gar_role_department";
CREATE TABLE "public"."gar_role_department" (
  role_id      INT8,
  department_id INT8
);
ALTER TABLE "public"."gar_role_department"
  ADD PRIMARY KEY ("role_id", "department_id");
COMMENT ON COLUMN "public"."gar_role_department"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_role_department"."department_id" IS '部门ID';

INSERT INTO public.gar_role_department (role_id, department_id) VALUES (1, 33);

--------------------- 用户-角色关联表 ----------------------
DROP TABLE IF EXISTS "public"."gar_user_role";
CREATE TABLE "public"."gar_user_role" (
  user_id  INT8,
  role_id  INT8
);
ALTER TABLE "public"."gar_user_role"
  ADD PRIMARY KEY ("user_id", "role_id");
COMMENT ON COLUMN "public"."gar_user_role"."user_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_user_role"."role_id" IS '部门ID';

INSERT INTO public.gar_user_role (user_id, role_id) VALUES (1, 1);


--------------------- 角色-权限关联表 ----------------------
DROP TABLE IF EXISTS "public"."gar_role_permission";
CREATE TABLE "public"."gar_role_permission" (
  role_id      INT8,
  permission_id INT8
);
ALTER TABLE "public"."gar_role_permission"
  ADD PRIMARY KEY ("role_id", "permission_id");
COMMENT ON COLUMN "public"."gar_role_permission"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_role_permission"."permission_id" IS '权限ID';

INSERT INTO public.gar_role_permission (role_id, permission_id) VALUES (1, 1);

INSERT INTO public.gar_permissions (application_id, name, wildcard, description, status) VALUES (1, '最高权限', '/%', 'Garnet最高权限', 1);

--------------------- 资源-API关联表 ----------------------
DROP TABLE IF EXISTS "public"."gar_resource_api";
CREATE TABLE "public"."gar_resource_api" (
  resource_id       INT8,
  api_id INT8
);
ALTER TABLE "public"."gar_resource_api"
  ADD PRIMARY KEY ("resource_id", "api_id");
COMMENT ON COLUMN "public"."gar_resource_api"."resource_id" IS '资源ID';
COMMENT ON COLUMN "public"."gar_resource_api"."api_id" IS 'API ID';

INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (1, 1);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (40, 70);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (5, 60);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (6, 15);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (7, 65);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (8, 18);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (9, 52);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (10, 38);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (12, 15);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (12, 45);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (13, 4);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (13, 14);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (14, 14);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (14, 58);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (15, 14);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (15, 59);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (15, 61);
INSERT INTO public.gar_resource_api (resource_id, api_id) VALUES (16, 57);


