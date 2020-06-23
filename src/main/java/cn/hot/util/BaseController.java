package cn.hot.util;

import cn.hot.admin.model.Customer;
import cn.hot.admin.model.Users;
import cn.hot.util.redis.SpringRedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class BaseController {
    public Logger log = LoggerFactory.getLogger(BaseController.class);

    private static List<String> autoTokens = new ArrayList<>();

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }


    public Users getUser() {
        String token = getRequest().getHeader("token");
        token = token.split(",")[0];
        log.info("token:" + token);
        Users user;
        try {
            user = (Users) SpringUtil.getBean(SpringRedisUtil.class).lGetIndex(token, 0);
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    public Customer getCustomer() {
        Customer customer;
        try {
            String openId = getOpenId();
            if (null != openId && !openId.trim().equals("")) {
                customer = (Customer) SpringUtil.getBean(SpringRedisUtil.class).get(openId);
                return customer;
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
        return null;
    }

    public String getOpenId() {
        String openId;
        try {
            String token = getRequest().getHeader("token");
            token = token.split(",")[0];
            log.info("token:" + token);
            openId = (String) SpringUtil.getBean(SpringRedisUtil.class).lGetIndex(token, 0);
        } catch (Exception e) {
            log.info("token转换openId失败：" + e.getMessage());
            openId = null;
        }
        return openId;
    }

    public static boolean containsToken(String token) {
        if (autoTokens.contains(token)) {
            return true;
        } else {
            autoTokens.add(token);
            return false;
        }
    }

}
