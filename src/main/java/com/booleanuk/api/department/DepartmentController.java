package com.booleanuk.api.department;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("departments")
public class DepartmentController {
    private DepartmentRepository departments;

    public DepartmentController() throws SQLException {
        this.departments = new DepartmentRepository();
    }

    @GetMapping
    public List<Department> getAll() throws SQLException {
        return departments.getAll();
    }

    @GetMapping("/{id}")
    public Department getOne(@PathVariable(name = "id") long id) throws SQLException {
        Department department = departments.get(id);
        if (department == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return department;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department create(@RequestBody Department department) throws SQLException {
        Department theDepartment = departments.add(department);
        if (theDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create the new department, please check all required fields are correct.");
        }
        return theDepartment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Department update(@PathVariable (name = "id") long id, @RequestBody Department department) throws SQLException {
        Department toBeUpdated = departments.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }

        Department updatedDepartment = departments.update(id, department);
        if (updatedDepartment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the department, please check all required fields are correct.");
        }
        return updatedDepartment;
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable (name = "id") long id) throws SQLException {
        Department toBeDeleted = departments.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No departments matching that id were found");
        }
        return departments.delete(id);
    }
}

