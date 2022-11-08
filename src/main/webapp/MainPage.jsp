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
  let ServersObjects = eval(<%=request.getAttribute("ServersData")%> )
  console.log(ServersObjects);
  
  function checker(){
	  console.log("I'm here");
  }
  
  
  
  
  ServersObjects.forEach(element => {
	  console.log(element.serverID);
	  var type = "checkbox";
	  var tag = `<input type="checkbox"`+" name=Server"+element.serverID + " value=Server"+element.serverID+ " id=Server"+element.serverID+"  />" +"Server "+element.serverID 
	  console.log(tag);
	  $('#Servers').append(tag);
	  
	  let checkbox = document.getElementById("Server"+element.serverID);

	  checkbox.addEventListener('change', (event) => {
		  axios.post('http://localhost:8080/UploadFileProject/StartDeployHandler', {
			    ServerName: 'Fred',
			    status: 'Flintstone'
			  })
			  .then(function (response) {
			    console.log(response);
			  })
			  .catch(function (error) {
			    console.log(error);
			  });
		  
		  
	    if (event.currentTarget.checked) {
	      console.log("Checked " + checkbox.value + "Checked : "+event.currentTarget.checked );
	    } else {
	    	console.log("UnChecked " + checkbox.value + "Checked : "+event.currentTarget.checked);
	    }
	  })

	  
	  
		 
  
  });
		 
 
   function uploadFile() {
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
		  
		  
		  document.getElementById("FileStatus").innerHTML = "Loading ...";
		  
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
  

   
	
		
   

  

  </script>


  

</body> 
</html>