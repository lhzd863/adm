new Vue({
        el: '#my-main-contriner',
        data: function() {
            return {
				tableData: [],
				yData: [],
				xData: [],
				pageName: "vue-echarts-line.html",
				headerInfo: []
            }
        },
		mounted() {
			
		},
        created: function(){
        	VueCommonDB.init(this.pageName);
        	this.headerInfo = JSON.parse(localStorage.getItem("cmts") || '[]');
            this.getSourceData();	  		
        },
        methods:{
          getSourceData:function(){
			  
        	  var self = this;
        	  fetch(VAR_GLOBAL_WEBSITE_PREFIX+"/api/data/line",{
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
                	  self.tableData=data;
					  this.$nextTick(() => {
                          this.initEcharts();	
			          })
                      //console.log(self.tableData);
                 })
                 .catch(function (err) {
                      console.log(err);
                 }); 
          },
		  initEcharts:function(){
			
			for(var i=0;i<this.tableData.length;i++){
				this.xData.push(this.tableData[i]["dt"]);
				this.yData.push(this.tableData[i]["val"]);
			}

			// 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(document.getElementById('my-echarts-location'));
		    
            // 指定图表的配置项和数据
            var option = {
				title: {
                    text: 'yahoo',
					x:'center',
					y:'top'
                },
				tooltip: {
                    trigger: 'axis',
                    formatter: function (params) {
                        params_value = params[0].value;
						params_name = params[0].name;
                        return params_name+","+params_value;
                    },
                    axisPointer: {
                        animation: false
                    }
                },
                xAxis: {
                    type: 'category',
					splitLine: {
                        show: false
                    },
                    data: this.xData
                },
                yAxis: {
                    type: 'value',
					boundaryGap: [0, '100%'],
					splitLine: {
                        show: false
                    },
					minInterval : 1,
					axisLabel:{
						show: true,
					    interval:0
				    }
                },
                series: [{
                    data: this.yData,
                    type: 'line',
					showSymbol: true,
                    hoverAnimation: false
                }]
            };
		    
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);  
		  }
        
        }
})