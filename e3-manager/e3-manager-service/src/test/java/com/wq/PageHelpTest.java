package com.wq;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wq.mapper.TbItemMapper;
import com.wq.pojo.TbItem;
import com.wq.pojo.TbItemExample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class PageHelpTest {

    public void testPageHelper() throws Exception{

        //初始化spring容器，获得tbItemMapper对象
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        TbItemMapper tbItemMapper = (TbItemMapper) context.getBean("tbItemMapper");

        //设置分页查询信息，要在查询之前调用的
        PageHelper.startPage(1,10);

        //根据example查询list
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);


        //取分页信息
        PageInfo info = new PageInfo(list);
        System.out.println(info.getPageNum());
        System.out.println(info.getPages());
        System.out.println(info.getTotal());


    }
}
