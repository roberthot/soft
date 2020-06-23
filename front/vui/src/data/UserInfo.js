import cookie from './Cookie';
import { Base64 } from 'js-base64'
import router from '@/router'
import Util from './Util'

let UserInfo = {
    get:function(){
        var isExistsToken = cookie.check("token");
        if(isExistsToken){
            return Base64.decode(cookie.get('userInfo'))
        }else{
            router.push("/login");
        }
    },

    set:function(name,obj){
        cookie.set(name,obj.token,24*60);
		cookie.set("userInfo", obj.data.userInfo,24*60);
		cookie.set("weChatInfo", obj.data.weChatSetting,24*60);
		sessionStorage.clear();
        sessionStorage.setItem("userInfo", obj.data.userInfo);
        sessionStorage.setItem("weChatInfo", obj.data.weChatSetting);
    },
    getToken:function(){
        var isExistsToken = cookie.check("token");
        if(isExistsToken){
            return cookie.get("token")+","+Util.key();
        }else{
            router.push("/login");
        }
    },
    getWeChatInfo(){
        return Base64.decode(cookie.get('weChatInfo'))
    },
	clear(){
		cookie.del('token');
		cookie.del('weChatInfo');
		cookie.del('userInfo');
		sessionStorage.removeItem("userInfo")
		sessionStorage.removeItem("weChatInfo")
	}
}

export default UserInfo;