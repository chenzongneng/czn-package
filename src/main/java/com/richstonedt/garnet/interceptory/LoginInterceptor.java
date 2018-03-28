package com.richstonedt.garnet.interceptory;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;

import com.richstonedt.garnet.model.Token;
import com.richstonedt.garnet.model.UserCredential;
import com.richstonedt.garnet.model.criteria.TokenCriteria;
import com.richstonedt.garnet.service.RouterGroupService;
import com.richstonedt.garnet.service.TokenService;
import com.richstonedt.garnet.service.UserCredentialService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 登录拦截器,样例 https://www.jianshu.com/p/97362fdf039e 可以按照此博客进行改造
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RouterGroupService routerGroupService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //如果是映射到方法，验证登录
        if (handler instanceof HandlerMethod) {

            //判断接口是否需要登录
            LoginRequired loginRequired = findAnnotation((HandlerMethod) handler, LoginRequired.class);
            //没有声明需要权限,或者声明不验证权限
            if(loginRequired == null){
                return true;
            } else{
                //有加token验证注解的情况
                String tokenWithAppCode = request.getHeader("token");
                if (StringUtils.isEmpty(tokenWithAppCode)) {
                    tokenWithAppCode = request.getHeader("accessToken");
                }
                if(StringUtils.isEmpty(tokenWithAppCode)){
                    tokenWithAppCode = request.getParameter("token");
                }

                //如果token存在
                if(!StringUtils.isEmpty(tokenWithAppCode) && !"null".equals(tokenWithAppCode)){
                    Map<String, Claim> tokenParams = null;
                    //从前端传来的token中获取真正的token。关键步骤！！！
                    String[] tokenParams1 = tokenWithAppCode.split("#");
                    //真正的token
                    String token = tokenParams1[0];

                    //从token中获取userName
                    String userName = JWT.decode(token).getAudience().get(0);
                    //通过userName获取密码
                    UserCredential userCredential = userCredentialService.getCredentialByUserName(userName);
                    if (ObjectUtils.isEmpty(userCredential)) {
                        return userNotExist(request, response);
                    }

                    //根据password解密
                    String password = userCredential.getCredential();
                    try {
                        tokenParams = JwtToken.verifyToken(token, password);
                    } catch (Exception e) {
                        //无法解密，token不正确
                        return tokenError(request, response);
                    }

                    //根据userName从数据库中拿出token
                    TokenCriteria tokenCriteria = new TokenCriteria();
                    tokenCriteria.createCriteria().andUserNameEqualTo(userName);
                    Token token1 = tokenService.selectSingleByCriteria(tokenCriteria);
                    if (ObjectUtils.isEmpty(token1)) {
                        return haveNotToken(request, response);
                    }

                    //将token和数据库中的token对比
                    String tokenFromDB = token1.getToken();
                    if (!token.equals(tokenFromDB)) {
                        return tokenError(request, response);
                    }

                    //验证token是否过期
                    Long expiredTime = token1.getExpireTime();
                    if (System.currentTimeMillis() > expiredTime) {
                        return tokenExpired(request, response);
                    }

                    return true;
                } else{
                    return haveNotToken(request, response);  //token为空
                }
            }
        } else{
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
    protected void responseOutWithJson(HttpServletResponse response, Object responseObject) {
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

    private boolean haveNotToken(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessage("请先登录");
        loginMessage.setLoginStatus("false");
        loginMessage.setCode(401);
        responseOutWithJson(response, loginMessage);
//        response.sendRedirect("/garnet/login.html");
//        request.getRequestDispatcher("/garnet/login.html").forward(request, response);
        return false;
    }

    private boolean tokenExpired(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessage("登录失效，请重新登录");
        loginMessage.setLoginStatus("false");
        loginMessage.setCode(401);
        responseOutWithJson(response, loginMessage);
        return false;
    }

    private boolean tokenError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessage("TOKEN验证错误，请重新登录或确保您有权限进行此操作");
        loginMessage.setLoginStatus("false");
        loginMessage.setCode(403);
        responseOutWithJson(response, loginMessage);
        return false;
    }

    private boolean userNotExist(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMessage("用户不存在，请重新登录");
        loginMessage.setLoginStatus("false");
        loginMessage.setCode(401);
        responseOutWithJson(response, loginMessage);
        return false;
    }



}