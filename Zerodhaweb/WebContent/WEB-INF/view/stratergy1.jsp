<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="kiteconnect.Model.Stock" %>
<%@ page import="kiteconnect.Model.Examples" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tick Strategy</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://momentjs.com/downloads/moment-with-locales.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<style type="text/css">
.container-fluid {
min-height: 1000px;
}
h1 {
    margin-bottom: 1rem;
}
.mygreen {
 background: #99ff99;
}
.myred {
 background: #ff99cc;
}
.btn {
margin-top: 0.5rem;

}
.btn-secondary {
background: #f1f2f2;
color: #212529;
}
.table {
font-size: 13px;
}
.table tr th {
background: #f1f2f2;
}
.table-bordered td, .table-bordered th {
    border: 1px solid #ccc;
}
h5 {
    padding: 0.3rem 0;
    margin: 0;
}
.hightab_h5 {
    background: #99ff99;
}
.lowtab_h5 {
    background: #ff99cc;
}

</style>
<script type="text/javascript">

/* var mq = window.matchMedia("screen and (min-width: 768px)");
if (mq.matches) {      
        $(window).scroll(function() { 
        	var top = 0;
             top = parseInt($('#low_table').offset().top - $(window).scrollTop()); 

          if( top <= 80 ){
                $('#low_heading').addClass('fixed-top container-fluid');
                $('#low_heading').css({"padding-top": "5.5em", "background":"#fff", "z-index":"1"});
                $('#low_heading tr').css({"border-top": "1px solid #ddd"});
                //$('#comparison_basic').css({"padding-top": "16em"});
          }
          else {
                $('#low_heading').removeClass('fixed-top container-fluid');
                $('#low_heading').css({"padding-top": "0", "background":"#fff", "z-index":"1"});
                //$('#comparison_basic').css({"padding-top": "0"});
          }
    });
} */
 
/* var webSocket = new WebSocket('ws://localhost:8080/Zerodhaweb/Ticker'); */
var webSocket = new WebSocket('ws://'+window.location.host+'/Zerodhaweb/Ticker');

webSocket.onerror = function(event) {
    console.log("Socket Error Occured");
};

webSocket.onopen = function(event) {
	console.log("Web Socket Opened");
};




webSocket.onmessage = function(msg) {
   /*  onMessage(event) */
   
   console.log(msg.data);
   $("#refresh").val("updated");
   
   var stock = JSON.parse(msg.data);
   
  /*  $("#"+stock.instrument_token).remove(); */
   if(stock.flag=='L')
	{
	   /* $("#low_heading").remove(); */
	   /* $("#low_"+stock.instrument_token).remove(); */
	   var perc_change = ((stock.LTP - stock.prev_close)/(stock.prev_close))*100;
	   var avg_vol_perc = ((stock.current_volume / stock.avg_volume)*100);
	   
	  if((parseInt(avg_vol_perc) > getDynamicVolume())&&(perc_change>-10) &&(perc_change<10)) 
		   {
	    	
	    	 if(parseInt(stock.low_counter)==40)
	          {
	    		 var tab_row = "<tr id='low_"+stock.instrument_token+"' class='myred'><td>"+moment(stock.time_stamp.toString()).format('hh:mm:ss')+"</td><td>"+stock.stock_name+"</td><td>"+stock.LTP+"</td><td>"+perc_change.toFixed(2)+"</td><td>"+stock.low_counter+"</td><td>"+commaSeparateNumber(avg_vol_perc.toFixed(0))+"</td><td>"+stock.prev_low+"</td><td>"+commaSeparateNumber(stock.current_volume)+"</td></tr>";
	    	  }
	    	 else
	    	  {
	    		 var timeStamp = "-";
	    		 if ( (stock.time_stamp!=null) || (typeof(stock.time_stamp)!= 'undefined') )
	    			 {
	    			    timeStamp = moment(stock.time_stamp.toString()).format('hh:mm:ss');
	    			 }
	    		 
	    		 var tab_row = "<tr id='low_"+stock.instrument_token+"' ><td>"+timeStamp+"</td><td>"+stock.stock_name+"</td><td>"+stock.LTP+"</td><td>"+perc_change.toFixed(2)+"</td><td>"+stock.low_counter+"</td><td>"+commaSeparateNumber(avg_vol_perc.toFixed(0))+"</td><td>"+stock.prev_low+"</td><td>"+commaSeparateNumber(stock.current_volume)+"</td></tr>";	 
	    	   }
		     	   
		   
		     
		     /* $("#low_table").prepend(tab_row); */
		      $("#low_heading").after(tab_row);
		     
		     
		      /*  save_sate(stock); */
		   }
	
	}
   else if(stock.flag=='H')
	{
	  /*  $("#high_heading").remove(); */
	   /* $("#high_"+stock.instrument_token).remove(); */
	   var avg_vol_perc = ((stock.current_volume / stock.avg_volume)*100);
	   var perc_change = ((stock.LTP - stock.prev_close)/(stock.prev_close))*100;
	   
	  if((parseInt(avg_vol_perc) > getDynamicVolume())&&(perc_change<10) &&(perc_change>-10))  
	   { 
		  if(parseInt(stock.high_counter)==40)
          {
			  var tab_row1 = "<tr id='high_"+stock.instrument_token+"' class='mygreen'><td>"+moment(stock.time_stamp.toString()).format('hh:mm:ss')+"</td><td>"+stock.stock_name+"</td><td>"+stock.LTP+"</td><td>"+perc_change.toFixed(2)+"</td><td class='lw_vol'>"+stock.high_counter+"</td><td>"+commaSeparateNumber(avg_vol_perc.toFixed(0))+"</td><td>"+stock.prev_high+"</td><td>"+commaSeparateNumber(stock.current_volume)+"</td></tr>";
          }
		  else
          {
			  var timeStamp = "-";
	    		 if ( (stock.time_stamp!=null) || (typeof(stock.time_stamp)!= 'undefined') )
	    			 {
	    			    timeStamp = moment(stock.time_stamp.toString()).format('hh:mm:ss');
	    			 } 
			  var tab_row1 = "<tr id='high_"+stock.instrument_token+"'><td>"+timeStamp+"</td><td>"+stock.stock_name+"</td><td>"+stock.LTP+"</td><td>"+perc_change.toFixed(2)+"</td><td class='lw_vol'>"+stock.high_counter+"</td><td>"+commaSeparateNumber(avg_vol_perc.toFixed(0))+"</td><td>"+stock.prev_high+"</td><td>"+commaSeparateNumber(stock.current_volume)+"</td></tr>";
          }
		   	   
		  /*  $("#high_table").prepend(tab_row1); */
		   $("#high_heading").after(tab_row1);
		  
		  
		   /* save_sate(stock); */
	   }
	}
  /*  $("#"+stock.instrument_token+"_ltp").text(stock.LTP);
   $("#"+stock.instrument_token+"_high").text(stock.high);
   $("#"+stock.instrument_token+"_low").text(stock.low);
   $("#"+stock.instrument_token+"_high_counter").text(stock.high_counter);
   $("#"+stock.instrument_token+"_low_counter").text(stock.low_counter);
   
   $("#"+stock.instrument_token+"_prev_low").text(stock.prev_low);
   $("#"+stock.instrument_token+"_prev_high").text(stock.prev_high);
   $("#"+stock.instrument_token+"_prev_close").text(stock.prev_close);
   $("#"+stock.instrument_token+"_avg_volume").text(stock.avg_volume); */
  
  
  /*  console.log("#"+stock.instrument_token);*/
   
  
   
   
  /*   console.log("tick Rec"+stock.instrument_token); */
   
   
   
};


 function getDynamicVolume()
{
	
	var x=5;	
	var now = new Date(); 
	var theAdd = new Date();
	theAdd.setHours(09,15,00);
 
	while (now.getTime() > theAdd.getTime()) 
		{
		   x =  x + 5; 
		   theAdd.setMinutes(theAdd.getMinutes() + 15);
		}
	
	
	
	return x;
}

 function commaSeparateNumber(val)
 {
    x=val.toString();
     var afterPoint = '';
     if(x.indexOf('.') > 0)
        afterPoint = x.substring(x.indexOf('.'),x.length);
     x = Math.floor(x);
     x=x.toString();
     var lastThree = x.substring(x.length-3);
     var otherNumbers = x.substring(0,x.length-3);
     if(otherNumbers != '')
         lastThree = ',' + lastThree;
     var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;  
     return res;
 }
 
 function invalidate_session()
 {	 
	 $.ajax({url: "/Zerodhaweb/function/StopSession", success: function(result)
		 {
		   /* alert("coming here 2"); */
		    if(result=="success")
		    {
		    	window.location.replace("/Zerodhaweb/function/dashboard?logic=yes");	
		    }
		  }});	 
 }

 function save_state()
 {
	 $.ajax({url: "/Zerodhaweb/function/SaveStateDB", success: function(result)
		 {
		    if(result=="success")
		    {
		    	alert("state Saved");	
		    }
		    else
		    	{
		    	alert(result);
		    	}
		  }});	
	 
	 
	/*  $.ajax({url: "/Zerodhaweb/function/SaveStateDB", data:obj , type:"POST" , contentType:"application/json" , success: function(result)
		 {
		    if(result=="success")
		    {	
		    }
		  }}); */	  
 }
 

 
 $(document).ready(function() 
		 {
	      console.log($("#refresh").val());  	      
	     });

</script>


</head>
<body>
<div class="container-fluid">
<div class="row">
<div class="col-12 offset-md-3 col-md-6">
<h1 class="text-center">Tick Strategy</h1>
</div>
<div class="col-md-3 text-right">
 <input class="btn btn-secondary btn-sm" type="button" onclick="invalidate_session()" value="Invalidate Session"/>
 <input class="btn btn-secondary btn-sm" type="button" onclick="save_state()" value="Save Current State"/>
</div>
<%-- <h4><c:out value="${margin.cash}" /></h4>  --%>
</div>

<div class="row">
<div class="col-6">
<input type="hidden" id="refresh" value="<%= request.getAttribute("refresh").toString()%>"/>


<h5 class="text-center hightab_h5">High Table</h5>
<table id="high_table" class="one table table-bordered table-sm">
  <thead>
    <tr ><th>Time Stamp</th><th>Token Name</th><th>LTP</th><th>(%) Change</th><th>High Count</th><th>Volume (%)</th><th>Prev. Day High</th><th>Volume</th></tr>
  </thead>
  <tbody >
  <tr style='display:none' id='high_heading'><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
  </tbody>
</table>

</div>
<div class="col-6">
<h5 class="text-center lowtab_h5">Low Table</h5>
<table id="low_table" class="two table table-bordered table-sm">
  <thead>
     <tr ><th>Time Stamp</th><th>Token Name</th><th>LTP</th><th>(%) Change</th><th>Low Count</th><th>Volume (%)</th><th>Prev. Day Low</th><th>Volume</th></tr>
  </thead>
   <tbody >
   <tr style='display:none' id='low_heading'><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
   </tbody>
</table>


<%-- <tr><td>Token No</td><td>LTP</td><td>High</td><td>Low</td><td>High Counter</td> <td>Low Counter</td></tr>
 <c:forEach items="${Stock_List}" var="usersMap" varStatus="usersMapStatus">  
        <tr id='${usersMap.key}'>
        <td></td>
        <td>${usersMap.key}</td>
        <c:forEach items="${usersMap.value}" var="currentLoggedInUserValue" varStatus="valueStatus">
            </tr> Value: ${currentLoggedInUserValue.LTP}
        </c:forEach>        
    </c:forEach>
</table> --%>
</div>
</div>
</div>

</body>
</html>