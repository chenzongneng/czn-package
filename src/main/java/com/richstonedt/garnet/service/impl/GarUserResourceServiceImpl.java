package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarUserResourceDao;
import com.richstonedt.garnet.service.GarUserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <b><code>GarUserResourceServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>11:00
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarUserResourceServiceImpl implements GarUserResourceService {

    /**
     * The User resource dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserResourceDao userResourceDao;

    /**
     * Gets code map list by user id.
     *
     * @param userId the user id
     * @param appId  the app id
     * @return the code map list by user id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public Map<String, Boolean> getCodeMapListByUserId(Long userId, Long appId) {
        List<String> codeList = userResourceDao.getCodeByUserId(userId,appId);
        Map<String, Boolean> buttonMap = new HashMap<>();
        for (String code : codeList) {
            buttonMap.put(code, true);
        }
        return buttonMap;
    }

    /**
     * Gets resource code by user id and app code.
     *
     * @param userId  the user id
     * @param appCode the app code
     * @return the resource code by user id and app code
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public Map<String, Boolean> getResourceCodeByUserIdAndAppCode(Long userId, String appCode) {
        Set<String> codeSet = userResourceDao.getResourceCodeByUserIdAndAppCode(userId,appCode);
        Map<String, Boolean> buttonMap = new HashMap<>();
        for (String code : codeSet) {
            buttonMap.put(code, true);
        }
        return buttonMap;
    }

}
