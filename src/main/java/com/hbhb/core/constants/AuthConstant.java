package com.hbhb.core.constants;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
public enum AuthConstant {
    /**
     * 超管标志
     */
    SUPER_ADMIN("super_admin"),
    /**
     * 管理员标志
     */
    ADMIN("admin"),
    /**
     * 完全权限标志
     */
    ALL_PERMISSION("*:*:*"),
    /**
     * 前端Layout组件标识
     */
    LAYOUT("Layout"),
    /**
     * 是否菜单外链-是
     */
    YES_FRAME("1"),
    /**
     * 是否菜单外链-否
     */
    NO_FRAME("0"),
    /**
     * 验证码 key 前缀
     */
    CAPTCHA_CODE_KEY("captcha_codes:"),
    /**
     * 验证码有效期（分钟）
     */
    CAPTCHA_EXPIRATION("1"),
    /**
     * JWT存储权限前缀
     */
    AUTHORITY_PREFIX("ROLE_"),
    /**
     * JWT存储权限属性
     */
    AUTHORITY_CLAIM_NAME("authorities"),
    /**
     * 认证信息Http请求头
     */
    JWT_TOKEN_HEADER("Authorization"),
    /**
     * JWT令牌前缀
     */
    JWT_TOKEN_PREFIX("Bearer"),
    /**
     * JWT载体key
     */
    JWT_PAYLOAD_KEY("payload"),
    /**
     * redis缓存权限规则key
     */
    RESOURCE_ROLES_KEY("auth:authorities:rule"),

    TOKEN_BLACKLIST_PREFIX("auth:token:blacklist:"),

    CLIENT_DETAILS_FIELDS("client_id, CONCAT('{noop}', client_secret) as client_secret, " +
            "resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, " +
            "access_token_validity, refresh_token_validity, additional_information, autoapprove"),

    BASE_CLIENT_DETAILS_SQL("select " + CLIENT_DETAILS_FIELDS.value() + " from oauth_client_details"),

    FIND_CLIENT_DETAILS_SQL(BASE_CLIENT_DETAILS_SQL.value() + " order by client_id"),

    SELECT_CLIENT_DETAILS_SQL(BASE_CLIENT_DETAILS_SQL.value() + " where client_id = ?"),

    BCRYPT("{bcrypt}"),

    JWT_USER_ID_KEY("id"),

    JWT_CLIENT_ID_KEY("client_id"),
    ;

    private final String value;

    AuthConstant(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
