package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.criteria.GroupUserCriteria;
import com.richstonedt.garnet.model.criteria.UserCriteria;
import com.richstonedt.garnet.model.view.UserView;
import com.richstonedt.garnet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {


    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired LogService logService;

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

        //是否是garnet用户
        boolean b = this.superAdminBelongGarnet(userId);
        List<Long> returnGroupIds = new ArrayList<>();

        //如果不是Garnet用户
        if (!b) {
            GroupCriteria groupCriteria = new GroupCriteria();
            groupCriteria.createCriteria().andIdIn(groupIds);
            List<Group> groups = groupService.selectByCriteria(groupCriteria);

            for(Group group : groups){
                if(group.getTenantId().longValue() != GarnetContants.GARNET_TENANT_ID.longValue()){
                    returnGroupIds.add(group.getId());
                }
            }
            return returnGroupIds;
        } else {
            //如果是，原封返回
            return groupIds;
        }
    }

    @Override
    public boolean insertLog(String meaasge, String operation){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String cookie = request.getHeader("Cookie");
        if (StringUtils.isEmpty(cookie)) {
            return false;
        }
        String userName = this.getCookieValue(cookie, "userName");

        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return false;
        }

        Long time = System.currentTimeMillis();

        Log log = new Log();
        log.setId(IdGeneratorUtil.generateId());
        log.setMessage(meaasge);
        log.setUserName(userName);
        log.setCreatedTime(time);
        log.setModifiedTime(time);
        log.setOperation(operation);
        log.setIp(ip);

        logService.insertSelective(log);

        return true;
    }

    @Override
    public boolean superAdminBelongGarnet(Long userId) {
        User user = userService.selectByPrimaryKey(userId);
        if (!ObjectUtils.isEmpty(user) && "Y".equals(user.getBelongToGarnet())) {
            return true;
        }
        return false;
    }

    /**
     * 获取Cookie中存的值
     * @param cookie
     * @param name
     * @return
     */
    private String getCookieValue(String cookie, String name) {

        String n = name + "=";

        String[] c = cookie.split(";");

        for (String s : cookie.split(";")) {
            String s1 = s.trim();
            if (s1.indexOf(n) == 0) {
                return s1.substring(n.length(), s1.length());
            }
        }

        return "";
    }


}
