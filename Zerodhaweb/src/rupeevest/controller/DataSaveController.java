package rupeevest.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;

import kiteconnect.Model.CandleDataBuilder;
import kiteconnect.Model.Examples;
import kiteconnect.Model.Stock;
import sessionFactory.C3poDataSource;

@Controller
@RequestMapping("/Data")
public class DataSaveController {
	
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
							         
//							         where cs.instrument_token=408065
//							        PreparedStatement pp = con.prepareStatement("select cs.* from nse_cash cash join current_state cs on cs.instrument_token=cash.instrument_token"); // for CASH
							        PreparedStatement pp = con.prepareStatement("select cs.* from mcx mx join current_state cs on cs.instrument_token=mx.instrument_token where mx.instrument_token='54322695'"); // for MCX
							        
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
      
		    
		    
//		    Examples examples = new Examples();
       CandleDataBuilder example = new CandleDataBuilder();
       
//		    examples.saveHLC();

//		    examples.saveAvgVolume();

		    // not this
//		    examples.getHistoricalData(TestController.kiteConnect);

//		    examples.getHistoricalData30min(TestController.kiteConnect);

//		    examples.getHistoricalDataday(TestController.kiteConnect);
//		    examples.tickerUsage(TestController.kiteConnect, tokens);    
            example.tickerUsageCandleMaker(TestController.kiteConnect, tokens);
		
		    
		return new ModelAndView("stratergy1_mindata");
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
	
	

}
