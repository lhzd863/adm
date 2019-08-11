package com.api.conf;

public class DefaultConf {

	public static void initialize(Configuration conf){
		if(conf.get(VarConstants.VAR_MYIBTAIS_DATASOURCE)==null){
			conf.set(VarConstants.VAR_MYIBTAIS_DATASOURCE,"ds-meta-hsqldb.xml");
		}
		
		if(conf.get(VarConstants.VAR_FILE_ROOT_DIR)==null){
			conf.set(VarConstants.VAR_FILE_ROOT_DIR,"/home/k8s/tomcat/tmp/");
		}
		
		if(conf.get(VarConstants.VAR_ICE_KEY_PASSWORD)==null){
			conf.set(VarConstants.VAR_ICE_KEY_PASSWORD,"abcd123");
		}
	}
}
