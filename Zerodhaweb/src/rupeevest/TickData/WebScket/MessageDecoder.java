package rupeevest.TickData.WebScket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import kiteconnect.Model.Stock;

public class MessageDecoder implements Decoder.Text<Stock> {
	 
    private static Gson gson = new Gson();
 
    @Override
    public Stock decode(String s) throws DecodeException {
        return gson.fromJson(s, Stock.class);
    }
 
    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }
 
    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }
 
    @Override
    public void destroy() {
        // Close resources
    }
}
