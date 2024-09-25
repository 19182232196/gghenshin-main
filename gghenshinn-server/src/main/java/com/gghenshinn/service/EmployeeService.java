package com.gghenshinn.service;

import com.gghenshinn.dto.EmployeeDTO;
import com.gghenshinn.dto.EmployeeLoginDTO;
import com.gghenshinn.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * 接口
     * @param employeeDTO
     * @return
     */
    void save(EmployeeDTO employeeDTO);
}
