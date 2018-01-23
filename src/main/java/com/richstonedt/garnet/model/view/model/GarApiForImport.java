package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarApi;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GarApiForImport</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>16:15
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@ApiModel(value = "API导入数据")
public class GarApiForImport {

    /**
     * The Api.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private GarApi api;

    /**
     * The Api list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private List<GarApi> apiList = new ArrayList<>();

    /**
     * Gets api.
     *
     * @return the api
     * @since garnet-core-be-fe 0.1.0
     */
    public GarApi getApi() {
        return api;
    }

    /**
     * Sets api.
     *
     * @param api the api
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApi(GarApi api) {
        this.api = api;
    }

    /**
     * Gets api list.
     *
     * @return the api list
     * @since garnet-core-be-fe 0.1.0
     */
    public List<GarApi> getApiList() {
        return apiList;
    }

    /**
     * Sets api list.
     *
     * @param apiList the api list
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApiList(List<GarApi> apiList) {
        this.apiList = apiList;
    }
}
