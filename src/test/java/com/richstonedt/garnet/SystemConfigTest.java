package com.richstonedt.garnet;

import com.richstonedt.garnet.model.SystemConfig;
import com.richstonedt.garnet.model.view.SystemConfigView;
import com.richstonedt.garnet.service.SystemConfigService;
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
public class SystemConfigTest {
    @Autowired
    private SystemConfigService systemConfigService;

    @Test
    public void test1InsertSystemConfig() {
        SystemConfigView systemConfigView = new SystemConfigView();
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setParameter("mode");
        systemConfig.setValue("paas");
        systemConfigView.setSystemConfig(systemConfig);
        Long id = systemConfigService.insertSystemConfig(systemConfigView);

        SystemConfig systemConfig1 = systemConfigService.selectByPrimaryKey(id);
        Assert.assertEquals(systemConfig1.getValue(), "paas");
        systemConfigService.deleteByPrimaryKey(id);
    }

    @Test
    public void test2SelectSystemConfig() {
        SystemConfig systemConfig = systemConfigService.selectSystemConfigByParam("mode");
        Assert.assertEquals(systemConfig.getValue(), "all");
    }

}
