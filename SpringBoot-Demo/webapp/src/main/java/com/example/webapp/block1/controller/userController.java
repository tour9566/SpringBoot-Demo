package com.example.webapp.block1.controller;

import com.example.webapp.framwork.aop.SystemActionLog;
import com.example.webapp.framwork.base.Result;
import com.example.webapp.framwork.base.ResultUtil;
import com.example.webapp.framwork.exception.Business.BusinessException;
import com.example.webapp.framwork.exception.Business.ErrorCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class userController {

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String getTest(){
        return "Test";
    }


    @RequestMapping(value ="/getName", method = RequestMethod.GET)
    @SystemActionLog
    public Result getName(@RequestParam(value = "name") String name){
        Result result = ResultUtil.success();
        if (name.equals("sucess")){
            result =  ResultUtil.success();
        }else if (name.equals("error")){
            result =  ResultUtil.error(ErrorCode.get(50000));
        }else{
            int i = 1/0;
        }
        return result;
    }

    @RequestMapping(value ="/getPassword", method = RequestMethod.GET)
    public String getPassword(){
        return "Password";
    }

    @RequestMapping(value ="/getAge", method = RequestMethod.GET)
    public String getAge(){
        return "Age";
    }


}
