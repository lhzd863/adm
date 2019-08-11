new Vue({
        el: '#my-main-contriner',
        data: function() {
            return {
            	fileList: [],
				props: {
                  label: 'name',
                  children: 'zones'
                },
                count: 1,
				selectFileData: [],
				fileNameData: [],
				pageName: "vue-file-upload.html",
				headerInfo: [],
				headerslst: {token:JSON.parse(localStorage.getItem("cmts") || '[]')[0].accesstoken,
				          username:JSON.parse(localStorage.getItem("cmts") || '[]')[0].username,
						  uid:JSON.parse(localStorage.getItem("cmts") || '[]')[0].uid}
            }
        },
        created: function(){
        	VueCommonDB.init(this.pageName);
        	this.headerInfo = JSON.parse(localStorage.getItem("cmts") || '[]');
        	this.lsFile();
        },
        methods:{
			submitUpload() {
              this.$refs.upload.submit();
            },
            handleRemove(file, fileList) {
              console.log(file, fileList);
            },
            handlePreview(file) {
              console.log(file);
            },
			handleSuccess(res, file){
				this.$message({
                    type: 'info',
                    message: 'upload succ',
                    duration: 6000
                });
				location.href="vue-file-upload.html";
			},
            handleDownload(){
				for(var i=0;i<this.selectFileData.length;i++){
					var form = $("<form>");
				    form.attr("style","display:none");
				    form.attr("target","");
				    form.attr("method","post");
				    form.attr("action",VAR_GLOBAL_WEBSITE_PREFIX+"/api/file/download?fileName="+this.selectFileData[i]+"&token="+this.headerInfo[0].accesstoken+"&username="+this.headerInfo[0].username+"&uid="+this.headerInfo[0].uid);
				    $("body").append(form);
				    form.submit();
				    form.remove();
				}
				this.selectFileData=[];
			},
			lsFile:function(){
        		var self = this;
          	    fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/file/ls",{
                    method: "POST",
                    mode:'cors',
                    headers: {
                    	'Content-Type': 'application/json;charset=UTF-8',
    					'token': this.headerInfo[0].accesstoken,
    					'username': this.headerInfo[0].username,
    					'uid': this.headerInfo[0].uid
                    },
                    body: JSON.stringify({
                      appid: 'a00001',
                      token: this.headerInfo[0].accesstoken,
                      username: this.headerInfo[0].username,
                      uid: this.headerInfo[0].uid,
					  dir: ""
                    })
                   })
                   .then(response => response.json())
                   .then((data) => {
						//this.fileNameData = data;
						for(var i=0;i<data.length;i++){
							let objTemp = {
								name: data[i].fileName,
							}
							this.fileNameData.push(objTemp);
						}
                   })
                   .catch(function (err) {
                        console.log(err);
                   }); 
        	},
			handleCheckChange(data, checked, indeterminate) {
			  this.selectFileData=[];
			  this.selectFileData.push(data.name);
              console.log(data, checked, indeterminate);
            },
            handleNodeClick(data) {
              console.log(data);
            },
			loadNode(node, resolve) {
              if (node.level === 0) {
                return resolve(this.fileNameData);
              }
              if (node.level > 2) return resolve([]);
	      
              var hasChild;
              if (node.data.name === 'region1') {
                hasChild = false;
              } else if (node.data.name === 'region2') {
                hasChild = false;
              } else {
                hasChild = false;
              }
	      
              setTimeout(() => {
                var data;
                if (hasChild) {
                  data = [{
                    name: 'zone' + this.count++
                  }, {
                    name: 'zone' + this.count++
                  }];
                } else {
                  data = [];
                }
	      
                resolve(data);
              }, 500);
            }
        }
})