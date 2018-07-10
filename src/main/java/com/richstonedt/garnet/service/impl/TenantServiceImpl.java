package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.StringUtil;
import com.google.gson.JsonArray;
import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.interceptory.LogRequired;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.TenantMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.*;
import com.richstonedt.garnet.model.parm.ApplicationParm;
import com.richstonedt.garnet.model.parm.TenantParm;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.service.*;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import sun.security.provider.PolicyParser;

import javax.jws.soap.SOAPBinding;
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

    private static final String TENANT_RELATED_ALLUSER_Y = "Y";

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
    private PermissionService permissionService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CommonService commonService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.tenantMapper;
    }

    @LogRequired(module = "租户管理模块", method = "新增租户")
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

        return tenant.getId();
    }

    @LogRequired(module = "租户管理模块", method = "更新租户")
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
        //取消租户和用户的绑定
//        if (!StringUtils.isEmpty(tenantView.getDelRelatedUserNames())) {
//            this.deleteTenantUserRelated(tenantView.getDelRelatedUserNames());
//        }
    }

    /**
     * 处理租户关联的外键
     */
    private void dealForgenKeyApplications(TenantView tenantView){

        //如果什么都没有动过直接submit为Null

        //把所有勾选的去掉会""

        //查询该登录用户有权限更改的应用列表
        ApplicationParm applicationParm = new ApplicationParm();
        applicationParm.setLoginUserId(tenantView.getLoginUserId());
        applicationParm.setUserId(tenantView.getLoginUserId());
        applicationParm.setMode("all");
        applicationParm.setQueryOrTree("tree");
        applicationParm.setPageSize(1000);
        applicationParm.setPageNumber(1);
        PageUtil appPages = applicationService.getApplicationsByParams(applicationParm);
        List<Application> applicationList = appPages.getList();
        List<Long> applicationIdList = new ArrayList<>();
        for (Application application : applicationList) {
            applicationIdList.add(application.getId());
        }
        //其余为正常选择
        if("".equals(tenantView.getAppIds())){
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andTenantIdEqualTo(tenantView.getTenant().getId()).andApplicationIdIn(applicationIdList);
            applicationTenantService.deleteByCriteria(applicationTenantCriteria);
        }

        if(!StringUtil.isEmpty(tenantView.getAppIds())){

            //先删除该租户绑定的app外键(只删除登录用户有权限看到的应用id)
            ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
            applicationTenantCriteria.createCriteria().andTenantIdEqualTo(tenantView.getTenant().getId()).andIdIn(applicationIdList);
            applicationTenantService.deleteByCriteria(applicationTenantCriteria);

            //如果paas模式，判断应用是否已被绑定
            this.checkApplicationBinked(tenantView);

            //应用没被绑定，完成update
            for (String appId:tenantView.getAppIds().split(",")) {
                ApplicationTenant applicationTenant = new ApplicationTenant();
                applicationTenant.setId(IdGeneratorUtil.generateId());
                //设置租户ID
                applicationTenant.setTenantId(tenantView.getTenant().getId());
                applicationTenant.setApplicationId(Long.parseLong(appId));
                applicationTenantService.insertSelective(applicationTenant);
            }

            Log log = new Log();
            log.setMessage("租户管理模块");
            log.setOperation("租户绑定应用");
            commonService.insertLog(log);
        }

    }

    /**
     * 检查应用是否已经被绑定
     * @param tenantView
     */
    private void checkApplicationBinked(TenantView tenantView) {
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
        tenantCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        TenantCriteria.Criteria criteria = tenantCriteria.createCriteria();
        criteria.andStatusEqualTo(1);


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
            return new PageUtil(this.selectByCriteria(tenantCriteria), (int)this.countByCriteria(tenantCriteria) ,tenantParm.getPageSize() ,tenantParm.getPageNumber());
        }

        switch (tenantParm.getMode()) {
            case SERVICE_MODE_SAAS :
                criteria.andServiceModeEqualTo(SERVICE_MODE_SAAS).andStatusEqualTo(1);
                break;
            case SERVICE_MODE_PAAS :
                criteria.andServiceModeEqualTo(SERVICE_MODE_PAAS).andStatusEqualTo(1);
                break;
            case SERVICE_MODE_ALL :
                break;
            default:
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

        return tenantView;
    }

    @LogRequired(module = "租户管理模块", method = "删除租户")
    @Override
    public void updateStatusById(Tenant tenant) {
        if (tenant.getId().longValue() == GarnetContants.GARNET_TENANT_ID) {
            throw new RuntimeException("不能删除超级租户");
        }

        Long currentTime = System.currentTimeMillis();
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
    public List<Long> getApplicationIds() {
        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
        List<ApplicationTenant> applicationTenants = applicationTenantService.selectByCriteria(applicationTenantCriteria);
        List<Long> applicationIds = new ArrayList<>();
        for (ApplicationTenant applicationTenant : applicationTenants) {
            applicationIds.add(applicationTenant.getApplicationId());
        }
        return applicationIds;
    }

    @Override
    public List<String> getRelatedUserNamesByTenantId(Long tenantId) {
        List<Long> userIds = getUserIdsByTenantId(tenantId);

        if (userIds.size() == 0) {
            userIds.add(GarnetContants.NON_VALUE);
        }

        UserCriteria userCredentia = new UserCriteria();
        userCredentia.createCriteria().andIdIn(userIds).andStatusEqualTo(1);
        List<User> users = userService.selectByCriteria(userCredentia);

        StringBuilder stringBuilder = new StringBuilder();
        List<String> userNameList = new ArrayList<>();
        for (User user : users) {
            userNameList.add(user.getUserName());
        }
        return userNameList;
    }

    @Override
    @LogRequired(module = "租户管理模块", method = "租户解绑用户")
    public void deleteTenantUserRelated(Long id, String userNames) {

        if (ObjectUtils.isEmpty(id)) {
            throw new RuntimeException("租户id不能为空");
        }

        if (StringUtils.isEmpty(userNames)) {
            throw new RuntimeException("要取消绑定的用户名不能为空");
        }

        List<Long> userIds = new ArrayList<>();
        for (String userName : userNames.split(",")) {
            UserCriteria userCriteria = new UserCriteria();
            userCriteria.createCriteria().andUserNameEqualTo(userName).andStatusEqualTo(1);
            User user = userService.selectSingleByCriteria(userCriteria);
            if (ObjectUtils.isEmpty(user)) {
                throw new RuntimeException("用户名：" + userName + " 不存在");
            }

            UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
            userTenantCriteria.createCriteria().andUserIdEqualTo(user.getId()).andTenantIdEqualTo(id);
            List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);
            if (CollectionUtils.isEmpty(userTenants)) {
                throw new RuntimeException("用户名：" + userName + " 没有被此租户绑定");
            }

            userIds.add(user.getId());
        }

        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andUserIdIn(userIds).andTenantIdEqualTo(id);
        userTenantService.deleteByCriteria(userTenantCriteria);

        //将garnet和admin的关联被删除，重新添加上
        UserTenantCriteria userTenantCriteria1 = new UserTenantCriteria();
        userTenantCriteria1.createCriteria().andTenantIdEqualTo(GarnetContants.GARNET_TENANT_ID).andUserIdEqualTo(GarnetContants.GARNET_USER_ID);
        UserTenant userTenant = userTenantService.selectSingleByCriteria(userTenantCriteria1);
        if (ObjectUtils.isEmpty(userTenant)) {
            userTenant = new UserTenant();
            userTenant.setUserId(GarnetContants.GARNET_USER_ID);
            userTenant.setTenantId(GarnetContants.GARNET_TENANT_ID);
            userTenant.setId(IdGeneratorUtil.generateId());
            userTenantService.insertSelective(userTenant);
        }
    }

    @Override
    public List<Tenant> getTenantListByUserId(Long userId) {
        String level = resourceService.getLevelByUserIdAndPath(userId, GarnetContants.GARNET_DATA_USERMANAGE_TENANTLIST_PATH);
        List<Tenant> tenantList = this.getTenantListByLevel(level, userId);
        return tenantList;
    }

    @Override
    public List<Tenant> getTenantManageListByUserId(Long userId) {
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andUserIdEqualTo(userId);
        List<UserTenant> userTenantList = userTenantService.selectByCriteria(userTenantCriteria);
        List<Long> tenantIdList = new ArrayList<>();
        for (UserTenant userTenant : userTenantList) {
            tenantIdList.add(userTenant.getTenantId());
        }

        List<Permission> permissions = resourceService.getPermissionsByUserId(userId);
        List<Long> permissionIdList = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionIdList.add(permission.getId());
        }

        List<Long> tenantIdList1 = new ArrayList<>();
        for (Long tenantId : tenantIdList) {
            PermissionCriteria permissionCriteria = new PermissionCriteria();
            permissionCriteria.createCriteria().andResourcePathWildcardLike(GarnetContants.GARNET_TENANTMANAGE_PATH + "%").andTenantIdEqualTo(tenantId).andStatusEqualTo(1).andIdIn(permissionIdList);
            List<Permission> permissionList = permissionService.selectByCriteria(permissionCriteria);
            if (!CollectionUtils.isEmpty(permissionList)) {
                tenantIdList1.add(tenantId);
            }
        }

        List<Tenant> tenantList = new ArrayList<>();
        Tenant tenant;
        for (Long tenantId : tenantIdList1) {
            tenant = this.selectByPrimaryKey(tenantId);
            tenantList.add(tenant);
        }

        return tenantList;
    }

    @Override
    public List<Tenant> getTenantListByUserIdAndPath(Long userId, String path) {
        String level = resourceService.getLevelByUserIdAndPath(userId, path);
        List<Tenant> tenantList = this.getTenantListByLevel(level, userId);
        return tenantList;
    }

    @LogRequired(module = "租户管理模块", method = "查询租户列表")
    @Override
    public PageUtil getTenantsByParams(TenantParm tenantParm) {
        Long userId = tenantParm.getUserId();

        TenantCriteria tenantCriteria = new TenantCriteria();
        tenantCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        TenantCriteria.Criteria criteria = tenantCriteria.createCriteria();
        criteria.andStatusEqualTo(1);

        if (!StringUtils.isEmpty(tenantParm.getSearchName())) {
            criteria.andNameLike("%" + tenantParm.getSearchName() + "%");
        }

        if (StringUtils.isEmpty(tenantParm.getMode())) {
            return new PageUtil(null, (int)this.countByCriteria(tenantCriteria) ,tenantParm.getPageSize() ,tenantParm.getPageNumber());
        }

        switch (tenantParm.getMode()) {
            case SERVICE_MODE_SAAS :
                criteria.andServiceModeEqualTo(SERVICE_MODE_SAAS);
                break;
            case SERVICE_MODE_PAAS :
                criteria.andServiceModeEqualTo(SERVICE_MODE_PAAS);
                break;
            case SERVICE_MODE_ALL :
                break;
            default:
                return new PageUtil(null, (int)this.countByCriteria(tenantCriteria) ,tenantParm.getPageSize(), tenantParm.getPageNumber());
        }

        List<Tenant> tenantList = new ArrayList<>();
        String queryOrTree = tenantParm.getQueryOrTree();
        String level;
        if (GarnetContants.QUERYORTREE_TREE.equals(queryOrTree)) {
            //租户树
            level = resourceService.getLevelByUserIdAndPath(userId, GarnetContants.GARNET_DATA_APPLICATIONMANAGE_TENANTLIST_PATH);
        } else {
            level = resourceService.getLevelByUserIdAndPath(userId, GarnetContants.GARNET_DATA_TENANTMANAGE_QUERY_PATH);
        }

        if (Integer.valueOf(level) == 1) {
            tenantList = this.selectByCriteria(tenantCriteria);
        } else if (Integer.valueOf(level) == 2) {
            List<Tenant> tenantList1 = this.selectByCriteria(tenantCriteria);
            for (Tenant tenant : tenantList1) {
                if (tenant.getId().longValue() != GarnetContants.GARNET_TENANT_ID.longValue()) {
                    tenantList.add(tenant);
                }
            }
        } else if (Integer.valueOf(level) == 3) {
            tenantList = this.getTenantManageListByUserId(userId);
        }

        PageUtil pageUtil = new PageUtil(tenantList, tenantList.size() ,tenantParm.getPageSize() ,tenantParm.getPageNumber());
        return pageUtil;
    }

    /**
     * 根据资源配置level，返回租户列表
     * level=1 全部数据
     * level=2 除garnet外的数据
     * level=3 本用户为租户管理员的租户
     * @param level
     * @param userId
     * @return
     */
    private List<Tenant> getTenantListByLevel(String level, Long userId) {
        List<Tenant> tenantList = new ArrayList<>();
        if (Integer.valueOf(level) == 1) {
            TenantCriteria tenantCriteria = new TenantCriteria();
            tenantCriteria.createCriteria().andStatusEqualTo(1);
            tenantList = this.selectByCriteria(tenantCriteria);
        } else if (Integer.valueOf(level) == 2) {
            List<Long> tenantIdList = new ArrayList<>();
            tenantIdList.add(GarnetContants.GARNET_TENANT_ID);
            TenantCriteria tenantCriteria = new TenantCriteria();
            tenantCriteria.createCriteria().andStatusEqualTo(1).andIdNotIn(tenantIdList);
            tenantList = this.selectByCriteria(tenantCriteria);
        } else if (Integer.valueOf(level) == 3) {
            tenantList = this.getTenantManageListByUserId(userId);
        }
        return tenantList;
    }

    /**
     * 查询租户关联的所有userIds
     * @param tenantId
     * @return
     */
    private List<Long> getUserIdsByTenantId(Long tenantId) {
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andTenantIdEqualTo(tenantId);
        List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);
        List<Long> userIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userTenants) && userTenants.size() != 0) {
            for (UserTenant userTenant : userTenants) {
                userIds.add(userTenant.getUserId());
            }
        }
        return userIds;
    }

    /**
     * 根据userName 处理关联的用户
     * @param tenantView
     */
    private void dealForgenKeyUsers(TenantView tenantView) {
        String userNames = tenantView.getUserNames();
        Long tenantId = tenantView.getTenant().getId();
        Long loginUserId = tenantView.getLoginUserId();

//        List<Resource> resourceList = resourceService.getResourcesByUserId(loginUserId);
//        boolean isRelateAllUsers = false;
//        for (Resource resource : resourceList) {
//            if (resource.getId().longValue() == GarnetContants.GARNET_RESOURCE_USERRELATION_ID.longValue()) {
//                isRelateAllUsers = true;
//            }
//        }

        String relatedAllUsers = tenantView.getTenant().getRelatedAllUsers();
        if (TENANT_RELATED_ALLUSER_Y.equals(relatedAllUsers)) {
//        if (isRelateAllUsers) {
            //如果选择了默认关联所有用户
            //删除租户与用户的所有关联
            UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
            userTenantCriteria.createCriteria().andTenantIdEqualTo(tenantId);
            userTenantService.deleteByCriteria(userTenantCriteria);

            //添加与所有用户的关联
            UserCriteria userCriteria = new UserCriteria();
            userCriteria.createCriteria().andStatusEqualTo(1);
            List<User> users = userService.selectByCriteria(userCriteria);

            Long userTenantId = IdGeneratorUtil.generateId();
            for (User user : users) {
                UserTenant userTenant = new UserTenant();
                userTenant.setUserId(user.getId());
                userTenant.setTenantId(tenantId);
                userTenant.setId(userTenantId);
                userTenantService.insertSelective(userTenant);
                userTenantId = userTenantId + 1L;
            }

            Log log = new Log();
            log.setMessage("租户管理模块");
            log.setOperation("租户绑定所有用户");
            commonService.insertLog(log);
        } else {
            if (!StringUtils.isEmpty(userNames) && !ObjectUtils.isEmpty(tenantId)) {
                for (String userName : userNames.split(",")) {
                    //完成外键的绑定
                    executeForeignUsers(tenantId, userName);
                }
            }
        }
    }

    /**
     * 完成租户-用户的外键关联
     * @param tenantId
     * @param userName
     */
    private void executeForeignUsers(Long tenantId, String userName) {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.createCriteria().andUserNameEqualTo(userName).andStatusEqualTo(1);
        List<User> users = userService.selectByCriteria(userCriteria);
        if (CollectionUtils.isEmpty(users) || users.size() == 0) {
            throw new RuntimeException("用户: " + userName +" 不存在（用户名之间不能包含空格）");
        } else {
            // username是唯一的
            User user = users.get(0);
            if (user.getStatus() == null || user.getStatus() == 0) {
                throw new RuntimeException("此用户已被冻结");
            }

            //如果该租户已经关联此用户，抛异常
            checkUserBinked(tenantId, user);

            //完成关联
            UserTenant userTenant = new UserTenant();
            userTenant.setUserId(user.getId());
            userTenant.setTenantId(tenantId);
            userTenant.setId(IdGeneratorUtil.generateId());
            userTenantService.insertSelective(userTenant);
        }

        Log log = new Log();
        log.setMessage("租户管理模块");
        log.setOperation("租户绑定用户");
        commonService.insertLog(log);
    }

    /**
     * 检查租户是否已经关联用户
     * @param tenantId
     * @param user
     */
    private void checkUserBinked(Long tenantId, User user) {
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andUserIdEqualTo(user.getId());
        List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);
        if (!CollectionUtils.isEmpty(userTenants)) {
            for (UserTenant userTenant : userTenants) {
                if (userTenant.getTenantId().longValue() == tenantId.longValue() && userTenant.getUserId().longValue() == user.getId().longValue()) {
                    throw new RuntimeException("您已经添加过用户：" + user.getUserName());
                }
            }

        }
    }
}