var VueCommonAjax = function () {
	var sidebarList = VAR_SIDEBAR_LIST;

	function createdbli(url){
		//alert(JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken);
		$(function(){$.ajax({
            type: "POST",
            url: VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/page/get?token="+JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken,
            data: JSON.stringify({
                appid: 'a00001',
                token: JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken,
                username: JSON.parse(localStorage.getItem("cmts") || '[]')[0].username,
                uid: JSON.parse(localStorage.getItem("cmts") || '[]')[0].uid
            }),
            dataType: "json",
            contentType:'application/json;charset=utf-8',
            headers:{
				'Content-Type': 'application/json;charset=UTF-8',
				'token': JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken,
				'username': JSON.parse(localStorage.getItem("cmts") || '[]')[0].username,
				'uid': JSON.parse(localStorage.getItem("cmts") || '[]')[0].uid
			},
            async: true,
            success: function(data){
                var str_sidebar='<ul class=\"nav flex-column\">';
				var str_title='tmp';
                for (var i=0;i<data.length;i++) {
                    var activestr='';
			        var activespan='';
			        if(data[i].page_name==url){
			        	activestr='active';
			        	activespan="<span class=\"sr-only\">(current)</span>";
			        	str_title=sidebarList[i].titlename;
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
            },
	        error:function(XMLHttpRequest, textStatus, errorThrown){
		        // 状态码
	            console.log(XMLHttpRequest.status);
	            // 状态
	            console.log(XMLHttpRequest.readyState);
	            // 错误信息   
	            console.log(textStatus);
	        }
        });});
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