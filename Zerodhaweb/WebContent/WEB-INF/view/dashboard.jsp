<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://momentjs.com/downloads/moment-with-locales.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<script type="text/javascript">
function remove_state()
{	 
	alert("remove state called");
	
	 $.ajax({url: "/Zerodhaweb/function/ClearStateDB", success: function(result)
		 {
		    if(result=="success")
		    {
		    	alert("all state Deleted");	
		    }
		    else
		    	{
		    	 alert(result);
		    	}
		  }});	
}

</script>
</head>
<body>
<br>
<br>
  <p><a href="stratergy1">stratergy1</a></p>
  
  
  
   <input class="btn btn-primary float-left" type="button" onclick="remove_state()" value="DELETE Current STATE"/>
</body>
</html>