package com.kalu.asmplugin.model;

import java.io.Serializable;

/**
 * description:
 * created by kalu on 2021-02-01
 */
public class PermissionVerificationModel implements Serializable {

    private String requestCall;
    private String requestSuperCall;
    private String requestMethodName;

    public String getRequestCall() {
        return requestCall;
    }

    public void setRequestCall(String requestCall) {
        this.requestCall = requestCall;
    }

    public String getRequestSuperCall() {
        return requestSuperCall;
    }

    public void setRequestSuperCall(String requestSuperCall) {
        this.requestSuperCall = requestSuperCall;
    }

    public String getRequestMethodName() {
        return requestMethodName;
    }

    public void setRequestMethodName(String requestMethodName) {
        this.requestMethodName = requestMethodName;
    }
}
