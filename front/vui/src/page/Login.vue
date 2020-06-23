<template>
	<div class="login-wrap">
		<div class="ms-login">
			<div class="ms-title">管理平台</div>
			<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="ms-content">
				<el-form-item prop="username">
					<el-input v-model="ruleForm.username" placeholder="请输入账户名">
						<span slot="prepend"><i class="el-icon-lx-people"></i></span>
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input type="password" placeholder="请输入密码" v-model="ruleForm.password" @keyup.enter.native="submitForm('ruleForm')">
						<span slot="prepend"><i class="el-icon-lx-lock"></i></span>
					</el-input>
				</el-form-item>
				<div class="login-btn">
					<el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
				</div>
				<p class="login-tips"></p>
			</el-form>
		</div>
	</div>
</template>
<script>
	export default {
		data: function() {
			return {
				ruleForm: {
					username: "",
					password: ""
				},
				rules: {
					username: [{
						required: true,
						message: "请输入用户名",
						trigger: "blur"
					}],
					password: [{
						required: true,
						message: "请输入密码",
						trigger: "blur"
					}],
				}
			};
		},
		methods: {
			submitForm() {
				this.$axios({
						method: "post",
						url: this.$Api.BaseUrl + '/login',
						data: this.qs.stringify({
							account: this.ruleForm.username,
							password: this.ruleForm.password
						})
					})
					.then(res => {
						if (res.data.status === 1) {
							this.$UserInfo.set("token", res.data);
							if(res.data.data.period == 0){
								this.$alert('系统使用权限已到期，暂停使用，请联系工作人员', '温馨提示', {
									confirmButtonText: '确定'
								});
								return;
							}
							if(res.data.data.period <= 5){
								this.$alert('系统使用权限即将到期（还有'+res.data.data.period+'天），请联系工作人员', '温馨提示', {
									confirmButtonText: '确定',
									callback: action => {
										this.$router.push("/");
									}
								});
							}else{
								this.$router.push("/");
							}
						} else {
							this.open8(res.data.msg);
							this.ruleForm.password = "";
						}
					})
					.catch(error => {
						console.log(error); //请求失败返回的数据
					});
			},
			open8(msg) {
				this.$message({
					showClose: true,
					message: "喔，" + msg + "，再一次?",
					type: "error"
				});
			}
		}
	};
</script>

<style scoped>
	.login-wrap {
		position: relative;
		width: 100%;
		height: 100%;
		background-image: url(../assets/img/login-bg.jpg);
		background-size: 100%;
	}

	.ms-title {
		width: 100%;
		line-height: 50px;
		text-align: center;
		font-size: 20px;
		color: #fff;
		border-bottom: 1px solid #ddd;
	}

	.ms-login {
		position: absolute;
		left: 50%;
		top: 50%;
		width: 350px;
		margin: -190px 0 0 -175px;
		border-radius: 5px;
		background: rgba(255, 255, 255, 0.3);
		overflow: hidden;
	}

	.ms-content {
		padding: 30px 30px;
	}

	.login-btn {
		text-align: center;
	}

	.login-btn button {
		width: 100%;
		height: 36px;
		margin-bottom: 10px;
	}

	.login-tips {
		font-size: 12px;
		line-height: 30px;
		color: #fff;
	}
</style>
