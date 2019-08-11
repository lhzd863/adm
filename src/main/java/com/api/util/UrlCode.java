package com.api.util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.api.bean.UrlCodeBean;
import com.api.mapper.UrlCodeMapper;
import com.api.parameter.UrlCodeParameter;


public class UrlCode {

	public UrlCodeParameter ur ;
    public List ret() {
		SqlSession session = new DatasourceMybatisSqlSession("ds-meta.xml").getSqlSession();
		List datalst = null;
		try {
			datalst = session.getMapper(UrlCodeMapper.class).getUrl(ur);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return datalst;
	}
    
    public String getUrl(String urlcode) {
    	String ret = "";
		try {
			ur = new UrlCodeParameter();
			ur.setUrlcode(urlcode);
			List list = ret();
			UrlCodeBean ucb = (UrlCodeBean)list.get(0);
			ret = ucb.getUrl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
