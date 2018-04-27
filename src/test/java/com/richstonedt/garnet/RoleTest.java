package com.richstonedt.garnet;


import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.parm.RoleParm;
import com.richstonedt.garnet.model.view.*;
import com.richstonedt.garnet.service.*;
import org.hibernate.validator.internal.xml.GroupsType;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PermissionService permissionService;

    private Long tenantId;

    private Long applicationId;

    private Long groupId;

    private Long permissionId;

    @Before
    public void inittestData() {
        com.richstonedt.garnet.model.Application application = new Application();
        ApplicationView applicationView = new ApplicationView();
        application.setName("test_role_appliction");
        application.setAppCode("test_role_appliction");
        applicationView.setApplication(application);
        Long applicationId = applicationService.insertApplication(applicationView);
        this.applicationId = applicationId;

        Tenant tenant = new Tenant();
        TenantView tenantView = new TenantView();
        tenant.setName("test_role_tenant");
        tenant.setDescription("test role with tenant");
        tenantView.setTenant(tenant);
        Long tenantId = tenantService.insertTenant(tenantView);
        this.tenantId = tenantId;

        GroupView groupView = new GroupView();
        Group group = new Group();
        group.setName("test_role_group");
        group.setTenantId(tenantId);
        group.setApplicationId(applicationId);
        groupView.setGroup(group);
        Long groupId = groupService.insertGroup(groupView);
        this.groupId = groupId;

        PermissionView permissionView = new PermissionView();
        Permission permission = new Permission();
        permission.setName("test_role_permission");
        permission.setTenantId(tenantId);
        permission.setApplicationId(applicationId);
        permission.setAction("edit");
        permission.setResourcePathWildcard("test%");
        permissionView.setPermission(permission);
        Long permissonId = permissionService.insertPermission(permissionView);
        this.permissionId = permissonId;
    }

    @After
    public void dealInitTestData() {
        tenantService.deleteByPrimaryKey(tenantId);
        applicationService.deleteByPrimaryKey(applicationId);
        groupService.deleteGroup(groupId);
        permissionService.deleteByPrimaryKey(permissionId);
    }

    @Test
    public void contextLoads() {}

    @Test
    public void test1QueryRolesByParms() {
        //查询角色列表
        RoleParm roleParm = new RoleParm();
        roleParm.setPageNumber(1);
        roleParm.setPageSize(10);
        roleParm.setUserId(GarnetContants.GARNET_USER_ID);
        PageUtil pageUtil = roleService.queryRolesByParms(roleParm);

        Assert.assertEquals(pageUtil.getList().size(), 1);
    }

    @Test
    public void test2InsertRole() {

        RoleView roleView = new RoleView();
        Role role = new Role();
        role.setName("test_role");
        role.setApplicationId(applicationId);
        role.setTenantId(tenantId);
        roleView.setRole(role);

        List<Long> groupIds = new ArrayList<>();
        groupIds.add(groupId);
        roleView.setGroupIds(groupIds);
        List<Long> permissionIds = new ArrayList<>();
        permissionIds.add(permissionId);
        roleView.setPermissionIds(permissionIds);

        Long roleId = roleService.insertRole(roleView);

        Role role1 = roleService.selectByPrimaryKey(roleId);
        Assert.assertNotNull(role1);
    }

    @Test
    public void test3UpdateRole() {
        RoleCriteria roleCriteria = new RoleCriteria();
        roleCriteria.createCriteria().andNameEqualTo("test_role");
        List<Role> roles = roleService.selectByCriteria(roleCriteria);
        Assert.assertEquals(roles.size(), 1);

        Role role = roles.get(0);
        role.setName("test_role_update");
        RoleView roleView = new RoleView();
        roleView.setRole(role);

        List<Long> groupIds = new ArrayList<>();
        groupIds.add(groupId);
        roleView.setGroupIds(groupIds);
        List<Long> permissionIds = new ArrayList<>();
        permissionIds.add(permissionId);
        roleView.setPermissionIds(permissionIds);

        roleService.updateRole(roleView);
        Role role1 = roleService.selectByPrimaryKey(role.getId());

        Assert.assertEquals(role1.getName(), "test_role_update");
    }

    @Test
    public void test4DeleteRole() {
        RoleCriteria roleCriteria = new RoleCriteria();
        roleCriteria.createCriteria().andNameEqualTo("test_role_update");
        List<Role> roles = roleService.selectByCriteria(roleCriteria);
        Assert.assertEquals(roles.size(), 1);

        Role role = roles.get(0);
        roleService.updateStatusById(role);

        RoleCriteria roleCriteria1 = new RoleCriteria();
        roleCriteria1.createCriteria().andNameEqualTo("test_role_update");
        List<Role> roleList = roleService.selectByCriteria(roleCriteria1);
        Assert.assertEquals(roleList.size(), 1);
        int status = roleList.get(0).getStatus();
        Assert.assertEquals(status, 0);

        //删除新增的app、tenant、role
        roleService.deleteByPrimaryKey(roleList.get(0).getId());
    }

    @Test
    public void selectRoleWithGroupAndPermission() {

        RoleView roleView = roleService.selectRoleWithGroupAndPermission(1L);
        String name = roleView.getRole().getName();
        Assert.assertEquals(name, "超级角色");

        List<Long> groupIds = roleView.getGroupIds();
        int groupIdsSize = groupIds.size();
        List<Long> permissionIds = roleView.getGroupIds();
        int permissionsIdsSize = groupIds.size();
        Assert.assertEquals(groupIdsSize, 1);
        Assert.assertEquals(permissionsIdsSize, 1);

    }

}
