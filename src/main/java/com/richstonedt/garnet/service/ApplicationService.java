package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import com.richstonedt.garnet.model.parm.ApplicationParm;
import com.richstonedt.garnet.model.view.ApplicationView;

import java.util.List;

/**
 * The interface Application service.
 */
public interface ApplicationService extends BaseService<Application, ApplicationCriteria, Long> {

    /**
     * Insert application.
     *
     * @param applicationView the application view
     */
    public Long insertApplication(ApplicationView applicationView);

    /**
     * Update application.
     *
     * @param applicationView the application view
     */
    public void updateApplication(ApplicationView applicationView);

    /**
     * Delete application.
     *
     * @param applicationId the application id
     */
    public void deleteApplication(Long applicationId);

    /**
     * 根据条件查询并且返回application列表
     * @param applicationParm
     * @return
     */
    public PageUtil queryApplicationsByParms(ApplicationParm applicationParm);


    /**
     * 获取应用并且包含租户
     * @param applicaitonId
     * @return
     */
    public ApplicationView getApplicationWithTenant(Long applicaitonId);

    /**
     * 当用户点击删除时，将其状态设为禁用
     * @param application
     */
    public void updateStatusById(Application application);

    /**
     * 查看该应用是否有关联到应用组
     * @return
     */
    boolean hasRelated(String ids);

    /**
     * 获取未被应用组选中的所有应用
     * @return
     */
    List<Application> getApplicatinsWithoutRouterGroup(ApplicationParm applicationParm);

    /**
     * 通过用户名获取默认应用首页URL列表
     * @param userName
     * @return
     */
    List<Application> getApplicationsByUserName(String userName);

    /**
     * 根据appCode获取单个应用
     */
    Application getApplicationByAppCode(String appCode);
}