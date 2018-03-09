/**
 * Created by amyxie in 2018
 * MessageDAO.java
 * 5 Mar. 2018
 */
package com.example.snsProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.snsProject.model.Message;


/**
 * @author amyxie
 *
 */
@Mapper
public interface MessageDAO {
	
	String TABLE_NAME = " message ";
	String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
	@Insert({"insert into ",TABLE_NAME," (from_id,to_id,content,conversation_id,created_date,has_read)values(#{fromId},#{toId},#{content},#{conversationId},#{createdDate},#{hasRead})"})
	int addMessage(Message message);
	
	
	
	
	@Select({"select * from " ,TABLE_NAME, "where conversation_id = #{conversationId} order by id desc limit #{offset},#{limit}"})
	List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);
	
	@Select({"select * from " ,TABLE_NAME, "where id = #{id}"})
	Message selectMessageById(int id);
	
	@Update({"update ", TABLE_NAME, "set comment_count = #{count} where id = #{id}"})
	void updateCommentCount(@Param("id") int id,@Param("count") int count);
	
    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConvesationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    
    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id  order by created_date desc limit #{offset}, #{limit}"})
    //@Select({"select * , count(id) as id from ( select * from ", TABLE_NAME, " where from_id = #{userId} or to_id = #{userId} order by id desc) tt group by conversation_id  order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,@Param("offset") int offset, @Param("limit") int limit);
}
