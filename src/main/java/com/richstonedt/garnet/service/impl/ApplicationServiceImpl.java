package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.StringUtil;
import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.ApplicationMapper;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.ApplicationTenant;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.criteria.ApplicationTenantCriteria;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.parm.ApplicationParm;
import com.richstonedt.garnet.model.parm.TenantParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.ApplicationTenantService;
import com.richstonedt.garnet.service.TenantService;
import com.richstonedt.garnet.service.UserTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ApplicationServiceImpl extends BaseServiceImpl<Application, ApplicationCriteria, Long> implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApplicationTenantService applicationTenantService;

    @Autowired
    private UserTenantService userTenantService;

    @Autowired
    private TenantService tenantService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.applicationMapper;
    }

    @Override
    public Long insertApplication(ApplicationView applicationView) {

        Application application = applicationView.getApplication();
        application.setId(IdGeneratorUtil.generateId());
        Long currentTime = new Date().getTime();
        application.setCreatedTime(currentTime);
        application.setModifiedTime(currentTime);
        this.insertSelective(application);
        this.dealForgenKeyApplications(applicationView);

        return application.getId();

    }

    @Override
    public void updateApplication(ApplicationView applicationView) {

        Application application = applicationView.getApplication();

        Long currentTime = new Date().getTime();
        application.setModifiedTime(currentTime);
        this.updateByPrimaryKeySelective(application);

        this.dealForgenKeyApplications(applicationView);


    }

    /**
     * 处理applicaiton外键
     *
     * @param applicationView
     */
    private void dealForgenKeyApplications(ApplicationView applicationView) {

        //如果什么都没有动过直接submit为Null

        //把所有勾选的去掉会""

        //其余为正常选择

        if ("".equals(applicationView.getTenantIds())) {

            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();

            applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(applicationView.getApplication().getId());

            applicationTenantService.deleteByCriteria(applicationTenantCriteria);
        } else if (!StringUtil.isEmpty(applicationView.getTenantIds())) {

            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();

            applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(applicationView.getApplication().getId());

            applicationTenantService.deleteByCriteria(applicationTenantCriteria);

            for (String tenantId : applicationView.getTenantIds().split(",")
                    ) {
                ApplicationTenant applicationTenant = new ApplicationTenant();

                applicationTenant.setId(IdGeneratorUtil.generateId());

                //applicationId
                applicationTenant.setApplicationId(applicationView.getApplication().getId());
                applicationTenant.setTenantId(Long.parseLong(tenantId));
                applicationTenantService.insertSelective(applicationTenant);
            }

        }


    }

    @Override
    public void deleteApplication(Long applicationId) {

        Application application = this.selectByPrimaryKey(applicationId);
        Long deleteId = application.getId();

        //删除对应的租户外键
        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
        applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(deleteId);
        applicationTenantService.deleteByCriteria(applicationTenantCriteria);

        this.deleteByPrimaryKey(applicationId);
    }

    @Override
    public PageUtil queryApplicationsByParms(ApplicationParm applicationParm) {

        Application application = applicationParm.getApplication();

        ApplicationCriteria applicationCriteria = new ApplicationCriteria();

        applicationCriteria.createCriteria().andStatusEqualTo(1);

        //根据tenant id 获取user对应的应用
        if (!ObjectUtils.isEmpty(applicationParm.getTenantId())) {

            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andTenantIdEqualTo(applicationParm.getTenantId());

            List<ApplicationTenant> applicationTenants = new ArrayList<ApplicationTenant>();
            applicationTenants.addAll(applicationTenantService.selectByCriteria(applicationTenantCriteria));

            List<Long> applicationTenantIds = new ArrayList<Long>();
            for (ApplicationTenant applicationTenant : applicationTenants) {
                applicationTenantIds.add(applicationTenant.getApplicationId());
            }

            if (applicationTenantIds.size() == 0) {
                applicationTenantIds.add(GarnetContants.NON_VALUE);
            }

            applicationCriteria.createCriteria().andIdIn(applicationTenantIds);
        }

        if (!ObjectUtils.isEmpty(applicationParm.getSearchName())) {
            applicationCriteria.createCriteria().andNameLike("%" + applicationParm.getSearchName() + "%");
        }

        PageUtil result = new PageUtil(this.selectByCriteria(applicationCriteria), (int) this.countByCriteria(applicationCriteria), applicationParm.getPageSize(), applicationParm.getPageNumber());


        return result;
    }

    @Override
    public ApplicationView getApplicationWithTenant(Long applicaitonId) {

        Application application = this.selectByPrimaryKey(applicaitonId);

        ApplicationView applicationView = new ApplicationView();

        applicationView.setApplication(application);

        TenantParm tenantParm = new TenantParm();

        tenantParm.setPageNumber(0);

        tenantParm.setApplicationId(applicaitonId);

        tenantParm.setPageSize(Integer.MAX_VALUE);

        List<Long> tenantIdList = new ArrayList<>();

        List<String> tenantNameList = new ArrayList<>();

        PageUtil<Tenant> tenantPageInfo = tenantService.queryTenantssByParms(tenantParm);

        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
        applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(applicaitonId);

        //返回已勾选的租户
        List<ApplicationTenant> applicationTenants = applicationTenantService.selectByCriteria(applicationTenantCriteria);
        if (!CollectionUtils.isEmpty(applicationTenants)) {
            for (ApplicationTenant applicationTenant : applicationTenants) {
                Long tenantId = applicationTenant.getTenantId();
                tenantIdList.add(tenantId);
                Tenant tenant = tenantService.selectByPrimaryKey(tenantId);
                if (!ObjectUtils.isEmpty(tenant)) {
                    tenantNameList.add(tenant.getName());
                }
            }
        }

//        for (Tenant tenant : tenantPageInfo.getList()) {
////            tenantIdList.add(tenant.getId());
//            tenantNameList.add(tenant.getName());
//        }

        applicationView.setTenantIdList(tenantIdList);
        applicationView.setTenantNameList(tenantNameList);

        return applicationView;
    }

    @Override
    public void updateStatusById(Application application) {
        Long currentTime = new Date().getTime();
        application.setModifiedTime(currentTime);
        application.setStatus(0);
        this.updateByPrimaryKeySelective(application);


        if (!ObjectUtils.isEmpty(application) && !ObjectUtils.isEmpty(application.getId())) {
            //删除关联外键
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(application.getId());
            List<ApplicationTenant> applicationTenants = applicationTenantService.selectByCriteria(applicationTenantCriteria);
            applicationTenantService.deleteByCriteria(applicationTenantCriteria);

            //删除关联着此应用的租户
            if (!CollectionUtils.isEmpty(applicationTenants)) {
                for (ApplicationTenant applicationTenant : applicationTenants) {
                    Long tenantId = applicationTenant.getTenantId();
                    Tenant tenant = new Tenant();
                    tenant.setId(tenantId);
                    tenantService.updateStatusById(tenant);
                }
            }
        }
    }

    @Override
    public boolean hasRelated(String ids) {
        List<Long> idList = new ArrayList<>();
        for (String id : ids.split(",")) {
            idList.add(Long.parseLong(id));
        }

        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
        applicationTenantCriteria.createCriteria().andApplicationIdIn(idList);
        List<ApplicationTenant> applicationTenants = applicationTenantService.selectByCriteria(applicationTenantCriteria);
        //如果应用有关联的租户，返回true
        if (!CollectionUtils.isEmpty(applicationTenants)) {
            return true;
        }

        return false;
    }

}