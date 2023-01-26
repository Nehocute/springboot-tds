package edu.spring.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class HelloController {
	
	@GetMapping(path={"/msg/{content}","/msg", "/msg/"})
	@ResponseBody
	public String action(@PathVariable(name="content", required=false) String content) {
		return "hello "+content;
	}
	
	@GetMapping("/view/{msg}")
	public String viewAction(@PathVariable String msg) {
		return "viewName";
	}
	
	@GetMapping("/yuumi/{msg}")
	public String yuumi(@PathVariable String msg) {
		return "yuumi";
	}
	
	@RequestMapping(path="/", method= {RequestMethod.POST, RequestMethod.GET})
	public String helloAction(HttpSession session) {
		return "index";
	}
	
	@PostMapping("/login")
	public RedirectView loginAction(HttpSession session, @ModelAttribute("login") String login) {
		session.setAttribute("user", login);
		return new RedirectView("/");
	}
	
	@GetMapping("/logout")
	public RedirectView logoutAction(HttpSession session) {
		//session.removeAttribute("user");
		session.invalidate();
		return new RedirectView("/");
	}
}
