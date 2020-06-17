package com.example.webapp.framwork.aop;

import com.example.webapp.framwork.exception.Business.BusinessException;
import com.example.webapp.framwork.exception.Business.ErrorCode;
import com.example.webapp.framwork.operaEnum.ProxyEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.concurrent.CancellationException;

import org.apache.log4j.Logger;


/***
 *  AOP切面类
 */

@Aspect
@Configuration
public class SpringLogAop {
    protected static final Logger log = Logger.getLogger(SpringLogAop.class);
	private HttpServletRequest request = null;// 获取request
	
	/***
	 * 方法名称: controllerAspect 
	 * 描述: (定义切点Pointcut)
	 */
	@Pointcut(value = "@annotation(com.example.webapp.framwork.aop.SystemActionLog)")
	public void aspectjMethod() {
		
	}

	/**
	 * Before 在核心业务执行前执行，不能阻止核心业务的调用。
	 * @author jt
	 * @param joinPoint
	 * @throws ClassNotFoundException
	 */
	@Before("aspectjMethod()")
	public void before(JoinPoint joinPoint) throws ClassNotFoundException {
	}

	/**
	 * After 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
	 * @author jt
	 * @param joinPoint
	 * @throws ClassNotFoundException
	 */
	@After(value = "aspectjMethod()")
	public void after(JoinPoint joinPoint) throws ClassNotFoundException {
	}

	/**
	 * Around 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
	 *
	 * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice 执行完AfterAdvice，再转到ThrowingAdvice
	 * @author jt
	 * @param joinPoint
	 * @return
	 */
	@Around(value = "aspectjMethod()")
	public Object around(ProceedingJoinPoint joinPoint) {
		// 调用核心逻辑
		Object obj =new Object();
		try {
			obj = joinPoint.proceed();
		}catch(NullPointerException e){
			obj =new BusinessException(ErrorCode.NullPointerException.msg, ErrorCode.NullPointerException.code).handle();
		}catch(IOException e){
			throw new BusinessException(ErrorCode.IOException.msg, ErrorCode.IOException.code);
		}catch(ClassNotFoundException e){
			throw new BusinessException(ErrorCode.ClassNotFoundException.msg, ErrorCode.ClassNotFoundException.code);
		}catch(ArithmeticException e) {
			throw new BusinessException(ErrorCode.ArithmeticException.msg, ErrorCode.ArithmeticException.code);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new BusinessException(ErrorCode.ArrayIndexOutOfBoundsException.msg, ErrorCode.ArrayIndexOutOfBoundsException.code);
		} catch(IllegalArgumentException e) {
			throw new BusinessException(ErrorCode.IllegalArgumentException.msg, ErrorCode.IllegalArgumentException.code);
		} catch(ClassCastException e) {
			throw new BusinessException( ErrorCode.ClassCastException.msg, ErrorCode.ClassCastException.code);
		} catch(SecurityException e){
			throw new BusinessException(ErrorCode.SecurityException.msg, ErrorCode.SecurityException.code);
		}  catch(SQLException e){
			throw new BusinessException(ErrorCode.SQLException.msg, ErrorCode.SQLException.code);
		} catch(NoSuchMethodError e) {
			throw new BusinessException(ErrorCode.NoSuchMethodError.msg, ErrorCode.NoSuchMethodError.code);
		}catch(InternalError e) {
			throw new BusinessException(ErrorCode.InternalError.msg, ErrorCode.InternalError.code);
		} catch(CancellationException e){
			throw new BusinessException( ErrorCode.CancellationException.msg, ErrorCode.CancellationException.code);
		} catch (Throwable e) {
			log.error(e);
		}finally {
			System.out.println(obj);
		}
		return obj;
	}

	/**
	 * AfterReturning 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
	 * @author jt
	 * @param joinPoint
	 */
	@AfterReturning(value = "aspectjMethod()", returning = "retVal")
	public void afterReturning(JoinPoint joinPoint, String retVal) {
		// todo something
	}

	/**
	 * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
	 *
	 * 注意：执行顺序在Around Advice之后
	 * @author jt
	 * @param joinPoint
	 * @param e
	 * @throws ClassNotFoundException
	 */
	@AfterThrowing(value = "aspectjMethod()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Throwable e) {
		Logger log = Logger.getLogger(joinPoint.getTarget().getClass());
		log.error("-------------------afterThrowing.handler.start-------------------");

		log.error(getMethodNameAndArgs(joinPoint));
		log.error("ConstantUtil.getTrace(e): " + getTrace(e));

		log.error("异常名称：" + e.getClass().toString());
		log.error("e.getMessage():" + e.getMessage());
		log.error("-------------------afterThrowing.handler.end-------------------");
		// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		// 在这里判断异常，根据不同的异常返回错误。
//
//		if (e.getClass().toString().equals(NullPointerException.class.toString())) {
//			throw new BusinessException(ErrorCode.NullPointerException.msg, ErrorCode.NullPointerException.code);
//		} else if (e.getClass().equals(IOException.class)) {
//			throw new BusinessException(ErrorCode.IOException.msg, ErrorCode.IOException.code);
//		} else if (e.getClass().equals(ClassNotFoundException.class)) {
//			throw new BusinessException(ErrorCode.ClassNotFoundException.msg, ErrorCode.ClassNotFoundException.code);
//		} else if (e.getClass().equals(ArithmeticException.class)) {
//			throw new BusinessException(ErrorCode.ArithmeticException.msg, ErrorCode.ArithmeticException.code);
//		} else if (e.getClass().equals(ArrayIndexOutOfBoundsException.class)) {
//			throw new BusinessException(ErrorCode.ArrayIndexOutOfBoundsException.msg, ErrorCode.ArrayIndexOutOfBoundsException.code);
//		} else if (e.getClass().equals(IllegalArgumentException.class)) {
//			throw new BusinessException(ErrorCode.IllegalArgumentException.msg, ErrorCode.IllegalArgumentException.code);
//		} else if (e.getClass().equals(ClassCastException.class)) {
//			throw new BusinessException( ErrorCode.ClassCastException.msg, ErrorCode.ClassCastException.code);
//		} else if (e.getClass().equals(SecurityException.class)) {
//			throw new BusinessException(ErrorCode.SecurityException.msg, ErrorCode.SecurityException.code);
//		} else if (e.getClass().equals(SQLException.class)) {
//			throw new BusinessException(ErrorCode.SQLException.msg, ErrorCode.SQLException.code);
//		} else if (e.getClass().equals(NoSuchMethodError.class)) {
//			throw new BusinessException(ErrorCode.NoSuchMethodError.msg, ErrorCode.NoSuchMethodError.code);
//		} else if (e.getClass().equals(InternalError.class)) {
//			throw new BusinessException( ErrorCode.InternalError.msg, ErrorCode.InternalError.code);
//		} else if(e.getClass().equals(ConnectException.class)){
//			throw new BusinessException( ErrorCode.ConnectException.msg, ErrorCode.ConnectException.code);
//		} else if(e.getClass().equals(CancellationException.class)){
//			throw new BusinessException( ErrorCode.CancellationException.msg, ErrorCode.CancellationException.code);
//		}else {
//			throw new BusinessException(ErrorCode.INTERNAL_PROGRAM_ERROR.msg + e.getMessage(), ErrorCode.INTERNAL_PROGRAM_ERROR.code);
//		}



	}

	/**
	 * 获取方法名和参数
	 * @author jt
	 * @param joinPoint
	 * @return
	 */
	private String getMethodNameAndArgs(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		StringBuffer sb = new StringBuffer("请求方法：");
		sb.append(joinPoint.getTarget().getClass().getName() + "."
				+ joinPoint.getSignature().getName() + "(");
		for (int i = 0; i < args.length; i++) {
			sb.append("arg[" + i + "]: " + args[i] + ",");
		}
		if (args.length > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 将异常信息输出到log文件
	 * @param t
	 * @return
	 */
	public static String getTrace(Throwable t) {
		StringWriter stringWriter= new StringWriter();
		PrintWriter writer= new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer= stringWriter.getBuffer();
		return buffer.toString();
	}

	private String  setLogBasic(JoinPoint joinPoint){
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = null;
		try {
			targetClass = Class.forName(targetName);
		} catch (ClassNotFoundException e) {
			log.error(e);
		}
		Method[] methods = targetClass.getMethods();
		ProxyEnum.OperaTypeEnum operationType = null;
		ProxyEnum.LogTypeEnum descType = null;
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					operationType = method.getAnnotation(SystemActionLog.class).operationType();
					descType = method.getAnnotation(SystemActionLog.class).logType();
					description=descType.getCode()+"-------------"+operationType.getCode()+"------------"+operationType.getDesc();
					break;
				}
			}
		}
		return description;
	}
}
