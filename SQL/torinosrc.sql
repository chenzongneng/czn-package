--------------------- 数据表 ----------------------

-- auto-generated definition
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

INSERT INTO public.gar_applications (application_id, name, code, company, remark) VALUES (1, 'Garnet', 'garnet', '丰石科技', 'Garnet安全模块');

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

-- 初始账号：admin  密码：admin
INSERT INTO public.gar_users (user_id, username, password, name, email, mobile, status, create_time)
VALUES (1, 'admin', '$2a$12$rFhdbcz5igsiwi45dt5S.uvg36BIT4Hk1AgV5QWl5NWQ0k0b2SrdO', '管理员', 'admin@you.xiang', '18812345678', 1, '2017-10-19 17:39:58.848979');

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


-- DROP TABLE IF EXISTS "public"."gar_authorities";
-- CREATE TABLE "public"."gar_authorities" (
--   authority_id BIGSERIAL PRIMARY KEY,
--   application_id INT8,
--   name         VARCHAR(100) COLLATE "default" NOT NULL,
--   wildcard  VARCHAR(512) COLLATE "default",
--   description  VARCHAR(512) COLLATE "default",
--   status       INT4
-- );
-- COMMENT ON TABLE "public"."gar_authorities" IS '权限';
-- COMMENT ON COLUMN "public"."gar_authorities"."application_id" IS '应用ID';
-- COMMENT ON COLUMN "public"."gar_authorities"."name" IS '具体名称';
-- COMMENT ON COLUMN "public"."gar_authorities"."wildcard" IS '通配符';
-- COMMENT ON COLUMN "public"."gar_authorities"."description" IS '详细说明';
-- COMMENT ON COLUMN "public"."gar_authorities"."status" IS '状态';


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


-- DROP TABLE IF EXISTS "public"."gar_permissions";
-- CREATE TABLE "public"."gar_permissions" (
--   permission_id  BIGSERIAL PRIMARY KEY,
--   application_id INT8,
--   parent_id      INT8,
--   name           VARCHAR(100) COLLATE "default",
--   permission     VARCHAR(100) COLLATE "default",
--   description    VARCHAR(512) COLLATE "default",
--   url            VARCHAR(100) COLLATE "default",
--   method         VARCHAR(32) COLLATE "default",
--   status         INT4 NOT NULL
-- );
-- COMMENT ON TABLE "public"."gar_permissions" IS '访问权限';
-- COMMENT ON COLUMN "public"."gar_permissions"."application_id" IS '应用ID';
-- COMMENT ON COLUMN "public"."gar_permissions"."parent_id" IS '父访问权限ID';
-- COMMENT ON COLUMN "public"."gar_permissions"."name" IS '访问权限名称';
-- COMMENT ON COLUMN "public"."gar_permissions"."permission" IS '权限标识符';
-- COMMENT ON COLUMN "public"."gar_permissions"."description" IS '说明';
-- COMMENT ON COLUMN "public"."gar_permissions"."url" IS '对应的链接';
-- COMMENT ON COLUMN "public"."gar_permissions"."method" IS '方法';
-- COMMENT ON COLUMN "public"."gar_permissions"."status" IS '状态';

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

--------------------- 关联表 ----------------------

DROP TABLE IF EXISTS "public"."gar_user_application";
CREATE TABLE "public"."gar_user_application" (
  user_id INT8,
  app_id  INT8
);
ALTER TABLE "public"."gar_user_application"
  ADD PRIMARY KEY ("user_id", "app_id");
COMMENT ON COLUMN "public"."gar_user_application"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."gar_user_application"."app_id" IS '应用ID';

DROP TABLE IF EXISTS "public"."gar_role_permission";
CREATE TABLE "public"."gar_role_permission" (
  role_id      INT8,
  permission_id INT8
);
ALTER TABLE "public"."gar_role_permission"
  ADD PRIMARY KEY ("role_id", "permission_id");
COMMENT ON COLUMN "public"."gar_role_permission"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_role_permission"."permission_id" IS '权限ID';

-- DROP TABLE IF EXISTS "public"."gar_authority_menu";
-- CREATE TABLE "public"."gar_authority_menu" (
--   authority_id INT8,
--   menu_id      INT8
-- );
-- ALTER TABLE "public"."gar_authority_menu"
--   ADD PRIMARY KEY ("authority_id", "menu_id");
-- COMMENT ON COLUMN "public"."gar_authority_menu"."authority_id" IS '权限ID';
-- COMMENT ON COLUMN "public"."gar_authority_menu"."menu_id" IS '菜单ID';

-- DROP TABLE IF EXISTS "public"."gar_menu_permission";
-- CREATE TABLE "public"."gar_menu_permission" (
--   menu_id       INT8,
--   permission_id INT8
-- );
-- ALTER TABLE "public"."gar_menu_permission"
--   ADD PRIMARY KEY ("menu_id", "permission_id");
-- COMMENT ON COLUMN "public"."gar_menu_permission"."menu_id" IS '菜单ID';
-- COMMENT ON COLUMN "public"."gar_menu_permission"."permission_id" IS '访问权限ID';

DROP TABLE IF EXISTS "public"."gar_resource_api";
CREATE TABLE "public"."gar_resource_api" (
  resource_id       INT8,
  api_id INT8
);
ALTER TABLE "public"."gar_resource_api"
  ADD PRIMARY KEY ("resource_id", "api_id");
COMMENT ON COLUMN "public"."gar_resource_api"."resource_id" IS '资源ID';
COMMENT ON COLUMN "public"."gar_resource_api"."api_id" IS 'API ID';

--------------------- 视图 ----------------------

DROP VIEW IF EXISTS "public"."gar_v_user_application";
DROP VIEW IF EXISTS "public"."gar_v_user_menu";
DROP VIEW IF EXISTS "public"."gar_v_user_permission";
DROP VIEW IF EXISTS "public"."gar_v_menu_permission";

CREATE VIEW "public"."gar_v_user_application" AS
  SELECT
    a.app_id      AS app_id,
    u.user_id     AS user_id,
    u.username    AS username,
    u.password    AS password,
    u.email       AS email,
    u.mobile      AS mobile,
    u.status      AS status,
    u.create_time AS create_time
  FROM gar_users u
    LEFT JOIN gar_user_application ua ON u.user_id = ua.user_id
    LEFT JOIN gar_applications a ON a.app_id = ua.app_id
  WHERE a.app_id NOTNULL;
COMMENT ON VIEW "public"."gar_v_user_application" IS '用户-应用视图';
--
-- CREATE VIEW "public"."gar_v_user_menu" AS
--   SELECT DISTINCT
--     u.user_id,
--     ap.app_id,
--     m.*
--   FROM gar_users u
--     LEFT JOIN gar_user_application ap ON ap.user_id = u.user_id
--     LEFT JOIN gar_user_dept ud ON ud.user_id = u.user_id
--     LEFT JOIN gar_role_dept rd ON rd.dept_id = ud.dept_id
--     LEFT JOIN gar_role_authority ra ON ra.role_id = rd.role_id
--     LEFT JOIN gar_authority_menu am ON am.authority_id = ra.authority_id
--     LEFT JOIN gar_menus m ON m.menu_id = am.menu_id
--   WHERE m.status = 1;
-- COMMENT ON VIEW "public"."gar_v_user_menu" IS '用户-菜单视图';
--
-- CREATE VIEW "public"."gar_v_user_permission" AS
--   SELECT DISTINCT
--     u.user_id,
--     ap.app_id,
--     p.permission
--   FROM gar_users u
--     LEFT JOIN gar_user_application ap ON ap.user_id = u.user_id
--     LEFT JOIN gar_user_dept ud ON ud.user_id = u.user_id
--     LEFT JOIN gar_role_dept rd ON rd.dept_id = ud.dept_id
--     LEFT JOIN gar_role_authority ra ON ra.role_id = rd.role_id
--     LEFT JOIN gar_authority_menu am ON am.authority_id = ra.authority_id
--     LEFT JOIN gar_menus m ON m.menu_id = am.menu_id
--     LEFT JOIN gar_menu_permission mp ON mp.menu_id = m.menu_id
--     LEFT JOIN gar_permissions p ON mp.permission_id = p.permission_id
--   WHERE m.status = 1 AND p.status = 1;
-- COMMENT ON VIEW "public"."gar_v_user_application" IS '用户-访问权限视图';
--
-- CREATE VIEW "public"."gar_v_menu_permission" AS
--   SELECT
--     m.menu_id,
--     m.name AS menu_name,
--     p.permission_id,
--     p.application_id,
--     p.parent_id,
--     p.name,
--     p.permission,
--     p.description,
--     p.url,
--     p.method,
--     p.status
--   FROM gar_menus m
--     LEFT JOIN gar_menu_permission mp ON mp.menu_id = m.menu_id
--     LEFT JOIN gar_permissions p ON p.permission_id = mp.permission_id;
-- COMMENT ON VIEW "public"."gar_v_menu_permission" IS '菜单-访问权限视图';
