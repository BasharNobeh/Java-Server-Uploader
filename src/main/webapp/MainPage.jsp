<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
   
    
<!DOCTYPE html> 
<html> 
<head> 
<script src="https://unpkg.com/axios@1.1.2/dist/axios.min.js"></script>
<script src="https://code.jquery.com/jquery-1.10.2.js"
    type="text/javascript"></script>

<title> Java Ajax File Upload Example </title> </head> 
<style>
body{
display:flex;
flex-direction:column;
justify-content:center;
align-items:center;

}


button {
box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
width: 200px;
font-size: 16px;
font-weight: 600;
color: rgb(255, 0, 0);
cursor: pointer;
margin: 20px;
height: 55px;
text-align:center;
border: none;
background-size: 300% 100%;
border-radius: 50px;
-o-transition: all .4s ease-in-out;
-webkit-transition: all .4s ease-in-out;
transition: all .4s ease-in-out;
}

button:hover {
background-position: 100% 100;

-o-transition: all .4s ease-in-out;
-webkit-transition: all .4s ease-in-out;
transition: all .4s ease-in-out;
}
#ajaxfile{
box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
padding:20px;
margin-top:4px;
background-color: #4CAF50; /* Green */
border: none;
color: white; 
text-align: center;
text-decoration: none;
display: inline-block;
font-size: 16px;
}
#statusText{
max-width:300px;
border: thick double #32a1ce;

}
#myDiv{
display:flex;
flex-direction: row;
justify-content: center;
align-items: center;

}
#Servers{
display:flex;
flex-direction: column;
justify-content: center;
align-items: center;

}
label{
text-align: center;
}




</style>
<body>





  <!-- HTML5 Input Form Elements -->
  <div id = "myDiv">
  
    <input id="ajaxfile" type="file"/>
  <button id = "MyButton" onclick="uploadFile()"> Upload </button>
  
  
  <div id = "Servers">
 

  </div>
  </div>
  
 
  


<div id = "FileStatus">

</div>
  


  <!-- Ajax to Java File Upload Logic -->
  <script>
  // Getting servers data
  let ServersObjects = eval(<%=request.getAttribute("ServersData")%> )
  
  var counter = 0 ;
  var NS_Array = [];
  // Creating the servers checkboxes along with eventlisteners (OnChange).
  
  ServersObjects.sort(function(a, b){
    return a.serverID - b.serverID;
});

  
  
  ServersObjects.forEach(element => {
	  
	  
	  var type = "checkbox";
	  var tag = "<label>"+"Server"+element.serverID+`<input type="checkbox"`+" name=Server"+element.serverID + " value=Server"+element.serverID+ " id=Server"+element.serverID+"  /></label>" ; 
	  console.log(tag);
	  $('#Servers').append(tag);
	  //event.currentTarget.checked 
	  let checkbox = document.getElementById("Server"+element.serverID);
	  checkbox.addEventListener('change', (event) => {
		  if(event.currentTarget.checked ){
			  NS_Array.push(element.serverID);
			  console.log(NS_Array);
			  
			  counter++;
		  }else {
			  NS_Array = NS_Array.filter(function (ServerID) {
				    return ServerID !== element.serverID;
				});
			  console.log(NS_Array);
			  counter--;
		  }
		  console.log(counter);
	    
	  })

	  
	  
		 
  
  });
  
		 
 // Will check if isDeployingRun is 0 or 1 
 // if isDeployingRun == 0 it will call the deyploy function
 // else it will display an alert with a msg
   function uploadFile() {
	   
	   
	   $.ajax({
           url:'http://localhost:8080/UploadFileProject/StartDeployHandler',
           type:'POST',
           data:{value : counter , NS_Array :NS_Array.toString() },
           success : function (response) {
			  console.log(response);
			  if (response.includes("Try again later")){
				  alert(response);  
			  }else {
				  alert("Deploying !")
				  Deploy();
			  }
 
		  }
         });
	   
	   
	   
	   
	   
	   
	   
	   
	   // Dploying Function : 
	   function Deploy(){
		   
	   
	   
	   
	   
	   // Getting the file from the input 
	 let formData = new FormData(); 
	 formData.append("file", ajaxfile.files[0]);
	 
	 
	 
	 ServersObjects.forEach(element => {
		 console.log(element);
		  //element.Uploading_URL
		   


		  fetch(element.Uploading_URL, {
		      method: "POST", 
		      body: formData
		    });
		  
		  // Loading text .. 
		  document.getElementById("FileStatus").innerHTML = "Loading ...";
		// logs loader function 
		  setTimeout(function(){
			  axios.get(element.logTracker)
			  .then(function (response) {
				  
				  if (response.data == ""){
					  response.data = "an Error happened ! in "+element.Uploading_URL;
					  
				  }
					  document.getElementById("FileStatus").insertAdjacentHTML("beforebegin","<span>" + response.data.replace("D@NE","")+"</span><br>");
					    document.getElementById("FileStatus").innerHTML = "";  
				  
				   
			  })
			  
			  
			  
		  },3000);
		  
		 
		
		});
	 
	 
	 
	 
	 
	   
	   
	   }
	   
	
	  
		  
		   
		   
		   
	   
	
	
	   
   }
   

   
	
		
   

  

  </script>


  

</body> 
</html>