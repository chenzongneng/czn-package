package com.richstonedt.garnet;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.model.criteria.RouterGroupCriteria;
import com.richstonedt.garnet.model.parm.RouterGroupParm;
import com.richstonedt.garnet.model.view.*;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.RouterGroupService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RouterGroupTest {

    @Autowired
    private RouterGroupService routerGroupService;

    @Autowired
    private ApplicationService applicationService;

    private Long applicationId1;

    private Long applicationId2;

    private String appCode1;

    private String appCode2;

    @Before
    public void inittestData() {
        com.richstonedt.garnet.model.Application application = new Application();
        ApplicationView applicationView = new ApplicationView();
        application.setName("test_routerGroup_appliction1");
        application.setAppCode("test_routerGroup_appliction1");
        applicationView.setApplication(application);
        Long applictionId1 = applicationService.insertApplication(applicationView);

        application.setName("test_routerGroup_appliction2");
        application.setAppCode("test_routerGroup_appliction2");
        applicationView.setApplication(application);
        Long applictionId2 = applicationService.insertApplication(applicationView);

        this.applicationId1 = applictionId1;
        this.applicationId2 = applictionId2;
        this.appCode1 = "test_routerGroup_appliction1";
        this.appCode2 = "test_routerGroup_appliction2";

        List<String> appCodeList = new ArrayList<>();
        appCodeList.add(this.appCode1);
        appCodeList.add(this.appCode2);
        RouterGroup routerGroup = new RouterGroup();
        RouterGroupView routerGroupView = new RouterGroupView();
        routerGroupView.setAppCodeList(appCodeList);
        routerGroup.setGroupName("test_routerGroup");
        routerGroupView.setRouterGroup(routerGroup);
        routerGroupService.insertRouterGroup(routerGroupView);

    }

    @After
    public void dealInitTestData() {
        applicationService.deleteByPrimaryKey(applicationId1);
        applicationService.deleteByPrimaryKey(applicationId2);

        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andGroupNameEqualTo("test_routerGroup");
        routerGroupService.deleteByCriteria(routerGroupCriteria);
    }

    @Test
    public void contextLoads() {}

    @Test
    public void test1QueryRouterGroupByParms() {
        //查询应用组列表
        RouterGroupParm routerGroupParm = new RouterGroupParm();
        routerGroupParm.setPageNumber(1);
        routerGroupParm.setPageSize(10);
        routerGroupParm.setUserId(GarnetContants.GARNET_USER_ID);
        PageUtil pageUtil = routerGroupService.queryRouterGroupByParms(routerGroupParm);

        Assert.assertEquals(pageUtil.getList().size(), 2); //返回的结果是去重后的
    }

    @Test
    public void test2InsertRouterGroup() {
        List<String> appCodeList = new ArrayList<>();
        appCodeList.add(this.appCode1);
        appCodeList.add(this.appCode2);
        RouterGroup routerGroup = new RouterGroup();
        RouterGroupView routerGroupView = new RouterGroupView();
        routerGroupView.setAppCodeList(appCodeList);
        routerGroup.setGroupName("test_routerGroup_insert");
        routerGroupView.setRouterGroup(routerGroup);
        routerGroupService.insertRouterGroup(routerGroupView);

        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andGroupNameEqualTo("test_routerGroup_insert");
        List<RouterGroup> routerGroups = routerGroupService.selectByCriteria(routerGroupCriteria);
        Assert.assertEquals(routerGroups.size(), 2);

        routerGroupService.deleteByCriteria(routerGroupCriteria);
        List<RouterGroup> routerGroupList = routerGroupService.selectByCriteria(routerGroupCriteria);
        Assert.assertEquals(routerGroupList.size(), 0);
    }

    @Test
    public void test3UpdateRouterGroup() {
        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andGroupNameEqualTo("test_routerGroup");
        List<RouterGroup> routerGroups = routerGroupService.selectByCriteria(routerGroupCriteria);
        Assert.assertEquals(routerGroups.size(), 2);
        List<String> appCodes = new ArrayList<>();
        for (RouterGroup routerGroup : routerGroups) {
            appCodes.add(routerGroup.getAppCode());
        }
        RouterGroupView routerGroupView = new RouterGroupView();
        routerGroupView.setAppCodeList(appCodes);
        RouterGroup routerGroup = routerGroups.get(0);
        routerGroup.setGroupName("test_routerGroup_update");
        routerGroupView.setRouterGroup(routerGroup);

        routerGroupService.updateRouterGroup(routerGroupView);

        RouterGroupCriteria routerGroupCriteria1 = new RouterGroupCriteria();
        routerGroupCriteria1.createCriteria().andGroupNameEqualTo("test_routerGroup_update");
        List<RouterGroup> routerGroupList = routerGroupService.selectByCriteria(routerGroupCriteria1);
        Assert.assertEquals(routerGroupList.size(), 2);

        routerGroupService.deleteByCriteria(routerGroupCriteria1);
    }

    @Test
    public void test4DeleteRouterGroup() {
        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andGroupNameEqualTo("test_routerGroup");
        List<RouterGroup> routerGroups = routerGroupService.selectByCriteria(routerGroupCriteria);
        Assert.assertEquals(routerGroups.size(), 2);
        List<String> appCodes = new ArrayList<>();
        for (RouterGroup routerGroup : routerGroups) {
            appCodes.add(routerGroup.getAppCode());
        }
        RouterGroupView routerGroupView = new RouterGroupView();
        routerGroupView.setAppCodeList(appCodes);
        RouterGroup routerGroup = routerGroups.get(0);
        routerGroupView.setRouterGroup(routerGroup);

        routerGroupService.deleteRouterGroup(routerGroupView);

        List<RouterGroup> routerGroupList = routerGroupService.selectByCriteria(routerGroupCriteria);
        Assert.assertEquals(routerGroupList.size(), 0);
    }

}
