-- ----------------------------
-- Table structure for gar_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_schedule_job";
CREATE TABLE "public"."gar_schedule_job" (
  "job_id" serial,
  "bean_name" varchar(200) COLLATE "default",
  "method_name" varchar(100) COLLATE "default",
  "params" varchar(2000) COLLATE "default",
  "cron_expression" varchar(100) COLLATE "default",
  "status" int4,
  "remark" varchar(255) COLLATE "default",
  "create_time" timestamp(6) DEFAULT now()
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_schedule_job"."job_id" IS '任务id';
COMMENT ON COLUMN "public"."gar_schedule_job"."bean_name" IS 'spring bean名称';
COMMENT ON COLUMN "public"."gar_schedule_job"."method_name" IS '方法名';
COMMENT ON COLUMN "public"."gar_schedule_job"."params" IS '参数';
COMMENT ON COLUMN "public"."gar_schedule_job"."cron_expression" IS 'cron表达式';
COMMENT ON COLUMN "public"."gar_schedule_job"."status" IS '任务状态  0：正常  1：暂停';
COMMENT ON COLUMN "public"."gar_schedule_job"."remark" IS '备注';
COMMENT ON COLUMN "public"."gar_schedule_job"."create_time" IS '创建时间';

ALTER TABLE "public"."gar_schedule_job" ADD PRIMARY KEY ("job_id");

INSERT INTO "public"."gar_schedule_job" ("job_id", "bean_name", "method_name", "params", "cron_expression", "status", "remark", "create_time") VALUES ('1', 'testTask', 'test', 'renren', '0 0/30 * * * ?', '0', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO "public"."gar_schedule_job" ("job_id", "bean_name", "method_name", "params", "cron_expression", "status", "remark", "create_time") VALUES ('2', 'testTask', 'test2', NULL, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56');



-- ----------------------------
-- Table structure for gar_schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_schedule_job_log";
CREATE TABLE "public"."gar_schedule_job_log" (
  "log_id" serial,
  "job_id" int8 NOT NULL,
  "bean_name" varchar(200) COLLATE "default",
  "method_name" varchar(100) COLLATE "default",
  "params" varchar(2000) COLLATE "default",
  "status" int4,
  "error" varchar(2000) COLLATE "default",
  "times" int8,
  "create_time" timestamp(6) DEFAULT now()
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_schedule_job_log"."log_id" IS '任务日志id';
COMMENT ON COLUMN "public"."gar_schedule_job_log"."job_id" IS '任务id';
COMMENT ON COLUMN "public"."gar_schedule_job_log"."bean_name" IS 'spring bean名称';
COMMENT ON COLUMN "public"."gar_schedule_job_log"."method_name" IS '方法名';
COMMENT ON COLUMN "public"."gar_schedule_job_log"."params" IS '参数';
COMMENT ON COLUMN "public"."gar_schedule_job_log"."status" IS '任务状态    0：成功    1：失败';
COMMENT ON COLUMN "public"."gar_schedule_job_log"."error" IS '失败信息';
COMMENT ON COLUMN "public"."gar_schedule_job_log"."times" IS '耗时(单位：毫秒)';
COMMENT ON COLUMN "public"."gar_schedule_job_log"."create_time" IS '创建时间';

ALTER TABLE "public"."gar_schedule_job_log" ADD PRIMARY KEY ("log_id");

-- ----------------------------
-- Table structure for gar_sys_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_config";
CREATE TABLE "public"."gar_sys_config" (
  "id" serial,
  "key" varchar(50) COLLATE "default",
  "value" varchar(2000) COLLATE "default",
  "status" int4 DEFAULT 1,
  "remark" varchar(500) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_config"."key" IS 'key';
COMMENT ON COLUMN "public"."gar_sys_config"."value" IS 'value';
COMMENT ON COLUMN "public"."gar_sys_config"."status" IS '状态   0：隐藏   1：显示';
COMMENT ON COLUMN "public"."gar_sys_config"."remark" IS '备注';

ALTER TABLE "public"."gar_sys_config" ADD PRIMARY KEY ("id");

ALTER TABLE "public"."gar_sys_config" ADD UNIQUE ("key");

-- ----------------------------
-- Table structure for gar_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_dept";
CREATE TABLE "public"."gar_sys_dept" (
  "dept_id" serial,
  "parent_id" int8,
  "name" varchar(50) COLLATE "default",
  "order_num" int4,
  "del_flag" int4 DEFAULT 0
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_dept"."parent_id" IS '上级部门ID，一级部门为0';
COMMENT ON COLUMN "public"."gar_sys_dept"."name" IS '部门名称';
COMMENT ON COLUMN "public"."gar_sys_dept"."order_num" IS '排序';
COMMENT ON COLUMN "public"."gar_sys_dept"."del_flag" IS '是否删除  -1：已删除  0：正常';

-- ----------------------------
-- Records of gar_sys_dept
-- ----------------------------
INSERT INTO "public"."gar_sys_dept" VALUES ('1', '0', '人人开源集团', '0', '0');
INSERT INTO "public"."gar_sys_dept" VALUES ('2', '1', '长沙分公司', '1', '0');
INSERT INTO "public"."gar_sys_dept" VALUES ('3', '1', '上海分公司', '2', '0');
INSERT INTO "public"."gar_sys_dept" VALUES ('4', '3', '技术部', '0', '0');
INSERT INTO "public"."gar_sys_dept" VALUES ('5', '3', '销售部', '1', '0');

ALTER TABLE "public"."gar_sys_dept" ADD PRIMARY KEY ("dept_id");

-- ----------------------------
-- Table structure for gar_sys_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_log";
CREATE TABLE "public"."gar_sys_log" (
  "id" serial,
  "username" varchar(50) COLLATE "default",
  "operation" varchar(50) COLLATE "default",
  "method" varchar(200) COLLATE "default",
  "params" varchar(5000) COLLATE "default",
  "time" int8 NOT NULL,
  "ip" varchar(64) COLLATE "default",
  "create_date" timestamp(6) DEFAULT now()
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_log"."username" IS '用户名';
COMMENT ON COLUMN "public"."gar_sys_log"."operation" IS '用户操作';
COMMENT ON COLUMN "public"."gar_sys_log"."method" IS '请求方法';
COMMENT ON COLUMN "public"."gar_sys_log"."params" IS '请求参数';
COMMENT ON COLUMN "public"."gar_sys_log"."time" IS '执行时长(毫秒)';
COMMENT ON COLUMN "public"."gar_sys_log"."ip" IS 'IP地址';
COMMENT ON COLUMN "public"."gar_sys_log"."create_date" IS '创建时间';

ALTER TABLE "public"."gar_sys_log" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Table structure for gar_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_menu";
CREATE TABLE "public"."gar_sys_menu" (
  "menu_id" serial,
  "parent_id" int8,
  "name" varchar(50) COLLATE "default",
  "url" varchar(200) COLLATE "default",
  "perms" varchar(500) COLLATE "default",
  "type" int4,
  "icon" varchar(50) COLLATE "default",
  "order_num" int4
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_menu"."parent_id" IS '父菜单ID，一级菜单为0';
COMMENT ON COLUMN "public"."gar_sys_menu"."name" IS '菜单名称';
COMMENT ON COLUMN "public"."gar_sys_menu"."url" IS '菜单URL';
COMMENT ON COLUMN "public"."gar_sys_menu"."perms" IS '授权(多个用逗号分隔，如：user:list,user:create)';
COMMENT ON COLUMN "public"."gar_sys_menu"."type" IS '类型   0：目录   1：菜单   2：按钮';
COMMENT ON COLUMN "public"."gar_sys_menu"."icon" IS '菜单图标';
COMMENT ON COLUMN "public"."gar_sys_menu"."order_num" IS '排序';

ALTER TABLE "public"."gar_sys_menu" ADD PRIMARY KEY ("menu_id");

INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('1', '0', '系统管理', NULL, NULL, '0', 'fa fa-cog', '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('2', '1', '用户管理', 'modules/sys/user.html', NULL, '1', 'fa fa-user', '1');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('3', '1', '角色管理', 'modules/sys/role.html', NULL, '1', 'fa fa-user-secret', '3');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('4', '1', '菜单管理', 'modules/sys/menu.html', NULL, '1', 'fa fa-th-list', '4');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('15', '2', '查看', NULL, 'sys:user:list,sys:user:info', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('16', '2', '新增', NULL, 'sys:user:save,sys:role:select', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('17', '2', '修改', NULL, 'sys:user:update,sys:role:select', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('18', '2', '删除', NULL, 'sys:user:delete', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('19', '3', '查看', NULL, 'sys:role:list,sys:role:info', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('20', '3', '新增', NULL, 'sys:role:save,sys:menu:perms', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('21', '3', '修改', NULL, 'sys:role:update,sys:menu:perms', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('22', '3', '删除', NULL, 'sys:role:delete', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('23', '4', '查看', NULL, 'sys:menu:list,sys:menu:info', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('24', '4', '新增', NULL, 'sys:menu:save,sys:menu:select', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('25', '4', '修改', NULL, 'sys:menu:update,sys:menu:select', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('26', '4', '删除', NULL, 'sys:menu:delete', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('27', '1', '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '6');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('29', '1', '系统日志', 'modules/sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '7');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('31', '1', '租户管理', 'modules/sys/dept.html', NULL, '1', 'fa fa-file-code-o', '2');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('32', '31', '查看', NULL, 'sys:dept:list,sys:dept:info', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('33', '31', '新增', NULL, 'sys:dept:save,sys:dept:select', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('34', '31', '修改', NULL, 'sys:dept:update,sys:dept:select', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('35', '31', '删除', NULL, 'sys:dept:delete', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('6', '1', '定时任务', 'modules/job/schedule.html', NULL, '1', 'fa fa-tasks', '5');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('7', '6', '查看', NULL, 'sys:schedule:list,sys:schedule:info', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('8', '6', '新增', NULL, 'sys:schedule:save', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('9', '6', '修改', NULL, 'sys:schedule:update', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('10', '6', '删除', NULL, 'sys:schedule:delete', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('11', '6', '暂停', NULL, 'sys:schedule:pause', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('12', '6', '恢复', NULL, 'sys:schedule:resume', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('13', '6', '立即执行', NULL, 'sys:schedule:run', '2', NULL, '0');
INSERT INTO "public"."gar_sys_menu" ("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES ('14', '6', '日志列表', NULL, 'sys:schedule:log', '2', NULL, '0');


-- ----------------------------
-- Table structure for gar_sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_role";
CREATE TABLE "public"."gar_sys_role" (
  "role_id" serial,
  "role_name" varchar(100) COLLATE "default",
  "remark" varchar(100) COLLATE "default",
  "dept_id" int8,
  "create_time" timestamp(6) DEFAULT now()
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "public"."gar_sys_role"."remark" IS '备注';
COMMENT ON COLUMN "public"."gar_sys_role"."dept_id" IS '部门ID';
COMMENT ON COLUMN "public"."gar_sys_role"."create_time" IS '创建时间';

ALTER TABLE "public"."gar_sys_role" ADD PRIMARY KEY ("role_id");

-- ----------------------------
-- Table structure for gar_sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_role_dept";
CREATE TABLE "public"."gar_sys_role_dept" (
  "id" serial,
  "role_id" int8,
  "dept_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_role_dept"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_sys_role_dept"."dept_id" IS '部门ID';

ALTER TABLE "public"."gar_sys_role_dept" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Table structure for gar_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_role_menu";
CREATE TABLE "public"."gar_sys_role_menu" (
  "id" serial,
  "role_id" int8,
  "menu_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_role_menu"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."gar_sys_role_menu"."menu_id" IS '菜单ID';

ALTER TABLE "public"."gar_sys_role_menu" ADD PRIMARY KEY ("id");


-- ----------------------------
-- Table structure for gar_sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_user";
CREATE TABLE "public"."gar_sys_user" (
  "user_id" serial,
  "username" varchar(50) COLLATE "default" NOT NULL,
  "password" varchar(100) COLLATE "default",
  "salt" varchar(20) COLLATE "default",
  "email" varchar(100) COLLATE "default",
  "mobile" varchar(100) COLLATE "default",
  "status" int4,
  "dept_id" int8,
  "create_time" timestamp(6) DEFAULT now()
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."gar_sys_user"."password" IS '密码';
COMMENT ON COLUMN "public"."gar_sys_user"."salt" IS '盐';
COMMENT ON COLUMN "public"."gar_sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."gar_sys_user"."mobile" IS '手机号';
COMMENT ON COLUMN "public"."gar_sys_user"."status" IS '状态  0：禁用   1：正常';
COMMENT ON COLUMN "public"."gar_sys_user"."dept_id" IS '部门ID';
COMMENT ON COLUMN "public"."gar_sys_user"."create_time" IS '创建时间';

ALTER TABLE "public"."gar_sys_user" ADD UNIQUE ("username");

ALTER TABLE "public"."gar_sys_user" ADD PRIMARY KEY ("user_id");

INSERT INTO "public"."gar_sys_user" ("user_id", "username", "password", "salt", "email", "mobile", "status", "dept_id", "create_time") VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', NULL, '2016-11-11 11:11:11');


-- ----------------------------
-- Table structure for gar_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_user_role";
CREATE TABLE "public"."gar_sys_user_role" (
  "id" serial,
  "user_id" int8,
  "role_id" int8
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_user_role"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."gar_sys_user_role"."role_id" IS '角色ID';

ALTER TABLE "public"."gar_sys_user_role" ADD PRIMARY KEY ("id");


-- ----------------------------
-- Table structure for gar_sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_sys_user_token";
CREATE TABLE "public"."gar_sys_user_token" (
  "user_id" int8 NOT NULL,
  "token" varchar(100) COLLATE "default" NOT NULL,
  "expire_time" timestamp(6),
  "update_time" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_sys_user_token"."token" IS 'token';
COMMENT ON COLUMN "public"."gar_sys_user_token"."expire_time" IS '过期时间';
COMMENT ON COLUMN "public"."gar_sys_user_token"."update_time" IS '更新时间';

ALTER TABLE "public"."gar_sys_user_token" ADD UNIQUE ("token");

ALTER TABLE "public"."gar_sys_user_token" ADD PRIMARY KEY ("user_id");

INSERT INTO "public"."gar_sys_user_token" ("user_id", "token", "expire_time", "update_time") VALUES ('1', '0a289458132125088d6babde201ae59e', '2017-08-08 07:40:28.626', '2017-08-07 19:40:28.626');



-- ----------------------------
-- Table structure for gar_tb_token
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_tb_token";
CREATE TABLE "public"."gar_tb_token" (
  "user_id" int8 NOT NULL,
  "token" varchar(100) COLLATE "default",
  "expire_time" timestamp(6),
  "update_time" timestamp(6)
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_tb_token"."token" IS 'token';
COMMENT ON COLUMN "public"."gar_tb_token"."expire_time" IS '过期时间';
COMMENT ON COLUMN "public"."gar_tb_token"."update_time" IS '更新时间';

ALTER TABLE "public"."gar_tb_token" ADD PRIMARY KEY ("user_id");

ALTER TABLE "public"."gar_tb_token" ADD UNIQUE ("token");


-- ----------------------------
-- Table structure for gar_tb_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."gar_tb_user";
CREATE TABLE "public"."gar_tb_user" (
  "user_id" serial,
  "username" varchar(50) COLLATE "default" NOT NULL,
  "mobile" varchar(20) COLLATE "default" NOT NULL,
  "password" varchar(64) COLLATE "default",
  "create_time" timestamp(6) DEFAULT now()
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."gar_tb_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."gar_tb_user"."mobile" IS '手机号';
COMMENT ON COLUMN "public"."gar_tb_user"."password" IS '密码';
COMMENT ON COLUMN "public"."gar_tb_user"."create_time" IS '创建时间';

ALTER TABLE "public"."gar_tb_user" ADD PRIMARY KEY ("user_id");

ALTER TABLE "public"."gar_tb_user" ADD UNIQUE ("username");

INSERT INTO "public"."gar_tb_user" ("user_id", "username", "mobile", "password", "create_time") VALUES ('1', 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');


-------    QUARTZ TABLES    -----------

drop table if exists qrtz_fired_triggers;
DROP TABLE if exists QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE if exists QRTZ_SCHEDULER_STATE;
DROP TABLE if exists QRTZ_LOCKS;
drop table if exists qrtz_simple_triggers;
drop table if exists qrtz_cron_triggers;
drop table if exists qrtz_simprop_triggers;
DROP TABLE if exists QRTZ_BLOB_TRIGGERS;
drop table if exists qrtz_triggers;
drop table if exists qrtz_job_details;
drop table if exists qrtz_calendars;

CREATE TABLE qrtz_job_details
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  JOB_NAME  VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  JOB_CLASS_NAME   VARCHAR(250) NOT NULL,
  IS_DURABLE BOOL NOT NULL,
  IS_NONCONCURRENT BOOL NOT NULL,
  IS_UPDATE_DATA BOOL NOT NULL,
  REQUESTS_RECOVERY BOOL NOT NULL,
  JOB_DATA BYTEA NULL,
  PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE qrtz_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  JOB_NAME  VARCHAR(200) NOT NULL,
  JOB_GROUP VARCHAR(200) NOT NULL,
  DESCRIPTION VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT NULL,
  PREV_FIRE_TIME BIGINT NULL,
  PRIORITY INTEGER NULL,
  TRIGGER_STATE VARCHAR(16) NOT NULL,
  TRIGGER_TYPE VARCHAR(8) NOT NULL,
  START_TIME BIGINT NOT NULL,
  END_TIME BIGINT NULL,
  CALENDAR_NAME VARCHAR(200) NULL,
  MISFIRE_INSTR SMALLINT NULL,
  JOB_DATA BYTEA NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE qrtz_simple_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  REPEAT_COUNT BIGINT NOT NULL,
  REPEAT_INTERVAL BIGINT NOT NULL,
  TIMES_TRIGGERED BIGINT NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_cron_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID VARCHAR(80),
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_simprop_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  STR_PROP_1 VARCHAR(512) NULL,
  STR_PROP_2 VARCHAR(512) NULL,
  STR_PROP_3 VARCHAR(512) NULL,
  INT_PROP_1 INT NULL,
  INT_PROP_2 INT NULL,
  LONG_PROP_1 BIGINT NULL,
  LONG_PROP_2 BIGINT NULL,
  DEC_PROP_1 NUMERIC(13,4) NULL,
  DEC_PROP_2 NUMERIC(13,4) NULL,
  BOOL_PROP_1 BOOL NULL,
  BOOL_PROP_2 BOOL NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_blob_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA BYTEA NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_calendars
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  CALENDAR_NAME  VARCHAR(200) NOT NULL,
  CALENDAR BYTEA NOT NULL,
  PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);


CREATE TABLE qrtz_paused_trigger_grps
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  TRIGGER_GROUP  VARCHAR(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_fired_triggers
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  ENTRY_ID VARCHAR(95) NOT NULL,
  TRIGGER_NAME VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  FIRED_TIME BIGINT NOT NULL,
  SCHED_TIME BIGINT NOT NULL,
  PRIORITY INTEGER NOT NULL,
  STATE VARCHAR(16) NOT NULL,
  JOB_NAME VARCHAR(200) NULL,
  JOB_GROUP VARCHAR(200) NULL,
  IS_NONCONCURRENT BOOL NULL,
  REQUESTS_RECOVERY BOOL NULL,
  PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);

CREATE TABLE qrtz_scheduler_state
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  INSTANCE_NAME VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT NOT NULL,
  CHECKIN_INTERVAL BIGINT NOT NULL,
  PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);

CREATE TABLE qrtz_locks
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME  VARCHAR(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);
