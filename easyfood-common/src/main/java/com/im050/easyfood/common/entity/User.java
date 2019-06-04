package com.im050.easyfood.common.entity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author linyulin
 * @since 2019-05-31
 */
@Data
@Accessors(chain = true)
public class User extends SuperEntity<User> {

	private String phoneNumber;
	private String nickname;
	private String wxOpenId;
	private String email;

}
