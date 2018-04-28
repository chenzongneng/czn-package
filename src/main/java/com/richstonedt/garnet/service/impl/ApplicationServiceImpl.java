package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.StringUtil;
import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.ApplicationMapper;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.*;
import com.richstonedt.garnet.model.parm.ApplicationParm;
import com.richstonedt.garnet.model.parm.TenantParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.net.CookieHandler;
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
    private UserService userService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private RouterGroupService routerGroupService;


    private static final String SERVICE_MODE_SAAS = "saas";

    private static final String SERVICE_MODE_PAAS = "paas";

    private static final String SERVICE_MODE_ALL = "all";

    @Autowired
    private CommonService commonService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.applicationMapper;
    }

    @Override
    public Long insertApplication(ApplicationView applicationView) {

        Application application = applicationView.getApplication();
        String message = " 已经存在";
        //检查appCode是否已存在
        String appCode = application.getAppCode();
        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo(appCode).andStatusEqualTo(1);
        Application application1 = this.selectSingleByCriteria(applicationCriteria);
        if (!ObjectUtils.isEmpty(application1)) {
            throw new RuntimeException("appCode: " + appCode + message);
        }

        //检查appName是否已存在
        String name = application.getName();
        ApplicationCriteria applicationCriteria1 = new ApplicationCriteria();
        applicationCriteria1.createCriteria().andNameEqualTo(name).andStatusEqualTo(1);
        Application application2 = this.selectSingleByCriteria(applicationCriteria1);
        if (!ObjectUtils.isEmpty(application2)) {
            throw new RuntimeException("应用名称: " + name + message);
        }

        application.setId(IdGeneratorUtil.generateId());
        Long currentTime = System.currentTimeMillis();
        application.setCreatedTime(currentTime);
        application.setModifiedTime(currentTime);
        this.insertSelective(application);
        this.dealForgenKeyApplications(applicationView);

        return application.getId();

    }

    @Override
    public void updateApplication(ApplicationView applicationView) {

        Application application = applicationView.getApplication();

        //检查appCode是否已存在
        String appCode = application.getAppCode();
        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo(appCode).andStatusEqualTo(1);
        Application application1 = this.selectSingleByCriteria(applicationCriteria);
        if (!ObjectUtils.isEmpty(application1) && application.getId().longValue() != application1.getId().longValue()) {
            throw new RuntimeException("appCode: " + appCode + " 已经存在");
        }

        Long currentTime = System.currentTimeMillis();
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

        Application application = applicationView.getApplication();
        if ("".equals(applicationView.getTenantIds())) {
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(application.getId());
            applicationTenantService.deleteByCriteria(applicationTenantCriteria);
        } else if (!StringUtil.isEmpty(applicationView.getTenantIds())) {
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(application.getId());
            applicationTenantService.deleteByCriteria(applicationTenantCriteria);

            //如果saas模式，判断租户是否已被绑定
            if (!ObjectUtils.isEmpty(application) && !StringUtils.isEmpty(application.getServiceMode()) && "saas".equals(application.getServiceMode())) {
                this.checkTenantIsBinded(applicationView);
            }

            //选择的租户未被绑定，完成更新
            for (String tenantId : applicationView.getTenantIds().split(",")) {
                ApplicationTenant applicationTenant = new ApplicationTenant();
                applicationTenant.setId(IdGeneratorUtil.generateId());
                //applicationId
                applicationTenant.setApplicationId(applicationView.getApplication().getId());
                applicationTenant.setTenantId(Long.parseLong(tenantId));
                applicationTenantService.insertSelective(applicationTenant);
            }
        }

    }

    /**
     * 检查租户是否已经绑定，如果已经绑定，抛出异常给前端
     * @param applicationView
     */
    private void checkTenantIsBinded(ApplicationView applicationView) {
        List<ApplicationTenant> applicationTenants = applicationTenantService.selectByCriteria(new ApplicationTenantCriteria());
        List<Long> tenantIds = new ArrayList<>(); //数据库中已绑定的tenantId

        for (ApplicationTenant applicationTenant : applicationTenants) {
            tenantIds.add(applicationTenant.getTenantId());
        }
        for (String tenantId : applicationView.getTenantIds().split(",")) {
            if (tenantIds.contains(Long.parseLong(tenantId))) {
                Tenant tenant = tenantService.selectByPrimaryKey(Long.parseLong(tenantId));
                throw new RuntimeException("租户" + tenant.getName() +"已被绑定");
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

    /**
     * 1. 判断是否有userId
     * 2. 根据判断登录用户是否是超级管理员，
     *    如果是超级管理员，返回全部applications
     *    如果不是超级管理员，根据userId获取 tenantId，并从中去掉超级租户的id
     *    根据tenanId 返回相对应的应用
     * 3. 根据传入的serviceMode，返回相对应模式的应用
     * @param applicationParm
     * @return
     */
    @Override
    public PageUtil queryApplicationsByParms(ApplicationParm applicationParm) {

        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        ApplicationCriteria.Criteria criteria = applicationCriteria.createCriteria();

        List<UserTenant> userTenants = null;

        //根据用户id拿应用
        if (!ObjectUtils.isEmpty(applicationParm.getUserId())) {

            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(applicationParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();

            //如果不是garnet的超级管理员,返回绑定tenantId下的应用
            if (!returnTenantIdView.isSuperAdmin() || (returnTenantIdView.isSuperAdmin() && !commonService.superAdminBelongGarnet(applicationParm.getUserId()))) {

                //通过租户id拿应用id
                ApplicationTenantCriteria applicationTenantCriteria1 = new ApplicationTenantCriteria();
                applicationTenantCriteria1.createCriteria().andTenantIdIn(tenantIds);
                List<ApplicationTenant> applicationTenantList = applicationTenantService.selectByCriteria(applicationTenantCriteria1);

                List<Long> applicationIds = new ArrayList<>();
                for (ApplicationTenant applicationTenant : applicationTenantList) {
                    applicationIds.add(applicationTenant.getApplicationId());
                }

                if (applicationIds.size() == 0) {
                    applicationIds.add(GarnetContants.NON_VALUE);
                }
                criteria.andIdIn(applicationIds);
            }

        }

        if (!ObjectUtils.isEmpty(applicationParm.getSearchName())) {
            criteria.andNameLike("%" + applicationParm.getSearchName() + "%");
        }

        if (StringUtils.isEmpty(applicationParm.getMode())) {
            criteria.andStatusEqualTo(1);
            return new PageUtil(this.selectByCriteria(applicationCriteria), (int) this.countByCriteria(applicationCriteria), applicationParm.getPageSize(), applicationParm.getPageNumber());
        }

        switch (applicationParm.getMode()) {
            case SERVICE_MODE_SAAS:
                criteria.andServiceModeEqualTo(SERVICE_MODE_SAAS).andStatusEqualTo(1);
                break;
            case SERVICE_MODE_PAAS:
                criteria.andServiceModeEqualTo(SERVICE_MODE_PAAS).andStatusEqualTo(1);
                break;
            case SERVICE_MODE_ALL:
                criteria.andStatusEqualTo(1);
                break;
            default:
                return new PageUtil(null, (int) this.countByCriteria(applicationCriteria), applicationParm.getPageSize(), applicationParm.getPageNumber());
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

        applicationView.setTenantIdList(tenantIdList);
        applicationView.setTenantNameList(tenantNameList);

        return applicationView;
    }

    @Override
    public void updateStatusById(Application application) {

        if (!ObjectUtils.isEmpty(application) && !ObjectUtils.isEmpty(application.getId())) {

            if (application.getId().longValue() == GarnetContants.GARNET_APPLICATION_ID.longValue()) {
                throw new RuntimeException("不能删除Garnet应用");
            }

            //删除关联外键
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(application.getId());
            applicationTenantService.deleteByCriteria(applicationTenantCriteria);

            //如果有关联到应用组，一起删除
            if (hasRelated(String.valueOf(application.getId()))) {
                Application application1 = this.selectByPrimaryKey(application.getId());
                RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
                routerGroupCriteria.createCriteria().andAppCodeEqualTo(application1.getAppCode());
                routerGroupService.deleteByCriteria(routerGroupCriteria);
            }
        }

        Long currentTime = System.currentTimeMillis();
        application.setModifiedTime(currentTime);
        application.setStatus(0);
        this.updateByPrimaryKeySelective(application);
    }

    @Override
    public boolean hasRelated(String ids) {
        List<Long> idList = new ArrayList<>();
        for (String id : ids.split(",")) {
            idList.add(Long.parseLong(id));
        }

        ApplicationCriteria  applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andIdIn(idList).andStatusEqualTo(1);
        List<Application> applications = this.selectByCriteria(applicationCriteria);
        List<String> appCodes = new ArrayList<>();
        for (Application application : applications) {
            appCodes.add(application.getAppCode());
        }

        RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
        routerGroupCriteria.createCriteria().andAppCodeIn(appCodes);
        List<RouterGroup> routerGroupList = routerGroupService.selectByCriteria(routerGroupCriteria);
        if (!CollectionUtils.isEmpty(routerGroupList) && routerGroupList.size() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public List<Application> getApplicatinsWithoutRouterGroup(ApplicationParm applicationParm) {
        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        ApplicationCriteria.Criteria criteria = applicationCriteria.createCriteria();
        criteria.andStatusEqualTo(1);
        List<UserTenant> userTenants = null;

        //根据用户id拿应用
        if (!ObjectUtils.isEmpty(applicationParm.getUserId())) {

            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(applicationParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();

            //如果不是garnet的超级管理员,返回绑定tenantId下的应用
            if (!returnTenantIdView.isSuperAdmin() || (returnTenantIdView.isSuperAdmin() && !commonService.superAdminBelongGarnet(applicationParm.getUserId()))) {

                //通过租户id拿应用id
                ApplicationTenantCriteria applicationTenantCriteria1 = new ApplicationTenantCriteria();
                applicationTenantCriteria1.createCriteria().andTenantIdIn(tenantIds);
                List<ApplicationTenant> applicationTenantList = applicationTenantService.selectByCriteria(applicationTenantCriteria1);

                List<Long> applicationIds = new ArrayList<>();
                for (ApplicationTenant applicationTenant : applicationTenantList) {
                    applicationIds.add(applicationTenant.getApplicationId());
                }
                criteria.andIdIn(applicationIds);
            }

        }

        List<Application> applications = this.selectByCriteria(applicationCriteria);

        if (CollectionUtils.isEmpty(applications) || applications.size() == 0) {
            return new ArrayList<>();
        }
        List<Application> applicationList = dealApplications(applicationParm, applications);


        return applicationList;
    }

    /**
     * 处理应用组中显示的应用树
     * @param applicationParm
     * @param applications
     */
    private List<Application> dealApplications(ApplicationParm applicationParm, List<Application> applications) {
        //将已经被选中的应用去掉
        RouterGroupCriteria routerGroupCriteria;
        List<Application> applications1 = new ArrayList<>();
        for (int i = 0; i < applications.size(); i ++) {
            Application application = applications.get(i);
            String appCode = application.getAppCode();
            routerGroupCriteria = new RouterGroupCriteria();
            routerGroupCriteria.createCriteria().andAppCodeEqualTo(appCode);
            List<RouterGroup> routerGroupList = routerGroupService.selectByCriteria(routerGroupCriteria);
            if (!CollectionUtils.isEmpty(routerGroupList) && routerGroupList.size() > 0) {
                applications1.add(applications.get(i));
            }
        }

        applications.removeAll(applications1);

        if (!ObjectUtils.isEmpty(applicationParm.getRouterGroupId())) {
            Long routerGroupId = applicationParm.getRouterGroupId();
            RouterGroup routerGroup = routerGroupService.selectByPrimaryKey(routerGroupId);
            routerGroupCriteria = new RouterGroupCriteria();
            routerGroupCriteria.createCriteria().andGroupNameEqualTo(routerGroup.getGroupName());
            List<RouterGroup> routerGroupList = routerGroupService.selectByCriteria(routerGroupCriteria);
            List<Application> applications2 = new ArrayList<>();
            Application application;
            ApplicationCriteria applicationCriteria1;
            for (RouterGroup routerGroup1 : routerGroupList) {
                applicationCriteria1 = new ApplicationCriteria();
                applicationCriteria1.createCriteria().andAppCodeEqualTo(routerGroup1.getAppCode()).andStatusEqualTo(1);
                application = this.selectSingleByCriteria(applicationCriteria1);
                applications2.add(application);
            }
            applications.addAll(applications2);
        }
        return applications;
    }

}