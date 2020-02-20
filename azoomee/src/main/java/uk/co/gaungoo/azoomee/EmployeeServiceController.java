package uk.co.gaungoo.azoomee;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.co.gaungoo.azoomee.persistence.Employee;
import uk.co.gaungoo.azoomee.service.EmployeeRecordService;

@RestController
public class EmployeeServiceController {

	@Autowired
	private EmployeeRecordService recordService;

	/*
	boolean createEmployee(Employee employee);
	boolean updateEmployee(Employee employee);
	boolean deleteEmployee(Long employeeId);
	Employee getEmployee(String string);
	Employee getEmployee(Long id);
	List<Employee> getAllStartingAfterDateWithIncomeGreaterThanAmount(Date date, BigDecimal income);
	Employee getThisMonthsWinner();
	Employee getWinnerForMonth(Calendar month);
	boolean updateLocationsForDepartment(String department, String newLocation);
	 */

	@GetMapping("/getEmployee")
	public Employee readEmployee(@RequestParam(value = "name") String name) {
		return recordService.getEmployee(name);
	}

	@PostMapping("/createEmployee")
	public String createEmployee(Employee employee) {
		String returnValue = "Create Failed";
		if (recordService.createEmployee(employee)) {
			returnValue = "Create Success";
		}
		return returnValue;
	}

	@PostMapping("/updateEmployee")
	public String updateEmployee(Employee employee) {
		String returnValue = "Update Failed";
		if (recordService.updateEmployee(employee)) {
			returnValue = "Update Success";
		}
		return returnValue;
	}

	@GetMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam(value = "id") String id) {
		String returnValue = "Delete Failed";
		try {
			if(recordService.deleteEmployee(Long.valueOf(id))) {
				returnValue = "Delete Success";
			}
		} catch (NumberFormatException e) {
			returnValue = "Delete Failed - Not a valid number";
		}
		return returnValue;
	}
	
	@GetMapping("/relocateDepartment")
	public String relocateDept(@RequestParam(value = "department") String dept, @RequestParam(value = "location") String newLocation) {
		String returnValue = "Relocation Failed";
		if (recordService.updateLocationsForDepartment(dept, newLocation)) {
			returnValue = "Relocation Success";
		}
		return returnValue;
	}

	@GetMapping("/getAllStartingAfterDateWithIncomeGreaterThanAmount")
	public List<Employee> readEmployee(@RequestParam(value = "date") String date, @RequestParam(value = "amount")BigDecimal amount) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
		Date dateval;
		try {
			dateval = formatter.parse(date);
		} catch (ParseException e) {
			dateval = new Date();
		}
		return recordService.getAllStartingAfterDateWithIncomeGreaterThanAmount(dateval, amount);
	}

}
