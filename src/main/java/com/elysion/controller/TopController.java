package com.elysion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopController {

	@RequestMapping(value = "/")
	public String init(Model model) {

		model.addAttribute("title", "Elysion | TOP");

		return "top.html";
	}

}
