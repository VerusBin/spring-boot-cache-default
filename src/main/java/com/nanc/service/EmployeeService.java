package com.nanc.service;

import com.nanc.entity.Employee;
import com.nanc.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author chennan
 * @date 2018/5/30 17:37
 */
@Service
@CacheConfig(cacheManager = "employeeCacheManager")//指定统一的cacheManager
public class EmployeeService {
	@Autowired
	private EmployeeMapper employeeMapper;


	@Cacheable(value = "cache_10_minutes",keyGenerator = "myKeyGenerator")
	public Employee getById(Integer id){
		System.out.println("查询 " + id + " 号员工");
		return employeeMapper.getById(id);
	}

	@CachePut(value = "cache_10_minutes",key="#result.id")
	public Integer updateEmp(Employee employee) {
		return employeeMapper.updateById(employee);
	}


	/**
	 * 会清除emp这个cache下的所有缓存,此时value值也可以不配置
	 * 因为当配置的是redis，此时会把redis   database=2下面的所有数据都清除了
	 * @param id
	 * @return
	 */
	@CacheEvict(value="cache_10_minutes",allEntries = true)
	public Integer deleteById(Integer id){
		return 1;
		//return employeeMapper.deleteById(id);
	}

	@Caching(
			cacheable = {
					@Cacheable(value = "cache_10_minutes", key="#lastName")
			},
			put = {
					@CachePut(value = "cache_10_minutes",key = "#result.id"),
					@CachePut(value = "cache_10_minutes", key="#result.email")
			}
	)
	public Employee getByName(String lastName){
		return employeeMapper.getByName(lastName);
	}

	public Integer insertEmp(Employee employee) {
		return employeeMapper.insertEmp(employee);
	}
}
