package com.stt.service.content.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.stt.myshop.commons.domain.TbContent;
import com.stt.service.content.api.TbContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "content")
public class TbContentController {

    @Reference(version = "${services.versions.content.v1}")
    private TbContentService tbContentService;


    @GetMapping(value = "page")
    public String page(
//            int pageNum,
//            int pageSize,
            Model model){
        PageInfo<TbContent> pageInfo = tbContentService.page(1,10);
        model.addAttribute("pageInfo", pageInfo);
        return "content/page";
    }
}