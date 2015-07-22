package org.sparta.springwebutils.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sparta.springwebutils.entity.TestCyclic;
import org.sparta.springwebutils.entity.TestEntity;
import org.sparta.springwebutils.entity.TestIgnoreInsideObj;
import org.sparta.springwebutils.entity.TestIgnoreType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/** 
 * 
 * 
 * @author danieldiehl - Sparta Java Team 
 * 
 * History: 
 *    Mar 6, 2014 - danieldiehl
 *  
 */
@Controller
public class NotAnnotatedController {
	
	@RequestMapping(value={"/testArraysStringRP", "/testArraysStringRP2"})
	public ModelAndView testArraysString(@RequestParam("requestParamStrArray") String[] strArrayAnnotated,
								   String[] strArrayNotAnnotated,
								   @RequestParam(value="requestParamStrList", defaultValue="default") List<String> strListAnnotated,
								   List<String> strListNotAnnotated,
								   @RequestParam(value="requestParamStrSet", required=false) Set<String> strSetAnnotated,
								   Set<String> strSetNotAnnotated,
								   @RequestParam(value="requestParamStrCollection", required = true) Collection<String> strCollectionAnnotated,
								   Collection<String> strCollectionNotAnnotated,
								   Map<String, String> ignorable) {
		return new ModelAndView();
	}
	
	@RequestMapping(value={"/testArraysIntRP", "/testArraysIntRP2"})
	public ModelAndView testArraysInt(@RequestParam("requestParamIntArray") int[] intArrayAnnotated,
								   	  int[] intArrayNotAnnotated,
								   	  @RequestParam("requestParamIntegerArray") Integer[] integerArrayAnnotated,
								   	  Integer[] integerArrayNotAnnotated,
								   	  HttpServletRequest requestIgnore) {
		return new ModelAndView();
	}
	
	@RequestMapping(value={"/testArraysLongRP"})
	public ModelAndView testArraysLong(@RequestParam("requestParamLongArray") long[] intArrayAnnotated,
								   	  long[] longArrayNotAnnotated,
								   	  @RequestParam("requestParamLongObjArray") Long[] longObjArrayAnnotated,
								   	  Long[] longObjArrayNotAnnotated,
								   	  HttpServletResponse responseIgnore) {
		return new ModelAndView();
	}
	
	@RequestMapping(value={"/testArraysDoubleRP"})
	public ModelAndView testArraysDouble(@RequestParam("requestParamDoubleArray") double[] doubleArrayAnnotated,
								   	  double[] doubleArrayNotAnnotated,
								   	  @RequestParam("requestParamDoubleObjArray") Double[] doubleObjArrayAnnotated,
								   	  Double[] doubleObjArrayNotAnnotated,
								   	  HttpSession responseIgnore, 
								   	  BindingResult brIgnore) {
		return new ModelAndView();
	}
	
	@RequestMapping("/primitives")
	public ModelAndView testPrimitives(String str, 
									   int pInt, long pLong, double pDouble, 
									   Integer pIntObj, Long pLongObj, Double pDoubleObj, Model model) {
		return new ModelAndView();
	}
	
	@RequestMapping("/testObject")
	public ModelAndView testObject (TestEntity entity) {
		return new ModelAndView();
	}
	
	@RequestMapping("/testCyclicReference")
	public ModelAndView testCyclicReference (TestCyclic testCyclic) {
		return new ModelAndView();
	} 
	
	@RequestMapping("/testIgnore")
	public ModelAndView testIgnoreObj (TestIgnoreType typeToIgnore, String ignoreByName, String str) {
		return new ModelAndView();
	}
	
	@RequestMapping("/testIgnoreInside")
	public ModelAndView testIgnoreInsideObj (TestIgnoreInsideObj inside) {
		return new ModelAndView();
	}	
	
	@RequestMapping("/testEmptyIgnore")
	public ModelAndView testEmptyIgnore (HttpServletRequest request) {
		return new ModelAndView();
	}
}
