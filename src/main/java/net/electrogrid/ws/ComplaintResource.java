package net.electrogrid.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/complaints")
public class ComplaintResource {
	
private ComplaintDAO dao = ComplaintDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Complaint> list(){
		return dao.ListAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Complaint complaint) throws URISyntaxException {
		int newcomplaintId = dao.add(complaint);
		URI uri = new URI("/complaintmanagement/payments/" + newcomplaintId);
		
		return Response.created(uri).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@FormParam("customerName") String customerName,
			          @FormParam("complaint") String complaint) throws URISyntaxException {
		Complaint compliant = new Complaint();
		compliant.setCustomerName(customerName);
		compliant.setComplaint(complaint);
		
		int newcomplaintId = dao.add(compliant);
		
		return "Item added! new id = "+newcomplaintId;
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		Complaint compliant = dao.get(id);
		if(compliant != null) {
			return Response.ok(compliant, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Complaint compliant) {
		compliant.setComId(id);
		if(dao.update(compliant)) {
			return Response.ok().build();
		}else {
			return Response.notModified().build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		if(dao.delete(id)) {
			return Response.ok().build();
		}else {
			return Response.notModified().build();
		}
	}

}
