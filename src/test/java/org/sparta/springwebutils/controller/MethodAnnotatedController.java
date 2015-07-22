package org.sparta.springwebutils.controller;

import org.sparta.springwebutils.annotation.ExternalEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/** 
 * @author Carlos Eduardo Endler Genz – Sparta Java Team 
 * 
 * History: 
 *    Mar 6, 2014 - Carlos Eduardo Endler Genz
 *  
 */ 
@Controller
public class MethodAnnotatedController {
	
	@ExternalEntryPoint(name="new_name", typeBlacklist=String.class, nameBlacklist="outToo")
	@RequestMapping(value="/methodAnnotatedController/testBlacklists", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView testBlacklists(String out, Integer in, @RequestParam(defaultValue="true", required=false) Boolean inToo, Long outToo) {
		return new ModelAndView();
	}
	
	@RequestMapping("/methodAnnotatedController/testOut")
	public ModelAndView testOut(String in) {
		return new ModelAndView();
	}
}
