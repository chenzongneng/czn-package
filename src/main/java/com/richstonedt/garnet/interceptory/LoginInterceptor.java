package com.richstonedt.garnet.interceptory;

import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;

/**
 * 登录拦截器,样例 https://www.jianshu.com/p/97362fdf039e 可以按照此博客进行改造
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            LoginRequired loginRequired = findAnnotation((HandlerMethod) handler, LoginRequired.class);
            //没有声明需要权限,或者声明不验证权限

            if(loginRequired==null){
                return true;
            }else{
                String token=request.getHeader("token");
                if(StringUtils.isEmpty(token)){
                    token=request.getParameter("token");
                }
                //在这里实现自己的权限验证逻辑
                if(!StringUtils.isEmpty(token)){//如果验证成功返回true（这里直接写false来模拟验证失败的处理）
                    return true;
                }else{//如果验证失败
                    response.setContentType("application/json; charset=utf-8");
                    LoginMessage loginMessage = new LoginMessage();
                    loginMessage.setMessage("请先登录");
                    responseOutWithJson(response,
                            loginMessage);
                    return false;
                }
            }
        }else{
            return true;
        }

    }

    private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) return annotation;
        return handler.getMethodAnnotation(annotationType);
    }

    /**
     * 以JSON格式输出
     * @param response
     */
    protected void responseOutWithJson(HttpServletResponse response,
                                       Object responseObject) {
        //将实体对象转换为JSON Object转换
        JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}