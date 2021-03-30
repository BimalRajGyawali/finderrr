package com.ncit.finder.db;

public class DBResponse {
    private String responseMessage;
    private boolean successStatus;

    public String getResponseMessage() {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    public boolean isSuccessStatus() {
        return successStatus;
    }
    public void setSuccessStatus(boolean successStatus) {
        this.successStatus = successStatus;
    }
    
}
