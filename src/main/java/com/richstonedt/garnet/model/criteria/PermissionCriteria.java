package com.richstonedt.garnet.model.criteria;

import java.util.ArrayList;
import java.util.List;

public class PermissionCriteria {
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
     * TABLE： gar_permissions
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected Integer start;

    /**
     * TABLE： gar_permissions
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    protected Integer end;

    /**
     * <br>
     *
     * TABLE： gar_permissions<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public PermissionCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * <br>
     *
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * TABLE： gar_permissions<br>
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
     * 对应的数据库表为： gar_permissions
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

        public Criteria andResourcePathWildcardIsNull() {
            addCriterion("resource_path_wildcard is null");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardIsNotNull() {
            addCriterion("resource_path_wildcard is not null");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardEqualTo(String value) {
            addCriterion("resource_path_wildcard =", value, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardNotEqualTo(String value) {
            addCriterion("resource_path_wildcard <>", value, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardGreaterThan(String value) {
            addCriterion("resource_path_wildcard >", value, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardGreaterThanOrEqualTo(String value) {
            addCriterion("resource_path_wildcard >=", value, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardLessThan(String value) {
            addCriterion("resource_path_wildcard <", value, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardLessThanOrEqualTo(String value) {
            addCriterion("resource_path_wildcard <=", value, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardLike(String value) {
            addCriterion("resource_path_wildcard like", value, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardNotLike(String value) {
            addCriterion("resource_path_wildcard not like", value, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardIn(List<String> values) {
            addCriterion("resource_path_wildcard in", values, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardNotIn(List<String> values) {
            addCriterion("resource_path_wildcard not in", values, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardBetween(String value1, String value2) {
            addCriterion("resource_path_wildcard between", value1, value2, "resourcePathWildcard");
            return (Criteria) this;
        }

        public Criteria andResourcePathWildcardNotBetween(String value1, String value2) {
            addCriterion("resource_path_wildcard not between", value1, value2, "resourcePathWildcard");
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

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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
     * 对应的数据库表为： gar_permissions
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
     * 对应的数据库表为： gar_permissions
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