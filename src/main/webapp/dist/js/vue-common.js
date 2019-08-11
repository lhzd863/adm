var VueCommonFun = function () {
	var sidebarList = VAR_SIDEBAR_LIST;

	function createli(url){
		document.getElementById("logonid").innerText = VAR_GLOBAL_WEBSITE_HEADER_LOGON;
		//document.getElementById("loginid").innerText = VAR_GLOBAL_WEBSITE_HEADER_LOGIN;
		document.getElementById("loginid").innerText = JSON.parse(localStorage.getItem("cmts") || '[]')[0].username;
		var str_sidebar='<ul class=\"nav flex-column\">';
		var str_title='tmp';
		for(var i=0;i<sidebarList.length;i++){
			var activestr='';
			var activespan='';
			if(sidebarList[i].islogin=='y'){
			    if(JSON.parse(localStorage.getItem("cmts") || '[]')[0].uid==VAR_GLOBAL_WEBSITE_DEFAULT_USER_ID){
			       	continue;
			    }
			}
			if(sidebarList[i].url==url){
				activestr='active';
				activespan="<span class=\"sr-only\">(current)</span>";
				str_title=sidebarList[i].titlename;
			}
			str_sidebar += "<li class=\"nav-item\">"+
			                    "<a class=\"nav-link "+activestr+"\" href=\""+sidebarList[i].url+"\">"+
							        "<i class=\""+sidebarList[i].icon+"\"></i>"+
			                        sidebarList[i].name+activespan+
								"</a>"+
							"</li>";
		}
		document.getElementsByTagName("title")[0].innerText = str_title;
		str_sidebar +="</ul>";
		$("#sidebarid").append(str_sidebar);
	}
	
	return {
        //main function to initiate the module
        init: function (url) {
			var url_name='';
			if(url.length>0){
				url_name = url;
			}else{
				var url_nm=window.location.href;
				var lst = url_nm.split("/");
				url_name = lst[lst.length-1];
			}
			if(JSON.parse(localStorage.getItem("cmts") || '[]')[0]==undefined||
			   JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken==undefined||
			   (JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken).length==0){
				var comment = {accesstoken: VAR_GLOBAL_ACCESS_TOKEN, username: VAR_GLOBAL_WEBSITE_DEFAULT_USER_NAME, uid: VAR_GLOBAL_WEBSITE_DEFAULT_USER_ID}
				var list = JSON.parse(localStorage.getItem("cmts") || '[]')
                list.unshift(comment)
                localStorage.setItem('cmts',JSON.stringify(list))
		    }
			for(var i=0;i<sidebarList.length;i++){
				if(sidebarList[i].url==url_name&&sidebarList[i].islogin=='y'&&JSON.parse(localStorage.getItem("cmts") || '[]')[0].uid==VAR_GLOBAL_WEBSITE_DEFAULT_USER_ID){
					location.href="vue-login.html";
				}else if(sidebarList[i].url==url_name&&sidebarList[i].islogin!='y') {
					break;
				}
			}
            createli(url_name);
        }
        
        
    };
}();