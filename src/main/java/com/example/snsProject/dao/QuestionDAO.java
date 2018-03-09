/**
 * Created by amyxie in 2018
 * UserDao.java
 * 5 Feb. 2018
 */
package com.example.snsProject.dao;



import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.snsProject.model.Question;

@Mapper
public interface QuestionDAO {
	String TABLE_NAME = " question ";
	@Insert({"insert into ",TABLE_NAME," (title,content,created_date,comment_count,user_id)values(#{title},#{content},#{createdDate},#{commentCount},#{userId})"})
	int addQuestion(Question question);
	
	
	List<Question> selectLatestQuestion(@Param("userId") int userId,
			@Param("offset") int offset,
			@Param("limit") int limit);
	
	@Select({"select * from " ,TABLE_NAME, "where id = #{id}"})
	Question selectById(int id);
	
	@Update({"update ", TABLE_NAME, "set comment_count = #{count} where id = #{id}"})
	void updateCommentCount(@Param("id") int id,@Param("count") int count);
	
}
 