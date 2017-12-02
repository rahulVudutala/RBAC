/**
 * 
 */
package com.masters.project.rbac.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rahul.vudutala
 *
 */
@RestController
public class RestHelloController {
	@RequestMapping("/")
	public String index() {
		return "Hello World Spring !";
	}
}
