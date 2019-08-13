package kiteconnect.Model;

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
			int low_counter) {
		super();
		this.instrument_token = instrument_token;
		LTP = lTP;
		this.high = high;
		this.low = low;
		this.high_counter = high_counter;
		this.low_counter = low_counter;
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
	
	
	
	

}