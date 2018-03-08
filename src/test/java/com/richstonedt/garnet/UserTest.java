package com.richstonedt.garnet;


import com.richstonedt.garnet.model.GroupUser;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.UserTenant;
import com.richstonedt.garnet.model.view.UserView;
import com.richstonedt.garnet.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
   private UserService userService;


    @Test
    public void testUserInsert() throws ParseException {

        UserView userView = new UserView();

        User user = new User();

        user.setUserName("minglee");

        userView.setPassword("123456");

        userView.setExpiredDateTime(new Date().getTime());

        userView.setUser(user);

        List<UserTenant> userTenants = new ArrayList<UserTenant>();

        UserTenant userTenant = new UserTenant();

        userTenant.setTenantId(100L);

        userTenants.add(userTenant);

        List<GroupUser> groupUsers = new ArrayList<GroupUser>();

        GroupUser groupUser = new GroupUser();

        groupUser.setGroupId(200L);

        groupUsers.add(groupUser);

        userView.setUserTenants(userTenants);

        userView.setGroupUsers(groupUsers);

        userService.insertUser(userView);

    }

    @Test
    public void testUserUpdate(){

        UserView userView = new UserView();

        User user = new User();

        user.setId(1519703398L);

        user.setUserName("minglee");

        userView.setPassword("123456");

        userView.setExpiredDateTime(new Date().getTime());

        userView.setUser(user);

        List<UserTenant> userTenants = new ArrayList<UserTenant>();

        UserTenant userTenant = new UserTenant();

        userTenant.setTenantId(500L);

        userTenants.add(userTenant);

        List<GroupUser> groupUsers = new ArrayList<GroupUser>();

        GroupUser groupUser = new GroupUser();

        groupUser.setGroupId(600L);

        groupUsers.add(groupUser);

        userView.setUserTenants(userTenants);

        userView.setGroupUsers(groupUsers);

        userService.updateUser(userView);


    }

}
