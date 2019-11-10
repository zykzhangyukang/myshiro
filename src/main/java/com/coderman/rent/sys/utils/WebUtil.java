package com.coderman.rent.sys.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class WebUtil {
    /**
     * 得到requset
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    /**
     * 得到session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 将前端的对象装成Map
     * @return
     */
    public static final Map<String, Object> convert() {
        HttpServletRequest request = WebUtil.getRequest();
        Map<String, Object> param = new HashMap<String, Object>();
        Enumeration<String> e = request.getParameterNames();
        String name = "";
        String str[] = null;
        while (e.hasMoreElements()) {
            name = e.nextElement();
            str = request.getParameterValues(name);
            name = name.replace("[", "").replace("]", "");
            if (str.length == 1)
                param.put(name, str[0].replaceAll("\"", "\\\"").replaceAll("\'", "\\\'").replaceAll("<", "<").replaceAll(">", ">"));
            else {
                for (int i = 0; i < str.length; i++)
                    str[i] = str[i].replaceAll("\"", "\\\"").replaceAll("\'", "\\\'").replaceAll("<", "<").replaceAll(">", ">");
                param.put(name, str);
            }
        }
        return param;
    }
}
