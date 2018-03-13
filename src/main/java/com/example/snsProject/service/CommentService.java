/**
 * Created by amyxie in 2018
 * CommentService.java
 * 3 Mar. 2018
 */
package com.example.snsProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snsProject.dao.CommentDAO;
import com.example.snsProject.model.Comment;

/**
 * @author amyxie
 *
 */
@Service
public class CommentService {
	
	@Autowired
	CommentDAO commentDAO;
	
	
	public int addComment(Comment comment) {
		//comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
		//comment.setContent(sensitiveWordService.filter(comment.getContent()));
		return commentDAO.addComment(comment);
	}
	
	public List<Comment> selectByEntity(int entityId,int entityType){
		return commentDAO.selectByEntity(entityId, entityType);
	}
	
	public int getCommentCount(int entityId,int entityType ) {
		return commentDAO.getCommentCount(entityId, entityType);
	}
	
	public void updateStatus(int entityId,int entityType) {
		commentDAO.updateStatus(entityId, entityType);
	}

	/**TODO
	 * @param commentId
	 * @return
	 */
	public Comment getCommentById(int commentId) {
		// TODO Auto-generated method stub
		return commentDAO.getCommentById(commentId);
	}
	
	
	

}
