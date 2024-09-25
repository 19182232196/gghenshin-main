package com.gghenshinn.service;

import com.gghenshinn.dto.EmployeeDTO;
import com.gghenshinn.dto.EmployeeLoginDTO;
import com.gghenshinn.entity.Employee;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);
}
