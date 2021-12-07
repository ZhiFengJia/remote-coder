package com.jzf.remote.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class indexController {

    @GetMapping
	public String index() {
		return "/RemoteDebug.html";
	}
    
}
