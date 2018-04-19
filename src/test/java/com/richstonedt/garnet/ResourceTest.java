package com.richstonedt.garnet;


import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.model.view.ResourceDynamicPropertyView;
import com.richstonedt.garnet.model.view.ResourceView;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.ResourceDynamicPropertyService;
import com.richstonedt.garnet.service.ResourceService;
import com.richstonedt.garnet.service.TenantService;
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
public class ResourceTest {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ResourceDynamicPropertyService resourceDynamicPropertyService;

    private Long applicationId;

    private Long tenantId;

    private Long resourceId;


    @Before
    public void inittestData() {
        com.richstonedt.garnet.model.Application application = new Application();
        ApplicationView applicationView = new ApplicationView();
        application.setName("test_resource_appliction");
        application.setAppCode("test_resource_appliction");
        applicationView.setApplication(application);
        Long applictionId = applicationService.insertApplication(applicationView);

        Tenant tenant = new Tenant();
        TenantView tenantView = new TenantView();
        tenant.setName("test_resource_tenant");
        tenant.setDescription("test resource with tenant");
        tenantView.setTenant(tenant);
        Long tenantId = tenantService.insertTenant(tenantView);

        ResourceDynamicProperty resourceDynamicProperty = new ResourceDynamicProperty();
        ResourceDynamicPropertyView resourceDynamicPropertyView = new ResourceDynamicPropertyView();
        resourceDynamicProperty.setType("test_resource");
        resourceDynamicProperty.setDescription("true/false");
        resourceDynamicProperty.setFiledName("varchar00");
        resourceDynamicPropertyView.setResourceDynamicProperty(resourceDynamicProperty);
        List<ResourceDynamicProperty> resourceDynamicPropertyList = new ArrayList<>();
        resourceDynamicPropertyList.add(resourceDynamicProperty);
        resourceDynamicPropertyView.setResourceDynamicPropertyList(resourceDynamicPropertyList);
        resourceDynamicPropertyService.insertResourceDynamicProperty(resourceDynamicPropertyView);

        this.applicationId = applictionId;
        this.tenantId = tenantId;

        Resource resource = new Resource();
        ResourceView resourceView = new ResourceView();
        resource.setApplicationId(this.applicationId);
        resource.setTenantId(this.tenantId);
        resource.setType("test_resource");
        resource.setActions("test");
        resource.setName("test_resource");
        resource.setVarchar00("true");
        resource.setBoolean01(true);
        resourceView.setResource(resource);

        Long resourceId = resourceService.insertResource(resourceView);
        Resource resource1 = resourceService.selectByPrimaryKey(resourceId);
        this.resourceId = resourceId;
        Assert.assertNotNull(resource1);
    }

    @After
    public void dealInitTestData() {
        applicationService.deleteByPrimaryKey(applicationId);
        tenantService.deleteByPrimaryKey(tenantId);

        ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
        resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo("test_resource");
        resourceDynamicPropertyService.deleteByCriteria(resourceDynamicPropertyCriteria);

        if (!ObjectUtils.isEmpty(resourceId)) {
//            resourceService.deleteByPrimaryKey(resourceId);
        }
    }

    @Test
    public void contextLoads() {}

    @Test
    public void test1QueryResourcesByParms() {
        //查询资源列表
        ResourceParm resourceParm = new ResourceParm();
        resourceParm.setPageNumber(1);
        resourceParm.setPageSize(1000);
        resourceParm.setUserId(GarnetContants.GARNET_USER_ID);
        //PageUtil pageUtil = resourceService.queryResourcesByParms(resourceParm);

//        Assert.assertEquals(pageUtil.getList().size(), 61);
    }

    @Test
    public void test2InsertResource() {

        Resource resource = new Resource();
        ResourceView resourceView = new ResourceView();
        resource.setApplicationId(this.applicationId);
        resource.setTenantId(this.tenantId);
        resource.setType("test_resource");
        resource.setActions("test");
        resource.setName("test_resource_insert");
        resource.setVarchar00("true");
        resourceView.setResource(resource);

        Long resourceId = resourceService.insertResource(resourceView);
        Resource resource1 = resourceService.selectByPrimaryKey(resourceId);
        Assert.assertNotNull(resource1);
        resourceService.deleteByPrimaryKey(resourceId);
        Assert.assertNull(resourceService.selectByPrimaryKey(resourceId));
    }

    @Test
    public void test3updateResource() {
        Resource resource = resourceService.selectByPrimaryKey(resourceId);
        resource.setName("test_resource_update");
        resource.setBoolean01(null);
        ResourceView resourceView = new ResourceView();
        resourceView.setResource(resource);

        resourceService.updateResource(resourceView);

        ResourceCriteria resourceCriteria = new ResourceCriteria();
        resourceCriteria.createCriteria().andNameEqualTo("test_resource_update");
        List<Resource> resources = resourceService.selectByCriteria(resourceCriteria);
        Assert.assertNotNull(resources);

//        resourceService.deleteByCriteria(resourceCriteria);
//        List<Resource> resourceList = resourceService.selectByCriteria(resourceCriteria);
//
//        Assert.assertEquals(resourceList.size(), 0);
    }

    @Test
    public void test4DeleteResource() {
        Resource resource = resourceService.selectByPrimaryKey(resourceId);
        ResourceView resourceView = new ResourceView();
        resourceView.setResource(resource);
        resourceService.deleteResource(resourceView);
    }

    @Test
    public void testInsert() {
        Resource resource = new Resource();
        ResourceView resourceView = new ResourceView();
        resource.setApplicationId(this.applicationId);
        resource.setTenantId(this.tenantId);
        resource.setType("test_resource");
        resource.setActions("edit");
        resource.setName("test_resource_insert");
        resource.setVarchar00("true");
        resource.setBoolean01(null);
        resourceView.setResource(resource);

        Long resourceId = resourceService.insertResource(resourceView);
        Resource resource1 = resourceService.selectByPrimaryKey(resourceId);
        Assert.assertNotNull(resource1);
    }
}
