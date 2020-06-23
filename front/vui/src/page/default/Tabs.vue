<template>
	<div class="">
		<div class="container">
			<el-tabs v-model="message">
                <el-tab-pane :label="`未读消息(${unread.length})`" name="first">
                    <el-table :data="unread" :show-header="false" style="width: 100%">
                        <el-table-column>
                            <template slot-scope="scope">
                                <span class="message-title">{{scope.row.title}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="date" width="180"></el-table-column>
                        <el-table-column width="120">
                            <template slot-scope="scope">
                                <el-button size="small" @click="handleRead(scope.$index)">标为已读</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="handle-row">
                        <el-button type="primary">全部标为已读</el-button>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="`已读消息(${read.length})`" name="second">
                    <template v-if="message === 'second'">
                        <el-table :data="read" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template slot-scope="scope">
                                    <span class="message-title">{{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" width="150"></el-table-column>
                            <el-table-column width="120">
                                <template slot-scope="scope">
                                    <el-button type="danger" @click="handleDel(scope.$index)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <el-button type="danger">删除全部</el-button>
                        </div>
                    </template>
                </el-tab-pane>
                <el-tab-pane :label="`回收站(${recycle.length})`" name="third">
                    <template v-if="message === 'third'">
                        <el-table :data="recycle" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template slot-scope="scope">
                                    <span class="message-title">{{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" width="150"></el-table-column>
                            <el-table-column width="120">
                                <template slot-scope="scope">
                                    <el-button @click="handleRestore(scope.$index)">还原</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <el-button type="danger">清空回收站</el-button>
                        </div>
                    </template>
                </el-tab-pane>
            </el-tabs>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'tabs',
		data() {
			return {
				message: 'first',
				showHeader: false,
				unread: [{
					date: '2018-04-19 20:00:00',
					title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护',
				}, {
					date: '2018-04-19 21:00:00',
					title: '今晚12点整发大红包，先到先得',
				}],
				read: [{
					date: '2018-04-19 20:00:00',
					title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护'
				}],
				recycle: [{
					date: '2018-04-19 20:00:00',
					title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护'
				}],
				tableData:[],
				page:{
					total:0,
					pageNum:1,
					pageSize:10
				},
			}
		},
		mounted(){
			this.getMessage()
		},
		methods: {
			//消息列表
			getMessage() {
				this.$axios({
						method: "get",
						params:{
							pageSize:this.page.pageSize,
							pageNum:this.page.pageNum
						},
						url: this.$Api.BaseUrl + '/queryMessage'
					})
					.then(res => {
						console.log(res)
						if(res.data.status == "200") {
							let data = res.data.data
							this.tableData = data.list
							this.page.total=data.total
						} else {
							this.$message.error(res.data.msg)
						}
					})
					.catch(error => {
						console.log(error); //请求失败返回的数据
					});
			},
			handelSizeChange(val) { //分页时页大小变化
				this.page.pageSize = val;
				this.getMessage()
			},
			handleCurrentChange(val) { //切换页码
				this.page.pageNum = val;
				console.log(val)
				this.getMessage()
			},
			handleRead(index) {
				const item = this.unread.splice(index, 1);
				console.log(item);
				this.read = item.concat(this.read);
			},
			handleDel(index) {
				const item = this.read.splice(index, 1);
				this.recycle = item.concat(this.recycle);
			},
			handleRestore(index) {
				const item = this.recycle.splice(index, 1);
				this.read = item.concat(this.read);
			}
		},
		computed: {
			unreadNum() {
				return this.unread.length;
			}
		}
	}
</script>

<style>
	.message-title {
		cursor: pointer;
	}
	
	.handle-row {
		margin-top: 30px;
	}
</style>