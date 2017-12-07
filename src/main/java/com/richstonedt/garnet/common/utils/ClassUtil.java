/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.common.utils;

import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;

/**
 * <b><code>ClassUtil</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/7 12:52
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public class ClassUtil {

    public static List<Class<?>> getClassListFromPackage(Class clazz) {
        try {
            List<Class<?>> clazzList = new ArrayList<>();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            String pkgName = clazz.getPackage().getName();
            String strFile = pkgName.replaceAll("\\.", "/");
            Enumeration<URL> urls = loader.getResources(strFile);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (!ObjectUtils.isEmpty(url)) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String pkgPath = url.getPath();
                        File[] files = filterClassFiles(pkgPath);
                        if (!ObjectUtils.isEmpty(files)) {
                            for (File file : files) {
                                String fileName = file.getName();
                                if (file.isFile()) {
                                    // .class 文件的情况
                                    String clazzName = getClassName(pkgName, fileName);
                                    clazz = Class.forName(clazzName);
                                    if (!ObjectUtils.isEmpty(clazz)) {
                                        clazzList.add(clazz);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return clazzList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static File[] filterClassFiles(String pkgPath) {
        if (pkgPath == null) {
            return null;
        }
        // 接收 .class 文件 或 类文件夹
        return new File(pkgPath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
    }
    private static String getClassName(String pkgName, String fileName) {
        int endIndex = fileName.lastIndexOf(".");
        String clazz = null;
        if (endIndex >= 0) {
            clazz = fileName.substring(0, endIndex);
        }
        String clazzName = null;
        if (clazz != null) {
            clazzName = pkgName + "." + clazz;
        }
        return clazzName;
    }
}
