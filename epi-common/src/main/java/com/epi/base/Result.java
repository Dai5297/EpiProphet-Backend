package com.epi.base;

import com.epi.constant.HttpStatus;
import com.epi.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 统一返回结果类
 * @author dai
 * @param <T>
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    /**
     * 响应返回编码
     */
    @ApiModelProperty(value = "状态码")
    private int code;

    /**
     * 响应返回信息
     */
    @ApiModelProperty(value = "状态信息")
    private String msg;

    /**
     * 返回结果
     */
    @ApiModelProperty(value = "返回结果")
    private T data;

    public Result<T> success(){
        Result<T> result = new Result<>();
        result.code = HttpStatus.SUCCESS;
        result.msg = "操作成功";
        result.data = null;
        return result;
    }

    public static  Result success(Object data){
        Result result = new Result<>();
        result.code = HttpStatus.SUCCESS;
        result.msg = "操作成功";
        result.data = data;
        return result;
    }

    public static Result success(String msg,Object data){
        Result result = new Result<>();
        result.code = HttpStatus.SUCCESS;
        result.msg = msg;
        result.data = data;
        return result;
    }

    public static Result error(){
        Result result = new Result<>();
        result.code = HttpStatus.ERROR;
        result.msg = "操作失败";
        result.data = null;
        return result;
    }

    public static Result error(int code) {
        Result result = new Result<>();
        result.code = code;
        result.msg = "操作失败";
        result.data = null;
        return result;
    }

    public static Result error(String msg){
        Result result = new Result<>();
        result.code = HttpStatus.ERROR;
        result.msg = msg;
        result.data = null;
        return result;
    }

    public static Result error(int code,String msg){
        Result result = new Result<>();
        result.code = code;
        result.msg = msg;
        result.data = null;
        return result;
    }

    public static Result error(int code,String msg,Object data){
        Result result = new Result<>();
        result.code = code;
        result.msg = msg;
        result.data = data;
        return result;
    }
}
