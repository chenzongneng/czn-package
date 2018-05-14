package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.PageHelper;
import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.ResourceMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.*;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.*;
import com.richstonedt.garnet.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ResourceServiceImpl extends BaseServiceImpl<Resource, ResourceCriteria, Long> implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResourceDynamicPropertyService resourceDynamicPropertyService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private GroupRoleService groupRoleService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.resourceMapper;
    }

    @Override
    public Long insertResource(ResourceView resourceView) {

        Resource resource = resourceView.getResource();

        resource.setId(IdGeneratorUtil.generateId());

        Long currentTime = System.currentTimeMillis();

        resource.setCreatedTime(currentTime);

        resource.setModifiedTime(currentTime);

        this.insertSelective(resource);

        if(!ObjectUtils.isEmpty(resourceView.getResourceDynamicProperties())){

            for (ResourceDynamicProperty resourceDynamicProperty:
                 resourceView.getResourceDynamicProperties()) {

                resourceDynamicProperty.setId(IdGeneratorUtil.generateId());
                resourceDynamicPropertyService.insertSelective(resourceDynamicProperty);

            }

        }

        return resource.getId();

    }

    @Override
    public void updateResource(ResourceView resourceView) {

        Resource resource = resourceView.getResource();
        Long currentTime = System.currentTimeMillis();
        resource.setModifiedTime(currentTime);
        this.updateByPrimaryKeySelective(resource);

        if(!ObjectUtils.isEmpty(resourceView.getResourceDynamicProperties())){
            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyService.deleteByCriteria(resourceDynamicPropertyCriteria);

            for (ResourceDynamicProperty resourceDynamicProperty:
                    resourceView.getResourceDynamicProperties()) {

                resourceDynamicProperty.setId(IdGeneratorUtil.generateId());
                resourceDynamicPropertyService.insertSelective(resourceDynamicProperty);

            }

        }


    }

    @Override
    public void deleteResource(ResourceView resourceView) {

        Resource resource = resourceView.getResource();

//        if(!ObjectUtils.isEmpty(resourceView.getResourceDynamicProperties())){
//            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
//            resourceDynamicPropertyService.deleteByCriteria(resourceDynamicPropertyCriteria);
//
//        }

        if (!ObjectUtils.isEmpty(resource) && !ObjectUtils.isEmpty(resource.getId())) {
            Long id = resource.getId();

            if (id.longValue() == 60L) {
                throw new RuntimeException("不能删除超级权限资源");
            }

            this.deleteByPrimaryKey(id);
        }


    }

    @Override
    public PageUtil queryResourcesByParms(ResourceParm resourceParm) {

        List<Resource> resourceList = null;
        ResourceCriteria resourceCriteria = new ResourceCriteria();
        resourceCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        ResourceCriteria.Criteria criteria =resourceCriteria.createCriteria();

        if (!StringUtils.isEmpty(resourceParm.getSearchName())) {
            criteria.andNameLike("%" + resourceParm.getSearchName() + "%");
        }

        if (!ObjectUtils.isEmpty(resourceParm.getApplicationId())) {
            criteria.andApplicationIdEqualTo(resourceParm.getApplicationId());
        }

        if (!StringUtils.isEmpty(resourceParm.getType())) {
            criteria.andTypeEqualTo(resourceParm.getType());
        }

        if (!StringUtils.isEmpty(resourceParm.getUserId())) {
            //获取用户绑定的tenantIds
            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(resourceParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();
            //change by ming
            tenantIds =  commonService.dealTenantIdsIfGarnet(resourceParm.getUserId(),tenantIds);

            //如果不是garnet的超级管理员，返回绑定tenantId下的resource
            if (!returnTenantIdView.isSuperAdmin() || (returnTenantIdView.isSuperAdmin() && !commonService.superAdminBelongGarnet(resourceParm.getUserId()))) {
                criteria.andTenantIdIn(tenantIds);
            } else {
                // 是超级管理员，返回所有resource
                resourceList = this.selectByCriteria(resourceCriteria);
            }
        }

        PageUtil result = new PageUtil(this.selectByCriteria(resourceCriteria), (int)this.countByCriteria(resourceCriteria),resourceParm.getPageSize(), resourceParm.getPageNumber());

        return result;
    }

    @Override
    public String getGarnetAppCodeResources(ResourceParm resourceParm) {

        if (!StringUtils.isEmpty(resourceParm.getUserId())) {
            List<Resource> resourceList = this.getResourceListByUserId(resourceParm.getUserId(), "garnet_appCode");
            if (CollectionUtils.isEmpty(resourceList)) {
                return "";
            }

            JSONObject jsonObject = new JSONObject();
            for (Resource resource : resourceList) {
                jsonObject.put(resource.getVarchar00(), Boolean.parseBoolean(resource.getVarchar01()));
            }
            return jsonObject.toString();
        } else {
            return "";
        }

    }

    @Override
    public String getGarnetSysMenuResources(ResourceParm resourceParm) {
        ResourceCriteria resourceCriteria = new ResourceCriteria();
        resourceCriteria.createCriteria().andTypeEqualTo("garnet_sysMenu").andVarchar07Like("garnetSysManagement%");
        List<Resource> resourceList1 = this.selectByCriteria(resourceCriteria);

        ResourceCriteria resourceCriteria1 = new ResourceCriteria();
        resourceCriteria.createCriteria().andTypeEqualTo("garnet_sysMenu").andVarchar07Like("garnetDevelopment%");
        List<Resource> resourceList2 = this.selectByCriteria(resourceCriteria1);

        //菜单管理一级
        SySMenuJsonObject sySMenuJsonObject1 = this.getSySMenuJsonObject(resourceList1, "1");

        //菜单管理二级
        SySMenuJsonObject sySMenuJsonObject2 = this.getSySMenuJsonObject(resourceList2, "9");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(sySMenuJsonObject1);
        jsonArray.add(sySMenuJsonObject2);

        return jsonArray.toString();
    }

    /**
     * 根据 resourceList 获取 garnet菜单对象
     * @param resourceList
     * @param parentId
     * @return
     */
    private SySMenuJsonObject getSySMenuJsonObject(List<Resource> resourceList, String parentId) {
        //菜单管理一级
        Resource resource1 = null;
        List<SySMenuJsonObject> sySMenuJsonObjectList = new ArrayList<>();
        for (Resource resource : resourceList) {
            if ("1".equals(resource.getVarchar05()) && parentId.equals(resource.getVarchar01())) {
                //菜单管理二级菜单
                SySMenuJsonObject sySMenuJsonObject1 = getSySMenuJsonObject(resource, null);
                //二级菜单的list
                sySMenuJsonObjectList.add(sySMenuJsonObject1);
            } else if ("0".equals(resource.getVarchar05()) && "0".equals(resource.getVarchar01())) {
                //菜单管理一级菜单
                resource1 = resource;
            }
        }

        if (ObjectUtils.isEmpty(resource1)) {
            return new SySMenuJsonObject();
        }

        //封装garnet菜单的jsonObject
        SySMenuJsonObject sySMenuJsonObject = getSySMenuJsonObject(resource1, sySMenuJsonObjectList);

        return sySMenuJsonObject;
    }

    /**
     * 封装garnet菜单的jsonObject
     * @param resource1
     * @param sySMenuJsonObjectList
     * @return
     */
    private SySMenuJsonObject getSySMenuJsonObject(Resource resource1, List<SySMenuJsonObject> sySMenuJsonObjectList) {
        SySMenuJsonObject sySMenuJsonObject = new SySMenuJsonObject();
        sySMenuJsonObject.setMenuId(Integer.valueOf(resource1.getVarchar00()));
        sySMenuJsonObject.setParentId(Integer.valueOf(resource1.getVarchar01()));
        sySMenuJsonObject.setParentName(resource1.getVarchar02() == "" ? null : resource1.getVarchar00());
        sySMenuJsonObject.setName(resource1.getVarchar03());
        sySMenuJsonObject.setUrl(resource1.getVarchar04() == "" ? null : resource1.getVarchar04());
        sySMenuJsonObject.setType(Integer.valueOf(resource1.getVarchar05()));
        sySMenuJsonObject.setIcon(resource1.getVarchar06());
        sySMenuJsonObject.setCode(resource1.getVarchar07());
        sySMenuJsonObject.setOrderNum(Integer.valueOf(resource1.getVarchar08()));
        sySMenuJsonObject.setOpen(resource1.getVarchar09() == "" ? null : resource1.getVarchar09());
        sySMenuJsonObject.setList(sySMenuJsonObjectList);
        return sySMenuJsonObject;
    }


    /**
     * 根据userId获取permissions，根据permissions获取相匹配的resources并去重
     * @param userId
     * @param type
     * @return
     */
    public List<Resource> getResourceListByUserId(Long userId, String type) {
        //根据userId 拿 group
        GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
        groupUserCriteria.createCriteria().andUserIdEqualTo(userId);
        List<GroupUser> groupUserList = groupUserService.selectByCriteria(groupUserCriteria);

        if (CollectionUtils.isEmpty(groupUserList)) {
            return new ArrayList<>();
        }
        //根据group 拿 role
        List<Long> groupIds = new ArrayList<>();
        for (GroupUser groupUser : groupUserList) {
            Long groupId = groupUser.getGroupId();
            groupIds.add(groupId);
        }
        //通过中间表拿关联的role
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andGroupIdIn(groupIds);
        List<GroupRole> groupRoleList = groupRoleService.selectByCriteria(groupRoleCriteria);
        if (CollectionUtils.isEmpty(groupRoleList)) {
            return new ArrayList<>();
        }

        List<Long> roleIds = new ArrayList<>();
        for (GroupRole groupRole : groupRoleList) {
            Long roleId = groupRole.getRoleId();
            roleIds.add(roleId);
        }
        //根据role拿permission
        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdIn(roleIds);
        List<RolePermission> rolePermissionList = rolePermissionService.selectByCriteria(rolePermissionCriteria);
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            return new ArrayList<>();
        }
        List<Long> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionList) {
            Long permissionId = rolePermission.getPermissionId();
            permissionIds.add(permissionId);
        }
        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andIdIn(permissionIds).andStatusEqualTo(1);
        List<Permission> permissionList = permissionService.selectByCriteria(permissionCriteria);
        if (CollectionUtils.isEmpty(permissionList)) {
            return new ArrayList<>();
        }

        //通过权限的通配符 查询相对应的resource
        List<Resource> resourceList = new ArrayList<>();
        for (Permission permission : permissionList) {
            String resourcePathWildcard = permission.getResourcePathWildcard();
            if (!StringUtils.isEmpty(resourcePathWildcard)) {
                ResourceCriteria resourceCriteria = new ResourceCriteria();
                resourceCriteria.createCriteria().andPathLike(resourcePathWildcard).andApplicationIdEqualTo(GarnetContants.GARNET_APPLICATION_ID);
                List<Resource> resources = this.selectByCriteria(resourceCriteria);
                resourceList.addAll(resources);
            }
        }
        if (CollectionUtils.isEmpty(resourceList)) {
            return new ArrayList<>();
        }
        //对resource去重
        Set<Resource> resourceSet = new HashSet<>();
        List<Resource> resourceList1 = new ArrayList<>();
        for (Resource resource : resourceList) {
            Long resourceId = resource.getId();
            if (!resourceSet.contains(resourceId)) {
                resourceList1.add(resource);
            }
        }

        return resourceList1;
    }

    /**
     * 根据应用和资源类型查看所有的配置
     * @param resourceParm
     * @return
     */
    @Override
    public String getAllResourceByAppAndType(ResourceParm resourceParm) {
        if (!ObjectUtils.isEmpty(resourceParm.getApplicationId()) && !StringUtils.isEmpty(resourceParm.getType())) {
            ResourceDynamicPropertyView resourceDynamicPropertyView = resourceDynamicPropertyService.selectResourceDynamicPropertyViewByType(resourceParm.getType());
            List<ResourceDynamicProperty> resourceDynamicPropertyList = resourceDynamicPropertyView.getResourceDynamicPropertyList();

            ResourceCriteria resourceCriteria = new ResourceCriteria();
            resourceCriteria.createCriteria().andTypeEqualTo(resourceParm.getType()).andApplicationIdEqualTo(resourceParm.getApplicationId());
            List<Resource> resourceList = this.selectByCriteria(resourceCriteria);

            StringBuilder result = new StringBuilder();
            JSONArray jsonArray = new JSONArray();
            for (Resource resource : resourceList) {

                //设置资源名称等基础信息
                JSONObject jsonObject = setBaseInfo(resource);

                for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicPropertyList) {

                    switch (resourceDynamicProperty.getFiledName()) {
                        case "varchar00" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar00());
                            break;
                        case "varchar01" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar01());
                            break;
                        case "varchar02" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar02());
                            break;
                        case "varchar03" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar03());
                            break;
                        case "varchar04" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar04());
                            break;
                        case "varchar05" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar05());
                            break;
                        case "varchar06" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar06());
                            break;
                        case "varchar07" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar07());
                            break;
                        case "varchar08" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar08());
                            break;
                        case "varchar09" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar09());
                            break;
                        case "varchar10" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar10());
                            break;
                        case "varchar11" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar11());
                            break;
                        case "varchar12" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar12());
                            break;
                        case "varchar13" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar13());
                            break;
                        case "varchar14" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar14());
                            break;
                        case "varchar15" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar15());
                            break;
                        case "varchar16" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar16());
                            break;
                        case "varchar17" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar17());
                            break;
                        case "varchar18" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar18());
                            break;
                        case "varchar19" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar19());
                            break;
                        case "int01" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt01());
                            break;
                        case "int02" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt02());
                            break;
                        case "int03" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt03());
                            break;
                        case "int04" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt04());
                            break;
                        case "int05" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt05());
                            break;
                        case "boolean01" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getBoolean01());
                            break;
                        case "boolean02" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getBoolean02());
                            break;
                        case "boolean03" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getBoolean03());
                            break;
                        case "boolean04" :
                            jsonObject.put(resourceDynamicProperty.getDescription(), resource.getBoolean04());
                            break;
                        default:
                            break;
                    }
                }

                result.append(jsonObject.toString());
                jsonArray.add(jsonObject);
            }

            return jsonArray.toString();
        }
        return null;
    }

    /**
     * 设置资源名称等基础信息
     * @param resource
     * @return
     */
    private JSONObject setBaseInfo(Resource resource) {
        JSONObject jsonObject = new JSONObject();
        String name = resource.getName();
        jsonObject.put("资源名称", name);

        if (!ObjectUtils.isEmpty(resource.getApplicationId())) {
            Application application = applicationService.selectByPrimaryKey(resource.getApplicationId());
            jsonObject.put("所属应用", application.getName());
        } else {
            jsonObject.put("所属应用", null);
        }

        if (!ObjectUtils.isEmpty(resource.getTenantId())) {
            Tenant tenant = tenantService.selectByPrimaryKey(resource.getTenantId());
            jsonObject.put("所属租户", tenant.getName());
        } else {
            jsonObject.put("所属租户", null);
        }

        jsonObject.put("路径标识", resource.getPath());
        jsonObject.put("action", resource.getActions());
        return jsonObject;
    }

    /**
     * 上传excel，将数据保存到数据库中
     * @param resourceExcelViews
     */
    @Override
    public void saveResourcesExcel(List<ResourceExcelView> resourceExcelViews) {

        ResourceView resourceView;
        Resource resource;
        for (ResourceExcelView resourceExcel : resourceExcelViews) {
            resourceView = new ResourceView();

            resource = new Resource();


            checkData(resourceExcel);

            //设置appId'
            ApplicationCriteria applicationCriteria = new ApplicationCriteria();
            applicationCriteria.createCriteria().andNameEqualTo(resourceExcel.getApplicationName());
            Application application = applicationService.selectSingleByCriteria(applicationCriteria);
            if (!ObjectUtils.isEmpty(application)) {
                resource.setApplicationId(application.getId());
            } else {
                throw new RuntimeException("应用："+ resourceExcel.getApplicationName() +" 不存在");
            }

            //设置tenantId
            TenantCriteria tenantCriteria = new TenantCriteria();
            tenantCriteria.createCriteria().andNameEqualTo(resourceExcel.getTenantName());
            Tenant tenant = tenantService.selectSingleByCriteria(tenantCriteria);
            if (!ObjectUtils.isEmpty(tenant)) {
                resource.setTenantId(tenant.getId());
            } else {
                throw new RuntimeException("租户：" + resourceExcel.getTenantName() + " 不存在");
            }

            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(resourceExcel.getType());
            List<ResourceDynamicProperty> resourceDynamicPropertyList = resourceDynamicPropertyService.selectByCriteria(resourceDynamicPropertyCriteria);
            if (!CollectionUtils.isEmpty(resourceDynamicPropertyList)) {
                resource.setType(resourceExcel.getType());
            }  else {
                throw new RuntimeException("资源类型：" + resourceExcel.getType() + " 不存在");
            }

            resource.setName(resourceExcel.getName());
            resource.setActions(resourceExcel.getActions());
            resource.setPath(resourceExcel.getPath());

            resource.setVarchar00(resourceExcel.getVarchar00());
            resource.setVarchar01(resourceExcel.getVarchar01());
            resource.setVarchar02(resourceExcel.getVarchar02());
            resource.setVarchar03(resourceExcel.getVarchar03());
            resource.setVarchar04(resourceExcel.getVarchar04());
            resource.setVarchar05(resourceExcel.getVarchar05());
            resource.setVarchar06(resourceExcel.getVarchar06());
            resource.setVarchar07(resourceExcel.getVarchar07());
            resource.setVarchar08(resourceExcel.getVarchar08());
            resource.setVarchar09(resourceExcel.getVarchar09());
            resource.setVarchar10(resourceExcel.getVarchar10());
            resource.setVarchar11(resourceExcel.getVarchar11());
            resource.setVarchar12(resourceExcel.getVarchar12());
            resource.setVarchar13(resourceExcel.getVarchar13());
            resource.setVarchar14(resourceExcel.getVarchar14());
            resource.setVarchar15(resourceExcel.getVarchar15());
            resource.setVarchar16(resourceExcel.getVarchar16());
            resource.setVarchar17(resourceExcel.getVarchar17());
            resource.setVarchar18(resourceExcel.getVarchar18());
            resource.setVarchar19(resourceExcel.getVarchar19());
            resource.setInt01(resourceExcel.getInt01());
            resource.setInt02(resourceExcel.getInt02());
            resource.setInt03(resourceExcel.getInt03());
            resource.setInt04(resourceExcel.getInt04());
            resource.setInt05(resourceExcel.getInt05());
            resource.setBoolean01(resourceExcel.getBoolean01());
            resource.setBoolean02(resourceExcel.getBoolean02());
            resource.setBoolean03(resourceExcel.getBoolean03());
            resource.setBoolean04(resourceExcel.getBoolean04());

            resourceView.setResource(resource);

            this.insertResource(resourceView);
        }

    }

    private void checkData(ResourceExcelView resourceExcel) {
        if (StringUtils.isEmpty(resourceExcel.getApplicationName())) {
            throw new RuntimeException("应用不能为空");
        }

        if (StringUtils.isEmpty(resourceExcel.getTenantName())) {
            throw new RuntimeException("租户不能为空");
        }

        if (StringUtils.isEmpty(resourceExcel.getType())) {
            throw new RuntimeException("资源类型不能为空");
        }

        if (StringUtils.isEmpty(resourceExcel.getName())) {
            throw new RuntimeException("资源名称不能为空");
        }

        if (StringUtils.isEmpty(resourceExcel.getPath())) {
            throw new RuntimeException("路径标识不能为空");
        }
    }

    @Override
    public boolean hasRelated(String ids) {

        List<Long> idList = new ArrayList<>();
        for (String id : ids.split(",")) {
            idList.add(Long.parseLong(id));
        }

        for (Long id : idList) {
            ResourceDynamicProperty resourceDynamicProperty = resourceDynamicPropertyService.selectByPrimaryKey(id);
            String type = resourceDynamicProperty.getType();

            ResourceCriteria resourceCriteria = new ResourceCriteria();
            resourceCriteria.createCriteria().andTypeEqualTo(type);
            List<Resource> resources = this.selectByCriteria(resourceCriteria);
            if (!CollectionUtils.isEmpty(resources) || resources.size() > 0) {
                return true;
            }
        }

        return false;
    }


}