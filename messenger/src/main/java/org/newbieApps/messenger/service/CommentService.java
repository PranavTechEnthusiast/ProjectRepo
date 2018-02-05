package org.newbieApps.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.newbieApps.messenger.database.DatabaseClass;
import org.newbieApps.messenger.model.Comment;
import org.newbieApps.messenger.model.ErrorMessage;
import org.newbieApps.messenger.model.Message;

public class CommentService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		return new ArrayList<Comment>(messages.get(messageId).getComments().values());
	}

	public Comment getComment(long messageId, long commentId) {
		/*This is kind of presentation layer code that's why custom web application exceptions 
		 * with response builders are not preferred */
		ErrorMessage errorMessage = new ErrorMessage("Custom: Message Not Found", 404, "https://google.co.in");
		Response customResponse=Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
		Message message=messages.get(messageId);
		if(message == null){
			throw new WebApplicationException(customResponse);
		}
		Comment comment=message.getComments().get(commentId);
		if(comment== null){
			throw new NotFoundException("Comment Not Found", customResponse);
		}
		return comment ;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		comment.setCommentId(comments.size()+1);
		comments.put(comment.getCommentId(), comment);
		return comments.get(comment.getCommentId());
	}

	public Comment updateComment(long messageId, Comment comment) {
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		if(comment.getCommentId()<=0){
			return null;
		}
		comments.put(comment.getCommentId(), comment);
		return comments.get(comment.getCommentId());
	}

	public Comment deleteComment(long messageId, long commentId) {
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}

	

}
