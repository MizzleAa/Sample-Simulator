package com.mizzle.simulator.advice;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class ViewControllerAdvice implements ErrorController{
    
    @ExceptionHandler(Throwable.class)
    @RequestMapping("/error")
    public String error(Exception e){
        return "error/error";
    }
}