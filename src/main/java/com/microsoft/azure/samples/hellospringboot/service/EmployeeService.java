package com.microsoft.azure.samples.hellospringboot.service;

import java.util.List;
import java.util.Optional;

import com.microsoft.azure.samples.hellospringboot.dao.EmployeeDao;
import com.microsoft.azure.samples.hellospringboot.entity.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class EmployeeService {
	
	
	@Autowired
	private EmployeeDao employeeDao;

	public List<EmployeeEntity> getAllEmployees() {
		List<EmployeeEntity> all = (List<EmployeeEntity>)employeeDao.findAll();
		return all;
		
	}

	public EmployeeEntity save(EmployeeEntity employeeEntity) {
		return employeeDao.save(employeeEntity);
		
	}

	public HttpStatus deleteEmployeeId(Integer employeeId) {
		employeeDao.deleteById(employeeId);
		return HttpStatus.OK;
	}
	public HttpStatus updateEmployeeDetail(Integer employeeId, EmployeeEntity employeeEnt) {
		   Optional<EmployeeEntity> empEntity=employeeDao.findById(employeeId);
		   
		     if(empEntity!=null && empEntity.isPresent()) {
				 employeeEnt.setId(employeeId);
				 employeeDao.save(employeeEnt);
				 return HttpStatus.OK;
		   }
		   
		   else return HttpStatus.NOT_FOUND;
		
	}

	public long getCount() {

		return employeeDao.count();
	}


}
