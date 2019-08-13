package rupeevest.TickData.WebScket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import kiteconnect.Model.Stock;

public class MessageEncoder implements Encoder.Text<Stock> {
	 
    private static Gson gson = new Gson();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Stock object) throws EncodeException {
		// TODO Auto-generated method stub
		return null;
	}
 
	
	 
	
	
	
	
 
}
