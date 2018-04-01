package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.PermissionMapper;
import com.richstonedt.garnet.model.Permission;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.RolePermission;
import com.richstonedt.garnet.model.criteria.PermissionCriteria;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.criteria.RolePermissionCriteria;
import com.richstonedt.garnet.model.parm.PermissionParm;
import com.richstonedt.garnet.model.parm.PermissionResourceParm;
import com.richstonedt.garnet.model.view.PermissionView;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission, PermissionCriteria, Long> implements PermissionService {

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

    @Override
    public BaseMapper getBaseMapper() {
        return this.permissionMapper;
    }


    @Override
    public Long insertPermission(PermissionView permissionView) {

        Permission permission = permissionView.getPermission();
        permission.setId(IdGeneratorUtil.generateId());
        Long currentTime = new Date().getTime();
        permission.setCreatedTime(currentTime);
        permission.setModifiedTime(currentTime);

        this.insertSelective(permission);

        if (!ObjectUtils.isEmpty(permissionView.getRolePermissions())) {

            for (RolePermission rolePermission :
                    permissionView.getRolePermissions()) {

                rolePermission.setId(IdGeneratorUtil.generateId());

                rolePermission.setPermissionId(permission.getId());

                rolePermissionService.insertSelective(rolePermission);

            }
        }

        return permission.getId();
    }

    @Override
    public void updatePerssion(PermissionView permissionView) {

        Permission permission = permissionView.getPermission();

        Long currentTime = new Date().getTime();

        permission.setModifiedTime(currentTime);

        this.updateByPrimaryKeySelective(permission);

        if (!ObjectUtils.isEmpty(permissionView.getRolePermissions())) {

            RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();

            rolePermissionCriteria.createCriteria().andPermissionIdEqualTo(permission.getId());

            rolePermissionService.deleteByCriteria(rolePermissionCriteria);

            for (RolePermission rolePermission :
                    permissionView.getRolePermissions()) {

                rolePermission.setId(IdGeneratorUtil.generateId());

                rolePermission.setPermissionId(permission.getId());

                rolePermissionService.insertSelective(rolePermission);

            }

        }
    }

    @Override
    public void deletePerssion(PermissionView permissionView) {

        Permission permission = permissionView.getPermission();

        if (!ObjectUtils.isEmpty(permissionView.getRolePermissions())) {

            RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();

            rolePermissionCriteria.createCriteria().andPermissionIdEqualTo(permission.getId());

            rolePermissionService.deleteByCriteria(rolePermissionCriteria);

        }

        this.deleteByPrimaryKey(permission.getId());

    }


    @Override
    public List<Resource> queryResourcesByPermissionResourceParm(PermissionResourceParm permissionResourceParm) {

        List<Long> permissionIds = permissionResourceParm.getPermissionIds();

        PermissionCriteria permissionCriteria = new PermissionCriteria();

        permissionCriteria.createCriteria().andIdIn(permissionIds);

        List<Permission> permissions = this.selectByCriteria(permissionCriteria);

        List<Resource> resources = new ArrayList<Resource>();

        for (Permission permission : permissions
                ) {

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
    public PageInfo<Permission> queryPerssionsByParms(PermissionParm permissionParm) {

        Permission permission = permissionParm.getPermission();
        PermissionCriteria permissionCriteria = new PermissionCriteria();
        PermissionCriteria.Criteria criteria = permissionCriteria.createCriteria();
        criteria.andStatusEqualTo(1); //查询没被删除的

        if (!StringUtils.isEmpty(permissionParm.getUserId())) {
            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(permissionParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();
            //如果不是超级管理员
            if (!returnTenantIdView.isSuperAdmin()) {

                if (!CollectionUtils.isEmpty(tenantIds) && tenantIds.size() > 0 ) {
                    criteria.andTenantIdIn(tenantIds);
                } else {
                    //没有关联租户，返回空
                    PageHelper.startPage(permissionParm.getPageNumber(), permissionParm.getPageSize());
                    return  new PageInfo<Permission>(null);
                }
            }
        }
        if (!StringUtils.isEmpty(permissionParm.getSearchName())) {
            criteria.andNameLike("%" + permissionParm.getSearchName() + "%");
        }

//        if (!ObjectUtils.isEmpty(permissionParm.getApplicationId())) {
//            permissionCriteria.createCriteria().andApplicationIdEqualTo(permissionParm.getApplicationId());
//        }
//        if (!ObjectUtils.isEmpty(permissionParm.getTenantId())) {
//            permissionCriteria.createCriteria().andTenantIdEqualTo(permissionParm.getTenantId());
//        }

        PageHelper.startPage(permissionParm.getPageNumber(), permissionParm.getPageSize());
        List<Permission> permissions = this.selectByCriteria(permissionCriteria);
        PageInfo<Permission> permissionPageInfo = new PageInfo<Permission>(permissions);

        return permissionPageInfo;
    }

    @Override
    public PageUtil<Permission> queryPermissionsByParms(PermissionParm permissionParm) {
        Permission permission = permissionParm.getPermission();

        PermissionCriteria permissionCriteria = new PermissionCriteria();
        PermissionCriteria.Criteria criteria = permissionCriteria.createCriteria();
        criteria.andStatusEqualTo(1); //查询没被删除的

        if (!StringUtils.isEmpty(permissionParm.getUserId())) {
            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(permissionParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();

            //如果不是超级管理员
            if (!returnTenantIdView.isSuperAdmin()) {

                //change my ming
                tenantIds =  commonService.dealTenantIdsIfGarnet(permissionParm.getUserId(),tenantIds);

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

//        if (!ObjectUtils.isEmpty(permissionParm.getApplicationId())) {
//            permissionCriteria.createCriteria().andApplicationIdEqualTo(permissionParm.getApplicationId());
//        }
//        if (!ObjectUtils.isEmpty(permissionParm.getTenantId())) {
//            permissionCriteria.createCriteria().andTenantIdEqualTo(permissionParm.getTenantId());
//        }

        PageUtil result = new PageUtil(this.selectByCriteria(permissionCriteria), (int)this.countByCriteria(permissionCriteria) ,permissionParm.getPageSize(), permissionParm.getPageNumber());

        return result;
    }

    @Override
    public void updateStatusById(Permission permission) {

        Long currentTime = new Date().getTime();
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
}