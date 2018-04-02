package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.criteria.UserCriteria;
import com.richstonedt.garnet.model.view.UserView;
import com.richstonedt.garnet.service.CommonService;
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

    @Override
    public List<Long> dealTenantIdsIfGarnet(Long userId, List<Long> tenantIds) {

        UserView userView = userService.getUserById(userId);

        System.out.println("userId: "+userId+" , tenantIds: "+tenantIds.size());

        if (("N").equals(userView.getUser().getBelongToGarnet())) {

            List<Long> tempIds = new ArrayList<>();

            for (Long tenantId :
                    tenantIds) {
                System.out.println("tenantID: "+tenantId);
                if(tenantId != GarnetContants.GARNET_TENANT_ID){

                    System.out.println("tenantAdd: "+tenantId);
                    tempIds.add(tenantId);

                }
            }

            return tempIds;

        }

        return tenantIds;
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
