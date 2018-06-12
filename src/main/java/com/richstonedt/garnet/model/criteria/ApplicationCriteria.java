package com.richstonedt.garnet.model.criteria;

import java.util.ArrayList;
import java.util.List;

public class ApplicationCriteria {
    /**
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    protected String orderByClause;

    /**
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    protected boolean distinct;

    /**
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    protected List<Criteria> oredCriteria;

    /**
     * TABLE： gar_applications
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    protected Integer start;

    /**
     * TABLE： gar_applications
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    protected Integer end;

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public ApplicationCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
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
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public Integer getStart() {
        return start;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public void setEnd(Integer end) {
        this.end = end;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public Integer getEnd() {
        return end;
    }

    /**
     * 此类为自动生成.
     * 对应的数据库表为： gar_applications
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
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

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andAppCodeIsNull() {
            addCriterion("app_code is null");
            return (Criteria) this;
        }

        public Criteria andAppCodeIsNotNull() {
            addCriterion("app_code is not null");
            return (Criteria) this;
        }

        public Criteria andAppCodeEqualTo(String value) {
            addCriterion("app_code =", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotEqualTo(String value) {
            addCriterion("app_code <>", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeGreaterThan(String value) {
            addCriterion("app_code >", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeGreaterThanOrEqualTo(String value) {
            addCriterion("app_code >=", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLessThan(String value) {
            addCriterion("app_code <", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLessThanOrEqualTo(String value) {
            addCriterion("app_code <=", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLike(String value) {
            addCriterion("app_code like", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotLike(String value) {
            addCriterion("app_code not like", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeIn(List<String> values) {
            addCriterion("app_code in", values, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotIn(List<String> values) {
            addCriterion("app_code not in", values, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeBetween(String value1, String value2) {
            addCriterion("app_code between", value1, value2, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotBetween(String value1, String value2) {
            addCriterion("app_code not between", value1, value2, "appCode");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiIsNull() {
            addCriterion("refresh_resources_api is null");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiIsNotNull() {
            addCriterion("refresh_resources_api is not null");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiEqualTo(String value) {
            addCriterion("refresh_resources_api =", value, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiNotEqualTo(String value) {
            addCriterion("refresh_resources_api <>", value, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiGreaterThan(String value) {
            addCriterion("refresh_resources_api >", value, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiGreaterThanOrEqualTo(String value) {
            addCriterion("refresh_resources_api >=", value, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiLessThan(String value) {
            addCriterion("refresh_resources_api <", value, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiLessThanOrEqualTo(String value) {
            addCriterion("refresh_resources_api <=", value, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiLike(String value) {
            addCriterion("refresh_resources_api like", value, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiNotLike(String value) {
            addCriterion("refresh_resources_api not like", value, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiIn(List<String> values) {
            addCriterion("refresh_resources_api in", values, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiNotIn(List<String> values) {
            addCriterion("refresh_resources_api not in", values, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiBetween(String value1, String value2) {
            addCriterion("refresh_resources_api between", value1, value2, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andRefreshResourcesApiNotBetween(String value1, String value2) {
            addCriterion("refresh_resources_api not between", value1, value2, "refreshResourcesApi");
            return (Criteria) this;
        }

        public Criteria andHostsIsNull() {
            addCriterion("hosts is null");
            return (Criteria) this;
        }

        public Criteria andHostsIsNotNull() {
            addCriterion("hosts is not null");
            return (Criteria) this;
        }

        public Criteria andHostsEqualTo(String value) {
            addCriterion("hosts =", value, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsNotEqualTo(String value) {
            addCriterion("hosts <>", value, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsGreaterThan(String value) {
            addCriterion("hosts >", value, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsGreaterThanOrEqualTo(String value) {
            addCriterion("hosts >=", value, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsLessThan(String value) {
            addCriterion("hosts <", value, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsLessThanOrEqualTo(String value) {
            addCriterion("hosts <=", value, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsLike(String value) {
            addCriterion("hosts like", value, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsNotLike(String value) {
            addCriterion("hosts not like", value, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsIn(List<String> values) {
            addCriterion("hosts in", values, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsNotIn(List<String> values) {
            addCriterion("hosts not in", values, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsBetween(String value1, String value2) {
            addCriterion("hosts between", value1, value2, "hosts");
            return (Criteria) this;
        }

        public Criteria andHostsNotBetween(String value1, String value2) {
            addCriterion("hosts not between", value1, value2, "hosts");
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

        public Criteria andServiceModeIsNull() {
            addCriterion("service_mode is null");
            return (Criteria) this;
        }

        public Criteria andServiceModeIsNotNull() {
            addCriterion("service_mode is not null");
            return (Criteria) this;
        }

        public Criteria andServiceModeEqualTo(String value) {
            addCriterion("service_mode =", value, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeNotEqualTo(String value) {
            addCriterion("service_mode <>", value, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeGreaterThan(String value) {
            addCriterion("service_mode >", value, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeGreaterThanOrEqualTo(String value) {
            addCriterion("service_mode >=", value, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeLessThan(String value) {
            addCriterion("service_mode <", value, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeLessThanOrEqualTo(String value) {
            addCriterion("service_mode <=", value, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeLike(String value) {
            addCriterion("service_mode like", value, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeNotLike(String value) {
            addCriterion("service_mode not like", value, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeIn(List<String> values) {
            addCriterion("service_mode in", values, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeNotIn(List<String> values) {
            addCriterion("service_mode not in", values, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeBetween(String value1, String value2) {
            addCriterion("service_mode between", value1, value2, "serviceMode");
            return (Criteria) this;
        }

        public Criteria andServiceModeNotBetween(String value1, String value2) {
            addCriterion("service_mode not between", value1, value2, "serviceMode");
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

        public Criteria andDefaultIndexUrlIsNull() {
            addCriterion("default_index_url is null");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlIsNotNull() {
            addCriterion("default_index_url is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlEqualTo(String value) {
            addCriterion("default_index_url =", value, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlNotEqualTo(String value) {
            addCriterion("default_index_url <>", value, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlGreaterThan(String value) {
            addCriterion("default_index_url >", value, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlGreaterThanOrEqualTo(String value) {
            addCriterion("default_index_url >=", value, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlLessThan(String value) {
            addCriterion("default_index_url <", value, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlLessThanOrEqualTo(String value) {
            addCriterion("default_index_url <=", value, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlLike(String value) {
            addCriterion("default_index_url like", value, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlNotLike(String value) {
            addCriterion("default_index_url not like", value, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlIn(List<String> values) {
            addCriterion("default_index_url in", values, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlNotIn(List<String> values) {
            addCriterion("default_index_url not in", values, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlBetween(String value1, String value2) {
            addCriterion("default_index_url between", value1, value2, "defaultIndexUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultIndexUrlNotBetween(String value1, String value2) {
            addCriterion("default_index_url not between", value1, value2, "defaultIndexUrl");
            return (Criteria) this;
        }
    }

    /**
     * 此类为自动生成.
     * 对应的数据库表为： gar_applications
     *
     * @mbg.generated 该代码为自动生成，请不要修改
     *
     * DATE: 2018-06-05 12:21
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 此类为自动生成.
     * 对应的数据库表为： gar_applications
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
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