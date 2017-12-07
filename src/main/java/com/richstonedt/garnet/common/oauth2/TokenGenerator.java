/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.oauth2;

import com.richstonedt.garnet.config.GarnetServiceException;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * 生成token
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-20 14:41
 * @since garnet-core-be-fe 1.0.0
 */
public class TokenGenerator {

    /**
     * Generate value string.
     *
     * @return the string
     * @since garnet-core-be-fe 1.0.0
     */
    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    /**
     * The constant hexCode.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    /**
     * To hex string string.
     *
     * @param data the data
     * @return the string
     * @since garnet-core-be-fe 1.0.0
     */
    private static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    /**
     * Generate value string.
     *
     * @param param the param
     * @return the string
     * @since garnet-core-be-fe 1.0.0
     */
    private static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new GarnetServiceException("生成Token失败", e);
        }
    }
}
