package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.FileUtil;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.ResourceMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.*;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.*;
import com.richstonedt.garnet.service.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.naming.factory.ResourceLinkFactory;
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
                //resourceDynamicProperty.setType(resource.getType());
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
            //resourceDynamicPropertyCriteria.createCriteria().andResourceIdEqualTo(resource.getId());
            resourceDynamicPropertyService.deleteByCriteria(resourceDynamicPropertyCriteria);

            for (ResourceDynamicProperty resourceDynamicProperty:
                    resourceView.getResourceDynamicProperties()) {

                resourceDynamicProperty.setId(IdGeneratorUtil.generateId());
                //resourceDynamicProperty.setResourceId(resource.getId());
                resourceDynamicPropertyService.insertSelective(resourceDynamicProperty);

            }

        }


    }

    @Override
    public void deleteResource(ResourceView resourceView) {

        Resource resource = resourceView.getResource();

        if(!ObjectUtils.isEmpty(resourceView.getResourceDynamicProperties())){

            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
            //resourceDynamicPropertyCriteria.createCriteria().andResourceIdEqualTo(resource.getId());
            resourceDynamicPropertyService.deleteByCriteria(resourceDynamicPropertyCriteria);

        }

        this.deleteByPrimaryKey(resource.getId());

    }

    @Override
    public PageUtil<Resource> queryResourcesByParms(ResourceParm resourceParm) {

        PageHelper.startPage(1, 10);
        Resource resource = resourceParm.getResource();
        List<Resource> resourceList = null;
        ResourceCriteria resourceCriteria = new ResourceCriteria();
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
//                criteria.andTenantIdIn(tenantIds);
                resourceList = this.getResourcesByUserId(resourceParm.getUserId());
            } else {
                // 是超级管理员，返回所有resource
                resourceList = this.selectByCriteria(resourceCriteria);
            }
        }

//        PageInfo pageInfo = new PageInfo(resourceList,10);
        PageUtil result = new PageUtil(resourceList, (int)this.countByCriteria(resourceCriteria),resourceParm.getPageSize(), resourceParm.getPageNumber());

        return result;
    }

    @Override
    public String getGarnetAppCodeResources(ResourceParm resourceParm) {

        if (!StringUtils.isEmpty(resourceParm.getUserId())) {
            List<Resource> resourceList = this.getResourceListByUserId(resourceParm.getUserId(), "garnet_appCode");
            if (CollectionUtils.isEmpty(resourceList)) {
                return "";
            }
//            ResourceCriteria resourceCriteria = ne  w ResourceCriteria();
//            resourceCriteria.createCriteria().andTypeEqualTo("garnet_appCode");
//            List<Resource> resourceList = this.selectByCriteria(resourceCriteria);
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
    public String getGarnetSysMenuResources(ResourceParm resourceParm) throws IOException {
        ResourceCriteria resourceCriteria = new ResourceCriteria();
        resourceCriteria.createCriteria().andTypeEqualTo("garnet_sysMenu").andVarchar07Like("garnetSysManagement%");
        List<Resource> resourceList1 = this.selectByCriteria(resourceCriteria);

        ResourceCriteria resourceCriteria1 = new ResourceCriteria();
        resourceCriteria.createCriteria().andTypeEqualTo("garnet_sysMenu").andVarchar07Like("garnetDevelopment%");
        List<Resource> resourceList2 = this.selectByCriteria(resourceCriteria1);


        //菜单管理一级
        SySMenuJsonObject sySMenuJsonObject1 = new SySMenuJsonObject();
        Resource resource1 = null;
        List<SySMenuJsonObject> sySMenuJsonObjectList1 = new ArrayList<>();
        for (Resource resource : resourceList1 ) {
            if ("1".equals(resource.getVarchar05()) && "1".equals(resource.getVarchar01())) {
                //菜单管理二级菜单
                SySMenuJsonObject sySMenuJsonObject11 = new SySMenuJsonObject();
                sySMenuJsonObject11.setMenuId(Integer.valueOf(resource.getVarchar00()));
                sySMenuJsonObject11.setParentId(Integer.valueOf(resource.getVarchar01()));
                sySMenuJsonObject11.setParentName(resource.getVarchar02() == "" ? null : resource.getVarchar00());
                sySMenuJsonObject11.setName(resource.getVarchar03());
                sySMenuJsonObject11.setUrl(resource.getVarchar04() == "" ? null : resource.getVarchar04());
                sySMenuJsonObject11.setType(Integer.valueOf(resource.getVarchar05()));
                sySMenuJsonObject11.setIcon(resource.getVarchar06());
                sySMenuJsonObject11.setCode(resource.getVarchar07());
                sySMenuJsonObject11.setOrderNum(Integer.valueOf(resource.getVarchar08()));
                sySMenuJsonObject11.setOpen(resource.getVarchar09() == "" ? null : resource.getVarchar09());
                sySMenuJsonObject11.setList(null);
                //二级菜单的list
                sySMenuJsonObjectList1.add(sySMenuJsonObject11);
            } else if ("0".equals(resource.getVarchar05()) && "0".equals(resource.getVarchar01())) {
                //菜单管理一级菜单
                resource1 = resource;
            }
        }

        sySMenuJsonObject1.setMenuId(Integer.valueOf(resource1.getVarchar00()));
        sySMenuJsonObject1.setParentId(Integer.valueOf(resource1.getVarchar01()));
        sySMenuJsonObject1.setParentName(resource1.getVarchar02() == "" ? null : resource1.getVarchar00());
        sySMenuJsonObject1.setName(resource1.getVarchar03());
        sySMenuJsonObject1.setUrl(resource1.getVarchar04() == "" ? null : resource1.getVarchar04());
        sySMenuJsonObject1.setType(Integer.valueOf(resource1.getVarchar05()));
        sySMenuJsonObject1.setIcon(resource1.getVarchar06());
        sySMenuJsonObject1.setCode(resource1.getVarchar07());
        sySMenuJsonObject1.setOrderNum(Integer.valueOf(resource1.getVarchar08()));
        sySMenuJsonObject1.setOpen(resource1.getVarchar09() == "" ? null : resource1.getVarchar09());
        sySMenuJsonObject1.setList(sySMenuJsonObjectList1);

        //菜单管理二级
        SySMenuJsonObject sySMenuJsonObject2 = new SySMenuJsonObject();
        Resource resource2 = null;
        List<SySMenuJsonObject> sySMenuJsonObjectList2 = new ArrayList<>();
        for (Resource resource : resourceList2 ) {
            if ("1".equals(resource.getVarchar05()) && "9".equals(resource.getVarchar01())) {
                //菜单管理二级菜单
                SySMenuJsonObject sySMenuJsonObject = new SySMenuJsonObject();
                sySMenuJsonObject.setMenuId(Integer.valueOf(resource.getVarchar00()));
                sySMenuJsonObject.setParentId(Integer.valueOf(resource.getVarchar01()));
                sySMenuJsonObject.setParentName(resource.getVarchar02() == "" ? null : resource.getVarchar00());
                sySMenuJsonObject.setName(resource.getVarchar03());
                sySMenuJsonObject.setUrl(resource.getVarchar04() == "" ? null : resource.getVarchar04());
                sySMenuJsonObject.setType(Integer.valueOf(resource.getVarchar05()));
                sySMenuJsonObject.setIcon(resource.getVarchar06());
                sySMenuJsonObject.setCode(resource.getVarchar07());
                sySMenuJsonObject.setOrderNum(Integer.valueOf(resource.getVarchar08()));
                sySMenuJsonObject.setOpen(resource.getVarchar09() == "" ? null : resource.getVarchar09());
                sySMenuJsonObject.setList(null);
                //二级菜单的list
                sySMenuJsonObjectList2.add(sySMenuJsonObject);
            } else if ("0".equals(resource.getVarchar05()) && "0".equals(resource.getVarchar01())) {
                //菜单管理一级菜单
                resource2 = resource;
            }
        }

        sySMenuJsonObject2.setMenuId(Integer.valueOf(resource2.getVarchar00()));
        sySMenuJsonObject2.setParentId(Integer.valueOf(resource2.getVarchar01()));
        sySMenuJsonObject2.setParentName(resource2.getVarchar02() == "" ? null : resource2.getVarchar00());
        sySMenuJsonObject2.setName(resource2.getVarchar03());
        sySMenuJsonObject2.setUrl(resource2.getVarchar04() == "" ? null : resource1.getVarchar04());
        sySMenuJsonObject2.setType(Integer.valueOf(resource1.getVarchar05()));
        sySMenuJsonObject2.setIcon(resource2.getVarchar06());
        sySMenuJsonObject2.setCode(resource2.getVarchar07());
        sySMenuJsonObject2.setOrderNum(Integer.valueOf(resource2.getVarchar08()));
        sySMenuJsonObject2.setOpen(resource2.getVarchar09() == "" ? null : resource2.getVarchar09());
        sySMenuJsonObject2.setList(sySMenuJsonObjectList2);

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(sySMenuJsonObject1);
        jsonArray.add(sySMenuJsonObject2);

        return jsonArray.toString();
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
            return null;
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
            return null;
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
            return null;
        }
        List<Long> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionList) {
            Long permissionId = rolePermission.getPermissionId();
            permissionIds.add(permissionId);
        }
        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andIdIn(permissionIds);
        List<Permission> permissionList = permissionService.selectByCriteria(permissionCriteria);
        if (CollectionUtils.isEmpty(permissionList)) {
            return null;
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
            return null;
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

                for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicPropertyList) {
                    if ("varchar00".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar00());
                    }
                    if ("varchar01".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar01());
                    }
                    if ("varchar02".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar02());
                    }
                    if ("varchar03".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar03());
                    }
                    if ("varchar04".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar04());
                    }
                    if ("varchar05".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar05());
                    }
                    if ("varchar06".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar06());
                    }
                    if ("varchar07".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar07());
                    }
                    if ("varchar08".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar08());
                    }
                    if ("varchar09".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar09());
                    }
                    if ("varchar10".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar10());
                    }
                    if ("varchar11".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar11());
                    }
                    if ("varchar12".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar12());
                    }
                    if ("varchar13".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar13());
                    }
                    if ("varchar14".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar14());
                    }
                    if ("varchar15".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar15());
                    }
                    if ("varchar16".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar16());
                    }
                    if ("varchar17".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar17());
                    }
                    if ("varchar18".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar18());
                    }
                    if ("varchar19".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getVarchar19());
                    }
                    if ("int01".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt01());
                    }
                    if ("int02".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt02());
                    }
                    if ("int03".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt03());
                    }
                    if ("int04".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt04());
                    }
                    if ("int05".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getInt05());
                    }
                    if ("boolean01".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getBoolean01());
                    }
                    if ("boolean02".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getBoolean02());
                    }
                    if ("boolean03".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getBoolean03());
                    }
                    if ("boolean04".equals(resourceDynamicProperty.getFiledName())) {
                        jsonObject.put(resourceDynamicProperty.getDescription(), resource.getBoolean04());
                    }
                }

                result.append(jsonObject.toString());
                jsonArray.add(jsonObject);
//                result.append("\n");
            }

            return jsonArray.toString();
        }
        return null;
    }

    @Override
    public void saveResourcesExcel(List<ResourceExcelView> resourceExcelViews) {

        ResourceView resourceView;
        Resource resource;
        for (ResourceExcelView resourceExcel : resourceExcelViews) {
            resourceView = new ResourceView();
            resource = new Resource();

            if (!StringUtils.isEmpty(resourceExcel.getApplicationName())) {
                //设置appId'
                ApplicationCriteria applicationCriteria = new ApplicationCriteria();
                applicationCriteria.createCriteria().andNameEqualTo(resourceExcel.getApplicationName());
                Application application = applicationService.selectSingleByCriteria(applicationCriteria);
                if (!ObjectUtils.isEmpty(application)) {
                    resource.setApplicationId(application.getId());
                } else {
                    throw new RuntimeException("应用："+ resourceExcel.getApplicationName() +" 不存在");
                }
            }
            if (!StringUtils.isEmpty(resourceExcel.getTenantName())) {
                //设置appId'
                TenantCriteria tenantCriteria = new TenantCriteria();
                tenantCriteria.createCriteria().andNameEqualTo(resourceExcel.getTenantName());
                Tenant tenant = tenantService.selectSingleByCriteria(tenantCriteria);
                if (!ObjectUtils.isEmpty(tenant)) {
                    resource.setTenantId(tenant.getId());
                } else {
                    throw new RuntimeException("租户：" + resourceExcel.getTenantName() + " 不存在");
                }
            }

            if (!StringUtils.isEmpty(resourceExcel.getType())) {
                ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
                resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(resourceExcel.getType());
                List<ResourceDynamicProperty> resourceDynamicPropertyList = resourceDynamicPropertyService.selectByCriteria(resourceDynamicPropertyCriteria);
                if (!CollectionUtils.isEmpty(resourceDynamicPropertyList)) {
                    resource.setType(resourceExcel.getType());
                }  else {
                    throw new RuntimeException("资源类型：" + resourceExcel.getType() + " 不存在");
                }
            }

            if (!StringUtils.isEmpty(resourceExcel.getName())) {
                resource.setName(resourceExcel.getName());
            } else {
                throw new RuntimeException("资源名称不能为空");
            }

            resource.setActions(resourceExcel.getActions());

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

    /**
     * 根据userId 获取权限，根据权限的resource_path_wildcard和action匹配获取资源列表
     * @param userId
     * @return
     */
    private List<Resource> getResourcesByUserId(Long userId) {
        //根据userId 拿 group
        GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
        groupUserCriteria.createCriteria().andUserIdEqualTo(userId);
        List<GroupUser> groupUserList = groupUserService.selectByCriteria(groupUserCriteria);

        if (CollectionUtils.isEmpty(groupUserList)) {
            return null;
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
            return null;
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
            return null;
        }
        List<Long> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionList) {
            Long permissionId = rolePermission.getPermissionId();
            permissionIds.add(permissionId);
        }
        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andIdIn(permissionIds);
        List<Permission> permissionList = permissionService.selectByCriteria(permissionCriteria);
        if (CollectionUtils.isEmpty(permissionList)) {
            return null;
        }

        //通过权限的通配符 查询相对应的resource
        List<Resource> resourceList = new ArrayList<>();
        for (Permission permission : permissionList) {
            String resourcePathWildcard = permission.getResourcePathWildcard();
            if (!StringUtils.isEmpty(resourcePathWildcard)) {
                ResourceCriteria resourceCriteria = new ResourceCriteria();
                resourceCriteria.createCriteria().andPathLike(resourcePathWildcard).andActionsLike(permission.getAction());
                List<Resource> resources = this.selectByCriteria(resourceCriteria);
                resourceList.addAll(resources);
            }
        }
        if (CollectionUtils.isEmpty(resourceList)) {
            return null;
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

        for (int i = 0; i < resourceList1.size(); i ++) {
            //foreach 内部是调用iterator实现的，不能直接使用remove
            if ("readonly".equals(resourceList1.get(i).getActions())) {
                resourceList1.remove(i);
            }
        }

        return resourceList1;
    }

}