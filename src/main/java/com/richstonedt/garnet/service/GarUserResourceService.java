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

    Map<String, Boolean> getCodeMapListByUserId(Long userId, Long appId);

    Map<String, Boolean> getResourceCodeByUserIdAndAppCode(Long userId, String appCode);

}
