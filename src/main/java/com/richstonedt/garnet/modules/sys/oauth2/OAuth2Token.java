/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-20 13:22
 * @since garnet-core-be-fe 1.0.0
 */
public class OAuth2Token implements AuthenticationToken {

    /**
     * The Token.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String token;

    /**
     * Instantiates a new O auth 2 token.
     *
     * @param token the token
     * @since garnet-core-be-fe 1.0.0
     */
    public OAuth2Token(String token) {
        this.token = token;
    }

    /**
     * get  Principal
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public String getPrincipal() {
        return token;
    }

    /**
     * get  Credentials.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public Object getCredentials() {
        return token;
    }

}
