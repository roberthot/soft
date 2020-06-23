import Vue from 'vue';
import Router from 'vue-router';
import defaultRouter from './defaultRouter';
//import customerRouter from './customerRouter';


let childrenRouter = [{
            path: '/main',
            component: resolve => require(['../page/main.vue'], resolve),
            meta: {
                title: '系统首页'
            }
        }
    ];
childrenRouter = childrenRouter.concat(defaultRouter.router);
//childrenRouter = childrenRouter.concat(customerRouter.router);
//console.log(childrenRouter)
Vue.use(Router);
export default new Router({
	//mode:'history',
    routes: [
        {
            path: '/',
            component: resolve => require(['../common/Home.vue'], resolve),
            children: childrenRouter
        },
        {
            path: '/login',
            component: resolve => require(['../page/Login.vue'], resolve)
        },
		{
            path: '*',
            redirect: '/404'
        },
        {
            path: '/message',
            component: resolve => require(['../page/message/message.vue'], resolve),
            meta: {
                title: '系统消息'
            }
        },
    ]
})