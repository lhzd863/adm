package adm;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.api.mapper.MonMapper;
import com.api.parameter.MonParameter;
import com.api.security.JWTToken;
import com.api.util.DatasourceMybatisSqlSession;

import io.jsonwebtoken.Claims;

public class T {

	public static void main(String[] args) {
		T t= new T();
		t.testCreateToken();

	}
	
	@Test
	public void testMyibtais() {
		 MonParameter mpara ;
		 mpara = new MonParameter();
		SqlSession session = new DatasourceMybatisSqlSession("ds-meta-hsqldb.xml").getSqlSession();
		List list = null;
		try {
			list = session.getMapper(MonMapper.class).getList(mpara);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println(list.size());
	}
	//@Test
	public void testCreateToken(){
        String userId = "WKSH121321KKsdfk";
        String issuer = "http://whytfjybj.com";
        String subject = "ʦ��ѧԺ";
        long ttlMillis = 1000 * 60;
        String audience = "schoolNo";
        String token = JWTToken.createJWT(userId,issuer,subject,ttlMillis,audience);
        //��ӡ��token��Ϣ
        System.out.println(token);
 
 
//        ����token��Ϣ
        Claims claims = JWTToken.getClaims(token);
        System.out.println("---------------------------���ܵ�token��Ϣ----------------------------------");
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
 
    }

}
