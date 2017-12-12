DROP TABLE IF EXISTS "public"."gar_authorities";
CREATE TABLE "public"."gar_authorities" (
  authority_id bigserial primary key,
  name          varchar(100) COLLATE "default" NOT NULL,
  description   varchar(512) COLLATE "default",
  status  INT4
)
;
COMMENT ON TABLE "public"."gar_authorities" IS '权限';
COMMENT ON COLUMN "public"."gar_authorities"."name" IS '具体名称';
COMMENT ON COLUMN "public"."gar_authorities"."description" IS '详细说明';
COMMENT ON COLUMN "public"."gar_authorities"."status" IS '状态';


DROP TABLE IF EXISTS "public"."gar_permissions";
CREATE TABLE "public"."gar_permissions" (
  permission_id bigserial primary key,
  application_id INT8,
  parent_id INT8,
  name   varchar(100) COLLATE "default",
  permission   varchar(100) COLLATE "default",
  description   varchar(512) COLLATE "default",
  url   varchar(100) COLLATE "default",
  method   varchar(32) COLLATE "default",
  status  INT4 NOT NULL
)
;
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
CREATE TABLE "public"."gar_menus"(
  menu_id   bigserial primary key,
  application_id INT8,
  type  INT4,
  name varchar(255) COLLATE "default",
  description  varchar(255) COLLATE "default",
  code varchar(255) COLLATE "default",
  parent_code varchar(255) COLLATE "default",
  icon varchar(255) COLLATE "default",
  url varchar(255) COLLATE "default",
  order_num INT4,
  status INT4
);

ALTER TABLE "public"."gar_menus" ADD UNIQUE ("code");

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

DROP TABLE IF EXISTS "public"."gar_role_authority";
CREATE TABLE "public"."gar_role_authority"(
  role_id INT8,
  authority_id INT8
);
ALTER TABLE "public"."gar_role_authority" ADD PRIMARY KEY ("role_id","authority_id");
COMMENT ON COLUMN "public"."gar_role_authority"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_role_authority"."authority_id" IS '权限ID';

DROP TABLE IF EXISTS "public"."gar_authority_menu";
CREATE TABLE "public"."gar_authority_menu"(
  authority_id INT8,
  menu_id INT8
);
ALTER TABLE "public"."gar_authority_menu" ADD PRIMARY KEY ("authority_id","menu_id");
COMMENT ON COLUMN "public"."gar_authority_menu"."authority_id" IS '权限ID';
COMMENT ON COLUMN "public"."gar_authority_menu"."menu_id" IS '菜单ID';

DROP TABLE IF EXISTS "public"."gar_menu_permission";
CREATE TABLE "public"."gar_menu_permission"(
  menu_id INT8,
  permission_id INT8
);
ALTER TABLE "public"."gar_menu_permission" ADD PRIMARY KEY ("menu_id","permission_id");
COMMENT ON COLUMN "public"."gar_menu_permission"."menu_id" IS '菜单ID';
COMMENT ON COLUMN "public"."gar_menu_permission"."permission_id" IS '访问权限ID';
