/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.ClassUtil;
import com.richstonedt.garnet.dao.GarApiDao;
import com.richstonedt.garnet.dao.GarApplicationDao;
import com.richstonedt.garnet.dao.GarResourceApiDao;
import com.richstonedt.garnet.model.GarApi;
import com.richstonedt.garnet.model.view.model.GarApiForImport;
import com.richstonedt.garnet.model.view.model.GarVmApi;
import com.richstonedt.garnet.service.GarApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * <b><code>GarApiServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:24
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarApiServiceImpl implements GarApiService {

    /**
     * The Entity to vm copier.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private BeanCopier entityToVMCopier = BeanCopier.create(GarApi.class, GarVmApi.class,
            false);

    /**
     * The Api dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApiDao apiDao;

    /**
     * The Application dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationDao applicationDao;

    /**
     * The Resource api dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarResourceApiDao resourceApiDao;

    /**
     * Save.
     *
     * @param garApi the gar api
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarApi garApi) {
        apiDao.save(garApi);
    }

    /**
     * Update.
     *
     * @param garApi the gar api
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarApi garApi) {
        apiDao.update(garApi);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteById(Long id) {
        apiDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        apiDao.deleteBatch(ids);
    }

    /**
     * Query object gar api.
     *
     * @param id the id
     * @return the gar api
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarApi queryObject(Long id) {
        return apiDao.queryObject(id);
    }

    /**
     * Query objects list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarApi> queryObjects(Map<String,Object> params) {
        return apiDao.queryObjects(params);
    }

    /**
     * Query total int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal(Map<String,Object> params) {
        return apiDao.queryTotal(params);
    }

    /**
     * Query api list list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVmApi> queryApiList(Map<String,Object> params) {
        List<GarVmApi> vmApis = new ArrayList<>();
        List<GarApi> apis = apiDao.queryApisByParams(params);
        for (GarApi api : apis) {
            vmApis.add(convertApiToVMApi(api));
        }
        return vmApis;
    }

    /**
     * Convert api to vm api gar vm api.
     *
     * @param api the api
     * @return the gar vm api
     * @since garnet-core-be-fe 0.1.0
     */
    private GarVmApi convertApiToVMApi(GarApi api) {
        GarVmApi vmApi = new GarVmApi();
        entityToVMCopier.copy(api, vmApi, null);
        return vmApi;
    }

    /**
     * Gets permissions by ids.
     *
     * @param ids the ids
     * @return the permissions by ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public Set<String> getPermissionsByIds(Set<Long> ids) {
        return apiDao.queryPermissionsByIds(ids);
    }

    /**
     * Import api from annotation.
     *
     * @param apiList       the api list
     * @param applicationId the application id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void importApiFromAnnotation(List<GarApiForImport> apiList, Long applicationId) {
        importApi(apiList,applicationId);
    }

    /**
     * Query api list by application id list.
     *
     * @param applicationId the application id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVmApi> queryApiListByApplicationId(Long applicationId) {
        List<GarApi> apiList = apiDao.queryApiByApplicationIdAndStatus(applicationId, 1);
        List<GarVmApi> vmApiList = new ArrayList<>();
        for (GarApi garApi : apiList) {
            GarVmApi vmApi = convertApiToVMApi(garApi);
            vmApiList.add(vmApi);
        }
        return vmApiList;
    }

    /**
     * Gets import api from annotation.
     *
     * @param controllerClass the controller class
     * @return the import api from annotation
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarApiForImport> getImportApiFromAnnotation(Class controllerClass) {

        List<GarApiForImport> importApiList = new ArrayList<>();
        List<Class<?>> classList = ClassUtil.getClassListFromPackage(controllerClass);
        if (!CollectionUtils.isEmpty(classList)) {
            for (Class<?> clazz : classList) {
                importApiList.add(this.getApisByAnnotation(clazz));
            }
        }
        return importApiList;
    }

    /**
     * Query total api int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotalApi(Map<String,Object> params) {
        return apiDao.queryTotalApi(params);
    }

    /**
     * Gets api by id.
     *
     * @param apisId the apis id
     * @return the api by id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarVmApi getApiById(Long apisId) {
        GarApi api = apiDao.queryObject(apisId);
        return convertApiToVMApi(api);
    }

    /**
     * Import api by app code.
     *
     * @param apiList the api list
     * @param appCode the app code
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void importApiByAppCode(List<GarApiForImport> apiList, String appCode) {
        Long applicationId = applicationDao.getApplicationIdByCode(appCode);
        importApi(apiList,applicationId);
    }

    /**
     * Delete batch by api ids map.
     *
     * @param apiIds the api ids
     * @return the map
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public Map<String, String> deleteBatchByApiIds(List<Long> apiIds) {
        Map<String, String> result = new HashMap<>();
        Set<String> apiNames = apiDao.queryApiNameByParentIds(apiIds);
        if (CollectionUtils.isEmpty(apiNames)) {
            resourceApiDao.deleteByApiIds(apiIds);
            apiDao.deleteBatch(apiIds);
        } else {
            result.put("status", "500");
            String message = "删除的API中存在如下的子API：" +
                    String.join("，", apiNames);
            result.put("message", message);
        }
        return result;
    }

    /**
     * Gets apis by annotation.
     *
     * @param clazz the clazz
     * @return the apis by annotation
     * @since garnet-core-be-fe 0.1.0
     */
    private GarApiForImport getApisByAnnotation(Class<?> clazz) {
        List<GarApi> apis = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        String baseUrl = "";
        String apiName = "";
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
        if (!ObjectUtils.isEmpty(requestMapping)) {
            baseUrl = requestMapping.value()[0];
        }
        Api apiAnnotation = clazz.getAnnotation(Api.class);
        if (!ObjectUtils.isEmpty(apiAnnotation) && !ObjectUtils.isEmpty(apiAnnotation.tags())) {
            apiName = apiAnnotation.tags()[0];
        }

        GarApiForImport apiForImport = new GarApiForImport();

        GarApi parentApi = new GarApi();
//        parentApi.setApplicationId(applicationId);
        parentApi.setName(apiName);
        parentApi.setStatus(1);
        parentApi.setParentId(0L);
//        saveOrUpdateApi(parentApi, true);
        apiForImport.setApi(parentApi);
        if (!ObjectUtils.isEmpty(methods)) {
            for (Method method : methods) {
                RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
                if (!ObjectUtils.isEmpty(requiresPermissions)) {
                    GarApi api = new GarApi();
                    ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                    RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
//                    api.setApplicationId(applicationId);
                    api.setParentId(parentApi.getApiId());
                    api.setPermission(requiresPermissions.value()[0]);
                    api.setName(apiOperation.value());
                    api.setDescription(apiOperation.notes());
                    api.setUrl(baseUrl + methodMapping.value()[0]);
                    api.setMethod(methodMapping.method()[0].toString());
                    api.setStatus(1);
                    apis.add(api);
                }
            }
//            updateApis(apis);
            apiForImport.setApiList(apis);
        }
        return apiForImport;
    }

    /**
     * Update apis.
     *
     * @param apis the apis
     * @since garnet-core-be-fe 0.1.0
     */
    private void updateApis(List<GarApi> apis) {
        for (GarApi api : apis) {
            saveOrUpdateApi(api, false);
        }
    }

    /**
     * Save or update api.
     *
     * @param api      the api
     * @param isParent the is parent
     * @since garnet-core-be-fe 0.1.0
     */
    private void saveOrUpdateApi(GarApi api, boolean isParent) {
        Map<String, Object> params = new HashMap<>();
        params.put("applicationId", api.getApplicationId());
        // 如果是父级,根据名称和应用id获取，子级则通过权限和应用id获取
        if (isParent) {
            params.put("name", api.getName());
        } else {
            params.put("permission", api.getPermission());
        }
        List<GarApi> dbApiList = apiDao.queryApisByParams(params);
        // 如果访问权限不存在就新增，否则进行修改
        if (CollectionUtils.isEmpty(dbApiList)) {
            apiDao.save(api);
        } else {
            api.setApiId(dbApiList.get(0).getApiId());
            apiDao.update(api);
        }
    }

    /**
     * Import api.
     *
     * @param importApiList the import api list
     * @param applicationId the application id
     * @since garnet-core-be-fe 0.1.0
     */
    private void importApi(List<GarApiForImport> importApiList,Long applicationId) {
        // 遍历需要导入的对象
        for (GarApiForImport importApi : importApiList) {
            GarApi parentApi = importApi.getApi();
            if (!ObjectUtils.isEmpty(parentApi)) {
                parentApi.setApplicationId(applicationId);
                saveOrUpdateApi(parentApi, true);
                // 获取并保存子权限
                if (!CollectionUtils.isEmpty(importApi.getApiList())) {
                    for (GarApi api : importApi.getApiList()) {
                        api.setApplicationId(applicationId);
                        api.setParentId(parentApi.getApiId());
                        saveOrUpdateApi(api, false);
                    }
                }
            }
        }
    }

}
