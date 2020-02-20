package uk.co.gaungoo.azoomee.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import uk.co.gaungoo.azoomee.persistence.Employee;
import uk.co.gaungoo.azoomee.persistence.EmployeeRepository;

@Service
public class EmployeeRecordServiceImpl implements EmployeeRecordService {
	
	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public boolean createEmployee(Employee employee) {
		Employee savedEmployee = employeeRepo.save(employee);
		return savedEmployee.getId() != null;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		return createEmployee(employee);
	}

	@Override
	public boolean deleteEmployee(Long employeeId) {
		employeeRepo.deleteById(employeeId);
		Optional<Employee> opt = employeeRepo.findById(employeeId);
		return !opt.isPresent();
	}

	@Override
	public List<Employee> getAllStartingAfterDateWithIncomeGreaterThanAmount(Date date, BigDecimal income) {
		List<Employee> list = StreamSupport.stream(employeeRepo.findAll().spliterator(), false).filter(e -> e.getStartDate().after(date) &&
				e.getSalary().compareTo(income) > 0).collect(Collectors.toList());
		return list;
	}

	@Override
	public boolean updateLocationsForDepartment(String department, String newLocation) {
		List<Employee> employeesOfDept = employeeRepo.findByDepartment(department);
		employeesOfDept.forEach(e -> e.setOfficeLocation(newLocation));
		employeeRepo.saveAll(employeesOfDept);
		return !employeeRepo.findByDepartment(department)
				.stream().anyMatch(e -> !e.getOfficeLocation().equals(newLocation));
	}

	@Override
	@Scheduled(cron = "0 0 8 1 * *")
	public Employee getThisMonthsWinner() {
		Optional<Employee> optMin = StreamSupport.stream(employeeRepo.findAll().spliterator(), false)
				.min(Comparator.comparingLong(Employee::getId));
		Optional<Employee> optMax = StreamSupport.stream(employeeRepo.findAll().spliterator(), false)
				.max(Comparator.comparingLong(Employee::getId));
		Long min = optMin.isPresent()?optMin.get().getId():0;
		Long max = optMax.isPresent()?optMax.get().getId():99999;
		Long generatedId = min + (long) (Math.random() * (max - min));
		Optional<Employee> optWinningEmployee = employeeRepo.findById(generatedId);
		
		return optWinningEmployee.isPresent()?optWinningEmployee.get():null;
	}

	@Override
	public Employee getEmployee(String string) {
		Optional<Employee> dbResult = employeeRepo.findByName(string);
		return dbResult.isPresent()?dbResult.get():null;
	}
	
	@Override
	public Employee getEmployee(Long id) {
		Optional<Employee> dbResult = employeeRepo.findById(id);
		return dbResult.isPresent()?dbResult.get():null;
	}

	public EmployeeRepository getEmployeeRepo() {
		return employeeRepo;
	}

	public void setEmployeeRepo(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

}
