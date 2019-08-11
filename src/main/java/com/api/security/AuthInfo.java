package com.api.security;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Claims;

public class AuthInfo {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public String username = "";
	public String uid = "";
	public String token = "";
	public String audience = "";
	public List role = new ArrayList();
	public String url = "";
	public String pagename = "";
	public String pageurlflg = "0";
	public String passToken = "n";
	public String passTokenStore = "n";
	public String passUserExists = "n";
	public String passPermission = "n";
	
	public AuthInfo(String token) {
		this.token = token;
		Claims claims = JWTToken.getClaims(token);
		this.username = claims.getIssuer();
		this.uid = claims.getId();
	}
	
	public String verify(String token) {
		this.token = token;
		Claims claims = JWTToken.getClaims(token);
		String starttime = sdf.format(claims.getExpiration()).toString();
		String endtime = sdf.format(new Date()).toString();
		try {
			if(sdf.parse(endtime).getTime()<=sdf.parse(starttime).getTime()) {
				passToken = "y";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return passToken;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public List getRole() {
		return role;
	}

	public void setRole(List role) {
		this.role = role;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	public String getPassToken() {
		return passToken;
	}

	public void setPassToken(String passToken) {
		this.passToken = passToken;
	}

	public String getPageurlflg() {
		return pageurlflg;
	}

	public void setPageurlflg(String pageurlflg) {
		this.pageurlflg = pageurlflg;
	}

	public String getPassTokenStore() {
		return passTokenStore;
	}

	public void setPassTokenStore(String passTokenStore) {
		this.passTokenStore = passTokenStore;
	}

	public String getPassUserExists() {
		return passUserExists;
	}

	public void setPassUserExists(String passUserExists) {
		this.passUserExists = passUserExists;
	}

	public String getPassPermission() {
		return passPermission;
	}

	public void setPassPermission(String passPermission) {
		this.passPermission = passPermission;
	}

	
}
