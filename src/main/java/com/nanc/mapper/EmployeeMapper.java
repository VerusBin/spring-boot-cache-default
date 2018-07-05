package com.nanc.mapper;

import com.nanc.entity.Employee;

/**
 * @author chennan
 * @date 2018/7/5 14:25
 */
public interface EmployeeMapper {
	Employee getById(Integer id);

	Integer updateById(Employee employee);

	Integer deleteById(Integer id);

	Integer insertEmp(Employee employee);

	Employee getByName(String lastName);
}
