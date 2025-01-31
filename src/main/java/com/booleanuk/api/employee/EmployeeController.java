package com.booleanuk.api.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employees;

    public EmployeeController() throws SQLException {
        this.employees = new EmployeeRepository();
    }

    @GetMapping
    public List<Employee> getAll() throws SQLException {
        return employees.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable(name = "id") long id) throws SQLException {
        Employee employee = employees.get(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees matching that id were found");
        }
        return employee;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) throws SQLException {
        Employee theEmployee = employees.add(employee);
        if (theEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create employee, please check all required fields are correct.");
        }
        return theEmployee;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable (name = "id") long id, @RequestBody Employee employee) throws SQLException {
        Employee toBeUpdated = employees.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees matching that id were found");
        }

        Employee updatedEmployee = employees.update(id, employee);
        if (updatedEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the employee, please check all required fields are correct.");
        }
        return updatedEmployee;
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable (name = "id") long id) throws SQLException {
        Employee toBeDeleted = employees.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees matching that id were found");
        }
        return employees.delete(id);
    }
}

