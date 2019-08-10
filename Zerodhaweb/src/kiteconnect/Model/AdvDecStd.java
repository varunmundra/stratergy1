package kiteconnect.Model;

import javax.persistence.Entity;

@Entity
public class AdvDecStd {
	
	java.util.Date timestamp;
	
	Long advance;
	Long decline;
	
	Long advance_vol;
	Long decline_vol;
	
	Double adv_dec;
	Double adv_dec_vol;
	
	

}
