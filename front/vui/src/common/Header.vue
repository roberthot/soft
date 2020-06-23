<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
            {{platformName}}
        </div>
        <div class="logo collapse-btn" @click="collapseChage">智能充电管理平台</div>
        <div class="header-right">
            <div class="header-user-con">
                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>
                <div class="btn-bell">
                    <el-tooltip effect="dark" :content="message?`有${message}条未读消息`:`消息中心`" placement="bottom">
                        <router-link to="/message">
                            <i class="el-icon-bell"></i>
                        </router-link>
                    </el-tooltip>
                    <span class="btn-bell-badge" v-if="message"></span>
                </div>
                <div class="user-avator">
                    <img src="../assets/img/img.jpg" v-show="!userInfo.headImage">
                    <img :src="userInfo.headImage" v-show="userInfo.headImage">
                </div>
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{name}} <i class="el-icon-caret-bottom"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="updatePassword">修改密码</el-dropdown-item>
                        <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
		
		<el-dialog title="修改密码" :visible.sync="updatePasswordVisible" width="300px" center >
			<el-form :model="form" label-width="80px">
				<el-form-item label="老密码" prop="password">
					<el-input type="password" v-model="form.password"></el-input>
				</el-form-item>
				<el-form-item label="新密码" prop="newPass">
					<el-input type="password" v-model="form.newPass"></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="okPass">
					<el-input type="password" v-model="form.okPass"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="updatePasswordVisible = false">取 消</el-button>
				<el-button type="primary" @click="confirmUpdate">确 定</el-button>
			  </span>
		</el-dialog>
    </div>
</template>
<script>
    import bus from '../common/bus';
    import URL from '@/data/URL';
    export default {
        data() {
            this.setName();
            return {
				form:{
					password:'',
					newPass:'',
					okPass:''
				},
                collapse: false,
                fullscreen: false,
                name: name,
                message: 0,
                platformName:'',
                userInfo:{},
                platform:{},
				updatePasswordVisible:false
            }
        },
        methods:{
            // 用户名下拉菜单选择事件
            handleCommand(command) {
                if(command == 'loginout'){
                    this.$UserInfo.clear();
                    this.$router.push('/login');
                }else if(command == "updatePassword"){
					this.updatePasswordVisible = true;
				}
            },
            // 侧边栏折叠
            collapseChage(){
                this.collapse = !this.collapse;
                bus.$emit('collapse', this.collapse);
            },
            // 全屏事件
            handleFullScreen(){
                let element = document.documentElement;
                if (this.fullscreen) {
                    if (document.exitFullscreen) {
                        document.exitFullscreen();
                    } else if (document.webkitCancelFullScreen) {
                        document.webkitCancelFullScreen();
                    } else if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen();
                    } else if (document.msExitFullscreen) {
                        document.msExitFullscreen();
                    }
                } else {
                    if (element.requestFullscreen) {
                        element.requestFullscreen();
                    } else if (element.webkitRequestFullScreen) {
                        element.webkitRequestFullScreen();
                    } else if (element.mozRequestFullScreen) {
                        element.mozRequestFullScreen();
                    } else if (element.msRequestFullscreen) {
                        // IE11
                        element.msRequestFullscreen();
                    }
                }
                this.fullscreen = !this.fullscreen;
            },
            setName(){
                let userInfo = this.$UserInfo.get();
               
                this.userInfo = eval('(' + userInfo + ')');
                let name = this.userInfo.name;
                this.name = name;

                let weChatInfo = this.$UserInfo.getWeChatInfo();
                this.platform = eval('(' + weChatInfo + ')');
                let platformName = this.platform.company;
                this.platformName = platformName;
            },
			confirmUpdate(){
				//updatePassword?userId=&password&newPassword
				this.$axios({
						method: "post",
						params: {
							password: this.form.password,
							newPassword: this.form.newPass,
						},
						url: this.$Api.BaseUrl + "/updatePassword"
					})
					.then(res => {
						this.Util.check(res, this.$message);
						if(res.status === 200) {
							this.$message.success(res.data.msg);
							this.updatePasswordVisible = false;
							this.$UserInfo.clear();
							this.$router.push('/login');
						}
					})
					.catch(error => {
						console.log(error);
					});
			}
        },
        mounted(){
            if(document.body.clientWidth < 1500){
                this.collapseChage();
            }
            this.setName();
        }
    }
</script>
<style scoped>
    .header {
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        color: #fff;
    }
    .collapse-btn{
        float: left;
        padding: 0px;
        cursor: pointer;
        line-height: 70px;
    }
    .header .logo{
        float: left;
        width:250px;
        line-height: 70px;
    }
    .header-right{
        float: right;
        padding-right: 20px;
    }
    .header-user-con{
        display: flex;
        height: 70px;
        align-items: center;
    }
    .btn-fullscreen{
        transform: rotate(45deg);
        margin-right: 5px;
        font-size: 24px;
    }
    .btn-bell, .btn-fullscreen{
        position: relative;
        width: 30px;
        height: 30px;
        text-align: center;
        border-radius: 15px;
        cursor: pointer;
    }
    .btn-bell-badge{
        position: absolute;
        right: 0;
        top: -2px;
        width: 8px;
        height: 8px;
        border-radius: 4px;
        background: #f56c6c;
        color: #fff;
    }
    .btn-bell .el-icon-bell{
        color: #fff;
    }
    .user-name{
        margin-left: 10px;
    }
    .user-avator{
        margin-left: 20px;
    }
    .user-avator img{
        display: block;
        width:30px;
        height:30px;
        border-radius: 50%;
    }
    .el-dropdown-link{
        color: #fff;
        cursor: pointer;
    }
    .el-dropdown-menu__item{
        text-align: center;
    }
</style>
