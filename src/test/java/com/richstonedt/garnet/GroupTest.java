package com.richstonedt.garnet;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.parm.GroupParm;
import com.richstonedt.garnet.model.view.*;
import com.richstonedt.garnet.service.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    private Long roleId;
    private Long userId;
    private Long applicationId;
    private Long tenantId;


    @Before
    public void inittestData() {
        TenantView tenantView = new TenantView();
        Tenant tenant = new Tenant();
        tenant.setName("test_group_tenant");
        tenant.setServiceMode("pass");
        tenantView.setTenant(tenant);
        Long tenantId = tenantService.insertTenant(tenantView);
        this.tenantId = tenantId;

        ApplicationView applicationView = new ApplicationView();
        Application application = new Application();
        application.setName("test_group_application");
        application.setAppCode("test_application_init");
        application.setServiceMode("pass");
        applicationView.setApplication(application);
        applicationView.setTenantIds(String.valueOf(tenantId));
        Long applicationId = applicationService.insertApplication(applicationView);
        this.applicationId = applicationId;

        RoleView roleView = new RoleView();
        Role role = new Role();
        role.setName("test_group_user");
        role.setStatus(1);
        roleView.setRole(role);
        Long roleId = roleService.insertRole(roleView);
        this.roleId = roleId;

        UserView userView = new UserView();
        User user = new User();
        user.setBelongToGarnet("N");
        user.setUserName("test_group_user");
        userView.setUser(user);
        Long userId = userService.insertUser(userView);
        this.userId = userId;
    }

    @After
    public void dealInitTestData() {
        tenantService.deleteByPrimaryKey(tenantId);
        applicationService.deleteApplication(applicationId);
        roleService.deleteByPrimaryKey(roleId);
        UserView userView = new UserView();
        User user = new User();
        user.setId(userId);
        userView.setUser(user);
        userService.deleteUser(userView);
    }

    @Test
    public void contextLoads() {}

    @Test
    public void test1QueryGroupsByParms() {
        //查询组列表
        GroupParm groupParm = new GroupParm();
        groupParm.setPageNumber(1);
        groupParm.setPageSize(10);
        groupParm.setUserId(GarnetContants.GARNET_USER_ID);

        PageUtil pageUtil = groupService.queryGroupsByParms(groupParm);

        Assert.assertEquals(pageUtil.getList().size(), 1);
    }

    @Test
    public void test2InsertGroupWithAppAndTenant() {

        Group group = new Group();
        GroupView groupView = new GroupView();
        group.setName("test");
        group.setTenantId(this.tenantId);
        group.setApplicationId(this.applicationId);
        groupView.setGroup(group);
        List userIds = new ArrayList();
        userIds.add(this.userId);
        groupView.setUserIds(userIds);
        List roleIds = new ArrayList();
        roleIds.add(roleId);
        groupView.setRoleIds(roleIds);

        Long groupId = groupService.insertGroup(groupView);
        Group group1 = groupService.selectByPrimaryKey(groupId);
        Assert.assertNotNull(group1);
    }

    @Test
    public void test3UpdateGroup() {
        GroupCriteria groupCriteria = new GroupCriteria();
        groupCriteria.createCriteria().andNameEqualTo("test");
        List<Group> groups = groupService.selectByCriteria(groupCriteria);
        Assert.assertEquals(groups.size(), 1);

        GroupView groupView = new GroupView();
        Group group = new Group();
        group.setId(groups.get(0).getId());
        group.setName("test_update");
        groupView.setGroup(group);
        List userIds = new ArrayList();
        userIds.add(this.userId);
        groupView.setUserIds(userIds);
        List roleIds = new ArrayList();
        roleIds.add(roleId);
        groupView.setRoleIds(roleIds);

        groupService.updateGroup(groupView);

        GroupCriteria groupCriteria1 = new GroupCriteria();
        groupCriteria1.createCriteria().andNameEqualTo("test_update");
        List<Group> groups1 = groupService.selectByCriteria(groupCriteria1);
        Assert.assertEquals(groups1.size(), 1);
    }

    @Test
    public void test4DeleteGroup() {
        GroupCriteria groupCriteria = new GroupCriteria();
        groupCriteria.createCriteria().andNameEqualTo("test_update");
        List<Group> groups = groupService.selectByCriteria(groupCriteria);
        Assert.assertEquals(groups.size(), 1);
        groupService.updateStatusById(groups.get(0));

        GroupCriteria groupCriteria1 = new GroupCriteria();
        groupCriteria1.createCriteria().andNameEqualTo("test_update");
        List<Group> groups1 = groupService.selectByCriteria(groupCriteria1);
        int status = groups1.get(0).getStatus();
        Assert.assertEquals(status, 0);

        //删除group
        groupService.deleteByCriteria(groupCriteria1);
    }

    @Test
    public void testGetGroupWithUserAndRole() {
        Group group = new Group();
        GroupView groupView = new GroupView();
        group.setName("test");
        group.setTenantId(this.tenantId);
        group.setApplicationId(this.applicationId);
        groupView.setGroup(group);
        List userIds = new ArrayList();
        userIds.add(this.userId);
        groupView.setUserIds(userIds);
        List roleIds = new ArrayList();
        roleIds.add(roleId);
        groupView.setRoleIds(roleIds);
        Long groupId = groupService.insertGroup(groupView);

        GroupView groupView1 = groupService.selectGroupWithUserAndRole(groupId);
        Assert.assertNotNull(groupView1);
        int roleIdSize = groupView1.getRoleIds().size();
        int userIdSize = groupView1.getUserIds().size();

        Assert.assertEquals(roleIdSize, 1);
        Assert.assertEquals(userIdSize, 1);

        groupService.deleteGroup(groupId);
    }

    @Test
    public void testQueryGroupsByTenantId() {
        GroupParm groupParm = new GroupParm();
        groupParm.setTenantId(1L);
        List<Group> groupList = groupService.queryGroupsByTenantId(groupParm);
        int size = groupList.size();
        Assert.assertEquals(size, 1);
    }

}
