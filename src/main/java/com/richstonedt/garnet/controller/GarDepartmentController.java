package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.view.model.GarVMDepartment;
import com.richstonedt.garnet.service.GarDepartmentService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b><code>GarDepartmentController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>11:29
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@RestController
@RequestMapping("/v1.0")
@Api(tags = "[Garnet]部门管理接口")
public class GarDepartmentController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarDepartmentController.class);

    /**
     * The Dept service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarDepartmentService departmentService;

//    /**
//     * The User service.
//     *
//     * @since garnet-core-be-fe 0.1.0
//     */
//    @Autowired
//    private GarUserService userService;


    /**
     * Gets dept list.
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the dept list
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/departments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询部门列表", notes = "Get department list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("department:info")
    public ResponseEntity<?> getDeptList(
            @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
            @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
            @ApiParam(value = "名称") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            Integer offset = (page - 1) * limit;
            Map<String, Object> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);
            params.put("searchName", searchName);
            List<GarVMDepartment> list = departmentService.queryDepartmentListByParams(params);
            int totalCount = departmentService.queryTotalMenuByParam(params);
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to Get department list ", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Gets department list.
     *
     * @param userId the user id
     * @return the department list
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/departments/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询该用户的部门列表", notes = "Get department list by user id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("department:list:byUser")
    public ResponseEntity<?> getDeptListByUserId(@ApiParam(value = "userId,用户ID") @PathVariable(value = "userId") Long userId) {
        try {
            return new ResponseEntity<>(departmentService.getUserDepartmentList(userId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to Get department list by userId :" + userId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

//    /**
//     * Gets department list to add.
//     *
//     * @param userId the user id
//     * @return the department list to add
//     * @since garnet-core-be-fe 0.1.0
//     */
//    @RequestMapping(value = "/departments/add/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(value = "[Garnet]查询部门列表用于增加部门", notes = "Get department list to add by user id")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "successful query"),
//            @ApiResponse(code = 500, message = "internal server error")})
//    @RequiresPermissions("department:list:toAdd:byUser")
//    public ResponseEntity<?> getDeptListToAdd(@ApiParam(value = "userId,用户ID") @PathVariable(value = "userId") Long userId) {
//        try {
//            List<GarVMDepartment> departmentList = departmentService.getUserDeptList(userId);
//            GarVMUser vmUser = userService.searchUser(userId);
//            GarVMDepartment root = new GarVMDepartment();
//            root.setDepartmentId(0L);
//            root.setName(vmUser.getTenantName());
//            root.setParentDeptId(-1L);
//            departmentList.add(root);
//            return new ResponseEntity<>(departmentList, HttpStatus.OK);
//        } catch (Throwable t) {
//            LOG.error("Failed to get department list to add", t);
//            return GarnetRsUtil.newResponseEntity(t);
//        }
//    }

    /**
     * Gets dept list to add.
     *
     * @return the dept list to add
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/departments/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询部门列表用于增加部门", notes = "Get department list to add by user id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("department:list:toAdd:byUser")
    public ResponseEntity<?> getDeptListToAdd() {
        try {
            List<GarVMDepartment> departmentList = departmentService.queryDepartmentListByParams(null);
            return new ResponseEntity<>(departmentList, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get department list to add", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Search department response entity.
     *
     * @param departmentId the department id
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/department/{departmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询部门信息", notes = "Get department by id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMDepartment.class),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("department:info")
    public ResponseEntity<?> searchDept(@ApiParam(value = "departmentId", required = true) @PathVariable("departmentId") Long departmentId) {
        try {
            return new ResponseEntity<>(departmentService.getVMDepartmentByDepartmentId(departmentId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get department :" + departmentId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Save department response entity.
     *
     * @param vmDept the gar department
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/department", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增部门", notes = "Create department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("department:create")
    public ResponseEntity<?> saveDept(@RequestBody GarVMDepartment vmDept) {
        try {
            departmentService.saveVMDepartment(vmDept);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create department :" + vmDept, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Update department response entity.
     *
     * @param vmDept the gar department
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/department", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]更新部门信息", notes = "Update department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("department:update")
    public ResponseEntity<?> updateDept(@RequestBody GarVMDepartment vmDept) {
        try {
            departmentService.updateVMDepartment(vmDept);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update department .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Delete department response entity.
     *
     * @param departmentIds the department ids
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/department", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id删除部门", notes = "Delete department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("department:delete:batch")
    public ResponseEntity<?> deleteDept(
            @ApiParam(value = "departmentIds,用‘,’隔开", required = true) @RequestParam(value = "departmentIds") String departmentIds) {
        try {
            Map<String, String> result = departmentService.deleteBatchByDepartmentIds(GarnetRsUtil.parseStringToList(departmentIds));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete applications :" + departmentIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
