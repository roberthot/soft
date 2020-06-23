<template>
	<div class="sidebar">
		<el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157" text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
			<template v-for="item in items">
				<template v-if="item.userFuncPermissions.length>=1">
					<el-submenu :index="item.url" :key="item.url">
						<template slot="title">
							<i :class="'el-icon-lx-'+item.icon"></i>
							<span slot="title">{{ item.name }}</span>
						</template>
						<template v-for="subItem in item.userFuncPermissions">
							<el-submenu v-if="subItem.userFuncPermissions && subItem.selPerm=='1'" :id="subItem.url" :key="subItem.url">
								<template slot="title">{{ subItem.name }}</template>
							</el-submenu>
							<el-menu-item v-else :index="subItem.url" :key="subItem.url" v-show="subItem.selPerm=='1'">
								<i :class="'el-icon-lx-'+subItem.icon"></i>{{ subItem.name }}</el-menu-item>
						</template>
					</el-submenu>
				</template>
				<template v-else>
					<el-menu-item :index="item.url" :key="item.url">
						<i :class="'el-icon-lx-'+item.icon"></i>
						<span slot="title">{{ item.name }}</span>
					</el-menu-item>
				</template>
			</template>
		</el-menu>
	</div>
</template>

<script>
	import bus from "../common/bus";
	export default {
		data() {
			return {
				collapse: false,
				items: []
			};
		},
		computed: {
			onRoutes() {
				return this.$route.path.replace("/", "");
			}
		},
		methods: {
			getdata() {
				let that = this;
				this.$axios({
					method: "post",
					url: this.$Api.BaseUrl + "/findMenuByUser"
				}).then(res => {
					if(res.data.status == 500){
						this.$router.push("/login")
					}
					let items = res.data.data;
					if(items[0].url == 'main')
						this.$router.push("/main")
					
					this.items = items;
					for(let x in items) {
						let item = items[x];
						if(item.userFuncPermissions.length != 0) {
							for(let y in item.userFuncPermissions) {
								let itemFun = item.userFuncPermissions[y];
								sessionStorage.setItem(itemFun.url, JSON.stringify(itemFun));
							}
						} else {
							sessionStorage.setItem(item.url, JSON.stringify(item));
						}
					}
				});
			}
		},
		mounted: function() {
			this.getdata();
		},
		created() {
			bus.$on("collapse", msg => {
				this.collapse = msg;
			});
		}
	};
</script>

<style scoped>
	.sidebar {
		display: block;
		position: absolute;
		left: 0;
		top: 70px;
		bottom: 0;
		overflow-y: scroll;
	}
	
	.sidebar::-webkit-scrollbar {
		width: 0;
	}
	
	.sidebar-el-menu:not(.el-menu--collapse) {
		width: 200px;
	}
	
	.sidebar>ul {
		height: 100%;
	}
</style>