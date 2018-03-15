package com.richstonedt.garnet.service;

import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Permission;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.criteria.PermissionCriteria;
import com.richstonedt.garnet.model.parm.PermissionParm;
import com.richstonedt.garnet.model.parm.PermissionResourceParm;
import com.richstonedt.garnet.model.view.PermissionView;

import java.util.List;

/**
 * The interface Permission service.
 */
public interface PermissionService extends BaseService<Permission, PermissionCriteria, Long> {

    /**
     * Insert permission.
     *
     * @param permissionView the permission view
     */
    public Long insertPermission(PermissionView permissionView);

    /**
     * Update perssion.
     *
     * @param permissionView the permission view
     */
    public void updatePerssion(PermissionView permissionView);

    /**
     * Delete perssion.
     *
     * @param permissionView the permission view
     */
    public void deletePerssion(PermissionView permissionView);

    /**
     * 根据permission_id获取所有的对应租户下对应applicaiton和对应user的所有资源
     *
     * @param permissionResourceParm the permission resource parm
     * @return list
     */
    public List<Resource> queryResourcesByPermissionResourceParm(PermissionResourceParm permissionResourceParm);

    /**
     * Query perssions by parms page info.
     *
     * @param permissionParm the permission parm
     * @return the page info
     */
    public PageInfo<Permission> queryPerssionsByParms(PermissionParm permissionParm);

    public PageUtil<Permission> queryPermissionsByParms(PermissionParm permissionParm);

    /**
     * 当删除时，更新status为0，即禁用
     */
    public void updateStatusById(Permission permission);

    public List<Permission> queryPermissionByTenantId(PermissionParm permissionParm);

}