package rupeevest.TickData.WebScket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import kiteconnect.Model.StockDatatoSend;

public class MessageEncoderForStock implements Encoder.Text<StockDatatoSend> {
    @Override
    public String encode(StockDatatoSend message) throws EncodeException {
        Gson gson = new Gson();
        String json = gson.toJson(message);
        return json;
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

