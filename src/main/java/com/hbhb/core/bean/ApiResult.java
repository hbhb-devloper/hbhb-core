package com.hbhb.core.bean;

import com.hbhb.core.enums.ResultCode;
import com.hbhb.core.utils.DateUtil;

import lombok.Data;

/**
 * @author dxk
 */
@Data
@SuppressWarnings(value = {"rawtypes"})
public class ApiResult<T> {
    private String code;
    private String message;
    private T data;
    private String timestamp = DateUtil.getCurrentDate();

    public static <T> ApiResult<T> success() {
        return success(null);
    }

    public static <T> ApiResult<T> success(T data) {
        return result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), data);
    }

    public static <T> ApiResult<T> error(String code, String message) {
        return result(code, message, null);
    }

    public static ApiResult error(ResultCode resultCode) {
        return result(resultCode.code(), resultCode.msg(), null);
    }

    private static <T> ApiResult<T> result(String code, String message, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
