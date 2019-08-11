package com.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
 
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.shiro.authc.AuthenticationToken;

import java.security.Key;
import java.util.Date;
 
public class JWTToken {
    private static final String APP_KEY = "admin";
    
    public JWTToken(String token) {
    	
    }
    
    public static String createJWT(String id,String issuer){
    	long ttlMillis = 1000 * 60 * 60 * 24 * 7;
    	String subject = issuer;
    	String audience = issuer;
    	return createJWT(id,issuer,subject, ttlMillis, audience);
    }

    public static String createJWT(String id,String issuer,String subject,long ttlMillis, String audience){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(APP_KEY);
//        System.out.println(apiKeySecretBytes);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm,signingKey);
        if(ttlMillis >=0){  
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp);
        }
        return jwtBuilder.compact();
 
    }
    
    public static Claims getClaims(String jwt) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(APP_KEY))
                .parseClaimsJws(jwt)
                .getBody();
    }
}