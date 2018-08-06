package com.wq.search.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object hnadler, Exception ex) {

        //捕获异常信息
        ex.printStackTrace();

        //打印日志
        logger.info("---------------------哈哈---------------");
        logger.error("出异常了",ex);
        //发送邮件 用Jmail

        //gfn
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message","系统发生异常，请稍后重试");
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
