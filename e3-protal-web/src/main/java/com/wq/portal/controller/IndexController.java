package com.wq.portal.controller;

import com.wq.content.service.ContentService;
import com.wq.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Value("${CONTENT_LUNBO_ID}")
    private Long CONTENT_LUNBO_ID;
    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String toIndex(Model model){
        List<TbContent> tbContents = contentService.getContentListByCid(CONTENT_LUNBO_ID);
        model.addAttribute("ad1List",tbContents);
        return "index";
    }
}
