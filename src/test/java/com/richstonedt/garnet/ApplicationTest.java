package com.richstonedt.garnet;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.parm.ApplicationParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.service.ApplicationService;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//按方法的字典序执行
public class ApplicationTest {

    @Autowired
    private ApplicationService applicationService;

    @Test
    public void contextLoads() {}

    @Test
    public void test1QueryApplicationsByParms() {
        //查询应用列表
        ApplicationParm applicationParm = new ApplicationParm();
        applicationParm.setUserId(GarnetContants.GARNET_USER_ID);
        applicationParm.setPageNumber(1);
        applicationParm.setPageSize(10);

        PageUtil pageUtil = applicationService.queryApplicationsByParms(applicationParm);

        Assert.assertEquals(pageUtil.getList().size(), 1);
    }

    @Test
    public void test2InsertApplication() {
        //测试新增
        Application application = new Application();
        application.setName("test");
        application.setAppCode("test");
        application.setCompany("garnet");
        ApplicationView applicationView = new ApplicationView();
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
}
