package com.nanc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chennan
 * @date 2018/7/5 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee implements Serializable {
	private static final long serialVersionUID = 1748441780826308444L;

	private Integer id;
	private String lastName;
	private String email;
	/**
	 * 性别 1男  0女
	 */
	private Integer gender;
	private Integer dId;
}
