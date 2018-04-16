package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.StringUtil;
import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.TenantMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.*;
import com.richstonedt.garnet.model.parm.TenantParm;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TenantServiceImpl extends BaseServiceImpl<Tenant, TenantCriteria, Long> implements TenantService {

    private static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);

    private static final String SERVICE_MODE_SAAS = "saas";

    private static final String SERVICE_MODE_PAAS = "paas";

    private static final String SERVICE_MODE_ALL = "all";

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private ApplicationTenantService applicationTenantService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserTenantService userTenantService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.tenantMapper;
    }

    @Override
    public Long insertTenant(TenantView tenantView) {

        TenantCriteria tenantCriteria = new TenantCriteria();
        tenantCriteria.createCriteria().andNameEqualTo(tenantView.getTenant().getName()).andStatusEqualTo(1);
        Tenant tenant1 = this.selectSingleByCriteria(tenantCriteria);
        if (!ObjectUtils.isEmpty(tenant1)) {
            throw new RuntimeException("租户名: " + tenant1.getName() +" 已经存在");
        }

        Tenant tenant = tenantView.getTenant();
        tenant.setId(IdGeneratorUtil.generateId());
        Long currentTime = System.currentTimeMillis();
        tenant.setCreatedTime(currentTime);
        tenant.setModifiedTime(currentTime);
        this.insertSelective(tenant);
        this.dealForgenKeyApplications(tenantView);
        this.dealForgenKeyUsers(tenantView);
        //如果用户添加不为空，添加记录到中间表
//        if(!ObjectUtils.isEmpty(tenantView.getUserTenants())){
//            for(UserTenant userTenant : tenantView.getUserTenants()){
//                userTenant.setId(IdGeneratorUtil.generateId());
//                userTenant.setTenantId(tenant.getId());
//                userTenantService.insertSelective(userTenant);
//            }
//        }
        return tenant.getId();
    }

    @Override
    public void updateTenant(TenantView tenantView) {
        Tenant tenant = tenantView.getTenant();

        //查看该租户名是否已经存在
        TenantCriteria tenantCriteria = new TenantCriteria();
        tenantCriteria.createCriteria().andNameEqualTo(tenant.getName()).andStatusEqualTo(1);
        Tenant tenant1 = this.selectSingleByCriteria(tenantCriteria);
        if (!ObjectUtils.isEmpty(tenant1) && tenantView.getTenant().getId().longValue() != tenant1.getId().longValue()) {
            throw new RuntimeException("租户名: " + tenant1.getName() +" 已经存在");
        }

        tenant.setModifiedTime(System.currentTimeMillis());
        this.updateByPrimaryKeySelective(tenant);
        this.dealForgenKeyApplications(tenantView);
        //更新用户
        this.dealForgenKeyUsers(tenantView);
    }

    /**
     * 处理租户关联的外键
     */
    private void dealForgenKeyApplications(TenantView tenantView){

        //如果什么都没有动过直接submit为Null

        //把所有勾选的去掉会""

        //其余为正常选择
        if("".equals(tenantView.getAppIds())){
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andTenantIdEqualTo(tenantView.getTenant().getId());
            applicationTenantService.deleteByCriteria(applicationTenantCriteria);
        }

        if(!StringUtil.isEmpty(tenantView.getAppIds())){
            //先删除该租户绑定的app外键
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andTenantIdEqualTo(tenantView.getTenant().getId());
            applicationTenantService.deleteByCriteria(applicationTenantCriteria);

            //如果paas模式，判断应用是否已被绑定
            Tenant tenant = tenantView.getTenant();
            if (!ObjectUtils.isEmpty(tenant) && !StringUtils.isEmpty(tenant.getServiceMode()) && "paas".equals(tenant.getServiceMode())) {
                List<ApplicationTenant> applicationTenants = applicationTenantService.selectByCriteria(new ApplicationTenantCriteria());
                List<Long> applicationIds = new ArrayList<>(); //数据库中已绑定的appId

                for (ApplicationTenant applicationTenant : applicationTenants) {
                    applicationIds.add(applicationTenant.getApplicationId());
                }
                for (String appId : tenantView.getAppIds().split(",")) {
                    if (applicationIds.contains(Long.parseLong(appId))) {
                        Application application = applicationService.selectByPrimaryKey(Long.parseLong(appId));
                        throw new RuntimeException("应用" + application.getName() +"已被绑定");
                    }
                }
            }
            //应用没被绑定，完成update
            for (String appId:tenantView.getAppIds().split(",")) {
                ApplicationTenant applicationTenant = new ApplicationTenant();
                applicationTenant.setId(IdGeneratorUtil.generateId());
                //设置租户ID
                applicationTenant.setTenantId(tenantView.getTenant().getId());
                applicationTenant.setApplicationId(Long.parseLong(appId));
                applicationTenantService.insertSelective(applicationTenant);
            }
        }

    }



    @Override
    public void deleteTenant(Long tenantId) {

        Tenant tenant = this.selectByPrimaryKey(tenantId);

        Long deleteId = tenant.getId();

        //删除关联用户
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();

        userTenantCriteria.createCriteria().andTenantIdEqualTo(deleteId);

        userTenantService.deleteByCriteria(userTenantCriteria);

        //删除关联应用
        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();

        applicationTenantCriteria.createCriteria().andTenantIdEqualTo(tenant.getId());

        applicationTenantService.deleteByCriteria(applicationTenantCriteria);

        this.deleteByPrimaryKey(tenantId);


    }

    @Override
    public PageUtil queryTenantssByParms(TenantParm tenantParm) {

        TenantCriteria tenantCriteria = new TenantCriteria();
        TenantCriteria.Criteria criteria = tenantCriteria.createCriteria();
//        criteria.andStatusEqualTo(1);

        if (!StringUtils.isEmpty(tenantParm.getSearchName())) {
            criteria.andNameLike("%" + tenantParm.getSearchName() + "%");
        }

        //根据userId获取用户所有的租户
        if(!ObjectUtils.isEmpty(tenantParm.getUserId())){

            ReturnTenantIdView returnTenantIdView =userService.getTenantIdsByUserId(tenantParm.getUserId());
            List<Long> userTenantIds = returnTenantIdView.getTenantIds();

            if(userTenantIds.size() == 0){
                userTenantIds.add(GarnetContants.NON_VALUE);
            }
            //如果不是属于garnet的超级管理员,根据tenantId返回
            if (!returnTenantIdView.isSuperAdmin() || (returnTenantIdView.isSuperAdmin() && !commonService.superAdminBelongGarnet(tenantParm.getUserId()))) {
                criteria.andIdIn(userTenantIds);
            }

        }

        //根据appclition获取租户
        if(!ObjectUtils.isEmpty(tenantParm.getApplicationId())){
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(tenantParm.getApplicationId());
            List<ApplicationTenant> applicationTenants = applicationTenantService.selectByCriteria(applicationTenantCriteria);
            List<Long> applicationTenantIds = new ArrayList<>();

            for (ApplicationTenant applicationTenant: applicationTenants) {
                 applicationTenantIds.add(applicationTenant.getTenantId());
            }

            if(applicationTenantIds.size()==0){
                applicationTenantIds.add(GarnetContants.NON_VALUE);
            }
            criteria.andIdIn(applicationTenantIds);
        }

        if (StringUtils.isEmpty(tenantParm.getMode())) {
            criteria.andStatusEqualTo(1);
        }  else if (SERVICE_MODE_SAAS.equals(tenantParm.getMode())) {
            criteria.andServiceModeEqualTo(SERVICE_MODE_SAAS).andStatusEqualTo(1);
        } else if (SERVICE_MODE_PAAS.equals(tenantParm.getMode())) {
            criteria.andServiceModeEqualTo(SERVICE_MODE_PAAS).andStatusEqualTo(1);
        } else if (SERVICE_MODE_ALL.equals(tenantParm.getMode())) {
            criteria.andStatusEqualTo(1);
        } else {
            return new PageUtil(null, (int)this.countByCriteria(tenantCriteria) ,tenantParm.getPageSize(), tenantParm.getPageNumber());
        }

        PageUtil result = new PageUtil(this.selectByCriteria(tenantCriteria), (int)this.countByCriteria(tenantCriteria) ,tenantParm.getPageSize() ,tenantParm.getPageNumber());
        return result;
    }

    @Override
    public TenantView getTenantWithApplication(Long tenantId) {

        Tenant tenant = this.selectByPrimaryKey(tenantId);
        TenantView tenantView = new TenantView();
        tenantView.setTenant(tenant);

//        ApplicationParm applicationParm = new ApplicationParm();
//        applicationParm.setTenantId(tenantId);
//        applicationParm.setPageNumber(1);
//        applicationParm.setPageSize(Integer.MAX_VALUE);
//        applicationParm.setTenantId(tenantId);
//        PageUtil<Application> applicationPage = applicationService.queryApplicationsByParms(applicationParm);

        //根据tenantId拿应用
        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
        applicationTenantCriteria.createCriteria().andTenantIdEqualTo(tenantId);
        List<ApplicationTenant> applicationTenants = applicationTenantService.selectByCriteria(applicationTenantCriteria);
        List<Long> appIdList = new ArrayList<>();
        List<String> appNameList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(applicationTenants) && applicationTenants.size() > 0) {
            for (ApplicationTenant applicationTenant : applicationTenants) {
                appIdList.add(applicationTenant.getApplicationId());
            }
            ApplicationCriteria applicationCriteria = new ApplicationCriteria();
            applicationCriteria.createCriteria().andIdIn(appIdList);
            List<Application> applications = applicationService.selectByCriteria(applicationCriteria);

            for (Application application: applications) {
                appNameList.add(application.getName());
            }
        }

        tenantView.setAppIdList(appIdList);
        tenantView.setAppNameList(appNameList);

        //获取关联用户
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andTenantIdEqualTo(tenantId);
        List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);
        if (!CollectionUtils.isEmpty(userTenants)) {
            Long userId = userTenants.get(0).getUserId();
            User user = userService.selectByPrimaryKey(userId);
            tenantView.setUserName(user.getUserName());
        }

//        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
//
//        applicationTenantCriteria.createCriteria().andTenantIdEqualTo(tenantId);
//
//        List<ApplicationTenant> applicationTenants = new ArrayList<>();
//
//        applicationTenants.addAll(applicationTenantService.selectByCriteria(applicationTenantCriteria));
//
//        List<Long> applicationIds = new ArrayList<>();
//
//        for (ApplicationTenant:
//                applicationTenants ) {
//
//        }

        return tenantView;
    }

    @Override
    public void updateStatusById(Tenant tenant) {
        Long currentTime = new Date().getTime();
        tenant.setModifiedTime(currentTime);
        tenant.setStatus(0);
        this.updateByPrimaryKeySelective(tenant);

        //删除关联外键
        if (!ObjectUtils.isEmpty(tenant) && !ObjectUtils.isEmpty(tenant.getId())) {
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andTenantIdEqualTo(tenant.getId());
            applicationTenantService.deleteByCriteria(applicationTenantCriteria);
        }
    }

    @Override
    public List<Long> getApplicationIs() {
        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
        List<ApplicationTenant> applicationTenants = applicationTenantService.selectByCriteria(applicationTenantCriteria);
        List<Long> applicationIds = new ArrayList<>();
        for (ApplicationTenant applicationTenant : applicationTenants) {
            applicationIds.add(applicationTenant.getApplicationId());
        }
        return applicationIds;
    }

    //根据userName 处理关联的用户
    private void dealForgenKeyUsers(TenantView tenantView) {
        if (!ObjectUtils.isEmpty(tenantView.getUserName())) {
            UserCriteria userCriteria = new UserCriteria();
            userCriteria.createCriteria().andUserNameEqualTo(tenantView.getUserName());
            List<User> users = userService.selectByCriteria(userCriteria);
            if (CollectionUtils.isEmpty(users)) {
                throw new RuntimeException("此用户不存在");
            } else {
                User user = users.get(0); // username是唯一的
                if (user.getStatus() == null || user.getStatus() == 0) {
                    throw new RuntimeException("此用户已被冻结");
                }
                List<UserTenant> userTenants = null;
                UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
                if (!ObjectUtils.isEmpty(tenantView.getTenant()) && !ObjectUtils.isEmpty(tenantView.getTenant().getId())) {
                    Long tenantId = tenantView.getTenant().getId();
                    userTenantCriteria.createCriteria().andTenantIdEqualTo(tenantId);
                    userTenants = userTenantService.selectByCriteria(userTenantCriteria);
                }
                //如果该租户已有其它外键关联，先删后加
//                if (!CollectionUtils.isEmpty(userTenants)) {
//                    userTenantService.deleteByCriteria(userTenantCriteria);
//                }
                //如果该租户已经关联此用户，抛异常
                UserTenantCriteria userTenantCriteria1 = new UserTenantCriteria();
                userTenantCriteria1.createCriteria().andUserIdEqualTo(user.getId());
                List<UserTenant> userTenants1 = userTenantService.selectByCriteria(userTenantCriteria1);
                if (!CollectionUtils.isEmpty(userTenants1)) {
                    for (UserTenant userTenant : userTenants1) {
                        if (userTenant.getTenantId().longValue() == tenantView.getTenant().getId().longValue() && userTenant.getUserId().longValue() == user.getId().longValue()) {
                            throw new RuntimeException("您已经添加过此用户");
                        }
                    }

                }

//                if (!CollectionUtils.isEmpty(userTenants1)) {
//                    throw new RuntimeException("此用户已被添加");
//                }

                UserTenant userTenant = new UserTenant();
                userTenant.setUserId(user.getId());
                userTenant.setTenantId(tenantView.getTenant().getId());
                userTenant.setId(IdGeneratorUtil.generateId());
                userTenantService.insertSelective(userTenant);
            }
        }
    }
}