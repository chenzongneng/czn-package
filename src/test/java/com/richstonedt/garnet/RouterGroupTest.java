package com.richstonedt.garnet;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.parm.RouterGroupParm;
import com.richstonedt.garnet.service.RouterGroupService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouterGroupTest {

    @Autowired
    private RouterGroupService routerGroupService;

    @Test
    public void contextLoads() {}

    @Test
    public void testQueryRouterGroupByParms() {
        //查询应用组列表
        RouterGroupParm routerGroupParm = new RouterGroupParm();
        routerGroupParm.setPageNumber(1);
        routerGroupParm.setPageSize(10);
        routerGroupParm.setUserId(GarnetContants.GARNET_USER_ID);
        PageUtil pageUtil = routerGroupService.queryRouterGroupByParms(routerGroupParm);

        Assert.assertEquals(pageUtil.getList().size(), 1);
    }
}
