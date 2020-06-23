import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
//import '../static/css/theme-green/index.css';       // 浅绿色主题
import './assets/css/icon.css';
import 'font-awesome/css/font-awesome.min.css';
import './common/directives';
import "babel-polyfill";
import qs from 'qs'
import Api from "@/data/URL"
import UserInfo from '@/data/UserInfo'
import MyDate from '@/data/MyDate'
import cookie from './data/Cookie';
import Util from './data/Util';
import  'echarts/theme/macarons.js'
import  'echarts/theme/shine.js'
import echarts from 'echarts' //引入echarts

var ElTreeGrid = require('element-tree-grid');

Vue.prototype.$echarts = echarts  //注册组件
Vue.prototype.$Api = Api;
Vue.prototype.$UserInfo = UserInfo;
Vue.prototype.$myDate = MyDate;
Vue.prototype.qs = qs;
Vue.prototype.$pageSize=20; //默认每页大小
Vue.prototype.$pageNum=1;
Vue.prototype.Util = Util;

Vue.config.productionTip = false
Vue.prototype.$axios = axios;
Vue.use(ElementUI, {size: 'small'});
Vue.component(ElTreeGrid.name,ElTreeGrid);

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    const token = cookie.get('token');
    if (!token && to.path !== '/login') {
        next('/login');
    } else {
        if (navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor') {
            Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
                confirmButtonText: '确定'
            });
        } else {
			if(to.path !== '/login'){
				axios.defaults.headers['token']=UserInfo.getToken();
			}
            next();
        }
    }
})

new Vue({
    router,
    Api,
    render: h => h(App)
}).$mount('#app')