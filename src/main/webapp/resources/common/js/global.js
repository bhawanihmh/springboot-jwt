"use strict";
/**
 	*Bhawani Singh Shekhawat
	*Global Functionality
*/

var Global = (function(){
	
	var ENUM_TYPE = {"POST":"POST", "PUT":"PUT","GET":"GET","DELETE":"DELETE"};
	var ENUM_CONTENTTYPE = {"JSON":"application/json"};
	 var TAG ="--------";
	/** 
	 * Enumration for method type
	 * @param none
	 * @return obj
	 */	
	var enumType = (function(){
		return ENUM_TYPE;
	})()
	
	/** 
	 * Enumration for content type
	 * @param none
	 * @return obj
	 */		
	var enumContentType = (function(){
		return ENUM_CONTENTTYPE;
	})();
	
	
	var BASE_URL ='http://localhost:8080/';
	var EMPLOYEE = 'Employee';
	
	/**
	 * 
	 */
	var logger = (function () {
		var mode = "";					 
		return {
			d: function (_tag,_message) {
				mode='DEBUG'; 
				console.log(mode+':'+_tag+':'+_message);
			},
			i: function (_tag,_message) {
				mode='INFO';
				console.log(_message);
			}
		}		
	})();
	
	var ModuleAJAXURL = {
			ADD_EMPLOYEE:{url:"addemployee"}
	};
	
	var addEmployee = function() {
		var isSuccess = true;	
		
		var url = BASE_URL+ModuleAJAXURL.ADD_EMPLOYEE.url;		
		alert("url = "+url);
		
		var empName = document.getElementById('name').value;
		var empSalary = document.getElementById('salary').value;
		
		var employee = {};
		employee.name = empName;
		employee.salary = empSalary;	
		
		$.ajax({
			url: url,
			type: enumType.POST,
			data:  JSON.stringify(employee), /*use JSON.stringify() to serialize an object to JSON*/
			contentType : enumContentType.JSON,
			success: function(data) {
				try {					
					addEmployeeCall(data);		
					logger.i(EMPLOYEE, 'After addEmployeeCall method! ');
				} catch (exception) {
					logger.i(EMPLOYEE, 'Exception! ' + exception);
				}
			},
			error: function() {
				logger.i(EMPLOYEE, 'Error! Error occured while addeing a employee');
			},
			complete: function() {
				isSuccess = true;
			}
		});

	};
	
	
	/*
	 * addEmployeeCall
	 * Call to decide whether the Employee has been added sucessfully
	 * @params _data
	 */
	var addEmployeeCall = function(_data) {		
		try{		
  		  var dataString = JSON.stringify(_data);
  		 //alert("dataString = " + dataString);  		
  		  var dataJson = JSON.parse(dataString); 		
  		  var employee = dataJson;  		  
  		  //console.log(dataJson);
  		  //alert("id = " + employee.id + ", name = " + employee.name + ", salary = " + employee.salary);
  		  
  		  logger.i(EMPLOYEE, 'Employee added successfully name = ' + employee.name);
  		  
		}catch(exception){			    		 
  		  logger.d(TAG,'Error while adding employee. error is '+exception);
  	  	}
	};
	//}.bind(this);
	
	
	/*$.ajax(
	{ type: "POST",
	 url: baseUrl + "query/",
	contentType: "application/json; charset=utf-8",
	 dataType: "json",
	headers: {
	 "Authorization": "Bearer " + accessToken	
	},
	data: JSON.stringify({q: text, lang: "en"}), //text is a String variable
	success: function(data) { prepareResponse(data); },
	error: function() { respond(messageInternalError); } });*/
	
	
	
	/*
	type : 'POST',
    //remove the .php from results.php.php
    url : "http://externalsite.cpm/results.php",
    //Add the request header
    headers : {
        Authorization : 'Bearer ' + 'XXXXXXXXXXXXXXXXXXXXXXXXX'
    },
    contentType : 'application/x-www-form-urlencoded',
    //Add form data
    data : {keyName : dataValue},
    success : function(response) {
        console.log(response);
    },
    error : function(xhr, status, error) {
        var err = eval("(" + xhr.responseText + ")");
        console.log(err);                   
    }
	*/
	
	return{
		addEmployee:addEmployee		
	}
	
})();





