package com.api.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.bean.PermissionBean;
import com.api.conf.Configuration;
import com.api.conf.DefaultConf;
import com.api.conf.VarConstants;
import com.api.filter.Authentication;
import com.api.mapper.PermissionMapper;
import com.api.parameter.PermissionParameter;
import com.api.security.IceKey;
import com.api.security.JWTToken;
import com.api.util.DatasourceMybatisSqlSession;
import com.api.util.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LoginController {
	Configuration conf = null;
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Authentication(validate=false)
	@RequestMapping(value="/api/login",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String loginUser(@RequestParam (value="username", defaultValue="") String username,@RequestParam (value="userpassword", defaultValue="") String userpassword,HttpServletRequest req, HttpServletResponse resp) {
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
		IceKey ik = new IceKey(0);
		userpassword=ik.encode(userpassword, conf.get(VarConstants.VAR_ICE_KEY_PASSWORD));
		PermissionParameter pp = new PermissionParameter();
		pp.setUser_name(username);
		pp.setPassword(userpassword);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		List list = null;
		try {
			list = session.getMapper(PermissionMapper.class).checkPassword(pp);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			return "[{\"accesstoken\":\"xxx\",\"uid\":\"0000\",\"msg\":\"fail\"}]";
		}finally{
			session.commit();
		}
		if(list.size()==0) {
			return "[{\"accesstoken\":\"xxx\",\"uid\":\"0000\",\"msg\":\"user not exists or password err\"}]";
		}
		PermissionBean pb = (PermissionBean) list.get(0);
		String userId = pb.getId();
        String issuer = pb.getUser_name();
        String token = JWTToken.createJWT(userId,issuer);
        
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("token", token);
		resp.setHeader("username", issuer);
		resp.setHeader("uid", userId);
        //add token to db
		try {
			PermissionParameter pp1 = new PermissionParameter();
			pp1.setToken(token);
			session.getMapper(PermissionMapper.class).addToken(pp1);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			return "[{\"accesstoken\":\"xxx\",\"uid\":\"0000\",\"msg\":\"fail\"}]";
			
		} finally {
			session.commit();
			session.close();
		}
		return "[{\"accesstoken\":\""+token+"\",\"uid\":\""+userId+"\",\"msg\":\""+"ok"+"\"}]";
		
	}
	
	@Authentication(validate=false)
	@RequestMapping(value="/api/sendredirect",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public void sendRedirectUrl(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
		ObjectMapper mapper = new ObjectMapper();
		Map m = null;
		try {
			m = mapper.readValue(jsonData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String url = (String) m.get("urlname");
		resp.setHeader("Content-Type", "application/json;charset=UTF-8");
		resp.setHeader("token", req.getHeader("token"));
		resp.setHeader("username", req.getHeader("username"));
		resp.setHeader("uid", req.getHeader("uid"));
		try {
			resp.sendRedirect("/adm/"+url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}
