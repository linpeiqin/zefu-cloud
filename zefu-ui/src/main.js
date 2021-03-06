import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'
import '@/assets/styles/index.scss' // global css
import '@/assets/styles/zefu.scss' // zefu css
import App from './App'
import store from './store'
import router from './router'
import permission from './directive/permission'
import { download } from '@/utils/request'
import global from '@/utils/global'
Vue.use(global)
import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/zefu";
import Pagination from "@/components/Pagination";
// 自定义表格工具扩展
import RightToolbar from "@/components/RightToolbar"
import BaiduMap from 'vue-baidu-map'
import JsonViewer from 'vue-json-viewer'
Vue.use(JsonViewer)
import ByteTree from "@/views/business/product/tree/ByteTree";
import ByteRetrieveTree from "@/views/business/product/tree/retrieve/ByteRetrieveTree";
Vue.component('byte-tree', ByteTree)
Vue.component('byte-retrieve-tree', ByteRetrieveTree)
import md5 from 'js-md5'
Vue.prototype.$md5 = md5
// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

Vue.prototype.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: "success" });
}

Vue.prototype.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: "error" });
}

Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
}

// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.use(permission)

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

Vue.use(BaiduMap, {
  ak: 'aLOsyh3woEhCZBbFMb0QQzP3B65GP9T3'
})
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
