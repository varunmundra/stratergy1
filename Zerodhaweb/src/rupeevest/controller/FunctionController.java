package rupeevest.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



import com.zerodhatech.kiteconnect.KiteConnect;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.Trade;
import com.zerodhatech.models.User;

import kiteconnect.Model.Stock;
import kiteconnect.Model.StockDatatoSend;
import kiteconnect.Model.Examples;
import sessionFactory.C3poDataSource;

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
	    		 
	    		  String access_tkn = null;
	    		  String public_tkn = null;
	    		  
	    		  if(req.getParameter("access_token")==null || req.getParameter("public_token")==null)
	    		  {
	    			  con = C3poDataSource.getConnection();
	    			  PreparedStatement stmt3=con.prepareStatement("select * from login_module");
	    			  ResultSet rr = stmt3.executeQuery();
	    			  while(rr.next())
	    			  {
	    				  access_tkn = rr.getString("access_token");
	    				  public_tkn = rr.getString("public_token");
	    			  }
	    			  con.close();
	    		  }
	    		  else
	    		  {
	    			  access_tkn = req.getParameter("access_token").toString();
	    			  public_tkn = req.getParameter("public_token").toString();
	    		  }
	    		 
	    			  
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
	public ModelAndView stratergy1 (HttpServletRequest req , HttpSession session) throws KiteException
	{

        Connection con = null;
        StringBuilder query_bldr = new StringBuilder("("); 
        ArrayList<Long> tokens = new ArrayList<Long>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
        	
//		       System.out.println("------------------- SESSION --------------------");
//		         System.out.println("SESSION ->"+session.getAttribute("statergy1"));
//		       System.out.println("--------------------CLOSE SESSION -------------------");
        	
       if(session.getAttribute("statergy1")==null || ((session.getAttribute("statergy1")!=null) && tokens.size()==0)) 	        	
       { 	
    	                            
    	                             if((session.getAttribute("statergy1")!=null) && tokens.size()==0)
    	                             {
    	                            	 session.removeAttribute("statergy1");
//    	                            	 session.invalidate();
    	                             }
//							         System.out.println("------IN NULL SESSION");
//							        FileReader filereader = new FileReader(new File("/Users/varun/Desktop/mcx.csv"));
//							        CSVReader csvReader = new CSVReader(filereader); 
//								    String[] nextRecord; 
							         con = C3poDataSource.getConnection();
							         
							        PreparedStatement pp = con.prepareStatement("select cs.* from nse_cash cash join current_state cs on cs.instrument_token=cash.instrument_token"); // for CASH
//							        PreparedStatement pp = con.prepareStatement("select cs.* from mcx mx join current_state cs on cs.instrument_token=mx.instrument_token"); // for MCX
							        
							        ResultSet Rs = pp.executeQuery();
							        
//								    while ((nextRecord = csvReader.readNext()) != null) 
							        while(Rs.next())
								    {
								    	tokens.add(Rs.getLong("instrument_token"));
//								    	Stock.stock_list.put(Rs.getLong("instrument_token"),new Stock(Rs.getLong("instrument_token")));
//								    	Rs.getLong("low")
								    	Stock.stock_list.put(Rs.getLong("instrument_token"),new Stock(Rs.getLong("instrument_token"), Rs.getLong("lTP"), Rs.getLong("high"), Rs.getLong("low"), Rs.getInt("high_counter"), Rs.getInt("low_counter"), Rs.getLong("prev_high"),  Rs.getLong("prev_low"), Rs.getLong("prev_close"), Rs.getLong("avg_volume"),Rs.getDate("time_stamp"),Rs.getLong("current_volume"))); 
								    	query_bldr.append(Rs.getString("instrument_token")) ;
								    	query_bldr.append(",");
								    	
								    }
								    
//							        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
//							        System.out.println("Token Size-->"+tokens.size());
//							        System.out.println("Query Buildr-->"+query_bldr);
//							        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
							        
							        
								    StringBuilder builder = new StringBuilder();
								    
								    
								    for (Long key : Stock.stock_list.keySet()) {
							//	    for( int i = 0 ; i < Stock.stock_list.size(); i++ ) 
//								    System.out.println("in name list---"+key);
								        builder.append(Stock.stock_list.get(key).getInstrument_token()+",");
								    }
								    
								    String stmt_setter = "select * from save_avg where instrument_token in (" 
								               + builder.deleteCharAt( builder.length() -1 ).toString() + ")";
							
								    
								    
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
									    session.setAttribute("statergy1", "ok");
									    req.setAttribute("refresh", "new");
		 
		    
        }
       else
       {
    	   req.setAttribute("refresh", "updated");
    	   
       }
      
		    
		    
		    Examples examples = new Examples();
//		    examples.saveHLC();

//		    examples.saveAvgVolume();

		    // not this
//		    examples.getHistoricalData(TestController.kiteConnect);

//		    examples.getHistoricalData30min(TestController.kiteConnect);

//		    examples.getHistoricalDataday(TestController.kiteConnect);
		    examples.tickerUsage(TestController.kiteConnect, tokens);    
     
		
		    
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
				
				
				System.out.println("here in error......");
				e.printStackTrace();
			}
	    	} 
        }
        return null;
	}
	
	
	@RequestMapping(value = "/StopSession" , method = RequestMethod.GET, produces = "application/text")
	@ResponseBody
	public String invalidateSession (HttpServletRequest req , HttpSession session) throws KiteException
	{
		String x="SEssion Invalidated";
		session.invalidate();
		Examples.StopTicker();
		
//		x = true;
		return "success";
	}
	
	
	@RequestMapping(value = "/SaveStateDB" , method = RequestMethod.GET, produces = "application/text")
	@ResponseBody
	public String saveState (HttpServletRequest request) 
	{
		
		
		Connection con = null;
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try
    	{
    		 
    		con = C3poDataSource.getConnection();
    		
    		
    		
            String sql="update current_state set LTP = ? ,high = ?,low = ?,high_counter = ?,low_counter = ?,prev_high = ?,prev_low = ?,prev_close = ?,avg_volume = ?,time_stamp = ?,current_volume = ? where instrument_token = ?";
            
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            
            
            for (Long key : Stock.stock_list.keySet()) 
//            for(Stock ob : Stock.stock_list.values())
            {
            	Stock ob = Stock.stock_list.get(key);
            	
            	preparedStatement.setDouble(1,ob.getLTP());
                preparedStatement.setDouble(2, ob.getHigh());
                preparedStatement.setDouble(3, ob.getLow());
                preparedStatement.setLong(4, ob.getHigh_counter());
                preparedStatement.setLong(5, ob.getLow_counter());
                preparedStatement.setDouble(6, ob.getPrev_high());
                preparedStatement.setDouble(7, ob.getPrev_low());
                preparedStatement.setDouble(8, ob.getPrev_close());
                preparedStatement.setDouble(9, ob.getAvg_volume());
                
             
                if(ob.getTime_stamp()!=null)
                {
                  preparedStatement.setString(10, formatter.format(ob.getTime_stamp()));
                }
                else
                {
                	preparedStatement.setString(10, null);	
                }
                preparedStatement.setDouble(11, ob.getCurrent_volume());
                preparedStatement.setLong(12, ob.getInstrument_token());
                
//                preparedStatement.addBatch();
                preparedStatement.executeUpdate();
            	
            }
            
            
    		
    		System.out.println(" Data Saved....");
    		
    		
    	}
    	catch (Exception e) {
			e.printStackTrace();
			
			return e.getMessage();
		}

		return "success";
	}
	
	
	
	@RequestMapping(value = "/ClearStateDB" , method = RequestMethod.GET, produces = "application/text")
	@ResponseBody
	public String deleteState (HttpServletRequest request) 
	{
		
		
		Connection con = null;
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try
    	{
    		 
    		con = C3poDataSource.getConnection();
    		
    		
    		
            String sql="update current_state set LTP = ? ,high = ?,low = ?,high_counter = ?,low_counter = ?,prev_high = ?,prev_low = ?,prev_close = ?,avg_volume = ?,time_stamp = ?,current_volume = ? where instrument_token = ?";
            
//            PreparedStatement pp = con.prepareStatement("select cs.* from nse_cash cash join current_state cs on cs.instrument_token=cash.instrument_token"); // for CASH
	        PreparedStatement pp1 = con.prepareStatement("select distinct(instrument_token) from current_state"); // for MCX
	        ResultSet rr = pp1.executeQuery();
            
            
	        PreparedStatement preparedStatement=null;
            
            System.out.println(Stock.stock_list.isEmpty());
            
//            for(Stock ob : Stock.stock_list.values())
            while(rr.next())
            {
            	preparedStatement = con.prepareStatement(sql);
//            	System.out.println(" here 1 ...");
            	preparedStatement.setDouble(1,new Double(0.0));
                preparedStatement.setDouble(2, new Double(0.0));
                preparedStatement.setDouble(3, new Double(500000.0));
                preparedStatement.setLong(4, new Long(0));
                preparedStatement.setLong(5, new Long(0));
                preparedStatement.setDouble(6, new Double(0.0));
                preparedStatement.setDouble(7, new Double(0.0));
                preparedStatement.setDouble(8, new Double(0.0));
                preparedStatement.setDouble(9, new Double(0.0));
                

               
                	preparedStatement.setString(10, null);	
                
                preparedStatement.setDouble(11, 0.0);
                preparedStatement.setLong(12, rr.getLong("instrument_token"));
                
//                preparedStatement.addBatch();
                
                preparedStatement.executeUpdate();
            	
            }
//            System.out.println(" here 2 ...");
            
    		
//    		System.out.println(" Data Erased Saved....");
    		
    		
    	}
    	catch (Exception e) {
			e.printStackTrace();
			
			return e.getMessage();
		}

		return "success";
	}
	
	
	@RequestMapping("/getTrades")
	public ModelAndView get_order (HttpServletRequest req) throws KiteException
	{
		
		try
		{
				if(TestController.kiteConnect!=null)
				{
					Examples examples = new Examples();
					List<Trade> list = examples.getTradesZerodha(TestController.kiteConnect);
				    req.setAttribute("order_list", list);
				    
				    return new ModelAndView("orderlist");
				}
				else
				{
					return new ModelAndView("redirect:/zerodha/login");
				}
		}
		catch (Exception e) 
		{
			System.out.println("----Error Occured---");
		    e.printStackTrace();
		}
		
		
		
		return null;
	}
	

}
