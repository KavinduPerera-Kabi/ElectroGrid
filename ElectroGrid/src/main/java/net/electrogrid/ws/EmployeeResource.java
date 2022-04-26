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

@Path("/employees")
public class EmployeeResource {
	
private  EmployeeDAO dao = EmployeeDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> list(){
		return dao.ListAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Employee employee) throws URISyntaxException {
		int newEmployeeId = dao.add(employee);
		URI uri = new URI("/employeemanagement/employees/" + newEmployeeId);
		
		return Response.created(uri).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@FormParam("name") String name,
			          @FormParam("address") String address,
			          @FormParam("phone") String phone,
			          @FormParam("gender") String gender,
			          @FormParam("age") int age,
			          @FormParam("email") String email
			          ) throws URISyntaxException {
		Employee employee = new Employee();
		employee.setName(name);
		employee.setAddress(address);
		employee.setPhone(phone);
		employee.setGender(gender);
		employee.setAge(age);
		employee.setEmail(email);
		
		int newEmployeeId = dao.add(employee);
		
		return "Item added! new id = "+newEmployeeId;
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		Employee employee = dao.get(id);
		if(employee != null) {
			return Response.ok(employee, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Employee employee) {
		employee.setEmployeeId(id);
		if(dao.update(employee)) {
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

