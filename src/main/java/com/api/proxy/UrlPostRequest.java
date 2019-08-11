package com.api.proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class UrlPostRequest {

	public static String doPost(String url) throws Exception {
		StringBuffer json = new StringBuffer();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                                        yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(new String(inputLine.getBytes(),"utf-8"));
            }
            in.close();
        } catch (Exception e)  {
            e.printStackTrace();
        }
        return json.toString();
	}
}
