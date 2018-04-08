package com.richstonedt.garnet;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.parm.ResourceDynamicPropertyParm;
import com.richstonedt.garnet.service.ResourceDynamicPropertyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceDynamicTest {

    @Autowired
    private ResourceDynamicPropertyService resourceDynamicPropertyService;

    @Test
    public void contextLoads() {}

    @Test
    public void testQueryResourceDynamicPropertiesByParms() {
        //查询去重后的动态资源配置列表
        ResourceDynamicPropertyParm resourceDynamicPropertyParm = new ResourceDynamicPropertyParm();
        resourceDynamicPropertyParm.setPageNumber(1);
        resourceDynamicPropertyParm.setPageSize(1000);
        resourceDynamicPropertyParm.setUserId(GarnetContants.GARNET_USER_ID);
        PageUtil pageUtil = resourceDynamicPropertyService.queryResourceDynamicPropertySByParms(resourceDynamicPropertyParm);

        Assert.assertEquals(pageUtil.getList().size(), 3);
    }

}
