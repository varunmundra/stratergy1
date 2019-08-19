package rupeevest.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.opencsv.CSVReader;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Margin;
import com.zerodhatech.models.User;

import kiteconnect.Model.InstruDetail;
import kiteconnect.Model.Stock;
import kiteconnect.Model.Examples;
import kiteconnect.Model.Login_module;
import sessionFactory.C3poDataSource;
import sessionFactory.HIbernateSession;

@Controller
@RequestMapping("/function")
public class FunctionController {
	
	@RequestMapping("/dashboard")
	public ModelAndView dashboard(HttpServletRequest req , Model model )
	{
		Connection con = null;
		String login_tmp = null;
		
		 
		
	    String req_token =  req.getParameter("request_token");
	    login_tmp =  req.getParameter("logic");
	    
	  
	    
	  
	    model.addAttribute("req_token", req_token);
	    
	    try {
//	    	User user =  TestController.kiteConnect.generateSession(req_token, "lmp6p6qkkwzxv5jwscajqbpjp5705b9j");
	    	
	    	
	    	if(login_tmp!= null && (login_tmp=="yes" || login_tmp.equals("yes")) )
	    	{
	    		 
	    		  String access_tkn = req.getParameter("access_token").toString();
	    		  String public_tkn = req.getParameter("public_token").toString();
	    		  TestController.kiteConnect = new KiteConnect("t8hgzuuqo9fke9j2");
	    		  TestController.kiteConnect.setAccessToken(access_tkn);    
	    		  TestController.kiteConnect.setPublicToken(public_tkn);
	    		Examples examples = new Examples();
	            examples.getProfile(TestController.kiteConnect);
	    	}
	    	else
	    	{
//	    		  TestController.kiteConnect = new KiteConnect("t8hgzuuqo9fke9j2"); 
	    		User user =  TestController.kiteConnect.generateSession(req_token, "lmp6p6qkkwzxv5jwscajqbpjp5705b9j");
	    		
	    		TestController.kiteConnect.setAccessToken(user.accessToken);
	    		TestController.kiteConnect.setPublicToken(user.publicToken);
	    		con = C3poDataSource.getConnection();
//				Login_module nw = new Login_module();
//			
//			    nw.setAccess_token(user.accessToken);
//				nw.setPublic_token(user.publicToken);
									
	    		  PreparedStatement stmt2=con.prepareStatement("insert into login_module(access_token,public_token) values ('"+user.accessToken+"','"+user.publicToken+"')");
 			      stmt2.executeUpdate();
 			      stmt2.close();
				
//				TestController.kiteConnect.setAccessToken(user.accessToken);
//				TestController.kiteConnect.setPublicToken(user.publicToken);
 			     Examples examples = new Examples();
 	            examples.getProfile(TestController.kiteConnect);
	    		
	    	}
	    	
            
//          return new ModelAndView("redirect:/function/dashboard");          
	    	
	    	
			
			
			
			 
			
			
			
			
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KiteException e) {
			
			e.printStackTrace();
			
			if(e.toString()=="com.zerodhatech.kiteconnect.kitehttp.exceptions.TokenException")
			{
				System.out.println("ERROR-----MEssage-=-=-=-=-=-=-=-=====");
				
				 PreparedStatement stmt3;
				try {
					System.out.println("Delete --------");
					con = C3poDataSource.getConnection();
					stmt3 = con.prepareStatement("delete from login_module");
					stmt3.executeUpdate();
					
					 System.out.println("Deleted completely --------");
					stmt3.close();
					con.close();
					return new ModelAndView("redirect:/zerodha/login");
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			      
				
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally
	    {
	    	try
	    	{   if (con!=null)
		    	{
	    		con.close();
		    	}
	    		
	    	}
	    	catch (Exception e) {
				e.printStackTrace();
			}
	    }

//        
	     
//		return "dashboard";
	    return new ModelAndView("dashboard");
	}
	
	@RequestMapping("/stratergy1")
	public ModelAndView stratergy1 (HttpServletRequest req) throws KiteException
	{

        Connection con = null;
        StringBuilder query_bldr = new StringBuilder("("); 
        try
        {
        ArrayList<Long> tokens = new ArrayList<Long>();
        FileReader filereader = new FileReader(new File("/Users/varun/Desktop/only_cash.csv"));
        CSVReader csvReader = new CSVReader(filereader); 
	    String[] nextRecord; 
	    
	    
	    while ((nextRecord = csvReader.readNext()) != null) 
	    {
	    	tokens.add(Long.parseLong(nextRecord[0]));
	    	Stock.stock_list.put(Long.parseLong(nextRecord[0]),new Stock(Long.parseLong(nextRecord[0])));
	    	 
	    	 query_bldr.append(nextRecord[0]) ;
	    	 query_bldr.append(",");
	    	
	    }
	    
	    StringBuilder builder = new StringBuilder();
	    
	    
	    for (Long key : Stock.stock_list.keySet()) {
//	    for( int i = 0 ; i < Stock.stock_list.size(); i++ ) 
	    
	        builder.append(Stock.stock_list.get(key).getInstrument_token()+",");
	    }
	    
	    String stmt_setter = "select * from save_avg where instrument_token in (" 
	               + builder.deleteCharAt( builder.length() -1 ).toString() + ")";

	    con = C3poDataSource.getConnection();
	    
	    PreparedStatement stmt_data_setter = con.prepareStatement(stmt_setter);
	    
	    ResultSet rs_3 = stmt_data_setter.executeQuery();
	    Stock temp_obj;
	    while(rs_3.next())
	    {
	    	temp_obj =  Stock.stock_list.get(rs_3.getLong("instrument_token"));
	    	
	    	if(rs_3.getString("close")!=null) 
	    	temp_obj.setPrev_close(rs_3.getDouble("close"));
	    	
	    	if(rs_3.getString("high")!=null) 
	    	temp_obj.setPrev_high(rs_3.getDouble("high"));
	    	
	    	if(rs_3.getString("low")!=null)
	    	temp_obj.setPrev_low(rs_3.getDouble("low"));
	    	
	    	if(rs_3.getString("volume")!=null)
	    	temp_obj.setAvg_volume(rs_3.getDouble("volume"));
	    	
	    	
	    	Stock.stock_list.put(rs_3.getLong("instrument_token"), temp_obj);
	    		    	
	    }
	    
	    
	    
	    int index=query_bldr.lastIndexOf(",");    
	    query_bldr.replace(index,1+index,"");    
	    
	    query_bldr.append(")");
//	    con = C3poDataSource.getConnection();
		
	    
//	   Object[] ob = tokens.toArray();
	    String sql = "select * from InstruDetail where instrument_token IN "+query_bldr.toString();
	    
//	    System.out.println("-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
//	      System.out.println(sql);
//	    System.out.println("-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	    
		PreparedStatement stmt3 = con.prepareStatement(sql);
//		stmt3.setString(1, query_bldr.toString());
		
//		Array array = stmt3.getConnection().createArrayOf("VARCHAR", new Object[]{"8150273", "8151809","8130049"});
//		stmt3.setArray(1, array);
		
		ResultSet rs_2 = stmt3.executeQuery();
		
		    
		    while(rs_2.next())
		    {
		    	Stock.name_list.put(rs_2.getLong("instrument_token"),rs_2.getString("tradingsymbol"));
		    }
		    
		    
		    
	    
		    con.close();
		    Examples examples = new Examples();
//		    examples.saveHLC();

//		    examples.saveAvgVolume();

//		    examples.getHistoricalData(TestController.kiteConnect);

//		    examples.getHistoricalData30min(TestController.kiteConnect);

//		    examples.getHistoricalDataday(TestController.kiteConnect);
		    examples.tickerUsage(TestController.kiteConnect, tokens);    
     
//		    req.setAttribute("Stock_List", Stock.stock_list);
		    
		return new ModelAndView("stratergy1");
        }
        
        catch (Exception e) {
			e.printStackTrace();
		}
        finally
        {
        	if (con!=null)
	    	{
    		try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	} 
        }
        return null;
	}
	
	

}
