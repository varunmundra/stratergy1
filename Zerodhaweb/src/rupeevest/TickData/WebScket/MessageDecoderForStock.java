package rupeevest.TickData.WebScket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import kiteconnect.Model.StockDatatoSend;

public class MessageDecoderForStock implements Decoder.Text<StockDatatoSend> {
    @Override
    public StockDatatoSend decode(String s) throws DecodeException {
        Gson gson = new Gson();
        StockDatatoSend message = gson.fromJson(s, StockDatatoSend.class);
        return message;
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

