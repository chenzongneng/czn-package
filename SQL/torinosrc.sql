DROP TABLE IF EXISTS "public"."gar_authority";
CREATE TABLE "public"."gar_authority" (
  authority_id bigserial primary key,
  name          varchar(100) COLLATE "default" NOT NULL,
  description   varchar(512) COLLATE "default",
  status  INT4
)
;
COMMENT ON TABLE "public"."gar_authority" IS '权限';
COMMENT ON COLUMN "public"."gar_authority"."name" IS '具体名称';
COMMENT ON COLUMN "public"."gar_authority"."description" IS '详细说明';
COMMENT ON COLUMN "public"."gar_authority"."status" IS '状态';


DROP TABLE IF EXISTS "public"."gar_permission";
CREATE TABLE "public"."gar_permission" (
  permission_id bigserial primary key,
  name   varchar(100) COLLATE "default" NOT NULL,
  api    varchar(100) COLLATE "default" NOT NULL,
  permission   varchar(100) COLLATE "default" NOT NULL,
  description   varchar(512) COLLATE "default",
  url   varchar(100) COLLATE "default",
  method   varchar(32) COLLATE "default",
  status  INT4 NOT NULL
)
;
COMMENT ON TABLE "public"."gar_permission" IS '访问权限';
COMMENT ON COLUMN "public"."gar_permission"."name" IS '具体名称';
COMMENT ON COLUMN "public"."gar_permission"."api" IS 'API';
COMMENT ON COLUMN "public"."gar_permission"."permission" IS '权限标识符';
COMMENT ON COLUMN "public"."gar_permission"."description" IS '说明';
COMMENT ON COLUMN "public"."gar_permission"."url" IS '对应的链接';
COMMENT ON COLUMN "public"."gar_permission"."method" IS '方法';
COMMENT ON COLUMN "public"."gar_permission"."status" IS '状态';

-- auto-generated definition
DROP TABLE IF EXISTS "public"."gar_sys_menu";
CREATE TABLE "public"."gar_sys_menu"(
  menu_id   bigserial primary key,
  parent_id INT4,
  type      INT4,
  order_num INT4,
  code varchar(255) COLLATE "default",
  pcode varchar(255) COLLATE "default",
  alias varchar(255) COLLATE "default",
  name varchar(255) COLLATE "default",
  icon varchar(255) COLLATE "default",
  url varchar(255) COLLATE "default",
  levels INT4,
  source text COLLATE "default",
  path varchar(255) COLLATE "default",
  status INT4,
  is_open INT4
);

COMMENT ON TABLE "public"."gar_sys_menu" IS '菜单';
COMMENT ON COLUMN "public"."gar_sys_menu"."parent_id" IS '父菜单ID';
COMMENT ON COLUMN "public"."gar_sys_menu"."type" IS '类型';
COMMENT ON COLUMN "public"."gar_sys_menu"."order_num" IS '排序';
COMMENT ON COLUMN "public"."gar_sys_menu"."code" IS '菜单编号';
COMMENT ON COLUMN "public"."gar_sys_menu"."pcode" IS '菜单父编号';
COMMENT ON COLUMN "public"."gar_sys_menu"."name" IS '菜单名称';
COMMENT ON COLUMN "public"."gar_sys_menu"."alias" IS '别名';
COMMENT ON COLUMN "public"."gar_sys_menu"."icon" IS '菜单图标';
COMMENT ON COLUMN "public"."gar_sys_menu"."url" IS '菜单URL';
COMMENT ON COLUMN "public"."gar_sys_menu"."levels" IS '层级';
COMMENT ON COLUMN "public"."gar_sys_menu"."source" IS '资源';
COMMENT ON COLUMN "public"."gar_sys_menu"."path" IS '路径';
COMMENT ON COLUMN "public"."gar_sys_menu"."status" IS '状态';
COMMENT ON COLUMN "public"."gar_sys_menu"."is_open" IS '是否开放';

DROP TABLE IF EXISTS "public"."gar_role_authority";
CREATE TABLE "public"."gar_role_authority"(
  role_id INT8,
  authority_id INT8
);
ALTER TABLE "public"."gar_role_authority" ADD PRIMARY KEY ("role_id","authority_id");
COMMENT ON COLUMN "public"."gar_role_authority"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_role_authority"."authority_id" IS '权限ID';

DROP TABLE IF EXISTS "public"."gar_authority_permission";
CREATE TABLE "public"."gar_authority_permission"(
  authority_id INT8,
  permission_id INT8
);
ALTER TABLE "public"."gar_authority_permission" ADD PRIMARY KEY ("authority_id","permission_id");
COMMENT ON COLUMN "public"."gar_authority_permission"."authority_id" IS '权限ID';
COMMENT ON COLUMN "public"."gar_authority_permission"."permission_id" IS '访问权限ID';

DROP TABLE IF EXISTS "public"."gar_authority_menu";
CREATE TABLE "public"."gar_authority_menu"(
  authority_id INT8,
  menu_id INT8
);
ALTER TABLE "public"."gar_authority_menu" ADD PRIMARY KEY ("authority_id","menu_id");
COMMENT ON COLUMN "public"."gar_authority_menu"."authority_id" IS '权限ID';
COMMENT ON COLUMN "public"."gar_authority_menu"."menu_id" IS '菜单ID';
