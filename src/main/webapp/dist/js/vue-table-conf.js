new Vue({
        el: '#my-main-contriner',
        data: function() {
            return {
                tableData: [],
				multipleSort:true,
                columns: [
				    {width: 50, titleAlign: 'center',columnAlign:'center',type: 'selection'},
                    {field: 'sys', title:'Sys', titleAlign: 'center',columnAlign:'center',width: 100},
                    {field: 'key', title: 'Key', titleAlign: 'center',columnAlign:'left',width: 300},
                    {field: 'val', title: 'Value', titleAlign: 'center',columnAlign:'center',width: 300,isEdit:true},
                    {field: 'remark', title: 'Remark', titleAlign: 'center',columnAlign:'center',width: 300,isEdit:true},
                    {field: 'enable', title: 'Enable', titleAlign: 'center',columnAlign:'center',width: 50,isEdit:true}
                ],
				pageIndex: 1,
				pageSize: 10,
				total: 0,
				sys: '',
				key: '',
				val: '',
				remark: '',
				enable:'',
				changeflag: '',
				selectObj: [],
				editObj: [],
				pageName: "vue-config.html",
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
        	  fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/conf/get",{
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
					if(this.editObj[i].key==rowData.key&&this.editObj[i].sys==rowData.sys){
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
        	fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/conf/add",{
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8',
  					    'token': this.headerInfo[0].accesstoken,
  				        'username': this.headerInfo[0].username,
  				        'uid': this.headerInfo[0].uid
                    },
                    body: JSON.stringify({
                        appid: 'a00002',
                        accesstoken: VAR_GLOBAL_ACCESS_TOKEN,
                        sys: self.sys,
				    	key: self.key,
				    	val: self.val,
						remark: self.remark,
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
				location.href="vue-config.html";
		  },
		  modifyData(){
			for (var i=0;i<this.editObj.length;i++) {
				var self = this;
        	    fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/conf/upd",{
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8',
  					    'token': this.headerInfo[0].accesstoken,
  				        'username': this.headerInfo[0].username,
  				        'uid': this.headerInfo[0].uid
                    },
                    body: JSON.stringify({
                        appid: 'a00002',
                        accesstoken: VAR_GLOBAL_ACCESS_TOKEN,
                        sys: this.editObj[i].sys,
				    	key: this.editObj[i].key,
				    	val: this.editObj[i].val,
						remark: this.editObj[i].remark,
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
				alert("update "+this.editObj[i].sys+"->"+this.editObj[i].key+" succ");
		    }
			alert("update succ");
			location.href="vue-config.html";
		  },
		  deleteData(){
			for (var i=0;i<this.selectObj.length;i++) {
        	    fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/conf/del",{
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8',
  					    'token': this.headerInfo[0].accesstoken,
  				        'username': this.headerInfo[0].username,
  				        'uid': this.headerInfo[0].uid
                    },
                    body: JSON.stringify({
                        appid: 'a00002',
                        accesstoken: VAR_GLOBAL_ACCESS_TOKEN,
                        sys: this.selectObj[i].sys,
				    	key: this.selectObj[i].key,
				    	val: this.selectObj[i].val,
						remark: this.selectObj[i].remark,
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
				alert("delete "+this.selectObj[i].sys+"->"+this.selectObj[i].key+" succ");
		    }
			alert("delete succ");
			location.href="vue-config.html";
			  //console.log(this.selectObj[0].key);
			  //console.log("delete");
		  }
        }
})