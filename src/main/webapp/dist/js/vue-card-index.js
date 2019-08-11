new Vue({
        el: '#my-main-contriner',
        data: function() {
            return {
            	currentDate: new Date(),
				sidebarList: VAR_SIDEBAR_LIST,
				pageName: "vue-card-index.html",
				headerInfo: []
            }
        },
        created: function(){
        	VueCommonDB.init(this.pageName);
        	this.headerInfo = JSON.parse(localStorage.getItem("cmts") || '[]');
        },
        methods:{
			
        }
})