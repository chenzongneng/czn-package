package com.richstonedt.garnet.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.interceptory.JwtToken;
import com.richstonedt.garnet.interceptory.LoginMessage;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.UserMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.*;
import com.richstonedt.garnet.model.message.MessageDescription;
import com.richstonedt.garnet.model.parm.UserParm;
import com.richstonedt.garnet.model.view.*;
import com.richstonedt.garnet.service.*;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserCriteria, Long> implements UserService {

    private static final String LOGINMESSAGE_STATUS_FALSE = "false";

    private static final String LOGINMESSAGE_STATUS_SUCCESS = "success";

    private static final String STRING_ACCESSTOKEN = "#accessToken";

    private static final String STRING_REFRESHTOKEN = "#refreshToken";

    private BeanCopier daoToViewCopier = BeanCopier.create(Resource.class, RefreshTokenResourceView.class,
            false);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private UserTenantService userTenantService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private RouterGroupService routerGroupService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ApplicationTenantService applicationTenantService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private GroupRoleService groupRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ResourceDynamicPropertyService resourceDynamicPropertyService;

    @Autowired
    private CommonService commonService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.userMapper;
    }

    @Override
    public Long insertUser(UserView userView) {

        String credential = userView.getPassword();
        User user = userView.getUser();

        user.setId(IdGeneratorUtil.generateId());
        UserCredential userCredential = new UserCredential();
        userCredential.setExpiredDateTime(userView.getExpiredDateTime());
        userCredential.setCredential(credential);
        userCredential.setId(IdGeneratorUtil.generateId());
        userCredential.setUserId(user.getId());
        Long currentTime = System.currentTimeMillis();
        userCredential.setCreatedTime(currentTime);
        userCredential.setModifiedTime(currentTime);

        user.setCreatedTime(currentTime);
        user.setModifiedTime(currentTime);

        //检查用户名称是否已被使用
        checkDuplicateName(user);

        this.insertSelective(user);

        userCredentialService.insertSelective(userCredential);

        //User - tenant中间表
        if (!ObjectUtils.isEmpty(userView.getUserTenants())) {
            for (UserTenant userTenant : userView.getUserTenants()) {
                userTenant.setId(IdGeneratorUtil.generateId());
                userTenant.setUserId(user.getId());
                userTenantService.insertSelective(userTenant);
            }
        }

        //User - Group 中间表
        if (!ObjectUtils.isEmpty(userView.getGroupUsers())) {
            for (GroupUser groupUser : userView.getGroupUsers()) {
                groupUser.setId(IdGeneratorUtil.generateId());
                groupUser.setUserId(user.getId());
                groupUserService.insertSelective(groupUser);
            }
        }
        return user.getId();
    }

    /**
     * 检查用户名称是否已被使用
     * @param user
     */
    private void checkDuplicateName(User user) {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.createCriteria().andUserNameEqualTo(user.getUserName()).andStatusEqualTo(1);
        User user1 = this.selectSingleByCriteria(userCriteria);
        if (!ObjectUtils.isEmpty(user1) && user1.getId().longValue() != user.getId().longValue()) {
            throw new RuntimeException("账号已存在");
        }
    }

    @Override
    public void updateUser(UserView userView) {

        //检查用户名称是否已被使用
        User user = userView.getUser();
        checkDuplicateName(user);

        //更新密码表

        UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();
        userCredentialCriteria.createCriteria().andUserIdEqualTo(userView.getUser().getId());
        UserCredential userCredential = userCredentialService.selectSingleByCriteria(userCredentialCriteria);

        if (!userCredential.getExpiredDateTime().equals(userView.getExpiredDateTime())) {
            userCredential.setExpiredDateTime(userView.getExpiredDateTime());
            userCredential.setModifiedTime(System.currentTimeMillis());
        }

        if (!userCredential.getCredential().equals(userView.getPassword())) {
            userCredential.setCredential(userView.getPassword());
            userCredential.setModifiedTime(System.currentTimeMillis());
        }

        userCredentialService.updateByPrimaryKeySelective(userCredential);

        //更新User表
        user.setModifiedTime(System.currentTimeMillis());
        this.updateByPrimaryKeySelective(user);

        //User - tenant中间表
        if (!ObjectUtils.isEmpty(userView.getUserTenants())) {
            UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
            userTenantCriteria.createCriteria().andUserIdEqualTo(userView.getUser().getId());
            userTenantService.deleteByCriteria(userTenantCriteria);
            for (UserTenant userTenant : userView.getUserTenants()) {
                userTenant.setId(IdGeneratorUtil.generateId());
                userTenant.setUserId(userView.getUser().getId());
                userTenantService.insertSelective(userTenant);
            }
        }

        //User - Group 中间表
        if (!ObjectUtils.isEmpty(userView.getGroupUsers())) {
            GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
            groupUserCriteria.createCriteria().andUserIdEqualTo(userView.getUser().getId());
            groupUserService.deleteByCriteria(groupUserCriteria);
            for (GroupUser groupUser : userView.getGroupUsers()) {
                groupUser.setId(IdGeneratorUtil.generateId());
                groupUser.setUserId(userView.getUser().getId());
                groupUserService.insertSelective(groupUser);
            }
        }

    }

    @Override
    public void deleteUser(UserView userView) {

        User user = userView.getUser();

        //删除密码表
        UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();
        userCredentialCriteria.createCriteria().andUserIdEqualTo(user.getId());
        userCredentialService.deleteByCriteria(userCredentialCriteria);

        //删除User - tenant中间表
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andUserIdEqualTo(userView.getUser().getId());
        userTenantService.deleteByCriteria(userTenantCriteria);

        //删除User - Group 中间表
        GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
        groupUserCriteria.createCriteria().andUserIdEqualTo(userView.getUser().getId());
        groupUserService.deleteByCriteria(groupUserCriteria);
        this.deleteByPrimaryKey(user.getId());
    }

    @Override
    public PageUtil queryUsersByParms(UserParm userParm) {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        UserCriteria.Criteria criteria = userCriteria.createCriteria();
        //查询没被删除的user
        criteria.andStatusEqualTo(1);

        if (!StringUtils.isEmpty(userParm.getSearchName())) {
            criteria.andUserNameLike("%" + userParm.getSearchName() + "%");
        }

        //根据userId ,获取tenantId列表
        if (!StringUtils.isEmpty(userParm.getUserId())) {
            ReturnTenantIdView returnTenantIdView = this.getTenantIdsByUserId(userParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();

            //如果不是属于garnet的超级管理员，根据tenantId返回
            if (!returnTenantIdView.isSuperAdmin() || (returnTenantIdView.isSuperAdmin() && !commonService.superAdminBelongGarnet(userParm.getUserId()))) {
                //根据tenantIds获取关联的userIds
                List<Long> userIdList = getUserIdList(tenantIds);
                criteria.andIdIn(userIdList);
            } else {
                //如果是garnet的超级管理员，直接返回所有user列表
                PageUtil result = new PageUtil(this.selectByCriteria(userCriteria), (int) this.countByCriteria(userCriteria), userParm.getPageSize(), userParm.getPageNumber());
                return result;
            }

        } else {
            //没有传入userId，直接返回null
            return new PageUtil(null, 0, userParm.getPageSize(), userParm.getPageNumber());
        }

        List<User> users = this.selectByCriteria(userCriteria);
        List<User> usersWithTenant = new ArrayList<>();
        for (User user : users) {
            Long userId = user.getId();
            UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
            userTenantCriteria.createCriteria().andUserIdEqualTo(userId);
            List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);
            //如果此用户有关联租户，则添加进返回的列表
            if (!CollectionUtils.isEmpty(userTenants)) {
                usersWithTenant.add(user);
            }
        }

        PageUtil result = new PageUtil(usersWithTenant, (int) this.countByCriteria(userCriteria), userParm.getPageSize(), userParm.getPageNumber());
        return result;
    }

    /**
     * 根据租户id获得用户ids
     * @param tenantIds
     * @return
     */
    private List<Long> getUserIdList(List<Long> tenantIds) {
        if (tenantIds.size() == 0) {
            tenantIds.add(GarnetContants.NON_VALUE);
        }

        //根据tenantId获取user列表
        List<Long> userIdList = this.getUserIdsByTenantIds(tenantIds);
        if (userIdList.size() == 0) {
            userIdList.add(GarnetContants.NON_VALUE);
        }
        return userIdList;
    }

    private List<Long> getUserIdsByTenantIds(List<Long> tenantIds) {
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andTenantIdIn(tenantIds);
        List<UserTenant> userTenants1 = userTenantService.selectByCriteria(userTenantCriteria);
        List<Long> userIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userTenants1) && userTenants1.size() > 0) {
            for (UserTenant userTenant : userTenants1) {
                userIds.add(userTenant.getUserId());
            }
        }
        // 如果userIds 不为空
        if (!CollectionUtils.isEmpty(userIds)) {
            List<Long> userIdList = new ArrayList<>();
            //隐藏 admin
            for (Long userId : userIds) {
                if (userId.longValue() != GarnetContants.GARNET_USER_ID) {
                    userIdList.add(userId);
                }
            }
            return userIdList;

        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 1. 判断参数是否正确（userName, password, appCode）
     * 2. 账号密码核验正确，生成token并计算过期时间
     * 3. 查询数据库，判断用户在数据库中是否已有token,有则更新，无则添加
     * 4. 返回状态码 201 给前端
     *
     * @param loginView
     * @return
     * @throws Exception
     */
    @Override
    public LoginMessage userLogin(LoginView loginView) throws Exception {

        UserCriteria userCriteria = new UserCriteria();
        LoginMessage loginMessage = new LoginMessage();
        userCriteria.createCriteria().andUserNameEqualTo(loginView.getUserName()).andStatusEqualTo(1);
        User user = this.selectSingleByCriteria(userCriteria);

        if (StringUtils.isEmpty(loginView.getUserName()) || StringUtils.isEmpty(loginView.getPassword())) {
            loginMessage.setMessage("账号或密码为空");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        if (ObjectUtils.isEmpty(user)) {
            loginMessage.setMessage(MessageDescription.LOGIN_USERNAME_NOT_EXIST);
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        if (StringUtils.isEmpty(loginView.getAppCode())) {
            loginMessage.setMessage("AppCode不能为空");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo(loginView.getAppCode()).andStatusEqualTo(1);
        Application application = applicationService.selectSingleByCriteria(applicationCriteria);
        if (ObjectUtils.isEmpty(application)) {
            loginMessage.setMessage("应用不存在");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        //获取密码,验证密码是否正确
        UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();
        userCredentialCriteria.createCriteria().andUserIdEqualTo(user.getId());
        UserCredential userCredential = userCredentialService.selectSingleByCriteria(userCredentialCriteria);
        if (!userCredential.getCredential().equals(loginView.getPassword())) {
            loginMessage.setMessage("密码不正确");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        //生成token返回
        String token = JwtToken.createToken(user.getUserName(), userCredential.getCredential(), loginView.getAppCode(), System.currentTimeMillis());
        loginMessage.setMessage("登录成功");
        loginMessage.setCode(201);
        loginMessage.setUser(user);
        loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_SUCCESS);
        //拼接返回前端的token
        String accessTokenReturn = token + "#" + loginView.getAppCode() + "#" + loginView.getUserName() + "#" + System.currentTimeMillis() + STRING_ACCESSTOKEN;
        String refreshTokenReturn = token + "#" + loginView.getAppCode() + "#" + loginView.getUserName() + "#" + System.currentTimeMillis() + STRING_REFRESHTOKEN;
        loginMessage.setAccessToken(accessTokenReturn);
        loginMessage.setRefreshToken(refreshTokenReturn);

        TokenCriteria tokenCriteria = new TokenCriteria();
        tokenCriteria.createCriteria().andUserNameEqualTo(loginView.getUserName());
        Token tokenOld = tokenService.selectSingleByCriteria(tokenCriteria);

        //如果是第一次登录，将token插入数据库
        if (ObjectUtils.isEmpty(tokenOld)) {
            Token token1 = new Token();
            token1.setCreatedTime(System.currentTimeMillis());
            token1.setModifiedTime(System.currentTimeMillis());
            Long expiredTime = token1.getCreatedTime() + 30 * 60000L;
            token1.setExpireTime(expiredTime);
            token1.setId(IdGeneratorUtil.generateId());
            token1.setUserName(loginView.getUserName());
            token1.setToken(token);
            tokenService.insertSelective(token1);
        } else {
            //不是第一次登录，更新token
            tokenOld.setToken(token);
            tokenOld.setModifiedTime(System.currentTimeMillis());
            Long expiredTime = tokenOld.getModifiedTime() + GarnetContants.TOKEN_EXPIRED_TIME;
            tokenOld.setExpireTime(expiredTime);
            tokenOld.setToken(token);
            tokenService.updateByPrimaryKeySelective(tokenOld);
        }

        //查询登录用户关联的tenantIds
        //*******************************change by ming***********************************
        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
        applicationTenantCriteria.createCriteria().andApplicationIdEqualTo(application.getId());
        List<ApplicationTenant> applicationTenants =applicationTenantService.selectByCriteria(applicationTenantCriteria);
        List<Long> applicationTenantsIds = new ArrayList<>();
        for (ApplicationTenant applicationTenant: applicationTenants) {
            applicationTenantsIds.add(applicationTenant.getTenantId());
        }
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andTenantIdIn(applicationTenantsIds).andUserIdEqualTo(user.getId());
        List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);
        List<Long> tenantsIds = new ArrayList<>();

        for (UserTenant userTenant: userTenants) {
            tenantsIds.add(userTenant.getTenantId());
        }

        TenantCriteria tenantCriteria = new TenantCriteria();
        tenantCriteria.createCriteria().andIdIn(tenantsIds);
        List<Tenant> tenants = tenantService.selectByCriteria(tenantCriteria);
        Map<String,Long> tenantNameAndIdMap= new HashMap<String,Long>();
        for (Tenant tenant:
                tenants ) {
            tenantNameAndIdMap.put(tenant.getName(),tenant.getId());
        }
        loginMessage.setUserTenantNameAndIdMap(tenantNameAndIdMap);
        //******************************************************************
        ReturnTenantIdView returnTenantIdView = this.getTenantIdsByUserId(user.getId());
        List<Long> tenantIdList = returnTenantIdView.getTenantIds();
        loginMessage.setTenantIdList(tenantIdList);

        return loginMessage;
    }

    @Override
    public UserView getUserById(Long userId) {

        User user = this.selectByPrimaryKey(userId);

        UserView userView = new UserView();

        userView.setUser(user);

        UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();
        userCredentialCriteria.createCriteria().andUserIdEqualTo(user.getId());
        UserCredential userCredential = userCredentialService.selectSingleByCriteria(userCredentialCriteria);
        userView.setPassword(userCredential.getCredential());
        userView.setExpiredDateTime(userCredential.getExpiredDateTime());

        return userView;
    }

    @Override
    public User getUserByUserName(String userName) {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.createCriteria().andUserNameEqualTo(userName).andStatusEqualTo(1);
        User user = this.selectSingleByCriteria(userCriteria);
        return user;
    }

    @Override
    public void updateStatusById(User user, Long loginUserId) {

        if (user.getId().longValue() == GarnetContants.GARNET_USER_ID) {
            throw new RuntimeException("不能删除超级管理员");
        }

        if (user.getId().longValue() == loginUserId) {
            throw new RuntimeException("不能删除自己");
        }

        //删除关联外键
        if (!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getId())) {
            UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
            userTenantCriteria.createCriteria().andUserIdEqualTo(user.getId());
            userTenantService.deleteByCriteria(userTenantCriteria);

            GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
            groupUserCriteria.createCriteria().andUserIdEqualTo(user.getId());
            groupUserService.deleteByCriteria(groupUserCriteria);

            UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();
            userCredentialCriteria.createCriteria().andUserIdEqualTo(user.getId());
            userCredentialService.deleteByCriteria(userCredentialCriteria);
        }

        Long currentTime = System.currentTimeMillis();
        user.setModifiedTime(currentTime);
        user.setStatus(0);
        this.updateByPrimaryKeySelective(user);

    }

    @Override
    public List<User> queryUserByTenantId(UserParm userParm) {
        Long tenantId = userParm.getTenantId();
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andTenantIdEqualTo(tenantId);
        List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);
        List<User> users = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userTenants)) {
            for (UserTenant userTenant : userTenants) {
                Long userId = userTenant.getUserId();
                User user = this.selectByPrimaryKey(userId);
                if (!ObjectUtils.isEmpty(user)) {
                    users.add(user);
                }
            }
        }

        return users;
    }

    /**
     * 刷新token
     * 1. 能通过拦截器，说明传入的token是正确且有效的,故不再验证token是否正确
     * 2. 取出token中携带的信息
     * 3. 验证基础信息是否正确
     * 4. 根据username 查询密码
     * 5. 生成新的token
     * 6. 根据userName拿出token并更新到数据库
     * 7. 取出资源列表
     * 8. 处理返回
     * @param tokenRefreshView
     * @return
     * @throws Exception
     */
    @Override
    public LoginMessage refreshToken(TokenRefreshView tokenRefreshView) throws Exception {
        //能通过拦截器，说明token是正确且有效的,故不再验证token是否正确
        LoginMessage loginMessage = new LoginMessage();

        //取出token中携带的信息
        String tokenWithAppCode = tokenRefreshView.getRefreshToken();

        if (StringUtils.isEmpty(tokenWithAppCode)) {
            throw new RuntimeException("请输入token");
        }

        String[] tokenParams = tokenWithAppCode.split("#");
        String token = tokenParams[0];
//        String appCode = tokenParams[1];
        String appCode = tokenRefreshView.getAppCode();
        String userName = tokenRefreshView.getUserName();
//        String userName = tokenParams[2];
        String createTime = tokenParams[3];

        //验证基础信息是否正确
        LoginMessage loginMessage2 = this.checkRefreshTokenData(tokenRefreshView);
        if (loginMessage2.getCode() == 401 || loginMessage2.getCode() == 403 || "false".equals(loginMessage2.getLoginStatus())) {
            return loginMessage2;
        }

        //根据username 查询密码
        UserCredential userCredential = userCredentialService.getCredentialByUserName(userName);
        String password = userCredential.getCredential();

        //更新token
        Long currentTime = System.currentTimeMillis();
        String tokenNew = JwtToken.createToken(userName, password, appCode, currentTime);
        String accessTokenReturn = tokenNew + "#" + appCode + "#" + userName + "#" + currentTime + STRING_ACCESSTOKEN;
        String refreshTokenReturn = tokenNew + "#" + appCode + "#" + userName + "#" + currentTime + STRING_REFRESHTOKEN;

        //根据userName拿出token并更新到数据库
        TokenCriteria tokenCriteria = new TokenCriteria();
        tokenCriteria.createCriteria().andUserNameEqualTo(userName);
        Token token1 = tokenService.selectSingleByCriteria(tokenCriteria);
        token1.setToken(tokenNew);
        token1.setModifiedTime(currentTime);
        token1.setExpireTime((long) currentTime + GarnetContants.TOKEN_EXPIRED_TIME);
        tokenService.updateByPrimaryKeySelective(token1);

        loginMessage.setMessage("token刷新成功");
        loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_SUCCESS);
        loginMessage.setCode(201);
        loginMessage.setAccessToken(accessTokenReturn);
        loginMessage.setRefreshToken(refreshTokenReturn);

        List<Long> tenantIdList = tokenRefreshView.getTenantIdList();
        if (CollectionUtils.isEmpty(tenantIdList) || tenantIdList.size() == 0) {
            throw new RuntimeException("租户id列表不能为空");
        }

        //取出资源列表
        LoginMessage loginMessage1 = this.getResourcesWhenRefreshToken(userCredential, appCode, loginMessage, tenantIdList);
//        List<Resource> resourceList = loginMessage1.getResourceList();
        List<RefreshTokenResourceView> refreshTokenResourceViewList = loginMessage1.getRefreshTokenResourceList();

        List<Resource> resourceListWithReadlyOnly = loginMessage1.getResourceListWithReadlyOnly();
        List<Resource> resourceListWithEdit = loginMessage1.getResourceListWithReadlyOnly();
        List<List<ResourceDynamicProperty>> resourceDynamicPropertyList = loginMessage1.getResourceDynamicPropertyList();

        if (CollectionUtils.isEmpty(resourceListWithReadlyOnly)) {
            resourceListWithReadlyOnly = new ArrayList<>();
        }

        if (CollectionUtils.isEmpty(resourceListWithEdit)) {
            resourceListWithEdit = new ArrayList<>();
        }

        if (CollectionUtils.isEmpty(resourceDynamicPropertyList)) {
            resourceDynamicPropertyList = new ArrayList<>();
        }


//        loginMessage.setResourceList(resourceList);
        loginMessage.setResourceDynamicPropertyList(resourceDynamicPropertyList);
        loginMessage.setRefreshTokenResourceList(refreshTokenResourceViewList);
        loginMessage.setResourceListWithReadlyOnly(resourceListWithReadlyOnly);
        loginMessage.setGetResourceListWithEdit(resourceListWithEdit);

        return loginMessage;
    }

    /**
     * 获取resource列表并分组返回
     *
     * @param userCredential
     * @param appCode
     * @param loginMessage
     * @return
     */
    private LoginMessage getResourcesWhenRefreshToken(UserCredential userCredential, String appCode, LoginMessage loginMessage, List tenantIdList) {
        //获取返回资源
        //根据appCode拿 application
        ApplicationCriteria applicationCriteria = new ApplicationCriteria();
        applicationCriteria.createCriteria().andAppCodeEqualTo(appCode).andStatusEqualTo(1);
        Application application = applicationService.selectSingleByCriteria(applicationCriteria);
        if (ObjectUtils.isEmpty(application)) {
            return loginMessage;
        }

        //根据username 拿 group
//        GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
//        groupUserCriteria.createCriteria().andUserIdEqualTo(userCredential.getUserId());
//        List<GroupUser> groupUserList = groupUserService.selectByCriteria(groupUserCriteria);
//
//        if (CollectionUtils.isEmpty(groupUserList)) {
//            return loginMessage;
//        }
//        //根据group 拿 role
//        List<Long> groupIds = new ArrayList<>();
//        for (GroupUser groupUser : groupUserList) {
//            Long groupId = groupUser.getGroupId();
//            groupIds.add(groupId);
//        }

        //根据TenantIdList 拿Groups
        GroupCriteria groupCriteria = new GroupCriteria();
        groupCriteria.createCriteria().andTenantIdIn(tenantIdList);
        List<Group> groups = groupService.selectByCriteria(groupCriteria);

        if (CollectionUtils.isEmpty(groups)) {
            return loginMessage;
        }

        //根据group 拿 role
        List<Long> groupIds = new ArrayList<>();
        for (Group group : groups) {
            groupIds.add(group.getId());
        }

        //通过中间表拿关联的role
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andGroupIdIn(groupIds);
        List<GroupRole> groupRoleList = groupRoleService.selectByCriteria(groupRoleCriteria);
        if (CollectionUtils.isEmpty(groupRoleList)) {
            return loginMessage;
        }

        List<Long> roleIds = new ArrayList<>();
        for (GroupRole groupRole : groupRoleList) {
            Long roleId = groupRole.getRoleId();
            roleIds.add(roleId);
        }
        //根据role拿permission
        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdIn(roleIds);
        List<RolePermission> rolePermissionList = rolePermissionService.selectByCriteria(rolePermissionCriteria);
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            return loginMessage;
        }
        List<Long> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionList) {
            Long permissionId = rolePermission.getPermissionId();
            permissionIds.add(permissionId);
        }
        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andIdIn(permissionIds).andStatusEqualTo(1);
        List<Permission> permissionList = permissionService.selectByCriteria(permissionCriteria);
        if (CollectionUtils.isEmpty(permissionList)) {
            return loginMessage;
        }

        //通过权限的通配符 查询相对应的resource
        List<Resource> resourceList = new ArrayList<>();
        for (Permission permission : permissionList) {
            String resourcePathWildcard = permission.getResourcePathWildcard();
            if (!StringUtils.isEmpty(resourcePathWildcard)) {
                ResourceCriteria resourceCriteria = new ResourceCriteria();
                resourceCriteria.createCriteria().andPathLike(resourcePathWildcard).andApplicationIdEqualTo(application.getId());
                List<Resource> resources = resourceService.selectByCriteria(resourceCriteria);
                resourceList.addAll(resources);
            }
        }
        if (CollectionUtils.isEmpty(resourceList)) {
            return loginMessage;
        }

        //通过resource列表获取 ResourceDynamicProperty列表
        List<List<ResourceDynamicProperty>> resourceDynamicPropertyList = new ArrayList<>();
        List<Resource> resourcesWithAction = new ArrayList<>();
        if (resourceList.size() > 0) {
            for (Resource resource : resourceList) {
                ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
                resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(resource.getType());
                List<ResourceDynamicProperty> resourceDynamicProperties = resourceDynamicPropertyService.selectByCriteria(resourceDynamicPropertyCriteria);
                resourceDynamicPropertyList.add(resourceDynamicProperties);
                resource.setActions(resourceDynamicProperties.get(0).getActions());
                resourcesWithAction.add(resource);
            }
        }

        //对resource列表进行去重处理
        LoginMessage loginMessage1 = this.getResourcesDstinct(permissionList, resourcesWithAction);


//        loginMessage.setResourceList(loginMessage1.getResourceList());
        loginMessage.setResourceDynamicPropertyList(loginMessage1.getResourceDynamicPropertyList());
        loginMessage.setRefreshTokenResourceList(loginMessage1.getRefreshTokenResourceList());
//        loginMessage.setGetResourceListWithEdit(loginMessage1.getGetResourceListWithEdit());
//        loginMessage.setResourceListWithReadlyOnly(loginMessage1.getResourceListWithReadlyOnly());
        return loginMessage;
    }

    /**
     * 对resource列表进行去重
     * 1. 根据permission的action匹配资源
     * 2. 对resource去重
     * 3. 通过resource列表获取 ResourceDynamicProperty列表
     * @param resourceList
     * @return
     */
    private LoginMessage getResourcesDstinct(List<Permission> permissionList, List<Resource> resourceList) {
        LoginMessage loginMessage = new LoginMessage();

        List<RefreshTokenResourceView> refreshTokenResourceViews = new ArrayList<>();

        //根据permission的action匹配资源
        List<Resource> resourceList1 = getResources(permissionList, resourceList);

        //对resource去重
        Set<Long> resourceSet = new HashSet<>();
        List<Resource> resourceList2 = new ArrayList<>();
        RefreshTokenResourceView refreshTokenResourceView;
        for (Resource resource : resourceList1) {
            Long resourceId = resource.getId();
            if (!resourceSet.contains(resourceId)) {
                resourceSet.add(resourceId);
                resourceList2.add(resource);
            }
        }

        for (Resource resource : resourceList2) {
            refreshTokenResourceView = new RefreshTokenResourceView();
            daoToViewCopier.copy(resource, refreshTokenResourceView, null);
            refreshTokenResourceView.setAction(resource.getActions());
            refreshTokenResourceViews.add(refreshTokenResourceView);
        }


        //通过resource列表获取 ResourceDynamicProperty列表
        List<List<ResourceDynamicProperty>> resourceDynamicPropertyList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resourceList2) && resourceList2.size() > 0) {
            for (Resource resource : resourceList2) {
                ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
                resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(resource.getType());
                List<ResourceDynamicProperty> resourceDynamicProperties = resourceDynamicPropertyService.selectByCriteria(resourceDynamicPropertyCriteria);
                resourceDynamicPropertyList.add(resourceDynamicProperties);
            }
        }



        loginMessage.setRefreshTokenResourceList(refreshTokenResourceViews);
        loginMessage.setResourceList(resourceList2);
        loginMessage.setResourceDynamicPropertyList(resourceDynamicPropertyList);
        return loginMessage;
    }

    /**
     * 根据权限中的action匹配resources
     * @param permissionList
     * @param resourceList
     * @return
     */
    private List<Resource> getResources(List<Permission> permissionList, List<Resource> resourceList) {
        List<Resource> resourceList1 = new ArrayList<>();
        for (Permission permission : permissionList) {
            String action = permission.getAction();
            String pattern = ".*" + action + ".*";
            for (Resource resource : resourceList) {
                String actions = resource.getActions();

                //add by ming 这句是添加的
                resource.setActions(action);

                if (actions.matches(pattern)) {
                    String[] actionList = actions.split(">");
                    //如果不是同级，返回action内容
                    if (actionList.length > 1) {
                        //处理要返回的action值
                        String action1 = this.getAction(action);

                        resource.setActions(action1);

                    }
                    resourceList1.add(resource);
                }
            }
        }
        return resourceList1;
    }

    /**
     * 处理要返回的action值
     * @param action
     * @return
     */
    private String getAction(String action) {

        if ("read".equals(action)) {
            return action;
        } else {
            String action1 = "edit";
            return action1;
        }
    }

    private LoginMessage checkRefreshTokenData(TokenRefreshView tokenRefreshView) {
        LoginMessage loginMessage = new LoginMessage();
        String tokenWithAppCode = tokenRefreshView.getRefreshToken();
        String[] tokenParams = tokenWithAppCode.split("#");
        System.out.println(tokenParams.length);
        String token = tokenParams[0];
        String appCode = tokenParams[1];
        String userName = tokenParams[2];
        String createTime = tokenParams[3];


        if (StringUtils.isEmpty(userName) || !userName.equals(tokenRefreshView.getUserName())) {
            loginMessage.setMessage("token错误");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        //如果要跳转的appCode不存在，返回错误
        if (StringUtils.isEmpty(tokenRefreshView.getAppCode())) {
            loginMessage.setMessage("appCode不能为空");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }


        //根据username 查询密码，如果查不到则账号有误
        User user = userService.getUserByUserName(userName);
        if (ObjectUtils.isEmpty(user)) {
            loginMessage.setMessage("token错误");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        UserCredential userCredential = userCredentialService.getCredentialByUserName(userName);
        if (ObjectUtils.isEmpty(userCredential)) {
            loginMessage.setMessage(MessageDescription.LOGIN_USERNAME_NOT_EXIST);
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        String password = userCredential.getCredential();
        Map<String, Claim> tokenPlayload = null;
        try {
            //用密码解密token
            tokenPlayload = JwtToken.verifyToken(token, password);
        } catch (Exception e) {
            loginMessage.setMessage("账号错误,请输入正确账号");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        //初始登录的应用 所在的组
        String routerGroupName = routerGroupService.getGroupNameByAppCode(appCode);
        //要跳转的应用 所在的组
        String routerGroupNameGo = routerGroupService.getGroupNameByAppCode(tokenRefreshView.getAppCode());
        //应用没有被添加进应用组或不在同一个应用组，无法访问
        if (StringUtils.isEmpty(routerGroupName) || StringUtils.isEmpty(routerGroupNameGo) || !routerGroupName.equals(routerGroupNameGo)) {
            loginMessage.setMessage("应用不存在或不在同一个应用组");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(403);
            return loginMessage;
        }
        return loginMessage;
    }


    @Override
    public LoginMessage garnetRefreshToken(LoginView loginView) throws Exception {
        //能通过拦截器，说明token是正确且有效的,故不再验证token是否正确
        LoginMessage loginMessage = new LoginMessage();

        //取出token中携带的信息
        String tokenWithAppCode = loginView.getToken();

        if (StringUtils.isEmpty(tokenWithAppCode)) {
            throw new RuntimeException("token不能为空");
        }

        String[] tokenParams = tokenWithAppCode.split("#");
        String token = tokenParams[0];
        String appCode = tokenParams[1];
        String userName = tokenParams[2];
        String createTime = tokenParams[3];

        //如果要跳转的appCode不存在，返回错误
        if (StringUtils.isEmpty(loginView.getAppCode())) {
            loginMessage.setMessage("appCode不能为空");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        //根据username 查询密码，如果查不到则账号有误
        UserCredential userCredential = userCredentialService.getCredentialByUserName(userName);
        if (ObjectUtils.isEmpty(userCredential)) {
            loginMessage.setMessage("用户不存在");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        String password = userCredential.getCredential();
        Map<String, Claim> tokenPlayload = null;
        try {
            //用密码解密token
            tokenPlayload = JwtToken.verifyToken(token, password);
        } catch (Exception e) {
            loginMessage.setMessage("账号或密码错误");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        //初始登录的应用 所在的组
        String routerGroupName = routerGroupService.getGroupNameByAppCode(appCode);
        //应用没有被添加进应用组或不在同一个应用组，无法访问
        if (StringUtils.isEmpty(routerGroupName)) {
            loginMessage.setMessage("没有权限");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(403);
            return loginMessage;
        }

        //更新token
        Long currentTime = System.currentTimeMillis();
        String tokenNew = JwtToken.createToken(userName, password, appCode, currentTime);
        String accessTokenReturn = tokenNew + "#" + appCode + "#" + userName + "#" + currentTime + STRING_ACCESSTOKEN;
        String refreshTokenReturn = tokenNew + "#" + appCode + "#" + userName + "#" + currentTime + STRING_REFRESHTOKEN;

        //根据userName拿出token并更新到数据库
        TokenCriteria tokenCriteria = new TokenCriteria();
        tokenCriteria.createCriteria().andUserNameEqualTo(userName);
        Token token1 = tokenService.selectSingleByCriteria(tokenCriteria);
        token1.setToken(tokenNew);
        token1.setModifiedTime(currentTime);
        token1.setExpireTime(currentTime + GarnetContants.TOKEN_EXPIRED_TIME);
        tokenService.updateByPrimaryKeySelective(token1);

        loginMessage.setMessage("token刷新成功");
        loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_SUCCESS);
        loginMessage.setCode(201);
        loginMessage.setAccessToken(accessTokenReturn);
        loginMessage.setRefreshToken(refreshTokenReturn);

        return loginMessage;
    }

    @Override
    public LoginMessage garLogin(GarLoginView garLoginView) throws Exception {
        UserCriteria userCriteria = new UserCriteria();
        LoginMessage loginMessage = new LoginMessage();
        userCriteria.createCriteria().andUserNameEqualTo(garLoginView.getUserName()).andStatusEqualTo(1);
        User user = this.selectSingleByCriteria(userCriteria);

        if (StringUtils.isEmpty(garLoginView.getUserName()) || StringUtils.isEmpty(garLoginView.getPassword())) {
            loginMessage.setMessage("账号或密码为空");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        if (ObjectUtils.isEmpty(user)) {
            loginMessage.setMessage("用户不存在");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        //获取密码,验证密码是否正确
        UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();
        userCredentialCriteria.createCriteria().andUserIdEqualTo(user.getId());
        UserCredential userCredential = userCredentialService.selectSingleByCriteria(userCredentialCriteria);
        if (!userCredential.getCredential().equals(garLoginView.getPassword())) {
            loginMessage.setMessage("密码不正确");
            loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_FALSE);
            loginMessage.setCode(401);
            return loginMessage;
        }

        //生成token返回
        String token = JwtToken.createToken(user.getUserName(), userCredential.getCredential(), garLoginView.getAppCode(), System.currentTimeMillis());
        loginMessage.setMessage("登录成功");
        loginMessage.setCode(201);
        loginMessage.setUser(user);
        loginMessage.setLoginStatus(LOGINMESSAGE_STATUS_SUCCESS);
        //拼接返回前端的token
        String accessTokenReturn = token + "#" + garLoginView.getAppCode() + "#" + garLoginView.getUserName() + "#" + System.currentTimeMillis() + "#access_token";
        String refreshTokenReturn = token + "#" + garLoginView.getAppCode() + "#" + garLoginView.getUserName() + "#" + System.currentTimeMillis() + "#refresh_token";
        loginMessage.setAccessToken(accessTokenReturn);
        loginMessage.setRefreshToken(refreshTokenReturn);

        TokenCriteria tokenCriteria = new TokenCriteria();
        tokenCriteria.createCriteria().andUserNameEqualTo(garLoginView.getUserName());
        Token tokenOld = tokenService.selectSingleByCriteria(tokenCriteria);

        //如果是第一次登录，将token插入数据库
        if (ObjectUtils.isEmpty(tokenOld)) {
            Token token1 = new Token();
            token1.setCreatedTime(System.currentTimeMillis());
            token1.setModifiedTime(System.currentTimeMillis());
            Long expiredTime = token1.getCreatedTime() + 30 * 60000L;
            token1.setExpireTime(expiredTime);
            token1.setId(IdGeneratorUtil.generateId());
            token1.setUserName(garLoginView.getUserName());
            token1.setToken(token);
            tokenService.insertSelective(token1);
        } else {
            //不是第一次登录，更新token
            tokenOld.setToken(token);
            tokenOld.setModifiedTime(System.currentTimeMillis());
            Long expiredTime = tokenOld.getModifiedTime() + GarnetContants.TOKEN_EXPIRED_TIME;
            tokenOld.setExpireTime(expiredTime);
            tokenOld.setToken(token);
            tokenService.updateByPrimaryKeySelective(tokenOld);
        }
        return loginMessage;
    }

    @Override
    public ReturnTenantIdView getTenantIdsByUserId(Long userId) {
        //根据userId 查 tenantId列表
        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andUserIdEqualTo(userId);
        List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);
        List<Long> tenantIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userTenants) && userTenants.size() > 0) {
            for (UserTenant userTenant : userTenants) {
                tenantIds.add(userTenant.getTenantId());
            }
        }

        //获取权限列表
        List<Permission> permissions = getPermissions(userId);


        List<Resource> resourceList = new ArrayList<>();
        for (Permission permission : permissions) {
            ResourceCriteria resourceCriteria = new ResourceCriteria();
            resourceCriteria.createCriteria().andTenantIdIn(tenantIds).andPathLike(permission.getResourcePathWildcard());
            resourceList.addAll(resourceService.selectByCriteria(resourceCriteria));
        }

        //判断是否拥有超级权限
        boolean flag = false;
        for (Resource resource : resourceList) {
            if (GarnetContants.RESOURCE_PERMISSION.equals(resource.getVarchar00())) {
                flag = true;
            }
        }

        ReturnTenantIdView returnTenantIdView = new ReturnTenantIdView();
        returnTenantIdView.setSuperAdmin(flag);

        if (flag) {
            TenantCriteria tenantCriteria = new TenantCriteria();
            tenantCriteria.createCriteria();
            List<Tenant> tenants = tenantService.selectByCriteria(tenantCriteria);
            List<Long> teantIdList = new ArrayList<>();
            for (Tenant tenant : tenants) {
                teantIdList.add(tenant.getId());
            }

            teantIdList = commonService.dealTenantIdsIfGarnet(userId, teantIdList);
            returnTenantIdView.setTenantIds(teantIdList);
            return returnTenantIdView;
        } else {
            tenantIds = commonService.dealTenantIdsIfGarnet(userId, tenantIds);
            returnTenantIdView.setTenantIds(tenantIds);
            return returnTenantIdView;
        }

    }

    private List<Permission> getPermissions(Long userId) {
        //change by ming
        GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
        groupUserCriteria.createCriteria().andUserIdEqualTo(userId);
        List<GroupUser> groupUsers = groupUserService.selectByCriteria(groupUserCriteria);

        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();

        List<Long> groupIds = new ArrayList<>();
        for (GroupUser groupUser : groupUsers) {
            groupIds.add(groupUser.getGroupId());
        }

        if (groupIds.size() == 0) {
            groupIds.add(GarnetContants.NON_VALUE);
        }

        groupRoleCriteria.createCriteria().andGroupIdIn(groupIds);
        List<GroupRole> groupRoles = groupRoleService.selectByCriteria(groupRoleCriteria);
        List<Long> roleIds = new ArrayList<>();
        for (GroupRole groupRole : groupRoles) {
            roleIds.add(groupRole.getRoleId());
        }

        if (roleIds.size() == 0) {
            roleIds.add(GarnetContants.NON_VALUE);
        }

        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdIn(roleIds);

        List<RolePermission> rolePermissions = rolePermissionService.selectByCriteria(rolePermissionCriteria);

        List<Long> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            permissionIds.add(rolePermission.getPermissionId());
        }

        if (permissionIds.size() == 0) {
            permissionIds.add(GarnetContants.NON_VALUE);
        }

        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andIdIn(permissionIds).andStatusEqualTo(1);

        //====================
        return permissionService.selectByCriteria(permissionCriteria);
    }

    @Override
    public void updateUserPassword(UserCredentialView userCredentialView) {
        if (ObjectUtils.isEmpty(userCredentialView) || ObjectUtils.isEmpty(userCredentialView.getUserId()) ||
                StringUtils.isEmpty(userCredentialView.getPassword()) || StringUtils.isEmpty(userCredentialView.getNewPassword())) {
            throw new RuntimeException("请输入正确参数");
        }

        UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();
        userCredentialCriteria.createCriteria().andUserIdEqualTo(userCredentialView.getUserId());
        UserCredential userCredential = userCredentialService.selectSingleByCriteria(userCredentialCriteria);
        if (!ObjectUtils.isEmpty(userCredential)) {
            if (userCredentialView.getPassword().equals(userCredential.getCredential())) {

                if (userCredentialView.getNewPassword().equals(userCredential.getCredential())) {
                    throw new RuntimeException("新密码不能与旧密码相同");
                }

                userCredential.setModifiedTime(System.currentTimeMillis());
                userCredential.setCredential(userCredentialView.getNewPassword());
                userCredentialService.updateByPrimaryKeySelective(userCredential);
            } else {
                throw new RuntimeException("初始密码不正确");
            }
        }
    }

    @Override
    public List<User> queryUsers() {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.createCriteria();
        List<User> userList = this.selectByCriteria(userCriteria);
        return userList;
    }

    @Override
    public List<User> queryUserByApplicationId(UserParm userParm) {
        Long applicationId = userParm.getApplicationId();
        Long tenantId = userParm.getTenantId();

        ApplicationTenantCriteria applicationTenantCriteria = new ApplicationTenantCriteria();
        ApplicationTenantCriteria.Criteria criteria = applicationTenantCriteria.createCriteria();

        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0) {
            criteria.andTenantIdEqualTo(tenantId);
        }

        if (!ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0) {
            criteria.andApplicationIdEqualTo(applicationId);
        }

        List<ApplicationTenant> applicationTenantList = applicationTenantService.selectByCriteria(applicationTenantCriteria);

        if (CollectionUtils.isEmpty(applicationTenantList) || applicationTenantList.size() == 0) {
            return new ArrayList<>();
        }

        List<Long> tenantIdList = new ArrayList<>();
        for (ApplicationTenant applicationTenant : applicationTenantList) {
            tenantIdList.add(applicationTenant.getTenantId());
        }

        List<Long> userIdList = getUserIdsByTenantIds(tenantIdList);

        UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
        userTenantCriteria.createCriteria().andTenantIdIn(tenantIdList).andUserIdEqualTo(GarnetContants.GARNET_USER_ID);
        List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);
        if (!CollectionUtils.isEmpty(userTenants) && userTenants.size() > 0) {
            userIdList.add(GarnetContants.GARNET_USER_ID);
        }

        List<User> userList = new ArrayList<>();
        User user;
        for (Long userId : userIdList) {
            user = this.selectByPrimaryKey(userId);
            userList.add(user);
        }

        return userList;
    }

    @Override
    public List<User> queryUserByParams(UserParm userParm) {
        Long applicationId = userParm.getApplicationId();
        Long tenantId = userParm.getTenantId();

        List<User> userList;
        if ((!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0) && (ObjectUtils.isEmpty(applicationId) || applicationId.longValue() == 0)) {
            userList = this.queryUserByTenantId(userParm);
        } else {
            userList = this.queryUserByApplicationId(userParm);
        }

        return userList;
    }
}