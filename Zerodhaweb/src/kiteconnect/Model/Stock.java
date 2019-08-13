package kiteconnect.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import kiteconnect.Model.StockDatatoSend;

public class Stock {
	
	public Stock()
	{
		high=0;
		low=0;
	}
	public Stock(long token_no)
	{
		this.instrument_token = token_no; 
		high=0;
		low=5000000;
	}
	
	
//	public static ArrayList<Stock> stock_list = new ArrayList<Stock>();
	
	public static HashMap<Long, Stock> stock_list = new HashMap<Long, Stock>();
	public static HashMap<Long, String> name_list = new HashMap<Long, String>(); 
	
	public LimitedSizeQueue<Date> high_queue =new LimitedSizeQueue<Date>(9);
	public LimitedSizeQueue<Date> low_queue =new LimitedSizeQueue<Date>(9);
	
	StockDatatoSend dummy_obj=null;
	
	
	public LimitedSizeQueue<Date> getHigh_queue() {
		return high_queue;
	}
	public void setHigh_queue(LimitedSizeQueue<Date> high_queue) {
		this.high_queue = high_queue;
	}
	public LimitedSizeQueue<Date> getLow_queue() {
		return low_queue;
	}
	public void setLow_queue(LimitedSizeQueue<Date> low_queue) {
		this.low_queue = low_queue;
	}


	long instrument_token;
	double LTP;
	double high;
	double low;
	int high_counter;
	int low_counter;
	
	public long getInstrument_token() {
		return instrument_token;
	}
	public void setInstrument_token(long instrument_token) {
		this.instrument_token = instrument_token;
	}
	public double getLTP() {
		return LTP;
	}
	public void setLTP(double lTP) {
		LTP = lTP;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public int getHigh_counter() {
		return high_counter;
	}
	public void setHigh_counter(int high_counter) {
		this.high_counter = high_counter;
	}
	public int getLow_counter() {
		return low_counter;
	}
	public void setLow_counter(int low_counter) {
		this.low_counter = low_counter;
	}
	
	public StockDatatoSend getNewDummyObj()
	{
		dummy_obj = new StockDatatoSend(this.instrument_token,this.LTP,this.high,this.low,this.high_counter,this.low_counter);
		return dummy_obj;
	}
	
	
	
	
}
