/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.utils;

import java.util.Random;

/**
 * <b><code>IdGeneratorUtil</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/19 12:19
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public class IdGeneratorUtil {

    /**
     * Generate Id long.
     *
     * @return the long
     * @since garnet-core-be-fe 0.1.0
     */
    public static Long generateId() {
//        long ourEpoch =Long.parseLong("1314220021721");
//        long seqId,nowMillis,result;
//        int shardId = 5;
//        seqId = new Random().nextInt(1000000000) % 1024;
//        nowMillis = (long) Math.floor(System.currentTimeMillis() * 1000);
//        result = (nowMillis - ourEpoch) << 23;
//        result = result | (shardId << 10);
//        result = result | (seqId);
//        return result;
        int i=(int)(Math.random() * 100);
        String ran = String.valueOf(i);
        if (ran.length() < 2) {
            Random random = new Random();
            ran = ran + random.nextInt(9);
        }
        String time = String.valueOf(System.currentTimeMillis()).substring(1,12);
        return Long.parseLong(time + ran);
//        return System.currentTimeMillis() + i;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0; i < 20; i++) {
            Thread.sleep(2000);
            System.out.println(IdGeneratorUtil.generateId());
        }
    }

}
