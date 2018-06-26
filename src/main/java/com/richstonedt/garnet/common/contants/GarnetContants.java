package com.richstonedt.garnet.common.contants;

public class GarnetContants {

    public final static String CREDENTIAL_EXPIRED_DATE = "2099-12-31";

    public final static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public final static Long NON_VALUE = -1L;

    public final static Long TOKEN_EXPIRED_TIME = 30 * 60000L;

    public static final String SAVE_PATH = "/home/users/liangzheng/garnet/excel";

    //初始数据
    public static final Long GARNET_TENANT_ID = 1L;

    public static final Long GARNET_APPLICATION_ID = 1L;

    public static final Long GARNET_USER_ID = 1L;

    public static final Long GARNET_PERMISSION_ID = 1L;

    public static final Long GARNET_ROLE_ID = 1L;

    public static final Long GARNET_GROUP_ID = 1L;

    public static final Long GARNET_SUPER_ROUTER_GROUP_ID = 1L;

    /**
     * 选项-租户默认关联所有用户
     */
    public static final Long GARNET_RESOURCE_USERRELATION_ID = 70L;

    /**
     *  Garnet系统按钮，和数据库type对应
     */
    public static final String GARNET_RESOURCE_DYNAMICPROPERTY_BUTTON = "garnet_button";

    /**
     *  Garnet系统模块，和数据库type对应
     */
    public static final String GARNET_RESOURCE_DYNAMICPROPERTY_MODULE = "garnet_module";

    /**
     *    默认菜单配置，和数据库type对应
     */
    public static final String GARNET_RESOURCE_DYNAMICPROPERTY_SYSMENU = "garnet_sysMenu";

    /**
     * 数据-用户管理关联租户列表
     */
    public static final String GARNET_DATA_USERMANAGE_TENANTLIST_PATH = "/garnet/data/userManage/tenantList";

    /**
     * 数据-为用户关联Garnet租户的组
     */
    public static final String GARNET_DATA_USERMANAGE_GARNETGROUPLIST_PATH = "/garnet/data/userManage/garnetGroupList";

    /**
     * 数据-组管理新增编辑页租户列表
     */
    public static final String GARNET_DATA_GROUPMANAGE_TENANTLIST_PATH = "/garnet/data/groupManage/tenantList";

    /**
     * 数据-租户数据列表
     */
    public static final String GARNET_DATA_TENANTMANAGE_QUERY_PATH = "/garnet/data/tenantManage/query";

    /**
     * 数据-应用列表
     */
    public static final String GARNET_DATA_APPLICATIONMANAGE_QUERY_PATH = "/garnet/data/applicationManage/query";

    /**
     * 数据-用户列表
     */
    public static final String GARNET_DATA_USERMANAGE_QUERY_PATH = "/garnet/data/userManage/query";

    /**
     * 数据-组列表
     */
    public static final String GARNET_DATA_GROUPMANAGE_QUERY_PATH = "/garnet/data/groupManage/query";

    /**
     * 数据-角色列表
     */
    public static final String GARNET_DATA_ROLEMANAGE_QUERY_PATH = "/garnet/data/roleManage/query";

    /**
     * 数据-权限列表
     */
    public static final String GARNET_DATA_PERMISSIONMANAGE_QUERY_PATH = "/garnet/data/permissionManage/query";

    /**
     * 数据-资源列表
     */
    public static final String GARNET_DATA_RESOURCEMANAGE_QUERY_PATH = "/garnet/data/resourceManage/query";

    /**
     * 数据-资源类型列表
     */
    public static final String GARNET_DATA_RESOURCETYPEMANAGE_QUERY_PATH = "/garnet/data/resourceTypeManage/query";

    /**
     * 数据-组列表
     */
    public static final String GARNET_DATA_SSOMANAGE_QUERY_PATH = "/garnet/data/ssoManage/query";

    /**
     * 租户管理员 path
     */
    public static final String GARNET_TENANTMANAGE_PATH = "/garnet/tenantManage";

    /**
     * 数据-租户管理应用选择列表
     */
    public static final String GARNET_DATA_TENANTMANAGE_APPLICATIONLIST_PATH = "/garnet/data/tenantManage/applicationList";

    /**
     * 数据-应用管理租户选择列表
     */
    public static final String GARNET_DATA_APPLICATIONMANAGE_TENANTLIST_PATH = "/garnet/data/applicationManage/tenantList";

    /**
     * 默认超级管理员配置，和数据库type对应
     */
    public static final String GARNET_RESOURCE_DYNAMICPROPERTY_SUPERADMIN = "superAdmin";

    /**
     * 默认是否属于Garnet用户配置，和数据库type对应
     */
    public static final String GARNET_RESOURCE_DYNAMICPROPERTY_BELONGTOGARNET = "garnet_system_isBelongToGarnet";

    /**
     * 判断是否拥有所有权限的标志，和数据库type对应
     */
    public static final String RESOURCE_PERMISSION = "all_permission";


    /**
     * 根据创建时间排序
     */
    public static final String ORDER_BY_CREATED_TIME = "created_time";

    /**
     * 标志请求是加载列表数据还是树
     */
    public static final String QUERYORTREE_TREE = "tree";
}
