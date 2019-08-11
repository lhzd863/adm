package com.api.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.conf.Configuration;
import com.api.conf.DefaultConf;
import com.api.conf.VarConstants;
import com.api.filter.Authentication;
import com.api.mapper.PermissionMapper;
import com.api.parameter.PermissionParameter;
import com.api.security.AuthInfo;
import com.api.util.DatasourceMybatisSqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;

@Controller
public class PermissionUserPageController {
	public PermissionParameter pp ;
	Configuration conf = null;
	@Authentication(validate=true)
	@RequestMapping(value="/api/permission/userpage/get",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String permissionUserPageGetJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
		ObjectMapper mapper = new ObjectMapper();
		Map m=null;
		try {
			m = mapper.readValue(jsonData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String headerToken=req.getHeader("token");
		AuthInfo ai = new AuthInfo(headerToken);
		String username = ai.getUsername();
		pp = new PermissionParameter();
		pp.setUser_name(username);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		List list = null;
		try {
			list = session.getMapper(PermissionMapper.class).getUserPage(pp);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
        JSONArray jsonarray = JSONArray.fromObject(list);
//        System.out.println(jsonarray.toString());
		return jsonarray.toString();
	}
	
	@Authentication(validate=true)
	@RequestMapping(value="/api/permission/userpage/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String permissionUserPageAddJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
		ObjectMapper mapper = new ObjectMapper();
		Map m=null;
		try {
			m = mapper.readValue(jsonData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String page_id = (String) m.get("page_id");
		String user_id = (String) m.get("user_id");
		String role_id = (String) m.get("role_id");
		
		pp = new PermissionParameter();
		pp.setPage_id(page_id);
		pp.setUser_id(user_id);
		pp.setRole_id(role_id);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		try {
			session.getMapper(PermissionMapper.class).addUserPage(pp);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.commit();
			session.close();	
		}
//        JSONArray jsonarray = JSONArray.fromObject(list);
//        System.out.println(jsonarray.toString());
//		return jsonarray.toString();
		return "[{}]";
	}
	
	@Authentication(validate=true)
	@RequestMapping(value="/api/permission/userpage/del",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String userPageDelJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
		ObjectMapper mapper = new ObjectMapper();
		Map m=null;
		try {
			m = mapper.readValue(jsonData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String id = (String) m.get("id");
		
		pp = new PermissionParameter();
		pp.setId(id);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		try {
			session.getMapper(PermissionMapper.class).delUserPage(pp);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.commit();
			session.close();
		}
//        JSONArray jsonarray = JSONArray.fromObject(list);
//        System.out.println(jsonarray.toString());
//		return jsonarray.toString();
		return "[{}]";
	}
	
	@Authentication(validate=true)
	@RequestMapping(value="/api/permission/userpage/upd",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String userPageUpdJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
		ObjectMapper mapper = new ObjectMapper();
		Map m=null;
		try {
			m = mapper.readValue(jsonData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String id = (String) m.get("id");
		String page_id = (String) m.get("page_id");
		String user_id = (String) m.get("user_id");
		String role_id = (String) m.get("role_id");
		String enable = (String) m.get("enable");
		
		pp = new PermissionParameter();
		pp.setId(id);
		pp.setPage_id(page_id);
		pp.setUser_id(user_id);
		pp.setRole_id(role_id);
		pp.setEnable(enable);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		try {
			session.getMapper(PermissionMapper.class).updUserPage(pp);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.commit();
			session.close();
		}
//        JSONArray jsonarray = JSONArray.fromObject(list);
//        System.out.println(jsonarray.toString());
		return "[{}]";
	}
}
