package com.hbhb.core.enums;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum ResultCode {

    SUCCESS("00000", "success"),

    API_GATEWAY_ERROR("10000", "网络繁忙，请稍后再试"),
    PARAM_ERROR("10001", "参数错误"),
    BUSINESS_ERROR("10002", "业务异常"),
    RPC_ERROR("10003", "远程调用出问题啦！"),


    FORBIDDEN("10403", "您的权限不足，无法访问该资源"),
    NOT_FOUND("10404", "哎呀，无法找到这个资源啦"),
    METHOD_NOT_ALLOWED("10405", "请换个姿势操作试试"),
    UNSUPPORTED_MEDIA_TYPE("10415", "不支持该媒体类型"),
    TRAFFIC_LIMITING("10429", "网络拥挤，等会再来吧"),

    EXCEPTION("99999", "服务器开小差，请稍后再试"),

    FAILED("FFFFFF", "empty data here"),
    ;

    private final String code;

    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }
}
