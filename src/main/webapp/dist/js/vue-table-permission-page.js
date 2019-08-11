new Vue({
        el: '#my-main-contriner',
        data: function() {
            return {
                tableData: [],
				multipleSort:true,
                columns: [
				    {width: 50, titleAlign: 'center',columnAlign:'center',type: 'selection'},
                    {field: 'id', title:'Id', titleAlign: 'center',columnAlign:'center',width: 50},
                    {field: 'page_id', title: 'Page_Id', titleAlign: 'center',columnAlign:'center',width: 50,isEdit:true},
                    {field: 'page_name', title: 'PageName', titleAlign: 'center',columnAlign:'center',width: 150,isEdit:true},
                    {field: 'feather', title: 'Feather', titleAlign: 'center',columnAlign:'center',width: 50,isEdit:true},
                    {field: 'icon', title: 'Icon', titleAlign: 'center',columnAlign:'center',width: 100,isEdit:true},
                    {field: 'page_title', title: 'PageTitle', titleAlign: 'center',columnAlign:'center',width: 100,isEdit:true},
                    {field: 'parent', title: 'Parent', titleAlign: 'center',columnAlign:'center',width: 50,isEdit:true},
                    {field: 'desc', title: 'Desc', titleAlign: 'center',columnAlign:'center',width: 150,isEdit:true},
                    {field: 'create_time', title: 'Create_Time', titleAlign: 'center',columnAlign:'center',width: 150,isEdit:true},
                    {field: 'update_time', title: 'Update_Time', titleAlign: 'center',columnAlign:'center',width: 150,isEdit:true},
                    {field: 'enable', title: 'Enable', titleAlign: 'center',columnAlign:'center',width: 50,isEdit:true}
                ],
				pageIndex: 1,
				pageSize: 10,
				total: 0,
				id: '',
				page_id: '',
				page_name: '',
				feather: '',
				icon: '',
				page_title: '',
				parent: '',
				desc: '',
				create_time: '',
				update_time: '',
				enable:'',
				uid: '',
				changeflag: '',
				selectObj: [],
				editObj: [],
				pageName: "vue-permission-page.html",
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
        	  fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/page/get",{
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
        	fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/page/add",{
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
				    	page_id: self.page_id,
				    	page_name: self.page_name,
						feather: self.feather,
						icon: self.icon,
						page_title: self.page_title,
						parent: self.parent,
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
				location.href="vue-permission-page.html";
		  },
		  modifyData(){
			for (var i=0;i<this.editObj.length;i++) {
				var self = this;
        	    fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/page/upd",{
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
                        page_id: this.editObj[i].page_id,
                        page_name: this.editObj[i].page_name,
                        feather: this.editObj[i].feather,
                        icon: this.editObj[i].icon,
                        page_title: this.editObj[i].page_title,
                        parent: this.editObj[i].parent,
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
			location.href="vue-permission-page.html";
		  },
		  deleteData(){
			for (var i=0;i<this.selectObj.length;i++) {
        	    fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/permission/page/del",{
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
                        page_id: this.selectObj[i].page_id,
                        page_name: this.selectObj[i].page_name,
                        feather: this.selectObj[i].feather,
                        icon: this.selectObj[i].icon,
                        page_title: this.selectObj[i].page_title,
                        parent: this.selectObj[i].parent,
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
			location.href="vue-permission-page.html";
			  //console.log(this.selectObj[0].key);
			  //console.log("delete");
		  }
        }
})