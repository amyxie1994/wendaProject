/**
 * Created by amyxie in 2018
 * UserDao.java
 * 5 Feb. 2018
 */
package com.example.snsProject.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.snsProject.model.User;

@Mapper
public interface UserDao {
	String TABLE_NAME = " user ";
	@Insert({"insert into ",TABLE_NAME," (name,password,salt,url)values(#{name},#{password},#{salt},#{url})"})
	int addUser(User user);
	
	@Select({"select * from ",TABLE_NAME})
	User getUserInfo();
	
	@Select({"select * from ",TABLE_NAME,"where id = #{userId}"})
	User getUserById(int userId);

	
	
	@Select({"select * from ",TABLE_NAME,"where name = #{name}"})
	User getUserByName(String name);
	
}
 