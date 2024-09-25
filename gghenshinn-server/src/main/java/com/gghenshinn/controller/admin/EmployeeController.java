package com.gghenshinn.controller.admin;

import com.gghenshinn.constant.JwtClaimsConstant;
import com.gghenshinn.dto.EmployeeDTO;
import com.gghenshinn.dto.EmployeeLoginDTO;
import com.gghenshinn.entity.Employee;
import com.gghenshinn.properties.JwtProperties;
import com.gghenshinn.result.Result;
import com.gghenshinn.service.EmployeeService;
import com.gghenshinn.utils.JwtUtil;
import com.gghenshinn.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags="员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value="员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        //记录日志
        log.info("员工登录：{}", employeeLoginDTO);

        //调用员工服务进行登录
        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        //将员工id放入jwt令牌中
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        //生成jwt令牌
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        //构建员工登录返回对象
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        //返回登录成功结果
        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value="员工注销")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * 封装数据传输给 employeeService
     * @param employeeDTO
     * @return
     */
    // 使用PostMapping注解，表示该方法处理POST请求
    @PostMapping
    // 使用ApiOperation注解，表示该方法的功能描述
    @ApiOperation("新增员工")
    // 处理POST请求，接收EmployeeDTO类型的参数
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}",employeeDTO);
        // 调用employeeService的save方法，保存员工信息
        employeeService.save(employeeDTO);
        // 返回成功结果
        return Result.success();
    }

}
