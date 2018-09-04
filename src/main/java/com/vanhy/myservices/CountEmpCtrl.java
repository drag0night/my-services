package com.vanhy.myservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.Param;
import org.rapidoid.jpa.JPA;

import com.vanhy.myservices.logback.RequestLog;

@Controller
public class CountEmpCtrl {

	@GET("/count-emp")
	public Object doGet(@Param("dept_no") String dept_no, @Param("from_date") Date from_date,
			@Param("to_date") Date to_date, @Param("detail") Boolean detail) {
		List<List<?>> result = null;
		if (dept_no != null && from_date != null && to_date != null && detail != null) {
			result = new ArrayList<>();

			Timestamp time = new Timestamp(System.currentTimeMillis());
			RequestLog log = new RequestLog(dept_no, from_date, to_date, detail.toString(), new Date(time.getTime()));
			JPA.save(log);

			if (detail == true) {
				StoredProcedureQuery quitEmpDetail = JPA.em().createStoredProcedureQuery("quitEmpDetail");
				quitEmpDetail.registerStoredProcedureParameter("deptNo", String.class, ParameterMode.IN);
				quitEmpDetail.registerStoredProcedureParameter("fromDate", Date.class, ParameterMode.IN);
				quitEmpDetail.registerStoredProcedureParameter("toDate", Date.class, ParameterMode.IN);
				List<?> quitEmp = quitEmpDetail.setParameter("deptNo", dept_no).setParameter("fromDate", from_date)
						.setParameter("toDate", to_date).getResultList();

				StoredProcedureQuery newEmpDetail = JPA.em().createStoredProcedureQuery("newEmpDetail");
				newEmpDetail.registerStoredProcedureParameter("deptNo", String.class, ParameterMode.IN);
				newEmpDetail.registerStoredProcedureParameter("fromDate", Date.class, ParameterMode.IN);
				newEmpDetail.registerStoredProcedureParameter("toDate", Date.class, ParameterMode.IN);
				List<?> newEmp = newEmpDetail.setParameter("deptNo", dept_no).setParameter("fromDate", from_date)
						.setParameter("toDate", to_date).getResultList();
				result.add(quitEmp);
				result.add(newEmp);
			} else {
				StoredProcedureQuery quitEmpCount = JPA.em().createStoredProcedureQuery("quitEmpCount");
				quitEmpCount.registerStoredProcedureParameter("deptNo", String.class, ParameterMode.IN);
				quitEmpCount.registerStoredProcedureParameter("fromDate", Date.class, ParameterMode.IN);
				quitEmpCount.registerStoredProcedureParameter("toDate", Date.class, ParameterMode.IN);
				List<?> quitEmp = quitEmpCount.setParameter("deptNo", dept_no).setParameter("fromDate", from_date)
						.setParameter("toDate", to_date).getResultList();

				StoredProcedureQuery newEmpCout = JPA.em().createStoredProcedureQuery("newEmpCount");
				newEmpCout.registerStoredProcedureParameter("deptNo", String.class, ParameterMode.IN);
				newEmpCout.registerStoredProcedureParameter("fromDate", Date.class, ParameterMode.IN);
				newEmpCout.registerStoredProcedureParameter("toDate", Date.class, ParameterMode.IN);
				List<?> newEmp = newEmpCout.setParameter("deptNo", dept_no).setParameter("fromDate", from_date)
						.setParameter("toDate", to_date).getResultList();
				result.add(quitEmp);
				result.add(newEmp);
			}
		}
		return result;
	}
}
