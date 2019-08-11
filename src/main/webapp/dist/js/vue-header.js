Vue.component('vue-html-sidebar', {
  props: ['isDashboardActive','isAdminActive'],
  template: `
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
          <div class="sidebar-sticky">
            <ul class="nav flex-column">
              <li class="nav-item">
                <a class="nav-link" href="vue-config.html">
                  <span data-feather="home"></span>
                                                              配置 <span class="sr-only" >(current)</span>
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="vue-mon.html">
                  <span data-feather="file"></span>
                                                               监控 
                </a>
              </li>
			  <li class="nav-item">
                <a class="nav-link" href="vue-echarts-line.html">
                  <span data-feather="file"></span>
                                                               图表 
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="vue-file-upload.html">
                  <span data-feather="file"></span>
                                                               文件管理
                </a>
              </li>
            </ul>
          </div>
        </nav>
  `
})
