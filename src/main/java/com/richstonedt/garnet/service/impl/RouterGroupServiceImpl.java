package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.RouterGroupMapper;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.RouterGroup;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.criteria.RouterGroupCriteria;
import com.richstonedt.garnet.model.parm.RouterGroupParm;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.model.view.RouterGroupView;
import com.richstonedt.garnet.service.*;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.service.ResourceGroup;

import javax.annotation.Resources;
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

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

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

        RouterGroupCriteria routerGroupCriteria;
        RouterGroup routerGroup1;
        Long currentTime = System.currentTimeMillis();
        for (String appCode : appCodeList) {

            routerGroupCriteria = new RouterGroupCriteria();
            routerGroupCriteria.createCriteria().andAppCodeEqualTo(appCode);
            List<RouterGroup> routerGroupList = this.selectByCriteria(routerGroupCriteria);
            if (!CollectionUtils.isEmpty(routerGroupList) && routerGroupList.size() > 0) {
                throw new RuntimeException("一个应用不能被添加到多个应用组");
            }

            routerGroup1 = new RouterGroup();
            routerGroup1.setId(IdGeneratorUtil.generateId());
            routerGroup1.setGroupName(routerGroup.getGroupName());
            routerGroup1.setAppCode(appCode);
            if (ObjectUtils.isEmpty(routerGroup.getCreatedTime())) {
                routerGroup1.setCreatedTime(currentTime);
            } else {
                routerGroup1.setCreatedTime(routerGroup.getCreatedTime());
            }
            routerGroup1.setModifiedTime(currentTime);
            routerGroup1.setRemark(routerGroup.getRemark());
            routerGroup1.setUpdatedByUserName(routerGroup.getUpdatedByUserName());
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

//        //获取创建时间
        List<RouterGroup> routerGroupList = this.selectByCriteria(routerGroupCriteria);
        if (!CollectionUtils.isEmpty(routerGroupList) && routerGroupList.size() > 0) {
            Long createTime = routerGroupList.get(0).getCreatedTime();
            routerGroup.setCreatedTime(createTime);
            routerGroupView.setRouterGroup(routerGroup);
        }

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

        //如果是超级应用组，不能删除
        if (routerGroup.getId().longValue() == GarnetContants.GARNET_SUPER_ROUTER_GROUP_ID.longValue()) {
            throw new RuntimeException("不能删除超级应用组");
        }


        //拿到应用组名
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
        routerGroupCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        RouterGroupCriteria.Criteria criteria = routerGroupCriteria.createCriteria();

        if (!StringUtils.isEmpty(routerGroupParm.getSearchName())) {
            criteria.andGroupNameLike("%" + routerGroupParm.getSearchName() + "%");
        }

        if (!ObjectUtils.isEmpty(routerGroup) && !ObjectUtils.isEmpty(routerGroup.getGroupName())) {
            criteria.andGroupNameEqualTo(routerGroup.getGroupName());
        }

        List<RouterGroup> routerGroups = this.selectByCriteria(routerGroupCriteria);
        List<RouterGroup> routerGroupList = new ArrayList<>();

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
        //判断是否是超级管理员，进行处理
        List<RouterGroup> routerGroupList1 = this.dealRouterGroupListIfGarnet(routerGroupParm.getUserId(), routerGroupList);

        //添加应用名
        List<RouterGroupView> routerGroupViewList = this.returnWithAppNames(routerGroupList1);

        PageUtil result = new PageUtil(routerGroupViewList, (int)this.countByCriteria(routerGroupCriteria) ,routerGroupParm.getPageSize(),routerGroupParm.getPageNumber());
        return result;
    }

    private List<RouterGroupView> returnWithAppNames(List<RouterGroup> resourceGroupList) {
        RouterGroupView routerGroupView = null;
        List<String> appNames = null;
        List<String> appCodeList = null;
        List<RouterGroupView> routerGroupViewList = new ArrayList<>();
        ApplicationCriteria applicationCriteria = null;
        RouterGroupCriteria routerGroupCriteria = null;

        for (RouterGroup routerGroup : resourceGroupList) {

            //根据应用组名查找所有记录，即查找所有绑定的appCode
            String routerGroupName = routerGroup.getGroupName();
            routerGroupCriteria = new RouterGroupCriteria();
            routerGroupCriteria.createCriteria().andGroupNameEqualTo(routerGroupName);
            List<RouterGroup> routerGroups = this.selectByCriteria(routerGroupCriteria);

            //添加应用名称
            appNames = new ArrayList<>();
            appCodeList = new ArrayList<>();
            for (RouterGroup routerGroup1 : routerGroups) {
                String appCode = routerGroup1.getAppCode();
                applicationCriteria = new ApplicationCriteria();
                applicationCriteria.createCriteria().andAppCodeEqualTo(appCode).andStatusEqualTo(1);
                Application application = applicationService.selectSingleByCriteria(applicationCriteria);

                if (!ObjectUtils.isEmpty(application)) {
                    appNames.add(application.getName());
                    appCodeList.add(application.getAppCode());
                }
            }

            routerGroupView = new RouterGroupView();
            routerGroupView.setRouterGroup(routerGroup);
            routerGroupView.setApplicationNames(appNames);
            routerGroupView.setAppCodeList(appCodeList);

            routerGroupViewList.add(routerGroupView);
        }

        return routerGroupViewList;
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
        List<String> appCodeList = new ArrayList<>();


        for (RouterGroup routerGroup1 : routerGroups) {
            String appCode = routerGroup1.getAppCode();
            ApplicationCriteria applicationCriteria = new ApplicationCriteria();
            applicationCriteria.createCriteria().andAppCodeEqualTo(appCode).andStatusEqualTo(1);
//            List<Application> applications = applicationService.selectByCriteria(applicationCriteria);
//            for (Application application : applications) {
//                appCodeList.add(application.getAppCode());
//                applicationIdList.add(application.getId());
//                applicationNames.add(application.getName());
//            }

            Application application = applicationService.selectSingleByCriteria(applicationCriteria);
            appCodeList.add(application.getAppCode());
            applicationIdList.add(application.getId());
            applicationNames.add(application.getName());
        }
        routerGroupView.setApplicationIdList(applicationIdList);
        routerGroupView.setApplicationNames(applicationNames);
        routerGroupView.setAppCodeList(appCodeList);
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

    @Override
    public boolean isGroupNameUsed(Long id, String groupName) {

        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andGroupNameEqualTo(groupName);
        List<RouterGroup> routerGroupList = this.selectByCriteria(routerGroupCriteria);
        boolean b = false;
        if (CollectionUtils.isEmpty(routerGroupList) || routerGroupList.size() <= 0) {
            b = true;
        } else {
            if (!ObjectUtils.isEmpty(id) && id.longValue() != 0L) {
                for (RouterGroup routerGroup : routerGroupList) {
                    if (routerGroup.getId().longValue() == id.longValue()) {
                        b = true;
                    }
                }
            }
        }

        return b;
    }

    public List<RouterGroup> dealRouterGroupListIfGarnet(Long userId, List<RouterGroup> routerGroups) {

        ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(userId);
        boolean isSuperAdmin = returnTenantIdView.isSuperAdmin();
        boolean isSuperAdminBelongGarnet = commonService.superAdminBelongGarnet(userId);

        List<RouterGroup> routerGroupList = new ArrayList<>();
        //如果不是超级管理员
        if (!(isSuperAdmin && isSuperAdminBelongGarnet)) {
            //去除超级应用组
            for (RouterGroup routerGroup : routerGroups) {
                if (!"超级应用组".equals(routerGroup.getGroupName())) {
                    routerGroupList.add(routerGroup);
                }
            }
            return routerGroupList;
        } else {
            return routerGroups;
        }
    }

}