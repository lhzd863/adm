package com.api.mapper;

import java.util.List;

import com.api.parameter.ConfParameter;

public interface ConfMapper {

	public List getList(ConfParameter cp);
	public void addAll(ConfParameter cp);
	public void delAll(ConfParameter cp);
	public void updAll(ConfParameter cp);
	
}
