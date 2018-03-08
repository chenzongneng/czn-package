/*
 * 广州丰石科技公司有限公司拥有本软件版权2015并保留所有权利。
 * Copyright 2015, GuangZhou Rich Stone Data Technologies Company Limited, 
 * All rights reserved.
 */
package com.richstonedt.garnet.model.message;

import io.swagger.annotations.ApiModel;

/**
 * <b><code>GarnetErrorResponseMessage</code></b>
 * <p/>
 * class_comment
 * <p/>
 * <b>Creation Time:</b> 2016/10/31 20:29.
 *
 * @author xiedongmei
 * @version $ {Revision} 2016/10/31
 * @since torinosrc-commons 0.0.1
 */
@ApiModel
public class GarnetErrorResponseMessage {

    private String errorResponseMessage;

    public GarnetErrorResponseMessage(){
        super();
    }

    public GarnetErrorResponseMessage(String errorResponseMessage){
        super();
        this.errorResponseMessage = errorResponseMessage;
    }

    public String getErrorResponseMessage() {
        return errorResponseMessage;
    }

    public void setErrorResponseMessage(String errorResponseMessage) {
        this.errorResponseMessage = errorResponseMessage;
    }

}
