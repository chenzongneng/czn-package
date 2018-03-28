package com.richstonedt.garnet.interceptory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.jdbc.support.nativejdbc.OracleJdbc4NativeJdbcExtractor;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class JwtToken {

    /**
     * 生成token
     * @return
     * @throws Exception
     */
    public static String createToken(String userName, String password,String appCode, Long currentTime) throws Exception {
        //公共密钥，保存在服务端，客户端是不会知道密钥的，以防被攻击
        String secret = password;

        //签发时间
        Date iatDate = new Date();
        //过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 30); // 30分钟过期
        Date expiresDate = nowTime.getTime();
        //当前时间的毫秒数
        String currentTime1 = String.valueOf(currentTime);

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)  //header
                .withClaim("userName", userName)//payload
                .withClaim("appCode", appCode)
//                .withClaim("currentTime", currentTime1)
                //.withExpiresAt(expiresDate) //设置过期时间，过期时间大于签发时间
//                .withIssuedAt(iatDate) //设置签发时间
                .withAudience(userName)
                .sign(Algorithm.HMAC256(secret)); //加密
        return token;
    }

    /**
     * 验证token
     * @param token
     * @param password
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token, String password) throws  Exception {
        //公共密钥，保存在服务端，客户端是不会知道密钥的，以防被攻击
        String secret = password;

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT jwt =  verifier.verify(token);
//        try {
//            jwt = verifier.verify(token);
//        } catch (Exception e) {
//            throw new RuntimeException("登录凭证失效，请重新登录");
//        }

        return  jwt.getClaims();
    }
}
