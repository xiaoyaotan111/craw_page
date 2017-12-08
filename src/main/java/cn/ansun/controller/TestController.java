package cn.ansun.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping(value = "/find", produces = "application/json; charset=utf-8")
    public @ResponseBody String find() throws Exception {
       
		System.out.println("666");
		return "fff";
    }
	
	
}
