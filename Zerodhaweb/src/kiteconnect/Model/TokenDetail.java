package kiteconnect.Model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
//@Table(name = "users", indexes = {
//        @Index(columnList = "token_no", name = "token_no_indx"),
//        @Index(columnList = "timeStamp", name = "time_stamp_index")
//})
public class TokenDetail {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	long token_no;
	String token_name;
	
	double open;
	double high;
	double low;
	double close;
	double volume;
	Date timeStamp;
    String time;

	public TokenDetail() {
		
	}


	public TokenDetail(long token_no, String token_name, double open, double high, double low, double close,
			double volume, Date timeStamp,String time) {
		super();
		this.token_no = token_no;
		this.token_name = token_name;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.timeStamp = timeStamp;
		this.time = time;
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public long getToken_no() {
		return token_no;
	}


	public void setToken_no(long token_no) {
		this.token_no = token_no;
	}


	public String getToken_name() {
		return token_name;
	}


	public void setToken_name(String token_name) {
		this.token_name = token_name;
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


	public Date getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}
	
	
	
	
	
	
	
	
	

}
