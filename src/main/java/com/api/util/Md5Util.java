package com.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    private static final String SALT = "lamb";
    public String encode(String password){
        password = password + SALT;
        StringBuffer stringBuffer = new StringBuffer();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] strByte = password.getBytes();
        byte[] result = digest.digest(strByte);
        System.out.println();
        for (byte aByte : result) {
            String s=Integer.toHexString(0xff & aByte);
            if(s.length()==1){
                stringBuffer.append(s);
            }else{
                stringBuffer.append(s);
            }
        }
        return stringBuffer.toString();
    }
}

