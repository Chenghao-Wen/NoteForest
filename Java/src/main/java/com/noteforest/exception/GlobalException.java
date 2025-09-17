package com.noteforest.exception;

import com.noteforest.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    // Catch custom exceptions
    @ExceptionHandler(Exception.class)
    public Result handleCustomException(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "An error occurred");
    }
}
