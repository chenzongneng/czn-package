
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
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (5, 1, '部门管理', 'modules/dept.html', 1, 'fa fa-institution', 'garnetSysManagementDept', 4);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (6, 1, '角色管理', 'modules/role.html', 1, 'fa  fa-group ', 'garnetSysManagementRole', 5);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (8, 1, '系统日志', 'modules/log.html', 1, 'fa fa-file-text-o', 'garnetSysManagementLog', 7);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (9, 0, '开发选项', '', 0, 'fa fa-cog', 'garnetDevelopment', 0);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (7, 1, '权限管理', 'modules/permission.html', 1, 'fa fa-th-list', 'garnetSysManagementPermission', 6);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (11, 9, 'API', 'modules/api.html', 1, 'fa fa-th-list', 'garnetDevelopmentApi', 2);
INSERT INTO public.gar_sys_menus (menu_id, parent_id, name, url, type, icon, code, order_num) VALUES (10, 9, '资源管理', 'modules/resource.html', 1, 'fa fa-th-list', 'garnetDevelopmentResource', 1);


--------------------- 租户表 ----------------------
DROP TABLE IF EXISTS "public"."gar_tenants";
CREATE TABLE "public"."gar_tenants"(
  tenant_id   INT8   PRIMARY KEY,
  name        VARCHAR(100) COLLATE "default" NOT NULL,
  remark        VARCHAR(100) COLLATE "default" NOT NULL,
  create_time     TIMESTAMP(6)
);
COMMENT ON TABLE "public"."gar_tenants" IS '租户';
COMMENT ON COLUMN "public"."gar_tenants"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_tenants"."name" IS '租户名称';
COMMENT ON COLUMN "public"."gar_tenants"."remark" IS '备注';
COMMENT ON COLUMN "public"."gar_tenants"."create_time" IS '创建时间';

INSERT INTO public.gar_tenants (tenant_id, name, remark, create_time, ctid) VALUES (1, 'Garnet', 'Garnet安全模块管理', '2017-10-20 16:13:05.152868', '(0,19)');


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
DROP TABLE IF EXISTS "public"."gar_depts";
CREATE TABLE "public"."gar_depts" (
  dept_id   INT8 PRIMARY KEY,
  parent_dept_id INT8,
  tenant_id INT8,
  app_id    INT8,
  name  VARCHAR(50) COLLATE "default",
  order_num  INT4
);
COMMENT ON TABLE "public"."gar_depts" IS '部门';
COMMENT ON COLUMN "public"."gar_depts"."dept_id" IS '部门ID';
COMMENT ON COLUMN "public"."gar_depts"."parent_dept_id" IS '父部门ID';
COMMENT ON COLUMN "public"."gar_depts"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_depts"."app_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_depts"."name" IS '部门名称';
COMMENT ON COLUMN "public"."gar_depts"."order_num" IS '排序';

INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (1, 0, 1, 1, '丰石科技', 0);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (2, 1, 1, 1, '研发部', 0);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (3, 1, 1, 1, '产品部', 1);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (4, 1, 1, 1, '人事部', 2);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (5, 1, 1, 1, '财务部', 3);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (6, 1, 1, 1, '商务部', 4);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (7, 1, 1, 1, '运营管理部', 5);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (8, 1, 1, 1, 'PMO', 6);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (9, 3, 1, 1, '实时客流组', 0);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (10, 3, 1, 1, '网络质量组', 1);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (11, 3, 1, 1, 'BDP产品组', 2);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (12, 3, 1, 1, '零售应用组', 3);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (13, 3, 1, 1, '品牌传播组', 4);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (14, 2, 1, 1, '架构组', 0);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (15, 2, 1, 1, '测试组', 1);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (16, 2, 1, 1, 'IT运维组', 4);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (17, 2, 1, 1, 'Gempile研发组', 3);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (18, 2, 1, 1, 'Gempile部署组', 4);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (19, 2, 1, 1, 'Hadoop研发组', 5);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (20, 2, 1, 1, '摇折扣研发组', 6);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (21, 2, 1, 1, '客流研发组', 7);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (22, 2, 1, 1, '物联网研发组', 8);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (23, 2, 1, 1, 'SmartPush研发组', 9);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (24, 0, 1, 1, '丰石科技 - 摇折扣', 2);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (25, 24, 1, 1, '研发部', 1);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (26, 24, 1, 1, '测试部', 2);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (27, 24, 1, 1, '产品部', 0);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (28, 0, 1, 1, '丰石科技 - Gempile系统', 1);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (29, 28, 1, 1, '研发部', 0);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (30, 28, 1, 1, '产品部', 1);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (31, 28, 1, 1, '产品部', 1);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (32, 28, 1, 1, '测试部', 2);
INSERT INTO public.gar_depts (dept_id, parent_dept_id, tenant_id, app_id, name, order_num) VALUES (33, 1, 1, 1, 'Garnet', 1);


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
COMMENT ON COLUMN "public"."gar_resources"."application_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_resources"."name" IS '资源名称';
COMMENT ON COLUMN "public"."gar_resources"."description" IS '说明';
COMMENT ON COLUMN "public"."gar_resources"."code" IS '资源标识';
COMMENT ON COLUMN "public"."gar_resources"."parent_code" IS '父资源标识';
COMMENT ON COLUMN "public"."gar_resources"."path" IS '路径标识';
COMMENT ON COLUMN "public"."gar_resources"."status" IS '状态';

INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, 'Garnet', 'Garnet项目', 'garnet', NULL, '/garnet', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '系统管理', 'Garnet系统管理', 'garnetSysManagement', 'garnet', '/garnet/sysManagement', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '开发选项', 'Garnet开发选项', 'garnetDevelopment', 'garnet', '/garnet/development', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '租户管理', 'Garnet租户管理', 'garnetSysManagementTenant', 'garnetSysManagement', '/garnet/sysManagement/tenant', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '应用管理', 'Garnet应用管理', 'garnetSysManagementApplication', 'garnetSysManagement', '/garnet/sysManagement/application', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '用户管理', 'Garnet用户管理', 'garnetSysManagementUser', 'garnetSysManagement', '/garnet/sysManagement/user', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '部门管理', 'Garnet部门管理', 'garnetSysManagementDept', 'garnetSysManagement', '/garnet/sysManagement/dept', 1);
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
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '部门管理新增', 'Garnet部门管理新增按钮', 'garnetSysManagementDeptAdd', 'garnetSysManagementDept', '/garnet/sysManagement/dept/add', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '部门管理修改', 'Garnet部门管理修改按钮', 'garnetSysManagementDeptUpdate', 'garnetSysManagementDept', '/garnet/sysManagement/dept/update', 1);
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '部门管理删除', 'Garnet部门管理删除按钮', 'garnetSysManagementDeptDelete', 'garnetSysManagementDept', '/garnet/sysManagement/dept/delete', 1);
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
INSERT INTO public.gar_resources (application_id, name, description, code, parent_code, path, status) VALUES (1, '全部API', '全部API，供开发使用', 'garnetDevelopmentAllApi', 'garnetDevelopment', '/garnet/development/all', 1);


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

--------------------- 关联表 ----------------------

DROP TABLE IF EXISTS "public"."gar_application_tenant";
CREATE TABLE "public"."gar_application_tenant" (
  app_id INT8,
  tenant_id  INT8
);
ALTER TABLE "public"."gar_application_tenant"
  ADD PRIMARY KEY ("app_id", "tenant_id");
COMMENT ON COLUMN "public"."gar_application_tenant"."app_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_application_tenant"."tenant_id" IS '租户ID';

INSERT INTO public.gar_application_tenant (app_id, tenant_id) VALUES (1, 1);

DROP TABLE IF EXISTS "public"."gar_user_application";
CREATE TABLE "public"."gar_user_application" (
  user_id INT8,
  app_id  INT8
);
ALTER TABLE "public"."gar_user_application"
  ADD PRIMARY KEY ("user_id", "app_id");
COMMENT ON COLUMN "public"."gar_user_application"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."gar_user_application"."app_id" IS '应用ID';

DROP TABLE IF EXISTS "public"."gar_user_dept";
CREATE TABLE "public"."gar_user_dept" (
  user_id INT8,
  dept_id  INT8
);
ALTER TABLE "public"."gar_user_dept"
  ADD PRIMARY KEY ("user_id", "dept_id");
COMMENT ON COLUMN "public"."gar_user_dept"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."gar_user_dept"."dept_id" IS '应用ID';

INSERT INTO public.gar_user_dept (user_id, dept_id) VALUES (1, 33);

DROP TABLE IF EXISTS "public"."gar_role_permission";
CREATE TABLE "public"."gar_role_permission" (
  role_id      INT8,
  permission_id INT8
);
ALTER TABLE "public"."gar_role_permission"
  ADD PRIMARY KEY ("role_id", "permission_id");
COMMENT ON COLUMN "public"."gar_role_permission"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_role_permission"."permission_id" IS '权限ID';


DROP TABLE IF EXISTS "public"."gar_resource_api";
CREATE TABLE "public"."gar_resource_api" (
  resource_id       INT8,
  api_id INT8
);
ALTER TABLE "public"."gar_resource_api"
  ADD PRIMARY KEY ("resource_id", "api_id");
COMMENT ON COLUMN "public"."gar_resource_api"."resource_id" IS '资源ID';
COMMENT ON COLUMN "public"."gar_resource_api"."api_id" IS 'API ID';
