const VAR_GLOBAL_HOST = "";
const VAR_GLOBAL_WEBSITE_PREFIX = "/adm";
const VAR_GLOBAL_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDAwMSIsInN1YiI6InFyeSIsImlhdCI6MTU2NTE3MDIyMywiaXNzIjoicXJ5IiwiYXVkIjoicXJ5IiwiZXhwIjoxNTY1NjcwODc3fQ.QxZO6BsEUZUOeIiULqURYis4V4lrA2GfNVLIKssjUjg";

const VAR_GLOBAL_WEBSITE_TITLE_MONITOR = "Mon";
const VAR_GLOBAL_WEBSITE_TITLE_CONFIG = "Config";
const VAR_GLOBAL_WEBSITE_TITLE_ECHARTS = "Echarts";
const VAR_GLOBAL_WEBSITE_TITLE_FILE = "file";
const VAR_GLOBAL_WEBSITE_TITLE_CARD = "card";

const VAR_GLOBAL_WEBSITE_HEADER_LOGON = "管理主页";
const VAR_GLOBAL_WEBSITE_HEADER_LOGIN = "Sign out";
const VAR_GLOBAL_WEBSITE_DEFAULT_USER_NAME = "qry";
const VAR_GLOBAL_WEBSITE_DEFAULT_USER_ID = "10001";

const VAR_GLOBAL_WEBSITE_DEFAULT_CRYPT_KEY = "qwer09876000tyui";

const VAR_SIDEBAR_LIST = [
{
	name: "配置",
	feather: "home",
	parent: "0",
	url: "vue-config.html",
	icon: "el-icon-setting",
	titlename: "配置",
	islogin: "y"
},
{
	name: "监控",
	feather: "file",
	parent: "0",
	url: "vue-mon.html",
	icon: "el-icon-monitor",
	titlename: "监控",
	islogin: "n"
},
{
	name: "图表",
	feather: "file",
	parent: "0",
	url: "vue-echarts-line.html",
	icon: "el-icon-data-line",
	titlename: "图表",
	islogin: "n"
},
{
	name: "文件管理",
	feather: "file",
	parent: "0",
	url: "vue-file-upload.html",
	icon: "el-icon-document",
	titlename: "文件管理",
	islogin: "y"
},
{
	name: "Card",
	feather: "file",
	parent: "0",
	url: "vue-card-index.html",
	icon: "el-icon-edit",
	titlename: "Card",
	islogin: "n"
},
{
	name: "权限配置",
	feather: "home",
	parent: "0",
	url: "vue-permission.html",
	icon: "el-icon-setting",
	titlename: "权限配置",
	islogin: "y"
},
{
	name: "用户管理",
	feather: "home",
	parent: "0",
	url: "vue-permission-user.html",
	icon: "el-icon-setting",
	titlename: "用户管理",
	islogin: "y"
},
{
	name: "角色管理",
	feather: "home",
	parent: "0",
	url: "vue-permission-role.html",
	icon: "el-icon-setting",
	titlename: "角色管理",
	islogin: "y"
},
{
	name: "用户角色管理",
	feather: "home",
	parent: "0",
	url: "vue-permission-user-role-mapping.html",
	icon: "el-icon-setting",
	titlename: "用户角色管理",
	islogin: "y"
},
]