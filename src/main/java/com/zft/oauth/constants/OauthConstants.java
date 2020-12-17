package com.zft.oauth.constants;

/**
 * Description: 常量
 * @author  fengtan.zhang
 * @date    2020/12/11 11:33
 */
public final class OauthConstants {

    // 客户端唯一标识
    public static final String CLIENT_ID = "";
    // Note that: 客户端密钥需要谨慎管理, 从github copy
    public static final String CLIENT_SECRET = "";
    // 从github获取access_token
    public static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    // 从github获取授权用户信息
    public static final String USER_URL = "https://api.github.com/user";
}
