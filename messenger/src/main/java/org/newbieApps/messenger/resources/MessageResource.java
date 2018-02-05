package org.newbieApps.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.newbieApps.messenger.model.Message;
import org.newbieApps.messenger.resources.beans.MessageFilterBean;
import org.newbieApps.messenger.service.MessageService;

@Path("/messages")
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJSONMessages(@QueryParam("year") int yr,@BeanParam MessageFilterBean filterBean) {
		if (yr > 0)
			return messageService.getAllMsgsForYear(yr);
		if(filterBean.getStart()>=0 && filterBean.getSize()>0)
			return messageService.getAllMsgsPaginated(filterBean.getStart(),filterBean.getSize());
		return messageService.getAllMessages();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXMLMessages(@QueryParam("year") int yr,@BeanParam MessageFilterBean filterBean) {
		if (yr > 0)
			return messageService.getAllMsgsForYear(yr);
		if(filterBean.getStart()>=0 && filterBean.getSize()>0)
			return messageService.getAllMsgsPaginated(filterBean.getStart(),filterBean.getSize());
		return messageService.getAllMessages();
	}
		
	@GET
	@Path("/{msgId}")
	/*To allow xml type to work resource objects needs to be annotated with JAX-B annotations*/
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("msgId") long id, @Context UriInfo uriInfo){
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message),"self");
		message.addLink(getUriForProfile(uriInfo, message),"profile");
		message.addLink(getUriForComments(uriInfo, message),"comments");
		return message;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(message.getId()))
				.build()
				.toString();
		return uri;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build()
				.toString();
		return uri;
	}
	
	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class,"getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("msgId", message.getId())
				.build()
				.toString();
		return uri;
	}
	
	/*Return type of this message is response that can be built using builder pattern*/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessages(Message message,@Context UriInfo uriInfo){
		Message newMessage = messageService.addMessage(message);
		String id=String.valueOf(newMessage.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(id).build();
		/*Response includes newly created uri and entity() takes newly created message that should be
		 * returned with response. Response.created() assign created{201} status code in response header.*/
		return Response.created(uri)
				.entity(newMessage)
				.build();
	}
	
	@PUT
	@Path("/{msgId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("msgId") long id,Message message){
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{msgId}")
	public void deleteMessage(@PathParam("msgId") long id){
		messageService.removeMessage(id);
	}
	
	/*This method is added in order to make comment sub-resource available in message resource */
	@Path("/{msgId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
	
	
}
