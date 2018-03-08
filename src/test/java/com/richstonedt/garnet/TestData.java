package com.richstonedt.garnet;

import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.view.*;
import com.richstonedt.garnet.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestData {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResourceService resourceService;


    @Test
    public void testInsertTenant(){

        //创建一个租户
        Tenant tenant = new Tenant();
        tenant.setName("Garnet权限系统-租户");
        TenantView tenantView = new TenantView();
        tenantView.setTenant(tenant);

        Long tenantId = tenantService.insertTenant(tenantView);

        System.out.println("tenantId: " + tenantId );
    }

    @Test
    public void testInsertApplication(){

        Long tenantId = 1519893064L;

        //创建应用的时候，并且关联了租户
        Application application = new Application();
        application.setCompany("广州丰石科技");
        application.setName("garnet权限系统-应用");
        application.setAppCode("123456789");

        ApplicationView applicationView = new ApplicationView();

        ApplicationTenant applicationTenant = new ApplicationTenant();
        applicationTenant.setTenantId(tenantId);

        applicationView.setApplication(application);

        List<ApplicationTenant> applicationTenants = new ArrayList<ApplicationTenant>();
        applicationTenants.add(applicationTenant);
        applicationView.setApplicationTenants(applicationTenants);

        Long applicationId = applicationService.insertApplication(applicationView);

        System.out.println("applicationId: " + applicationId);
    }

    @Test
    public void testInsertGroup(){

        Long tenantId = 1519893064L;
        Long applicationId = 1519893176L;

        Group group = new Group();
        group.setApplicationId(applicationId);
        group.setTenantId(tenantId);
        group.setName("运维组");
        GroupView groupView = new GroupView();
        groupView.setGroup(group);

        Long groupId = groupService.insertGroup(groupView);

        System.out.println("groupId: " + groupId);

    }


    @Test
    public void testInsertRole(){

        Long tenantId = 1519893064L;
        Long applicationId = 1519893176L;
        Long groupId = 1519893247L;

        Role role = new Role();
        role.setApplicationId(applicationId);
        role.setTenantId(tenantId);
        role.setName("管理员");

        RoleView roleView = new RoleView();
        //选择关联的组
        GroupRole groupRole = new GroupRole();
        groupRole.setGroupId(groupId);
        roleView.setRole(role);

        List<GroupRole> groupRoles = new ArrayList<>();
        groupRoles.add(groupRole);
        roleView.setGroupRoles(groupRoles);

        Long roleId = roleService.insertRole(roleView);

        //1519893278
        System.out.println("roleId: " + roleId);

    }

    @Test
    public void testInsertUser() throws ParseException {

        Long tenantId = 1519893064L;
        Long applicationId = 1519893176L;
        Long groupId = 1519893247L;

        User user = new User();
        user.setUserName("admin");
        UserView userView = new UserView();
        userView.setUser(user);
        userView.setPassword("admin");
        userView.setExpiredDateTime(new Date().getTime());
        UserTenant userTenant = new UserTenant();
        userTenant.setTenantId(1519893064L);
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(1519893247L);

        List<UserTenant> userTenants = new ArrayList<>();
        userTenants.add(userTenant);

        List<GroupUser> groupUsers = new ArrayList<>();
        groupUsers.add(groupUser);

        userView.setGroupUsers(groupUsers);
        userView.setUserTenants(userTenants);

        Long userId = userService.insertUser(userView);

        //1519893278
        System.out.println("userId: " + userId);

    }







}
