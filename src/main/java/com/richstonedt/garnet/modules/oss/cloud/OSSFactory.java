/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.oss.cloud;

import com.richstonedt.garnet.common.utils.ConfigConstant;
import com.richstonedt.garnet.common.utils.Constant;
import com.richstonedt.garnet.common.utils.SpringContextUtils;
import com.richstonedt.garnet.modules.sys.service.SysConfigService;

/**
 * 文件上传Factory
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 10:18
 */
public final class OSSFactory {

    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build() {
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            return new QcloudCloudStorageService(config);
        }

        return null;
    }

}
