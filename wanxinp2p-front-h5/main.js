import Vue from 'vue'
import App from './App'

import store from './store'
import utils from './utils/utils.js'
import request from './utils/request.js'

Vue.config.productionTip = false

Vue.prototype.$store = store
Vue.prototype.utils = utils
Vue.prototype.request = request.request
Vue.prototype.baseUrl = request.baseUrl
Vue.prototype.fileServiceUrl = request.fileServiceUrl

App.mpType = 'app'

const app = new Vue({
    store,
    ...App
})
app.$mount()
