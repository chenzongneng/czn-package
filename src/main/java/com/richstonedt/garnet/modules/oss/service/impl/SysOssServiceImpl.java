/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.oss.service.impl;

import com.richstonedt.garnet.modules.oss.dao.SysOssDao;
import com.richstonedt.garnet.modules.oss.entity.SysOssEntity;
import com.richstonedt.garnet.modules.oss.service.SysOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysOssService")
public class SysOssServiceImpl implements SysOssService {

    @Autowired
    private SysOssDao sysOssDao;

    @Override
    public SysOssEntity queryObject(Long id) {
        return sysOssDao.queryObject(id);
    }

    @Override
    public List<SysOssEntity> queryList(Map<String, Object> map) {
        return sysOssDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysOssDao.queryTotal(map);
    }

    @Override
    public void save(SysOssEntity sysOss) {
        sysOssDao.save(sysOss);
    }

    @Override
    public void update(SysOssEntity sysOss) {
        sysOssDao.update(sysOss);
    }

    @Override
    public void delete(Long id) {
        sysOssDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sysOssDao.deleteBatch(ids);
    }

}
