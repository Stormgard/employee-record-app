package uk.co.gaungoo.azoomee.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import uk.co.gaungoo.azoomee.persistence.Employee;

public interface EmployeeRecordService {
	boolean createEmployee(Employee employee);
	boolean updateEmployee(Employee employee);
	boolean deleteEmployee(Long employeeId);
	Employee getEmployee(String string);
	Employee getEmployee(Long id);
	List<Employee> getAllStartingAfterDateWithIncomeGreaterThanAmount(Date date, BigDecimal income);
	Employee getThisMonthsWinner();
	boolean updateLocationsForDepartment(String department, String newLocation);
}
