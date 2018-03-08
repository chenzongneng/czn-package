/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.model.ApplicationTenant;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.UserTenant;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.service.TenantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TenantTests {

	@Autowired
	private TenantService tenantService;

	@Test
	public void contextLoads() {




	}

	@Test
	public void testInsertTenant(){

		Tenant tenant = new Tenant();
		tenant.setId(IdGeneratorUtil.generateId());
		tenant.setName("测试租户3");
		Long createTime = new Date().getTime();
		Long modifiedTime = new Date().getTime();
		System.out.println("tenant.getId():"+tenant.getId());
		tenant.setCreatedTime(createTime);
		tenant.setModifiedTime(modifiedTime);
		tenantService.insertSelective(tenant);


	}


	@Test
	public void testSearchExample(){

		TenantCriteria tenantCriteria = new TenantCriteria();
		PageHelper.startPage(2,10);

		List<Tenant> tenants = tenantService.selectByCriteria(tenantCriteria);

		PageInfo<Tenant> tenantPageInfo = new PageInfo<Tenant>(tenants);

		for (Tenant tenant:tenantPageInfo.getList()
			 ) {

			System.out.println("result: "+tenant.toString());

		}

	}

	@Test
	public void testInsertTenantWithApplicationUser(){

		Tenant tenant = new Tenant();
		tenant.setId(1519574674L);
		tenant.setName("测试租户5");
		Long createTime = new Date().getTime();
		Long modifiedTime = new Date().getTime();
		System.out.println("tenant.getId():"+tenant.getId());
		tenant.setCreatedTime(createTime);
		tenant.setModifiedTime(modifiedTime);

		UserTenant userTenant = new UserTenant();
		userTenant.setUserId(456L);

		ApplicationTenant applicationTenant = new ApplicationTenant();
		applicationTenant.setApplicationId(789L);

		List<UserTenant> userTenants = new ArrayList<UserTenant>();
		userTenants.add(userTenant);

		List<ApplicationTenant> applicationTenants = new ArrayList<ApplicationTenant>();
		applicationTenants.add(applicationTenant);

		TenantView tenantView = new TenantView();
		tenantView.setTenant(tenant);
		tenantView.setApplicationTenants(applicationTenants);
		tenantView.setUserTenants(userTenants);

		tenantService.updateTenant(tenantView);

	}


}
