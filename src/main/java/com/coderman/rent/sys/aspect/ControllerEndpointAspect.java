package com.coderman.rent.sys.aspect;

import com.coderman.rent.sys.annotation.ControllerEndpoint;
import com.coderman.rent.sys.enums.ResultEnum;
import com.coderman.rent.sys.service.LogService;
import com.coderman.rent.sys.utils.WebUtil;
import com.coderman.rent.sys.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author MrBird
 */
@Aspect
@Component
public class ControllerEndpointAspect extends AspectSupport {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.coderman.rent.sys.annotation.ControllerEndpoint)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point)  {
        Object result;
        Method targetMethod = resolveMethod(point);
        ControllerEndpoint annotation = targetMethod.getAnnotation(ControllerEndpoint.class);
        String operation = annotation.operation();
        long start = System.currentTimeMillis();
        try {
            result = point.proceed();
            if (StringUtils.isNotBlank(operation)) {
                HttpServletRequest request = WebUtil.getRequest();
                logService.saveLog(point, targetMethod, request, operation, start);
            }
            return result;
        } catch (Throwable throwable) {
            String exceptionMessage = annotation.exceptionMessage();
            String message = throwable.getMessage();
            System.out.println(message+":"+exceptionMessage);
            return ResultVo.ERROR(ResultEnum.SYS_ERROR);
        }
    }
}



