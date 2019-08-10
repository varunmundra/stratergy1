package kiteconnect.Model;

import java.util.ArrayList;
import java.util.Date;

public class LimitedSizeQueue<Date> extends ArrayList<Date> {

    private int maxSize;

    public LimitedSizeQueue(int size){
        this.maxSize = size;
    }

    public boolean add(Date k){
        boolean r = super.add(k);
        if (size() > maxSize){
            removeRange(0, size() - maxSize - 1);
        }
        return r;
    }

    public Date getYoungest() {
        return get(size() - 1);
    }

    public Date getOldest() {
        return get(0);
    }
    
    public long getDifference()
    {
    	
    	long diff = ((((java.util.Date) getYoungest()).getTime() - ((java.util.Date) getOldest()).getTime())/1000);
    	return diff;
    }
    
}
