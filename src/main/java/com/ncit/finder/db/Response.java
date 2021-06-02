package com.ncit.finder.db;

public class Response {
    private String responseMessage;
    private boolean successStatus;

    public Response() {
    }

    public Response(String responseMessage, boolean successStatus) {
        this.responseMessage = responseMessage;
        this.successStatus = successStatus;
    }
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

    @Override
    public String toString() {
        return "Response [responseMessage=" + responseMessage + ", successStatus=" + successStatus + "]";
    }
    
    
}
