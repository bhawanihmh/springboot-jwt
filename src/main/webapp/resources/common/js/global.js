"use strict";
/**
 	*Bhawani Singh Shekhawat
	*Global Functionality
*/

var Global = (function(){
	
	var ENUM_TYPE = {"POST":"POST", "PUT":"PUT","GET":"GET","DELETE":"DELETE"};
	var ENUM_CONTENTTYPE = {"JSON":"application/json"};
	var accessToken;
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
	var LOGIN = 'Login';	
	
	/**
	 * logging
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
			ADD_EMPLOYEE:{url:"addemployee"},
			LOGIN_PROCESS:{url:"loginProcess"},
			SHOW_ADD_EMPLOYEE:{url:"showaddemployee"}
	};
	
	/**
	 * loginProcess
	 * Call login process
	 */
	var loginProcess = function() {
		var isSuccess = true;	
		
		var url = BASE_URL + ModuleAJAXURL.LOGIN_PROCESS.url;		
		alert("url = " + url);
		
		var userName = document.getElementById('userName').value;
		var password = document.getElementById('password').value;
		
		var login = {};
		login.userName = userName;
		login.password = password;	
		
		$.ajax({
			url: url,
			type: enumType.POST,
			data:  JSON.stringify(login), /*use JSON.stringify() to serialize an object to JSON*/
			contentType : enumContentType.JSON,	
			returnType : enumContentType.JSON,
			success: function(data, textStatus, request) {
				try {
					accessToken = request.getResponseHeader('Authorization');
					
					if (typeof(Storage) !== "undefined") {
						localStorage.setItem("accessToken", accessToken);
						logger.i(LOGIN, 'accessToken = ' + accessToken);
					} else {
						logger.i(LOGIN, 'Sorry! No Web Storage support..');
					}				
					
					var url = BASE_URL + ModuleAJAXURL.SHOW_ADD_EMPLOYEE.url;		
					logger.i(LOGIN, 'url = ' + url);
					window.location.replace(url);					
							
					logger.i(LOGIN, 'After loadAddEmployee method! ');
				} catch (exception) {
					logger.i(LOGIN, 'Exception! ' + exception);
				}
			},
			error: function() {
				logger.i(LOGIN, 'Error! Error occured while addeing a employee');
			},
			complete: function() {
				isSuccess = true;
			}
		});

	};	
	
	/**
	 * addEmployee
	 * Call add employee
	 */
	var addEmployee = function() {
		var isSuccess = true;	
		
		var url = BASE_URL+ModuleAJAXURL.ADD_EMPLOYEE.url;		
		alert("url = "+url);
		
		var empName = document.getElementById('name').value;
		var empSalary = document.getElementById('salary').value;
		
		var employee = {};
		employee.name = empName;
		employee.salary = empSalary;
		
		accessToken = localStorage.getItem("accessToken");		
		logger.i(EMPLOYEE, 'accessToken = ' + accessToken);
		
		$.ajax({
			url: url,
			type: enumType.POST,
			data:  JSON.stringify(employee), /*use JSON.stringify() to serialize an object to JSON*/
			contentType : enumContentType.JSON,			
			headers: {
				 "Authorization": "Bearer " + accessToken
			},			
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
	
	
	/**
	 * addEmployeeCall
	 * Call to decide whether the Employee has been added sucessfully
	 * @params _data
	 */
	var addEmployeeCall = function(_data) {		
		try{		
  		  var dataString = JSON.stringify(_data);  		 
  		  var dataJson = JSON.parse(dataString); 		
  		  var employee = dataJson;  		  
  		  logger.i(EMPLOYEE, 'Employee added successfully name = ' + employee.name);  		  
		}catch(exception){			    		 
  		  logger.d(TAG,'Error while adding employee. error is '+exception);
  	  	}
	};	
	
	return {
		addEmployee:addEmployee,
		accessToken:accessToken,
		loginProcess:loginProcess
	}
	
})();
