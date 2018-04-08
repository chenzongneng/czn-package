package com.richstonedt.garnet;


import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.GroupUser;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.UserTenant;
import com.richstonedt.garnet.model.criteria.UserCriteria;
import com.richstonedt.garnet.model.parm.UserParm;
import com.richstonedt.garnet.model.view.UserView;
import com.richstonedt.garnet.service.UserService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//按方法的字典序执行
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {}

    @Test
    public void test1QueryUsersByParms() {
        UserParm userParm = new UserParm();
        userParm.setPageNumber(1);
        userParm.setPageSize(10);
        userParm.setUserId(GarnetContants.GARNET_USER_ID);

        PageUtil pageUtil = userService.queryUsersByParms(userParm);

        Assert.assertEquals(pageUtil.getList().size(), 1);
    }

    @Test
    public void test2InsertUser() throws ParseException {
        User user = new User();
        UserView userView = new UserView();
        user.setUserName("test");
        user.setBelongToGarnet("N");
        userView.setUser(user);
        userView.setPassword("test");
        Long userId = userService.insertUser(userView);

        User user1 = userService.selectByPrimaryKey(userId);

        Assert.assertNotNull(user1);
    }

    @Test
    public void test3UpdateUser() {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.createCriteria().andUserNameEqualTo("test");
        User user = userService.selectSingleByCriteria(userCriteria);
        Assert.assertNotNull(user);

        UserView userView = new UserView();
        user.setUserName("test_user_update");
        userView.setUser(user);

        userService.updateUser(userView);

        UserCriteria userCriteria1 = new UserCriteria();
        userCriteria1.createCriteria().andUserNameEqualTo("test_user_update");
        User users1 = userService.selectSingleByCriteria(userCriteria1);

        Assert.assertNotNull(users1);
    }

    @Test
    public void test4DeleteUser() {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.createCriteria().andUserNameEqualTo("test_user_update");
        User user = userService.selectSingleByCriteria(userCriteria);
        Assert.assertNotNull(user);

        userService.updateStatusById(user);

        UserCriteria userCriteria1 = new UserCriteria();
        userCriteria1.createCriteria().andUserNameEqualTo("test_user_update");
        User user1 = userService.selectSingleByCriteria(userCriteria);
        int status = user1.getStatus();
        Assert.assertEquals(status, 0);

        userService.deleteByPrimaryKey(user.getId());
    }

//    @Test
//    public void testUserInsert() throws ParseException {
//
//        UserView userView = new UserView();
//
//        User user = new User();
//
//        user.setUserName("minglee");
//
//        userView.setPassword("123456");
//
//        userView.setExpiredDateTime(new Date().getTime());
//
//        userView.setUser(user);
//
//        List<UserTenant> userTenants = new ArrayList<UserTenant>();
//
//        UserTenant userTenant = new UserTenant();
//
//        userTenant.setTenantId(100L);
//
//        userTenants.add(userTenant);
//
//        List<GroupUser> groupUsers = new ArrayList<GroupUser>();
//
//        GroupUser groupUser = new GroupUser();
//
//        groupUser.setGroupId(200L);
//
//        groupUsers.add(groupUser);
//
//        userView.setUserTenants(userTenants);
//
//        userView.setGroupUsers(groupUsers);
//
//        userService.insertUser(userView);
//
//    }
//
//    @Test
//    public void testUserUpdate(){
//
//        UserView userView = new UserView();
//
//        User user = new User();
//
//        user.setId(1519703398L);
//
//        user.setUserName("minglee");
//
//        userView.setPassword("123456");
//
//        userView.setExpiredDateTime(new Date().getTime());
//
//        userView.setUser(user);
//
//        List<UserTenant> userTenants = new ArrayList<UserTenant>();
//
//        UserTenant userTenant = new UserTenant();
//
//        userTenant.setTenantId(500L);
//
//        userTenants.add(userTenant);
//
//        List<GroupUser> groupUsers = new ArrayList<GroupUser>();
//
//        GroupUser groupUser = new GroupUser();
//
//        groupUser.setGroupId(600L);
//
//        groupUsers.add(groupUser);
//
//        userView.setUserTenants(userTenants);
//
//        userView.setGroupUsers(groupUsers);
//
//        userService.updateUser(userView);
//
//
//    }

}
