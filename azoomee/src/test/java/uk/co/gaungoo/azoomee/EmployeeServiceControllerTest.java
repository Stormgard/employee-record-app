package uk.co.gaungoo.azoomee;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import uk.co.gaungoo.azoomee.persistence.Employee;
import uk.co.gaungoo.azoomee.service.EmployeeRecordService;

class EmployeeServiceControllerTest {
	
	private EmployeeServiceController controller = new EmployeeServiceController();
	private EmployeeRecordService recordService = Mockito.mock(EmployeeRecordService.class);
	private Employee sampleEmployee = new Employee();

	@BeforeEach
	void setUp() throws Exception {
		populateSampleEmployee();
	}
	
	@Test
	void testReadEmployeeReturnsCorrectEmployee() {
		sampleEmployee.setId(19l);
		Mockito.when(recordService.getEmployee("Data")).thenReturn(sampleEmployee);
		ReflectionTestUtils.setField(controller, "recordService", recordService);
		assertEquals(sampleEmployee, controller.readEmployee("Data"));
	}
	
	private void populateSampleEmployee() {
		sampleEmployee.setName("Data");
		sampleEmployee.setOfficeLocation("Bridge");
		sampleEmployee.setSalary(new BigDecimal(800000));
		sampleEmployee.setStartDate(new Date());
	}

}
