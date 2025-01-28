package com.mm.ai.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController
{
	@RequestMapping("/welcome")
	public String locale()
	{
		return "welcome";
	}

}
