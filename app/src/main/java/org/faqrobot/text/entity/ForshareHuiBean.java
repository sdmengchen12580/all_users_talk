package org.faqrobot.text.entity;

/**
 * Created by yunwen on 2017/8/17.
 */

public class ForshareHuiBean {


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
     * returnObject : {"dlpCppBalance":8243,"dealerNo":"335407502","currentAwardPoint":0,"currentNewPoint":0,"currentBalance":9633.5,"usedPoint":-94600,"inactiveDate":"2017年12月31日","fullName":"李爱兰"}
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
         * dlpCppBalance : 8243
         * dealerNo : 335407502
         * currentAwardPoint : 0
         * currentNewPoint : 0
         * currentBalance : 9633.5
         * usedPoint : -94600
         * inactiveDate : 2017年12月31日
         * fullName : 李爱兰
         */

        private int dlpCppBalance;
        private String dealerNo;
        private int currentAwardPoint;
        private int currentNewPoint;
        private double currentBalance;
        private int usedPoint;
        private String inactiveDate;
        private String fullName;

        public int getDlpCppBalance() {
            return dlpCppBalance;
        }

        public void setDlpCppBalance(int dlpCppBalance) {
            this.dlpCppBalance = dlpCppBalance;
        }

        public String getDealerNo() {
            return dealerNo;
        }

        public void setDealerNo(String dealerNo) {
            this.dealerNo = dealerNo;
        }

        public int getCurrentAwardPoint() {
            return currentAwardPoint;
        }

        public void setCurrentAwardPoint(int currentAwardPoint) {
            this.currentAwardPoint = currentAwardPoint;
        }

        public int getCurrentNewPoint() {
            return currentNewPoint;
        }

        public void setCurrentNewPoint(int currentNewPoint) {
            this.currentNewPoint = currentNewPoint;
        }

        public double getCurrentBalance() {
            return currentBalance;
        }

        public void setCurrentBalance(double currentBalance) {
            this.currentBalance = currentBalance;
        }

        public int getUsedPoint() {
            return usedPoint;
        }

        public void setUsedPoint(int usedPoint) {
            this.usedPoint = usedPoint;
        }

        public String getInactiveDate() {
            return inactiveDate;
        }

        public void setInactiveDate(String inactiveDate) {
            this.inactiveDate = inactiveDate;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}
