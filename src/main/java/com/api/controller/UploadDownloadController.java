package com.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.bean.FileAttBean;
import com.api.conf.Configuration;
import com.api.conf.DefaultConf;
import com.api.conf.VarConstants;
import com.api.filter.Authentication;
import com.api.mapper.MonMapper;
import com.api.parameter.MonParameter;
import com.api.util.DatasourceMybatisSqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;

@RestController
public class UploadDownloadController {
	
	String fileRootDir = "/home/k8s/tomcat/tmp/";
//	String fileRootDir = "D:/download/20190725/";
	Configuration conf = null;
	@Authentication(validate=true)
	@RequestMapping(value="/api/file/upload",method=RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile[] file, HttpServletRequest request, HttpServletResponse resp) throws IOException{
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
		fileRootDir = conf.get(VarConstants.VAR_FILE_ROOT_DIR);
		for (MultipartFile f : file) {
			if (f.isEmpty()) {
				System.out.println("no file upload.");
			} else {
				String fileName = f.getOriginalFilename();
				//String path1 = request.getSession().getServletContext().getRealPath("image") + File.separator;
				//String path = path1 + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
				//System.out.println(path);
				File localFile = new File(fileRootDir+fileName);
				f.transferTo(localFile);
			}
		}
		
		return "[{\"dir\":\""+fileRootDir+"\"}]";
	}
	
	@Authentication(validate=true)
	@RequestMapping("/api/file/download")
	public String download(String filePath,String fileName, HttpServletRequest request, HttpServletResponse response) {
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
		fileRootDir = conf.get(VarConstants.VAR_FILE_ROOT_DIR);
		if(filePath==null) {
			filePath="";
		}else {
			filePath+="/";
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try {
			InputStream inputStream = new FileInputStream(new File(fileRootDir+filePath + fileName));
 
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.close();
 
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "[{\"dir\":\""+fileRootDir+"\"}]";
	}
	
	@Authentication(validate=true)
	@RequestMapping(value="/api/file/ls",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String listLs(@RequestBody String jsonData,HttpServletRequest req, HttpServletResponse resp) {
		conf = new Configuration();
		conf.initialize(new File(System.getenv(VarConstants.VAR_SYSTEM_ENV_PATH_CONF_NAME)));
		DefaultConf.initialize(conf);
		fileRootDir = conf.get(VarConstants.VAR_FILE_ROOT_DIR);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ObjectMapper mapper = new ObjectMapper();
		Map m=null;
		try {
			m = mapper.readValue(jsonData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String id = (String) m.get("apid");
		String dir = (String) m.get("dir");
		if(dir==null||dir.trim().length()==0) {
			dir="";
		}else {
			dir += "/";
		}
        File[] list = new File(fileRootDir+dir).listFiles();
        List lst = new ArrayList();
        for(File file:list) {
        	FileAttBean fab = new FileAttBean();
        	if(file.isFile()) {
        		fab.setFiledir("f");
        	}else {
        		fab.setFiledir("d");
        	}
        	fab.setFileName(file.getName());
        	fab.setModifyTime(sdf.format(file.lastModified()*1000));
        	fab.setFileSize(file.length()+"");
        	lst.add(fab);
        }
        JSONArray jsonarray = JSONArray.fromObject(lst);
		return jsonarray.toString();
	}
	
}
