package com.api.security;

import java.io.File;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.bean.PermissionBean;
import com.api.conf.Configuration;
import com.api.conf.DefaultConf;
import com.api.conf.VarConstants;
import com.api.filter.AuthInterceptor;
import com.api.mapper.PermissionMapper;
import com.api.parameter.PermissionParameter;
import com.api.util.DatasourceMybatisSqlSession;

public class Realm {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	Configuration conf = null;
	public AuthInfo doGetAuthorizationInfo(AuthInfo ati) {
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
        String userName = ati.getUsername();
        
        SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
        //user exists
        List listUser = null;
		try {
			PermissionParameter pp = new PermissionParameter();
			pp.setUser_name(userName);
			listUser = session.getMapper(PermissionMapper.class).getUser(pp);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			return ati;
		} finally {
			session.commit();
		}
		if(listUser.size()>0) {
			ati.setPassUserExists("y");
		}
		//token exp
		ati.setPassToken(ati.verify(ati.getToken()));
		//store token
		List tokenlist = null;
		try {
			PermissionParameter pp = new PermissionParameter();
			pp.setToken(ati.getToken());
			tokenlist = session.getMapper(PermissionMapper.class).getToken(pp);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			return ati;
		} finally{
			session.commit();
		}
		if(tokenlist.size()>0) {
			ati.setPassTokenStore("y");
		}
//		for(int i=0;i<tokenlist.size();i++) {
//			PermissionBean pb = (PermissionBean)tokenlist.get(i);
//			AuthInfo ati1 = new AuthInfo(pb.getToken());
//			ati1.setPassToken(ati1.verify(pb.getToken()));
//            if(ati1.getPassToken().equals("n")) {
//            	try {
//            		PermissionParameter pp1 = new PermissionParameter();
//            		pp1.setToken(pb.getToken());
//        			session.getMapper(PermissionMapper.class).delToken(pp1);
//        		} catch (Exception e) {
//        			session.rollback();
//        			e.printStackTrace();
//        			return ati;
//        		} finally{
//        			session.commit();
//        		}
//            }
//            if(pb.getToken().equals(ati.getToken())) {
//            	ati.setPassTokenStore("y");
//            	break;
//            }
//		}
		
		session.close();
        return ati;
    }
	
	public AuthInfo doGetAuthenticationInfo(AuthInfo ati) throws Exception {
		
		SqlSession session = new DatasourceMybatisSqlSession(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)).getSqlSession();
        List listPermission = null;
        String tmpUrl = ati.getUrl();
        if(ati.getPageurlflg().equals("1")) {
        	tmpUrl = ati.getPagename();
        }
        try {
        	PermissionParameter pp = new PermissionParameter();
        	pp.setUser_name(ati.getUsername());
        	listPermission = session.getMapper(PermissionMapper.class).getUrl(pp);
        } catch (Exception e) {
        	session.rollback();
        	e.printStackTrace();
        	return ati;
        } finally {
        	session.commit();
        }
        
        for(int i=0;i<listPermission.size();i++) {
        	PermissionBean pb = (PermissionBean) listPermission.get(i);
        	//logger.debug(tmpUrl+"->"+pb.getUrl());
        	if(tmpUrl.endsWith(pb.getUrl())) {
        		ati.setPassPermission("y");
        		break;
        	}
        }
        
		session.close();
        return ati;
    }
}
