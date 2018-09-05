package com.vanhy.myservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.Param;
import org.rapidoid.jpa.JPA;

import com.vanhy.myservices.entity.EmpResult;
import com.vanhy.myservices.logback.RequestLog;

@Controller
public class CountEmpCtrl {

	@SuppressWarnings("unchecked")
	@GET("/count-emp")
	public Object doGet(@Param("dept_no") String dept_no, @Param("from_date") Date from_date,
			@Param("to_date") Date to_date, @Param("detail") Boolean detail) {
		JSONObject result = null;
		if (dept_no != null && from_date != null && to_date != null && detail != null) {
			result = new JSONObject();

			if (detail == true) {
				StoredProcedureQuery quitEmpDetail = JPA.em().createStoredProcedureQuery("quitEmpDetail",
						"EmpResultMapping");
				quitEmpDetail.registerStoredProcedureParameter("deptNo", String.class, ParameterMode.IN);
				quitEmpDetail.registerStoredProcedureParameter("fromDate", Date.class, ParameterMode.IN);
				quitEmpDetail.registerStoredProcedureParameter("toDate", Date.class, ParameterMode.IN);
				List<EmpResult> quitEmp = quitEmpDetail.setParameter("deptNo", dept_no)
						.setParameter("fromDate", from_date).setParameter("toDate", to_date).getResultList();

				JSONArray jsonQuitEmp = new JSONArray(quitEmp);
				result.put("quit_emp", jsonQuitEmp);

				StoredProcedureQuery newEmpDetail = JPA.em().createStoredProcedureQuery("newEmpDetail",
						"EmpResultMapping");
				newEmpDetail.registerStoredProcedureParameter("deptNo", String.class, ParameterMode.IN);
				newEmpDetail.registerStoredProcedureParameter("fromDate", Date.class, ParameterMode.IN);
				newEmpDetail.registerStoredProcedureParameter("toDate", Date.class, ParameterMode.IN);
				List<EmpResult> newEmp = newEmpDetail.setParameter("deptNo", dept_no)
						.setParameter("fromDate", from_date).setParameter("toDate", to_date).getResultList();

				JSONArray jsonNewEmp = new JSONArray(newEmp);
				result.put("new_emp", jsonNewEmp);
			} else {
				StoredProcedureQuery quitEmpCount = JPA.em().createStoredProcedureQuery("quitEmpCount");
				quitEmpCount.registerStoredProcedureParameter("deptNo", String.class, ParameterMode.IN);
				quitEmpCount.registerStoredProcedureParameter("fromDate", Date.class, ParameterMode.IN);
				quitEmpCount.registerStoredProcedureParameter("toDate", Date.class, ParameterMode.IN);
				List<Integer> quitEmp = (List<Integer>) quitEmpCount.setParameter("deptNo", dept_no)
						.setParameter("fromDate", from_date).setParameter("toDate", to_date).getResultList();

				StoredProcedureQuery newEmpCout = JPA.em().createStoredProcedureQuery("newEmpCount");
				newEmpCout.registerStoredProcedureParameter("deptNo", String.class, ParameterMode.IN);
				newEmpCout.registerStoredProcedureParameter("fromDate", Date.class, ParameterMode.IN);
				newEmpCout.registerStoredProcedureParameter("toDate", Date.class, ParameterMode.IN);
				List<Integer> newEmp = (List<Integer>) newEmpCout.setParameter("deptNo", dept_no)
						.setParameter("fromDate", from_date).setParameter("toDate", to_date).getResultList();

				JSONArray jsonQuitEmp = new JSONArray(quitEmp);
				JSONArray jsonNewEmp = new JSONArray(newEmp);
				result.put("quit_emp_count", jsonQuitEmp);
				result.put("new_emp_count", jsonNewEmp);
			}

			Timestamp time = new Timestamp(System.currentTimeMillis());
			RequestLog log = new RequestLog(dept_no, from_date, to_date, detail.toString(), new Date(time.getTime()));
			JPA.save(log);
		}
		return result;
	}
}
