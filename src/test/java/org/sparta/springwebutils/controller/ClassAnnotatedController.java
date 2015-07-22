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
@ExternalEntryPoint(typeBlacklist=Object.class, nameBlacklist={"out", "out_two"})
public class ClassAnnotatedController {
	
	@RequestMapping(value="/classAnnotatedController/testTypeBlacklist", method=RequestMethod.POST)
	public ModelAndView testTypeBlacklist(String inOne, Integer inTwo, Object fileOut) {
		return new ModelAndView();
	}
	
	@RequestMapping(value="/classAnnotatedController/testNameBlacklist", method=RequestMethod.GET)
	public ModelAndView testNameBlacklist(String out, Integer in, Boolean inToo, @RequestParam("out_two") Long outToo) {
		return new ModelAndView();
	}
}
