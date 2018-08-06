package com.wq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InitSpringContext {

    @Test
    public void initSpringContexts() throws Exception{

        //加载spring配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        System.out.println("服务已经启动-------------");
        System.in.read();
        System.out.println("服务已经关闭------------");
    }
}
