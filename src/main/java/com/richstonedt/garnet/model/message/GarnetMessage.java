package com.richstonedt.garnet.model.message;

/**
 * <b><code>GarnetMessage</code></b>
 * <p/>
 * TorinoSrcMessage是所有返回对象的封装对象，直接暴露给外界
 * <p/>
 * <b>Creation Time:</b> Thu Oct 01 18:45:41 GMT+08:00 2017.
 *
 * @author lvxin
 * @version 1.0.0
 * @since torinosrc-model 1.0.0
 */
public class GarnetMessage<T> {

    /**
     * 操作代码
     */
    private String messageCode;

    /**
     * 操作状态
     */
    private String messageStatus;

    /**
     * 操作说明
     */
    private String messageDescription;

    /**
     * 返回数据
     */
    private T data;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 设置Message的所有值
     *
     * @param messageCode 操作代码
     * @param messageStatus 操作状态
     * @param messageDescription 操作说明
     * @param data 返回数据
     */
    public void setTorinosrcMessage(String messageCode, String messageStatus, String messageDescription, T data){
        this.setMessageCode(messageCode);
        this.setMessageStatus(messageStatus);
        this.setMessageDescription(messageDescription);
        this.setData(data);
    }

}
