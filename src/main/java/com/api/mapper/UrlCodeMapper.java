package com.api.mapper;

import java.util.List;

import com.api.parameter.UrlCodeParameter;

public interface UrlCodeMapper {

	public List getUrl(UrlCodeParameter up);
	public List getPasswd(UrlCodeParameter up);
}
