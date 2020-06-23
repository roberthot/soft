package cn.hot.admin.Controller;

import cn.hot.admin.model.Users;
import cn.hot.restApi.Result;
import cn.hot.admin.service.UserService;
import cn.hot.util.Constants;
import cn.hot.util.SpringUtil;
import cn.hot.util.UUID;
import cn.hot.util.encrypt.Base64;
import cn.hot.util.redis.SpringRedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {


    @Resource
    UserService userService;

    @RequestMapping("/login")
    public Result login(Users user) {
        Result result = userService.login(user);
        if (result.getStatus() == 1) {
            Users user1 = (Users) result.getData();
            result.setToken(UUID.uuid());
            SpringUtil.getBean(SpringRedisUtil.class).lSet(result.getToken(), result.getData(), Constants.LOGIN_TIMEOUT);
            Map<String, Object> data = new HashMap<>();
            data.put("userInfo", Base64.encode(JSON.toJSONString(result.getData())));
            result.setData(data);
        }
        return result;
    }

}
