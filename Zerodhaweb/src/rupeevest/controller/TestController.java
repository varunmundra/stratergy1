package rupeevest.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zerodhatech.kiteconnect.KiteConnect;

import kiteconnect.MOdel.InstruDetail;
import kiteconnect.Model.Login_module;
import sessionFactory.C3poDataSource;
import sessionFactory.HIbernateSession;





@Controller
@RequestMapping("/zerodha")
public class TestController {
	
	
	static KiteConnect kiteConnect=null;
	
	
	@RequestMapping("/login")
	public ModelAndView login()
	{
		Connection con = null;
		try
		{
			con = C3poDataSource.getConnection();
			
			 PreparedStatement stmt3 = con.prepareStatement("select * from Login_module");
	         ResultSet rs_2 = stmt3.executeQuery();
			
			    Login_module obj =null;   
			    
			    while(rs_2.next())
			    {
			    	obj = new Login_module();
			    	obj.setAccess_token(rs_2.getString("access_token"));
			    	obj.setPublic_token(rs_2.getString("public_token"));
			    }
			    
			    
			    
			 if(obj!=null)   
			 {   
//				 req.setAttribute("request_token", obj.getAccess_token());
//				 req.setAttribute("login", "yes");
				 ModelAndView mv = new ModelAndView("redirect:/function/dashboard");
				 mv.addObject("logic", "yes");
				 mv.addObject("access_token", obj.getAccess_token());
				 mv.addObject("public_token", obj.getPublic_token());
				 
				 System.out.println("=-=-=-=-=-==-=-coming here-=-=-=-=-=-");
				 
				 return mv;
			 }
			
		     kiteConnect = new KiteConnect("t8hgzuuqo9fke9j2");
	
	
	//	      Set userId
		     kiteConnect.setUserId("PS3288");
	
	//	      Get login url
		     String url = kiteConnect.getLoginURL();
		     
	//	     System.out.println(url);
	//	     return "redirect:"+url;
		     return new ModelAndView("redirect:"+url);
	     
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (con!=null)
		    	{
	    		con.close();
		    	}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	     
		
	}
	
	
	
	 

}
