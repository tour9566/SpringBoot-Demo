package com.example.webapp.framwork.aop;

import com.example.webapp.framwork.operaEnum.ProxyEnum;
import java.lang.annotation.*;

/***
 * 用于日志打印的注解类
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface  SystemActionLog {
	ProxyEnum.OperaTypeEnum operationType() default ProxyEnum.OperaTypeEnum.UNKONWN;
	ProxyEnum.LogTypeEnum logType() default ProxyEnum.LogTypeEnum.UNKNOW;
}
