package com.oyty.im.net;

/**
 * Created by oyty on 5/5/16.
 */
public class AppException extends Exception {
    public enum ExceptionStatus{
        FileNotFoundException, IllegalStateException, ParseException, IOException, CancelException, ServerException, ParameterException, TimeoutException, ParseJsonException
    }

    private ExceptionStatus status;
    private int errorCode = -1;
    private String errorInfo;

    public AppException(ExceptionStatus status,String detailMessage) {
        super(detailMessage);
        this.status = status;
    }

    public AppException(String detailMessage,int errorCode,String errorInfo){
        super(detailMessage);
        this.status = ExceptionStatus.ServerException;
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public ExceptionStatus getStatus(){
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
