package com.richstonedt.garnet.service;

import java.util.Map;

/**
 * <b><code>GarUserResourceService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:59
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarUserResourceService {

    /**
     * Gets code map list by user id.
     *
     * @param userId the user id
     * @param appId  the app id
     * @return the code map list by user id
     * @since garnet-core-be-fe 0.1.0
     */
    Map<String, Boolean> getCodeMapListByUserId(Long userId, Long appId);

    /**
     * Gets resource code by user id and app code.
     *
     * @param userId  the user id
     * @param appCode the app code
     * @return the resource code by user id and app code
     * @since garnet-core-be-fe 0.1.0
     */
    Map<String, Boolean> getResourceCodeByUserIdAndAppCode(Long userId, String appCode);

}
