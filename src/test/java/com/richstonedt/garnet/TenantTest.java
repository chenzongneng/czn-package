/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.parm.TenantParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.model.view.UserView;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.TenantService;
import com.richstonedt.garnet.service.UserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TenantTest {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    private Long applicationId;

    private Long userId;

    @Before
    public void inittestData() {
        ApplicationView applicationView = new ApplicationView();
        Application application = new Application();
        application.setName("test_tenant_application");
        application.setAppCode("test_tenant_application");
        application.setServiceMode("saas");
        applicationView.setApplication(application);
        Long applicationId = applicationService.insertApplication(applicationView);
        this.applicationId = applicationId;

        UserView userView = new UserView();
        User user = new User();
        user.setBelongToGarnet("N");
        user.setUserName("test_tenant_user");
        userView.setUser(user);
        userView.setPassword("test_tenant_user");
        long userId = userService.insertUser(userView);
        this.userId = userId;
    }

    @After
    public void dealInitTestData() {
        applicationService.deleteByPrimaryKey(applicationId);
        UserView userView = new UserView();
        User user = new User();
        user.setId(userId);
        userView.setUser(user);
        userService.deleteUser(userView);
    }

    @Test
    public void contextLoads() {}

    @Test
    public void test1QueryTenantsByParams() {
        //查询租户列表
        TenantParm tenantParm = new TenantParm();
        tenantParm.setUserId(GarnetContants.GARNET_USER_ID);
        tenantParm.setApplicationId(1L);
        tenantParm.setPageNumber(1);
        tenantParm.setPageSize(10);
        tenantParm.setMode("all");
        PageUtil pageUtil = tenantService.queryTenantssByParms(tenantParm);

        Assert.assertEquals(pageUtil.getList().size(), 1);
    }

    @Test
    public void test2InsertTenant() {
        //测试新增
        Tenant tenant = new Tenant();
        tenant.setName("test");
        tenant.setServiceMode("saas");
        tenant.setDescription("test tenant");
        TenantView tenantView = new TenantView();
        tenantView.setAppIds(String.valueOf(applicationId));
        tenantView.setTenant(tenant);
        long id = tenantService.insertTenant(tenantView);
        Assert.assertNotNull(id);
    }

    @Test
    public void test3UpdateTenant() {
        User user = userService.selectByPrimaryKey(userId);
        String userName = user.getUserName();

        //测试修改
        TenantCriteria tenantCriteria = new TenantCriteria();
        tenantCriteria.createCriteria().andNameEqualTo("test");
        List<Tenant> tenantList = tenantService.selectByCriteria(tenantCriteria);
        Assert.assertEquals(tenantList.size(), 1);

        Tenant tenant = new Tenant();
        tenant.setName("test_update");
        tenant.setId(tenantList.get(0).getId());
        tenant.setServiceMode("paas");
        tenant.setDescription("test tenant update");
        TenantView tenantView = new TenantView();
        tenantView.setTenant(tenant);
        tenantView.setUserNames(userName);
        tenantService.updateTenant(tenantView);
        TenantCriteria tenantCriteria1 = new TenantCriteria();
        tenantCriteria1.createCriteria().andNameEqualTo("test_update");
        List<Tenant> tenants = tenantService.selectByCriteria(tenantCriteria1);
        Assert.assertEquals(tenants.size(), 1);
    }

    @Test
    public void test4DeleteTenant() {
        //测试删除
        TenantCriteria tenantCriteria = new TenantCriteria();
        tenantCriteria.createCriteria().andNameEqualTo("test_update");
        List<Tenant> tenantList = tenantService.selectByCriteria(tenantCriteria);
        Assert.assertEquals(tenantList.size(), 1);
        Tenant tenant = tenantList.get(0);
        tenantService.updateStatusById(tenant);

        TenantCriteria tenantCriteria1 = new TenantCriteria();
        tenantCriteria1.createCriteria().andNameEqualTo("test_update");
        List<Tenant> tenantList1 = tenantService.selectByCriteria(tenantCriteria1);
        Assert.assertEquals(tenantList1.size(), 1);
        int status = tenantList1.get(0).getStatus();
        Assert.assertEquals(status, 0);

        tenantService.deleteTenant(tenantList.get(0).getId());
    }

    @Test
    public void testGetTenantWithApplication() {
        Tenant tenant = new Tenant();
        tenant.setName("test_getTenantWithApplication");
        TenantView tenantView = new TenantView();
        tenantView.setAppIds(String.valueOf(applicationId));
        tenantView.setTenant(tenant);
        long id = tenantService.insertTenant(tenantView);
        TenantView tenantView1 = tenantService.getTenantWithApplication(id);
        String tenantName = tenantView1.getTenant().getName();
        Assert.assertEquals(tenantName, "test_getTenantWithApplication");
        int size = tenantView1.getAppIdList().size();
        Assert.assertEquals(size, 1);
        tenantService.deleteTenant(id);
    }

    @Test
    public void testGetRelatedUserNamesByTenantId() {
        List<String> userNames = tenantService.getRelatedUserNamesByTenantId(1L);
        int size = userNames.size();
        Assert.assertEquals(size, 1);
    }

    @Test
    public void testGetApplicationIds() {
        List<Long> applicationIds =  tenantService.getApplicationIds();
        int size = applicationIds.size();
        Assert.assertEquals(size, 1);
    }

}
