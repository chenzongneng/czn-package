-- ---------------------------------------------------------------------------------------------------------------------
-- Table gar_departments
-- ---------------------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS "public"."gar_departments";
CREATE TABLE "public"."gar_departments" (
  "id"          SERIAL,
  "name"        VARCHAR(50) COLLATE "default",
  "parent_id"   INT8,
  "tenant_id"   INT8,
  "order_num"   INT4,
  "delete_flag" BOOL DEFAULT FALSE
) WITH (OIDS = FALSE
);
ALTER TABLE "public"."gar_departments"
  ADD PRIMARY KEY ("id");
COMMENT ON COLUMN "public"."gar_departments"."name" IS '部门名称';
COMMENT ON COLUMN "public"."gar_departments"."parent_id" IS '上级部门ID';
COMMENT ON COLUMN "public"."gar_departments"."tenant_id" IS '所属租户ID';
COMMENT ON COLUMN "public"."gar_departments"."order_num" IS '排序';
COMMENT ON COLUMN "public"."gar_departments"."delete_flag" IS '是否删除[false:正常; true:已删除]';
-- Records
INSERT INTO "public"."gar_departments" (id, name, parent_id, tenant_id, order_num, delete_flag)
VALUES ('0', '无', NULL, NULL, '0', 'f');

-- ---------------------------------------------------------------------------------------------------------------------
-- Table gar_tenants
-- ---------------------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS "public"."gar_tenants";
CREATE TABLE "public"."gar_tenants" (
  "id"          SERIAL,
  "name"        VARCHAR(50) COLLATE "default",
  "delete_flag" BOOL DEFAULT FALSE
) WITH (OIDS = FALSE
);
ALTER TABLE "public"."gar_tenants"
  ADD PRIMARY KEY ("id");
COMMENT ON COLUMN "public"."gar_tenants"."name" IS '租户名称';
COMMENT ON COLUMN "public"."gar_tenants"."delete_flag" IS '是否删除[false:正常; true:已删除]';

