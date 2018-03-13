/**
 * Created by amyxie in 2018
 * LikeController.java
 * 10 Mar. 2018
 */
package com.example.snsProject.controller;

import java.util.Date;

import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.snsProject.async.EventModel;
import com.example.snsProject.async.EventProducer;
import com.example.snsProject.async.EventType;
import com.example.snsProject.model.Comment;
import com.example.snsProject.model.EntityType;
import com.example.snsProject.model.HostHolder;
import com.example.snsProject.model.Question;
import com.example.snsProject.service.CommentService;
import com.example.snsProject.service.LikeService;
import com.example.snsProject.util.JsonFunction;

/**
 * @author amyxie
 *
 */
@Controller
public class LikeController {
	
	private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	EventProducer eventProducer;
	
	
	@Autowired
	CommentService commentService;
	
	
	@RequestMapping(path= {"/like"},method= {RequestMethod.POST} )
	@ResponseBody
	public String like(@RequestParam("commentId") int commentId) {
         if(hostHolder.getUser() == null) {
        	 return JsonFunction.getJsonString(999);
	}
         Comment comment = commentService.getCommentById(commentId);
         eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId())
        		 .setEntityType(EntityType.ENTITY_COMMENT).setEntityId(commentId)
        		 .setEntityOwnerId(comment.getUserId()).setExt("questionId", String.valueOf(comment.getEntityId())));
         


         
         
         long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
         return JsonFunction.getJsonString(0,String.valueOf(likeCount));
         
     }
	
	@RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return JsonFunction.getJsonString(999);
        }

        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return JsonFunction.getJsonString(0, String.valueOf(likeCount));
    }

}
