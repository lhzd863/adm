package com.api.mapper;

import java.util.List;

import com.api.parameter.MonParameter;
import com.api.parameter.PermissionParameter;

public interface PermissionMapper {

	
	public List checkPassword(PermissionParameter pp);
	
	public List getUser(PermissionParameter pp);
	public void addUser(PermissionParameter pp);
	public void updUser(PermissionParameter pp);
	public void delUser(PermissionParameter pp);
	
	public List getRole(PermissionParameter pp);
	public void addRole(PermissionParameter pp);
	public void updRole(PermissionParameter pp);
	public void delRole(PermissionParameter pp);
	
	public List getToken(PermissionParameter pp);
	public void addToken(PermissionParameter pp);
	public void delToken(PermissionParameter pp);
	
	public List getUrl(PermissionParameter pp);
	public void addUrl(PermissionParameter pp);
	public void updUrl(PermissionParameter pp);
	public void delUrl(PermissionParameter pp);
	
	public List getUserRole(PermissionParameter pp);
	public void addUserRole(PermissionParameter pp);
	public void updUserRole(PermissionParameter pp);
	public void delUserRole(PermissionParameter pp);
	
	public List getPage(PermissionParameter pp);
	public void addPage(PermissionParameter pp);
	public void updPage(PermissionParameter pp);
	public void delPage(PermissionParameter pp);
	
	public List getUserPage(PermissionParameter pp);
	public void addUserPage(PermissionParameter pp);
	public void updUserPage(PermissionParameter pp);
	public void delUserPage(PermissionParameter pp);
	
	public List getRight(PermissionParameter pp);
	public void addRight(PermissionParameter pp);
	public void updRight(PermissionParameter pp);
	public void delRight(PermissionParameter pp);
	
	public List getUserUrl(PermissionParameter pp);
	public void addUserUrl(PermissionParameter pp);
	public void updUserUrl(PermissionParameter pp);
	public void delUserUrl(PermissionParameter pp);
	
}
