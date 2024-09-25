package com.gghenshinn.service.impl;

import com.gghenshinn.constant.MessageConstant;
import com.gghenshinn.constant.PasswordConstant;
import com.gghenshinn.constant.StatusConstant;
import com.gghenshinn.dto.EmployeeDTO;
import com.gghenshinn.dto.EmployeeLoginDTO;
import com.gghenshinn.entity.Employee;
import com.gghenshinn.exception.AccountLockedException;
import com.gghenshinn.exception.AccountNotFoundException;
import com.gghenshinn.exception.PasswordErrorException;
import com.gghenshinn.mapper.EmployeeMapper;
import com.gghenshinn.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端传过来的密码进行md5加密处理
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     * 将未封装数据再封装，调用到employeeMapper
     * @param employeeDTO
     * @return
     */
    public void save(EmployeeDTO employeeDTO) {
        System.out.println("当前ID：" + Thread.currentThread().getId());

        //创建实体
        Employee employee =new Employee();

        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO,employee);

        //设置账号状态
        employee.setStatus(StatusConstant.ENABLE);

        //设置密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //创建当前记录时间，修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        // TODO 修改动态获取
        //设置当前记录用户ID，修改用户ID
        employee.setCreateUser(10L);
        employee.setUpdateUser(10l);

        employeeMapper.insert(employee);
    }

}
