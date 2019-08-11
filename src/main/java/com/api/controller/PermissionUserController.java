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
public class PermissionUserController {
	public PermissionParameter pp ;
	Configuration conf = null;
	@Authentication(validate=true)
	@RequestMapping(value="/api/permission/user/get",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String userGetJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
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
			list = session.getMapper(PermissionMapper.class).getUser(pp);
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
	@RequestMapping(value="/api/permission/user/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String userAddJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
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
		String password = (String) m.get("password");
		String show_name = (String) m.get("show_name");
		String desc = (String) m.get("desc");
		
		IceKey ik = new IceKey(0);
		password=ik.encode(password, conf.get(VarConstants.VAR_ICE_KEY_PASSWORD));
		
		pp = new PermissionParameter();
		pp.setUser_name(user_name);
		pp.setPassword(password);
		pp.setShow_name(show_name);
		pp.setDesc(desc);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		try {
			session.getMapper(PermissionMapper.class).addUser(pp);
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
	@RequestMapping(value="/api/permission/user/del",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String userDelJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
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
			session.getMapper(PermissionMapper.class).delUser(pp);
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
	@RequestMapping(value="/api/permission/user/upd",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String userUpdJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
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
		String user_name = (String) m.get("user_name");
		String password = (String) m.get("password");
		String show_name = (String) m.get("show_name");
		String desc = (String) m.get("desc");
		String enable = (String) m.get("enable");
		
		IceKey ik = new IceKey(0);
		password=ik.encode(password, conf.get(VarConstants.VAR_ICE_KEY_PASSWORD));
		
		pp = new PermissionParameter();
		pp.setId(id);
		pp.setUser_name(user_name);
		pp.setPassword(password);
		pp.setShow_name(show_name);
		pp.setDesc(desc);
		pp.setEnable(enable);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		try {
			session.getMapper(PermissionMapper.class).updUser(pp);
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
