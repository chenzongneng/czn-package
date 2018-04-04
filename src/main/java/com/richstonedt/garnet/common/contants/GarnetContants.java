package com.richstonedt.garnet.common.contants;

public class GarnetContants {

    public final static String CREDENTIAL_EXPIRED_DATE = "2099-12-31";

    public final static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public final static Long NON_VALUE = -1L;

    public final static Long TOKEN_EXPIRED_TIME = 30 * 60000L;

    public static final String RESOURCE_PERMISSION = "all_permission";//判断是否拥有所有权限的标志，和数据库对应

    public static final String JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/views/test.json";

    public static final Long GARNET_TENANT_ID= 1L;

    public static final Long GARNET_USER_ID= 1L;

    public static final Long GARNET_SUPER_ROUTER_GROUP_ID= 1L;

    public static final String GARNET_RESOURCE_DYNAMICPROPERTY_APPCODE = "garnet_appCode"; //默认菜单列表，和数据库type对应

    public static final String GARNET_RESOURCE_DYNAMICPROPERTY_SYSMENU = "garnet_sysMenu"; //默认菜单配置，和数据库type对应

    public static final String GARNET_RESOURCE_DYNAMICPROPERTY_SUPERADMIN = "superAdmin"; //默认超级管理员配置，和数据库type对应

    public static final String SAVE_PATH = "C:\\www\\root\\garnet";


}
