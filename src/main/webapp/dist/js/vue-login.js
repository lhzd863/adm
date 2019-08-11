new Vue({
        el: '#my-main-contriner',
        data: function() {
            return {
            	currentDate: new Date(),
				sidebarList: VAR_SIDEBAR_LIST,
				pageName: "vue-card-index.html",
				username : '',
			    userpassword : ''
            }
        },
        created: function(){
        },
        methods:{
			logincheck : function(event){
				//获取值
				var name = this.username;
				var password = this.userpassword;
				if(name == '' || password == ''){
					this.$message({
						message : '账号或密码为空！',
						type : 'error'
					})
					return;
				}
				//alert(this.username);
				fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/login?username="+this.username+"&userpassword="+this.userpassword,{
                    method: "POST",
                    mode:'cors'
                   })
                   .then(function(response) {
				        return response.json();
				   })
                   .then((data) => {
                	    var ttoken=data[0].accesstoken;
               	        if(ttoken == "xxx"){
               	    	    this.$message.error(data[0].msg);
							sleep(1000);
   					        location.href="vue-login.html";
               	        }else{
							//this.fileNameData = data;
               	           window.localStorage.removeItem("cmts");
						   var comment = {accesstoken: data[0].accesstoken, username: this.username, uid: data[0].uid}
						   var list = JSON.parse(localStorage.getItem("cmts") || '[]')
                           list.unshift(comment)
                           localStorage.setItem('cmts',JSON.stringify(list))
						   this.$message.success(this.username+"登录成功");
						   location.href="vue-mon.html?token="+data[0].accesstoken+"&username="+this.username+"&uid="+data[0].uid;
				        }
                   })
                   .catch(function (err) {
                        console.log(err);
                   });
			}
			
		}
})