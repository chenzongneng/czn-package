package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.RouterGroupMapper;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.RouterGroup;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.criteria.RouterGroupCriteria;
import com.richstonedt.garnet.model.parm.RouterGroupParm;
import com.richstonedt.garnet.model.view.RouterGroupView;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.RouterGroupService;
import com.sun.javafx.iio.common.RoughScaler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RouterGroupServiceImpl extends BaseServiceImpl<RouterGroup, RouterGroupCriteria, Long> implements RouterGroupService {
    @Autowired
    private RouterGroupMapper routerGroupMapper;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.routerGroupMapper;
    }

    @Override
    public String insertRouterGroup(RouterGroupView routerGroupView) {
        RouterGroup routerGroup = routerGroupView.getRouterGroup();
        List<String> appCodeList = routerGroupView.getAppCodeList();
        if (ObjectUtils.isEmpty(routerGroup) || CollectionUtils.isEmpty(appCodeList)) {
            throw new RuntimeException("routerGroup or appCode can not be null");
        }

        for (String appCode : appCodeList) {
            RouterGroup routerGroup1 = new RouterGroup();
            routerGroup1.setId(IdGeneratorUtil.generateId());
            routerGroup1.setGroupName(routerGroup.getGroupName());
            routerGroup1.setAppCode(appCode);
            this.insertSelective(routerGroup1);
        }

        return routerGroup.getGroupName();
    }

    @Override
    public void updateRouterGroup(RouterGroupView routerGroupView) {

        RouterGroup routerGroup = routerGroupView.getRouterGroup();
        if (ObjectUtils.isEmpty(routerGroup) || ObjectUtils.isEmpty(routerGroup.getId())) {
            throw new RuntimeException("routerGroup or id can not be null");
        }
        //拿到旧的groupName
        Long routerGroupId = routerGroup.getId();
        RouterGroup routerGroup1 = this.selectByPrimaryKey(routerGroupId);
        if (ObjectUtils.isEmpty(routerGroup1)) {
            throw new RuntimeException("routerGroup not exist");
        }
        String groupName = routerGroup1.getGroupName();
        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andGroupNameEqualTo(groupName);
        //删除整个应用组
        this.deleteByCriteria(routerGroupCriteria);
        //重新添加应用组
        this.insertRouterGroup(routerGroupView);
    }

    @Override
    public void deleteRouterGroup(RouterGroupView routerGroupView) {
        RouterGroup routerGroup = routerGroupView.getRouterGroup();
        if (ObjectUtils.isEmpty(routerGroup) || ObjectUtils.isEmpty(routerGroup.getId())) {
            throw new RuntimeException("routerGroup or id can not be null");
        }

        //拿到旧的groupName
        RouterGroup routerGroup1 = this.selectByPrimaryKey(routerGroup.getId());
        if (ObjectUtils.isEmpty(routerGroup1)) {
            throw new RuntimeException("routerGroup not exist");
        }
        String groupName = routerGroup1.getGroupName();
        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andGroupNameEqualTo(groupName);
        //删除整个应用组
        this.deleteByCriteria(routerGroupCriteria);
    }

    @Override
    public PageUtil queryRouterGroupByParms(RouterGroupParm routerGroupParm) {

        RouterGroup routerGroup = routerGroupParm.getRouterGroup();
        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();

        if (!ObjectUtils.isEmpty(routerGroup) && !ObjectUtils.isEmpty(routerGroup.getGroupName())) {
            routerGroupCriteria.createCriteria().andGroupNameEqualTo(routerGroup.getGroupName());
        }

        List<RouterGroup> routerGroups = this.selectByCriteria(routerGroupCriteria);
        List<RouterGroup> routerGroupList = new ArrayList<>();
        List<RouterGroupView> routerGroupViews = new ArrayList<>();
        List<String> appCodes = new ArrayList<>();

        if (!CollectionUtils.isEmpty(routerGroups)) {
            //根据groupName进行去重
            Set<String> groupNameSet = new HashSet<>();
            for (RouterGroup routerGroup1 :routerGroups) {
                String groupName = routerGroup1.getGroupName();
                if (!groupNameSet.contains(groupName)) {
                    groupNameSet.add(groupName);
                    routerGroupList.add(routerGroup1);
                }
            }
        }

//        for (RouterGroup routerGroup1 : routerGroupList) {
//            RouterGroupView routerGroupView = new RouterGroupView();
//            appCodes.add(routerGroup1.getAppCode());
//            routerGroupView.setAppCodeList(appCodes);
//            routerGroupView.setRouterGroup(routerGroup1);
//            routerGroupViews.add(routerGroupView);
//        }
        PageUtil result = new PageUtil(routerGroupList, (int)this.countByCriteria(routerGroupCriteria) ,routerGroupParm.getPageSize(),routerGroupParm.getPageNumber());
        return result;
    }

    @Override
    public RouterGroupView selectRouterByIdWithApp(Long id) {
        RouterGroupView routerGroupView = new RouterGroupView();
        RouterGroup routerGroup = this.selectByPrimaryKey(id);
        routerGroupView.setRouterGroup(routerGroup);
        String groupName = routerGroup.getGroupName();
        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andGroupNameEqualTo(groupName);
        List<RouterGroup> routerGroups = this.selectByCriteria(routerGroupCriteria);

        List<Long> applicationIdList = new ArrayList<>();
        List<String> applicationNames = new ArrayList<>();


        for (RouterGroup routerGroup1 : routerGroups) {
            String appCode = routerGroup1.getAppCode();
            ApplicationCriteria applicationCriteria = new ApplicationCriteria();
            applicationCriteria.createCriteria().andAppCodeEqualTo(appCode);
            List<Application> applications = applicationService.selectByCriteria(applicationCriteria);
            for (Application application : applications) {
                applicationIdList.add(application.getId());
                applicationNames.add(application.getName());
            }
        }
        routerGroupView.setApplicationIdList(applicationIdList);
        routerGroupView.setApplicationNames(applicationNames);
        return routerGroupView;
    }

    @Override
    public String getGroupNameByAppCode(String appCode) {
        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andAppCodeEqualTo(appCode);
        RouterGroup routerGroup = this.selectSingleByCriteria(routerGroupCriteria);
        //返回空给拦截器添加返回信息
        if (ObjectUtils.isEmpty(routerGroup)) {
            return null;
        }

        String groupName = routerGroup.getGroupName();
        return groupName;
    }
}