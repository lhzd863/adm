new Vue({
        el: '#my-main-contriner',
        data: function() {
            return {
                tableData: [],
				multipleSort:true,
                columns: [
				    {width: 50, titleAlign: 'center',columnAlign:'center',type: 'selection'},
                    {field: 'id', title:'Id', titleAlign: 'center',columnAlign:'center',width: 100},
                    {field: 'name', title: 'Name', titleAlign: 'center',columnAlign:'left',width: 300},
                    {field: 'current', title: 'Current', titleAlign: 'center',columnAlign:'center',width: 100},
                    {field: 'operator', title: 'Operator', titleAlign: 'center',columnAlign:'center',width: 100},
                    {field: 'alert', title: 'Alert', titleAlign: 'center',columnAlign:'center',width: 100},
                    {field: 'status', title: 'Status', titleAlign: 'center',columnAlign:'center',width: 100,
                      formatter: function (rowData, index) {
                          return rowData.status=='Ok' ? rowData.status :'<span style="color:red;font-weight: bold;">' + rowData.status + '</span>'
                      }
					},
                    {field: 'timestamp', title: 'Timestamp', titleAlign: 'center',columnAlign:'center',width: 150},
                    {field: 'remark', title: 'Remark', titleAlign: 'center',columnAlign:'left',width: 100}
                ],
				pageIndex: 1,
				pageSize: 10,
				total: 0,
				pageName: "vue-mon.html",
				headerInfo: []
            }
        },
        created: function(){
        	VueCommonDB.init(this.pageName);
        	this.headerInfo = JSON.parse(localStorage.getItem("cmts") || '[]');	
			//alert(token);
            this.getTableData();	  
        },
        methods:{
          getSourceData:function(){
        	  var self = this;
        	  fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/mon",{
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
              console.log('select-aLL',selection);
          },
          selectChange(selection,rowData){
              console.log('select-change',selection,rowData.name);
          },
          selectGroupChange(selection){
              console.log('select-group-change',selection[0].name);
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
              this.tableData[rowIndex][field] = newValue;
			  console.log(rowIndex);
          },
		  addData(){
			  console.log("add"); 
		  },
		  modifyData(){
			  console.log("modify");
		  },
		  deleteData(){
			  console.log("delete");
		  }
        }
})