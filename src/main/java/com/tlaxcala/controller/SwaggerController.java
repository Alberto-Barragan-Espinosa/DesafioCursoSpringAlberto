package com.tlaxcala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {
	@GetMapping({"","/index","/"})
	public String index(Model model){	
		//System.out.println(enviarCorreo.enviarCorreoPagoExitosoVicomm("albertobe8@gmail.com"));
	return "redirect:http://localhost:8080/swagger-ui/index.html?continue#/login-controller/login";
	}

}
