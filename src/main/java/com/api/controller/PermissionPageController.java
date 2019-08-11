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
import com.api.mapper.ConfMapper;
import com.api.mapper.PermissionMapper;
import com.api.parameter.ConfParameter;
import com.api.parameter.PermissionParameter;
import com.api.security.AuthInfo;
import com.api.security.IceKey;
import com.api.util.DatasourceMybatisSqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;

@Controller
public class PermissionPageController {
	public PermissionParameter pp ;
	Configuration conf = null;
	@Authentication(validate=false)
	@RequestMapping(value="/api/permission/page/get",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String permissionPageGetJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
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
			list = session.getMapper(PermissionMapper.class).getPage(pp);
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
	@RequestMapping(value="/api/permission/page/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String permissionPageAddJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
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

		String user_name = (String) m.get("user_name");
		String page_id = (String) m.get("page_id");
		String page_name = (String) m.get("page_name");
		String feather = (String) m.get("feather");
		String icon = (String) m.get("icon");
		String page_title = (String) m.get("page_title");
		String parent = (String) m.get("parent");
		String desc = (String) m.get("desc");
		
		pp = new PermissionParameter();
		pp.setUser_name(user_name);
		pp.setPage_id(page_id);
		pp.setPage_name(page_name);
		pp.setFeather(feather);
		pp.setIcon(icon);
		pp.setPage_title(page_title);
		pp.setParent(parent);
		pp.setDesc(desc);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		try {
			session.getMapper(PermissionMapper.class).addPage(pp);
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
	@RequestMapping(value="/api/permission/page/del",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String permissionPageDelJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
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
			session.getMapper(PermissionMapper.class).delPage(pp);
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
	@RequestMapping(value="/api/permission/page/upd",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String permissionPageUpdJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
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
		String page_name = (String) m.get("page_name");
		String feather = (String) m.get("feather");
		String icon = (String) m.get("icon");
		String page_title = (String) m.get("page_title");
		String parent = (String) m.get("parent");
		String desc = (String) m.get("desc");
		String enable = (String) m.get("enable");
		
		pp = new PermissionParameter();
		pp.setId(id);
		pp.setPage_id(page_id);
		pp.setPage_name(page_name);
		pp.setFeather(feather);
		pp.setIcon(icon);
		pp.setPage_title(page_title);
		pp.setParent(parent);
		pp.setDesc(desc);
		pp.setEnable(enable);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		try {
			session.getMapper(PermissionMapper.class).updPage(pp);
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
