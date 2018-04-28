package com.richstonedt.garnet;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.Permission;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.criteria.PermissionCriteria;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.parm.PermissionParm;
import com.richstonedt.garnet.model.parm.PermissionResourceParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.model.view.PermissionView;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.PermissionService;
import com.richstonedt.garnet.service.TenantService;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//按方法的字典序执行
public class PermissionTest {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TenantService tenantService;

    private Long applicationId;

    private Long tenantId;

    @Test
    public void contextLoads() {}

    @Before
    public void inittestData() {
        com.richstonedt.garnet.model.Application application = new Application();
        ApplicationView applicationView = new ApplicationView();
        application.setName("test_permission_appliction");
        application.setAppCode("test_permission_appliction");
        applicationView.setApplication(application);
        Long applicationId = applicationService.insertApplication(applicationView);

        Tenant tenant = new Tenant();
        TenantView tenantView = new TenantView();
        tenant.setName("test_permission_tenant");
        tenant.setDescription("test permission with tenant");
        tenantView.setTenant(tenant);
        Long tenantId = tenantService.insertTenant(tenantView);

        this.applicationId = applicationId;
        this.tenantId = tenantId;

    }

    @After
    public void dealInitTestData() {
        applicationService.deleteByPrimaryKey(applicationId);
        tenantService.deleteByPrimaryKey(tenantId);
    }

    @Test
    public void test1QueryPermissionsByParms() {
        //查询权限列表
        PermissionParm permissionParm = new PermissionParm();
        permissionParm.setPageNumber(1);
        permissionParm.setPageSize(10);
        permissionParm.setUserId(GarnetContants.GARNET_USER_ID);
        PageUtil pageUtil = permissionService.queryPermissionsByParms(permissionParm);

        Assert.assertEquals(pageUtil.getList().size(), 1);
    }

    @Test
    public void test2InsertPermission() {


        Permission permission = new Permission();
        PermissionView permissionView = new PermissionView();
        permission.setName("test_permission");
        permission.setResourcePathWildcard("test%");
        permission.setApplicationId(applicationId);
        permission.setTenantId(tenantId);
        permission.setAction("edit");
        permissionView.setPermission(permission);

        Long permissionId = permissionService.insertPermission(permissionView);

        Permission permission1 = permissionService.selectByPrimaryKey(permissionId);
        Assert.assertNotNull(permission1);
    }

    @Test
    public void test3UpdatePermission() {
        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andNameEqualTo("test_permission");
        List<Permission> permissions = permissionService.selectByCriteria(permissionCriteria);
        Assert.assertEquals(permissions.size(), 1);

        Permission permission = permissions.get(0);
        permission.setName("test_permission_update");
        PermissionView  permissionView = new PermissionView();
        permissionView.setPermission(permission);

        permissionService.updatePerssion(permissionView);

        Permission permission1 = permissionService.selectByPrimaryKey(permission.getId());

        Assert.assertNotNull(permission1.getName(), "test_permission_update");
    }

    @Test
    public void test4DeletePermission() {
        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andNameLike("test_permission_update");
        List<Permission> permissions = permissionService.selectByCriteria(permissionCriteria);
        Assert.assertEquals(permissions.size(), 1);

        Permission permission = permissions.get(0);
        permissionService.updateStatusById(permission);

        PermissionCriteria permissionCriteria1 = new PermissionCriteria();
        permissionCriteria1.createCriteria().andNameLike("test_permission_update");
        List<Permission> permissionList = permissionService.selectByCriteria(permissionCriteria1);
        Assert.assertEquals(permissionList.size(), 1);

        int status = permissionList.get(0).getStatus();
        Assert.assertEquals(status, 0);

        //真正删除测试permission及app、tenant，方便下次测试
        permissionService.deleteByPrimaryKey(permissionList.get(0).getId());

        TenantCriteria tenantCriteria = new TenantCriteria();
        tenantCriteria.createCriteria().andNameEqualTo("test_permission_tenant");
        List<Tenant> tenants = tenantService.selectByCriteria(tenantCriteria);
        tenantService.deleteByPrimaryKey(tenants.get(0).getId());

        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo("test_permission_appliction");
        List<Application> applications = applicationService.selectByCriteria(applicationCriteria);
        applicationService.deleteByPrimaryKey(applications.get(0).getId());
    }

    @Test
    public void testQueryPermissionByTenantId() {
        PermissionParm permissionParm = new PermissionParm();
        permissionParm.setTenantId(1L);
        List<Permission> permissionList = permissionService.queryPermissionByTenantId(permissionParm);
        int size = permissionList.size();
        Assert.assertEquals(size, 1);
    }

    @Test
    public void testQueryResourcesByPermissionResourceParm() {
        PermissionResourceParm permissionResourceParm = new PermissionResourceParm();
        List<Long> permissionIds = new ArrayList<>();
        permissionIds.add(1L);
        permissionResourceParm.setPermissionIds(permissionIds);

        List<Resource> resourceList = permissionService.queryResourcesByPermissionResourceParm(permissionResourceParm);
        int size = resourceList.size();

        Assert.assertEquals(size, 60);
    }
}
