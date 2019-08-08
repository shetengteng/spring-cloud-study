package com.stt.service.user.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.stt.myshop.commons.domain.TbUser;
import com.stt.service.user.api.TbUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "user")
public class TbUserController {

    @Reference(version = "${services.versions.user.v1}")
    private TbUserService tbUserService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        List<TbUser> tbUsers = tbUserService.selectAll();
        model.addAttribute("tbUsers", tbUsers);
        return "user/list";
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    public String page(
//            int pageNum,
//            int pageSize,
            Model model){
        PageInfo<TbUser> pageInfo = tbUserService.page(1,10);
        model.addAttribute("pageInfo", pageInfo);
        return "user/page";
    }
}