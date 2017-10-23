/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarDeptDao;
import com.richstonedt.garnet.model.GarDept;
import com.richstonedt.garnet.service.GarDeptService;
import com.richstonedt.garnet.utils.IdGeneratorUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GarDeptServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/23 10:07
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarDeptServiceImpl implements GarDeptService {

    /**
     * The Dept dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarDeptDao deptDao;

    /**
     * Save.
     *
     * @param garDept the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarDept garDept) {
        if (garDept.getDeptId() == null) {
            garDept.setDeptId(IdGeneratorUtil.generateId());
        }
        deptDao.save(garDept);
    }

    /**
     * Update.
     *
     * @param garDept the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarDept garDept) {
        deptDao.update(garDept);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        deptDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {

    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarDept queryObject(Long id) {
        return deptDao.queryObject(id);
    }

    /**
     * Query objects list.
     *
     * @param searchName the search name
     * @param page       the offset
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarDept> queryObjects(String searchName, Integer page, Integer limit) {
        //todo 通过用户ID查询部门信息
        String subDeptList = getSubDeptIdList(0L);
        return deptDao.queryDeptList(subDeptList, null, null, null);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return 0;
    }


    /**
     * Query detp id list list.
     *
     * @param parentDeptId the parent dept id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<Long> queryDetpIdList(Long parentDeptId) {
        return deptDao.queryDetpIdList(parentDeptId);
    }

    /**
     * Gets sub dept id list.
     *
     * @param deptId the dept id
     * @return the sub dept id list
     * @since garnet-core-be-fe 0.1.0
     */
    public String getSubDeptIdList(Long deptId) {
        //部门及子部门ID列表
        List<Long> deptIdList = new ArrayList<>();

        //获取子部门ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        //添加本部门
        deptIdList.add(deptId);

        return StringUtils.join(deptIdList, ",");
    }


    /**
     * Get dept tree list.
     *
     * @param subIdList  the sub id list
     * @param deptIdList the dept id list
     * @since garnet-core-be-fe 0.1.0
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
        for (Long deptId : subIdList) {
            List<Long> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }
            deptIdList.add(deptId);
        }
    }
}
