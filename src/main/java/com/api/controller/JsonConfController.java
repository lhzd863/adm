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
import com.api.parameter.ConfParameter;
import com.api.util.DatasourceMybatisSqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;

@Controller
public class JsonConfController {
	public ConfParameter cpara ;
	Configuration conf = null;
	@Authentication(validate=true)
	@RequestMapping(value="/api/conf/get",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String confGetJSON(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
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

		String id = (String) m.get("apid");
		cpara = new ConfParameter();
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		List list = null;
		try {
			list = session.getMapper(ConfMapper.class).getList(cpara);
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
	@RequestMapping(value="/api/conf/add",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String confAddJSON(@RequestBody String jsonData) {
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

		String id = (String) m.get("apid");
		String sys = (String) m.get("sys");
		String key = (String) m.get("key");
		String val = (String) m.get("val");
		String remark = (String) m.get("remark");
		String enable = (String) m.get("enable");
		
		cpara = new ConfParameter();
		cpara.setSys(sys);
		cpara.setKey(key);
		cpara.setVal(val);
		cpara.setRemark(remark);
		cpara.setEnable(enable);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		List list = null;
		try {
			session.getMapper(ConfMapper.class).addAll(cpara);
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
	@RequestMapping(value="/api/conf/del",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String confDelJSON(@RequestBody String jsonData) {
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

		String id = (String) m.get("apid");
		String sys = (String) m.get("sys");
		String key = (String) m.get("key");
		String val = (String) m.get("val");
		String remark = (String) m.get("remark");
		String enable = (String) m.get("enable");
		
		cpara = new ConfParameter();
		cpara.setSys(sys);
		cpara.setKey(key);
		cpara.setVal(val);
		cpara.setRemark(remark);
		cpara.setEnable(enable);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		List list = null;
		try {
			session.getMapper(ConfMapper.class).delAll(cpara);
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
	@RequestMapping(value="/api/conf/upd",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String confUpdJSON(@RequestBody String jsonData) {
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

		String id = (String) m.get("apid");
		String sys = (String) m.get("sys");
		String key = (String) m.get("key");
		String val = (String) m.get("val");
		String remark = (String) m.get("remark");
		String enable = (String) m.get("enable");
		
		cpara = new ConfParameter();
		cpara.setSys(sys);
		cpara.setKey(key);
		cpara.setVal(val);
		cpara.setRemark(remark);
		cpara.setEnable(enable);
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
		List list = null;
		try {
			session.getMapper(ConfMapper.class).updAll(cpara);
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
