package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.RouterGroup;

import java.net.UnknownHostException;
import java.util.List;

public interface CommonService{

    /**
     * 判断当前登录者是否为garnet自身的管理员，如果不是则不需要garnet本身的数据
     * @param userId
     * @param tenantIds
     * @return
     */
    public List<Long> dealTenantIdsIfGarnet(Long userId,List<Long> tenantIds);

    /**
     * 返回是不是garnet的超级管理员
     * @param userId
     * @return
     */
    boolean superAdminBelongGarnet(Long userId);

    /**
     * 根据是不是Garnet超级管理员，对组id列表进行处理
     * @param userId
     * @param groupIds
     * @return
     */
    List<Long> dealGroupIdsIfGarnet(Long userId, List<Long> groupIds);

    boolean insertLog(String meaasge, String operation);

//    String getCookieValue(String cookie, String name);

}
