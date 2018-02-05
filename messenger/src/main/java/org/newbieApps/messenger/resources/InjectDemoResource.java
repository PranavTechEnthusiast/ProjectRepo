package org.newbieApps.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
/*This is a demo resource to demonstrate use of different types of parameters.
 * This does not have any significant use in the messenger application*/
@Path("/injectdemo")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	/*Matrix Param should be passed in as "url/xxx;param=xyz"
	 *Header Param passed in as header value in http request headers
	 *Cookie Param value is passed through cookies
	 *Apart from these three FormParam(not widely used) exists that maps to html form elements
	 *These parameters can be used if you know exact names of the parameters 
	 *otherwise contextparam or beanparam can be used.*/
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("matrixParam") String matrixParam,
											@HeaderParam("headerParam") String headerParam,
											@CookieParam("cookieParameter") String cookieParam) {
		return "Matrix:"+matrixParam+" :: Header:"+headerParam+" :: Cookie"+cookieParam;
	}

	/*Special types of instance need to be used along with @Context annotation.
	 * They are HttpHeaders,UriInfo*/
	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo,@Context HttpHeaders headers){
		String path=uriInfo.getAbsolutePath().toString();
		String cookies=headers.getCookies().toString();
		return "Path:"+path+" Cookies:"+cookies;
	}
	
}
