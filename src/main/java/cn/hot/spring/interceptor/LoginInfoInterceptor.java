package cn.hot.spring.interceptor;

import cn.hot.restApi.Result;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInfoInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    private void out(int status,String msg,HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        Result result = new Result();
        //result.setCode(""+status);
        result.setStatus(status);
        result.setMsg(msg);
        out.print(JSONObject.toJSON(result));
        out.flush();
        out.close();
    }
}
