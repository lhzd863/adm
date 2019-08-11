package com.api.mapper;

import java.util.List;

import com.api.parameter.DataParameter;

public interface DataMapper {

	public List getList(DataParameter dp);
	public void addAll(DataParameter dp);
	public void delAll(DataParameter dp);
	public void updAll(DataParameter dp);
	
}
