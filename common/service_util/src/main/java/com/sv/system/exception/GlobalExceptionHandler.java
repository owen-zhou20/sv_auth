package com.sv.system.exception;

import com.sv.common.result.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Global exception
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Global exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.fail().message("Execute Global exception");
    }

    // 2. Arithmetic exception
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.fail().message("Execute Arithmetic exception");
    }

    // 3. Sv exception
    @ExceptionHandler(SvException.class)
    @ResponseBody
    public R error(SvException e){
        e.printStackTrace();
        return R.fail().code(e.getCode()).message( e.getMsg());
    }

    /**
     * spring security auth
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public R error(AccessDeniedException e) throws AccessDeniedException {
        return R.fail().message("You need authorization to do this action! Please contact the administrator!");
    }

}
