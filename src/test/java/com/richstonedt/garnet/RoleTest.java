package com.richstonedt.garnet;


import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.parm.RoleParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.model.view.RoleView;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.RoleService;
import com.richstonedt.garnet.service.TenantService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

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
        com.richstonedt.garnet.model.Application application = new Application();
        ApplicationView applicationView = new ApplicationView();
        application.setName("test_role_appliction");
        application.setAppCode("test_role_appliction");
        applicationView.setApplication(application);
        Long applictionId = applicationService.insertApplication(applicationView);

        Tenant tenant = new Tenant();
        TenantView tenantView = new TenantView();
        tenant.setName("test_role_tenant");
        tenant.setDescription("test role with tenant");
        tenantView.setTenant(tenant);
        Long tenantId = tenantService.insertTenant(tenantView);
        RoleView roleView = new RoleView();
        Role role = new Role();
        role.setName("test_role");
        role.setApplicationId(applictionId);
        role.setTenantId(tenantId);
        roleView.setRole(role);

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

        TenantCriteria tenantCriteria = new TenantCriteria();
        tenantCriteria.createCriteria().andNameEqualTo("test_role_tenant");
        List<Tenant> tenants = tenantService.selectByCriteria(tenantCriteria);
        tenantService.deleteByPrimaryKey(tenants.get(0).getId());

        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo("test_role_appliction");
        List<Application> applications = applicationService.selectByCriteria(applicationCriteria);
        applicationService.deleteByPrimaryKey(applications.get(0).getId());
    }

}
