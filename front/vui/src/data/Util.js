/** 生成加密信息，在前端请求服务器时调用 **/
import { Base64 } from 'js-base64'
let Util = {
    key:function () {
        let initInfo = "vertical";
        let time     = (new Date()).getTime();
        let info = initInfo + time;
        info = Base64.encode(info);
        let result = "";
        for(let i=0;i<info.length;i++){
            let code = info.charCodeAt(i);
            code = code + 8;
            result += String.fromCharCode(code);
        }
        return Base64.encode(result);
    },

    check:function(res,callback){
        if(res.data.status != 200){
            callback(res.data.msg);
        }
    }
};

export default Util