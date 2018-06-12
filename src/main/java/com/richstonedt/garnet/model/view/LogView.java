package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.ApplicationTenant;
import com.richstonedt.garnet.model.Log;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * The type Application view.
 */
public class LogView {

    private Log log;

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }
}
