package kiteconnect.Model;

import java.util.Date;

public class MinuteData {
	
	long instrument_token;
	Date time;
	double open;
	double high;
	double low;
	double close;
	double volume;
	
	
	public MinuteData()
	{
		
	}
	
	
	public MinuteData(long instrument_token, Date time, double open, double high, double low, double close,
			double volume) {
		super();
		this.instrument_token = instrument_token;
		this.time = time;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}


	public long getInstrument_token() {
		return instrument_token;
	}
	public void setInstrument_token(long instrument_token) {
		this.instrument_token = instrument_token;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
}
