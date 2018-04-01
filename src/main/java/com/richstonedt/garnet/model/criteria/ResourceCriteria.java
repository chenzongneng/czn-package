package com.richstonedt.garnet.model.criteria;

import java.util.ArrayList;
import java.util.List;

public class ResourceCriteria {
    /**
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected String orderByClause;

    /**
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected boolean distinct;

    /**
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected List<Criteria> oredCriteria;

    /**
     * TABLE： gar_resources
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected Integer start;

    /**
     * TABLE： gar_resources
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected Integer end;

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public ResourceCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public Integer getStart() {
        return start;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setEnd(Integer end) {
        this.end = end;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public Integer getEnd() {
        return end;
    }

    /**
     * 此类为自动生成.
     * 对应的数据库表为： gar_resources
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andApplicationIdIsNull() {
            addCriterion("application_id is null");
            return (Criteria) this;
        }

        public Criteria andApplicationIdIsNotNull() {
            addCriterion("application_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplicationIdEqualTo(Long value) {
            addCriterion("application_id =", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdNotEqualTo(Long value) {
            addCriterion("application_id <>", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdGreaterThan(Long value) {
            addCriterion("application_id >", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdGreaterThanOrEqualTo(Long value) {
            addCriterion("application_id >=", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdLessThan(Long value) {
            addCriterion("application_id <", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdLessThanOrEqualTo(Long value) {
            addCriterion("application_id <=", value, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdIn(List<Long> values) {
            addCriterion("application_id in", values, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdNotIn(List<Long> values) {
            addCriterion("application_id not in", values, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdBetween(Long value1, Long value2) {
            addCriterion("application_id between", value1, value2, "applicationId");
            return (Criteria) this;
        }

        public Criteria andApplicationIdNotBetween(Long value1, Long value2) {
            addCriterion("application_id not between", value1, value2, "applicationId");
            return (Criteria) this;
        }

        public Criteria andPathIsNull() {
            addCriterion("path is null");
            return (Criteria) this;
        }

        public Criteria andPathIsNotNull() {
            addCriterion("path is not null");
            return (Criteria) this;
        }

        public Criteria andPathEqualTo(String value) {
            addCriterion("path =", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotEqualTo(String value) {
            addCriterion("path <>", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThan(String value) {
            addCriterion("path >", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThanOrEqualTo(String value) {
            addCriterion("path >=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThan(String value) {
            addCriterion("path <", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThanOrEqualTo(String value) {
            addCriterion("path <=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLike(String value) {
            addCriterion("path like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotLike(String value) {
            addCriterion("path not like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathIn(List<String> values) {
            addCriterion("path in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotIn(List<String> values) {
            addCriterion("path not in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathBetween(String value1, String value2) {
            addCriterion("path between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotBetween(String value1, String value2) {
            addCriterion("path not between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andActionsIsNull() {
            addCriterion("actions is null");
            return (Criteria) this;
        }

        public Criteria andActionsIsNotNull() {
            addCriterion("actions is not null");
            return (Criteria) this;
        }

        public Criteria andActionsEqualTo(String value) {
            addCriterion("actions =", value, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsNotEqualTo(String value) {
            addCriterion("actions <>", value, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsGreaterThan(String value) {
            addCriterion("actions >", value, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsGreaterThanOrEqualTo(String value) {
            addCriterion("actions >=", value, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsLessThan(String value) {
            addCriterion("actions <", value, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsLessThanOrEqualTo(String value) {
            addCriterion("actions <=", value, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsLike(String value) {
            addCriterion("actions like", value, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsNotLike(String value) {
            addCriterion("actions not like", value, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsIn(List<String> values) {
            addCriterion("actions in", values, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsNotIn(List<String> values) {
            addCriterion("actions not in", values, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsBetween(String value1, String value2) {
            addCriterion("actions between", value1, value2, "actions");
            return (Criteria) this;
        }

        public Criteria andActionsNotBetween(String value1, String value2) {
            addCriterion("actions not between", value1, value2, "actions");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("created_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("created_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Long value) {
            addCriterion("created_time =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Long value) {
            addCriterion("created_time <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Long value) {
            addCriterion("created_time >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("created_time >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Long value) {
            addCriterion("created_time <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Long value) {
            addCriterion("created_time <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Long> values) {
            addCriterion("created_time in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Long> values) {
            addCriterion("created_time not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Long value1, Long value2) {
            addCriterion("created_time between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Long value1, Long value2) {
            addCriterion("created_time not between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeIsNull() {
            addCriterion("modified_time is null");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeIsNotNull() {
            addCriterion("modified_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeEqualTo(Long value) {
            addCriterion("modified_time =", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotEqualTo(Long value) {
            addCriterion("modified_time <>", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeGreaterThan(Long value) {
            addCriterion("modified_time >", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("modified_time >=", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeLessThan(Long value) {
            addCriterion("modified_time <", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeLessThanOrEqualTo(Long value) {
            addCriterion("modified_time <=", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeIn(List<Long> values) {
            addCriterion("modified_time in", values, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotIn(List<Long> values) {
            addCriterion("modified_time not in", values, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeBetween(Long value1, Long value2) {
            addCriterion("modified_time between", value1, value2, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotBetween(Long value1, Long value2) {
            addCriterion("modified_time not between", value1, value2, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNull() {
            addCriterion("tenant_id is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("tenant_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(Long value) {
            addCriterion("tenant_id =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(Long value) {
            addCriterion("tenant_id <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(Long value) {
            addCriterion("tenant_id >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tenant_id >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(Long value) {
            addCriterion("tenant_id <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(Long value) {
            addCriterion("tenant_id <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<Long> values) {
            addCriterion("tenant_id in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<Long> values) {
            addCriterion("tenant_id not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(Long value1, Long value2) {
            addCriterion("tenant_id between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(Long value1, Long value2) {
            addCriterion("tenant_id not between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andVarchar00IsNull() {
            addCriterion("varchar_00 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar00IsNotNull() {
            addCriterion("varchar_00 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar00EqualTo(String value) {
            addCriterion("varchar_00 =", value, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00NotEqualTo(String value) {
            addCriterion("varchar_00 <>", value, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00GreaterThan(String value) {
            addCriterion("varchar_00 >", value, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_00 >=", value, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00LessThan(String value) {
            addCriterion("varchar_00 <", value, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00LessThanOrEqualTo(String value) {
            addCriterion("varchar_00 <=", value, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00Like(String value) {
            addCriterion("varchar_00 like", value, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00NotLike(String value) {
            addCriterion("varchar_00 not like", value, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00In(List<String> values) {
            addCriterion("varchar_00 in", values, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00NotIn(List<String> values) {
            addCriterion("varchar_00 not in", values, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00Between(String value1, String value2) {
            addCriterion("varchar_00 between", value1, value2, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar00NotBetween(String value1, String value2) {
            addCriterion("varchar_00 not between", value1, value2, "varchar00");
            return (Criteria) this;
        }

        public Criteria andVarchar01IsNull() {
            addCriterion("varchar_01 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar01IsNotNull() {
            addCriterion("varchar_01 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar01EqualTo(String value) {
            addCriterion("varchar_01 =", value, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01NotEqualTo(String value) {
            addCriterion("varchar_01 <>", value, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01GreaterThan(String value) {
            addCriterion("varchar_01 >", value, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_01 >=", value, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01LessThan(String value) {
            addCriterion("varchar_01 <", value, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01LessThanOrEqualTo(String value) {
            addCriterion("varchar_01 <=", value, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01Like(String value) {
            addCriterion("varchar_01 like", value, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01NotLike(String value) {
            addCriterion("varchar_01 not like", value, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01In(List<String> values) {
            addCriterion("varchar_01 in", values, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01NotIn(List<String> values) {
            addCriterion("varchar_01 not in", values, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01Between(String value1, String value2) {
            addCriterion("varchar_01 between", value1, value2, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar01NotBetween(String value1, String value2) {
            addCriterion("varchar_01 not between", value1, value2, "varchar01");
            return (Criteria) this;
        }

        public Criteria andVarchar02IsNull() {
            addCriterion("varchar_02 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar02IsNotNull() {
            addCriterion("varchar_02 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar02EqualTo(String value) {
            addCriterion("varchar_02 =", value, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02NotEqualTo(String value) {
            addCriterion("varchar_02 <>", value, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02GreaterThan(String value) {
            addCriterion("varchar_02 >", value, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_02 >=", value, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02LessThan(String value) {
            addCriterion("varchar_02 <", value, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02LessThanOrEqualTo(String value) {
            addCriterion("varchar_02 <=", value, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02Like(String value) {
            addCriterion("varchar_02 like", value, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02NotLike(String value) {
            addCriterion("varchar_02 not like", value, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02In(List<String> values) {
            addCriterion("varchar_02 in", values, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02NotIn(List<String> values) {
            addCriterion("varchar_02 not in", values, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02Between(String value1, String value2) {
            addCriterion("varchar_02 between", value1, value2, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar02NotBetween(String value1, String value2) {
            addCriterion("varchar_02 not between", value1, value2, "varchar02");
            return (Criteria) this;
        }

        public Criteria andVarchar03IsNull() {
            addCriterion("varchar_03 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar03IsNotNull() {
            addCriterion("varchar_03 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar03EqualTo(String value) {
            addCriterion("varchar_03 =", value, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03NotEqualTo(String value) {
            addCriterion("varchar_03 <>", value, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03GreaterThan(String value) {
            addCriterion("varchar_03 >", value, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_03 >=", value, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03LessThan(String value) {
            addCriterion("varchar_03 <", value, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03LessThanOrEqualTo(String value) {
            addCriterion("varchar_03 <=", value, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03Like(String value) {
            addCriterion("varchar_03 like", value, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03NotLike(String value) {
            addCriterion("varchar_03 not like", value, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03In(List<String> values) {
            addCriterion("varchar_03 in", values, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03NotIn(List<String> values) {
            addCriterion("varchar_03 not in", values, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03Between(String value1, String value2) {
            addCriterion("varchar_03 between", value1, value2, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar03NotBetween(String value1, String value2) {
            addCriterion("varchar_03 not between", value1, value2, "varchar03");
            return (Criteria) this;
        }

        public Criteria andVarchar04IsNull() {
            addCriterion("varchar_04 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar04IsNotNull() {
            addCriterion("varchar_04 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar04EqualTo(String value) {
            addCriterion("varchar_04 =", value, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04NotEqualTo(String value) {
            addCriterion("varchar_04 <>", value, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04GreaterThan(String value) {
            addCriterion("varchar_04 >", value, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_04 >=", value, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04LessThan(String value) {
            addCriterion("varchar_04 <", value, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04LessThanOrEqualTo(String value) {
            addCriterion("varchar_04 <=", value, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04Like(String value) {
            addCriterion("varchar_04 like", value, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04NotLike(String value) {
            addCriterion("varchar_04 not like", value, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04In(List<String> values) {
            addCriterion("varchar_04 in", values, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04NotIn(List<String> values) {
            addCriterion("varchar_04 not in", values, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04Between(String value1, String value2) {
            addCriterion("varchar_04 between", value1, value2, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar04NotBetween(String value1, String value2) {
            addCriterion("varchar_04 not between", value1, value2, "varchar04");
            return (Criteria) this;
        }

        public Criteria andVarchar05IsNull() {
            addCriterion("varchar_05 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar05IsNotNull() {
            addCriterion("varchar_05 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar05EqualTo(String value) {
            addCriterion("varchar_05 =", value, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05NotEqualTo(String value) {
            addCriterion("varchar_05 <>", value, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05GreaterThan(String value) {
            addCriterion("varchar_05 >", value, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_05 >=", value, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05LessThan(String value) {
            addCriterion("varchar_05 <", value, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05LessThanOrEqualTo(String value) {
            addCriterion("varchar_05 <=", value, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05Like(String value) {
            addCriterion("varchar_05 like", value, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05NotLike(String value) {
            addCriterion("varchar_05 not like", value, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05In(List<String> values) {
            addCriterion("varchar_05 in", values, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05NotIn(List<String> values) {
            addCriterion("varchar_05 not in", values, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05Between(String value1, String value2) {
            addCriterion("varchar_05 between", value1, value2, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar05NotBetween(String value1, String value2) {
            addCriterion("varchar_05 not between", value1, value2, "varchar05");
            return (Criteria) this;
        }

        public Criteria andVarchar06IsNull() {
            addCriterion("varchar_06 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar06IsNotNull() {
            addCriterion("varchar_06 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar06EqualTo(String value) {
            addCriterion("varchar_06 =", value, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06NotEqualTo(String value) {
            addCriterion("varchar_06 <>", value, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06GreaterThan(String value) {
            addCriterion("varchar_06 >", value, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_06 >=", value, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06LessThan(String value) {
            addCriterion("varchar_06 <", value, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06LessThanOrEqualTo(String value) {
            addCriterion("varchar_06 <=", value, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06Like(String value) {
            addCriterion("varchar_06 like", value, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06NotLike(String value) {
            addCriterion("varchar_06 not like", value, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06In(List<String> values) {
            addCriterion("varchar_06 in", values, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06NotIn(List<String> values) {
            addCriterion("varchar_06 not in", values, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06Between(String value1, String value2) {
            addCriterion("varchar_06 between", value1, value2, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar06NotBetween(String value1, String value2) {
            addCriterion("varchar_06 not between", value1, value2, "varchar06");
            return (Criteria) this;
        }

        public Criteria andVarchar07IsNull() {
            addCriterion("varchar_07 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar07IsNotNull() {
            addCriterion("varchar_07 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar07EqualTo(String value) {
            addCriterion("varchar_07 =", value, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07NotEqualTo(String value) {
            addCriterion("varchar_07 <>", value, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07GreaterThan(String value) {
            addCriterion("varchar_07 >", value, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_07 >=", value, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07LessThan(String value) {
            addCriterion("varchar_07 <", value, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07LessThanOrEqualTo(String value) {
            addCriterion("varchar_07 <=", value, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07Like(String value) {
            addCriterion("varchar_07 like", value, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07NotLike(String value) {
            addCriterion("varchar_07 not like", value, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07In(List<String> values) {
            addCriterion("varchar_07 in", values, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07NotIn(List<String> values) {
            addCriterion("varchar_07 not in", values, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07Between(String value1, String value2) {
            addCriterion("varchar_07 between", value1, value2, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar07NotBetween(String value1, String value2) {
            addCriterion("varchar_07 not between", value1, value2, "varchar07");
            return (Criteria) this;
        }

        public Criteria andVarchar08IsNull() {
            addCriterion("varchar_08 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar08IsNotNull() {
            addCriterion("varchar_08 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar08EqualTo(String value) {
            addCriterion("varchar_08 =", value, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08NotEqualTo(String value) {
            addCriterion("varchar_08 <>", value, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08GreaterThan(String value) {
            addCriterion("varchar_08 >", value, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_08 >=", value, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08LessThan(String value) {
            addCriterion("varchar_08 <", value, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08LessThanOrEqualTo(String value) {
            addCriterion("varchar_08 <=", value, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08Like(String value) {
            addCriterion("varchar_08 like", value, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08NotLike(String value) {
            addCriterion("varchar_08 not like", value, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08In(List<String> values) {
            addCriterion("varchar_08 in", values, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08NotIn(List<String> values) {
            addCriterion("varchar_08 not in", values, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08Between(String value1, String value2) {
            addCriterion("varchar_08 between", value1, value2, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar08NotBetween(String value1, String value2) {
            addCriterion("varchar_08 not between", value1, value2, "varchar08");
            return (Criteria) this;
        }

        public Criteria andVarchar09IsNull() {
            addCriterion("varchar_09 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar09IsNotNull() {
            addCriterion("varchar_09 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar09EqualTo(String value) {
            addCriterion("varchar_09 =", value, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09NotEqualTo(String value) {
            addCriterion("varchar_09 <>", value, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09GreaterThan(String value) {
            addCriterion("varchar_09 >", value, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_09 >=", value, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09LessThan(String value) {
            addCriterion("varchar_09 <", value, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09LessThanOrEqualTo(String value) {
            addCriterion("varchar_09 <=", value, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09Like(String value) {
            addCriterion("varchar_09 like", value, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09NotLike(String value) {
            addCriterion("varchar_09 not like", value, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09In(List<String> values) {
            addCriterion("varchar_09 in", values, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09NotIn(List<String> values) {
            addCriterion("varchar_09 not in", values, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09Between(String value1, String value2) {
            addCriterion("varchar_09 between", value1, value2, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar09NotBetween(String value1, String value2) {
            addCriterion("varchar_09 not between", value1, value2, "varchar09");
            return (Criteria) this;
        }

        public Criteria andVarchar10IsNull() {
            addCriterion("varchar_10 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar10IsNotNull() {
            addCriterion("varchar_10 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar10EqualTo(String value) {
            addCriterion("varchar_10 =", value, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10NotEqualTo(String value) {
            addCriterion("varchar_10 <>", value, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10GreaterThan(String value) {
            addCriterion("varchar_10 >", value, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_10 >=", value, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10LessThan(String value) {
            addCriterion("varchar_10 <", value, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10LessThanOrEqualTo(String value) {
            addCriterion("varchar_10 <=", value, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10Like(String value) {
            addCriterion("varchar_10 like", value, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10NotLike(String value) {
            addCriterion("varchar_10 not like", value, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10In(List<String> values) {
            addCriterion("varchar_10 in", values, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10NotIn(List<String> values) {
            addCriterion("varchar_10 not in", values, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10Between(String value1, String value2) {
            addCriterion("varchar_10 between", value1, value2, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar10NotBetween(String value1, String value2) {
            addCriterion("varchar_10 not between", value1, value2, "varchar10");
            return (Criteria) this;
        }

        public Criteria andVarchar11IsNull() {
            addCriterion("varchar_11 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar11IsNotNull() {
            addCriterion("varchar_11 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar11EqualTo(String value) {
            addCriterion("varchar_11 =", value, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11NotEqualTo(String value) {
            addCriterion("varchar_11 <>", value, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11GreaterThan(String value) {
            addCriterion("varchar_11 >", value, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_11 >=", value, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11LessThan(String value) {
            addCriterion("varchar_11 <", value, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11LessThanOrEqualTo(String value) {
            addCriterion("varchar_11 <=", value, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11Like(String value) {
            addCriterion("varchar_11 like", value, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11NotLike(String value) {
            addCriterion("varchar_11 not like", value, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11In(List<String> values) {
            addCriterion("varchar_11 in", values, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11NotIn(List<String> values) {
            addCriterion("varchar_11 not in", values, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11Between(String value1, String value2) {
            addCriterion("varchar_11 between", value1, value2, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar11NotBetween(String value1, String value2) {
            addCriterion("varchar_11 not between", value1, value2, "varchar11");
            return (Criteria) this;
        }

        public Criteria andVarchar12IsNull() {
            addCriterion("varchar_12 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar12IsNotNull() {
            addCriterion("varchar_12 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar12EqualTo(String value) {
            addCriterion("varchar_12 =", value, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12NotEqualTo(String value) {
            addCriterion("varchar_12 <>", value, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12GreaterThan(String value) {
            addCriterion("varchar_12 >", value, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_12 >=", value, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12LessThan(String value) {
            addCriterion("varchar_12 <", value, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12LessThanOrEqualTo(String value) {
            addCriterion("varchar_12 <=", value, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12Like(String value) {
            addCriterion("varchar_12 like", value, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12NotLike(String value) {
            addCriterion("varchar_12 not like", value, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12In(List<String> values) {
            addCriterion("varchar_12 in", values, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12NotIn(List<String> values) {
            addCriterion("varchar_12 not in", values, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12Between(String value1, String value2) {
            addCriterion("varchar_12 between", value1, value2, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar12NotBetween(String value1, String value2) {
            addCriterion("varchar_12 not between", value1, value2, "varchar12");
            return (Criteria) this;
        }

        public Criteria andVarchar13IsNull() {
            addCriterion("varchar_13 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar13IsNotNull() {
            addCriterion("varchar_13 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar13EqualTo(String value) {
            addCriterion("varchar_13 =", value, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13NotEqualTo(String value) {
            addCriterion("varchar_13 <>", value, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13GreaterThan(String value) {
            addCriterion("varchar_13 >", value, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_13 >=", value, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13LessThan(String value) {
            addCriterion("varchar_13 <", value, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13LessThanOrEqualTo(String value) {
            addCriterion("varchar_13 <=", value, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13Like(String value) {
            addCriterion("varchar_13 like", value, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13NotLike(String value) {
            addCriterion("varchar_13 not like", value, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13In(List<String> values) {
            addCriterion("varchar_13 in", values, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13NotIn(List<String> values) {
            addCriterion("varchar_13 not in", values, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13Between(String value1, String value2) {
            addCriterion("varchar_13 between", value1, value2, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar13NotBetween(String value1, String value2) {
            addCriterion("varchar_13 not between", value1, value2, "varchar13");
            return (Criteria) this;
        }

        public Criteria andVarchar14IsNull() {
            addCriterion("varchar_14 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar14IsNotNull() {
            addCriterion("varchar_14 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar14EqualTo(String value) {
            addCriterion("varchar_14 =", value, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14NotEqualTo(String value) {
            addCriterion("varchar_14 <>", value, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14GreaterThan(String value) {
            addCriterion("varchar_14 >", value, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_14 >=", value, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14LessThan(String value) {
            addCriterion("varchar_14 <", value, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14LessThanOrEqualTo(String value) {
            addCriterion("varchar_14 <=", value, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14Like(String value) {
            addCriterion("varchar_14 like", value, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14NotLike(String value) {
            addCriterion("varchar_14 not like", value, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14In(List<String> values) {
            addCriterion("varchar_14 in", values, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14NotIn(List<String> values) {
            addCriterion("varchar_14 not in", values, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14Between(String value1, String value2) {
            addCriterion("varchar_14 between", value1, value2, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar14NotBetween(String value1, String value2) {
            addCriterion("varchar_14 not between", value1, value2, "varchar14");
            return (Criteria) this;
        }

        public Criteria andVarchar15IsNull() {
            addCriterion("varchar_15 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar15IsNotNull() {
            addCriterion("varchar_15 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar15EqualTo(String value) {
            addCriterion("varchar_15 =", value, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15NotEqualTo(String value) {
            addCriterion("varchar_15 <>", value, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15GreaterThan(String value) {
            addCriterion("varchar_15 >", value, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_15 >=", value, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15LessThan(String value) {
            addCriterion("varchar_15 <", value, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15LessThanOrEqualTo(String value) {
            addCriterion("varchar_15 <=", value, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15Like(String value) {
            addCriterion("varchar_15 like", value, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15NotLike(String value) {
            addCriterion("varchar_15 not like", value, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15In(List<String> values) {
            addCriterion("varchar_15 in", values, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15NotIn(List<String> values) {
            addCriterion("varchar_15 not in", values, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15Between(String value1, String value2) {
            addCriterion("varchar_15 between", value1, value2, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar15NotBetween(String value1, String value2) {
            addCriterion("varchar_15 not between", value1, value2, "varchar15");
            return (Criteria) this;
        }

        public Criteria andVarchar16IsNull() {
            addCriterion("varchar_16 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar16IsNotNull() {
            addCriterion("varchar_16 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar16EqualTo(String value) {
            addCriterion("varchar_16 =", value, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16NotEqualTo(String value) {
            addCriterion("varchar_16 <>", value, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16GreaterThan(String value) {
            addCriterion("varchar_16 >", value, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_16 >=", value, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16LessThan(String value) {
            addCriterion("varchar_16 <", value, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16LessThanOrEqualTo(String value) {
            addCriterion("varchar_16 <=", value, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16Like(String value) {
            addCriterion("varchar_16 like", value, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16NotLike(String value) {
            addCriterion("varchar_16 not like", value, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16In(List<String> values) {
            addCriterion("varchar_16 in", values, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16NotIn(List<String> values) {
            addCriterion("varchar_16 not in", values, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16Between(String value1, String value2) {
            addCriterion("varchar_16 between", value1, value2, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar16NotBetween(String value1, String value2) {
            addCriterion("varchar_16 not between", value1, value2, "varchar16");
            return (Criteria) this;
        }

        public Criteria andVarchar17IsNull() {
            addCriterion("varchar_17 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar17IsNotNull() {
            addCriterion("varchar_17 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar17EqualTo(String value) {
            addCriterion("varchar_17 =", value, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17NotEqualTo(String value) {
            addCriterion("varchar_17 <>", value, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17GreaterThan(String value) {
            addCriterion("varchar_17 >", value, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_17 >=", value, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17LessThan(String value) {
            addCriterion("varchar_17 <", value, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17LessThanOrEqualTo(String value) {
            addCriterion("varchar_17 <=", value, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17Like(String value) {
            addCriterion("varchar_17 like", value, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17NotLike(String value) {
            addCriterion("varchar_17 not like", value, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17In(List<String> values) {
            addCriterion("varchar_17 in", values, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17NotIn(List<String> values) {
            addCriterion("varchar_17 not in", values, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17Between(String value1, String value2) {
            addCriterion("varchar_17 between", value1, value2, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar17NotBetween(String value1, String value2) {
            addCriterion("varchar_17 not between", value1, value2, "varchar17");
            return (Criteria) this;
        }

        public Criteria andVarchar18IsNull() {
            addCriterion("varchar_18 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar18IsNotNull() {
            addCriterion("varchar_18 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar18EqualTo(String value) {
            addCriterion("varchar_18 =", value, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18NotEqualTo(String value) {
            addCriterion("varchar_18 <>", value, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18GreaterThan(String value) {
            addCriterion("varchar_18 >", value, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_18 >=", value, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18LessThan(String value) {
            addCriterion("varchar_18 <", value, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18LessThanOrEqualTo(String value) {
            addCriterion("varchar_18 <=", value, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18Like(String value) {
            addCriterion("varchar_18 like", value, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18NotLike(String value) {
            addCriterion("varchar_18 not like", value, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18In(List<String> values) {
            addCriterion("varchar_18 in", values, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18NotIn(List<String> values) {
            addCriterion("varchar_18 not in", values, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18Between(String value1, String value2) {
            addCriterion("varchar_18 between", value1, value2, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar18NotBetween(String value1, String value2) {
            addCriterion("varchar_18 not between", value1, value2, "varchar18");
            return (Criteria) this;
        }

        public Criteria andVarchar19IsNull() {
            addCriterion("varchar_19 is null");
            return (Criteria) this;
        }

        public Criteria andVarchar19IsNotNull() {
            addCriterion("varchar_19 is not null");
            return (Criteria) this;
        }

        public Criteria andVarchar19EqualTo(String value) {
            addCriterion("varchar_19 =", value, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19NotEqualTo(String value) {
            addCriterion("varchar_19 <>", value, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19GreaterThan(String value) {
            addCriterion("varchar_19 >", value, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19GreaterThanOrEqualTo(String value) {
            addCriterion("varchar_19 >=", value, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19LessThan(String value) {
            addCriterion("varchar_19 <", value, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19LessThanOrEqualTo(String value) {
            addCriterion("varchar_19 <=", value, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19Like(String value) {
            addCriterion("varchar_19 like", value, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19NotLike(String value) {
            addCriterion("varchar_19 not like", value, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19In(List<String> values) {
            addCriterion("varchar_19 in", values, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19NotIn(List<String> values) {
            addCriterion("varchar_19 not in", values, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19Between(String value1, String value2) {
            addCriterion("varchar_19 between", value1, value2, "varchar19");
            return (Criteria) this;
        }

        public Criteria andVarchar19NotBetween(String value1, String value2) {
            addCriterion("varchar_19 not between", value1, value2, "varchar19");
            return (Criteria) this;
        }

        public Criteria andInt01IsNull() {
            addCriterion("int_01 is null");
            return (Criteria) this;
        }

        public Criteria andInt01IsNotNull() {
            addCriterion("int_01 is not null");
            return (Criteria) this;
        }

        public Criteria andInt01EqualTo(Integer value) {
            addCriterion("int_01 =", value, "int01");
            return (Criteria) this;
        }

        public Criteria andInt01NotEqualTo(Integer value) {
            addCriterion("int_01 <>", value, "int01");
            return (Criteria) this;
        }

        public Criteria andInt01GreaterThan(Integer value) {
            addCriterion("int_01 >", value, "int01");
            return (Criteria) this;
        }

        public Criteria andInt01GreaterThanOrEqualTo(Integer value) {
            addCriterion("int_01 >=", value, "int01");
            return (Criteria) this;
        }

        public Criteria andInt01LessThan(Integer value) {
            addCriterion("int_01 <", value, "int01");
            return (Criteria) this;
        }

        public Criteria andInt01LessThanOrEqualTo(Integer value) {
            addCriterion("int_01 <=", value, "int01");
            return (Criteria) this;
        }

        public Criteria andInt01In(List<Integer> values) {
            addCriterion("int_01 in", values, "int01");
            return (Criteria) this;
        }

        public Criteria andInt01NotIn(List<Integer> values) {
            addCriterion("int_01 not in", values, "int01");
            return (Criteria) this;
        }

        public Criteria andInt01Between(Integer value1, Integer value2) {
            addCriterion("int_01 between", value1, value2, "int01");
            return (Criteria) this;
        }

        public Criteria andInt01NotBetween(Integer value1, Integer value2) {
            addCriterion("int_01 not between", value1, value2, "int01");
            return (Criteria) this;
        }

        public Criteria andInt02IsNull() {
            addCriterion("int_02 is null");
            return (Criteria) this;
        }

        public Criteria andInt02IsNotNull() {
            addCriterion("int_02 is not null");
            return (Criteria) this;
        }

        public Criteria andInt02EqualTo(Integer value) {
            addCriterion("int_02 =", value, "int02");
            return (Criteria) this;
        }

        public Criteria andInt02NotEqualTo(Integer value) {
            addCriterion("int_02 <>", value, "int02");
            return (Criteria) this;
        }

        public Criteria andInt02GreaterThan(Integer value) {
            addCriterion("int_02 >", value, "int02");
            return (Criteria) this;
        }

        public Criteria andInt02GreaterThanOrEqualTo(Integer value) {
            addCriterion("int_02 >=", value, "int02");
            return (Criteria) this;
        }

        public Criteria andInt02LessThan(Integer value) {
            addCriterion("int_02 <", value, "int02");
            return (Criteria) this;
        }

        public Criteria andInt02LessThanOrEqualTo(Integer value) {
            addCriterion("int_02 <=", value, "int02");
            return (Criteria) this;
        }

        public Criteria andInt02In(List<Integer> values) {
            addCriterion("int_02 in", values, "int02");
            return (Criteria) this;
        }

        public Criteria andInt02NotIn(List<Integer> values) {
            addCriterion("int_02 not in", values, "int02");
            return (Criteria) this;
        }

        public Criteria andInt02Between(Integer value1, Integer value2) {
            addCriterion("int_02 between", value1, value2, "int02");
            return (Criteria) this;
        }

        public Criteria andInt02NotBetween(Integer value1, Integer value2) {
            addCriterion("int_02 not between", value1, value2, "int02");
            return (Criteria) this;
        }

        public Criteria andInt03IsNull() {
            addCriterion("int_03 is null");
            return (Criteria) this;
        }

        public Criteria andInt03IsNotNull() {
            addCriterion("int_03 is not null");
            return (Criteria) this;
        }

        public Criteria andInt03EqualTo(Integer value) {
            addCriterion("int_03 =", value, "int03");
            return (Criteria) this;
        }

        public Criteria andInt03NotEqualTo(Integer value) {
            addCriterion("int_03 <>", value, "int03");
            return (Criteria) this;
        }

        public Criteria andInt03GreaterThan(Integer value) {
            addCriterion("int_03 >", value, "int03");
            return (Criteria) this;
        }

        public Criteria andInt03GreaterThanOrEqualTo(Integer value) {
            addCriterion("int_03 >=", value, "int03");
            return (Criteria) this;
        }

        public Criteria andInt03LessThan(Integer value) {
            addCriterion("int_03 <", value, "int03");
            return (Criteria) this;
        }

        public Criteria andInt03LessThanOrEqualTo(Integer value) {
            addCriterion("int_03 <=", value, "int03");
            return (Criteria) this;
        }

        public Criteria andInt03In(List<Integer> values) {
            addCriterion("int_03 in", values, "int03");
            return (Criteria) this;
        }

        public Criteria andInt03NotIn(List<Integer> values) {
            addCriterion("int_03 not in", values, "int03");
            return (Criteria) this;
        }

        public Criteria andInt03Between(Integer value1, Integer value2) {
            addCriterion("int_03 between", value1, value2, "int03");
            return (Criteria) this;
        }

        public Criteria andInt03NotBetween(Integer value1, Integer value2) {
            addCriterion("int_03 not between", value1, value2, "int03");
            return (Criteria) this;
        }

        public Criteria andInt04IsNull() {
            addCriterion("int_04 is null");
            return (Criteria) this;
        }

        public Criteria andInt04IsNotNull() {
            addCriterion("int_04 is not null");
            return (Criteria) this;
        }

        public Criteria andInt04EqualTo(Integer value) {
            addCriterion("int_04 =", value, "int04");
            return (Criteria) this;
        }

        public Criteria andInt04NotEqualTo(Integer value) {
            addCriterion("int_04 <>", value, "int04");
            return (Criteria) this;
        }

        public Criteria andInt04GreaterThan(Integer value) {
            addCriterion("int_04 >", value, "int04");
            return (Criteria) this;
        }

        public Criteria andInt04GreaterThanOrEqualTo(Integer value) {
            addCriterion("int_04 >=", value, "int04");
            return (Criteria) this;
        }

        public Criteria andInt04LessThan(Integer value) {
            addCriterion("int_04 <", value, "int04");
            return (Criteria) this;
        }

        public Criteria andInt04LessThanOrEqualTo(Integer value) {
            addCriterion("int_04 <=", value, "int04");
            return (Criteria) this;
        }

        public Criteria andInt04In(List<Integer> values) {
            addCriterion("int_04 in", values, "int04");
            return (Criteria) this;
        }

        public Criteria andInt04NotIn(List<Integer> values) {
            addCriterion("int_04 not in", values, "int04");
            return (Criteria) this;
        }

        public Criteria andInt04Between(Integer value1, Integer value2) {
            addCriterion("int_04 between", value1, value2, "int04");
            return (Criteria) this;
        }

        public Criteria andInt04NotBetween(Integer value1, Integer value2) {
            addCriterion("int_04 not between", value1, value2, "int04");
            return (Criteria) this;
        }

        public Criteria andInt05IsNull() {
            addCriterion("int_05 is null");
            return (Criteria) this;
        }

        public Criteria andInt05IsNotNull() {
            addCriterion("int_05 is not null");
            return (Criteria) this;
        }

        public Criteria andInt05EqualTo(Integer value) {
            addCriterion("int_05 =", value, "int05");
            return (Criteria) this;
        }

        public Criteria andInt05NotEqualTo(Integer value) {
            addCriterion("int_05 <>", value, "int05");
            return (Criteria) this;
        }

        public Criteria andInt05GreaterThan(Integer value) {
            addCriterion("int_05 >", value, "int05");
            return (Criteria) this;
        }

        public Criteria andInt05GreaterThanOrEqualTo(Integer value) {
            addCriterion("int_05 >=", value, "int05");
            return (Criteria) this;
        }

        public Criteria andInt05LessThan(Integer value) {
            addCriterion("int_05 <", value, "int05");
            return (Criteria) this;
        }

        public Criteria andInt05LessThanOrEqualTo(Integer value) {
            addCriterion("int_05 <=", value, "int05");
            return (Criteria) this;
        }

        public Criteria andInt05In(List<Integer> values) {
            addCriterion("int_05 in", values, "int05");
            return (Criteria) this;
        }

        public Criteria andInt05NotIn(List<Integer> values) {
            addCriterion("int_05 not in", values, "int05");
            return (Criteria) this;
        }

        public Criteria andInt05Between(Integer value1, Integer value2) {
            addCriterion("int_05 between", value1, value2, "int05");
            return (Criteria) this;
        }

        public Criteria andInt05NotBetween(Integer value1, Integer value2) {
            addCriterion("int_05 not between", value1, value2, "int05");
            return (Criteria) this;
        }

        public Criteria andBoolean01IsNull() {
            addCriterion("boolean_01 is null");
            return (Criteria) this;
        }

        public Criteria andBoolean01IsNotNull() {
            addCriterion("boolean_01 is not null");
            return (Criteria) this;
        }

        public Criteria andBoolean01EqualTo(Integer value) {
            addCriterion("boolean_01 =", value, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean01NotEqualTo(Integer value) {
            addCriterion("boolean_01 <>", value, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean01GreaterThan(Integer value) {
            addCriterion("boolean_01 >", value, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean01GreaterThanOrEqualTo(Integer value) {
            addCriterion("boolean_01 >=", value, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean01LessThan(Integer value) {
            addCriterion("boolean_01 <", value, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean01LessThanOrEqualTo(Integer value) {
            addCriterion("boolean_01 <=", value, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean01In(List<Integer> values) {
            addCriterion("boolean_01 in", values, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean01NotIn(List<Integer> values) {
            addCriterion("boolean_01 not in", values, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean01Between(Integer value1, Integer value2) {
            addCriterion("boolean_01 between", value1, value2, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean01NotBetween(Integer value1, Integer value2) {
            addCriterion("boolean_01 not between", value1, value2, "boolean01");
            return (Criteria) this;
        }

        public Criteria andBoolean02IsNull() {
            addCriterion("boolean_02 is null");
            return (Criteria) this;
        }

        public Criteria andBoolean02IsNotNull() {
            addCriterion("boolean_02 is not null");
            return (Criteria) this;
        }

        public Criteria andBoolean02EqualTo(Integer value) {
            addCriterion("boolean_02 =", value, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean02NotEqualTo(Integer value) {
            addCriterion("boolean_02 <>", value, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean02GreaterThan(Integer value) {
            addCriterion("boolean_02 >", value, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean02GreaterThanOrEqualTo(Integer value) {
            addCriterion("boolean_02 >=", value, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean02LessThan(Integer value) {
            addCriterion("boolean_02 <", value, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean02LessThanOrEqualTo(Integer value) {
            addCriterion("boolean_02 <=", value, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean02In(List<Integer> values) {
            addCriterion("boolean_02 in", values, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean02NotIn(List<Integer> values) {
            addCriterion("boolean_02 not in", values, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean02Between(Integer value1, Integer value2) {
            addCriterion("boolean_02 between", value1, value2, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean02NotBetween(Integer value1, Integer value2) {
            addCriterion("boolean_02 not between", value1, value2, "boolean02");
            return (Criteria) this;
        }

        public Criteria andBoolean03IsNull() {
            addCriterion("boolean_03 is null");
            return (Criteria) this;
        }

        public Criteria andBoolean03IsNotNull() {
            addCriterion("boolean_03 is not null");
            return (Criteria) this;
        }

        public Criteria andBoolean03EqualTo(Integer value) {
            addCriterion("boolean_03 =", value, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean03NotEqualTo(Integer value) {
            addCriterion("boolean_03 <>", value, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean03GreaterThan(Integer value) {
            addCriterion("boolean_03 >", value, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean03GreaterThanOrEqualTo(Integer value) {
            addCriterion("boolean_03 >=", value, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean03LessThan(Integer value) {
            addCriterion("boolean_03 <", value, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean03LessThanOrEqualTo(Integer value) {
            addCriterion("boolean_03 <=", value, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean03In(List<Integer> values) {
            addCriterion("boolean_03 in", values, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean03NotIn(List<Integer> values) {
            addCriterion("boolean_03 not in", values, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean03Between(Integer value1, Integer value2) {
            addCriterion("boolean_03 between", value1, value2, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean03NotBetween(Integer value1, Integer value2) {
            addCriterion("boolean_03 not between", value1, value2, "boolean03");
            return (Criteria) this;
        }

        public Criteria andBoolean04IsNull() {
            addCriterion("boolean_04 is null");
            return (Criteria) this;
        }

        public Criteria andBoolean04IsNotNull() {
            addCriterion("boolean_04 is not null");
            return (Criteria) this;
        }

        public Criteria andBoolean04EqualTo(Integer value) {
            addCriterion("boolean_04 =", value, "boolean04");
            return (Criteria) this;
        }

        public Criteria andBoolean04NotEqualTo(Integer value) {
            addCriterion("boolean_04 <>", value, "boolean04");
            return (Criteria) this;
        }

        public Criteria andBoolean04GreaterThan(Integer value) {
            addCriterion("boolean_04 >", value, "boolean04");
            return (Criteria) this;
        }

        public Criteria andBoolean04GreaterThanOrEqualTo(Integer value) {
            addCriterion("boolean_04 >=", value, "boolean04");
            return (Criteria) this;
        }

        public Criteria andBoolean04LessThan(Integer value) {
            addCriterion("boolean_04 <", value, "boolean04");
            return (Criteria) this;
        }

        public Criteria andBoolean04LessThanOrEqualTo(Integer value) {
            addCriterion("boolean_04 <=", value, "boolean04");
            return (Criteria) this;
        }

        public Criteria andBoolean04In(List<Integer> values) {
            addCriterion("boolean_04 in", values, "boolean04");
            return (Criteria) this;
        }

        public Criteria andBoolean04NotIn(List<Integer> values) {
            addCriterion("boolean_04 not in", values, "boolean04");
            return (Criteria) this;
        }

        public Criteria andBoolean04Between(Integer value1, Integer value2) {
            addCriterion("boolean_04 between", value1, value2, "boolean04");
            return (Criteria) this;
        }

        public Criteria andBoolean04NotBetween(Integer value1, Integer value2) {
            addCriterion("boolean_04 not between", value1, value2, "boolean04");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameIsNull() {
            addCriterion("updated_by_user_name is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameIsNotNull() {
            addCriterion("updated_by_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameEqualTo(String value) {
            addCriterion("updated_by_user_name =", value, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameNotEqualTo(String value) {
            addCriterion("updated_by_user_name <>", value, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameGreaterThan(String value) {
            addCriterion("updated_by_user_name >", value, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("updated_by_user_name >=", value, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameLessThan(String value) {
            addCriterion("updated_by_user_name <", value, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameLessThanOrEqualTo(String value) {
            addCriterion("updated_by_user_name <=", value, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameLike(String value) {
            addCriterion("updated_by_user_name like", value, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameNotLike(String value) {
            addCriterion("updated_by_user_name not like", value, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameIn(List<String> values) {
            addCriterion("updated_by_user_name in", values, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameNotIn(List<String> values) {
            addCriterion("updated_by_user_name not in", values, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameBetween(String value1, String value2) {
            addCriterion("updated_by_user_name between", value1, value2, "updatedByUserName");
            return (Criteria) this;
        }

        public Criteria andUpdatedByUserNameNotBetween(String value1, String value2) {
            addCriterion("updated_by_user_name not between", value1, value2, "updatedByUserName");
            return (Criteria) this;
        }
    }

    /**
     * 此类为自动生成.
     * 对应的数据库表为： gar_resources
     *
     * @mbg.generated 该代码为自动生成，请不要修改
     *
     * DATE: 2018-03-30 13:23
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 此类为自动生成.
     * 对应的数据库表为： gar_resources
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}