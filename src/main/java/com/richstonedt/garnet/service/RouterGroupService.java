package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.RouterGroup;
import com.richstonedt.garnet.model.criteria.RouterGroupCriteria;
import com.richstonedt.garnet.model.parm.RouterGroupParm;
import com.richstonedt.garnet.model.view.RouterGroupView;

public interface RouterGroupService extends BaseService<RouterGroup, RouterGroupCriteria, Long> {


    String insertRouterGroup(RouterGroupView routerGroupView);

    void updateRouterGroup(RouterGroupView routerGroupView);

    void deleteRouterGroup(RouterGroupView routerGroupView);

    PageUtil queryRouterGroupByParms(RouterGroupParm routerGroupParm);

    /**
     * 获取单个router白名单及其绑定的applications
     * @param id
     * @return
     */
    RouterGroupView selectRouterByIdWithApp(Long id);

    String getGroupNameByAppCode(String appCode);
}