package org.newbieApps.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.newbieApps.messenger.model.Comment;
import org.newbieApps.messenger.service.CommentService;
/*It is best practise to use consumes and produces annotations at class level.
 * */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {
	CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("msgId") long messageId){
		return commentService.getAllComments(messageId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("msgId") long messageId,@PathParam("commentId") long commentId){
		return commentService.getComment(messageId,commentId);
	}
	
	@POST
	public Comment addComment(@PathParam("msgId") long messageId, Comment comment){
		return commentService.addComment(messageId,comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment editComment(@PathParam("msgId") long messageId, @PathParam("commentId") long commentId, 
			Comment comment){
		comment.setCommentId(commentId);
		return commentService.updateComment(messageId,comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void removeComment(@PathParam("msgId") long messageId,@PathParam("commentId") long commentId){
		commentService.deleteComment(messageId,commentId);
	}
}
