package com.stt.spring.boot.thymeleaf.controller;

import com.google.common.collect.Lists;
import com.stt.spring.boot.thymeleaf.entity.PersonBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2019/6/26.
 */
@Controller
@RequestMapping(value = "thymeleaf")
public class IndexController {

	@RequestMapping(value = "index",method = RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("person",
				PersonBean.builder().name("stt").age(19).build());

		model.addAttribute("people", Lists.newArrayList(
				PersonBean.builder().name("people1").age(10).build(),
				PersonBean.builder().name("people2").age(12).build(),
				PersonBean.builder().name("people3").age(13).build()
		));
		return "index";
	}
}
