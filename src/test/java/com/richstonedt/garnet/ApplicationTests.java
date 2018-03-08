/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.parm.ApplicationParm;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.TenantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


	@Autowired
	private ApplicationService applicationService;

	@Test
	public void contextLoads() {




	}

	@Test
	public void testQueryApplication(){

		ApplicationParm applicationParm = new ApplicationParm();

		applicationParm.setPageNumber(1);
		applicationParm.setPageSize(10);
		applicationParm.setUserId(1519893662L);
		applicationParm.setTenantId(1519893064L);
		PageUtil pageUtil = applicationService.queryApplicationsByParms(applicationParm);
		System.out.println("pageUtil size: "+pageUtil.getList().size());
	}



}
