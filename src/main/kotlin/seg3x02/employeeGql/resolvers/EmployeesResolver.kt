package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeesRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? =
        employeesRepository.findById(id).orElse(null)

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = createEmployeeInput.name ?: throw Exception("Requires Name"),
            dateOfBirth = createEmployeeInput.dateOfBirth ?: throw Exception("Requires DOB"),
            city = createEmployeeInput.city ?: throw Exception("Requires City"),
            salary = createEmployeeInput.salary ?: throw Exception("Requires Salary"),
            gender = createEmployeeInput.gender,
            email = createEmployeeInput.email
        )
        employee.id = UUID.randomUUID().toString()
        return employeesRepository.save(employee)
    }
}