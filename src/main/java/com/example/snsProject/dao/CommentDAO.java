/**
 * Created by amyxie in 2018
 * CommentDAO.java
 * 3 Mar. 2018
 */
package com.example.snsProject.dao;



import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.snsProject.model.Comment;

/**
 * @author amyxie
 *
 */
@Mapper
public interface CommentDAO {
	
	String TABLE_NAME = "comment";
	
	
	@Insert({"insert into ",TABLE_NAME, "(user_id ,content, entity_id,created_date,entity_type,status) values (#{userId}, #{content},  #{entityId}, #{createdDate},#{entityType},#{status})"})
	int addComment(Comment comment);
	
	@Select({"select * from ", TABLE_NAME, " where entity_id = #{entityId} and entity_type = #{entityType} order by created_date desc"})
	List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);
	
	
	@Update({"update " , TABLE_NAME, "set status = #{status} where entity_id = #{entityId} and entity_type = #{entityType}"})
	void updateStatus(@Param("entityId") int entityId, @Param("entityType") int entityType);
	
	@Select({"select count(id) from " , TABLE_NAME, "where entity_id = #{entityId} and entity_type = #{entityType}"}) 
	int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);
	
	

}
