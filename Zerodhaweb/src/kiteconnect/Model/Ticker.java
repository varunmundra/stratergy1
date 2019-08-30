package kiteconnect.Model;

import java.util.Date;

public class Ticker {

	long instrument_token;
	double LTP;
	double open;
	double high;
	double low;
	double close;
	Date time_stamp;
	double current_volume;
//	time to hold minute information in databse
	
	public Ticker() {}
	public Ticker(long instrument_token, double lTP, double open, double high, double low, double close,
			Date time_stamp, double current_volume) {
		super();
		this.instrument_token = instrument_token;
		this.LTP = lTP;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.time_stamp = time_stamp;
		this.current_volume = current_volume;
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
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
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
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
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
