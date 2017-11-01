package org.faqrobot.text.entity;

/**
 * Created by yunwen on 2017/8/17.
 * 业务资格有效期查询
 */

public class BussinessInactiveDateBean {


    /**
     * success : true
     * errorCode : null
     * errorMessage : null
     * exceptionCode : null
     * exceptionMessage : null
     * exceptionStack : null
     * redirection : null
     * validateErrors : null
     * returnObject : {"dealerNo ":"336065666","fullName ":"张三","inactiveDate":"2015-10-10"}
     */

    private boolean success;
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
         * dealerNo  : 336065666
         * fullName  : 张三
         * inactiveDate : 2015-10-10
         */

        private String dealerNo;
        private String fullName;
        private String inactiveDate;

        public String getDealerNo() {
            return dealerNo;
        }

        public void setDealerNo(String dealerNo) {
            this.dealerNo = dealerNo;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getInactiveDate() {
            return inactiveDate;
        }

        public void setInactiveDate(String inactiveDate) {
            this.inactiveDate = inactiveDate;
        }
    }
}
