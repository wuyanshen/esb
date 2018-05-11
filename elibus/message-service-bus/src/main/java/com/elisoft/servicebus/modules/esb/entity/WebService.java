package com.elisoft.servicebus.modules.esb.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JiJie.LianG
 * @date 2018-05-09 下午5:03
 */
public class WebService implements Serializable{
    private String id;
    private String soapUrl;
    private String soapAction;
    private String reqInfo;
    private String respInfo;
    private Date createDate;
    private Date updateDate;
    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSoapUrl() {
        return soapUrl;
    }

    public void setSoapUrl(String soapUrl) {
        this.soapUrl = soapUrl;
    }

    public String getSoapAction() {
        return soapAction;
    }

    public void setSoapAction(String soapAction) {
        this.soapAction = soapAction;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(String reqInfo) {
        this.reqInfo = reqInfo;
    }

    public String getRespInfo() {
        return respInfo;
    }

    public void setRespInfo(String respInfo) {
        this.respInfo = respInfo;
    }

    @Override
    public String toString() {
        return "WebService{" +
                "id='" + id + '\'' +
                ", soapUrl='" + soapUrl + '\'' +
                ", soapAction='" + soapAction + '\'' +
                ", reqInfo='" + reqInfo + '\'' +
                ", respInfo='" + respInfo + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
