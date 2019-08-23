package kiteconnect.Model;
import com.neovisionaries.ws.client.WebSocketException;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.kiteconnect.utils.Constants;
import com.zerodhatech.models.*;
import com.zerodhatech.ticker.*;

import kiteconnect.Model.Stock;
import rupeevest.TickData.WebScket.TickerEndpoint;
import sessionFactory.C3poDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sujith on 15/10/16.
 */
public class Examples {

      
    public void getProfile(KiteConnect kiteConnect) throws IOException, KiteException {
        Profile profile = kiteConnect.getProfile();
        System.out.println(profile.userName);
    }

    /**Gets Margin.*/
    public void getMargins(KiteConnect kiteConnect) throws KiteException, IOException {
        // Get margins returns margin model, you can pass equity or commodity as arguments to get margins of respective segments.
        //Margins margins = kiteConnect.getMargins("equity");
        Margin margins = kiteConnect.getMargins("equity");
        System.out.println(margins.available.cash);
        System.out.println(margins.utilised.debits);
        System.out.println(margins.utilised.m2mUnrealised);
        
    }
    
    
    
    public Margin getMargins1(KiteConnect kiteConnect) throws KiteException, IOException {
        // Get margins returns margin model, you can pass equity or commodity as arguments to get margins of respective segments.
        //Margins margins = kiteConnect.getMargins("equity");
        Margin margins = kiteConnect.getMargins("equity");
        
        return margins;
        
    }

    /**Place order.*/
    public void placeOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        /** Place order method requires a orderParams argument which contains,
         * tradingsymbol, exchange, transaction_type, order_type, quantity, product, price, trigger_price, disclosed_quantity, validity
         * squareoff_value, stoploss_value, trailing_stoploss
         * and variety (value can be regular, bo, co, amo)
         * place order will return order model which will have only orderId in the order model
         *
         * Following is an example param for LIMIT order,
         * if a call fails then KiteException will have error message in it
         * Success of this call implies only order has been placed successfully, not order execution. */

        OrderParams orderParams = new OrderParams();
        orderParams.quantity = 1;
        orderParams.orderType = Constants.ORDER_TYPE_LIMIT;
        orderParams.tradingsymbol = "ASHOKLEY";
        orderParams.product = Constants.PRODUCT_CNC;
        orderParams.exchange = Constants.EXCHANGE_NSE;
        orderParams.transactionType = Constants.TRANSACTION_TYPE_BUY;
        orderParams.validity = Constants.VALIDITY_DAY;
        orderParams.price = 122.2;
        orderParams.triggerPrice = 0.0;
        orderParams.tag = "myTag"; //tag is optional and it cannot be more than 8 characters and only alphanumeric is allowed

        Order order = kiteConnect.placeOrder(orderParams, Constants.VARIETY_REGULAR);
        System.out.println(order.orderId);
    }

    /** Place bracket order.*/
    public void placeBracketOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        /** Bracket order:- following is example param for bracket order*
         * trailing_stoploss and stoploss_value are points and not tick or price
         */
        OrderParams orderParams = new OrderParams();
        orderParams.quantity = 1;
        orderParams.orderType = Constants.ORDER_TYPE_LIMIT;
        orderParams.price = 30.5;
        orderParams.transactionType = Constants.TRANSACTION_TYPE_BUY;
        orderParams.tradingsymbol = "SOUTHBANK";
        orderParams.trailingStoploss = 1.0;
        orderParams.stoploss = 2.0;
        orderParams.exchange = Constants.EXCHANGE_NSE;
        orderParams.validity = Constants.VALIDITY_DAY;
        orderParams.squareoff = 3.0;
        orderParams.product = Constants.PRODUCT_MIS;
         Order order10 = kiteConnect.placeOrder(orderParams, Constants.VARIETY_BO);
         System.out.println(order10.orderId);
    }

    /** Place cover order.*/
    public void placeCoverOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        /** Cover Order:- following is an example param for the cover order
         * key: quantity value: 1
         * key: price value: 0
         * key: transaction_type value: BUY
         * key: tradingsymbol value: HINDALCO
         * key: exchange value: NSE
         * key: validity value: DAY
         * key: trigger_price value: 157
         * key: order_type value: MARKET
         * key: variety value: co
         * key: product value: MIS
         */
        OrderParams orderParams = new OrderParams();
        orderParams.price = 0.0;
        orderParams.quantity = 1;
        orderParams.transactionType = Constants.TRANSACTION_TYPE_BUY;
        orderParams.orderType = Constants.ORDER_TYPE_MARKET;
        orderParams.tradingsymbol = "SOUTHBANK";
        orderParams.exchange = Constants.EXCHANGE_NSE;
        orderParams.validity = Constants.VALIDITY_DAY;
        orderParams.triggerPrice = 30.5;
        orderParams.product = Constants.PRODUCT_MIS;

        Order order11 = kiteConnect.placeOrder(orderParams, Constants.VARIETY_CO);
        System.out.println(order11.orderId);
    }

    /** Get trigger range.*/
    public void getTriggerRange(KiteConnect kiteConnect) throws KiteException, IOException {
        // You need to send transaction_type, exchange and tradingsymbol to get trigger range.
        String[] instruments = {"BSE:INFY", "NSE:APOLLOTYRE", "NSE:SBIN"};
        Map<String, TriggerRange> triggerRangeMap = kiteConnect.getTriggerRange(instruments, Constants.TRANSACTION_TYPE_BUY);
        System.out.println(triggerRangeMap.get("NSE:SBIN").lower);
        System.out.println(triggerRangeMap.get("NSE:APOLLOTYRE").upper);
        System.out.println(triggerRangeMap.get("BSE:INFY").percentage);
    }

    /** Get orderbook.*/
    public void getOrders(KiteConnect kiteConnect) throws KiteException, IOException {
        // Get orders returns order model which will have list of orders inside, which can be accessed as follows,
        List<Order> orders = kiteConnect.getOrders();
        for(int i = 0; i< orders.size(); i++){
            System.out.println(orders.get(i).tradingSymbol+" "+orders.get(i).orderId+" "+orders.get(i).parentOrderId+" "+orders.get(i).orderType+" "+orders.get(i).averagePrice+" "+orders.get(i).exchangeTimestamp);
        }
        System.out.println("list of orders size is "+orders.size());
    }

    
    public List<Order> getOrdersZerodha(KiteConnect kiteConnect) throws KiteException, IOException {
        // Get orders returns order model which will have list of orders inside, which can be accessed as follows,
        List<Order> orders = kiteConnect.getOrders();
        for(int i = 0; i< orders.size(); i++){
            System.out.println(orders.get(i).tradingSymbol+" "+orders.get(i).orderId+" "+orders.get(i).parentOrderId+" "+orders.get(i).orderType+" "+orders.get(i).averagePrice+" "+orders.get(i).exchangeTimestamp);
        }
        System.out.println("list of orders size is "+orders.size());
        return orders;
    }
    
    /** Get order details*/
    public void getOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        List<Order> orders = kiteConnect.getOrderHistory("180111000561605");
        for(int i = 0; i< orders.size(); i++){
            System.out.println(orders.get(i).orderId+" "+orders.get(i).status);
        }
        System.out.println("list size is "+orders.size());
    }

    /** Get tradebook*/
    public void getTrades(KiteConnect kiteConnect) throws KiteException, IOException {
        // Returns tradebook.
        List<Trade> trades = kiteConnect.getTrades();
        for (int i=0; i < trades.size(); i++) {
            System.out.println(trades.get(i).tradingSymbol+" "+trades.size());
        }
        System.out.println(trades.size());
    }
    
    
    public List<Trade> getTradesZerodha(KiteConnect kiteConnect) throws KiteException, IOException {
        // Returns tradebook.
        List<Trade> trades = kiteConnect.getTrades();
//        for (int i=0; i < trades.size(); i++) {
//            System.out.println(trades.get(i).tradingSymbol+" "+trades.size());
//        }
//        System.out.println(trades.size());
        
        return trades;
    }

    /** Get trades for an order.*/
    public void getTradesWithOrderId(KiteConnect kiteConnect) throws KiteException, IOException {
        // Returns trades for the given order.
        List<Trade> trades = kiteConnect.getOrderTrades("180111000561605");
        System.out.println(trades.size());
    }

    /** Modify order.*/
    public void modifyOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        // Order modify request will return order model which will contain only order_id.
        OrderParams orderParams =  new OrderParams();
        orderParams.quantity = 1;
        orderParams.orderType = Constants.ORDER_TYPE_LIMIT;
        orderParams.tradingsymbol = "ASHOKLEY";
        orderParams.product = Constants.PRODUCT_CNC;
        orderParams.exchange = Constants.EXCHANGE_NSE;
        orderParams.transactionType = Constants.TRANSACTION_TYPE_BUY;
        orderParams.validity = Constants.VALIDITY_DAY;
        orderParams.price = 122.25;

        Order order21 = kiteConnect.modifyOrder("180116000984900", orderParams, Constants.VARIETY_REGULAR);
        System.out.println(order21.orderId);
    }

    /** Modify first leg bracket order.*/
    public void modifyFirstLegBo(KiteConnect kiteConnect) throws KiteException, IOException {
        OrderParams orderParams = new OrderParams();
        orderParams.quantity = 1;
        orderParams.price = 31.0;
        orderParams.transactionType = Constants.TRANSACTION_TYPE_BUY;
        orderParams.tradingsymbol = "SOUTHBANK";
        orderParams.exchange = Constants.EXCHANGE_NSE;
        orderParams.validity = Constants.VALIDITY_DAY;
        orderParams.product = Constants.PRODUCT_MIS;
        orderParams.tag = "myTag";
        orderParams.triggerPrice = 0.0;

        Order order = kiteConnect.modifyOrder("180116000798058", orderParams, Constants.VARIETY_BO);
        System.out.println(order.orderId);
    }

    public void modifySecondLegBoSLM(KiteConnect kiteConnect) throws KiteException, IOException {

        OrderParams orderParams = new OrderParams();
        orderParams.parentOrderId = "180116000798058";
        orderParams.tradingsymbol = "SOUTHBANK";
        orderParams.exchange = Constants.EXCHANGE_NSE;
        orderParams.product = Constants.PRODUCT_MIS;
        orderParams.validity = Constants.VALIDITY_DAY;
        orderParams.triggerPrice = 30.5;
        orderParams.price = 0.0;
        orderParams.orderType = Constants.ORDER_TYPE_SLM;
        orderParams.transactionType = Constants.TRANSACTION_TYPE_SELL;

        Order order = kiteConnect.modifyOrder("180116000812154", orderParams, Constants.VARIETY_BO);
        System.out.println(order.orderId);
    }

    public void modifySecondLegBoLIMIT(KiteConnect kiteConnect) throws KiteException, IOException {
        OrderParams orderParams =  new OrderParams();
        orderParams.parentOrderId = "180116000798058";
        orderParams.tradingsymbol = "SOUTHBANK";
        orderParams.exchange = Constants.EXCHANGE_NSE;
        orderParams.quantity =  1;
        orderParams.product = Constants.PRODUCT_MIS;
        orderParams.validity = Constants.VALIDITY_DAY;
        orderParams.price = 35.3;
        orderParams.orderType = Constants.ORDER_TYPE_LIMIT;
        orderParams.transactionType = Constants.TRANSACTION_TYPE_SELL;

        Order order = kiteConnect.modifyOrder("180116000812153", orderParams, Constants.VARIETY_BO);
        System.out.println(order.orderId);
    }

    /** Cancel an order*/
    public void cancelOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        // Order modify request will return order model which will contain only order_id.
        // Cancel order will return order model which will only have orderId.
        Order order2 = kiteConnect.cancelOrder("180116000727266", Constants.VARIETY_REGULAR);
        System.out.println(order2.orderId);
    }

    public void exitBracketOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        Order order = kiteConnect.cancelOrder("180116000812153","180116000798058", Constants.VARIETY_BO);
        System.out.println(order.orderId);
    }

    /** Get all positions.*/
    public void getPositions(KiteConnect kiteConnect) throws KiteException, IOException {
        // Get positions returns position model which contains list of positions.
        Map<String, List<Position>> position = kiteConnect.getPositions();
        System.out.println(position.get("net").size());
        System.out.println(position.get("day").size());
    }

    /** Get holdings.*/
    public void getHoldings(KiteConnect kiteConnect) throws KiteException, IOException {
        // Get holdings returns holdings model which contains list of holdings.
        List<Holding> holdings = kiteConnect.getHoldings();
        System.out.println(holdings.size());
    }

    /** Converts position*/
    public void converPosition(KiteConnect kiteConnect) throws KiteException, IOException {
        //Modify product can be used to change MIS to NRML(CNC) or NRML(CNC) to MIS.
        JSONObject jsonObject6 = kiteConnect.convertPosition("ASHOKLEY", Constants.EXCHANGE_NSE, Constants.TRANSACTION_TYPE_BUY, Constants.POSITION_DAY, Constants.PRODUCT_MIS, Constants.PRODUCT_CNC, 1);
        System.out.println(jsonObject6);
    }

    /** Get all instruments that can be traded using kite connect.*/
    public void getAllInstruments(KiteConnect kiteConnect) throws KiteException, IOException {
        // Get all instruments list. This call is very expensive as it involves downloading of large data dump.
        // Hence, it is recommended that this call be made once and the results stored locally once every morning before market opening.
        List<Instrument> instruments = kiteConnect.getInstruments();
        System.out.println(instruments.size());
    }

    /** Get instruments for the desired exchange.*/
    public void getInstrumentsForExchange(KiteConnect kiteConnect) throws KiteException, IOException {
        // Get instruments for an exchange.
        List<Instrument> nseInstruments = kiteConnect.getInstruments("CDS");
        System.out.println(nseInstruments.size());
    }

    /** Get quote for a scrip.*/
    public void getQuote(KiteConnect kiteConnect) throws KiteException, IOException {
        // Get quotes returns quote for desired tradingsymbol.
        String[] instruments = {"256265","BSE:INFY", "NSE:APOLLOTYRE", "NSE:NIFTY 50"};
        Map<String, Quote> quotes = kiteConnect.getQuote(instruments);
        System.out.println(quotes.get("NSE:APOLLOTYRE").instrumentToken+"");
        System.out.println(quotes.get("NSE:APOLLOTYRE").oi +"");
        System.out.println(quotes.get("NSE:APOLLOTYRE").depth.buy.get(4).getPrice());
        System.out.println(quotes.get("NSE:APOLLOTYRE").timestamp);
    }

    /* Get ohlc and lastprice for multiple instruments at once.
     * Users can either pass exchange with tradingsymbol or instrument token only. For example {NSE:NIFTY 50, BSE:SENSEX} or {256265, 265}*/
    public void getOHLC(KiteConnect kiteConnect) throws KiteException, IOException {
        String[] instruments = {"256265","BSE:INFY", "NSE:INFY", "NSE:NIFTY 50"};
        System.out.println(kiteConnect.getOHLC(instruments).get("256265").lastPrice);
        System.out.println(kiteConnect.getOHLC(instruments).get("NSE:NIFTY 50").ohlc.open);
    }

    /** Get last price for multiple instruments at once.
     * USers can either pass exchange with tradingsymbol or instrument token only. For example {NSE:NIFTY 50, BSE:SENSEX} or {256265, 265}*/
    public void getLTP(KiteConnect kiteConnect) throws KiteException, IOException {
        String[] instruments = {"256265","BSE:INFY", "NSE:INFY", "NSE:NIFTY 50"};
        System.out.println(kiteConnect.getLTP(instruments).get("256265").lastPrice);
    }

    /** Get historical data for an instrument.*/
    public void getHistoricalData(KiteConnect kiteConnect) throws KiteException, IOException {
        /** Get historical data dump, requires from and to date, intrument token, interval, continuous (for expired F&O contracts)
         * returns historical data object which will have list of historical data inside the object.*/
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date from =  new Date();
        Date to = new Date();
        try {
            from = formatter.parse("2015-01-01 09:15:00");
            to = formatter.parse("2016-12-31 15:30:00");
        }catch (ParseException e) {
            e.printStackTrace();
        }
        HistoricalData historicalData = kiteConnect.getHistoricalData(from, to, "52737", "minute", false);
        System.out.println(historicalData.dataArrayList.size());
        System.out.println(historicalData.dataArrayList.get(0).volume);
        System.out.println(historicalData.dataArrayList.get(historicalData.dataArrayList.size() - 1).volume);
        
        for(HistoricalData bb :historicalData.dataArrayList)
        {
        	System.out.println(bb.close+","+bb.volume+","+bb.timeStamp);
        }
    }

    /** Logout user.*/
    public void logout(KiteConnect kiteConnect) throws KiteException, IOException {
        /** Logout user and kill session. */
        JSONObject jsonObject10 = kiteConnect.logout();
        System.out.println(jsonObject10);
    }

    /** Retrieve mf instrument dump */
    public void getMFInstruments(KiteConnect kiteConnect) throws KiteException, IOException {
        List<MFInstrument> mfList = kiteConnect.getMFInstruments();
        System.out.println("size of mf instrument list: "+mfList.size());
    }

    /* Get all mutualfunds holdings */
    public void getMFHoldings(KiteConnect kiteConnect) throws KiteException, IOException {
        List<MFHolding> MFHoldings = kiteConnect.getMFHoldings();
        System.out.println("mf holdings "+ MFHoldings.size());
    }

    /* Place a mutualfunds order */
    public void placeMFOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        System.out.println("place order: "+ kiteConnect.placeMFOrder("INF174K01LS2", Constants.TRANSACTION_TYPE_BUY, 5000, 0, "myTag").orderId);
    }

    /* cancel mutualfunds order */
    public void cancelMFOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        kiteConnect.cancelMFOrder("668604240868430");
        System.out.println("cancel order successful");
    }

    /* retrieve all mutualfunds orders */
    public void getMFOrders(KiteConnect kiteConnect) throws KiteException, IOException {
        List<MFOrder> MFOrders = kiteConnect.getMFOrders();
        System.out.println("mf orders: "+ MFOrders.size());
    }

    /* retrieve individual mutualfunds order */
    public void getMFOrder(KiteConnect kiteConnect) throws KiteException, IOException {
        System.out.println("mf order: "+ kiteConnect.getMFOrder("106580291331583").tradingsymbol);
    }

    /* place mutualfunds sip */
    public void placeMFSIP(KiteConnect kiteConnect) throws KiteException, IOException {
        System.out.println("mf place sip: "+ kiteConnect.placeMFSIP("INF174K01LS2", "monthly", 1, -1, 5000, 1000).sipId);
    }

    /* modify a mutual fund sip */
    public void modifyMFSIP(KiteConnect kiteConnect) throws KiteException, IOException {
        kiteConnect.modifyMFSIP("weekly", 1, 5, 1000, "active", "504341441825418");
    }

    /* cancel a mutualfunds sip */
    public void cancelMFSIP(KiteConnect kiteConnect) throws KiteException, IOException {
        kiteConnect.cancelMFSIP("504341441825418");
        System.out.println("cancel sip successful");
    }

    /* retrieve all mutualfunds sip */
    public void getMFSIPS(KiteConnect kiteConnect) throws KiteException, IOException {
        List<MFSIP> sips = kiteConnect.getMFSIPs();
        System.out.println("mf sips: "+ sips.size());
    }

    /* retrieve individual mutualfunds sip */
    public void getMFSIP(KiteConnect kiteConnect) throws KiteException, IOException {
        System.out.println("mf sip: "+ kiteConnect.getMFSIP("291156521960679").instalments);
    }

    
    
    static KiteTicker tickerProvider;

    
    public static void StopTicker()
    {
    	if(tickerProvider!=null)
    	tickerProvider.disconnect();
    	
    	System.out.println("Ticker Stopped.....");
    }
    
    /** Demonstrates com.zerodhatech.ticker connection, subcribing for instruments, unsubscribing for instruments, set mode of tick data, com.zerodhatech.ticker disconnection*/
    public void tickerUsage(KiteConnect kiteConnect, final ArrayList<Long> tokens) throws IOException, WebSocketException, KiteException {
        /** To get live price use websocket connection.
         * It is recommended to use only one websocket connection at any point of time and make sure you stop connection, once user goes out of app.
         * custom url points to new endpoint which can be used till complete Kite Connect 3 migration is done. */
       tickerProvider = new KiteTicker(kiteConnect.getAccessToken(), kiteConnect.getApiKey());
        System.out.println("1");
        tickerProvider.setOnConnectedListener(new OnConnect() {
            @Override
            public void onConnected() {
                /** Subscribe ticks for token.
                 * By default, all tokens are subscribed for modeQuote.
                 * */
//            	System.out.println("2");
                tickerProvider.subscribe(tokens);
//                System.out.println(tokens);
                tickerProvider.setMode(tokens, KiteTicker.modeFull);
            }
        });

        tickerProvider.setOnDisconnectedListener(new OnDisconnect() {
            @Override
            public void onDisconnected() {
                // your code goes here
            }
        });

        /** Set listener to get order updates.*/
        tickerProvider.setOnOrderUpdateListener(new OnOrderUpdate() {
            @Override
            public void onOrderUpdate(Order order) {
                System.out.println("order update "+order.orderId);
            }
        });

        /** Set error listener to listen to errors.*/
        tickerProvider.setOnErrorListener(new OnError() {
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

        tickerProvider.setOnTickerArrivalListener(new OnTicks() {
            @Override
            public void onTicks(ArrayList<Tick> ticks) {
            	System.out.println("------------------------------------------------------------------------");
                NumberFormat formatter = new DecimalFormat();
                System.out.println("ticks size "+ticks.size());
                if(ticks.size() > 0) {
                	for(int i=0;i<ticks.size();i++) {
//                		System.out.println("aaaaaaaaaaaaa"+i);
                		
                		Long instrument_token_tmp= ticks.get(i).getInstrumentToken();
                		
                		Stock ob = Stock.stock_list.get(instrument_token_tmp);
                		
                		ob.setLTP(ticks.get(i).getLastTradedPrice());
                		ob.setTime_stamp(ticks.get(i).getTickTimestamp()); 
            			ob.setCurrent_volume(ticks.get(i).getVolumeTradedToday());
            			
            			
//            			 if(Stock.Print_TimeStamp.after(new Date()))
//            			  {
//            				 System.out.println("----Will print for 2 minutes-------");
//            				 System.out.println(Stock.name_list.get(ob.getInstrument_token())+" ,******* High_now->  "+ob.getHigh_counter()+"   ******** , Low->"+ob.getLow_counter()+" , LTP->"+ob.getLTP()+" , Time->"+ticks.get(i).getTickTimestamp()+",  Volume Today--->   "+ticks.get(i).getVolumeTradedToday()+" Diff of last 10 highs->"+ob.getHigh_queue().getDifference()+" Sec");
//            				 
//            			  }
            			
            			
                		
                		if ( Double.compare(ticks.get(i).getLastTradedPrice(),ob.getHigh()) > 0 )
//                		if( ticks.get(i).getLastTradedPrice() > ob.getHigh())
                		{
                			ob.setHigh(ticks.get(i).getLastTradedPrice());
                			ob.setHigh_counter(ob.getHigh_counter()+1);
                			ob.setFlag('H'); // H means high 	
                			
//                			if((ticks.get(i).getTickTimestamp()==null))
//                			{
//                				ob.getHigh_queue().add(new Date());
//                			}
//                			else
//                			{
//                				ob.getHigh_queue().add(ticks.get(i).getTickTimestamp());
//                			}
                			
                			if( (ob.getHigh_counter() >=25)  )
                			{
//                				if(ob.getHigh_counter() ==33)
//                				{
//                					System.out.println("##################");
//                				}	
                				System.out.println("Dynamic Val->"+getDynamicValue()); 
                			 System.out.println("Token No->"+ob.getInstrument_token()+"  "+Stock.name_list.get(ob.getInstrument_token())+" ,******* High_now->  "+ob.getHigh_counter()+"   ******** , Low->"+ob.getLow_counter()+" , LTP->"+ob.getLTP()+" , Time->"+ticks.get(i).getTickTimestamp()+",  Volume Today--->   "+ticks.get(i).getVolumeTradedToday());
//                			 if(ob.getHigh_counter() ==33)
//             				{
//             					System.out.println("##################");
//             				}
                			}
//                			if((ob.getHigh_counter() >=25) && (ob.getCurrent_volume() * ob.getLTP() > getDynamicValue()) )
                			if( (ob.getHigh_counter() >=25) && (Double.compare((ob.getCurrent_volume() * ob.getLTP()), getDynamicValue()) > 0 ) )	
//                			    if( (ob.getHigh_counter() >=3)  )
                			        {
                				        TickerEndpoint.sendData(ob.getNewDummyObj());	
                					}
                			
                		}
                		
                		if ( Double.compare(ticks.get(i).getLastTradedPrice(),ob.getLow()) < 0 )
//                		if( ticks.get(i).getLastTradedPrice() < ob.getLow())
                		{
                			ob.setLow(ticks.get(i).getLastTradedPrice());
                			ob.setLow_counter(ob.getLow_counter()+1);
                			ob.setFlag('L'); // L means low 
                			
//                			if(ticks.get(i).getTickTimestamp()==null)
//                			{
//                				ob.getLow_queue().add(new Date());
//                			}
//                			else
//                			{
//                				ob.getLow_queue().add(ticks.get(i).getTickTimestamp());
//                			}
                			 
                			if( (ob.getLow_counter() >=25) && (ob.getCurrent_volume() * ob.getLTP() > getDynamicValue()) )
//                			if( (ob.getLow_counter() >=3) )
                			{
//                				if(ob.getLow_counter() ==33)
//                				{
//                					System.out.println("##################");
//                				}
                				System.out.println("Dynamic Val->"+getDynamicValue());
                				System.out.println("Token No->"+ob.getInstrument_token()+" "+Stock.name_list.get(ob.getInstrument_token())+" , High->"+ob.getHigh_counter()+" ,******** Low_now->  "+ob.getLow_counter()+"   ********* , LTP->"+ob.getLTP()+" , Time->"+ticks.get(i).getTickTimestamp()+",  Volume Today--->   "+ticks.get(i).getVolumeTradedToday());
//                				if(ob.getLow_counter() ==33)
//                				{
//                					System.out.println("##################");
//                				}
                			}
                			
                			if( (ob.getLow_counter() >=25) && (Double.compare((ob.getCurrent_volume() * ob.getLTP()), getDynamicValue()) > 0 ) )
//                			if( (ob.getLow_counter() >=3	) )
                					{
                				        TickerEndpoint.sendData(ob.getNewDummyObj());	
                					}
                			
                			 
                		}                		                		
                		
                		if(Stock.Print_counter < 500)
                		{
                			System.out.println("-==-=-=-=-=-IN COUNTER CHECK=-=-=-=-=-=-=-=");
                			System.out.println("Token No->"+ob.getInstrument_token()+" "+Stock.name_list.get(ob.getInstrument_token())+" , High->"+ob.getHigh_counter()+" ,******** Low_now->  "+ob.getLow_counter()+"   ********* , LTP->"+ob.getLTP()+" , Time->"+ticks.get(i).getTickTimestamp()+",  Volume Today--->   "+ticks.get(i).getVolumeTradedToday());
                			Stock.Print_counter++;
                		}
                		
                		Stock.stock_list.put(instrument_token_tmp,ob);
//                		save_State(ob);
                		 
                	        	
                		
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
                }
            }
        });
        // Make sure this is called before calling connect.
        tickerProvider.setTryReconnection(true);
        //maximum retries and should be greater than 0
        tickerProvider.setMaximumRetries(10);
        //set maximum retry interval in seconds
        tickerProvider.setMaximumRetryInterval(30);

        /** connects to com.zerodhatech.com.zerodhatech.ticker server for getting live quotes*/
        tickerProvider.connect();

        /** You can check, if websocket connection is open or not using the following method.*/
        boolean isConnected = tickerProvider.isConnectionOpen();
//        System.out.println(isConnected+"sdfsdf");

        /** set mode is used to set mode in which you need tick for list of tokens.
         * Ticker allows three modes, modeFull, modeQuote, modeLTP.
         * For getting only last traded price, use modeLTP
         * For getting last traded price, last traded quantity, average price, volume traded today, total sell quantity and total buy quantity, open, high, low, close, change, use modeQuote
         * For getting all data with depth, use modeFull*/
        tickerProvider.setMode(tokens, KiteTicker.modeLTP);

        // Unsubscribe for a token.
//        tickerProvider.unsubscribe(tokens);
//        System.out.println("4");
        // After using com.zerodhatech.com.zerodhatech.ticker, close websocket connection.
//        tickerProvider.disconnect();
//        System.out.println("5");
    }

	public void oneMinBreadth(KiteConnect kiteConnect, final ArrayList<Long> tokens)throws IOException, WebSocketException, KiteException {
		final KiteTicker tickerProvider = new KiteTicker(kiteConnect.getAccessToken(), kiteConnect.getApiKey());
	    
		 tickerProvider.setOnConnectedListener(new OnConnect() {
	            @Override
	            public void onConnected() {
	                tickerProvider.subscribe(tokens);
	                tickerProvider.setMode(tokens, KiteTicker.modeFull);
	            }
	        });
		 
		 tickerProvider.setOnDisconnectedListener(new OnDisconnect() {
	            @Override
	            public void onDisconnected() {
	                // your code goes here
	            }
	        });

	        /** Set listener to get order updates.*/
	        tickerProvider.setOnOrderUpdateListener(new OnOrderUpdate() {
	            @Override
	            public void onOrderUpdate(Order order) {
	                System.out.println("order update "+order.orderId);
	            }
	        });

	        /** Set error listener to listen to errors.*/
	        tickerProvider.setOnErrorListener(new OnError() {
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
	        
	        tickerProvider.setOnTickerArrivalListener(new OnTicks() {
	            @Override
	            public void onTicks(ArrayList<Tick> ticks) {
	            	System.out.println("------------------------------------------------------------------------");
	                NumberFormat formatter = new DecimalFormat();
	                System.out.println("ticks size "+ticks.size());
	                if(ticks.size() > 0) {
	                	for(int i=0;i<ticks.size();i++) {
//	                		System.out.println("aaaaaaaaaaaaa"+i);
	                		
	                		
	                		
	                	
	                		
	                		
//	                		System.out.println(ob.getLow());
	                		
	                		
	                		
//	                	  System.out.println("token number "+ticks.get(i).getInstrumentToken());
//	                    System.out.println("last price "+ticks.get(i).getLastTradedPrice());
//	                    System.out.println("open interest "+formatter.format(ticks.get(i).getOi()));
//	                    System.out.println("day high OI "+formatter.format(ticks.get(i).getOpenInterestDayHigh()));
//	                    System.out.println("day low OI "+formatter.format(ticks.get(i).getOpenInterestDayLow()));
//	                    System.out.println("change "+formatter.format(ticks.get(i).getChange()));
//	                    System.out.println("tick timestamp "+ticks.get(i).getTickTimestamp());
//	                    System.out.println("tick timestamp date "+ticks.get(i).getTickTimestamp());
//	                    System.out.println("last traded time "+ticks.get(i).getLastTradedTime());
//	                    System.out.println(ticks.get(i).getMarketDepth().get("buy").size());
	                	}
	                }
	            }
	        });
	        

		
	}

	public void fiveMinBreadth(KiteConnect kiteConnect, ArrayList<Long> tokens) throws JSONException, IOException, KiteException {
		
		/** Get historical data dump, requires from and to date, intrument token, interval, continuous (for expired F&O contracts)
         * returns historical data object which will have list of historical data inside the object.*/
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date from =  new Date();
        Date to = new Date();
        try {
            from = formatter.parse("2019-07-19 14:00:00");
            to = formatter.parse("2019-07-19 14:50:00");
        }catch (ParseException e) {
            e.printStackTrace();
        }
        HistoricalData historicalData = kiteConnect.getHistoricalData(from, to, "884737", "minute", false);
        System.out.println(historicalData.dataArrayList.size());
        System.out.println(historicalData.dataArrayList.get(0).volume);
        System.out.println(historicalData.dataArrayList.get(historicalData.dataArrayList.size() - 1).volume);
        
        for(HistoricalData bb :historicalData.dataArrayList)
        {
        	System.out.println(bb.close+","+bb.volume+","+bb.timeStamp);
        }
		
	}
	
	public void getHistoricalData30min(KiteConnect kiteConnect) throws KiteException, IOException, SQLException, JSONException, ParseException {

        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date from =  new Date();

        Date to = new Date();

//        try {
//
//            from = formatter.parse("2019-06-01 09:15:00");
//
//            to = formatter.parse("2019-08-20 17:30:00");
//
//        }catch (ParseException e) {
//
//            e.printStackTrace();
//
//        }

        Connection con = C3poDataSource.getConnection();

        PreparedStatement pp = con.prepareStatement("select * from nse_cash");
        
        ResultSet Rs = pp.executeQuery();
        
        Calendar cal = Calendar.getInstance();
       
//    	Iterator<Long> it = tokens.iterator(); 

//        while (it.hasNext()) {
        while (Rs.next()) {

//        	System.out.println(it.next().toString()); 

//        	String a1 = it.next().toString();  
        	 if(Rs.getString("h_30")!=null)
        	 {
        		 cal.setTime(formatter.parse(Rs.getString("h_30")));
        		 cal.add(Calendar.MINUTE, 31);
        	 }
        	 else
        	 {
        		 cal.setTime(formatter.parse("2019-06-01 09:15:00"));
        	 }
        	 
        	 
        	 
//        	 System.out.println(Rs.getString("h_30"));
//        	 System.out.println(cal.getTime());
//        	 
//        	 System.exit(0);
//        	 HistoricalData historicalData = kiteConnect.getHistoricalData(formatter.parse(formatter.format(cal.getTime())), formatter.parse((formatter.format(new Date()))), Rs.getString("instrument_token"), "30minute", false);
             
        	 HistoricalData historicalData = kiteConnect.getHistoricalData(cal.getTime(), new Date(), Rs.getString("instrument_token"), "30minute", false);
//        	HistoricalData historicalData = kiteConnect.getHistoricalData(from, to, Rs.getString("instrument_token"), "30minute", false);
             Long Temp_instrument_token = Rs.getLong("instrument_token");
             java.sql.Timestamp Last_date_temp = null;
             ArrayList<HistoricalData> lst = (ArrayList<HistoricalData>) historicalData.dataArrayList;
//             if(lst.size()==0)
//             {
//            	
//            	 from = formatter.parse("2019-06-15 09:15:00"); 
//            	 System.out.println("Not Found For---->>>"+Rs.getString("instrument_token")+" trying with date ->"+from);
//            	 historicalData = kiteConnect.getHistoricalData(from, new Date(), Rs.getString("instrument_token"), "30minute", false);
//            	 lst = (ArrayList<HistoricalData>) historicalData.dataArrayList;
//             }
//             for(HistoricalData bb :historicalData.dataArrayList)
               if( lst.size()>0)
               {
			            	   for(HistoricalData bb : lst)
			                   {
			                  	 
			                  	
			//                  	 System.out.println(bb.timeStamp);
			                  	 System.out.println(bb.open);
			//                  	 System.out.println(bb.volume);
			                  	 String sql = "INSERT INTO historical_30min (instrument_token,timeStamp,open,high,low,close,volume)\n" + 
			
			                    			"VALUES (?,?,?,?,?,?,?);";
			
			
			
			                    	PreparedStatement preparedStatement = con.prepareStatement(sql);
			
			
			
			                    	preparedStatement.setLong(1, Rs.getLong("instrument_token"));
			
			                    	SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			
			                    	Date ts = new Date();
			
			                    	try {
			           				ts = sdf3.parse(bb.timeStamp); 
			           			} catch (ParseException e) {
			
			           				// TODO Auto-generated catch block
			                         System.out.println("----parse Exception-----");
			           				e.printStackTrace();
			
			           			}
			                    	catch (Exception e) {
			                    		System.out.println("-=-=-=Exception Occured-=-=-=--=-");
			                    		e.printStackTrace();
			                    	}
			
			                    	java.sql.Timestamp ts1 = new java.sql.Timestamp(ts.getTime());
			
			                    	preparedStatement.setTimestamp(2, ts1);
			
			                    	preparedStatement.setDouble(3, bb.open);
			
			                    	preparedStatement.setDouble(4, bb.high);
			
			                    	preparedStatement.setDouble(5, bb.low);
			
			                    	preparedStatement.setDouble(6, bb.close);
			
			                    	preparedStatement.setLong(7, bb.volume);
			
			                    	Last_date_temp = ts1;
			                    	preparedStatement.executeUpdate();
			                    	
			
			                   }
               }
               if (Last_date_temp!=null) {
		             PreparedStatement pstmt_fin = con.prepareStatement("update nse_cash set h_30 = ? where instrument_token= ?");
		             pstmt_fin.setTimestamp(1, Last_date_temp);
		             pstmt_fin.setLong(2, Temp_instrument_token);
		             pstmt_fin.executeUpdate();
               }
             

        }
        System.out.println("finishes 30 min");
       con.close();
       

    }
	
	
	public void getHistoricalDataday(KiteConnect kiteConnect) throws KiteException, IOException, SQLException, ParseException {

        /** Get historical data dump, requires from and to date, intrument token, interval, continuous (for expired F&O contracts)

         * returns historical data object which will have list of historical data inside the object.   checking for 408065*/

    	
		
            

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        
        Date from =  new Date();

        Date to = new Date();

//        try {

//            from = formatter.parse("2019-08-19 00:00:00");
//
//            to = formatter.parse("2019-08-19 16:50:00");
            
//            from = formatter.parse("2019-06-01 00:00:00");
//
//            to = formatter.parse("2019-08-21 00:00:00");

//        }catch (ParseException e) {
//
//            e.printStackTrace();
//
//        }

        Connection con = C3poDataSource.getConnection();

        PreparedStatement pp = con.prepareStatement("select * from nse_cash");
        
        ResultSet Rs = pp.executeQuery();
        
        Calendar cal = Calendar.getInstance();
       
//    	Iterator<Long> it = tokens.iterator(); 

//        while (it.hasNext()) {
        while (Rs.next()) {

//        	System.out.println(it.next().toString()); 

//        	String a1 = it.next().toString();  
        	 
//        	 cal.setTime(formatter1.parse(Rs.getString("h_1")));
//        	 cal.add(Calendar.DATE, 1);

        	 if(Rs.getString("h_1")!=null)
        	 {
        		 cal.setTime(formatter1.parse(Rs.getString("h_1")));
            	 cal.add(Calendar.DATE, 1);
        	 }
        	 else
        	 {
        		 cal.setTime(formatter.parse("2019-06-01 00:00:00"));
        	 }
        	 
        	 
//             System.out.println(cal.getTime());
        	 
//        	String a1 = it.next().toString();

        	 HistoricalData historicalData = kiteConnect.getHistoricalData(cal.getTime(), to, Rs.getString("instrument_token"), "day", false);
        	 Long Temp_instrument_token = Rs.getLong("instrument_token");
             java.sql.Timestamp Last_date_temp = null;
             

             for(HistoricalData bb :historicalData.dataArrayList)

             {
            	 


	            	 String sql = "INSERT INTO historical_day (instrument_token,timeStamp,open,high,low,close,volume)\n" + 
	
	              			"VALUES (?,?,?,?,?,?,?);";
	
	
	
	              	PreparedStatement preparedStatement = con.prepareStatement(sql);
	
	
	
	              	preparedStatement.setLong(1, Rs.getLong("instrument_token"));
	
	              	SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	
	              	Date ts = new Date();
	
	              	try {
	
	              		
	
	     				ts = sdf3.parse(bb.timeStamp);
	
	     				
	
	     			} catch (ParseException e) {
	
	     				// TODO Auto-generated catch block
	
	     				e.printStackTrace();
	
	     			}
	
	              	java.sql.Timestamp ts1 = new java.sql.Timestamp(ts.getTime());
	
	              	
	
	              	preparedStatement.setTimestamp(2, ts1);
	
	              	preparedStatement.setDouble(3, bb.open);
	
	              	preparedStatement.setDouble(4, bb.high);
	
	              	preparedStatement.setDouble(5, bb.low);
	
	              	preparedStatement.setDouble(6, bb.close);
	
	              	preparedStatement.setLong(7, bb.volume);
	              	
	              	Last_date_temp = ts1;
	              	
	              	preparedStatement.executeUpdate();

             }
             if (Last_date_temp!=null) {
             PreparedStatement pstmt_fin = con.prepareStatement("update nse_cash set h_1 = ? where instrument_token= ?");
             pstmt_fin.setTimestamp(1, Last_date_temp);
             pstmt_fin.setLong(2, Temp_instrument_token);
             pstmt_fin.executeUpdate();
             }

        }
       
       System.out.println("finishes 1 day"); 
       con.close();

    }

    

    public void saveHLC()  {

        /** Save avg volume and h l c  */

    	try {

			Connection con = C3poDataSource.getConnection();

			String sql = "select distinct(instrument_token) from historical_day;";

			PreparedStatement stmt = con.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			 while(rs.next())

			    {

//			    	System.out.println(rs.getLong("instrument_token"));

			    	String sql2 = "select max(timeStamp) as max_date from historical_day where instrument_token=?";

			    	PreparedStatement stmt2 = con.prepareStatement(sql2);

			    	stmt2.setLong(1,rs.getLong("instrument_token"));

			    	ResultSet rs2 = stmt2.executeQuery();

			    	while(rs2.next()) {

//			    		System.out.println(rs2.getTimestamp("max_date"));

			    		String sql3 = "select high,low,close from historical_day where instrument_token=? and timeStamp = ?";

				    	PreparedStatement stmt3 = con.prepareStatement(sql3);

				    	stmt3.setLong(1,rs.getLong("instrument_token"));

				    	stmt3.setTimestamp(2,rs2.getTimestamp("max_date"));

				    	ResultSet rs3 = stmt3.executeQuery();

				    	while(rs3.next()) {

//				    		System.out.println(rs3.getDouble("high"));

//				    		System.out.println(rs3.getDouble("low"));

//				    		System.out.println(rs3.getDouble("close"));

				    		
				    		String sql4 = "UPDATE save_avg set high = ?, low = ?, close = ? where instrument_token = ?";

					    	PreparedStatement stmt4 = con.prepareStatement(sql4);

					    	

					    	stmt4.setDouble(1, rs3.getDouble("high"));

					    	stmt4.setDouble(2, rs3.getDouble("low"));

					    	stmt4.setDouble(3, rs3.getDouble("close"));
					    	
					    	stmt4.setLong(4,rs.getLong("instrument_token"));

					    	stmt4.executeUpdate();

				    		

				    		

				    		

				    	}

				    	

			    		

			    	}

			    	

			    }

			 con.close();

			

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

    	

    	

        

        System.out.println("finish hlc");

    }

    

    public void saveAvgVolume()  {

    	
    	
    	
    	
        /** Save avg volume and h l c  */

    	try {

			Connection con = C3poDataSource.getConnection();
			String sql1 = "select distinct(instrument_token) from historical_day;";

			PreparedStatement stmt2 = con.prepareStatement(sql1);

			ResultSet rs2 = stmt2.executeQuery();

			 while(rs2.next())

			    {
			
			String sql = "select avg(volume) as avg_vol  from (select volume from historical_day where instrument_token = ? order by timeStamp desc limit 50) tmp;";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, rs2.getLong("instrument_token"));
			ResultSet rs = stmt.executeQuery();

			 while(rs.next())

			    {

			    	String sql4 = "UPDATE save_avg SET volume = ? WHERE instrument_token = ?;";

			    	PreparedStatement stmt4 = con.prepareStatement(sql4);

			    	stmt4.setDouble(1,rs.getDouble("avg_vol"));

			    	stmt4.setLong(2,rs2.getLong("instrument_token"));

			    	stmt4.executeUpdate();

			    	

			    }
			    }
			 con.close();

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

    	

    	

        

        System.out.println("finish avg");

    }
	
    
    
    public static int getDynamicValue()
	{
		  int x = 10000000;
		  LocalTime now = LocalTime.now();
		  LocalTime t1 = LocalTime.parse( "09:15" );		
		  while(t1.isBefore(now))
		  {
			  x = x+10000000;
			  t1 = t1.plusMinutes(15);
//			  System.out.println(t1+" "+x);
		  }
		return x;
	}
	
    
//    public static int getDynamicVol()
//   	{
//   		  int x = 0;
//   		  LocalTime now = LocalTime.now();
//   		  LocalTime t1 = LocalTime.parse( "09:15" );		
//   		  while(t1.isBefore(now))
//   		  {
//   			  x = x+50;
//   			  t1 = t1.plusMinutes(15);
//   			  System.out.println(t1+" "+x);
//   		  }
//   		return x;
//   	}
	
	
    
    public static void save_State(Stock ob)
    {
//    	 System.out.println(" In .....Save State.......");
        ExecutorService pool = Executors.newCachedThreadPool();
        
				        Runnable task = new Runnable() {
				            public void run() {
				            	Connection con = null;
				            	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				            	  
				            	try
				            	{
				            	con = C3poDataSource.getConnection();
				                String sql="update current_state set LTP = ? ,high = ?,low = ?,high_counter = ?,low_counter = ?,prev_high = ?,prev_low = ?,prev_close = ?,avg_volume = ?,time_stamp = ?,current_volume = ? where instrument_token = ?";
				                
				//("+ob.getInstrument_token()+","+ob.getLTP()+","+ob.getHigh()+","+ob.getLow()+","+ob.getHigh_counter()+","+ob.getLow_counter()+","+ob.prev_high+","+ob.getPrev_low()+","+ob.getPrev_close()+","+ob.getAvg_volume()+",'"+ob.getTime_stamp()+"',"+ob.getCurrent_volume()+")";
				
				                PreparedStatement preparedStatement = con.prepareStatement(sql);
				                
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
				                preparedStatement.executeUpdate();
				                
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
