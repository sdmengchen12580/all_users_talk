package org.faqrobot.text.entity;

/**
 * Created by yunwen on 2017/8/15.
 */

public class LoginBean {


    /**
     * success : true
     * httpStatus : null
     * errorCode : null
     * errorMessage : null
     * exceptionCode : null
     * exceptionMessage : null
     * exceptionStack : null
     * redirection : null
     * validateErrors : null
     * returnObject : {"userName":"335407502","authenticated":true}
     */

    private boolean success;
    private Object httpStatus;
    private Object errorCode;
    private Object errorMessage;
    private Object exceptionCode;
    private Object exceptionMessage;
    private Object exceptionStack;
    private Object redirection;
    private Object validateErrors;
    private ReturnObjectBean returnObject;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Object httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(Object exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public Object getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(Object exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Object getExceptionStack() {
        return exceptionStack;
    }

    public void setExceptionStack(Object exceptionStack) {
        this.exceptionStack = exceptionStack;
    }

    public Object getRedirection() {
        return redirection;
    }

    public void setRedirection(Object redirection) {
        this.redirection = redirection;
    }

    public Object getValidateErrors() {
        return validateErrors;
    }

    public void setValidateErrors(Object validateErrors) {
        this.validateErrors = validateErrors;
    }

    public ReturnObjectBean getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(ReturnObjectBean returnObject) {
        this.returnObject = returnObject;
    }

    public static class ReturnObjectBean {
        /**
         * userName : 335407502
         * authenticated : true
         */

        private String userName;
        private String authenticated;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAuthenticated() {
            return authenticated;
        }

        public void setAuthenticated(String authenticated) {
            this.authenticated = authenticated;
        }
    }
}
