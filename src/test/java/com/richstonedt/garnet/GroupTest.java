package com.richstonedt.garnet;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.parm.GroupParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.model.view.GroupView;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.GroupService;
import com.richstonedt.garnet.service.TenantService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        Application application = new Application();
        ApplicationView applicationView = new ApplicationView();
        application.setName("test_group_appliction");
        application.setAppCode("test_group_appliction");
        applicationView.setApplication(application);
        Long applictionId = applicationService.insertApplication(applicationView);

        Tenant tenant = new Tenant();
        TenantView tenantView = new TenantView();
        tenant.setName("test_group_tenant");
        tenant.setDescription("test group with tenant");
        tenantView.setTenant(tenant);
        Long tenantId = tenantService.insertTenant(tenantView);

        Group group = new Group();
        GroupView groupView = new GroupView();
        group.setName("test");
        group.setTenantId(tenantId);
        group.setApplicationId(applictionId);
        groupView.setGroup(group);

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

        //删除新增的应用和租户
        TenantCriteria tenantCriteria = new TenantCriteria();
        tenantCriteria.createCriteria().andNameEqualTo("test_group_tenant");
        List<Tenant> tenants = tenantService.selectByCriteria(tenantCriteria);
        tenantService.deleteByPrimaryKey(tenants.get(0).getId());

        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo("test_group_appliction");
        List<Application> applications = applicationService.selectByCriteria(applicationCriteria);
        applicationService.deleteByPrimaryKey(applications.get(0).getId());

        //删除group
        groupService.deleteByCriteria(groupCriteria1);
    }

}
