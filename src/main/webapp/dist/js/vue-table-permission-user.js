new Vue({
        el: '#my-main-contriner',
        data: function() {
            return {
                tableData: [],
				multipleSort:true,
                columns: [
				    {width: 50, titleAlign: 'center',columnAlign:'center',type: 'selection'},
                    {field: 'id', title:'Id', titleAlign: 'center',columnAlign:'center',width: 50},
                    {field: 'user_name', title: 'UserName', titleAlign: 'center',columnAlign:'center',width: 150,isEdit:true},
                    {field: 'password', title: 'Password', titleAlign: 'center',columnAlign:'center',width: 150,isEdit:true},
                    {field: 'show_name', title: 'ShowName', titleAlign: 'center',columnAlign:'center',width: 150,isEdit:true},
                    {field: 'desc', title: 'Desc', titleAlign: 'center',columnAlign:'center',width: 200,isEdit:true},
                    {field: 'create_time', title: 'Create_Time', titleAlign: 'center',columnAlign:'center',width: 150,isEdit:true},
                    {field: 'update_time', title: 'Update_Time', titleAlign: 'center',columnAlign:'center',width: 150,isEdit:true},
                    {field: 'enable', title: 'Enable', titleAlign: 'center',columnAlign:'center',width: 50,isEdit:true}
                ],
				pageIndex: 1,
				pageSize: 10,
				total: 0,
				id: '',
				user_name: '',
				password: '',
				show_name: '',
				desc: '',
				create_time: '',
				update_time: '',
				enable:'',
				changeflag: '',
				selectObj: [],
				editObj: [],
				pageName: "vue-permission-user.html",
				headerInfo: []
            }
        },
        created: function(){
        	VueCommonDB.init(this.pageName);
        	this.headerInfo = JSON.parse(localStorage.getItem("cmts") || '[]');
            this.getTableData();	  
        },
        methods:{
          getSourceData:function(){
        	  var self = this;
        	  fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/user/get",{
                  method: "POST",
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
                    uid: this.headerInfo[0].uid
                  })
                 })
                 .then(response => response.json())
                 .then((data) => {
                	  self.tableData=data.slice((this.pageIndex-1)*this.pageSize,(this.pageIndex)*this.pageSize);
					  self.total=Object.keys(data).length;
                      //console.log(self.tableData);
                 })
                 .catch(function (err) {
                      console.log(err);
                 });
          },
		  getTableData:function(){
			  this.getSourceData();
			  var self = this;
			  self.tableData = self.tableData.slice((this.pageIndex-1)*this.pageSize,(this.pageIndex)*this.pageSize);  
		  },
          handleEdit(index, row) {
        	  console.log(index, row.name);
          },
          handleDelete(index, row) {
        	  console.log(index, row);
          },
		  selectALL(selection){
			  this.selectObj=[];
			  this.selectObj = this.selectObj.concat(selection);
          },
          selectChange(selection,rowData){
			  this.selectObj=[];
			  this.selectObj = this.selectObj.concat(selection);
          },
          selectGroupChange(selection){
			  this.selectObj=[];
			  this.selectObj = this.selectObj.concat(selection);
          },
		  pageChange1(pageIndex){
              //console.log(pageIndex)
			  this.pageIndex = pageIndex;
			  this.getTableData();
          },
          pageSizeChange1(pageSize){
              //console.log(pageSize)
			  this.pageIndex = 1;
			  this.pageSize = pageSize;
			  this.getTableData();
          },
		  cellEditDone(newValue,oldValue,rowIndex,rowData,field){
			  var obj = this.tableData[rowIndex];
			  obj[field] = newValue;
			  var flg=0;
			  for (var i=0;i<this.editObj.length;i++) {
					if(this.editObj[i].id==rowData.id){
						this.editObj[i][field] = newValue;
						flg=1;
					}
			  }
			  if(flg!=1){
				this.editObj = this.editObj.concat(obj); 
			  }
			  
              //this.tableData[rowIndex][field] = newValue;
			  //console.log(rowIndex);
          },
		  addData(){
			//console.log(this.key+"->"+this.key+"->"+this.val+"->"+this.remark+"->"+this.enable);
			var self = this;
        	fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/user/add",{
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8',
  					    'token': this.headerInfo[0].accesstoken,
  				        'username': this.headerInfo[0].username,
  				        'uid': this.headerInfo[0].uid
                    },
                    body: JSON.stringify({
                        appid: 'a00002',
                        accesstoken: this.headerInfo[0].accesstoken,
                        id: self.id,
				    	user_name: self.user_name,
				    	show_name: self.show_name,
						password: self.password,
				    	desc: self.desc,
				    	create_time: self.create_time,
				    	update_time: self.update_time,
				    	enable: self.enable,
						token: this.headerInfo[0].accesstoken,
                        username: this.headerInfo[0].username,
                        uid: this.headerInfo[0].uid
                    })
                })
                .then(response => response.json())
                .then((data) => {
                    console.log("");
                })
                .catch(function (err) {
                    console.log(err);
                });
				alert("add succ");
				location.href="vue-permission-user.html";
		  },
		  modifyData(){
			for (var i=0;i<this.editObj.length;i++) {
				var self = this;
        	    fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/user/upd",{
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8',
  					    'token': this.headerInfo[0].accesstoken,
  				        'username': this.headerInfo[0].username,
  				        'uid': this.headerInfo[0].uid
                    },
                    body: JSON.stringify({
                        appid: 'a00002',
                        accesstoken: this.headerInfo[0].accesstoken,
                        id: this.editObj[i].id,
                        user_name: this.editObj[i].user_name,
                        show_name: this.editObj[i].show_name,
                        password: this.editObj[i].password,
                        desc: this.editObj[i].desc,
				    	create_time: this.editObj[i].create_time,
				    	update_time: this.editObj[i].update_time,
				    	enable: this.editObj[i].enable,
						token: this.headerInfo[0].accesstoken,
                        username: this.headerInfo[0].username,
                        uid: this.headerInfo[0].uid
                    })
                })
                .then(response => response.json())
                .then((data) => {
					//this.editObj = [];
                })
                .catch(function (err) {
                    console.log(err);
                });
				alert("update "+this.editObj[i].id+" succ");
		    }
			//alert("update succ");
			location.href="vue-permission-user.html";
		  },
		  deleteData(){
			for (var i=0;i<this.selectObj.length;i++) {
        	    fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/user/del",{
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8',
  					    'token': this.headerInfo[0].accesstoken,
  				        'username': this.headerInfo[0].username,
  				        'uid': this.headerInfo[0].uid
                    },
                    body: JSON.stringify({
                        appid: 'a00002',
                        accesstoken: this.headerInfo[0].accesstoken,
                        id: this.selectObj[i].id,
                        user_name: this.selectObj[i].user_name,
                        show_name: this.selectObj[i].show_name,
                        password: this.selectObj[i].password,
                        desc: this.selectObj[i].desc,
						create_time: this.selectObj[i].create_time,
						update_time: this.selectObj[i].update_time,
				    	enable: this.selectObj[i].enable,
						token: this.headerInfo[0].accesstoken,
                        username: this.headerInfo[0].username,
                        uid: this.headerInfo[0].uid
                    })
                })
                .then(response => response.json())
                .then((data) => {
                    console.log("");
                })
                .catch(function (err) {
                    console.log(err);
                });
				alert("delete "+this.selectObj[i].id+" succ");
		    }
			alert("delete succ");
			location.href="vue-permission-user.html";
			  //console.log(this.selectObj[0].key);
			  //console.log("delete");
		  }
        }
})