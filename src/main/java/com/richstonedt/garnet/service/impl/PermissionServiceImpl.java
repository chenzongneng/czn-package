package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.interceptory.LogRequired;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.PermissionMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.criteria.PermissionCriteria;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.criteria.RolePermissionCriteria;
import com.richstonedt.garnet.model.parm.PermissionParm;
import com.richstonedt.garnet.model.parm.PermissionResourceParm;
import com.richstonedt.garnet.model.view.PermissionView;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.service.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.json.Json;

import java.util.*;

@Service
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission, PermissionCriteria, Long> implements PermissionService {

    private static final Long TENANTID_NULL = 0L;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TenantService tenantService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.permissionMapper;
    }


    @LogRequired(module = "权限管理模块", method = "新增权限")
    @Override
    public Long insertPermission(PermissionView permissionView) {

        Permission permission = permissionView.getPermission();
        permission.setId(IdGeneratorUtil.generateId());
        Long currentTime = System.currentTimeMillis();
        permission.setCreatedTime(currentTime);
        permission.setModifiedTime(currentTime);

        //检查权限名称是否已被使用
        checkDuplicateName(permission);

        this.insertSelective(permission);

        return permission.getId();
    }

    @LogRequired(module = "权限管理模块", method = "配置权限")
    @Override
    public void updatePerssion(PermissionView permissionView) {

        Permission permission = permissionView.getPermission();

        Long currentTime = System.currentTimeMillis();

        permission.setModifiedTime(currentTime);

        checkDuplicateName(permission);

        this.updateByPrimaryKeySelective(permission);

    }


    @Override
    public List<Resource> queryResourcesByPermissionResourceParm(PermissionResourceParm permissionResourceParm) {

        List<Long> permissionIds = permissionResourceParm.getPermissionIds();

        PermissionCriteria permissionCriteria = new PermissionCriteria();

        permissionCriteria.createCriteria().andIdIn(permissionIds);

        List<Permission> permissions = this.selectByCriteria(permissionCriteria);

        List<Resource> resources = new ArrayList<Resource>();

        for (Permission permission : permissions) {

            String wildCard = permission.getResourcePathWildcard();

            if(!StringUtil.isEmpty(wildCard)){

                ResourceCriteria resourceCriteria = new ResourceCriteria();

                resourceCriteria.createCriteria().andPathLike(wildCard);

                List<Resource> resources1 = resourceService.selectByCriteria(resourceCriteria);

                resources.addAll(resources1);

            }

        }

        return resources;
    }

    @Override
    public PageUtil<Permission> queryPermissionsByParms(PermissionParm permissionParm) {

        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        PermissionCriteria.Criteria criteria = permissionCriteria.createCriteria();
        criteria.andStatusEqualTo(1); //查询没被删除的

        if (!StringUtils.isEmpty(permissionParm.getUserId())) {
            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(permissionParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();

            //如果不是garnet的超级管理员
            if (!returnTenantIdView.isSuperAdmin() || (returnTenantIdView.isSuperAdmin() && !commonService.superAdminBelongGarnet(permissionParm.getUserId()))) {
                if (!CollectionUtils.isEmpty(tenantIds) && tenantIds.size() > 0 ) {
                    criteria.andTenantIdIn(tenantIds);
                } else {
                    //没有关联租户，返回空
                    return  new PageUtil(null, 0 ,permissionParm.getPageSize(), permissionParm.getPageNumber());
                }
            }

        }
        if (!StringUtils.isEmpty(permissionParm.getSearchName())) {
            criteria.andNameLike("%" + permissionParm.getSearchName() + "%");
        }

        if (!ObjectUtils.isEmpty(permissionParm.getTenantId())) {
            criteria.andTenantIdEqualTo(permissionParm.getTenantId());
        }

        if (!ObjectUtils.isEmpty(permissionParm.getApplicationId())) {
            criteria.andApplicationIdEqualTo(permissionParm.getApplicationId());
        }

        List<PermissionView> permissionViewList = new ArrayList<>();
        List<Permission> permissionList = this.selectByCriteria(permissionCriteria);
        PermissionView permissionView;
        Application application;
        Tenant tenant;
        for (Permission permission : permissionList) {
            permissionView = new PermissionView();
            permissionView.setPermission(permission);
            application = applicationService.selectByPrimaryKey(permission.getApplicationId());
            String applicationName;
            if (ObjectUtils.isEmpty(application)) {
                applicationName = "";
            } else {
                applicationName = application.getName();
            }
            permissionView.setApplicationName(applicationName);
            tenant = tenantService.selectByPrimaryKey(permission.getTenantId());
            String tenantName;
            if (ObjectUtils.isEmpty(tenant)) {
                tenantName = "";
            } else {
                tenantName = tenant.getName();
            }
            permissionView.setTenantName(tenantName);

            if (permission.getTenantId().longValue() != 0 && permission.getApplicationId().longValue() == 0) {
                permissionView.setType("租户");
            } else if (permission.getTenantId().longValue() == 0 && permission.getApplicationId().longValue() != 0) {
                permissionView.setType("应用");
            } else {
                permissionView.setType("租户+应用");
            }

            permissionViewList.add(permissionView);
        }

        PageUtil result = new PageUtil(permissionViewList, (int)this.countByCriteria(permissionCriteria) ,permissionParm.getPageSize(), permissionParm.getPageNumber());

        return result;
    }

    @LogRequired(module = "权限管理模块", method = "删除权限")
    @Override
    public void updateStatusById(Permission permission) {

        //判断是否是超级权限，如果是，不能删除
        if (permission.getId().longValue() == GarnetContants.GARNET_PERMISSION_ID) {
            throw new RuntimeException("不能删除超级权限");
        }

        //删除关联外键
        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andPermissionIdEqualTo(permission.getId());
        List<RolePermission> rolePermissions = rolePermissionService.selectByCriteria(rolePermissionCriteria);
        if (CollectionUtils.isEmpty(rolePermissions) && rolePermissions.size() > 0) {
            for (RolePermission rolePermission : rolePermissions) {
                rolePermissionService.deleteByPrimaryKey(rolePermission.getId());
            }
        }

        Long currentTime = System.currentTimeMillis();
        permission.setModifiedTime(currentTime);
        permission.setStatus(0);
        this.updateByPrimaryKeySelective(permission);

    }

    @Override
    public List<Permission> queryPermissionByTenantId(PermissionParm permissionParm) {
        Long tenantId = permissionParm.getTenantId();

        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andTenantIdEqualTo(tenantId).andStatusEqualTo(1);
        List<Permission> permissions = this.selectByCriteria(permissionCriteria);

        return permissions;
    }

    @Override
    public List<Permission> queryPermissionByApplicationId(PermissionParm permissionParm) {
        Long applicationId = permissionParm.getApplicationId();

        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andApplicationIdEqualTo(applicationId).andStatusEqualTo(1);
        List<Permission> permissions = this.selectByCriteria(permissionCriteria);

        return permissions;
    }

    @Override
    public List<Permission> queryPermissionByParams(PermissionParm permissionParm) {
        Long applicationId = permissionParm.getApplicationId();
        Long tenantId = permissionParm.getTenantId();
        List<Permission> permissions = new ArrayList<>();

        PermissionCriteria permissionCriteria = new PermissionCriteria();

        if (!ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0 && (ObjectUtils.isEmpty(tenantId) || tenantId.longValue() == 0)) {
            //应用级
            permissionCriteria.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(0L).andStatusEqualTo(1);
            permissions = this.selectByCriteria(permissionCriteria);
        }

        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0 && (ObjectUtils.isEmpty(applicationId) || applicationId.longValue() == 0)) {
            //租户级
            permissionCriteria.createCriteria().andTenantIdEqualTo(tenantId).andApplicationIdEqualTo(0L).andStatusEqualTo(1);
            permissions = this.selectByCriteria(permissionCriteria);
        }

        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0 && !ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0) {
            PermissionCriteria permissionCriteria1 = new PermissionCriteria();
            permissionCriteria1.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(0L).andStatusEqualTo(1);
            PermissionCriteria permissionCriteria2 = new PermissionCriteria();
            permissionCriteria2.createCriteria().andTenantIdEqualTo(tenantId).andApplicationIdEqualTo(0L).andStatusEqualTo(1);
            PermissionCriteria permissionCriteria3 = new PermissionCriteria();
            permissionCriteria3.createCriteria().andTenantIdEqualTo(tenantId).andApplicationIdEqualTo(applicationId).andStatusEqualTo(1);

            List<Permission> permissionList1 = this.selectByCriteria(permissionCriteria1);
            List<Permission> permissionList2 = this.selectByCriteria(permissionCriteria2);
            List<Permission> permissionList3 = this.selectByCriteria(permissionCriteria3);

            permissions.addAll(permissionList1);
            permissions.addAll(permissionList2);
            permissions.addAll(permissionList3);
        }

        //去重
        Set<Long> permissionIdSet = new HashSet<>();
        List<Permission> permissionList = new ArrayList<>();
        for (Permission permission : permissions) {
            if (!permissionIdSet.contains(permission.getId())) {
                permissionIdSet.add(permission.getId());
                permissionList.add(permission);
            }
        }

        return permissionList;
    }

    @LogRequired(module = "权限管理模块", method = "查询权限列表")
    @Override
    public PageUtil getPermissionsByParams(PermissionParm permissionParm) {
        Long userId = permissionParm.getUserId();
        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        PermissionCriteria.Criteria criteria = permissionCriteria.createCriteria();
        criteria.andStatusEqualTo(1);

        if (!StringUtils.isEmpty(permissionParm.getSearchName())) {
            criteria.andNameLike("%" + permissionParm.getSearchName() + "%");
        }

        if (!ObjectUtils.isEmpty(permissionParm.getTenantId())) {
            criteria.andTenantIdEqualTo(permissionParm.getTenantId());
        }

        if (!ObjectUtils.isEmpty(permissionParm.getApplicationId())) {
            criteria.andApplicationIdEqualTo(permissionParm.getApplicationId());
        }

        String level = resourceService.getLevelByUserIdAndPath(userId, GarnetContants.GARNET_DATA_PERMISSIONMANAGE_QUERY_PATH);
        List<Permission> permissionList = new ArrayList<>();
        if (Integer.valueOf(level) == 1) {
            //全部数据
            permissionList = this.selectByCriteria(permissionCriteria);
        } else if (Integer.valueOf(level) == 2) {
            //非Garnet数据
            List<Long> tenantIdList = commonService.getTenantIdsNotGarnet(userId);
            PermissionCriteria permissionCriteria1 = new PermissionCriteria();
            permissionCriteria1.createCriteria().andTenantIdIn(tenantIdList).andStatusEqualTo(1);
            List<Permission> permissionList1 = this.selectByCriteria(permissionCriteria1);

            PermissionCriteria permissionCriteria2 = new PermissionCriteria();
            permissionCriteria2.createCriteria().andTenantIdEqualTo(TENANTID_NULL).andApplicationIdNotEqualTo(GarnetContants.GARNET_APPLICATION_ID).andStatusEqualTo(1);
            List<Permission> permissionList2 = this.selectByCriteria(permissionCriteria2);

            List<Permission> permissionList3 = new ArrayList<>();
            permissionList3.addAll(permissionList1);
            permissionList3.addAll(permissionList2);

            Set<Long> permissionIdSet = new HashSet<>();
            for (Permission permission : permissionList3) {
                if (!permissionIdSet.contains(permission.getId())) {
                    permissionList.add(permission);
                    permissionIdSet.add(permission.getId());
                }
            }

//            List<Long> permissionIdList = new ArrayList<>();
//            for (Permission permission : permissionList3) {
//                permissionIdList.add(permission.getId());
//            }
//
//            criteria.andIdIn(permissionIdList);
//            permissionList = this.selectByCriteria(permissionCriteria);
        } else if (Integer.valueOf(level) == 3) {
            List<Tenant> tenantList = tenantService.getTenantManageListByUserId(userId);
            List<Long> tenantIdList = new ArrayList<>();
            for (Tenant tenant : tenantList) {
                tenantIdList.add(tenant.getId());
            }
            criteria.andTenantIdIn(tenantIdList);
            permissionList = this.selectByCriteria(permissionCriteria);
        }

        List<PermissionView> permissionViewList = new ArrayList<>();
        PermissionView permissionView;
        Application application;
        Tenant tenant;
        for (Permission permission : permissionList) {
            permissionView = new PermissionView();
            permissionView.setPermission(permission);
            application = applicationService.selectByPrimaryKey(permission.getApplicationId());
            String applicationName;
            if (ObjectUtils.isEmpty(application)) {
                applicationName = "";
            } else {
                applicationName = application.getName();
            }
            permissionView.setApplicationName(applicationName);
            tenant = tenantService.selectByPrimaryKey(permission.getTenantId());
            String tenantName;
            if (ObjectUtils.isEmpty(tenant)) {
                tenantName = "";
            } else {
                tenantName = tenant.getName();
            }
            permissionView.setTenantName(tenantName);

            if (permission.getTenantId().longValue() != 0 && permission.getApplicationId().longValue() == 0) {
                permissionView.setType("租户");
            } else if (permission.getTenantId().longValue() == 0 && permission.getApplicationId().longValue() != 0) {
                permissionView.setType("应用");
            } else {
                permissionView.setType("租户+应用");
            }
            permissionViewList.add(permissionView);
        }

        PageUtil result = new PageUtil(permissionViewList, (int)this.countByCriteria(permissionCriteria) ,permissionParm.getPageSize(), permissionParm.getPageNumber());

        return result;
    }

    private void checkDuplicateName(Permission permission) {

        Long id = permission.getId();
        String name = permission.getName();

        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andNameEqualTo(name).andStatusEqualTo(1);
        Permission permission1 = this.selectSingleByCriteria(permissionCriteria);
        if (!ObjectUtils.isEmpty(permission1) && permission1.getId().longValue() != id.longValue()) {
            throw new RuntimeException("权限名称已被使用");
        }

    }

}