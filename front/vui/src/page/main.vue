<template>
	<div>
		<el-row>
			<el-col :span="24">
				<el-row class="mgb20">
					<el-col :span="4" style="padding-left:0px;">
						<el-card shadow="hover" :body-style="{padding: '0px'}">
							<div class="grid-content grid-con-1">
								<i class="el-icon-lx-people grid-con-icon"></i>
								<div class="grid-cont-right">
									<div class="grid-num">{{headerInfo.customerNum?headerInfo.customerNum:0}}</div>
									<div class="grid-label">用户总数</div>
								</div>
							</div>
						</el-card>
					</el-col>
					<el-col :span="4">
						<el-card shadow="hover" :body-style="{padding: '0px'}">
							<div class="grid-content grid-con-2">
								<i class="el-icon-lx-people grid-con-icon"></i>
								<div class="grid-cont-right">
									<div class="grid-num">{{headerInfo.addCustomerNum?headerInfo.addCustomerNum:0}}</div>
									<div class="grid-label">今日新增</div>
								</div>
							</div>
						</el-card>
					</el-col>
					<el-col :span="4">
						<el-card shadow="hover" :body-style="{padding: '0px'}">
							<div class="grid-content grid-con-3">
								<i class="el-icon-lx-goods grid-con-icon"></i>
								<div class="grid-cont-right">
									<div class="grid-num">{{headerInfo.sumBalance?(headerInfo.sumBalance/1000).toFixed(0):0}}</div>
									<div class="grid-label">总充值（元）</div>
								</div>
							</div>
						</el-card>
					</el-col>
					<el-col :span="4">
						<el-card shadow="hover" :body-style="{padding: '0px'}">
							<div class="grid-content grid-con-4">
								<i class="el-icon-lx-goods grid-con-icon"></i>
								<div class="grid-cont-right">
									<div class="grid-num">{{headerInfo.toDaySumBalance?(headerInfo.toDaySumBalance/1000):0}}</div>
									<div class="grid-label">今日充值</div>
								</div>
							</div>
						</el-card>
					</el-col>
					<el-col :span="4">
						<el-card shadow="hover" :body-style="{padding: '0px'}">
							<div class="grid-content grid-con-5">
								<i class="el-icon-document grid-con-icon"></i>
								<div class="grid-cont-right">
									<div class="grid-num">{{(headerInfo.chargeInfoNum?headerInfo.chargeInfoNum:0)}}</div>
									<div class="grid-label">订单总数</div>
								</div>
							</div>
						</el-card>
					</el-col>
					<el-col :span="4" style="padding-right:0">
						<el-card shadow="hover" :body-style="{padding: '0px'}">
							<div class="grid-content grid-con-6">
								<i class="el-icon-document grid-con-icon"></i>
								<div class="grid-cont-right">
									<div class="grid-num">{{headerInfo.toDayExpenditureBalance?(headerInfo.toDayExpenditureBalance):0}}</div>
									<div class="grid-label">今日订单</div>
								</div>
							</div>
						</el-card>
					</el-col>
				</el-row>
			</el-col>
		</el-row>
		<el-row>
			<el-col :span="12">
				<el-card shadow="hover">
					<div ref="bar" class="echart" id="last7User"></div>
				</el-card>
			</el-col>
			<el-col :span="12">
				<el-card shadow="hover">
					<div ref="line" class="echart" id="last7Recharge"></div>
				</el-card>
			</el-col>
		</el-row>
		<el-row>
			<el-col :span="6">
				<el-card shadow="hover" class="mgb20">
					<div style="font-weight: 900;text-align: center;margin-bottom:10px;">我的消息</div>
					
				</el-card>
			</el-col>
			<el-col :span="6">
				<el-card shadow="hover">
					<div ref="pie" class="echart" id="myPie1"></div>
				</el-card>
			</el-col>
			<el-col :span="6">
				<el-card shadow="hover">
					<div ref="pie" class="echart" id="myPie2"></div>
				</el-card>
			</el-col>
			<el-col :span="6">
				<el-card shadow="hover">
					<div ref="pie" class="echart" id="myPie3"></div>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				userInfo: {}, //用户数据
				headerInfo:{},
				the7thDayCustomerData:{
					date:[],
					num:[]
				},
				showIndex:false
			}
		},
		components: {

		},
		computed: {
			
		},
		mounted() {
			let that = this;
			this.setName();
			that.showIndex = true;
			that.loadHeaderInfo();
			that.the7thDayCustomerRise();
			that.last7Recharge();
			that.a1()
			that.a2()
			that.a3()
		},
		methods: {
			//用户基本信息
			setName() {
				let userInfo = this.$UserInfo.get();
				this.userInfo = eval('(' + userInfo + ')');
			},
			loadHeaderInfo(){
				//interfaceHeader
				this.$axios({
						method: "post",
						url: this.$Api.BaseUrl + "/interfaceHeader"
					})
					.then(res => {
						if(res.data.status === 200) {
							this.headerInfo = res.data.data;
						}
					})
					.catch(error => {
						console.log(error);
					});
			},
			the7thDayCustomerRise(){
				var myChart = this.$echarts.init(document.getElementById('last7User'),'macarons');
				this.$axios({
						method: "post",
						url: this.$Api.BaseUrl + "/the7thDayCustomerRise"
					})
					.then(res => {
						if(res.data.status === 200) {
							let data = res.data.data;
							for(let i in data){
								this.the7thDayCustomerData.date[i] = data[i].addTime;
								this.the7thDayCustomerData.num[i] = data[i].num;
							}
							let option = {
								color: ['#3398DB'],
								title: {text:'近两周用户增长柱状图'},
								tooltip : {trigger: 'axis',axisPointer : {type : 'shadow'}},
								grid: {left: '2%',right: '3%',bottom: '2%',containLabel: true},
								xAxis : [
									{
										type : 'category',
										data : this.the7thDayCustomerData.date,
										axisTick: {
											alignWithLabel: true
										}
									}
								],
								yAxis : [
									{
										type : 'value'
									}
								],
								series : [
									{
										type:'bar',
										barWidth: '50%',
										data:this.the7thDayCustomerData.num
									}
								]
							};
							
							myChart.setOption(option);
						}
					})
					.catch(error => {
						console.log(error);
					});
			},
			last7Recharge(){
				var myChart = this.$echarts.init(document.getElementById('last7Recharge'),'macarons');
				this.$axios({
						method: "post",
						url: this.$Api.BaseUrl + "/the7thDayRecharge"
					}).then(res => {
						if(res.data.status === 200) {
							let data = res.data.data;
							let the7thDayRecharge = {balance:[],date:[]};
							for(let i in data){
								the7thDayRecharge.date[i] = data[i].addTime;
								the7thDayRecharge.balance[i] = data[i].balance;
							}
							let option = {
								title: {text: '近两周充值曲线图'},
								tooltip : {trigger: 'axis',axisPointer : {type : 'shadow'}},
								grid: {left: '2%',right: '3%',bottom: '2%',containLabel: true},
								xAxis: {
									type: 'category',
									data: the7thDayRecharge.date
								},
								yAxis: {
									type: 'value'
								},
								series: [{
									data: the7thDayRecharge.balance,
									type: 'line'
								}]
							};
							myChart.setOption(option);
						}
					});
			},
			a1(){//近一周充电记录图
				var myChart = this.$echarts.init(document.getElementById('myPie1'),'macarons');
				this.$axios({
						method: "post",
						url: this.$Api.BaseUrl + "/the7thDayChargeInfo"
					}).then(res => {
						if(res.data.status === 200) {
							let data = res.data.data;
							let the7thDayChargeInfo = new Array();
							the7thDayChargeInfo.push(['amount', 'product'])
							for(let i in data){
								let dt = new Array();
								dt.push(data[i].countStartTime);
								dt.push(data[i].startTime);
								the7thDayChargeInfo.push(dt);
							}
							let option = {
								title: {text: '近一周充电记录图'},
								tooltip : {trigger: 'axis',axisPointer : {type : 'shadow'}},
								dataset: {source: the7thDayChargeInfo},
								grid: {left: '1%',right: '1%',bottom: '2%',containLabel: true},
								xAxis: {name: 'amount',axisLabel:{interval:0,rotate:40}},
								yAxis: {type: 'category',axisLabel:{interval:0,rotate:40}},
								series: [{type: 'bar',encode: {x: 'amount',y: 'product'}}]
							};
							myChart.setOption(option);
						}
					});
			},
			a2(){
				var myChart = this.$echarts.init(document.getElementById('myPie2'),'macarons');
				this.$axios({
						method: "post",
						url: this.$Api.BaseUrl + "/the7thDayUsageRate"
					}).then(res => {
						if(res.data.status === 200) {
							let data = Math.floor(res.data.data*100);
							let option = {
								title: {
									text: '设备使用率',
									left: 'center'
								},
								tooltip : {
									formatter: "{a} <br/>{b} : {c}%"
								},
								series: [{name: '',
									type: 'gauge',
									detail: {formatter:'{value}%'},
									center: ['50%', '60%'],//饼图的位置 
									data: [{value: data, name: ''}]
								}]
							};
							myChart.setOption(option);
						}
					});
			},
			a3(){
				var myChart = this.$echarts.init(document.getElementById('myPie3'),'macarons');
				this.$axios({
						method: "post",
						url: this.$Api.BaseUrl + "/portGroupBy"
					}).then(res => {
						if(res.data.status === 200) {
							let data = res.data.data;
							let portGroupBy = new Array();
							for(let i in data){
								if(data[i].port == 1){
									portGroupBy.push({value:data[i].portCount,name:'单路'});
								}else if(data[i].port == 2){
									portGroupBy.push({value:data[i].portCount,name:'双路'});
								}else if(data[i].port == 10){
									portGroupBy.push({value:data[i].portCount,name:'十路'});
								}else if(data[i].port == 16){
									portGroupBy.push({value:data[i].portCount,name:'十六路'});
								}else if(data[i].port == 3){
									portGroupBy.push({value:data[i].portCount,name:'充值机'});
								}
									
							}
							let option = {
								title: {
									text: '设备数据占比图',
									left: 'center'
								},
								tooltip: {
									trigger: 'item',
									formatter: "{a} <br/>{b}: {c} ({d}%)"
								},
								legend: {
									orient: 'vertical',
									x: 'left',
									data:['单路','双路','十路','十六路','充值机']
								},
								series: [{
										name:'设备类型',
										type:'pie',
										radius: ['30%','45%'],
										center: ['50%', '70%'],//饼图的位置 
										label: {
											normal: {
												show: true,
												formatter:'{d}%',
											},
											emphasis: {
												show: true,
												textStyle: {
													fontSize: '15',
												}
											}
										},
										data:portGroupBy
									}
								]
							};
							myChart.setOption(option);
						}
					});
			},
		}
	}
</script>

<style scoped>
	.el-row{
		margin-top: 2px;
	}
	.el-card{
		border: 5px solid #EBEEF5 !important;
	}
	.grid-content {
		display: flex;
		align-items: center;
		height: 100px;
	}
	
	.grid-cont-right {
		flex: 1;
		text-align: center;
		font-size: 14px;
		color: #999;
	}
	
	.grid-num {
		font-size: 18px;
	}
	.grid-label{
		font-size: 14px;
	}
	
	.grid-con-icon {
		font-size: 50px;
		width: 90px;
		height: 90px;
		text-align: center;
		line-height: 80px;
		color: #fff;
		margin-left:5px;
	}
	
	.grid-con-1 .grid-con-icon {
		background: rgb(45, 140, 240);
	}
	
	.grid-con-1 .grid-num {
		color: rgb(45, 140, 240);
	}
	
	.grid-con-2 .grid-con-icon {
		background: rgb(100, 213, 114);
	}
	
	.grid-con-2 .grid-num {
		color: rgb(45, 140, 240);
	}
	
	.grid-con-3 .grid-con-icon {
		background: rgb(242, 94, 67);
	}
	
	.grid-con-3 .grid-num {
		color: rgb(242, 94, 67);
	}
	
	.grid-con-4 .grid-num {
		color: #E6A23C;
	}
	
	.grid-con-4 .grid-con-icon {
		background: #E6A23C;
	}
	
	.grid-con-5 .grid-num {
		color: #0FEE8A;
	}
	
	.grid-con-5 .grid-con-icon {
		background: #0FEE8A;
	}
	
	.grid-con-6 .grid-num {
		color: rgba(149, 231, 7, 0.8);
	}
	
	.grid-con-6 .grid-con-icon {
		background: rgba(149, 231, 7, 0.8);
	}
	
	.user-info {
		display: flex;
		align-items: center;
		padding-bottom: 20px;
		border-bottom: 2px solid #ccc;
		margin-bottom: 20px;
	}
	
	.user-avator {
		width: 120px;
		height: 120px;
		border-radius: 50%;
	}
	
	.user-info-cont {
		padding-left: 50px;
		flex: 1;
		font-size: 14px;
		color: #999;
	}
	
	.user-info-cont div:first-child {
		font-size: 30px;
		color: #222;
	}
	
	.user-info-list {
		font-size: 14px;
		color: #999;
		line-height: 25px;
	}
	
	.user-info-list span {
		margin-left: 70px;
	}
	
	.mgb20 {
		margin: 0 !important;
	}
	
	.todo-item {
		font-size: 14px;
	}
	
	.todo-item-del {
		text-decoration: line-through;
		color: #999;
	}
	
	.echart {
		width: 100%;
		height: 300px;
	}
</style>