package com.changgou.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dylan Guo
 * @date 9/16/2020 15:54
 * @description TODO
 */
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 异常处理
     *
     * @param e 异常
     * @return 异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
