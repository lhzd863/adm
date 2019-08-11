var VueCommonDB = function () {
	var sidebarList = VAR_SIDEBAR_LIST;

	function createdbli(url){
		document.getElementById("logonid").innerText = VAR_GLOBAL_WEBSITE_HEADER_LOGON;
		document.getElementById("loginid").innerText = JSON.parse(localStorage.getItem("cmts") || '[]')[0].username;
		fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/page/get?token="+JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken,{
                method: "POST",
                mode:'cors',
				headers: {
                    'Content-Type': 'application/json;charset=UTF-8',
				    'token': JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken,
				    'username': JSON.parse(localStorage.getItem("cmts") || '[]')[0].username,
				    'uid': JSON.parse(localStorage.getItem("cmts") || '[]')[0].uid
                },
                body: JSON.stringify({
                    appid: 'a00001',
                    token: JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken,
                    username: JSON.parse(localStorage.getItem("cmts") || '[]')[0].username,
                    uid: JSON.parse(localStorage.getItem("cmts") || '[]')[0].uid
                })
            })
            .then(function(response) {
			    return response.json();
			})
            .then((data) => {
				var str_sidebar='<ul class=\"nav flex-column\">';
				var str_title='tmp';
                for (var i=0;i<data.length;i++) {
                    var activestr='';
			        var activespan='';
			        if(data[i].page_name==url){
			        	activestr='active';
			        	activespan="<span class=\"sr-only\">(current)</span>";
			        	str_title=data[i].page_title;
			        }
			        str_sidebar += "<li class=\"nav-item\">"+
			                            "<a class=\"nav-link "+activestr+"\" href=\""+data[i].page_name+"\">"+
			        				        "<i class=\""+data[i].icon+"\"></i>"+
			                                data[i].desc+activespan+
			        					"</a>"+
			        				"</li>";
							
                }
                document.getElementsByTagName("title")[0].innerText = str_title;
		        str_sidebar +="</ul>";
		        $("#sidebarid").append(str_sidebar);				
            })
            .catch(function (err) {
                   console.log(err);
            });
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
			createdbli(url_name);
        }
        
        
    };
}();