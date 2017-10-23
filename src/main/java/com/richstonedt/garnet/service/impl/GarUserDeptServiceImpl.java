package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarUserDeptDao;
import com.richstonedt.garnet.model.GarUserDept;
import com.richstonedt.garnet.service.GarUserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarUserDeptServiceImpl implements GarUserDeptService {

    @Autowired
    private GarUserDeptDao userDeptDao;

    @Override
    public List<GarUserDept> queryObjectByDeptId(Long deptId) {
        return userDeptDao.queryObjectByDeptId(deptId);
    }

    @Override
    public void save(GarUserDept garUserDept) {
        userDeptDao.save(garUserDept);
    }

    @Override
    public void update(GarUserDept garUserDept) {
        userDeptDao.update(garUserDept);
    }

    @Override
    public void deleteById(Long id) {
        userDeptDao.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {

    }

    @Override
    public GarUserDept queryObject(Long id) {
        return userDeptDao.queryObject(id);
    }

    @Override
    public List<GarUserDept> queryObjects(String searchName, Integer page, Integer limit) {
        return null;
    }

    @Override
    public int queryTotal() {
        return 0;
    }
}
