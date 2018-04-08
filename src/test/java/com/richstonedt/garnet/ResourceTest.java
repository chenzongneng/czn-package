package com.richstonedt.garnet;


import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.ResourceService;
import com.richstonedt.garnet.service.TenantService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourceTest {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TenantService tenantService;

    @Test
    public void contextLoads() {}

    @Test
    public void testQueryResourcesByParms() {
        //查询资源列表
        ResourceParm resourceParm = new ResourceParm();
        resourceParm.setPageNumber(1);
        resourceParm.setPageSize(1000);
        resourceParm.setUserId(GarnetContants.GARNET_USER_ID);
        PageUtil pageUtil = resourceService.queryResourcesByParms(resourceParm);

        Assert.assertEquals(pageUtil.getList().size(), 60);
    }
}
