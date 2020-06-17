package com.example.webapp.framwork.exception.Business;

import com.example.webapp.framwork.base.Result;
import org.apache.log4j.Logger;


public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3152616724785436891L;
    private static final Logger log = Logger.getLogger(BusinessException.class);

    public BusinessException(String errorMsg, Number errorCode) {
        super(createFriendlyErrMsg(errorMsg, errorCode));
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(Throwable throwable, String errorMsg, Number errorCode) {
        super(throwable);
    }

    //处理内部错误，不返回错误提示给前端
    private static String createFriendlyErrMsg(String msgBody, Number errorCode) {
        Result result=new Result();
        result.setStatus(errorCode);
        result.setMsg(msgBody);
        return result.toString();
    }

    //处理业务错误，返回错误提示给前端
    public Result handle(){
        Result result=new Result();
        result.setStatus(500);
        result.setMsg("服务器内部错误,联系管理员");
        return result;
    }
}