package com.api.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.api.conf.Configuration;
import com.api.security.AuthInfo;
import com.api.security.Realm;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	Configuration conf = null;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
    	HandlerMethod methodHandler=(HandlerMethod) obj;
    	Authentication auth=methodHandler.getMethodAnnotation(Authentication.class);
    	if (auth == null|| auth.validate() == false) {
    		logger.info("no check");
			return true;
		} else {
    	  String headerToken=request.getHeader("token");
          String tokenStr=request.getParameter("token");
          if(!request.getRequestURI().equals("/adm/api/login")){
          	if(headerToken==null && tokenStr==null){
          		response.sendRedirect("/adm/vue-login.html");
          		logger.info("token is null");
          		return false;
          	}
          	if(tokenStr!=null){
          		headerToken=tokenStr;
          	}
          	
          	//check token
//          	logger.info(headerToken);
          	AuthInfo ati = new AuthInfo(headerToken);
          	String[] urlstr = request.getRequestURI().split("\\?");
          	if(urlstr[0].endsWith(".html")) {
          		ati.setPageurlflg("1");
          		ati.setPagename(request.getRequestURI());
          	}else {
          		ati.setPageurlflg("2");
          		ati.setUrl(request.getRequestURI());
          	}
          	
          	//Authorization
          	Realm rl = new Realm();
          	ati = rl.doGetAuthorizationInfo(ati);
          	if(ati.getPassUserExists().equals("n")) {
          		logger.info("User not exists or password is error");
          		returnAuthInfo(response,"300","User not exists or password is error");
          		return false;
          	}
          	if(ati.getPassToken().equals("n")) {
          		logger.info("Token is exp");
          		returnAuthInfo(response,"301","Token is exp");
          		return false;
          	}
          	if(ati.getPassTokenStore().equals("n")) {
          		logger.info("Token is not exists in db");
          		returnAuthInfo(response,"302","Token is not exists in db");
          		return false;
          	}
          	//Authentication
          	ati = rl.doGetAuthenticationInfo(ati);
          	if(ati.getPassPermission().equals("n")) {
          		logger.info("permission is not right");
          		returnAuthInfo(response,"303","permission is not right");
          		return false;
          	}
          }
          if(!request.getRequestURI().contains("/api/file/download")) {
          	response.setHeader("token",headerToken);
          }
		}
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj,ModelAndView modelAndView) throws Exception {
    	System.out.println("post");
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj, Exception e) throws Exception {
    	System.out.println("after");
    }
    
    private void returnAuthInfo(HttpServletResponse response,String code,String msg) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultcode", code);
		resultMap.put("msg", msg);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().write(JSON.toJSONString(resultMap));
	}
}
