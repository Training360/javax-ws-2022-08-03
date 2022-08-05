package employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeesService {

    private EmployeeMapper employeeMapper;

    private final AtomicLong idGenerator = new AtomicLong();

    private final List<Employee> employees = Collections.synchronizedList(new ArrayList<>(List.of(
        new Employee(idGenerator.incrementAndGet(), "John Doe"),
        new Employee(idGenerator.incrementAndGet(), "Jack Doe")
    )));

    public List<EmployeeDto> listEmployees(Optional<String> prefix) {
        List<Employee> filtered = employees.stream()
                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                .toList();
        return employeeMapper.toDto(filtered);
    }

    public EmployeeDto findEmployeeById(long id) {
        return employeeMapper.toDto(employees.stream()
                .filter(e -> e.getId() == id).findAny()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found: " + id, id)));
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee(idGenerator.incrementAndGet(), command.getName());
        employees.add(employee);
        return employeeMapper.toDto(employee);
    }

    public EmployeeDto updateEmployee(long id, UpdateEmployeeCommand command) {
        Employee employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst().orElseThrow(() -> new EmployeeNotFoundException("Employee not found: " + id, id));
        employee.setName(command.getName());
        return employeeMapper.toDto(employee);
    }

    public void deleteEmployee(long id) {
        Employee employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst().orElseThrow(() -> new EmployeeNotFoundException("Employee not found: " + id, id));
        employees.remove(employee);
    }
}
