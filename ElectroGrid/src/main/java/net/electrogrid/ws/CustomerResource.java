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


@Path("/customers")
public class CustomerResource {
	
private CustomerDAO dao = CustomerDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> list(){
		return dao.ListAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Customer customer) throws URISyntaxException {
		int customer_ID = dao.add(customer);
		URI uri = new URI("/customermanagement/customers/" + customer_ID);
		
		return Response.created(uri).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@FormParam("nic") String nic,
			          @FormParam("name") float name,
			          @FormParam("address") String address,
			          @FormParam("phone") String phone,
			          @FormParam("gender") String gender,
			          @FormParam("age") int age,
			          @FormParam("email") String email) throws URISyntaxException {
		Customer customer = new Customer();
		customer.setNic(nic);
		customer.setAddress(address);
		customer.setPhone(phone);
		customer.setGender(gender);
		customer.setAge(age);
		customer.setEmail(email);
		
		int newcustomer_ID = dao.add(customer);
		
		return "Item added! new id = "+newcustomer_ID;
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		Customer customer = dao.get(id);
		if(customer != null) {
			return Response.ok(customer, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id,Customer customer) {
		customer.setCustomerId(id);
		if(dao.update(customer)) {
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
