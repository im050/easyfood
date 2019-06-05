package com.im050.easyfood.common.entity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
@Data
@Accessors(chain = true)
public class Merchant extends SuperEntity<Merchant> {

	private String username;
	private String password;
	private String realName;
	private Integer parentId;
	private Integer roleId;
	private String phoneNumber;
	private String email;

}
