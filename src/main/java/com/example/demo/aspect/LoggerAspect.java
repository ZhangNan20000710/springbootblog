package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LoggerAspect {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.demo.controller.*.*(..))")
    public void log(){

    }
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String name = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        logger.info("日志信息前置通知{}",new RequestLog(args,ip,url,name).toString());
    }
    @After("log()")
    public void doAfter(){
    }
    @AfterReturning(pointcut = "log()",returning = "result")
    public void doAfterReturn(Object result){
        logger.info("返回值:{}",result.toString());
    }
    private class RequestLog{
        Object [] args;
        String ip;
        String url;
        String methodName;

        public RequestLog(Object[] args, String ip, String url, String methodName) {
            this.args = args;
            this.ip = ip;
            this.url = url;
            this.methodName = methodName;
        }

        @Override
        public String toString() {
            return "{" +
                    "args=" + Arrays.toString(args) +
                    ", ip='" + ip + '\'' +
                    ", url='" + url + '\'' +
                    ", methodName='" + methodName + '\'' +
                    '}';
        }
    }
}
