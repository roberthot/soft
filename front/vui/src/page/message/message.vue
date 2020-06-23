<template>
	<div class="">
		<div class="container">
			<el-table :data="tableData" :show-header="false" style="width: 100%">
				<el-table-column>
					<template slot-scope="scope">
						<span class="message-title">{{scope.row.content}}</span>
					</template>
				</el-table-column>
				<el-table-column prop="add_time" width="150"></el-table-column>
			</el-table>
			<div class="pagination">
				<el-pagination background @current-change="handleCurrentChange" @size-change="handelSizeChange" :current-page="page.pageNum" :page-sizes="[10,20, 30, 40, 50, 100]" :page-size="page.pageSize" layout="sizes, prev, pager, next" :total="page.total"></el-pagination>
			</div>
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