package rupeevest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zerodhatech.kiteconnect.KiteConnect;

@Controller
public class HelloWorldController {

	
	@RequestMapping("/showForm")
	public String showform()
	{
		return "hellowworld-form";
	}
	
	@RequestMapping("/processForm")
	public String processForm()
	{
		return "process-Form";
	}
	
	@RequestMapping("/login2")
	public String login()
	{
		KiteConnect kiteConnect = new KiteConnect("t8hgzuuqo9fke9j2");


//	      Set userId
	     kiteConnect.setUserId("PS3288");

//	      Get login url
	     String url = kiteConnect.getLoginURL();
	     
	     System.out.println(url);
	     return "hellowworld-form";
		
	}
	
	@RequestMapping("/processFormTwo")
//	public String LetsShout(HttpServletRequest req , Model model)
	public String LetsShout(@RequestParam("StudentName") String name , Model model)
	{
//		String name = req.getParameter("StudentName"); 
		
		name = name.toUpperCase();
		
		model.addAttribute("message", name);
		return "process-Form";
	}
	
}