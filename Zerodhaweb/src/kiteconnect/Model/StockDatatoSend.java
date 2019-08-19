package kiteconnect.Model;

import java.util.Date;

public class StockDatatoSend {
	public StockDatatoSend()
	{
		high=0;
		low=0;
	}
	public StockDatatoSend(long token_no)
	{
		this.instrument_token = token_no; 
		high=0;
		low=5000000;
	}
	
	
	
	public StockDatatoSend(long instrument_token, double lTP, double high, double low, int high_counter,
			int low_counter,double prev_high,double prev_low,double prev_close,double avg_volume ,char flag, double current_volume , Date time_stamp) {
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
		this.flag = flag;
		this.stock_name = Stock.name_list.get(instrument_token);
		this.current_volume = current_volume;
		this.time_stamp = time_stamp;
	}



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
	char flag;  // L- means low , H - means high
	String stock_name;
	Date time_stamp;
	double current_volume;
	
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
	public char getFlag() {
		return flag;
	}
	public void setFlag(char flag) {
		this.flag = flag;
	}
	public String getStock_name() {
		return stock_name;
	}
	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public Date getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Date time_stamp) {
		this.time_stamp = time_stamp;
	}
	public double getCurrent_volume() {
		return current_volume;
	}
	public void setCurrent_volume(double current_volume) {
		this.current_volume = current_volume;
	}
	
	
	
	

}