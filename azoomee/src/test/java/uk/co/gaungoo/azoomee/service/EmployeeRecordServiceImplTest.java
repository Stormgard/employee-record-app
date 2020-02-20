package uk.co.gaungoo.azoomee.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import uk.co.gaungoo.azoomee.persistence.Employee;
import uk.co.gaungoo.azoomee.persistence.EmployeeRepository;

class EmployeeRecordServiceImplTest {

	private EmployeeRecordServiceImpl service = new EmployeeRecordServiceImpl();
	private EmployeeRepository repo = Mockito.mock(EmployeeRepository.class);
	private Employee sampleEmployee = new Employee();
	
	@BeforeEach
	void setUp() throws Exception {
		ReflectionTestUtils.setField(service, "employeeRepo", repo);
		Mockito.when(repo.findByName("Spock")).thenReturn(Optional.of(new Employee()));
		populateSampleEmployee();
	}

	private void populateSampleEmployee() {
		sampleEmployee.setName("Data");
		sampleEmployee.setOfficeLocation("Bridge");
		sampleEmployee.setSalary(new BigDecimal(800000));
		sampleEmployee.setStartDate(new Date());
	}

	@Test
	void testCreateEmployee() {
		Mockito.when(repo.save(sampleEmployee)).thenReturn((sampleEmployee));
		service.createEmployee(sampleEmployee);
		Mockito.verify(repo).save(Mockito.any());
	}

	@Test
	void testUpdateEmployee() {
		Mockito.when(repo.save(sampleEmployee)).thenReturn((sampleEmployee));
		service.updateEmployee(sampleEmployee);
		Mockito.verify(repo).save(Mockito.any());
	}

	@Test
	void testDeleteEmployee() {
		service.deleteEmployee(19l);
		Mockito.verify(repo).deleteById(Mockito.anyLong());
	}

	@Test
	void testGetEmployee() {
		Mockito.when(repo.findByName(Mockito.anyString())).thenReturn((Optional.of(sampleEmployee)));
		assertEquals(sampleEmployee, service.getEmployee("Data"));
	}

	@Test
	void testGetAllStartingAfterDateWithIncomeGreaterThanAmountEmpty() {
		Mockito.when(repo.findAll()).thenReturn(populateVariedStartDateAndSalaryList());
		Calendar cal = Calendar.getInstance();
		cal.set(1986, 1, 1);
		assertTrue(service.getAllStartingAfterDateWithIncomeGreaterThanAmount(cal.getTime(), new BigDecimal(3000000)).isEmpty());
	}
	
	@Test
	void testGetAllStartingAfterDateWithIncomeGreaterThanAmount() {
		Mockito.when(repo.findAll()).thenReturn(populateVariedStartDateAndSalaryList());
		Calendar cal = Calendar.getInstance();
		cal.set(1986, 1, 1);
		assertEquals(2, service.getAllStartingAfterDateWithIncomeGreaterThanAmount(cal.getTime(), new BigDecimal(2300)).size());
	}

	private Iterable<Employee> populateVariedStartDateAndSalaryList() {
		List<Employee> sampleList = new ArrayList<Employee>();
		sampleList.add(sampleEmployee);
		Calendar cal = Calendar.getInstance();
		cal.set(1985, 5, 21);
		sampleList.add(sampleEmployeeWithSalaryAndStartDate(new BigDecimal(12000), cal.getTime()));
		cal.set(1990, 4, 23);
		sampleList.add(sampleEmployeeWithSalaryAndStartDate(new BigDecimal(266000), cal.getTime()));
		return sampleList;
	}

	

	@Test
	void testUpdateLocationsForDepartment() {
		List<Employee> sampleList = new ArrayList<Employee>();
		sampleList.add(sampleEmployee);
		Mockito.when(repo.findByDepartment(Mockito.anyString())).thenReturn(sampleList);
		assertTrue(service.updateLocationsForDepartment("Operations", "Main Engineering"));
	}

	@Test
	void testGetThisMonthsWinner() {
		fail("Not yet implemented");
	}

	private Employee sampleEmployeeWithSalaryAndStartDate(BigDecimal salary, Date date) {
		Employee e = new Employee();
		e.setName("B4");
		e.setOfficeLocation("Bridge");
		e.setSalary(salary);
		e.setStartDate(date);
		return e;
	}

}
