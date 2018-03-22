package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceException;
import com.richstonedt.garnet.interceptory.JwtToken;
import com.richstonedt.garnet.interceptory.LoginMessage;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.UserMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.*;
import com.richstonedt.garnet.model.parm.UserParm;
import com.richstonedt.garnet.model.view.LoginView;
import com.richstonedt.garnet.model.view.UserProfile;
import com.richstonedt.garnet.model.view.UserView;
import com.richstonedt.garnet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserCriteria, Long> implements UserService {
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

    @Override
    public BaseMapper getBaseMapper() {
        return this.userMapper;
    }

    @Override
    public Long insertUser(UserView userView) throws ParseException {

        String credential = userView.getPassword();

        User user = userView.getUser();

        user.setId(IdGeneratorUtil.generateId());

        UserCredential userCredential = new UserCredential();

        userCredential.setExpiredDateTime(userView.getExpiredDateTime());

        userCredential.setCredential(credential);

        userCredential.setId(IdGeneratorUtil.generateId());

        userCredential.setUserId(user.getId());

        Long currentTime = new Date().getTime();

        userCredential.setCreatedTime(currentTime);

        userCredential.setModifiedTime(currentTime);

        user.setCreatedTime(currentTime);

        user.setModifiedTime(currentTime);

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

            for (GroupUser groupUser : userView.getGroupUsers()
                    ) {

                groupUser.setId(IdGeneratorUtil.generateId());

                groupUser.setUserId(user.getId());

                groupUserService.insertSelective(groupUser);

            }

        }

        return user.getId();

    }

    @Override
    public void updateUser(UserView userView) {


        //更新密码表

        UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();

        userCredentialCriteria.createCriteria().andUserIdEqualTo(userView.getUser().getId());

        UserCredential userCredential = userCredentialService.selectSingleByCriteria(userCredentialCriteria);

        if (!userCredential.getExpiredDateTime().equals(userView.getExpiredDateTime())) {

            userCredential.setExpiredDateTime(userView.getExpiredDateTime());
            userCredential.setModifiedTime(new Date().getTime());

        }

        if (!userCredential.getCredential().equals(userView.getPassword())) {

            userCredential.setCredential(userView.getPassword());
            userCredential.setModifiedTime(new Date().getTime());
        }

        userCredentialService.updateByPrimaryKeySelective(userCredential);

        //更新User表
        User user = userView.getUser();
        user.setModifiedTime(new Date().getTime());
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

            for (GroupUser groupUser : userView.getGroupUsers()
                    ) {

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


    }

    @Override
    public PageUtil queryUsersByParms(UserParm userParm) {

        UserCriteria userCriteria = new UserCriteria();

        userCriteria.createCriteria().andStatusEqualTo(1);
        //获取tenantId,获取统一租户下的所有用户的列表
        if (!ObjectUtils.isEmpty(userParm.getTenantId())) {
            UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
            userTenantCriteria.createCriteria().andTenantIdEqualTo(userParm.getTenantId());
            List<UserTenant> userTenants = userTenantService.selectByCriteria(userTenantCriteria);

            //根据中间表获取User的具体信息并且返回
            List<Long> userTenantIds = new ArrayList<>();
            for (UserTenant userTenant : userTenants) {
                userTenantIds.add(userTenant.getUserId());
            }

            if (userTenantIds.size() == 0) {
                userTenantIds.add(GarnetContants.NON_VALUE);
            }

            userCriteria.createCriteria().andIdIn(userTenantIds);

        }

        PageUtil result = new PageUtil(this.selectByCriteria(userCriteria), (int) this.countByCriteria(userCriteria), userParm.getPageSize(), userParm.getPageNumber());

        return result;
    }

    @Override
    public LoginMessage userLogin(LoginView loginView) throws Exception {

        UserCriteria userCriteria = new UserCriteria();
        LoginMessage loginMessage = new LoginMessage();
        userCriteria.createCriteria().andUserNameEqualTo(loginView.getUserName());
        User user = this.selectSingleByCriteria(userCriteria);
        if (ObjectUtils.isEmpty(user)) {
            loginMessage.setMessage("用户不存在");
            loginMessage.setLoginStatus("false");
            loginMessage.setCode(401);
            return loginMessage;
        }

        //获取密码,验证密码是否正确
        UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();
        userCredentialCriteria.createCriteria().andUserIdEqualTo(user.getId());
        UserCredential userCredential = userCredentialService.selectSingleByCriteria(userCredentialCriteria);
        if (!userCredential.getCredential().equals(loginView.getPassword())) {
            loginMessage.setMessage("密码不正确");
            loginMessage.setLoginStatus("false");
            loginMessage.setCode(401);
            return loginMessage;

        }

        //生成token返回
        String token = JwtToken.createToken(user.getUserName(), loginView.getAppCode(),userCredential.getCredential());
        loginMessage.setToken(token);
        loginMessage.setUser(user);
        loginMessage.setLoginStatus("success");

        //将token插入数据库
        if (StringUtils.isEmpty(loginView.getAppCode())) {
            loginMessage.setMessage("请选择要登录的应用");
            loginMessage.setLoginStatus("false");
            loginMessage.setCode(403);
            return loginMessage;
        }

        String routerGroupName = routerGroupService.getGroupNameByAppCode(loginView.getAppCode());
        TokenCriteria tokenCriteria = new TokenCriteria();
        tokenCriteria.createCriteria().andRouterGroupNameEqualTo(routerGroupName);
        Token tokenOld = tokenService.selectSingleByCriteria(tokenCriteria);
        //如果是第一次登录，将token插入数据库
        if (ObjectUtils.isEmpty(tokenOld)) {
            Token token1 = new Token();
            token1.setCreatedTime(System.currentTimeMillis());
            token1.setModifiedTime(System.currentTimeMillis());
            Long expiredTime = token1.getCreatedTime() + 30 * 60000L;
            token1.setExpiredTime(expiredTime);
            token1.setId(IdGeneratorUtil.generateId());
            token1.setRouterGroupName(routerGroupName);
            token1.setToken(token);
            tokenService.insertSelective(token1);
        } else {
            tokenOld.setToken(token);
            tokenOld.setModifiedTime(System.currentTimeMillis());
            Long expiredTime = tokenOld.getModifiedTime() + 30 * 60000L;
            tokenOld.setExpiredTime(expiredTime);
            tokenService.insertSelective(tokenOld);
        }
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
        userCriteria.createCriteria().andUserNameEqualTo(userName);
        User user = this.selectSingleByCriteria(userCriteria);
        return user;
    }

    @Override
    public void updateStatusById(User user) {
        Long currentTime = new Date().getTime();
        user.setModifiedTime(currentTime);
        user.setStatus(0);
        this.updateByPrimaryKeySelective(user);

        //删除关联外键
        if (!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getId())) {
            UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
            userTenantCriteria.createCriteria().andUserIdEqualTo(user.getId());
            userTenantService.deleteByCriteria(userTenantCriteria);
        }

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
                if (!ObjectUtils.isEmpty(user)) users.add(user);
            }
        }

        return users;
    }


}