package com.vanhy.myservices;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.Param;

@Controller
public class CountEmpCtrl {
	
	@GET("/count-emp")
	public Object doGet(@Param("dept_no") String dept_no, @Param("from_date") String from_date,
			@Param("to_date") String to_date, @Param("detail") boolean detail) {
		
		return "hello";
	}
}
