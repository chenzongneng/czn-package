package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.GroupUser;
import com.richstonedt.garnet.model.RouterGroup;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.criteria.GroupUserCriteria;
import com.richstonedt.garnet.model.criteria.UserCriteria;
import com.richstonedt.garnet.model.view.UserView;
import com.richstonedt.garnet.service.CommonService;
import com.richstonedt.garnet.service.GroupService;
import com.richstonedt.garnet.service.GroupUserService;
import com.richstonedt.garnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {


    @Autowired
    private UserService userService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private GroupService groupService;

    @Override
    public List<Long> dealTenantIdsIfGarnet(Long userId, List<Long> tenantIds) {

        UserView userView = userService.getUserById(userId);

        if (("N").equals(userView.getUser().getBelongToGarnet())) {

            List<Long> tempIds = new ArrayList<>();

            for (Long tenantId : tenantIds) {
                if(tenantId.longValue() != GarnetContants.GARNET_TENANT_ID.longValue()){
                    tempIds.add(tenantId);
                }
            }

            return tempIds;

        }

        return tenantIds;
    }

    @Override
    public List<Long> dealGroupIdsIfGarnet(Long userId, List<Long> groupIds) {

        boolean b = this.superAdminBelongGarnet(userId);

        List<Long> returnGroupIds = new ArrayList<>();

        //如果不是超级管理员
        if (!b) {
            GroupCriteria groupCriteria = new GroupCriteria();
            groupCriteria.createCriteria().andIdIn(groupIds);
            List<Group> groups = groupService.selectByCriteria(groupCriteria);

            for(Group group : groups){
                if(group.getTenantId() != GarnetContants.GARNET_TENANT_ID){
                    returnGroupIds.add(group.getId());
                }
            }

            return returnGroupIds;
        } else {
            //如果是超级管理员，原封返回
            return groupIds;
        }
    }

    @Override
    public boolean superAdminBelongGarnet(Long userId) {
        User user = userService.selectByPrimaryKey(userId);
        if (!ObjectUtils.isEmpty(user) && "Y".equals(user.getBelongToGarnet())) {
            return true;
        }
        return false;
    }


}
