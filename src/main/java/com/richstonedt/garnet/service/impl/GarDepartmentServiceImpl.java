package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.dao.GarDepartmentDao;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.view.model.GarVMDepartment;
import com.richstonedt.garnet.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <b><code>GarDepartmentServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:52
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarDepartmentServiceImpl implements GarDepartmentService{

    /**
     * The Department dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarDepartmentDao departmentDao;

    /**
     * The User department service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserDeptService userDeptService;

    /**
     * The Application service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationService applicationService;

    /**
     * The Tenant service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTenantService tenantService;

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserService userService;

    /**
     * The Role service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarRoleService roleService;

    /**
     * The Role department service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarRoleDeptService roleDepartmentService;

    /**
     * Save.
     *
     * @param garDepartment the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarDepartment garDepartment) {
        if (garDepartment.getDepartmentId() == null) {
            garDepartment.setDepartmentId(IdGeneratorUtil.generateId());
        }
        departmentDao.save(garDepartment);
    }

    /**
     * Update.
     *
     * @param garDepartment the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarDepartment garDepartment) {
        departmentDao.update(garDepartment);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        departmentDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {

    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarDepartment queryObject(Long id) {
        return departmentDao.queryObject(id);
    }

    /**
     * Query objects list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarDepartment> queryObjects(Map<String, Object> params) {
        return departmentDao.queryObjects(params);
    }

    /**
     * Query total int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal(Map<String, Object> params) {
        return departmentDao.queryTotal(params);
    }


    /**
     * Query detp id list list.
     *
     * @param parentDepartmentId the parent department id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<Long> queryDepartmentIdList(Long parentDepartmentId) {
        return departmentDao.queryDepartmentIdList(parentDepartmentId);
    }

    /**
     * Gets user department list.
     * 获取该用户的部门列表
     *
     * @param userId the user id
     * @return the user department list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVMDepartment> getUserDepartmentList(Long userId) {
        GarUser user = userService.queryObject(userId);
//        GarDepartment garDepartment = departmentDao.getDepartmentByParentDepartmentId(getMinParentDepartmentId(userId));
//        Long departmentId = garDepartment == null ? 1 : garDepartment.getDepartmentId();
        String subDepartmentList = getSubDepartmentIdList(1L);
        List<GarDepartment> departments = departmentDao.queryDepartmentList(user.getTenantId(), subDepartmentList);
        List<GarVMDepartment> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(departments)) {
            for (GarDepartment department : departments) {
                result.add(convertDepartmentToVMDepartment(department));
            }
        }
        return result;
    }

    /**
     * Gets vm department by department id.
     *
     * @param departmentId the department id
     * @return the vm department by department id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarVMDepartment getVMDepartmentByDepartmentId(Long departmentId) {
        return convertDepartmentToVMDepartment(queryObject(departmentId));
    }

    /**
     * Save vm department.
     *
     * @param vmDepartment the vm department
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void saveVMDepartment(GarVMDepartment vmDepartment) {
        if (vmDepartment.getDepartmentId() == null) {
            vmDepartment.setDepartmentId(IdGeneratorUtil.generateId());
        }

        //保存该部门下的所有用户
        saveUserInDepartment(vmDepartment);

        //保存该部门下的所有角色
        saveRoleInDepartment(vmDepartment);

        save(vmDepartment);
    }

    /**
     * Update vm department.
     *
     * @param vmDepartment the vm department
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void updateVMDepartment(GarVMDepartment vmDepartment) {
        //更新该部门下的所有用户,先删除，在插入
        userDeptService.deleteUserDeptByDeptId(vmDepartment.getDepartmentId());
        saveUserInDepartment(vmDepartment);

        //更新该部门下的所有角色,先删除，在插入
        roleDepartmentService.deleteRoleDeptByDeptId(vmDepartment.getDepartmentId());
        saveRoleInDepartment(vmDepartment);

        update(vmDepartment);
    }

    /**
     * Delete vm department.
     *
     * @param departmentId the department id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteVMDepartment(Long departmentId) {
        //删除该部门下的所有用户
        userDeptService.deleteUserDeptByDeptId(departmentId);

        //删除该部门下的所有角色
        roleDepartmentService.deleteRoleDeptByDeptId(departmentId);

        deleteById(departmentId);
    }

    /**
     * Query department list by params list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVMDepartment> queryDepartmentListByParams(Map<String, Object> params) {
        List<GarDepartment> departmentList = departmentDao.queryObjects(params);
        List<GarVMDepartment> vmDepartmentList = new ArrayList<>(departmentList.size());
        for (GarDepartment department : departmentList) {
            GarVMDepartment vmDepartment = convertDepartmentToVMDepartment(department);
            vmDepartmentList.add(vmDepartment);
        }
        return vmDepartmentList;
    }

    /**
     * Query total menu by param int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotalMenuByParam(Map<String, Object> params) {
        return departmentDao.getTotalDepartmentByParam(params);
    }

    /**
     * Delete batch by department ids map.
     *
     * @param departmentIds the department ids
     * @return the map
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public Map<String, String> deleteBatchByDepartmentIds(List<Long> departmentIds) {
        Map<String, String> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("parentDepartmentIds", departmentIds);
        List<GarDepartment> departmentList = departmentDao.queryObjects(params);
        if (CollectionUtils.isEmpty(departmentList)) {
            departmentDao.deleteBatch(departmentIds);
        } else {
            result.put("status", "500");
            String message = "删除的部门中存在如下的子部门：" +
                    departmentList.parallelStream().map(GarDepartment::getName).collect(Collectors.joining("，"));
            result.put("message", message);
        }
        return result;
    }

    /**
     * Gets sub department id list.
     *
     * @param departmentId the department id
     * @return the sub department id list
     * @since garnet-core-be-fe 0.1.0
     */
    private String getSubDepartmentIdList(Long departmentId) {
        //部门及子部门ID列表
        List<Long> departmentIdList = new ArrayList<>();
        //获取子部门ID
        List<Long> subIdList = queryDepartmentIdList(departmentId);
        getDepartmentTreeList(subIdList, departmentIdList);
        //添加本部门
        departmentIdList.add(departmentId);
        return StringUtils.join(departmentIdList, ",");
    }


    /**
     * Get department tree list.
     *
     * @param subIdList        the sub id list
     * @param departmentIdList the department id list
     * @since garnet-core-be-fe 0.1.0
     */
    private void getDepartmentTreeList(List<Long> subIdList, List<Long> departmentIdList) {
        for (Long departmentId : subIdList) {
            List<Long> list = queryDepartmentIdList(departmentId);
            if (list.size() > 0) {
                getDepartmentTreeList(list, departmentIdList);
            }
            departmentIdList.add(departmentId);
        }
    }

    /**
     * Convert department to vm department gar vm department.
     *
     * @param department the department
     * @return the gar vm department
     * @since garnet-core-be-fe 0.1.0
     */
    private GarVMDepartment convertDepartmentToVMDepartment(GarDepartment department) {
        GarVMDepartment vmDepartment = new GarVMDepartment();
        GarApplication application = applicationService.queryObject(department.getApplicationId());
        if (!ObjectUtils.isEmpty(application)) {
            vmDepartment.setAppName(application.getName());
        }
        GarTenant tenant = tenantService.queryObject(department.getTenantId());
        if (!ObjectUtils.isEmpty(tenant)) {
            vmDepartment.setTenantName(tenant.getName());
        }
        GarDepartment parentDepartment = departmentDao.getDepartmentByParentDepartmentId(department.getParentDepartmentId());
        if (parentDepartment != null) {
            vmDepartment.setParentName(parentDepartment.getName());
        }

        vmDepartment.setDepartmentId(department.getDepartmentId());
        vmDepartment.setParentDepartmentId(department.getParentDepartmentId());
        vmDepartment.setTenantId(department.getTenantId());
        vmDepartment.setApplicationId(department.getApplicationId());
        vmDepartment.setName(department.getName());
        vmDepartment.setOrderNum(department.getOrderNum());

        //获取该部门下的用户列表
        List<String> userNameList = new ArrayList<>();
        List<Long> userIdList = new ArrayList<>();
        List<GarUserDept> userDepartmentList = userDeptService.getUserDeptByDeptId(department.getDepartmentId());
        if (!CollectionUtils.isEmpty(userDepartmentList)) {
            for (GarUserDept userDepartment : userDepartmentList) {
                GarUser user = userService.queryObject(userDepartment.getUserId());
                if (!ObjectUtils.isEmpty(user)) {
                    userNameList.add(user.getUserName());
                    userIdList.add(userDepartment.getUserId());
                }
            }
        }
        vmDepartment.setUserNameList(userNameList);
        vmDepartment.setUserIdLList(userIdList);

        //获取该部门下的角色列表
        List<String> roleNameList = new ArrayList<>();
        List<Long> roleIdList = new ArrayList<>();
        List<GarRoleDept> roleDepartmentList = roleDepartmentService.getRoleDeptByDeptId(department.getDepartmentId());
        if (!CollectionUtils.isEmpty(roleDepartmentList)) {
            for (GarRoleDept roleDepartment : roleDepartmentList) {
                roleNameList.add(roleService.queryObject(roleDepartment.getRoleId()).getName());
                roleIdList.add(roleDepartment.getRoleId());
            }
        }
        vmDepartment.setRoleNameList(roleNameList);
        vmDepartment.setRoleIdLList(roleIdList);
        return vmDepartment;
    }

    /**
     * Save user in department.
     *
     * @param vmDepartment the vm department
     * @since garnet-core-be-fe 0.1.0
     */
    private void saveUserInDepartment(GarVMDepartment vmDepartment) {
        List<Long> userIdList = GarnetRsUtil.parseStringToList(vmDepartment.getUserIds());
        if (!CollectionUtils.isEmpty(userIdList)) {
            for (Long userId : userIdList) {
                GarUserDept userDepartment = new GarUserDept();
                userDepartment.setUserId(userId);
                userDepartment.setDepartmentId(vmDepartment.getDepartmentId());
                userDeptService.save(userDepartment);
            }
        }
    }

    /**
     * Save role in department.
     *
     * @param vmDepartment the vm department
     * @since garnet-core-be-fe 0.1.0
     */
    private void saveRoleInDepartment(GarVMDepartment vmDepartment) {
        List<Long> roleIdList = GarnetRsUtil.parseStringToList(vmDepartment.getRoleIds());
        if (!CollectionUtils.isEmpty(roleIdList)) {
            for (Long roleId : roleIdList) {
                GarRoleDept roleDepartment = new GarRoleDept();
                roleDepartment.setRoleId(roleId);
                roleDepartment.setDepartmentId(vmDepartment.getDepartmentId());
                roleDepartmentService.save(roleDepartment);
            }
        }
    }

    /**
     * The Line.
     * 存该部门一直到顶级部门这条路线
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private String line = "";

//    /**
//     * Gets Min Parent Department Id.
//     * 找到同时包含所有部门的最小父部门
//     * 1. 通过 userId 找到用户的所有部门
//     * 2. 找到每条该部门到顶级部门的这条线
//     * 3. 反转这条线，从顶级部门到部门
//     * 4. 随便取一条线（默认第一条）找到这些线中最长的相同段
//     * (如果都在同一部门，那么其中一条线必然包括另外一条线，如果选择长的线，那么将会数组越界，此时上一个点即为该点；
//     * 如果选择短的线，那么程序最终返回该线的最后一个点就是该点。)
//     * 5. 那么那个点就是这些部门的最小父部门
//     *
//     * @param userId the user id
//     * @return the max department id
//     * @since garnet-core-be-fe 0.1.0
//     */
//    private Long getMinParentDepartmentId(Long userId) {
//        List<GarUserDept> userDepartmentList = userDeptService.getUserDeptByUserId(userId);
//        List<String> departmentLines = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(userDepartmentList)) {
//            if (userDepartmentList.size() == 1) {
//                return queryObject(userDepartmentList.get(0).getDepartmentId()).getParentDepartmentId();
//            }
//            for (GarUserDept userDepartment : userDepartmentList) {
//                GarDepartment department = queryObject(userDepartment.getDepartmentId());
//                if (!ObjectUtils.isEmpty(department)) {
//                    line = userDepartment.getDepartmentId().toString();
//                    getDepartmentLine(department.getParentDepartmentId());
//                    departmentLines.add(StringUtils.reverse(line));
//                }
//            }
//        }
//        List<String[]> listStr = new ArrayList<>();
//        for (String departmentLine : departmentLines) {
//            listStr.add(departmentLine.split(","));
//        }
//        for (int i = 0; i < listStr.get(0).length; i++) {
//            for (String[] strArray : listStr) {
//                try {
//                    if (!strArray[i].equals(listStr.get(0)[i])) {
//                        return Long.valueOf(listStr.get(0)[i - 1]);
//                    }
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    return Long.valueOf(listStr.get(0)[i - 1]);
//                }
//            }
//        }
//        return Long.valueOf(listStr.get(0)[listStr.get(0).length - 1]);
//    }

//    /**
//     * Gets department line.
//     * 通过递归找到这条线
//     *
//     * @param parentDepartmentId the parent department id
//     * @since garnet-core-be-fe 0.1.0
//     */
//    private void getDepartmentLine(Long parentDepartmentId) {
//        if (ObjectUtils.isEmpty(parentDepartmentId)) {
//            return;
//        }
//        line += "," + parentDepartmentId.toString();
//        if (parentDepartmentId == 0L) {
//            return;
//        }
//        getDepartmentLine(departmentDao.getDepartmentByParentDepartmentId(parentDepartmentId).getParentDepartmentId());
//    }
}

