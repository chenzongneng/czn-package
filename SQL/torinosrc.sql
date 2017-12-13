--------------------- 数据表 ----------------------

DROP TABLE IF EXISTS "public"."gar_users";
CREATE TABLE "public"."gar_users" (
  user_id     BIGSERIAL PRIMARY KEY,
  username    VARCHAR(50) COLLATE "default" NOT NULL,
  password    VARCHAR(200) COLLATE "default",
  email       VARCHAR(100) COLLATE "default",
  mobile      VARCHAR(100) COLLATE "default",
  status      INT4,
  create_time TIMESTAMP(6)
);

ALTER TABLE "public"."gar_users"
  ADD UNIQUE ("username");

COMMENT ON TABLE "public"."gar_users" IS '用户';
COMMENT ON COLUMN "public"."gar_users"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."gar_users"."username" IS '账号';
COMMENT ON COLUMN "public"."gar_users"."password" IS '密码';
COMMENT ON COLUMN "public"."gar_users"."email" IS '邮箱';
COMMENT ON COLUMN "public"."gar_users"."mobile" IS '电话';
COMMENT ON COLUMN "public"."gar_users"."status" IS '状态';
COMMENT ON COLUMN "public"."gar_users"."create_time" IS '创建时间';


DROP TABLE IF EXISTS "public"."gar_authorities";
CREATE TABLE "public"."gar_authorities" (
  authority_id BIGSERIAL PRIMARY KEY,
  name         VARCHAR(100) COLLATE "default" NOT NULL,
  description  VARCHAR(512) COLLATE "default",
  status       INT4
);
COMMENT ON TABLE "public"."gar_authorities" IS '权限';
COMMENT ON COLUMN "public"."gar_authorities"."name" IS '具体名称';
COMMENT ON COLUMN "public"."gar_authorities"."description" IS '详细说明';
COMMENT ON COLUMN "public"."gar_authorities"."status" IS '状态';


DROP TABLE IF EXISTS "public"."gar_permissions";
CREATE TABLE "public"."gar_permissions" (
  permission_id  BIGSERIAL PRIMARY KEY,
  application_id INT8,
  parent_id      INT8,
  name           VARCHAR(100) COLLATE "default",
  permission     VARCHAR(100) COLLATE "default",
  description    VARCHAR(512) COLLATE "default",
  url            VARCHAR(100) COLLATE "default",
  method         VARCHAR(32) COLLATE "default",
  status         INT4 NOT NULL
);
COMMENT ON TABLE "public"."gar_permissions" IS '访问权限';
COMMENT ON COLUMN "public"."gar_permissions"."application_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_permissions"."parent_id" IS '父访问权限ID';
COMMENT ON COLUMN "public"."gar_permissions"."name" IS '访问权限名称';
COMMENT ON COLUMN "public"."gar_permissions"."permission" IS '权限标识符';
COMMENT ON COLUMN "public"."gar_permissions"."description" IS '说明';
COMMENT ON COLUMN "public"."gar_permissions"."url" IS '对应的链接';
COMMENT ON COLUMN "public"."gar_permissions"."method" IS '方法';
COMMENT ON COLUMN "public"."gar_permissions"."status" IS '状态';

-- auto-generated definition
DROP TABLE IF EXISTS "public"."gar_menus";
CREATE TABLE "public"."gar_menus" (
  menu_id        BIGSERIAL PRIMARY KEY,
  application_id INT8,
  type           INT4,
  name           VARCHAR(255) COLLATE "default",
  description    VARCHAR(255) COLLATE "default",
  code           VARCHAR(255) COLLATE "default",
  parent_code    VARCHAR(255) COLLATE "default",
  icon           VARCHAR(255) COLLATE "default",
  url            VARCHAR(255) COLLATE "default",
  order_num      INT4,
  status         INT4
);

ALTER TABLE "public"."gar_menus"
  ADD UNIQUE ("code");

COMMENT ON TABLE "public"."gar_menus" IS '菜单';
COMMENT ON COLUMN "public"."gar_menus"."application_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_menus"."type" IS '类型';
COMMENT ON COLUMN "public"."gar_menus"."name" IS '菜单名称';
COMMENT ON COLUMN "public"."gar_menus"."description" IS '说明';
COMMENT ON COLUMN "public"."gar_menus"."code" IS '菜单标识';
COMMENT ON COLUMN "public"."gar_menus"."parent_code" IS '父菜单标识';
COMMENT ON COLUMN "public"."gar_menus"."icon" IS '菜单图标';
COMMENT ON COLUMN "public"."gar_menus"."url" IS '菜单URL';
COMMENT ON COLUMN "public"."gar_menus"."order_num" IS '排序';
COMMENT ON COLUMN "public"."gar_menus"."status" IS '状态';

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

DROP TABLE IF EXISTS "public"."gar_role_authority";
CREATE TABLE "public"."gar_role_authority" (
  role_id      INT8,
  authority_id INT8
);
ALTER TABLE "public"."gar_role_authority"
  ADD PRIMARY KEY ("role_id", "authority_id");
COMMENT ON COLUMN "public"."gar_role_authority"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_role_authority"."authority_id" IS '权限ID';

DROP TABLE IF EXISTS "public"."gar_authority_menu";
CREATE TABLE "public"."gar_authority_menu" (
  authority_id INT8,
  menu_id      INT8
);
ALTER TABLE "public"."gar_authority_menu"
  ADD PRIMARY KEY ("authority_id", "menu_id");
COMMENT ON COLUMN "public"."gar_authority_menu"."authority_id" IS '权限ID';
COMMENT ON COLUMN "public"."gar_authority_menu"."menu_id" IS '菜单ID';

DROP TABLE IF EXISTS "public"."gar_menu_permission";
CREATE TABLE "public"."gar_menu_permission" (
  menu_id       INT8,
  permission_id INT8
);
ALTER TABLE "public"."gar_menu_permission"
  ADD PRIMARY KEY ("menu_id", "permission_id");
COMMENT ON COLUMN "public"."gar_menu_permission"."menu_id" IS '菜单ID';
COMMENT ON COLUMN "public"."gar_menu_permission"."permission_id" IS '访问权限ID';

--------------------- 视图 ----------------------

DROP VIEW IF EXISTS "public"."gar_v_user_application";
CREATE VIEW "public"."gar_v_user_application" AS
  SELECT
    a.app_id AS app_id,
    u.user_id AS user_id,
    u.username AS username,
    u.password AS password,
    u.email AS email,
    u.mobile AS mobile,
    u.status AS status,
    u.create_time AS create_time
  FROM gar_users u
    LEFT JOIN gar_user_application ua ON u.user_id = ua.user_id
    LEFT JOIN gar_applications a ON a.app_id = ua.app_id
  WHERE a.app_id NOTNULL;


DROP VIEW IF EXISTS "public"."gar_v_user_menu";
CREATE VIEW "public"."gar_v_user_menu" AS
  SELECT DISTINCT
    u.user_id,
    ap.app_id,
    m.*
  FROM gar_users u
    LEFT JOIN gar_user_application ap ON ap.user_id = u.user_id
    LEFT JOIN gar_user_dept ud ON ud.user_id = u.user_id
    LEFT JOIN gar_role_dept rd ON rd.dept_id = ud.dept_id
    LEFT JOIN gar_role_authority ra ON ra.role_id = rd.role_id
    LEFT JOIN gar_authority_menu am ON am.authority_id = ra.authority_id
    LEFT JOIN gar_menus m ON m.menu_id = am.menu_id
  WHERE m.status = 1
