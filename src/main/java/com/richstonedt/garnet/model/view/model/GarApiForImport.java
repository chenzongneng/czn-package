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

    private GarApi api;

    private List<GarApi> apiList = new ArrayList<>();

    public GarApi getApi() {
        return api;
    }

    public void setApi(GarApi api) {
        this.api = api;
    }

    public List<GarApi> getApiList() {
        return apiList;
    }

    public void setApiList(List<GarApi> apiList) {
        this.apiList = apiList;
    }
}
