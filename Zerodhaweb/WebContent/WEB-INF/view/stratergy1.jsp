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
 <script src="<c:url value="/resources/js/sortable.min.js" />"></script>
 <link href="<c:url value="/resources/css/sortable-theme-bootstrap.css" />" rel="stylesheet">
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
background: #d8d9d9;
border-bottom: 0;
}
/* .table tr td {
    word-break: break-word;
}
.table tr th:nth-child(1), .table tr td:nth-child(1) {
	width: 80px;
}
.table tr th:nth-child(2), .table tr td:nth-child(2) {
	width: 117px;
}
.table tr th:nth-child(3), .table tr td:nth-child(3) {
	width: 75px;
}
.table tr th:nth-child(4), .table tr td:nth-child(4) {
	width: 65px;
}
.table tr th:nth-child(5), .table tr td:nth-child(5) {
	width: 65px;
}
.table tr th:nth-child(6), .table tr td:nth-child(6) {
	width: 65px;
}
.table tr th:nth-child(7), .table tr td:nth-child(7) {
	width: 75px;
}
.table tr th:nth-child(8), .table tr td:nth-child(8) {
	width: 100px;
} */

table[data-sortable] th, table[data-sortable] td {
    padding: 0.25rem;
}
.table-bordered td, .table-bordered th {
    border-color: #b8b8b8; 
    
}
.fixed_header{
    width: 100%;
    table-layout: fixed;
}

.fixed_header tbody{
  display:block;
  width: 100%;
  overflow: auto;
  max-height: 50vh;
}
 .fixed_header thead tr {
   display: flex;
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
var display_array_low= new Array();
var display_array_high= new Array();

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


function check_connection()
{
	
    if(webSocket.readyState === WebSocket.CLOSED)
    	{
    	  alert("Web Socket CLosed in ClientSide");
    	}
    else{
    	console.log("connection Ok");
    }
	
}
var high_tab_row_counter=0;
var low_tab_row_counter=0;

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
	   var perc_change = ((stock.low - stock.prev_close)/(stock.prev_close))*100;
	   var avg_vol_perc = ((stock.current_volume / stock.avg_volume)*100);
	   
	    if((parseInt(avg_vol_perc) > getDynamicVolume())&&(perc_change>-10) &&(perc_change<10))  
		   {
	    	
	    	  if((parseInt(stock.low_counter)==40) ||  ((display_array_low.indexOf(stock.instrument_token)== -1) && stock.low_counter > 40) ) 
	    	/*  if(parseInt(stock.low_counter)==40) */
	          {
	    		 var tab_row = "<tr id='low_"+stock.instrument_token+"' class='myred'><td>"+moment(stock.time_stamp.toString()).format('hh:mm:ss')+"</td><td>"+stock.stock_name+"</td><td>"+stock.low+"</td><td>"+perc_change.toFixed(2)+"</td><td>"+stock.low_counter+"</td><td>"+commaSeparateNumber(avg_vol_perc.toFixed(0))+"</td><td>"+stock.prev_low+"</td><td>"+commaSeparateNumber(stock.current_volume)+"</td></tr>";
	    		  display_array_low.push(stock.instrument_token);
	    		  Save_Marked_data(stock);
	    		 
	    	  }
	    	 else
	    	  {
	    		 var timeStamp = "-";
	    		 if ( (stock.time_stamp!=null) || (typeof(stock.time_stamp)!= 'undefined') )
	    			 {
	    			    timeStamp = moment(stock.time_stamp.toString()).format('hh:mm:ss');
	    			 }
	    		 
	    		 var tab_row = "<tr id='low_"+stock.instrument_token+"' ><td>"+timeStamp+"</td><td>"+stock.stock_name+"</td><td>"+stock.low+"</td><td>"+perc_change.toFixed(2)+"</td><td>"+stock.low_counter+"</td><td>"+commaSeparateNumber(avg_vol_perc.toFixed(0))+"</td><td>"+stock.prev_low+"</td><td>"+commaSeparateNumber(stock.current_volume)+"</td></tr>";	 
	    	   }
		     	   
	    	  low_tab_row_counter++;
		   
		     
		     /* $("#low_table").prepend(tab_row); */
		     /*  $("#low_heading").after(tab_row); */
		    /*   $("#low_heading").insertBefore('table > tbody > tr:first'); */
		      /* $("#low_heading").prependTo("table > tbody"); */
		      
		      if(low_tab_row_counter==1)
		    	  {
		    	  $("#low_heading").append(tab_row);
		    	  }
		      else
		    	  {
		    	  $("#low_heading").prepend(tab_row);
		    	  }
		      /* $("#low_heading tbody").prepend(tab_row); */
		     
		      /*  save_sate(stock); */
		   }
		   
	
	}
   else if(stock.flag=='H')
	{
	  /*  $("#high_heading").remove(); */
	   /* $("#high_"+stock.instrument_token).remove(); */
	   var avg_vol_perc = ((stock.current_volume / stock.avg_volume)*100);
	   var perc_change = ((stock.high - stock.prev_close)/(stock.prev_close))*100;
	   
	   if((parseInt(avg_vol_perc) > getDynamicVolume())&&(perc_change<10) &&(perc_change>-10)) 
	   { 
		   /* if(parseInt(stock.high_counter)==40) */
		   if(parseInt(stock.high_counter)==40 || ((display_array_high.indexOf(stock.instrument_token)== -1) && stock.high_counter > 40)) 
          {
			  var tab_row1 = "<tr id='high_"+stock.instrument_token+"' class='mygreen'><td>"+moment(stock.time_stamp.toString()).format('hh:mm:ss')+"</td><td>"+stock.stock_name+"</td><td>"+stock.high+"</td><td>"+perc_change.toFixed(2)+"</td><td class='lw_vol'>"+stock.high_counter+"</td><td>"+commaSeparateNumber(avg_vol_perc.toFixed(0))+"</td><td>"+stock.prev_high+"</td><td>"+commaSeparateNumber(stock.current_volume)+"</td></tr>";
			  display_array_high.push(stock.instrument_token);
			  Save_Marked_data(stock);
          }
		  else
          {
			  var timeStamp = "-";
	    		 if ( (stock.time_stamp!=null) || (typeof(stock.time_stamp)!= 'undefined') )
	    			 {
	    			    timeStamp = moment(stock.time_stamp.toString()).format('hh:mm:ss');
	    			 } 
			  var tab_row1 = "<tr id='high_"+stock.instrument_token+"'><td>"+timeStamp+"</td><td>"+stock.stock_name+"</td><td>"+stock.high+"</td><td>"+perc_change.toFixed(2)+"</td><td class='lw_vol'>"+stock.high_counter+"</td><td>"+commaSeparateNumber(avg_vol_perc.toFixed(0))+"</td><td>"+stock.prev_high+"</td><td>"+commaSeparateNumber(stock.current_volume)+"</td></tr>";
          }
		   high_tab_row_counter++;
		   
		  /*  $("#high_table").prepend(tab_row1); */
		   /* $("#high_heading").after(tab_row1); */
		   /* $("#high_heading").insertBefore('table > tbody > tr:first'); */
		   /* $("#high_heading tbody").append(tab_row1); */
		   
		   if(high_tab_row_counter==1)
	    	  {
	    	  $("#high_heading").append(tab_row1);
	    	  }
	      else
	    	  {
	    	  $("#high_heading").prepend(tab_row1);
	    	  }
		  
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
 
 
 function Save_Marked_data(stock)
 {
	$.ajax({url: "/Zerodhaweb/function/SaveMarkedData", data:JSON.stringify(stock) , type:"POST" , contentType:"application/json" , success: function(result)
	 {
	    if(result=="success")
	    {	
	    	console.log("SUccess");
	    }
	    else
	    	{
	    	console.log(result);
	    	}
	  }});	 
 }

 
 $(document).ready(function() 
		 {
	      console.log($("#refresh").val());  	      
	      
	      
	     /*  $(".table tbody").sortable({
	            items: 'tr:not(:first)'
	        }); */
	      
	      /* $('#low_table').DataTable();
	      $('.dataTables_length').addClass('bs-select');
	      
	      
	      $('#high_table').DataTable();
	      $('.dataTables_length').addClass('bs-select'); */
	      
	      
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
<table id="high_table" class="one table table-bordered table-sm" data-sortable>
  <thead>
    <tr ><th data-sortable-type="date">Time Stamp</th><th data-sortable-type="alpha">Token Name</th><th data-sortable-type="numeric">Price</th><th data-sortable-type="numeric">(%) Change</th><th data-sortable-type="numeric">High Count</th><th data-sortable-type="numeric">Volume (%)</th><th data-sortable-type="numeric">Prev. Day High</th><th data-sortable-type="numeric">Volume</th></tr>
  </thead>
  <tbody id='high_heading'>
  <!-- <tr style='display:none' id='high_heading'><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr> -->
  </tbody>
</table>

</div>
<div class="col-6">
<h5 class="text-center lowtab_h5">Low Table</h5>
<table id="low_table" class="two table table-bordered table-sm" data-sortable>
  <thead>
     <tr ><th data-sortable-type="date">Time Stamp</th><th data-sortable-type="alpha">Token Name</th><th data-sortable-type="numeric">Price</th><th data-sortable-type="numeric">(%) Change</th><th data-sortable-type="numeric">Low Count</th><th data-sortable-type="numeric">Volume (%)</th><th data-sortable-type="numeric">Prev. Day Low</th><th data-sortable-type="numeric">Volume</th></tr>
  </thead>
   <tbody id='low_heading'>
   <!-- <tr style='display:none' id='low_heading'><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr> -->
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