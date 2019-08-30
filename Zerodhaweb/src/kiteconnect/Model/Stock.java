package kiteconnect.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import kiteconnect.Model.StockDatatoSend;

public class Stock {
	
	public Stock()
	{
		high=0;
		low=5000000;
	}
	public Stock(long token_no)
	{
		this.instrument_token = token_no; 
		high=0;
		low=5000000;
	}
	
	
	
	
	public Stock(long instrument_token, double lTP, double high, double low, int high_counter, int low_counter,
			double prev_high, double prev_low, double prev_close, double avg_volume, Date time_stamp,
			double current_volume) {
		super();
		this.instrument_token = instrument_token;
		LTP = lTP;
		this.high = high;
		this.low = low;
		this.high_counter = high_counter;
		this.low_counter = low_counter;
		this.prev_high = prev_high;
		this.prev_low = prev_low;
		this.prev_close = prev_close;
		this.avg_volume = avg_volume;
		this.time_stamp = time_stamp;
		this.current_volume = current_volume;
	}




	public static int Print_counter;
	
//	public static ArrayList<Stock> stock_list = new ArrayList<Stock>();
	
	public static HashMap<Long, Stock> stock_list = new HashMap<Long, Stock>();
	public static HashMap<Long, String> name_list = new HashMap<Long, String>(); 
	
	public ArrayList<Ticker> minute_data = new ArrayList<Ticker>(); // for minute data calculation
	
	public LimitedSizeQueue<Date> high_queue =new LimitedSizeQueue<Date>(9);
//	public LimitedSizeQueue<Date> low_queue =new LimitedSizeQueue<Date>(9);
	
	StockDatatoSend dummy_obj=null;
	char flag;  // L- means low , H - means high
	
	
	double temp_previous_volume; // used for minute data calculation
	
//	public LimitedSizeQueue<Date> getHigh_queue() {
//		return high_queue;
//	}
//	public void setHigh_queue(LimitedSizeQueue<Date> high_queue) {
//		this.high_queue = high_queue;
//	}
//	public LimitedSizeQueue<Date> getLow_queue() {
//		return low_queue;
//	}
//	public void setLow_queue(LimitedSizeQueue<Date> low_queue) {
//		this.low_queue = low_queue;
//	}


	long instrument_token;
	double LTP;
	double high;
	double low;
	int high_counter;
	int low_counter;
	double prev_high;
	double prev_low;
	double prev_close;
	double avg_volume;
	Date time_stamp;
	double current_volume;
	
	public StockDatatoSend getDummy_obj() {
		return dummy_obj;
	}
	public void setDummy_obj(StockDatatoSend dummy_obj) {
		this.dummy_obj = dummy_obj;
	}
	public double getPrev_high() {
		return prev_high;
	}
	public void setPrev_high(double prev_high) {
		this.prev_high = prev_high;
	}
	public double getPrev_low() {
		return prev_low;
	}
	public void setPrev_low(double prev_low) {
		this.prev_low = prev_low;
	}
	public double getPrev_close() {
		return prev_close;
	}
	public void setPrev_close(double prev_close) {
		this.prev_close = prev_close;
	}


	public double getAvg_volume() {
		return avg_volume;
	}
	public void setAvg_volume(double avg_volume) {
		this.avg_volume = avg_volume;
	}
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
		dummy_obj = new StockDatatoSend(this.instrument_token,this.LTP,this.high,this.low,this.high_counter,this.low_counter,this.prev_high,this.prev_low,this.prev_close,this.avg_volume, this.flag ,this.current_volume , this.time_stamp);
		return dummy_obj;
	}
	public char getFlag() {
		return flag;
	}
	public void setFlag(char flag) {
		this.flag = flag;
	}

	public double getCurrent_volume() {
		return current_volume;
	}
	public void setCurrent_volume(double current_volume) {
		this.current_volume = current_volume;
	}
	public Date getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}
	public ArrayList<Ticker> getMinute_data() {
		return minute_data;
	}
	public void setMinute_data(ArrayList<Ticker> minute_data) {
		this.minute_data = minute_data;
	}
	public double getTemp_previous_volume() {
		return temp_previous_volume;
	}
	public void setTemp_previous_volume(double temp_previous_volume) {
		this.temp_previous_volume = temp_previous_volume;
	}
	
	
	
	
	
	
}
