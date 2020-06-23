const defaultRouter = {router:[
	{
		path: '/icon',
		component: resolve => require(['../page/Icon.vue'], resolve),
		meta: {
			title: '自定义图标'
		}
	},
	{
		path: '/form',
		component: resolve => require(['../page/default/BaseForm.vue'], resolve),
		meta: {
			title: '基本表单'
		}
	},
	{
		// 富文本编辑器组件
		path: '/editor',
		component: resolve => require(['../page/default/VueEditor.vue'], resolve),
		meta: {
			title: '富文本编辑器'
		}
	},
	{
		// markdown组件
		path: '/markdown',
		component: resolve => require(['../page/default/Markdown.vue'], resolve),
		meta: {
			title: 'markdown编辑器'
		}
	},
	{
		// 图片上传组件
		path: '/upload',
		component: resolve => require(['../page/default/Upload.vue'], resolve),
		meta: {
			title: '文件上传'
		}
	},
	{
		// vue-schart组件
		path: '/charts',
		component: resolve => require(['../page/default/BaseCharts.vue'], resolve),
		meta: {
			title: 'schart图表'
		}
	},
	{
		// vue-schart组件
		path: '/tabs',
		component: resolve => require(['../page/default/Tabs.vue'], resolve),
		meta: {
			title: 'Tabs'
		}
	},
	{
		// 拖拽列表组件
		path: '/drag',
		component: resolve => require(['../page/default/DragList.vue'], resolve),
		meta: {
			title: '拖拽列表'
		}
	},
	{
		// 拖拽Dialog组件
		path: '/dialog',
		component: resolve => require(['../page/default/DragDialog.vue'], resolve),
		meta: {
			title: '拖拽弹框'
		}
	},
	{
		// 权限页面
		path: '/permission',
		component: resolve => require(['../page/default/Permission.vue'], resolve),
		meta: {
			title: '权限测试',
			permission: true
		}
	},
	{
		path: '/404',
		component: resolve => require(['../page/404.vue'], resolve),
		meta: {
			title: '404'
		}
	},
	{
		path: '/403',
		component: resolve => require(['../page/403.vue'], resolve),
		meta: {
			title: '403'
		}
	}]
};
				
export default defaultRouter