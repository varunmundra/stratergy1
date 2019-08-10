package kiteconnect.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InstruDetail 
{
	@Id 
	long instrument_token;
	
	int exchange_token;
	
	String tradingsymbol;
	
	String name;
	
	double last_price;
	
	@Column(nullable=true)
	java.util.Date expiry;
	
	int strike;
	
	double tick_size;
	
	double lot_size;
	
	String instrument_type;
	
	String segment;
	
	String exchange;
		
	

	public long getInstrument_token() {
		return instrument_token;
	}

	public void setInstrument_token(long instrument_token) {
		this.instrument_token = instrument_token;
	}

	public int getExchange_token() {
		return exchange_token;
	}

	public void setExchange_token(int exchange_token) {
		this.exchange_token = exchange_token;
	}

	public String getTradingsymbol() {
		return tradingsymbol;
	}

	public void setTradingsymbol(String tradingsymbol) {
		this.tradingsymbol = tradingsymbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLast_price() {
		return last_price;
	}

	public void setLast_price(double last_price) {
		this.last_price = last_price;
	}

	public java.util.Date getExpiry() {
		return expiry;
	}

	public void setExpiry(java.util.Date expiry) {
		this.expiry = expiry;
	}

	public int getStrike() {
		return strike;
	}

	public void setStrike(int strike) {
		this.strike = strike;
	}

	public double getTick_size() {
		return tick_size;
	}

	public void setTick_size(double tick_size) {
		this.tick_size = tick_size;
	}

	public double getLot_size() {
		return lot_size;
	}

	public void setLot_size(double lot_size) {
		this.lot_size = lot_size;
	}

	public String getInstrument_type() {
		return instrument_type;
	}

	public void setInstrument_type(String instrument_type) {
		this.instrument_type = instrument_type;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	
	
	
  
}
