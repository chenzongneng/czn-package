package com.richstonedt.garnet;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.parm.ApplicationParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.TenantService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//按方法的字典序执行
public class ApplicationTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TenantService tenantService;

    private Long tenantId;
    private Long applicationId;

    @Before
    public void inittestData() {
        TenantView tenantView = new TenantView();
        Tenant tenant = new Tenant();
        tenant.setName("test_application_tenant");
        tenant.setServiceMode("saas");
        tenantView.setTenant(tenant);
        Long tenantId = tenantService.insertTenant(tenantView);
        this.tenantId = tenantId;

        ApplicationView applicationView = new ApplicationView();
        Application application = new Application();
        application.setName("test_application_init");
        application.setAppCode("test_application_init");
        application.setServiceMode("saas");
        applicationView.setApplication(application);
        applicationView.setTenantIds(String.valueOf(tenantId));
        Long applicationId = applicationService.insertApplication(applicationView);
        this.applicationId = applicationId;
    }

    @After
    public void dealInitTestData() {
        tenantService.deleteByPrimaryKey(tenantId);
        applicationService.deleteApplication(applicationId);
    }


    @Test
    public void contextLoads() {}

    @Test
    public void test1QueryApplicationsByParms() {
        //查询应用列表
        ApplicationParm applicationParm = new ApplicationParm();
        applicationParm.setUserId(GarnetContants.GARNET_USER_ID);
        applicationParm.setPageNumber(1);
        applicationParm.setPageSize(10);
        applicationParm.setMode("all");

        PageUtil pageUtil = applicationService.queryApplicationsByParms(applicationParm);

        Assert.assertEquals(pageUtil.getList().size(), 2);
    }

    @Test
    public void test2InsertApplication() {
        //测试新增
        Application application = new Application();
        application.setName("test");
        application.setAppCode("test");
        application.setCompany("garnet");
        application.setServiceMode("paas");
        ApplicationView applicationView = new ApplicationView();
        applicationView.setTenantIds(String.valueOf(tenantId));
        applicationView.setApplication(application);

        applicationService.insertApplication(applicationView);

        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo("test");
        List<Application> applications = applicationService.selectByCriteria(applicationCriteria);

        Assert.assertEquals(applications.size(), 1);
    }

    @Test
    public void test3UpdateApplication() {
        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo("test");

        Application application1 = applicationService.selectSingleByCriteria(applicationCriteria);
        //测试修改
        Application application = new Application();
        application.setName("test_update");
        application.setAppCode("test_update");
        application.setCompany("garnet");
        application.setId(application1.getId());
        application.setServiceMode("saas");
        ApplicationView applicationView = new ApplicationView();
        applicationView.setApplication(application);

        applicationService.updateApplication(applicationView);

        ApplicationCriteria applicationCriteria1 = new ApplicationCriteria();
        applicationCriteria1.createCriteria().andAppCodeEqualTo("test_update");
        List<Application> applications = applicationService.selectByCriteria(applicationCriteria1);

        Assert.assertEquals(applications.size(), 1);
        Assert.assertEquals(applications.get(0).getName(), "test_update");
    }

    @Test
    public void test4DeleteAppliction() {
        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo("test_update");
        Application application = applicationService.selectSingleByCriteria(applicationCriteria);

        applicationService.updateStatusById(application);

        ApplicationCriteria applicationCriteria1 = new ApplicationCriteria();
        applicationCriteria1.createCriteria().andAppCodeEqualTo("test_update");
        List<Application> applications = applicationService.selectByCriteria(applicationCriteria1);
        Assert.assertEquals(applications.size(), 1);
        int status = applications.get(0).getStatus();
        Assert.assertEquals(status, 0);

        applicationService.deleteByPrimaryKey(application.getId());
    }

    @Test
    public void getApplication() {
        ApplicationView applicationView = applicationService.getApplicationWithTenant(applicationId);
        int size = applicationView.getTenantNameList().size();
        Assert.assertEquals(size, 1);
    }

    @Test
    public void getApplicatinsWithoutRouterGroup() {
        ApplicationParm applicationParm = new ApplicationParm();
        applicationParm.setRouterGroupId(1L);
        applicationParm.setUserId(1L);
        List<Application> applicationList = applicationService.getApplicatinsWithoutRouterGroup(applicationParm);
        int size = applicationList.size();
        Assert.assertEquals(size, 2);
    }
}
