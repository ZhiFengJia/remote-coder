package com.jzf.remote.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class indexController {

    @GetMapping
	public String index() {
		return "/WebIDE.html";
	}
    
}
