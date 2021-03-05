package com.elysion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GameController {

	@RequestMapping(value = "/game")
	public String init(Model model) {

		model.addAttribute("title", "Elysion | GAME");

		return "game.html";
	}

}
