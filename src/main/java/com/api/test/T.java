package com.api.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.api.bean.PermissionBean;
import com.api.conf.Configuration;
import com.api.conf.DefaultConf;
import com.api.conf.VarConstants;
import com.api.mapper.MonMapper;
import com.api.mapper.PermissionMapper;
import com.api.parameter.MonParameter;
import com.api.parameter.PermissionParameter;
import com.api.security.IceKey;
import com.api.security.JWTToken;
import com.api.util.DatasourceMybatisSqlSession;

import io.jsonwebtoken.Claims;

public class T {

	public static void main(String[] args) {
		T t = new T();
		t.testCreatePassword();
//		t.testCreateToken();
		String tt= "MjSqMBjLYgnF6zhmgNSqdJaswOwsDoKbmLuQ5wkd7l1N2O34Nb38nCx8U9XDJMs91NNdPXrGgm5SzdjUhCcN4TzE25gvvYkywovgi5804U7+TyChq8k+v3jhaU2kXaXlVxucGCHHYqo7wUWa+ggbgDnac/6r1tSYPMBy9U7CoJzOLiSOoUEX2HRpjD0e+b5dEJq8rahTEbZayqavxqHrA04zPdERfRhmzQGsFZZw4v29QG8aFb94rTWviUI9xUTmbi4dHC5ZWxScb6nTjLrG2eL0GzBoIOErlJi8GpitjioPIAQCb+l0zJmWHdq2K3wPJlVhbjw8ptUtBsCiIsW3VvK4UbSz0QsOHYlcdJKvEUk=";
//		System.out.println(Aes.aesDecrypt(tt));
//		String url ="http://127.0.0.1:8080/adm/vue-mon.html?token=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDAwMCIsInN1YiI6ImFkbWluIiwiaWF0IjoxNTY1MTY4NTQyLCJpc3MiOiJhZG1pbiIsImF1ZCI6ImFkbWluIiwiZXhwIjoxNTY1MTcyMTQyfQ.-s1wzXfPvYsve121_H3EplhDIDMANflUqVXEKq7kfBQ&username=admin&uid=10000";
//		String[] str = url.split("\\?");
//		System.out.println(str[0]);
//		String crypto = "admin";
//		String t_username = Aes.aesEncrypt(crypto);
//		System.out.println(t_username);
//		System.out.println(System.getProperty("user.dir"));
//		File directory = new File("");//设定为当前文件夹 
//		try{ 
//		    System.out.println(directory.getCanonicalPath());//获取标准的路径 
//		    System.out.println(directory.getAbsolutePath());//获取绝对路径 
//		}catch(Exception e){
//			
//		} 
//		Configuration conf = new Configuration();
//		conf.initialize(new File("/WEB-INF/classes/adm-site.xml"));
//		DefaultConf.initialize(conf);
//		System.out.println(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE));

		System.out.println(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME));
	}

	public void testCreateToken(){
        String userId = "10001";
        String issuer = "qry";
        String subject = "qry";
        long ttlMillis = 1000 * 60 * 60 *24 *1000;
        String audience = "qry";
        String token = JWTToken.createJWT(userId,issuer,subject,ttlMillis,audience);
        //锟斤拷印锟斤拷token锟斤拷息
        System.out.println(token);
 
 
        Claims claims = JWTToken.getClaims("lfxRE8rayQn6APlTL89v3FIKqp8a5pT7/EY05UrAJBRgJ7HQUMydyWEDphy0y0y9AavUHx8qgIPzk0A08bAoM6p3zi6J1W++ZL/7gUX7ZvBTqAdswO6epb3zz+JDN5svCKNMXfifYBqcdnUPB3npYqSiUbXqqM5z+/Sj8Z+cT3St9TS+v7JDIOXXjWx9n2Wi94TN9BUj0W8NPGdojjiPWZM7VLZrhfgis9OVYu21lTlYJ4+7OHLLRwinFMInb3KP");
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String starttime = sdf.format(claims.getExpiration()).toString();
		String endtime = sdf.format(new Date()).toString();
		try {
			System.out.println(sdf.parse(endtime).getTime()+"->"+sdf.parse(starttime).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
 
    }
	
	public void testUser() {
		PermissionParameter pp = new PermissionParameter();
		pp.setUser_name("admin");
		pp.setPassword("admin");
        SqlSession session = new DatasourceMybatisSqlSession("ds-meta-hsqldb.xml").getSqlSession();
		List list = null;
		try {
			list = session.getMapper(PermissionMapper.class).getUser(pp);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		PermissionBean pb = (PermissionBean) list.get(0);
		System.out.println(pb.getCreate_time());
	}
	
	public void testCreatePassword() {
		IceKey ik = new IceKey(0);
		System.out.println(ik.encode("admin", "ice123"));
	}
}
