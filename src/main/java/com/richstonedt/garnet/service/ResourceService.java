package com.richstonedt.garnet.service;

import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.ResourceExcelView;
import com.richstonedt.garnet.model.view.ResourceView;
import sun.security.util.Resources_pt_BR;

import java.io.IOException;
import java.util.List;

public interface ResourceService extends BaseService<Resource, ResourceCriteria, Long> {

    /**
     * 新增资源
     * @param resourceView
     * @return
     */
    Long insertResource(ResourceView resourceView);

    /**
     * 更新资源
     * @param resourceView
     */
    void updateResource(ResourceView resourceView);

    /**
     * 删除资源
     * @param resourceView
     */
    void deleteResource(ResourceView resourceView);

    /**
     * 根据参数查询resouces
     * @param resourceParm
     * @return
     */
    PageUtil queryResourcesByParms(ResourceParm resourceParm);

    /**
     * 获取garnet_appCode下的所有resources
     * @return
     * @throws IOException
     */
    String getGarnetAppCodeResources(ResourceParm resourceParm) throws IOException;

    /**
     * 获取garnet_sysMenu下的所有resources
     * @return
     * @throws IOException
     */
    String getGarnetSysMenuResources(ResourceParm resourceParm) throws IOException;

    /**
     * 通过应用id和资源类型获取资源列表,并拼接成字符串返回
     * @param resourceParm
     * @return
     */
    String getAllResourceByAppAndType(ResourceParm resourceParm);

    /**
     * 导入excel表中的数据到数据库
     * @param resourceExcelViews
     */
    void saveResourcesExcel(List<ResourceExcelView> resourceExcelViews);

    /**
     * 查看资源配置类型是否有被关联资源
     * @param ids
     * @return
     */
    boolean hasRelated(String ids);

    /**
     * 根据userId返回
     * @param userId
     * @return
     */
    List<Resource> getResourcesByUserId(Long userId);

    /**
     * 根据登录用户和权限匹配路径查询该用户拥有的查看数据的权限等级
     * @param userId
     * @param path
     * @return
     */
    String getLevelByUserIdAndPath(Long userId, String path);

    /**
     * 根据登录用户和权限匹配路径查询该用户新增页的类型选项（应用级、租户级等）
     * @param userId
     * @param path
     * @return
     */
    String getTypeByUserIdAndPath(Long userId, String path);

    /**
     * 查询资源列表
     * @param resourceParm
     * @return
     */
    PageUtil getResourcesByParams(ResourceParm resourceParm);
}