package rupeevest.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.HistoricalData;

import kiteconnect.Model.Examples;
import sessionFactory.C3poDataSource;

@Controller
@RequestMapping("/Download")
public class DownloadController {
	
	
	@RequestMapping(value = "/DownloadDay" , method = RequestMethod.GET, produces = "application/text")
	@ResponseBody
	public String HistoricalData(HttpServletRequest req , Model model )
	{
		String responce=null;
		try
		{
		  Examples ee = new Examples();
	      responce = ee.getHistoricalDataday(TestController.kiteConnect);
		}
		catch (Exception e) {
//			e.printStackTrace();
			responce = e.getMessage();
		} 
		
		return responce;
	}
	
	
	@RequestMapping(value = "/Hlc" , method = RequestMethod.GET, produces = "application/text")
	@ResponseBody
	public String HLC(HttpServletRequest req , Model model )
	{
		String responce=null;
		try
		{
		  Examples ee = new Examples();
	      responce = ee.saveHLC();
		}
		catch (Exception e) {
//			e.printStackTrace();
			responce = e.getMessage();
		} 
		
		return responce;
	}
	
	
	@RequestMapping(value = "/AvgVolume" , method = RequestMethod.GET, produces = "application/text")
	@ResponseBody
	public String Average_Volume(HttpServletRequest req , Model model )
	{
		String responce=null;
		try
		{
		  Examples ee = new Examples();
	      responce = ee.saveAvgVolume();
		}
		catch (Exception e) {
//			e.printStackTrace();
			responce = e.getMessage();
		} 
		
		return responce;
	}

}
