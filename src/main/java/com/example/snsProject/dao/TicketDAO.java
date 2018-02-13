/**
 * Created by amyxie in 2018
 * TicketDAO.java
 * 12 Feb. 2018
 */
package com.example.snsProject.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.snsProject.model.Ticket;


/**
 * @author amyxie
 *
 */

@Mapper
public interface TicketDAO {
	
	
	String TABLE_NAME = " ticket ";
	String INSERT_FILEDS = " user_id,expired_date,status,ticket ";
	
	@Insert({"insert into ",TABLE_NAME," (",INSERT_FILEDS,")values(#{userId},#{expiredDate},#{status},#{ticket})"})
	int addTicket(Ticket ticket);
	
	
	@Update({"update ",TABLE_NAME," set  status = #{status} where ticket = #{ticket}"})
	void updateStatus(@Param("ticket") String ticket,@Param("status") int status);
	
	@Select({"select * from ",TABLE_NAME,"  where ticket = #{ticket}"})
	Ticket getTicketByTicket(String ticket);
	
	
	

}
