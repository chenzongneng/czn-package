-- ----------------------------
-- Table structure for gar_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_user";
CREATE TABLE "public"."gar_user" (
  "user_id" serial NOT NULL,
  tenant_id int4 NOT NULL,
  app_id int4 NOT NULL,
  "username" varchar(50) COLLATE "default" NOT NULL,
  "password" varchar(200) COLLATE "default",
  "email" varchar(100) COLLATE "default",
  "mobile" varchar(100) COLLATE "default",
  "status" int4,
  "create_time" timestamp(6) DEFAULT now()
)
;
COMMENT ON COLUMN "public"."gar_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."gar_user"."password" IS '密码';
COMMENT ON COLUMN "public"."gar_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."gar_user"."mobile" IS '手机号';
COMMENT ON COLUMN "public"."gar_user"."status" IS '状态  0：禁用   1：正常';
COMMENT ON COLUMN "public"."gar_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."gar_user"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_user"."app_id" IS '应用ID';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Uniques structure for table gar_user
-- ----------------------------
ALTER TABLE "public"."gar_user" ADD UNIQUE ("username");

-- ----------------------------
-- Primary Key structure for table gar_user
-- ----------------------------
ALTER TABLE "public"."gar_user" ADD PRIMARY KEY ("user_id");

-- ----------------------------
-- Table structure for gar_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_role";
CREATE TABLE "public"."gar_role" (
  "role_id" serial,
  tenant_id int4 NOT NULL,
  app_id int4 NOT NULL,
  "name" varchar(100) COLLATE "default",
  "remark" varchar(100) COLLATE "default",
  "create_time" timestamp(6) DEFAULT now()
)
;
COMMENT ON COLUMN "public"."gar_role"."name" IS '角色名称';
COMMENT ON COLUMN "public"."gar_role"."remark" IS '备注';
COMMENT ON COLUMN "public"."gar_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."gar_role"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_role"."app_id" IS '应用ID';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table gar_role
-- ----------------------------
ALTER TABLE "public"."gar_role" ADD PRIMARY KEY ("role_id");

DROP TABLE IF EXISTS "public"."gar_dept";
CREATE TABLE "public"."gar_dept" (
  "dept_id" serial,
  "parent_dept_id" int8,
  tenant_id int4 NOT NULL,
  app_id int4 NOT NULL,
  "name" varchar(50) COLLATE "default",
  "order_num" int4
)
;
COMMENT ON COLUMN "public"."gar_dept"."parent_dept_id" IS '上级部门ID，一级部门为0';
COMMENT ON COLUMN "public"."gar_dept"."name" IS '部门名称';
COMMENT ON COLUMN "public"."gar_dept"."order_num" IS '排序';
COMMENT ON COLUMN "public"."gar_dept"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_dept"."app_id" IS '应用ID';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table gar_dept
-- ----------------------------
ALTER TABLE "public"."gar_dept" ADD PRIMARY KEY ("dept_id");

DROP TABLE IF EXISTS "public"."gar_user_dept";
CREATE TABLE "public"."gar_user_dept" (
  user_id int4 NOT NULL,
  dept_id int4 NOT NULL
)
;
COMMENT ON COLUMN gar_user_dept.user_id IS '用户ID';
COMMENT ON COLUMN gar_user_dept.dept_id IS '部门ID';

DROP TABLE IF EXISTS "public"."gar_role_dept";
CREATE TABLE "public"."gar_role_dept" (
  role_id int4 NOT NULL,
  dept_id int4 NOT NULL
)
;
COMMENT ON COLUMN gar_role_dept.role_id IS '角色ID';
COMMENT ON COLUMN gar_role_dept.dept_id IS '部门ID';


DROP TABLE IF EXISTS "public"."gar_permission";
CREATE TABLE "public"."gar_permission" (
  permission_id serial,
  tenant_id int4 NOT NULL,
  app_id int4 NOT NULL,
  name VARCHAR(100) NOT NULL,
  class varchar(255),
  actions varchar(255),
  resource varchar(255)
)
;
COMMENT ON COLUMN "public"."gar_permission"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_permission"."app_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_permission"."name" IS '这个Permission具体的名称';
COMMENT ON COLUMN "public"."gar_permission"."class" IS '这个Permission对应的java类';
COMMENT ON COLUMN "public"."gar_permission"."actions" IS '用户可以在这个资源上的权限列表，譬如read（读，访问），edit（编辑）。当赋予到Privilege时只能选择其中一个';
COMMENT ON COLUMN "public"."gar_permission"."resource" IS '通过属性来定义该Permission控制的资源';

ALTER TABLE "public"."gar_permission" ADD PRIMARY KEY ("permission_id");


/*DROP TABLE IF EXISTS "public"."gar_menu";
CREATE TABLE "public"."gar_menu" (
  menu_id serial,
  parent_menu_id int4,
  tenant_id int4 NOT NULL,
  app_id int4 NOT NULL,
  name varchar(100),
  url varchar(100),
  icon varchar(100)
)
;
COMMENT ON COLUMN "public"."gar_menu"."parent_menu_id" IS '父菜单名称';
COMMENT ON COLUMN "public"."gar_menu"."app_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_menu"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_menu"."name" IS '菜单名称';
COMMENT ON COLUMN "public"."gar_menu"."url" IS '路径';
COMMENT ON COLUMN "public"."gar_menu"."icon" IS '图标';

ALTER TABLE "public"."gar_menu" ADD PRIMARY KEY ("menu_id");

DROP TABLE IF EXISTS "public"."gar_resource";
CREATE TABLE "public"."gar_resource" (
  resource_id serial,
  parent_resource_id int4,
  tenant_id int4 NOT NULL,
  app_id int4 NOT NULL,
  name varchar(100),
  type varchar(100),
  url varchar(100)
)
;
COMMENT ON COLUMN "public"."gar_resource"."parent_resource_id" IS '父资源名称';
COMMENT ON COLUMN "public"."gar_resource"."app_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_resource"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_resource"."name" IS '资源名称';
COMMENT ON COLUMN "public"."gar_resource"."url" IS '路径';
COMMENT ON COLUMN "public"."gar_resource"."type" IS '资源类型';

ALTER TABLE "public"."gar_resource" ADD PRIMARY KEY ("resource_id");


DROP TABLE IF EXISTS "public"."gar_operation";
CREATE TABLE "public"."gar_operation" (
  operation_id serial,
  parent_operation_id int4,
  tenant_id int4 NOT NULL,
  app_id int4 NOT NULL,
  name varchar(100)
)
;
COMMENT ON COLUMN "public"."gar_operation"."parent_operation_id" IS '父操作名称';
COMMENT ON COLUMN "public"."gar_operation"."app_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_operation"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_operation"."name" IS '操作名称';

ALTER TABLE "public"."gar_operation" ADD PRIMARY KEY ("operation_id");*/


DROP TABLE IF EXISTS "public"."gar_privilege";
CREATE TABLE "public"."gar_privilege" (
  role_id int4 NOT NULL  ,
  permission_id int4 NOT NULL,
  action VARCHAR(200)
)
;
COMMENT ON COLUMN gar_privilege.permission_id IS '权限ID';
COMMENT ON COLUMN gar_privilege.role_id IS '角色ID';
COMMENT ON COLUMN gar_privilege.action IS '对应权限resource上的某个action';

/*DROP TABLE IF EXISTS "public"."gar_permission_operation";
CREATE TABLE "public"."gar_permission_operation" (
  operation_id int4 NOT NULL  ,
  permission_id int4 NOT NULL
)
;
COMMENT ON COLUMN gar_permission_operation.permission_id IS '权限ID';
COMMENT ON COLUMN gar_permission_operation.operation_id IS '角色ID';

DROP TABLE IF EXISTS "public"."gar_permission_resource";
CREATE TABLE "public"."gar_permission_resource" (
  resource_id int4 NOT NULL  ,
  permission_id int4 NOT NULL
)
;
COMMENT ON COLUMN gar_permission_resource.permission_id IS '权限ID';
COMMENT ON COLUMN gar_permission_resource.resource_id IS '资源ID';

DROP TABLE IF EXISTS "public"."gar_permission_menu";
CREATE TABLE "public"."gar_permission_menu" (
  menu_id int4 NOT NULL  ,
  permission_id int4 NOT NULL
)
;
COMMENT ON COLUMN gar_permission_menu.permission_id IS '权限ID';
COMMENT ON COLUMN gar_permission_menu.menu_id IS '菜单ID';*/

DROP TABLE IF EXISTS "public"."gar_application";
CREATE TABLE "public"."gar_application" (
  app_id serial,
  name varchar(100),
  company varchar(100),
  remark varchar(100)
)
;

COMMENT ON COLUMN "public"."gar_application"."app_id" IS '应用ID';
COMMENT ON COLUMN "public"."gar_application"."name" IS '应用名称';
COMMENT ON COLUMN "public"."gar_application"."company" IS '所属公司';
COMMENT ON COLUMN "public"."gar_application"."remark" IS '简介';

ALTER TABLE "public"."gar_application" ADD PRIMARY KEY ("app_id");

DROP TABLE IF EXISTS "public"."gar_tenant";
CREATE TABLE "public"."gar_tenant" (
  tenant_id serial,
  name varchar(100),
  remark varchar(100)
)
;

COMMENT ON COLUMN "public"."gar_tenant"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "public"."gar_tenant"."name" IS '租户名称';
COMMENT ON COLUMN "public"."gar_tenant"."remark" IS '简介';

ALTER TABLE "public"."gar_tenant" ADD PRIMARY KEY ("tenant_id");

-- ----------------------------
-- Table structure for gar_log_operation
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_log_operation";
CREATE TABLE "public"."gar_log_operation" (
  "id" serial,
  "url" varchar(200) COLLATE "default",
  "method" varchar(50) COLLATE "default",
  "operation" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table gar_log_operation
-- ----------------------------
ALTER TABLE "public"."gar_log_operation" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Table structure for gar_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_log";
CREATE TABLE "public"."gar_log" (
  "id" serial,
  "username" varchar(50) COLLATE "default",
  "operation" varchar(50) COLLATE "default",
  "method" varchar(50) COLLATE "default",
  "url" varchar(255) COLLATE "default",
  "ip" varchar(255) COLLATE "default",
  "sql" text COLLATE "default",
  "created_time" timestamp(6) DEFAULT now()
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_log"."username" IS '用户名';
COMMENT ON COLUMN "public"."gar_log"."operation" IS '用户操作';
COMMENT ON COLUMN "public"."gar_log"."method" IS '请求方法';
COMMENT ON COLUMN "public"."gar_log"."url" IS '请求URL';
COMMENT ON COLUMN "public"."gar_log"."ip" IS 'ip';
COMMENT ON COLUMN "public"."gar_log"."sql" IS '执行sql';
COMMENT ON COLUMN "public"."gar_log"."created_time" IS '创建时间';

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table gar_log
-- ----------------------------
ALTER TABLE "public"."gar_log" ADD PRIMARY KEY ("id");
