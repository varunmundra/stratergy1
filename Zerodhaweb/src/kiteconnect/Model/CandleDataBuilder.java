package kiteconnect.Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.neovisionaries.ws.client.WebSocketException;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.Tick;
import com.zerodhatech.ticker.KiteTicker;
import com.zerodhatech.ticker.OnConnect;
import com.zerodhatech.ticker.OnDisconnect;
import com.zerodhatech.ticker.OnError;
import com.zerodhatech.ticker.OnOrderUpdate;
import com.zerodhatech.ticker.OnTicks;

import rupeevest.TickData.WebScket.TickerEndpoint;
import sessionFactory.C3poDataSource;

public class CandleDataBuilder {
static KiteTicker tickerProvider1;

    
    public static void StopTicker()
    {
    	{
    	   tickerProvider1.disconnect();
    	   tickerProvider1 = null;
    	   System.out.println("Ticker Stopped.....");
    	}
    	
    }
    
    double tmp_open = 0;
	double tmp_high = 0;
	double tmp_low = 5000;
	double tmp_close = 0;
	double tmp_vol = 0;
	long diff = 0 ;
	
	
    /** Demonstrates com.zerodhatech.ticker connection, subcribing for instruments, unsubscribing for instruments, set mode of tick data, com.zerodhatech.ticker disconnection*/
    public void tickerUsageCandleMaker(KiteConnect kiteConnect, final ArrayList<Long> tokens) throws IOException, WebSocketException, KiteException {
        /** To get live price use websocket connection.
         * It is recommended to use only one websocket connection at any point of time and make sure you stop connection, once user goes out of app.
         * custom url points to new endpoint which can be used till complete Kite Connect 3 migration is done. */
       tickerProvider1 = new KiteTicker(kiteConnect.getAccessToken(), kiteConnect.getApiKey());
        System.out.println("1");
        tickerProvider1.setOnConnectedListener(new OnConnect() {
            @Override
            public void onConnected() {
                /** Subscribe ticks for token.
                 * By default, all tokens are subscribed for modeQuote.
                 * */
//            	System.out.println("2");
                tickerProvider1.subscribe(tokens);
//                System.out.println(tokens);
                tickerProvider1.setMode(tokens, KiteTicker.modeFull);
            }
        });

        tickerProvider1.setOnDisconnectedListener(new OnDisconnect() {
            @Override
            public void onDisconnected() {
                // your code goes here
            }
        });

        /** Set listener to get order updates.*/
        tickerProvider1.setOnOrderUpdateListener(new OnOrderUpdate() {
            @Override
            public void onOrderUpdate(Order order) {
                System.out.println("order update "+order.orderId);
            }
        });

        /** Set error listener to listen to errors.*/
        tickerProvider1.setOnErrorListener(new OnError() {
            @Override
            public void onError(Exception exception) {
                //handle here.
            }

            @Override
            public void onError(KiteException kiteException) {
                //handle here.
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });

        tickerProvider1.setOnTickerArrivalListener(new OnTicks() {
            @Override
            public void onTicks(ArrayList<Tick> ticks) {
//            	System.out.println("------------------------------------------------------------------------");
                NumberFormat formatter = new DecimalFormat();
                ArrayList<MinuteData> db_save_list = new ArrayList<MinuteData>();
                System.out.println("ticks size "+ticks.size());
                if(ticks.size() > 0) {
                	for(int i=0;i<ticks.size();i++) {
//                		System.out.println("aaaaaaaaaaaaa"+i);
                		
                		Long instrument_token_tmp= ticks.get(i).getInstrumentToken();
                		
                		Stock ob = Stock.stock_list.get(instrument_token_tmp);
                		
                		ob.setLTP(ticks.get(i).getLastTradedPrice());
                		ob.setTime_stamp(ticks.get(i).getTickTimestamp()); 
            			ob.setCurrent_volume(ticks.get(i).getVolumeTradedToday());
      
            			ob.setHigh(ticks.get(i).getHighPrice());
            			ob.setLow(ticks.get(i).getLowPrice());
            			Calendar cal_fx_min = Calendar.getInstance();
            			
            			Calendar cal_chk = Calendar.getInstance();
            			
            			
            			ArrayList<Ticker> nn = ob.getMinute_data();
            			if(nn.size()==0)
            			{
            				nn.add(new Ticker(ticks.get(i).getInstrumentToken(), ticks.get(i).getLastTradedPrice(), ticks.get(i).getOpenPrice(), ticks.get(i).getHighPrice(), ticks.get(i).getLowPrice(), ticks.get(i).getClosePrice(), ticks.get(i).getTickTimestamp(), ticks.get(i).getVolumeTradedToday()));
            			}
            			else
            			{
            				
            				Calendar cal = Calendar.getInstance();
                			cal.setTime(nn.get((nn.size()-1)).getTime_stamp());
                			
                			cal_fx_min.setTime(nn.get(0).getTime_stamp());
                			cal_chk.setTime(nn.get(0).getTime_stamp());
                			cal_fx_min.add(Calendar.MINUTE, 1);
                			cal_fx_min.set(Calendar.SECOND, 0);
                			
                			Calendar cal_2 = Calendar.getInstance();
                			cal_2.setTime(ticks.get(i).getTickTimestamp());
                			
                			System.out.println("1  Time(max)"+cal.getTime());
                			System.out.println("2 Time(Latest)"+cal_2.getTime());
                			System.out.println("3  Exit Time(1)->"+cal_fx_min.getTime());
                			System.out.println("Volume ->"+ticks.get(i).getVolumeTradedToday());
                			System.out.println("LTP ->"+ticks.get(i).getLastTradedPrice());
                			System.out.println("2 compare 3 ->"+cal_2.compareTo(cal_fx_min));
                			
//                			System.out.println(cal.get(Calendar.MINUTE) != cal_2.get(Calendar.MINUTE) && (cal_2.get(Calendar.SECOND)!=0 ));
//                			System.out.println(cal_2.get(Calendar.SECOND));
//                			System.out.println((cal_2.get(Calendar.SECOND)!=0 ));
                			
//                			System.out.println((cal.get(Calendar.HOUR) != cal_2.get(Calendar.HOUR)));
                			
//                			System.out.println((cal.get(Calendar.MINUTE) != cal_2.get(Calendar.MINUTE)));
                			
//                			if((cal.get(Calendar.HOUR) != cal_2.get(Calendar.HOUR) ) || (cal.get(Calendar.MINUTE) != cal_2.get(Calendar.MINUTE)))
//                			if( cal.get(Calendar.MINUTE) != cal_2.get(Calendar.MINUTE) && (cal_2.get(Calendar.SECOND)!=0 ))
                			if(cal_2.compareTo(cal_fx_min) >= 0)
                			  {
                				diff = 0;
//                				diff = Math.abs(cal.getTimeInMillis() - cal_2.getTimeInMillis()) * 1000 * 60;
                				diff = Math.abs(cal_2.getTimeInMillis() - cal_chk.getTimeInMillis()) / 1000 / 60;
                				
                				
                				System.out.println("HERE in IFFF-----------Time Difference-->"+diff);
                				
		                				if(diff <= 1	)
		                				{
			                					System.out.println("New MINUTE----->"+instrument_token_tmp+" ->");
			                   				 tmp_open = nn.get((0)).getLTP();
			                   				 tmp_high = 0;
			                   				 tmp_low = 500000000;
			                   				 tmp_close = 0;
			                   				 tmp_vol = 0;
			                   				
			                   				for(Ticker kk : nn)
			                   				{   
			                   					 
//			                   					if(tmp_open==0) {tmp_open = kk.getLTP();}
			                   					if (tmp_high<kk.getLTP()) {tmp_high = kk.getLTP();}
			                   					if (tmp_low>kk.getLTP()) {tmp_low = kk.getLTP();}
			//                   					tmp_vol = tmp_vol + kk.getCurrent_volume();
			                   				}
			                   				tmp_close = nn.get((nn.size() - 1)).getLTP();
			                   				
			                   				tmp_vol = (nn.get((nn.size() - 1)).getCurrent_volume() - ob.getTemp_previous_volume());
			                   				
			                   				ob.setTemp_previous_volume(nn.get((nn.size() - 1)).getCurrent_volume());
			                   				
			                   				cal.set(Calendar.SECOND,0); 
			                   				db_save_list.add(new MinuteData(instrument_token_tmp, cal.getTime(), tmp_open, tmp_high, tmp_low, tmp_close, tmp_vol));
			                   				nn.clear();
			                   				
			                   				nn.add(new Ticker(ticks.get(i).getInstrumentToken(), ticks.get(i).getLastTradedPrice(), ticks.get(i).getOpenPrice(), ticks.get(i).getHighPrice(), ticks.get(i).getLowPrice(), ticks.get(i).getClosePrice(), ticks.get(i).getTickTimestamp(), ticks.get(i).getVolumeTradedToday()));
		                   			     
		                				}
		                				else
		                				{
		                					System.out.println("Time difference more than 1 minute");
		                					
		                					 tmp_open = nn.get((0)).getLTP();
			                   				 tmp_high = 0;
			                   				 tmp_low = 500000000;
			                   				 tmp_close = 0;
			                   				 tmp_vol = 0;
		                					
		                					for(Ticker kk : nn)
			                   				{   
			                   					 
//			                   					if(tmp_open==0) {tmp_open = kk.getLTP();}
			                   					if (tmp_high<kk.getLTP()) {tmp_high = kk.getLTP();}
			                   					if (tmp_low>kk.getLTP()) {tmp_low = kk.getLTP();}
			//                   					tmp_vol = tmp_vol + kk.getCurrent_volume();
			                   				}
			                   				tmp_close = nn.get((nn.size() - 1)).getLTP();
			                   				
			                   				tmp_vol = (nn.get((nn.size() - 1)).getCurrent_volume() - ob.getTemp_previous_volume());
			                   				
			                   				ob.setTemp_previous_volume(nn.get((nn.size() - 1)).getCurrent_volume());
			                   				
			                   			    cal.set(Calendar.SECOND,0); 
			                   				db_save_list.add(new MinuteData(instrument_token_tmp, cal.getTime(), tmp_open, tmp_high, tmp_low, tmp_close, tmp_vol));
			                   				nn.clear();
			                   				
			                   				nn.add(new Ticker(ticks.get(i).getInstrumentToken(), ticks.get(i).getLastTradedPrice(), ticks.get(i).getOpenPrice(), ticks.get(i).getHighPrice(), ticks.get(i).getLowPrice(), ticks.get(i).getClosePrice(), ticks.get(i).getTickTimestamp(), ticks.get(i).getVolumeTradedToday()));
		                				}
                				
                			  }
                			else
                			  {
                				System.out.println("SHOULD NOT BE HERE---------------OLD MINUTE ---->"+instrument_token_tmp);
                				nn.add(new Ticker(ticks.get(i).getInstrumentToken(), ticks.get(i).getLastTradedPrice(), ticks.get(i).getOpenPrice(), ticks.get(i).getHighPrice(), ticks.get(i).getLowPrice(), ticks.get(i).getClosePrice(), ticks.get(i).getTickTimestamp(), ticks.get(i).getVolumeTradedToday()));  
                			  }
            				
            			}
            			
            			
            			
//            			 if(Stock.Print_TimeStamp.after(new Date()))
//            			  {
//            				 System.out.println("----Will print for 2 minutes-------");
//            				 System.out.println(Stock.name_list.get(ob.getInstrument_token())+" ,******* High_now->  "+ob.getHigh_counter()+"   ******** , Low->"+ob.getLow_counter()+" , LTP->"+ob.getLTP()+" , Time->"+ticks.get(i).getTickTimestamp()+",  Volume Today--->   "+ticks.get(i).getVolumeTradedToday()+" Diff of last 10 highs->"+ob.getHigh_queue().getDifference()+" Sec");
//            				 
//            			  }
//            			  System.out.println("<------------->");
//            			  System.out.println("Token No->"+ob.getInstrument_token()+"  "+Stock.name_list.get(ob.getInstrument_token())+" ,******* High_now->  "+ticks.get(i).getHighPrice()+"   ******** , Low->"+ticks.get(i).getLowPrice()+" , LTP->"+ticks.get(i).getLastTradedPrice()+" , Time->"+ticks.get(i).getTickTimestamp()+",  Volume Today--->   "+ticks.get(i).getVolumeTradedToday());
//            			  System.out.println("<------------->");
            			
//            			System.out.println(Double.compare(ticks.get(i).getHighPrice(),ob.getHigh()) > 0 ) ;
            			
            			
//            			if ( Double.compare(ticks.get(i).getHighPrice(),ob.getHigh()) > 0 ) 
////                		if ( Double.compare(ticks.get(i).getLastTradedPrice(),ob.getHigh()) > 0 )
////                		if( ticks.get(i).getLastTradedPrice() > ob.getHigh())
//                		{
////                			ob.setHigh(ticks.get(i).getLastTradedPrice());
//                			ob.setHigh(ticks.get(i).getHighPrice());
//                			ob.setHigh_counter(ob.getHigh_counter()+1);
//                			ob.setFlag('H'); // H means high 	
//                			
////                			if((ticks.get(i).getTickTimestamp()==null))
////                			{
////                				ob.getHigh_queue().add(new Date());
////                			}
////                			else
////                			{
////                				ob.getHigh_queue().add(ticks.get(i).getTickTimestamp());
////                			}
//                			
//                			if( (ob.getHigh_counter() >=25)  )
//                			{
//                				System.out.println("Dynamic Val->"+getDynamicValue()); 
//                			    System.out.println("Token No->"+ob.getInstrument_token()+"  "+Stock.name_list.get(ob.getInstrument_token())+" ,******* High_now->  "+ob.getHigh_counter()+"   ******** , Low->"+ob.getLow_counter()+" , LTP->"+ob.getLTP()+" , Time->"+ticks.get(i).getTickTimestamp()+",  Volume Today--->   "+ticks.get(i).getVolumeTradedToday());
//                			}
////                			if((ob.getHigh_counter() >=25) && (ob.getCurrent_volume() * ob.getLTP() > getDynamicValue()) )
//                			if( (ob.getHigh_counter() >=25) && (Double.compare((ob.getCurrent_volume() * ob.getHigh()), getDynamicValue()) > 0 ) )
////                			if( (ob.getHigh_counter() >=25) && (Double.compare((ob.getCurrent_volume() * ob.getLTP()), getDynamicValue()) > 0 ) )	
////                			    if( (ob.getHigh_counter() >=2)  )
//                			        {
//                				        TickerEndpoint.sendData(ob.getNewDummyObj());	
//                					}
//                			
//                			
////                			System.out.println("going to set HIGH COUNTER------------>"+ob.getTime_stamp()+" Current High Price-->"+ticks.get(i).getHighPrice()+"     "+ob.getInstrument_token()+" High->"+ob.getHigh());
//                			
//                		}
                		
            			
//            			System.out.println(Double.compare(ticks.get(i).getHighPrice(),ob.getHigh()) > 0 ) ;
            			
//                		if ( Double.compare(ticks.get(i).getLowPrice(),ob.getLow()) < 0 )
////                		if ( Double.compare(ticks.get(i).getLastTradedPrice(),ob.getLow()) < 0 )
////                		if( ticks.get(i).getLastTradedPrice() < ob.getLow())
//                		{
////                			ob.setLow(ticks.get(i).getLastTradedPrice());
//                			ob.setLow(ticks.get(i).getLowPrice());
//                			ob.setLow_counter(ob.getLow_counter()+1);
//                			ob.setFlag('L'); // L means low 
//                			
////                			if(ticks.get(i).getTickTimestamp()==null)
////                			{
////                				ob.getLow_queue().add(new Date());
////                			}
////                			else
////                			{
////                				ob.getLow_queue().add(ticks.get(i).getTickTimestamp());
////                			}
//                			
////                			if( (ob.getLow_counter() >=25) && (ob.getCurrent_volume() * ob.getLow() > getDynamicValue()) )
////                			if( (ob.getLow_counter() >=25) && (ob.getCurrent_volume() * ob.getLTP() > getDynamicValue()) )
//                			if( (ob.getLow_counter() >=25) )
//                			{
//                				System.out.println("Dynamic Val->"+getDynamicValue());
//                				System.out.println("Token No->"+ob.getInstrument_token()+" "+Stock.name_list.get(ob.getInstrument_token())+" , High->"+ob.getHigh_counter()+" ,******** Low_now->  "+ob.getLow_counter()+"   ********* , LTP->"+ob.getLTP()+" , Time->"+ticks.get(i).getTickTimestamp()+",  Volume Today--->   "+ticks.get(i).getVolumeTradedToday());
//                			}
//                			
//                			if( (ob.getLow_counter() >=25) && (Double.compare((ob.getCurrent_volume() * ob.getLow()), getDynamicValue()) > 0 ) )
////                			if( (ob.getLow_counter() >=2))
//                					{
//                				        TickerEndpoint.sendData(ob.getNewDummyObj());	
//                					}
//                			
////                			System.out.println("going to set LOW COUNTER------------>"+ob.getTime_stamp()+"  Current Low Price-->"+ticks.get(i).getLowPrice()+"     "+ob.getInstrument_token()+" Low->"+ob.getLow()); 
//                		}                		                		
                		
//	                		if(Stock.Print_counter < 500)
	                		{
//	                			System.out.println("-==-=-=-=-=-Tick Check=-=-=-=-=-=-=-=");
//	                			   System.out.println("Token No->"+ob.getInstrument_token()+" "+Stock.name_list.get(ob.getInstrument_token())+" , High->"+ticks.get(i).getHighPrice()+" ,******** Low_now->  "+ticks.get(i).getLowPrice()+"   ********* , LTP->"+ticks.get(i).getLastTradedPrice()+" , Time->"+ticks.get(i).getTickTimestamp()+",  Volume Today--->   "+ticks.get(i).getVolumeTradedToday());
//	                			System.out.println("-==-=-=-=-=-Tick Check=-=-=-=-=-=-=-=");
	//                			
//	                			Stock.Print_counter++;
	                		}
                		
//	                	db_save_list.add(new Ticker(ticks.get(i).getInstrumentToken(), ticks.get(i).getLastTradedPrice(), ticks.get(i).getOpenPrice(), ticks.get(i).getHighPrice(), ticks.get(i).getLowPrice(), ticks.get(i).getClosePrice(), ticks.get(i).getTickTimestamp(), ticks.get(i).getVolumeTradedToday()));
                		Stock.stock_list.put(instrument_token_tmp,ob);
//                		
                		 
                	        	
                		
//                		if((ob.getLow_counter() >=10) || (ob.getHigh_counter() >= 10))
//                		{
//                		     TickerEndpoint.sendData(ob.getNewDummyObj());
//                		}
                		
//                		System.out.println(ob.getLow());
                		
                		
                		
//                	  System.out.println("token number "+ticks.get(i).getInstrumentToken());
//                    System.out.println("last price "+ticks.get(i).getLastTradedPrice());
//                    System.out.println("open interest "+formatter.format(ticks.get(i).getOi()));
//                    System.out.println("day high OI "+formatter.format(ticks.get(i).getOpenInterestDayHigh()));
//                    System.out.println("day low OI "+formatter.format(ticks.get(i).getOpenInterestDayLow()));
//                    System.out.println("change "+formatter.format(ticks.get(i).getChange()));
//                    System.out.println("tick timestamp "+ticks.get(i).getTickTimestamp());
//                    System.out.println("tick timestamp date "+ticks.get(i).getTickTimestamp());
//                    System.out.println("last traded time "+ticks.get(i).getLastTradedTime());
//                    System.out.println(ticks.get(i).getMarketDepth().get("buy").size());
                	}
                	if(db_save_list.size()>0)
                	save_Minute_Data(db_save_list);
                	
                }
            }
        });
        // Make sure this is called before calling connect.
        tickerProvider1.setTryReconnection(true);
        //maximum retries and should be greater than 0
        tickerProvider1.setMaximumRetries(10);
        //set maximum retry interval in seconds
        tickerProvider1.setMaximumRetryInterval(30);

        /** connects to com.zerodhatech.com.zerodhatech.ticker server for getting live quotes*/
        tickerProvider1.connect();

        /** You can check, if websocket connection is open or not using the following method.*/
        boolean isConnected = tickerProvider1.isConnectionOpen();
//        System.out.println(isConnected+"sdfsdf");

        /** set mode is used to set mode in which you need tick for list of tokens.
         * Ticker allows three modes, modeFull, modeQuote, modeLTP.
         * For getting only last traded price, use modeLTP
         * For getting last traded price, last traded quantity, average price, volume traded today, total sell quantity and total buy quantity, open, high, low, close, change, use modeQuote
         * For getting all data with depth, use modeFull*/
        tickerProvider1.setMode(tokens, KiteTicker.modeLTP);

        // Unsubscribe for a token.
//        tickerProvider.unsubscribe(tokens);
//        System.out.println("4");
        // After using com.zerodhatech.com.zerodhatech.ticker, close websocket connection.
//        tickerProvider.disconnect();
//        System.out.println("5");
    }
    
    
    
    
    public static void save_Minute_Data(List<MinuteData> ob_lst)
    {
    	 System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=IN MINUTE DATA SAVING=-=-=-=-=-=-=-=-=-=-=-=-=--===-=-");
        ExecutorService pool = Executors.newCachedThreadPool();
        
				        Runnable task = new Runnable() {
				            public void run() {
				            	Connection con = null;
				            	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				            	final int batchSize = 200;
				            	int count = 0;
				            	
				            	try
				            	{
				            	con = C3poDataSource.getConnection();
				            	
				                String sql="insert into minute_data (high,low,open,close,time,volume,instrument_token) values (?,?,?,?,?,?,?)";
				                
				//("+ob.getInstrument_token()+","+ob.getLTP()+","+ob.getHigh()+","+ob.getLow()+","+ob.getHigh_counter()+","+ob.getLow_counter()+","+ob.prev_high+","+ob.getPrev_low()+","+ob.getPrev_close()+","+ob.getAvg_volume()+",'"+ob.getTime_stamp()+"',"+ob.getCurrent_volume()+")";
				
				                PreparedStatement preparedStatement = con.prepareStatement(sql);
				                
				                for (MinuteData ob: ob_lst) {
//							                preparedStatement.setDouble(1,ob.getLTP());
							                preparedStatement.setDouble(1, ob.getHigh());
							                preparedStatement.setDouble(2, ob.getLow());
							                preparedStatement.setDouble(3, ob.getOpen());
							                preparedStatement.setDouble(4, ob.getClose());
							            
							                if(ob.getTime()!=null)
							                {
							                  preparedStatement.setString(5, formatter.format(ob.getTime()));
							                }
							                else
							                {
							                	preparedStatement.setString(5, null);	
							                }
							                preparedStatement.setDouble(6, ob.getVolume());
							                preparedStatement.setLong(7, ob.getInstrument_token());
							                preparedStatement.addBatch();
							                
							                if(++count % batchSize == 0) {
							                	preparedStatement.executeBatch();
							            	}
				                }
				                
				                preparedStatement.executeBatch(); // insert remaining records
				                preparedStatement.close();
				                con.close();
//				                System.out.println("=====DATA SAVED IN DB-=-=-=-=-");
				            	}
				            	catch (Exception e) {
				            		System.out.println("Error Occured while saving State in DB");
									e.printStackTrace();
								}
				            	finally
				            	{
				            		try
				            		{
				            			if(con!=null)
				            			con.close();
				            		}
				            		catch (Exception e) {
										e.printStackTrace();
									}
				            	}
				            }
				        };
        
        
        pool.execute(task);
    }
    

}
