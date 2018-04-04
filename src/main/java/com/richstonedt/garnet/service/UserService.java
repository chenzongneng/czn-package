package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceException;
import com.richstonedt.garnet.interceptory.LoginMessage;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.criteria.UserCriteria;
import com.richstonedt.garnet.model.parm.UserParm;
import com.richstonedt.garnet.model.view.*;

import java.text.ParseException;
import java.util.List;

/**
 * The interface User service.
 */
public interface UserService extends BaseService<User, UserCriteria, Long> {

    /**
     * Insert user.
     *
     * @param userView the user view
     * @throws ParseException the parse exception
     */
    public  Long insertUser(UserView userView) throws ParseException;

    /**
     * Update user.
     *
     * @param userView the user view
     */
    public  void updateUser(UserView userView);

    /**
     * Delete user.
     *
     * @param userView the user view
     */
    public  void deleteUser(UserView userView);

    /**
     * 按照条件查询user
     */
    public PageUtil queryUsersByParms(UserParm userParm);

    /**
     * 登录验证
     */
    public LoginMessage userLogin(LoginView loginView) throws Exception;

    /**
     * 通过id获取User
     */
    public UserView getUserById(Long userId);

    /**
     * 通过账号拿user
     * @param userName
     * @return
     */
    User getUserByUserName(String userName);

    /**
     * 当用户点击删除时，将其状态设为禁用
     * @param user
     */
    public void updateStatusById(User user);

    public List<User> queryUserByTenantId(UserParm userParm);

    LoginMessage refreshToken(TokenRefreshView tokenRefreshView) throws Exception;

    LoginMessage garnetRefreshToken(LoginView loginView) throws Exception;

    LoginMessage garLogin(GarLoginView garLoginView) throws Exception;

    /**
     * 通过userId查询tenantId列表
     * @param userId
     * @return
     */
    ReturnTenantIdView getTenantIdsByUserId(Long userId);


}