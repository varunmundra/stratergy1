package rupeevest.TickData.WebScket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import kiteconnect.Model.Stock;
import kiteconnect.Model.StockDatatoSend;

@ServerEndpoint(value = "/Ticker", decoders = MessageDecoderForStock.class, encoders = MessageEncoderForStock.class)
public class TickerEndpoint {
	
	    static long lTP=50;
	    static long high=100;
	    static long low = 50;
	    static int high_counter = 0;
	    static int low_counter = 0;
		
    public Session session;
    private static final Set<TickerEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {

        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), "usr1"); // usr1 is just a random user name

//        Message message = new Message();
        StockDatatoSend mm = new StockDatatoSend();
//        message.setFrom(username);
//        message.setContent("Connected!");
        broadcast(mm);
    }

    @OnMessage
    public void onMessage(Session session, StockDatatoSend message) throws IOException, EncodeException {
//        message.setFrom(users.get(session.getId()));
    	
//    	new java.util.Timer().schedule( 
//    	        new java.util.TimerTask() {
//    	            @Override
//    	            public void run() {
//    	                // your code here
//    	            	
//    	            	try {
//    	            		
//    	            		long lTP=50;
//    	            		long high=100;
//    	            		long low = 50;
//    	            		int high_counter = 0;
//    	            		int low_counter = 0;
//    	            		
//    	            		StockDatatoSend message = new StockDatatoSend(180032, lTP++, high++, low++, high_counter++, low_counter++);
//							broadcast(message);
//						} catch (IOException | EncodeException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//    	            }
//    	        }, 
//    	        100 
//    	);
    	
//        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(this);
        StockDatatoSend message = new StockDatatoSend();
//        message.setFrom(users.get(session.getId()));
//        message.setContent("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
    
    
    public static void sendData(StockDatatoSend message)
    {
    	
//    	Timer t = new Timer();
//		t.schedule(new TimerTask() {
//		    @Override
//		    public void run() {
//		       System.out.println("Soket every 1 sec");
		       
		   
       		
//       		StockDatatoSend message = new StockDatatoSend(180032, lTP++, high++, low++, high_counter++, low_counter++);
       		
       		try {
				broadcast(message);
			}
       		catch (IOException | EncodeException e) 
       		{
				
				e.printStackTrace();
			}
//		    }
//		}, 0, 1000);
    	 
    }

    private static void broadcast(StockDatatoSend message) throws IOException, EncodeException {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote()
                        .sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
